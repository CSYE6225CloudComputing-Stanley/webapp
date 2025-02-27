variable "aws_region" {
  type    = string
  default = "us-east-1"
}

variable "aws_profile" {
  type    = string
  default = "dev"
}

variable "ami_name" {
  type    = string
  default = "ubuntu-24-04"
}

variable "aws_instance_type" {
  type    = string
  default = "t3.micro"
}

variable "ami_filter_name" {
  type    = string
  default = "ubuntu/images/hvm-ssd-gp3/ubuntu-noble-24.04-amd64-server-*"
}

variable "ami_root_device_type" {
  type    = string
  default = "ebs"
}

variable "ami_virtualization_type" {
  type    = string
  default = "hvm"
}

variable "ami_filter_owners" {
  type    = string
  default = "099720109477"
}

variable "ssh_username" {
  type    = string
  default = "ubuntu"
}

variable "DB_NAME" {
  type    = string
  default = "dummy_name"
}

variable "DB_USERNAME" {
  type    = string
  default = "dummy_username"
}

variable "DB_PASSWORD" {
  type    = string
  default = "dummy_password"
}

variable "demo_account_id" {
  type    = string
  default = "038462758666"
}