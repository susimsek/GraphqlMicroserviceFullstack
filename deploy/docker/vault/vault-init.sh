#!/bin/sh

alias vault="docker exec -i $(docker ps|awk '/vault:latest/ {print $1}') vault"

while ! nc -z localhost 8200;
do
  echo sleeping;
  sleep 1;
done;
echo "Vault Connected"

sleep 20

vault operator init |awk -F: '/:/ {
    if ($1 ~ /Unseal Key 1/) print "UNSEAL_KEY_1="$2
    if ($1 ~ /Unseal Key 2/) print "UNSEAL_KEY_2="$2
    if ($1 ~ /Unseal Key 3/) print "UNSEAL_KEY_3="$2
    if ($1 ~ /Unseal Key 4/) print "UNSEAL_KEY_4="$2
    if ($1 ~ /Unseal Key 5/) print "UNSEAL_KEY_5="$2
    if ($1 ~ /Initial Root Token/) print "INITIAL_ROOT_TOKEN="$2
}' \
| sed 's/ //g' \
| tee -a deploy/docker/.env

source deploy/docker/.env

# unseal the vault using 3 of the keys
vault operator unseal $UNSEAL_KEY_1
vault operator unseal $UNSEAL_KEY_2
vault operator unseal $UNSEAL_KEY_3

echo $INITIAL_ROOT_TOKEN|vault login -

vault auth enable approle
vault secrets enable -path=secret/ kv

# enable file audit to the mounted logs volume
vault audit enable file file_path=/vault/logs/audit.log
vault audit list

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

# create the app-specific policy
vault policy write auth-service /policies/auth-service-policy.json
vault policy read auth-service

vault policy write product-service /policies/product-service-policy.json
vault policy read product-service

vault policy write review-service /policies/review-service-policy.json
vault policy read review-service

vault policy write apollo-gateway /policies/apollo-gateway-policy.json
vault policy read apollo-gateway

vault policy write app /policies/app-policy.json
vault policy read app

# create the app token
vault token create -policy=app | awk '/token/ {
if ($1 == "token") print "APP_TOKEN="$2
else if ($1 == "token_accessor") print "APP_TOKEN_ACCESSOR="$2
}' \
| tee -a deploy/docker/.env

vault write auth/approle/role/myapp \
secret_id_num_uses=0 \
secret_id_ttl=0 \
token_num_uses=0 \
token_ttl=10m \
token_max_ttl=10m \
policies=app

vault write auth/approle/role/auth \
secret_id_num_uses=0 \
secret_id_ttl=0 \
token_num_uses=0 \
token_ttl=10m \
token_max_ttl=10m \
policies=auth-service

vault write auth/approle/role/product \
secret_id_num_uses=0 \
secret_id_ttl=0 \
token_num_uses=0 \
token_ttl=10m \
token_max_ttl=10m \
policies=product-service

vault write auth/approle/role/review \
secret_id_num_uses=0 \
secret_id_ttl=0 \
token_num_uses=0 \
token_ttl=10m \
token_max_ttl=10m \
policies=review-service

vault write auth/approle/role/apollo-gateway \
secret_id_num_uses=0 \
secret_id_ttl=0 \
token_num_uses=0 \
token_ttl=10m \
token_max_ttl=10m \
policies=apollo-gateway

vault write auth/approle/role/app \
secret_id_num_uses=0 \
secret_id_ttl=0 \
token_num_uses=0 \
token_ttl=10m \
token_max_ttl=10m \
policies=app

vault read auth/approle/role/myapp/role-id | awk '/role_id/ {
print "MYAPP_APP_ROLE_ROLE_ID="$2
}' \
| tee -a deploy/docker/.env

vault write -f auth/approle/role/myapp/secret-id | awk '/secret_id/ {
if ($1 == "secret_id") print "MYAPP_APP_ROLE_SECRET_ID="$2
else if ($1 == "secret_id_accessor") print "MYAPP_APP_ROLE_SECRET_ID_ACCESSOR="$2
}' \
| tee -a deploy/docker/.env

vault read auth/approle/role/auth/role-id | awk '/role_id/ {
print "AUTH_APP_ROLE_ROLE_ID="$2
}' \
| tee -a deploy/docker/.env

vault write -f auth/approle/role/auth/secret-id | awk '/secret_id/ {
if ($1 == "secret_id") print "AUTH_APP_ROLE_SECRET_ID="$2
else if ($1 == "secret_id_accessor") print "AUTH_APP_ROLE_SECRET_ID_ACCESSOR="$2
}' \
| tee -a deploy/docker/.env

vault read auth/approle/role/product/role-id | awk '/role_id/ {
print "PRODUCT_APP_ROLE_ROLE_ID="$2
}' \
| tee -a deploy/docker/.env

vault write -f auth/approle/role/product/secret-id | awk '/secret_id/ {
if ($1 == "secret_id") print "PRODUCT_APP_ROLE_SECRET_ID="$2
else if ($1 == "secret_id_accessor") print "PRODUCT_APP_ROLE_SECRET_ID_ACCESSOR="$2
}' \
| tee -a deploy/docker/.env

vault read auth/approle/role/review/role-id | awk '/role_id/ {
print "REVIEW_APP_ROLE_ROLE_ID="$2
}' \
| tee -a deploy/docker/.env

vault write -f auth/approle/role/review/secret-id | awk '/secret_id/ {
if ($1 == "secret_id") print "REVIEW_APP_ROLE_SECRET_ID="$2
else if ($1 == "secret_id_accessor") print "REVIEW_APP_ROLE_SECRET_ID_ACCESSOR="$2
}' \
| tee -a deploy/docker/.env

vault read auth/approle/role/apollo-gateway/role-id | awk '/role_id/ {
print "APOLLO_GATEWAY_APP_ROLE_ROLE_ID="$2
}' \
| tee -a deploy/docker/.env

vault write -f auth/approle/role/apollo-gateway/secret-id | awk '/secret_id/ {
if ($1 == "secret_id") print "APOLLO_GATEWAY_APP_ROLE_SECRET_ID="$2
else if ($1 == "secret_id_accessor") print "APOLLO_GATEWAY_APP_ROLE_SECRET_ID_ACCESSOR="$2
}' \
| tee -a deploy/docker/.env

# source the env file to get the new key vars
source deploy/docker/.env

# create initial secrets
vault kv put secret/application/prod SPRING_SECURITY_OAUTH2_RESOURCE-SERVER_JWT_ISSUER-URI=http://auth-service:9000 SPRING_CLOUD_CONSUL_HOST=consul SPRING_CLOUD_CONSUL_PORT=8500 SPRING_CLOUD_CONSUL_DISCOVERY_ACL_TOKEN=${CONSUL_ACL_TOKEN} SPRING_DATA_MONGODB_HOST=mongodb SPRING_DATA_MONGODB_PORT=27017 LOGSTASH_HOST=logstash LOGSTASH_PORT=5000 SPRING_REDIS_HOST=redis SPRING_REDIS_PORT=6379 SPRING_REDIS_DATABASE=0 SPRING_REDIS_PASSWORD=d41d8cd98f00b204e9800998ecf8427e
vault kv put secret/auth-service/prod PORT=9000 AUTH-SERVER_PROVIDER_ISSUER=http://auth-service:9000 CORS_ALLOWED-ORIGINS='http://localhost:3000, http://127.0.0.1:3000, http://localhost, http://127.0.0.1' GOOGLE_CLIENT_ID=10959265505-a56ge3f9j1p4p0gf3brntbfu3r1sa58t.apps.googleusercontent.com GOOGLE_CLIENT_SECRET=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 ACCESS_TOKEN_TIME_TO_LIVE=5m REFRESH_TOKEN_TIME_TO_LIVE=5m
vault kv put secret/product-service/prod PORT=8081 CACHE_EXPIRATION=3600s
vault kv put secret/review-service/prod PORT=8082 CACHE_EXPIRATION=3600s
vault kv put secret/apollo-gateway/production PORT=4000 CORS_ALLOWED_ORIGINS='http://localhost:3000, http://127.0.0.1:3000, https://studio.apollographql.com' CONSUL_HOST=consul CONSUL_PORT=8500 CONSUL_ACL_TOKEN=${CONSUL_ACL_TOKEN} APOLLO_SCHEMA_CONFIG_EMBEDDED='true' SUPERGRAPH_PATH='/etc/config/supergraph.graphql' APOLLO_KEY=service:GqlMicroServiceGraph:RyxWPbKzOUBVjr75J2ln9A APOLLO_GRAPH_REF=GqlMicroServiceGraph@current
