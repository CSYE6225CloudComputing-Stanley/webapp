# Web Application Infrastructure

This project implements an CI/CD pipeline using GitHub Actions to build Amazon Machine Images with Packer, and provision scalable AWS infrastructure with Terraform for deploying a backend application.

## Technologies Used

- **Java 21**
- **Spring Boot 3.x**
- **Spring Web**
- **Spring Data JPA**
- **AWS RDS MySQL**
- **AWS S3**
- **AWS**
- **Packer**
- **Terraform**
- **GitHub Actions**

---

## High Level Architecture

<img src="https://i.imgur.com/OWrKs4i.png" alt="High Level Architecture 1" width="1000" height="800"/>

<img src="https://i.imgur.com/EcptUQW.png" alt="High Level Architecture 2" width="1000" height="800"/>

---

## GitHub Actions Workflow

### Jobs Overview

#### 1. `packer fmt & validate`
- **Trigger**: PR opened
- **Steps**: Checkout repo → Install Packer → Initialize → Format Check → Validate Templates

#### 2. `webapp unit & integration test`
- **Trigger**: PR opened
- **Steps**: Checkout repo → Setup Java 21 → Start MySQL → Run Unit & Integration Tests

#### 3. `build artifact`
- **Trigger**: PR merged
- **Steps**: Build `webapp.jar`

#### 4. `packer build AMI`
- **Trigger**: After `build artifact`
- **Steps**: Configure AWS Credentials → Install Packer → Build New AMI

#### 5. `cd-process`
- **Trigger**: After `packer build AMI`
- **Steps**: Retrieve Latest AMI ID and ASG → Update Launch Template → Start Instance Refresh

If any of the above steps fail, the pull request will be blocked and the process will stop.

---

## AMI Creation with Packer

### Source Configuration
- Defines an **Amazon EBS** source that uses an Ubuntu-based AMI as the base image.

### Build Configuration
- Moves the application JAR file and various shell scripts into the instance.
- Runs a sequence of shell commands to:
  - Install dependencies.
  - Configure the application environment and systemd service.

### Systemd Service Setup
- Creates a `springboot.service` systemd unit file to manage the application.
- Configures the service to:
  - Start after the network and CloudWatch Agent are ready.

---

## AWS Infrastructure

For detailed infrastructure setup, see the [tf-aws-infra](https://github.com/godUsoop/tf-aws-infra) repository.

---

## Architecture Components

### Amazon Route 53
- Manages DNS for the application domain (`demo.hahahaha.it.com`).
- Alias record points to the Application Load Balancer public DNS.

---

### Security Groups

#### 1. Web Application Security Group

**Purpose**: Controls access to the EC2 instances hosting the web application.

- **Inbound Rules**:
  - Allow **SSH** (port **22**) access only from your IP.
  - Allow **HTTP** traffic (port **8080**) from the **Load Balancer Security Group**.

- **Outbound Rules**:
  - Allow **all outbound traffic** to any destination (`0.0.0.0/0`).

---

#### 2. Database Security Group

**Purpose**: Controls access to the Amazon RDS database instance.

- **Inbound Rules**:
  - Allow **MySQL** connections (port **3306**) from the **Web Application Security Group**.

- **Outbound Rules**:
  - Allow **all outbound traffic** to any destination (`0.0.0.0/0`).

---

#### 3. Load Balancer Security Group

**Purpose**: Controls access to the Application Load Balancer.

- **Inbound Rules**:
  - Allow **HTTP** (port **80**) traffic from anywhere (`0.0.0.0/0`).
  - Allow **HTTPS** (port **443**) traffic from anywhere (`0.0.0.0/0`).

- **Outbound Rules**:
  - Allow **all outbound traffic** to any destination (`0.0.0.0/0`).

---

### VPC & Internet Gateway (IGW)
- Connects external internet traffic into the VPC's public subnets.
- Used by clients accessing the ALB and EC2 instances reaching S3, RDS or CloudWatch.
- EC2 instances and the load balancer are deployed in public subnets.
- RDS instance is deployed in private subnets.

<img src="https://i.imgur.com/8mFGCwO.png" alt="High Level Architecture 1" width="1000" height="500"/>

### Application Load Balancer (ALB)
- Public-facing load balancer.
- Listens on **port 443 (HTTPS)**.
- Forwards requests to EC2 instances over **TCP 8080**.

<img src="https://i.imgur.com/P0bqpS9.png" alt="High Level Architecture 1" width="1000" height="500"/>

### Auto Scaling Group (ASG)
- Dynamically manages EC2 instance counts based on CPU usage.
- Configuration:
  - **Min**: 3 instances
  - **Max**: 5 instances
  - **Desired**: 3 instances
- Scaling Policies:
  - **Scale Up**: Add 1 instance if CPU utilization > 15% over two periods.
  - **Scale Down**: Remove 1 instance if CPU utilization < 10% over two periods.
- Supports **Instance Refresh** during continuous deployment.

<img src="https://i.imgur.com/NatMGih.png" alt="High Level Architecture 1" width="1000" height="500"/>

Once the CPU usage is above or below the threshold, the Auto Scaling Group will automatically launch a new instance or terminate an existing one.


<img src="https://i.imgur.com/95bpqSS.png" alt="High Level Architecture 1" width="1000" height="500"/>

<img src="https://i.imgur.com/v33l6bl.png" alt="High Level Architecture 1" width="1000" height="500"/>

<img src="https://i.imgur.com/9ZfXScY.png" alt="High Level Architecture 1" width="1000" height="500"/>




### EC2 Instances
- Each instance:
  - The application will start via **systemD** once the instance is launched.
  - Runs a **Spring Boot** application on **port 8080**.
  - Loads environment variables (DB credentials, S3 bucket) via **user_data** script.
  - Connects securely to **RDS** over Private IP inside VPC.
  - Accesses **S3** via public endpoint through IGW.
  - Sends **StatsD** metrics (UDP 8125) to **CloudWatch Agent**.

### Spring Boot Application
- Exposes healthCheck and file upload RESTful APIs.
- Persists data to **MySQL RDS**.
- Uploads/downloads/delete files to/from **Amazon S3**.
- Sends custom metrics and logs to CloudWatch via **StatsD** and the **CloudWatch agent**.

### Amazon RDS (MySQL)
- Managed relational database hosted in **private subnets**.
- Only accessible from within the VPC via private IP networking.

### Amazon S3
- Object storage for application files.
- Accessed securely via public endpoint through Internet Gateway.

### KMS
- **EC2 KMS Key**
  - Encrypts EC2 EBS volume

- **RDS KMS Key**
  - Encrypts RDS resources.

- **S3 KMS Key**
  - Encrypts S3 bucket objects.

- **Secrets Manager KMS Key**
  - Encrypts database passwords in Secrets Manager.

### Amazon CloudWatch
- Collects and aggregates logs and custom metrics from EC2 instances.
- Metrics and application logs are sent to CloudWatch.

<img src="https://i.imgur.com/Fvzkljm.png" alt="High Level Architecture 1" width="1000" height="500"/>

<img src="https://i.imgur.com/dNd9ek6.png" alt="High Level Architecture 1" width="1000" height="500"/>

---