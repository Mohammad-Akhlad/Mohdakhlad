# MultiModule Logging Project

## Project Description

This project is a Spring Boot Maven application in which we are providing the GET and 
POST operation


## Table of Contents


- [Overview](#Overview)
- [Functionality](#Functionality)
- [Preconditions](#Preconditions)
- [Initial -Setup](#Initial-Setup)
- [Implementation ](#Implementation)
- [Logging](#Logging)


## Overview

Welcome to the Multi-Module Logging Project for GET and POST Methods!

This project aims to provide a comprehensive solution for logging activities related to GET and POST methods in a multi-module environment. Logging plays a crucial role in understanding system behavior, diagnosing issues, and monitoring the flow of data in applications.

By implementing this project, you can easily integrate logging functionality into your multi-module project, specifically targeting the GET and POST methods. It allows you to capture and record relevant information, such as request details, response data, timestamps, and any custom parameters you choose to include.



## Functionality


Key Features
------------
- Seamless integration: The logging functionality seamlessly integrates into your existing multi-module project, minimizing any code modifications required.
- Granular control: You can selectively enable or disable logging for specific modules, GET or POST methods, or even individual endpoints, providing flexibility based on your project's requirements.
- Customizable logging format: Tailor the logging output to suit your needs by defining the desired format, including the level of detail, log file location, and additional metadata.
- Error tracking: Identify and track errors effectively by logging exceptions, error codes, and stack traces, enabling faster troubleshooting and debugging.
- Performance monitoring: Gain insights into the performance of your GET and POST methods by logging execution times, resource utilization, and other relevant metrics.


## Preconditions

Before getting started with the logging project, ensure that you have the following prerequisites in place:
- Your multimodule project is set up and functional.
- The necessary logging libraries or frameworks (e.g., log4j, Winston) are installed and configured.


## Initial-Setup


To incorporate the logging functionality for GET and POST methods into your multi-module project, follow these steps:
1. Install the required logging library by including the appropriate dependency in your project's build file (e.g., Maven, Gradle).
2. Configure the logging framework with the desired settings, such as log level, log format, and output destination.
3. Identify the modules, endpoints, or specific GET and POST methods where you want to enable logging.
4. Integrate the logging code within the relevant parts of your project, ensuring proper handling of exceptions and capturing essential information.
5. Test the logging functionality by performing GET and POST requests and verifying that the logs are generated as expected.
6. Fine-tune the logging configuration and output format based on your project's specific requirements.


## Implementation

Once the logging functionality is implemented in your multi-module project, you can leverage it in the following ways:
- Monitor and analyze the flow of data through GET and POST methods to gain insights into system behavior and identify potential issues.
- Use the logged information for auditing purposes, compliance requirements, or regulatory standards.
- Debug and troubleshoot problems by reviewing the logs, including error messages, stack traces, and relevant context.
- Optimize the performance of your GET and POST methods based on the logged execution times and resource utilization metrics.


## Logging

The logging includes:

- It captures the details about the requests and responses.
- Requests details include HTTP method, endpoint URL,query parameters, and headers.
- Response details includes HTTP status codes, response payloads, and response headers.
- Timestamps are recorded for each request and response.
- Custom parameters can be added for additional contextual information.
- Logging helps track request flow, monitor responses, and analyze project performance.

## EC2 Instance Link
This application is present in the EC2 instance to access run below link

     http://ec2-13-235-81-85.ap-south-1.compute.amazonaws.com:8080/customers/get

# Configuration


## Mysql Details

User: Mohdakhlad

Password: "Akhlad@123"

Database Name: jdbc_video

## Application setting about EC2 instances 

EC2 name : AWS-Instances

EC2 address: ec2-13-235-81-85.ap-south-1.compute.amazonaws.com

Public IPV4 address: 13.235.81.85







## Application setting about RDS instances

Instance Identifier: Akhlad-RDS

Username: Akhlad123

Password: Akhlad123

Database Name: Akhlad123

RDS Endpoint : akhlad-rds.cvc0llveztzp.ap-south-1.rds.amazonaws.com

Port No. 3306


## Two Entity Tables

curl -X POST -H "Content-Type: application/json" -d '{"name":"Mahim","city":"Allahabad","customer1":{"age":45}}' 
http://localhost:8080/customers/post

curl -X GET http://localhost:8080/customers/get

curl -X PUT -H "Content-Type: application/json" -d '{"name":"Mahi","city":"Lucknow","customer1":{"age":30}}'
http://localhost:8080/customers/update/21

curl -X GET http://localhost:8080/customers/get/21



## Base 64

curl -X POST -H "Content-Type: application/json" -d '{"username":"Amir", "password":"mohd1234"}'
http://localhost:8080/customers/post2

curl -X GET -H "Content-Type: application/json" http://localhost:8080/customers/get2

curl -X POST -H "Content-Type: application/json" -d '{"username":"Amir", "password":"mohd1234"}'
http://ec2-13-235-81-85.ap-south-1.compute.amazonaws.com:8080/customers/post2

curl -X GET http://ec2-13-235-81-85.ap-south-1.compute.amazonaws.com:8080/customers/get2

curl -X PUT -H "Content-Type: application/json" -d '{"username":"Amir", "password":"mohd1234"}'  
http://localhost:8080/customers/update/21




## Docker with local host

curl -X POST -H "Content-Type: application/json" -d '{"username":"Amir", "password":"mohd1234"}' 
http://localhost:8080/customers/post2

curl -X GET -H "Content-Type: application/json" http://localhost:8080/customers/get2



## Docker with EC2 instance

docker run -p 8080:8080 --name Container2 mohdakhlad/mohd:first

curl -X POST -H "Content-Type: application/json" -d '{"username":"Aero", "password":"mohd1234"}'
http://ec2-13-235-81-85.ap-south-1.compute.amazonaws.com:8080/customers/post2

curl -X GET -H "Content-Type: application/json" http://ec2-13-235-81-85.ap-south-1.compute.amazonaws.com:8080/customers/get2


## Backward Compatibility with two API  in local and Docker too

curl -X POST -H "Content-Type: application/json" -d '{
"name": "Maria",
"city": "Allahabad",
"dateOfBirth": "1999-03-17",
"phoneNumber": "1254567890",
"customer1": {
"age": 45
}
}' http://localhost:8080/customers/post




curl -X POST -H "Content-Type: application/json" -d '{
"name": "Maria",
"city": "Allahabad",
"customer1": {
"age": 45
}
}' http://localhost:8080/customers/post






## Backward Compatibility with Docker EC2 instance

curl -X POST -H "Content-Type: application/json" -d '{
"name": "Aishwarya",
"city": "Allahabad",
"dateOfBirth": "1998-03-16",
"phoneNumber": "1254567690",
"customer1": {
"age": 15 } }' http://ec2-13-235-81-85.ap-south-1.compute.amazonaws.com:8080/customers/post




curl -X POST -H "Content-Type: application/json" -d '{
"name": "Aishwarya",
"city": "Allahabad",
"customer1": {
"age": 15 } }' http://ec2-13-235-81-85.ap-south-1.compute.amazonaws.com:8080/customers/post


curl -X GET http://ec2-13-235-81-85.ap-south-1.compute.amazonaws.com:8080/customers/get




## Phone_No validation in local  as well as docker (local host)


curl -X POST -H "Content-Type: application/json" -d '{
"name": "Mina","city": "Mathura","dateOfBirth": "1990-06-10","phoneNumber": "7587955679","customer1": {"age": 56 }
}'http://localhost:8080/customers/post


curl -X PUT -H "Content-Type: application/json" -d '{"name": "Hiraa","city": "Mathura","dateOfBirth": "1990-06-10","phoneNumber": "6569767569","customer1": {"age": 45 }
}' http://localhost:8080/customers/update/95

curl -X GET 
http://localhost:8080/customers/get/95

curl -X GET 
http://localhost:8080/customers/get


## Phone_No validation in Docker using EC2 instance


curl -X POST -H "Content-Type: application/json" -d '{"name": "Aisha","city": "Allahabad","dateOfBirth": "1998-03-16","phoneNumber": "9254567690","customer1": {"age": 15 }
}' http://ec2-13-235-81-85.ap-south-1.compute.amazonaws.com:8080/customers/post

curl -X PUT -H "Content-Type: application/json" -d '{"name": "Hari","city": "Mathura","dateOfBirth": "1990-06-10","phoneNumber": "7669767569","customer1": {"age": 45 }
}' http://ec2-13-235-81-85.ap-south-1.compute.amazonaws.com:8080/customers/update/95

curl -X GET http://ec2-13-235-81-85.ap-south-1.compute.amazonaws.com:8080/customers/get/95

curl -X GET http://ec2-13-235-81-85.ap-south-1.compute.amazonaws.com:8080/customers/get



## S3 bucket with dev 

aws.accessKey=AKIAQLSL4MY3G2MSCQXB

aws.secretKey=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

aws.region= ap-south-1

aws.s3.bucketName=my-dev-bucket1


## S3 bucket with prod

aws.accessKey=AKIAQLSL4MY3G2MSCQXB

aws.secretKey=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

aws.region= ap-south-1

aws.s3.bucketName=my-prod-bucket1














