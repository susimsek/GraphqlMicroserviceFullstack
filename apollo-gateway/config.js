const dotenv = require('dotenv');
const path = require('path');
const fs = require("fs");

dotenv.config({
    path: path.resolve(__dirname, `${process.env.NODE_ENV}.env`)
});


const vaultUri = process.env.VAULT_URI || 'http://127.0.0.1:8200';

const vault = require("node-vault")({
    apiVersion: "v1",
    endpoint: vaultUri,
});

const roleId = process.env.VAULT_APP_ROLE_ROLE_ID;
const secretId = process.env.VAULT_APP_ROLE_SECRET_ID;

const kubernetesRole = process.env.KUBERNETES_ROLE;
const vaultAuthentication = process.env.VAULT_AUTHENTICATION;
const serviceAccountToken = KUBERNETES_SERVICE_ACCOUNT_TOKEN_FILE = "/var/run/secrets/kubernetes.io/serviceaccount/token";

const initConfig = async () => {
    let config;
    if (process.env.NODE_ENV === 'development') {
        config = process.env;
    } else {
        switch (vaultAuthentication) {
            case "KUBERNETES":
                config = await kubernetesAuth()
                break;
            case "APPROLE":
                config = await approleAuth()
                break;
            default:
                config = await approleAuth()
        }
    }
    return {
        NODE_ENV: process.env.NODE_ENV || 'development',
        PORT: config.PORT || 4000,
        CORS_ALLOWED_ORIGINS: config.CORS_ALLOWED_ORIGINS || 'http://localhost:3000, http://127.0.0.1:3000, https://studio.apollographql.com',
        CORS_ALLOW_CREDENTIALS: process.env.CORS_ALLOW_CREDENTIALS || true
    };
};

const approleAuth = async () => {
    const result = await vault.approleLogin({
        role_id: roleId,
        secret_id: secretId,
    });

    vault.token = result.auth.client_token;

    const { data } = await vault.read(`secret/apollo-gateway/production`);
    return data
}

const kubernetesAuth = async () => {
    const buffer = fs.readFileSync(serviceAccountToken);
    const jwt = buffer.toString();
    const result = await vault.kubernetesLogin({
        role: kubernetesRole,
        jwt: jwt
    });

    vault.token = result.auth.client_token;

    const { data } = await vault.read(`secret/data/apollo-gateway/production`);
    return data
}

module.exports.initConfig = initConfig;