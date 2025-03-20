#!/bin/bash

# echo "Saving environment variables to /etc/environment....."
# echo "DB_NAME=${DB_NAME}" | sudo tee -a /etc/environment
# echo "DB_USERNAME=${DB_USERNAME}" | sudo tee -a /etc/environment
# echo "DB_PASSWORD=${DB_PASSWORD}" | sudo tee -a /etc/environment

echo "Creating empty /etc/environment file..."
sudo touch /etc/environment


echo "Creating systemd service file....."
cat <<EOF | sudo tee /etc/systemd/system/springboot.service

[Unit]
Description=Web Application
After=network.target

[Service]
User=${Owner}
Group=${Group}
EnvironmentFile=/etc/environment
ExecStart=/usr/bin/java -jar /opt/csye6225/webapp.jar
Restart=always

[Install]
WantedBy=multi-user.target
EOF

echo "Enabling and starting systemd service..."
sudo systemctl daemon-reload
sudo systemctl enable springboot.service