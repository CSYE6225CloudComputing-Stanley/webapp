#!/bin/bash

if ! command -v unzip; then
    echo "Unzip is not installed. Installing..."
    sudo apt install -y unzip
fi

echo "Installing amazon cloudwatch-agent....."
sudo wget https://s3.amazonaws.com/amazoncloudwatch-agent/ubuntu/amd64/latest/amazon-cloudwatch-agent.deb
sudo dpkg -i amazon-cloudwatch-agent.deb

echo "Installing jq....."
sudo apt install -y jq

echo "Installing aws cli....."
sudo curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install

echo "Updating packages....."
sudo apt update

echo "Upgrading installed packages....."
sudo apt upgrade -y

echo "create a group....."
sudo groupadd ${Group}

echo "add a user to the group....."
sudo useradd -r -s /usr/sbin/nologin -g ${Group} ${Owner}