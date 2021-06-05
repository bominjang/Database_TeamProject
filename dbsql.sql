CREATE DATABASE DB2021Team03;
use DB2021Team03;

##User
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

CREATE INDEX Unick ON DB2021_User(nickname);


## Director
CREATE TABLE DB2021_Director(
ID int auto_increment not null,
name varchar(20) not null,
country varchar(20) not null,
birth date,

primary key(ID, name)
);

CREATE INDEX Dname ON DB2021_Director(name);


## Movie(director와 1:N)
CREATE TABLE DB2021_MOVIE(
ID int auto_increment not null,
title varchar(20) not null,
genre varchar(20) not null,
country varchar(20) not null,
running_time int,
opening_date date,
director varchar(20) not null,
plot varchar(3000),
rating float,
age int not null,

primary key(ID),
foreign key(director) references DB2021_Director(name) on delete cascade on update cascade
);

CREATE INDEX Mtitle ON DB2021_MOVIE(title);



## Director_Prize(1:Many)
CREATE TABLE DB2021_Director_Prize(
ID int auto_increment not null,
prize varchar(100) not null,
director varchar(20) not null,
movie varchar(20) not null,

primary key(ID),
foreign key(director) references DB2021_Director(name) on delete cascade on update cascade,
foreign key(movie) references DB2021_MOVIE(title) on delete cascade on update cascade
);

CREATE INDEX DPidx ON DB2021_Director_Prize(director);



## Actor
CREATE TABLE DB2021_Actor(
ID int auto_increment not null,
name varchar(20) not null,
country varchar(20) not null,
birth date,

primary key(ID, name)
);

CREATE INDEX Aname ON DB2021_Actor(name);


## Actor_Movie(Many to Many)
CREATE TABLE DB2021_Actor_Movie(
actor varchar(20) not null,
movie varchar(20) not null,

primary key(actor, movie),

foreign key(actor) references DB2021_Actor(name) on delete cascade on update cascade,
foreign key(movie) references DB2021_Movie(title) on delete cascade on update cascade
);


## Actor_Prize
CREATE TABLE DB2021_Actor_Prize(
ID int auto_increment not null,
prize varchar(100) not null,
actor varchar(20) not null,
movie varchar(20) not null,

primary key(ID),
foreign key(actor) references DB2021_Actor(name) on delete cascade on update cascade,
foreign key(movie) references DB2021_Movie(title) on delete cascade on update cascade
);

CREATE INDEX APidx ON DB2021_Actor_Prize(actor);



## Review
CREATE TABLE DB2021_Review(
ID int auto_increment not null,
movie varchar(20) not null,
nickname varchar(20) not null,
create_time timestamp not null,
rating float not null,
detail varchar(500) not null,

primary key(ID),
foreign key(movie) references DB2021_Movie(title) on delete cascade on update cascade,
foreign key(nickname) references DB2021_User(nickname) on delete cascade on update cascade
);


## 좋아요
CREATE TABLE DB2021_Likes(
ID int auto_increment,
movie varchar(20),
nickname varchar(20),

primary key(ID),
foreign key(movie) references DB2021_Movie(title) on delete cascade on update cascade,
foreign key(nickname) references DB2021_User(nickname) on delete cascade on update cascade
);


