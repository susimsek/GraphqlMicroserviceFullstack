#!/bin/sh
alias vault="kubectl -n demo exec -i  vault-0 -- vault"
namespace=$1
suffix=$2
kubectl -n ${namespace} exec vault-0 -- vault operator init -key-shares=1 -key-threshold=1 -format=json > ./deploy/${suffix}/cluster-keys.json
      VAULT_UNSEAL_KEY=$(cat ./deploy/${suffix}/cluster-keys.json | jq -r ".unseal_keys_b64[]")
      INITIAL_ROOT_TOKEN=$(cat ./deploy/${suffix}/cluster-keys.json | jq -r ".root_token")

      echo $INITIAL_ROOT_TOKEN
      for i in 0 1 2
      do
        kubectl -n ${namespace} exec vault-$i -- vault operator unseal $VAULT_UNSEAL_KEY
      done

      vault login $INITIAL_ROOT_TOKEN
      vault secrets enable -path=secret/ kv
      vault secrets enable database
      vault write database/config/mongodb \
          plugin_name=mongodb-database-plugin \
          allowed_roles="auth-admin-role","product-admin-role","review-admin-role" \
          connection_url="mongodb://{{username}}:{{password}}@mongodb:27017/admin?tls=false" \
          username="root" \
          password="g44bsljDAi5nbWjf"

      vault write database/roles/auth-admin-role \
          db_name=mongodb\
          creation_statements='{ "db": "admin", "roles": [{ "role": "readWrite" }, {"role": "readWrite", "db": "auth"}] }' \
          default_ttl="1h" \
          max_ttl="24h"

      vault write database/roles/product-admin-role \
          db_name=mongodb\
          creation_statements='{ "db": "admin", "roles": [{ "role": "readWrite" }, {"role": "readWrite", "db": "product"}] }' \
          default_ttl="1h" \
          max_ttl="24h"

      vault write database/roles/review-admin-role \
          db_name=mongodb\
          creation_statements='{ "db": "admin", "roles": [{ "role": "readWrite" }, {"role": "readWrite", "db": "review"}] }' \
          default_ttl="1h" \
          max_ttl="24h"

      vault kv put secret/application/prod SPRING_SECURITY_OAUTH2_RESOURCE-SERVER_JWT_ISSUER-URI=http://auth-service:9000 SPRING_CLOUD_CONSUL_HOST=consul-server SPRING_CLOUD_CONSUL_PORT=8500 SPRING_DATA_MONGODB_HOST=mongodb SPRING_DATA_MONGODB_PORT=27017 LOGSTASH_HOST=logstash-logstash LOGSTASH_PORT=5000 SPRING_REDIS_HOST=redis-master SPRING_REDIS_PORT=6379 SPRING_REDIS_DATABASE=0 SPRING_REDIS_PASSWORD=d41d8cd98f00b204e9800998ecf8427e
      vault kv put secret/auth-service/prod PORT=9000 AUTH-SERVER_PROVIDER_ISSUER=http://auth-service:9000 CORS_ALLOWED-ORIGINS='http://localhost:3000, http://127.0.0.1:3000, http://gqlmsweb.susimsek.github.io, https://gqlmsweb.susimsek.github.io' GOOGLE_CLIENT_ID=10959265505-a56ge3f9j1p4p0gf3brntbfu3r1sa58t.apps.googleusercontent.com GOOGLE_CLIENT_SECRET=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 ACCESS_TOKEN_TIME_TO_LIVE=5m REFRESH_TOKEN_TIME_TO_LIVE=5m
      vault kv put secret/product-service/prod PORT=8081 CACHE_EXPIRATION=3600s
      vault kv put secret/review-service/prod PORT=8082 CACHE_EXPIRATION=3600s
      vault kv put secret/apollo-gateway/production PORT=4000 CORS_ALLOWED_ORIGINS='http://localhost:3000, http://127.0.0.1:3000, https://studio.apollographql.com' CONSUL_HOST=consul-server CONSUL_PORT=8500

      vault auth enable kubernetes

      kubectl -n ${namespace} exec vault-0 -- /bin/sh -c 'vault write auth/kubernetes/config \
                                                        kubernetes_host="https://$KUBERNETES_PORT_443_TCP_ADDR:443" \
                                                        token_reviewer_jwt="$(cat /var/run/secrets/kubernetes.io/serviceaccount/token)" \
                                                        kubernetes_ca_cert=@/var/run/secrets/kubernetes.io/serviceaccount/ca.crt \
                                                        issuer="https://kubernetes.default.svc.cluster.local"'
      vault policy write internal-app - <<EOF
      path "secret/auth-service*" {
        capabilities = ["create", "read", "update", "delete", "list"]
      }

      path "secret/product-service*" {
        capabilities = ["create", "read", "update", "delete", "list"]
      }

      path "secret/review-service*" {
        capabilities = ["create", "read", "update", "delete", "list"]
      }

      path "secret/application*" {
        capabilities = ["create", "read", "update", "delete", "list"]
      }

       path "secret/apollo-gateway*" {
         capabilities = ["create", "read", "update", "delete", "list"]
      }

      path "database/creds*" {
         capabilities = ["read"]
      }
EOF
      kubectl -n ${namespace} create sa internal-app
      vault write auth/kubernetes/role/internal-app \
          bound_service_account_names=internal-app \
          bound_service_account_namespaces=${namespace} \
          policies=internal-app \
          ttl=24h