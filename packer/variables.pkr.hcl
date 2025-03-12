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

variable "gcp_project_id" {
  type        = string
  description = "GCP Project ID"
  default     = "cellular-line-452210-e4"
}


variable "gcp_source_image" {
  type        = string
  description = "Source image"
  default     = "ubuntu-2404-noble-amd64-v20250228"
}

variable "gcp_source_image_project_id" {
  type    = list(string)
  default = ["ubuntu-os-cloud"]
}

variable "gcp_service_account_email" {
  type        = string
  description = "service account email"
  default     = "packer-runner@cellular-line-452210-e4.iam.gserviceaccount.com"
}

variable "gcp_machine_type" {
  type        = string
  description = "GCP Machine Type"
  default     = "e2-medium"
}

variable "gcp_zone" {
  type        = string
  description = "GCP Zone"
  default     = "us-central1-a"
}

variable "gcp_image_name" {
  type        = string
  description = "Name of the GCP custom image"
  default     = "my-gcp-image"
}

variable "gcp_ssh_username" {
  type        = string
  description = "ssh username"
  default     = "ubuntu"
}


variable "gcp_dev_project_id" {
  type        = string
  description = "dev project id"
  default     = "cellular-line-452210-e4"
}

variable "gcp_demo_service_account" {
  type        = string
  description = "service account email"
  default     = "instance-launcher@gcp-demo-453420.iam.gserviceaccount.com"
}