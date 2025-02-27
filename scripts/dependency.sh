#!/bin/bash

if ! command -v mysql; then
    echo "Installing MySQL Server....."
    sudo apt install mysql-server -y
fi


if ! command -v java; then
    echo "Installing jdk....."
    sudo apt install openjdk-21-jdk -y
fi
