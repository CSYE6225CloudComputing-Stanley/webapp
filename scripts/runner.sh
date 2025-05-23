#!/bin/bash


echo "Creating empty /etc/environment file..."
sudo touch /etc/environment


echo "Creating systemd service file....."
cat <<EOF | sudo tee /etc/systemd/system/springboot.service

[Unit]
Description=Web Application
After=network.target amazon-cloudwatch-agent.service
Requires=amazon-cloudwatch-agent.service

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