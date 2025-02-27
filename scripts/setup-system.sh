#!/bin/bash

if ! command -v unzip; then
    echo "Unzip is not installed. Installing..."
    sudo apt install -y unzip
fi

echo "Updating packages....."
sudo apt update

echo "Upgrading installed packages....."
sudo apt upgrade -y

echo "create a group....."
sudo groupadd ${Group}

echo "add a user to the group....."
sudo useradd -r -s /usr/sbin/nologin -g ${Group} ${Owner}