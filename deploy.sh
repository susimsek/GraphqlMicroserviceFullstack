#!/bin/sh
# Files are ordered in proper order with needed wait for the dependent custom resource definitions to get initialized.
# Usage: bash deploy.sh

print_usage() {
  echo "App Deployment Strategies"
  echo " "
  echo "Deployment [options] application [arguments]"
  echo " "
  echo "options:"
  echo "-h                show brief help"
  echo "-d                deploy docker using docker-compose"
  echo "-k                deploy kubernetes using helm"
  echo "-i                enable istio for kubernetes deployment"
  echo "-r                remove app"
  echo "-u                update app(only kubernetes support)"
  exit 0
}

remove=""
upgrade=""
istio=""
docker=""
k8s=""
while getopts 'hdkiru' flag; do
    case "${flag}" in
        h) print_usage
           exit 1 ;;
        d) docker='true' ;;
        k) k8s='true' ;;
        i) istio='true' ;;
        r) remove='true' ;;
        u) upgrade='true' ;;
        *) print_usage
           exit 1 ;;
    esac
done

if [ $# -eq 0 ]
  then
    print_usage
    exit 1
fi

suffix=helm
name=myapp
namespace=demo
args=""
helmVersion=$(helm version --client | grep -E "v3\\.[0-9]{1,3}\\.[0-9]{1,3}" | wc -l)

if [ -n "$remove" ]; then
   if [ -n "$docker" ]; then
         docker-compose -f ./deploy/docker/docker-compose.yaml down -v
         rm -f deploy/docker/.env
         rm -f deploy/docker/vault/config/vault-config.json
   elif [ -n "$k8s" ]; then
        helm uninstall mongodb -n ${namespace}
        helm uninstall consul -n ${namespace}
        helm uninstall vault -n ${namespace}
        helm uninstall ${name} -n ${namespace}
        kubectl -n ${namespace} delete sa internal-app
        kubectl delete pvc --selector="chart=consul-helm" -n ${namespace}
        kubectl -n ${namespace} get secrets --field-selector="type=Opaque" | grep consul | awk '{print $1}' | xargs kubectl -n ${namespace} delete secret
        kubectl -n ${namespace} delete serviceaccount consul-tls-init
        rm -f deploy/${suffix}/cluster-keys.json
          if [ -n "$istio" ]; then
             kubectl remove -f ./deploy/istio-k8s
             kubectl label namespace ${namespace} istio-injection-
          fi
   fi
elif [ -n "$upgrade" ]; then
  if [ -n "$k8s" ]; then
     helm upgrade --install ${name} ./deploy/${suffix} --namespace ${namespace}
  fi
else
   if [ -n "$docker" ]; then
       docker-compose -f ./deploy/docker/docker-compose.yaml up -d consul
       sudo chmod +x ./deploy/docker/consul/consul-init.sh
       ./deploy/docker/consul/consul-init.sh
       docker-compose -f ./deploy/docker/docker-compose.yaml up -d vault
       sudo chmod +x ./deploy/docker/vault/vault-init.sh
       ./deploy/docker/vault/vault-init.sh
       docker-compose -f ./deploy/docker/docker-compose.yaml up -d
   elif [ -n "$k8s" ]; then
     if [ $helmVersion -eq 1 ]; then
       helm uninstall ${name} 2>/dev/null
     else
       helm remove --purge ${name} 2>/dev/null
     fi
      kubectl create namespace ${namespace}
      helm repo add hashicorp https://helm.releases.hashicorp.com
      helm repo add bitnami https://charts.bitnami.com/bitnami
      helm repo update
      helm install mongodb bitnami/mongodb --values ./deploy/${suffix}/helm-mongodb-values.yml -n ${namespace}
      kubectl rollout status deployment mongodb -n ${namespace}
      helm install consul hashicorp/consul --values ./deploy/${suffix}/helm-consul-values.yml -n ${namespace}
      kubectl rollout status statefulset consul-server -n ${namespace}
      kubectl rollout status daemonset consul-client -n ${namespace}
      helm install vault hashicorp/vault --values ./deploy/${suffix}/helm-vault-values.yml -n ${namespace}
      kubectl rollout status deployment vault-agent-injector -n ${namespace}
      sudo chmod +x ./deploy/helm/vault/vault-init.sh
      ./deploy/helm/vault/vault-init.sh $namespace

      helm dep up ./deploy/${suffix}
       if [ -n "$istio" ]; then
           kubectl label namespace ${namespace} istio-injection=enabled --overwrite=true
           kubectl apply -f ./deploy/istio-k8s
           args="--set istio.enabled=true"
       fi
       if [ $helmVersion -eq 1 ]; then
         helm install ${name} ./deploy/${suffix} --replace --namespace ${namespace} ${args}
       else
         helm install --name ${name} ./deploy/${suffix} --replace --namespace ${namespace} ${args}
       fi
   fi
fi