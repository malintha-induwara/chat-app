create database if not exists chatapp;
use chatapp;

create table user(
    username varchar(15) primary key,
    password varchar(15) not null,
    img varchar(200) not null);
