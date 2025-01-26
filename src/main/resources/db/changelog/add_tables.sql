--liquibase formatted sql

--changeset AVVoy:1
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       login VARCHAR(255),
                       password VARCHAR(255)

);

CREATE TABLE Products (
                          id UUID PRIMARY KEY ,
                          name VARCHAR(255),
                          imageUrl VARCHAR(250),
                          user_id UUID REFERENCES users(id)

);