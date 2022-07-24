const { ApolloServer } = require('apollo-server');
const { ApolloGateway, RemoteGraphQLDataSource, IntrospectAndCompose} = require('@apollo/gateway');


const {initConfig} = require("./config");
const {initConsul, registerService, getServiceList, unregisterService} = require("./consul");


initConfig().then(config => {
    let servers = [];
    if (process.env.NODE_ENV === 'development') {
        servers = [
            { name: 'product', url: 'http://localhost:8081/graphql' },
            { name: 'review', url: 'http://localhost:8082/graphql' },
        ]
        startGateway(config, servers)
    } else {
        initConsul(config)
        getServiceList((err, services) => {
            if (err) throw err;
            servers = Object.keys(services)
                .filter(id => (services[id].Tags.indexOf('graphql') > -1))
                .map(id => {
                    return { name: services[id].Service.replaceAll('-service', ''), url: `http://${services[id].Address}:${services[id].Port}/graphql` }
                });
            startGateway(config, servers)
        });
    }
}).catch(err => {
    console.log(err);
});

class AuthenticatedDataSource extends RemoteGraphQLDataSource {
    willSendRequest({request, context}) {
        request.http.headers.set('Authorization', context.authHeaderValue);
    }
}

const startGateway = (config, servers) => {
    const corsOptions = {
        origin: config.CORS_ALLOWED_ORIGINS.split(", "),
        credentials: config.CORS_ALLOW_CREDENTIALS
    }

    const gateway = new ApolloGateway({
        supergraphSdl: new IntrospectAndCompose({
            subgraphs: servers,
        }),
        buildService({name, url}) {
            return new AuthenticatedDataSource({url});
        },
    });

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
                    if (process.env.NODE_ENV !== 'development') {
                        registerService(config)
                    }
                    return {
                        async serverWillStop() {
                            if (process.env.NODE_ENV !== 'development') {
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
        console.log(`🚀 Server ready at ${url}`);
    });
}
