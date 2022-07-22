import {onError} from "@apollo/client/link/error";
import {ApolloClient, from, InMemoryCache} from "@apollo/client";
import {setContext} from "@apollo/client/link/context";
import {accessTokenKey} from "./constants/storage";
import {logout} from "./authcode/authcode";
import {graphqlApiUrl} from "./constants/url";

function createHttpLink() {
        const { HttpLink } = require('@apollo/client/link/http');
        return new HttpLink({
            uri: graphqlApiUrl
        });
}

const authLink = setContext(async (_, { headers }) => {
    const token = localStorage.getItem(accessTokenKey);
    return {
        headers: {
            ...headers,
            authorization: token ? `Bearer ${token}` : "",
        },
    };
});

export const createApolloClient = () => {
    const errorLink = onError(({ networkError, graphQLErrors, operation, forward }) => {
        if (graphQLErrors) {
            graphQLErrors.forEach(({ extensions: { code , serviceName }, message, path }) => {
                if (code === 'UNAUTHENTICATED') {
                    logout();
                    window.location.reload();
                }
                else {
                    console.error(
                        `[GraphQL error]: Message: ${message}, Service: ${serviceName}, Path: ${path[0]}`
                    )
                }
            });
        }
        if (networkError) {
            if (typeof window !== 'undefined' && !window.navigator.onLine) {
                alert('Sorry, your browser is offline.');
            } else {
                console.error(`[Network error]: ${networkError}`);
                alert('Some other network error occurred.');
            }
        }

        return forward(operation)
    });
    return new ApolloClient({
        ssrMode: typeof window === 'undefined',
        link: from([errorLink, authLink, createHttpLink()]),
        cache: new InMemoryCache()
    });
}