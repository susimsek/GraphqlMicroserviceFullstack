export const authServerUrl = window._env_.AUTH_SERVER_URL;
export const authorizeApiUri = `${authServerUrl}/oauth2/authorize`
export const tokenApiUri = `${authServerUrl}/oauth2/token`
export const graphqlApiUrl = `${process.env.REACT_APP_GRAPHQL_API_URL}`;