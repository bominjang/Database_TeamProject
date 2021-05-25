CREATE DATABASE DB2021Team03;
use DB2021Team03;

CREATE TABLE DB2021_User(
                            ID int auto_increment not null,
                            nickname varchar(20) not null,
                            password varchar(20) not null,
                            name varchar(20) not null,
                            birth date,
                            phone varchar(11),

                            join_time timestamp not null,
                            privacy_fg char(1) not null,
                            admin_fg char(1) not null,
                            delete_fg char(1) not null,
                            delete_time timestamp,

                            primary key(ID)
);