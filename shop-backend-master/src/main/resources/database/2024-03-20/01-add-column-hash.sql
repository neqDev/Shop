--liquibase formatted sql
--changeset kpawelec:23
alter table users add hash varchar(120);
--changeset kpawelec:24
alter table users add hash_date datetime;

