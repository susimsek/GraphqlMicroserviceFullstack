mongo --eval "db.auth('$MONGO_INITDB_ROOT_USERNAME', '$MONGO_INITDB_ROOT_PASSWORD');
db = db.getSiblingDB('auth');
db.createUser({ user: 'auth-admin', pwd: 'iXCjXb7e2yjJbjRa', roles: [{ role: 'readWrite', db: 'auth' }] });
db = db.getSiblingDB('product');
db.createUser({ user: 'product-admin', pwd: 'iXCjXb7e2yjJbjRp', roles: [{ role: 'readWrite', db: 'product' }] });
db = db.getSiblingDB('review');
db.createUser({ user: 'review-admin', pwd: 'iXCjXb7e2yjJbjRr', roles: [{ role: 'readWrite', db: 'review' }] });"