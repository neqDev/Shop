--liquibase formatted sql
--changeset kpawelec:2
alter table product add image varchar(128) after currency;
