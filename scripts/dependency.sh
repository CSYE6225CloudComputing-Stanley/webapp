#!/bin/bash


if ! command -v java; then
    echo "Installing jdk....."
    sudo apt install openjdk-21-jdk -y
fi
