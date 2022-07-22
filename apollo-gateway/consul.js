const aclToken = process.env.CONSUL_ACL_TOKEN || '';
const consulHost = process.env.CONSUL_HOST || 'localhost';
const consulPort = process.env.CONSUL_PORT || 8500;
const { v4: uuidv4 } = require('uuid');
const ip = require("ip");
let consulClient;
let serviceId;
initConsul = () => {
    const Consul = require('consul');
    consulClient =  new Consul({
        host: consulHost,
        port: consulPort,
        secure: false,
        promisify: true,
        defaults: {
            token: aclToken
        }
    });
}

const registerService = (config) => {
    const ipAddress = ip.address()
    const port = parseInt(config.PORT)
    const name = "apollo-gateway"
    serviceId = `${name}:${uuidv4()}`;

    let serviceDefinition = {
        id: serviceId,
        name: name,
        address: ipAddress,
        port: port,
        check: {
            http: `http://${ipAddress}:${port}/.well-known/apollo/server-health`,
            interval: '10s',
            timeout: '5s'
        }
    }

    consulClient.agent.service.register(serviceDefinition, (err) => {
        if (err) throw err;
    });
}

const unregisterService = async (err) => {
    return new Promise(function(resolve, reject) {
        if (err) reject(err);
        consulClient.agent.service.deregister(serviceId, () => {
            resolve(serviceId)
        });
    });
};

const getServiceList = (callback) => {
    consulClient.agent.service.list(callback);
}

module.exports.initConsul = initConsul;
module.exports.registerService = registerService;
module.exports.unregisterService = unregisterService;
module.exports.getServiceList = getServiceList;

