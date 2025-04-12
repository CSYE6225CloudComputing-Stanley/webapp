packer {
  required_plugins {
    amazon = {
      version = ">= 1.2.8"
      source  = "github.com/hashicorp/amazon"
    }
    # googlecompute = {
    #   version = ">= 0.1.0"
    #   source  = "github.com/hashicorp/googlecompute"
    # }
  }
}

source "amazon-ebs" "ubuntu" {
  ami_name      = "webapp-{{timestamp}}"
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

  tags = {
    Name = "webapp"
  }
}

# source "googlecompute" "ubuntu" {
#   project_id              = var.gcp_project_id
#   source_image            = var.gcp_source_image
#   source_image_project_id = var.gcp_source_image_project_id
#   service_account_email   = var.gcp_service_account_email
#   machine_type            = var.gcp_machine_type
#   zone                    = var.gcp_zone
#   image_name              = var.gcp_image_name
#   ssh_username            = var.gcp_ssh_username
# }

build {
  # sources = ["source.amazon-ebs.ubuntu", "source.googlecompute.ubuntu"]
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
      # "DB_NAME=${var.DB_NAME}",
      # "DB_USERNAME=${var.DB_USERNAME}",
      # "DB_PASSWORD=${var.DB_PASSWORD}",
    ]
    inline = [
      "set -e",
      "chmod +x /tmp/*.sh",
      "sudo -E /tmp/setup-system.sh",
      "sudo /tmp/dependency.sh",
      # "sudo -E /tmp/db_config.sh",
      "sudo -E /tmp/app_config.sh",
      "sudo -E /tmp/runner.sh"
    ]
  }

  # post-processor "shell-local" {
  #   inline = [
  #     "echo 'Granting GCP demo permission to use this image...'",
  #     "gcloud compute images add-iam-policy-binding ${var.gcp_image_name} --project=${var.gcp_dev_project_id} --member=serviceAccount:${var.gcp_demo_service_account} --role=roles/compute.imageUser"
  #   ]
  # }
}
