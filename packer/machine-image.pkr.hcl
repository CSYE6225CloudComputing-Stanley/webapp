packer {
  required_plugins {
    amazon = {
      version = ">= 1.2.8"
      source  = "github.com/hashicorp/amazon"
    }
  }
}

source "amazon-ebs" "ubuntu" {
  ami_name      = var.ami_name
  instance_type = var.aws_instance_type
  region        = var.aws_region
  profile       = var.aws_profile
  source_ami_filter {
    filters = {
      name                = var.ami_filter_name
      root-device-type    = var.ami_root_device_type
      virtualization-type = var.ami_virtualization_type
    }
    most_recent = true
    owners      = [var.ami_filter_owners]
  }

  ssh_username = var.ssh_username
  ami_users    = [var.demo_account_id]
  ami_groups   = []
}

build {
  sources = ["source.amazon-ebs.ubuntu"]

  provisioner "file" {
    source      = "${path.root}/artifacts/webapp.jar"
    destination = "/tmp/webapp.jar"
    generated   = true
  }

  provisioner "file" {
    source      = "${path.root}/../scripts/setup-system.sh"
    destination = "/tmp/setup-system.sh"
  }

  provisioner "file" {
    source      = "${path.root}/../scripts/dependency.sh"
    destination = "/tmp/dependency.sh"
  }

  provisioner "file" {
    source      = "${path.root}/../scripts/db_config.sh"
    destination = "/tmp/db_config.sh"
  }

  provisioner "file" {
    source      = "${path.root}/../scripts/app_config.sh"
    destination = "/tmp/app_config.sh"
  }

  provisioner "file" {
    source      = "${path.root}/../scripts/runner.sh"
    destination = "/tmp/runner.sh"
  }

  provisioner "shell" {
    environment_vars = [
      "Owner=csye6225",
      "Group=csye6225",
      "DB_NAME=${var.DB_NAME}",
      "DB_USERNAME=${var.DB_USERNAME}",
      "DB_PASSWORD=${var.DB_PASSWORD}",
    ]
    inline = [
      "set -e",
      "chmod +x /tmp/*.sh",
      "sudo -E /tmp/setup-system.sh",
      "sudo /tmp/dependency.sh",
      "sudo -E /tmp/db_config.sh",
      "sudo -E /tmp/app_config.sh",
      "sudo -E /tmp/runner.sh"
    ]
  }
}