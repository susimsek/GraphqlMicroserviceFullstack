# build
FROM node:18-alpine as build
WORKDIR /app
COPY package*.json ./
ENV PATH="./node_modules/.bin:$PATH"
RUN npm install

COPY . ./
RUN npm run build

# production
FROM nginx:stable-alpine
COPY --from=build /app/build  /usr/share/nginx/html

COPY nginx/default.conf /etc/nginx/conf.d/default.conf
COPY nginx/gzip.conf /etc/nginx/conf.d/gzip.conf

WORKDIR /usr/share/nginx/html
COPY ./env.sh .
COPY .env .
RUN chmod +x env.sh

ENV PORT=80
CMD sed -i -e 's/$PORT/'"$PORT"'/g' /etc/nginx/conf.d/default.conf && /bin/sh -c /usr/share/nginx/html/env.sh && nginx -g 'daemon off;'