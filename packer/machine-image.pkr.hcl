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
  ami_groups   = []
}

build {
  sources = ["source.amazon-ebs.ubuntu"]

  provisioner "file" {
    source      = "${path.root}/../scripts/setup-system.sh"
    destination = "/tmp/setup-system.sh"
  }

  provisioner "shell" {
    inline = [
      "chmod +x /tmp/setup-system.sh",
      "sudo /tmp/setup-system.sh"
    ]
  }
}