#!/bin/bash

echo "make webapp directory....."
sudo mkdir -p /opt/csye6225

echo "move webapp artifact to /opt/csye6225/....."
sudo mv /tmp/webapp.jar /opt/csye6225/webapp.jar

echo "Change ownership of all files under /opt/csye6225....."
sudo chown -R ${Owner}:${Group} /opt/csye6225

echo "change csye6225 mode....."
sudo chmod 770 -R /opt/csye6225