#!/bin/bash

DB_NAME=$1
DB_USERNAME=$2
DB_PASSWORD=$3
GROUP_NAME=$4
GROUP_USER=$5
DEST_DIR=$6
ZIP_FILE=$7

if ! command -v unzip; then
    echo "Unzip is not installed. Installing..."
    sudo apt install -y unzip
fi

echo "Updating packages....."
sudo apt update

echo "Upgrading installed packages....."
sudo apt upgrade -y

echo "Installing MySQL Server....."
sudo apt install mysql-server -y

systemctl is-active --quiet mysql
if [ $? -eq 0 ]; then 
    echo "MySQL is running"
else 
    echo "MySQL is not running..... Starting MySQL"
    sudo systemctl start mysql
fi

echo "Creating MySQL database....."


sudo mysql -e "CREATE DATABASE ${DB_NAME}"
echo "Show Databases"
sudo mysql -e "SHOW DATABASES;"

sudo mysql -e "CREATE USER '${DB_USERNAME}'@'localhost' IDENTIFIED BY '${DB_PASSWORD}';"
sudo mysql -e "GRANT ALL PRIVILEGES ON \`${DB_NAME}\`.* TO '${DB_USERNAME}'@'localhost';"
sudo mysql -e "FLUSH PRIVILEGES;"

sudo groupadd ${GROUP_NAME}
if [ $? -eq 0 ]; then
    echo "Group ${GROUP_NAME} is created"
else
    echo "Failed to add group"
fi

sudo useradd -m -g ${GROUP_NAME} -s /bin/bash ${GROUP_USER}

if [ $? -eq 0 ]; then
    echo "User: ${GROUP_USER} is added to the group ${GROUP_NAME}"
else 
    echo "Failed to add ${GROUP_USER} to the group: ${GROUP_NAME}"
fi

echo "making file in ${DEST_DIR}"
sudo mkdir ${DEST_DIR}

echo "moving ${ZIP_FILE} to ${DEST_DIR}"
sudo mv $PWD/${ZIP_FILE} ${DEST_DIR}

echo "unzipping file in ${DEST_DIR}"
cd ${DEST_DIR}
sudo unzip ${ZIP_FILE}

echo "Change the ${DEST_DIR} ownership"
sudo chown -R root:${GROUP_NAME} ${DEST_DIR}


echo "Update the permissions of the folder and artifacts in the ${DEST_DIR}"
sudo chmod -R 775 ${DEST_DIR}