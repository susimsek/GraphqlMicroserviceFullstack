#!/bin/sh
set -eu

ENV_STR=''

list_env_var_names() {
  ALL_ENV=$(env | sed -e 's/=.*//g')
  for var in $ALL_ENV
  do
    ENV_STR="$ENV_STR"" \$\$""$var"
  done
}

nginx_conf_envsubst() {
  envsubst "$ENV_STR" < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf
}

react_main_js_envsubst() {
  MAIN_JS=$(find /usr/share/nginx/html/static/js/main.*js)
  cp "$MAIN_JS" "$MAIN_JS".copy
  envsubst "$ENV_STR" < "$MAIN_JS".copy > "$MAIN_JS"
  rm "$MAIN_JS".copy
}

list_env_var_names
nginx_conf_envsubst
react_main_js_envsubst
exec "$@"