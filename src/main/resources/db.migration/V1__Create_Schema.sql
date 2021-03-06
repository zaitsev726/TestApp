DROP TABLE IF EXISTS customers CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS purchases CASCADE;
DROP SEQUENCE IF EXISTS Customers_generator CASCADE;
DROP SEQUENCE IF EXISTS Products_generator CASCADE;
DROP SEQUENCE IF EXISTS Purchases_generator CASCADE;


create sequence Customers_generator
    as integer
    minvalue 1
    maxvalue 2147483647;

create sequence Products_generator
    as integer
    minvalue 1
    maxvalue 2147483647;

create sequence Purchases_generator
    as integer
    minvalue 1
    maxvalue 2147483647;


CREATE TABLE customers
(
    id_customer integer not null PRIMARY KEY DEFAULT nextval('Customers_generator'),
    name VARCHAR(150) not null ,
    surname VARCHAR(150) not null
);

CREATE TABLE products
(
    id_product integer PRIMARY KEY DEFAULT nextval('Products_generator'),
    product_title VARCHAR(150) not null ,
    price float4 not null
);

CREATE TABLE purchases
(
    id_purchase integer PRIMARY KEY DEFAULT nextval('Purchases_generator'),
    id_customer integer not null ,
    id_product integer not null ,
    purchase_date date not null ,
    quantity integer not null ,
    FOREIGN KEY (id_customer) REFERENCES customers(id_customer) ON DELETE CASCADE ,
    FOREIGN KEY (id_product) REFERENCES  products(id_product) ON DELETE CASCADE
);

insert into customers(name, surname) values ('Константин','Агафонов');
insert into customers(name, surname) values ('Виктория','Белоусова');
insert into customers(name, surname) values ('Роберт','Кудряшов');
insert into customers(name, surname) values ('Дональд','Королёв');
insert into customers(name, surname) values ('Роман','Блинов');
insert into customers(name, surname) values ('Амира','Логинова');
insert into customers(name, surname) values ('Эжени','Лобанова');
insert into customers(name, surname) values ('Триана','Шашкова');
insert into customers(name, surname) values ('Архип','Ковалёв');
insert into customers(name, surname) values ('Иван','Иванов');
insert into customers(name, surname) values ('Владимир','Иванов');
insert into customers(name, surname) values ('Александр','Блинов');
insert into customers(name, surname) values ('Анастасия','Клолёва');
insert into customers(name, surname) values ('Любовь','Кудряшова');

insert into products(product_title, price) values ('Манго',64.90);
insert into products(product_title, price) values ('Голубика',119.00);
insert into products(product_title, price) values ('Багет',39.99);
insert into products(product_title, price) values ('Виноград',149.00);
insert into products(product_title, price) values ('Лайм',79.99);
insert into products(product_title, price) values ('Говядина',449.00);
insert into products(product_title, price) values ('Редис',59.99);
insert into products(product_title, price) values ('Колбаса',539.00);
insert into products(product_title, price) values ('Голень цыпленка',179.00);
insert into products(product_title, price) values ('Груша',159.00);
insert into products(product_title, price) values ('Чиз-кейк',329.00);
insert into products(product_title, price) values ('Капуста',69.99);
insert into products(product_title, price) values ('Печенье',299.90);
insert into products(product_title, price) values ('Свинина',219.00);
insert into products(product_title, price) values ('Томаты',139.00);
insert into products(product_title, price) values ('Сыр',1614.15);
insert into products(product_title, price) values ('Апельсины',109.00);
insert into products(product_title, price) values ('Коктейль',229.00);
insert into products(product_title, price) values ('Чай',55.99);
insert into products(product_title, price) values ('Мыло',89.99);
insert into products(product_title, price) values ('Шампунь',86.90);
insert into products(product_title, price) values ('Минеральная вода',23.30);
insert into products(product_title, price) values ('Газированная вода',89.90);
insert into products(product_title, price) values ('Шоколад',46.75);
insert into products(product_title, price) values ('Гречневая каша',18.30);

insert into purchases(id_customer, id_product, purchase_date,quantity) values (1,16,date '2020-03-25',3);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (1,3,date '2020-03-25',6);

insert into purchases(id_customer, id_product, purchase_date,quantity) values (2,20,date '2020-04-15',2);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (2,15,date '2020-04-15',5);

insert into purchases(id_customer, id_product, purchase_date,quantity) values (3,7,date '2020-03-25',10);

insert into purchases(id_customer, id_product, purchase_date,quantity) values (4,20,date '2019-08-08',8);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (4,6,date '2019-08-08',3);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (4,9,date '2019-08-08',7);

insert into purchases(id_customer, id_product, purchase_date,quantity) values (5,18,date '2020-10-19',7);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (5,4,date '2020-10-19',2);

insert into purchases(id_customer, id_product, purchase_date,quantity) values (6,2,date '2020-01-21',8);

insert into purchases(id_customer, id_product, purchase_date,quantity) values (7,13,date '2019-03-17',3);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (7,9,date '2019-03-17',10);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (7,2,date '2019-03-17',1);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (7,7,date '2019-03-17',4);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (7,19,date '2019-03-17',5);

insert into purchases(id_customer, id_product, purchase_date,quantity) values (8,14,date '2020-02-20',9);

insert into purchases(id_customer, id_product, purchase_date,quantity) values (9,6,date '2019-07-03',3);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (9,3,date '2019-07-03',7);

insert into purchases(id_customer, id_product, purchase_date,quantity) values (10,3,date '2020-03-03',4);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (10,14,date '2020-03-03',6);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (10,2,date '2020-03-03',6);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (10,8,date '2020-03-03',1);

insert into purchases(id_customer, id_product, purchase_date,quantity) values (11,22,date '2019-07-23',4);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (11,8,date '2019-07-23',7);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (11,8,date '2019-07-23',2);

insert into purchases(id_customer, id_product, purchase_date,quantity) values (12,3,date '2020-05-17',1);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (12,8,date '2020-03-17',3);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (12,25,date '2020-03-17',6);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (12,22,date '2020-03-17',4);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (12,16,date '2020-03-17',2);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (12,21,date '2020-03-17',5);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (12,1,date '2020-03-17',3);

insert into purchases(id_customer, id_product, purchase_date,quantity) values (13,2,date '2019-09-09',3);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (13,7,date '2019-09-09',2);

insert into purchases(id_customer, id_product, purchase_date,quantity) values (14,10,date '2020-01-12',1);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (14,18,date '2020-01-12',6);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (14,19,date '2020-01-12',8);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (14,9,date '2020-01-12',4);
insert into purchases(id_customer, id_product, purchase_date,quantity) values (14,6,date '2020-01-12',5);