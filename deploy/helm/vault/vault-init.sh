#!/bin/sh
alias vault="kubectl -n demo exec -i  vault-0 -- vault"
namespace=$1

kubectl -n ${namespace} exec vault-0 -- vault operator init -key-shares=1 -key-threshold=1 -format=json > ./deploy/${suffix}/cluster-keys.json
      VAULT_UNSEAL_KEY=$(cat ./deploy/${suffix}/cluster-keys.json | jq -r ".unseal_keys_b64[]")
      INITIAL_ROOT_TOKEN=$(cat ./deploy/${suffix}/cluster-keys.json | jq -r ".root_token")

      echo $INITIAL_ROOT_TOKEN
      for i in 0 1 2
      do
        kubectl -n ${namespace} exec vault-$i -- vault operator unseal $VAULT_UNSEAL_KEY
      done

      vault login $INITIAL_ROOT_TOKEN
      vault secrets enable -path=secret kv-v2

      vault kv put secret/application/prod SPRING_SECURITY_OAUTH2_RESOURCE-SERVER_JWT_ISSUER-URI=http://auth-service:9000
      vault kv put secret/auth-service/prod PORT=9000 AUTH-SERVER_PROVIDER_ISSUER=http://auth-service:9000 CORS_ALLOWED-ORIGINS='http://localhost:3000, http://127.0.0.1:3000, http://gqlmsweb.susimsek.github.io, https://gqlmsweb.susimsek.github.io' SPRING_DATA_MONGODB_URI=mongodb://auth-admin:iXCjXb7e2yjJbjRa@mongodb:27017/auth GOOGLE_CLIENT_ID=10959265505-a56ge3f9j1p4p0gf3brntbfu3r1sa58t.apps.googleusercontent.com GOOGLE_CLIENT_SECRET=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
      vault kv put secret/product-service/prod PORT=8081 SPRING_DATA_MONGODB_URI=mongodb://product-admin:iXCjXb7e2yjJbjRp@mongodb:27017/product
      vault kv put secret/review-service/prod PORT=8082 SPRING_DATA_MONGODB_URI=mongodb://review-admin:iXCjXb7e2yjJbjRr@mongodb:27017/review
      vault kv put secret/apollo-gateway/production PORT=4000 CORS_ALLOWED_ORIGINS='http://localhost:3000, http://127.0.0.1:3000, https://studio.apollographql.com'

      vault auth enable kubernetes

      kubectl -n ${namespace} exec vault-0 -- /bin/sh -c 'vault write auth/kubernetes/config \
                                                        kubernetes_host="https://$KUBERNETES_PORT_443_TCP_ADDR:443" \
                                                        token_reviewer_jwt="$(cat /var/run/secrets/kubernetes.io/serviceaccount/token)" \
                                                        kubernetes_ca_cert=@/var/run/secrets/kubernetes.io/serviceaccount/ca.crt \
                                                        issuer="https://kubernetes.default.svc.cluster.local"'
      vault policy write internal-app - <<EOF
      path "secret/data/auth-service*" {
        capabilities = ["create", "read", "update", "delete", "list"]
      }

      path "secret/data/product-service*" {
        capabilities = ["create", "read", "update", "delete", "list"]
      }

      path "secret/data/review-service*" {
        capabilities = ["create", "read", "update", "delete", "list"]
      }

      path "secret/data/application*" {
        capabilities = ["create", "read", "update", "delete", "list"]
      }

       path "secret/data/apollo-gateway*" {
         capabilities = ["create", "read", "update", "delete", "list"]
      }
EOF
      kubectl -n ${namespace} create sa internal-app
      vault write auth/kubernetes/role/internal-app \
          bound_service_account_names=internal-app \
          bound_service_account_namespaces=${namespace} \
          policies=internal-app \
          ttl=24h