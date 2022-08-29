const { ApolloServer } = require('apollo-server');
const { ApolloGateway, RemoteGraphQLDataSource, IntrospectAndCompose} = require('@apollo/gateway');
const { readFileSync } = require('fs');


const {initConfig} = require("./config");
const {initConsul, registerService, getServiceList, unregisterService} = require("./consul");

initConfig().then(config => {
    if (config.CONSUL_ENABLED === 'true') {
        initConsul(config)
    }

    const apolloGatewayConfig = {
        buildService({name, url}) {
            return new AuthenticatedDataSource({url});
        }
    };

    const embeddedSchema = config.APOLLO_SCHEMA_CONFIG_EMBEDDED

    if (embeddedSchema === 'true'){
        const supergraph = config.SUPERGRAPH_PATH
        apolloGatewayConfig['supergraphSdl'] = readFileSync(supergraph).toString();
        console.log('Starting Apollo Gateway in local mode ...');
        console.log(`Using local: ${supergraph}`)
    } else {
        console.log('Starting Apollo Gateway in managed mode ...');
    }
    startGateway(config, apolloGatewayConfig, config.CONSUL_ENABLED)
}).catch(err => {
    console.log(err);
});

class AuthenticatedDataSource extends RemoteGraphQLDataSource {
    willSendRequest({request, context}) {
        request.http.headers.set('Authorization', context.authHeaderValue);
    }
}

const startGateway = (config, apolloGatewayConfig, consulEnabled) => {
    const corsOptions = {
        origin: config.CORS_ALLOWED_ORIGINS.split(", "),
        credentials: config.CORS_ALLOW_CREDENTIALS
    }

    const gateway = new ApolloGateway(apolloGatewayConfig);

    const server = new ApolloServer({
        gateway,
        cors: corsOptions,
        csrfPrevention: false,
        subscriptions: false,
        cache: "bounded",
        context: ({req}) => ({
            authHeaderValue: req.headers.authorization
        }),
        plugins: [
            {
                async serverWillStart() {
                    if (consulEnabled === 'true') {
                        registerService(config)
                    }
                    return {
                        async serverWillStop() {
                            if (consulEnabled === 'true') {
                                await unregisterService()
                            }
                        }
                    }
                }
            }
        ]
    });

    server.listen({
        port: config.PORT,
    }).then(({ url }) => {
        console.log(`🚀 Graph Router ready at ${url}`);
    });
}
