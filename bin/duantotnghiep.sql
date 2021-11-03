create database datn CHARACTER SET utf8 COLLATE utf8_unicode_ci;
use datn;

create table ctv(
idctv int primary key auto_increment not null,
fullname varchar(50) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL,
sex VARCHAR(4) NULL,
image text,
createdate date,
username varchar(50)
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

create table Regi_Products(
idregi int primary key auto_increment not null,
regidate datetime,
idpro varchar(50),
idctv int
);

create table Accounts(
username varchar(50) primary key not null,
password varchar(50) not null,
sdt varchar(15),
money int,
address varchar(250) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL,
email varchar(100),
active bit
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

create table ncc(
idncc int primary key auto_increment not null,
nccname varchar(250) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL,
ncclogo text,
description text CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL,
username varchar(50)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

create table Comments(
idcmt int primary key auto_increment not null,
username varchar(50),
idpro varchar(50),
star int,
content varchar(250) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL,
createdate datetime
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

create table Products(
idpro varchar(50) primary key not null,
name varchar(100)  CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' not NULL,
description text CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL,
pricectv int,
createdate date,
qty int not null,
dvt varchar(50) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL,
image0 text,
image1 text,
image2 text,
image3 text,
origin varchar(100) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL,
idcate varchar(50),
idncc int
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

create table Admin(
idadmin int primary key auto_increment not null,
active bit,
fullname varchar(100) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL,
username varchar(50)
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

create table Transaction(
idtran int primary key auto_increment not null,
type int,
username varchar(50),
value varchar(100),
createdate datetime,
note text CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

create table Orders(
idorder int primary key auto_increment not null,
dateorder datetime,
total int,
status int,
address text CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL,
customer nvarchar(100),
sdtcustomer varchar(15),
payment varchar(50),
idctv int,
idncc int
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

create table Details(
iddetails int primary key auto_increment not null,
qty int,
idpro varchar(50),
idorder int
);
create table Category(
idcate varchar(50) primary key not null,
typename varchar(100) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL,
img text
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


ALTER TABLE ctv ADD FOREIGN KEY(username) REFERENCES Accounts(username);
ALTER TABLE Regi_Products ADD FOREIGN KEY(idpro) REFERENCES Products(idpro);
ALTER TABLE Regi_Products ADD FOREIGN KEY(idctv) REFERENCES ctv(idctv);
ALTER TABLE ncc ADD FOREIGN KEY(username) REFERENCES accounts(username);
ALTER TABLE Comments ADD FOREIGN KEY(username) REFERENCES Accounts(username);
ALTER TABLE Comments ADD FOREIGN KEY(idpro) REFERENCES Products(idpro);
ALTER TABLE Products ADD FOREIGN KEY(idcate) REFERENCES Category(idcate);
ALTER TABLE Products ADD FOREIGN KEY(idncc) REFERENCES ncc(idncc);
ALTER TABLE Admin ADD FOREIGN KEY(username) REFERENCES Accounts(username);
ALTER TABLE Transaction ADD FOREIGN KEY(username) REFERENCES Accounts(username);
ALTER TABLE Orders ADD FOREIGN KEY(idctv) REFERENCES ctv(idctv);
ALTER TABLE Orders ADD FOREIGN KEY(idncc) REFERENCES ncc(idncc);
ALTER TABLE Details ADD FOREIGN KEY(idpro) REFERENCES Products(idpro);
ALTER TABLE Details ADD FOREIGN KEY(idorder) REFERENCES Orders(idorder);