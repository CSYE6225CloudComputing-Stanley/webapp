#!/bin/bash

echo "Creating MySQL database....."

sudo mysql -e "CREATE DATABASE ${DB_NAME}"
echo "Show Databases"
sudo mysql -e "SHOW DATABASES;"

sudo mysql -e "CREATE USER '${DB_USERNAME}'@'localhost' IDENTIFIED BY '${DB_PASSWORD}';"
sudo mysql -e "GRANT ALL PRIVILEGES ON \`${DB_NAME}\`.* TO '${DB_USERNAME}'@'localhost';"
sudo mysql -e "FLUSH PRIVILEGES;"