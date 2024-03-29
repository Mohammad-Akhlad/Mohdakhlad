CREATE TABLE customer (
  id SERIAL NOT NULL,
  city VARCHAR(255) DEFAULT NULL,
  created_on TIMESTAMP(6) DEFAULT NULL,
  "date_of_birth" DATE DEFAULT NULL,
  "image_url" VARCHAR(255) DEFAULT NULL,
  last_modified TIMESTAMP(6) DEFAULT NULL,
  name VARCHAR(255) DEFAULT NULL,
  password VARCHAR(255) DEFAULT NULL,
  "phone_no" VARCHAR(255) DEFAULT NULL,
  email VARCHAR(255) DEFAULT NULL, -- Add the "email" column definition here
  username VARCHAR(255) DEFAULT NULL
);


CREATE TABLE customer1 (
  id SERIAL NOT Null,
  age INTEGER DEFAULT NULL,
  created_on TIMESTAMP DEFAULT NULL,
  last_modified TIMESTAMP DEFAULT NULL,
  customer_id BIGINT DEFAULT NULL

);

CREATE TABLE customer2 (
  id SERIAL NOT Null,
  created_on TIMESTAMP DEFAULT NULL,
  encrypted_password VARCHAR(255) DEFAULT NULL,
  last_modified TIMESTAMP DEFAULT NULL,
  username VARCHAR(255) DEFAULT NULL,
  customer_id BIGINT DEFAULT NULL

);
