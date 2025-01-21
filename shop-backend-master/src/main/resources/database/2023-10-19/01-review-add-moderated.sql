--liquibase formatted sql
--changeset kpawelec:9
alter table review add moderated boolean default false;
