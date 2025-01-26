--liquibase formatted sql

--changeset AVVoy:2
ALTER TABLE products
    ALTER COLUMN imageurl SET DATA TYPE text;