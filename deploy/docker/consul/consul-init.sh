#!/bin/sh

alias consul="docker exec -i $(docker ps|awk '/consul:latest/ {print $1}') consul"

while ! nc -z localhost 8500;
do
  echo sleeping;
  sleep 1;
done;
echo "Consul Connected"

sleep 2

consul acl bootstrap |awk -F: '/:/ {
    if ($1 ~ /SecretID/) print "CONSUL_ACL_TOKEN="$2
}' \
| sed 's/ //g' \
| tee > deploy/docker/.env

source deploy/docker/.env

function json_vcp {
   sed -e "s#\${CONSUL}#consul#" \
       -e "s#\${TOKEN}#$CONSUL_ACL_TOKEN#" \
          deploy/docker/vault/vault-config-template.json
}

echo "$(json_vcp)" >  deploy/docker/vault/config/vault-config.json
