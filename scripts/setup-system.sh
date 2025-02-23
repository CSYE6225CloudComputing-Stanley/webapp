#!/bin/bash

if ! command -v unzip; then
    echo "Unzip is not installed. Installing..."
    sudo apt install -y unzip
fi

if ! command -v mysql; then
    echo "Installing MySQL Server....."
    sudo apt install mysql-server -y
fi

echo "Updating packages....."
sudo apt update

echo "Upgrading installed packages....."
sudo apt upgrade -y