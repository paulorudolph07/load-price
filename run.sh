#!/bin/bash
echo "Building mysql-standalone container..."
docker run --name mysql-standalone -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=loadpricedb -e MYSQL_USER=loadprice -e MYSQL_PASSWORD=loadprice123 -d mysql:5.6

echo "Waiting for mysql container"
sleep 9

echo "Building load-price image..."
docker build . -t load-price

echo "Building load-price container: port 8080"
docker run -p 8080:8080 --name load-price --link mysql-standalone:mysql -d load-price

echo "Finished!"
