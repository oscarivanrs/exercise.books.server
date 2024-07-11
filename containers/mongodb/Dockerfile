FROM mongo:latest

RUN mkdir -p /data/db
RUN chown -R mongodb:mongodb /data/db

COPY init-mongo.js /docker-entrypoint-initdb.d/
