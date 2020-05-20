DROP TABLE IF EXISTS Customers CASCADE;
DROP TABLE IF EXISTS Products CASCADE;
DROP TABLE IF EXISTS Purchases CASCADE;
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


CREATE TABLE Customers
(
    id_customer integer not null PRIMARY KEY DEFAULT nextval('Customers_generator'),
    name VARCHAR(50) not null ,
    surname VARCHAR(50) not null
);

CREATE TABLE Products
(
    id_product integer not null PRIMARY KEY DEFAULT nextval('Products_generator'),
    product_title VARCHAR(100) not null ,
    price float4 not null
);

CREATE TABLE Purchases
(
    id_purchases integer not null PRIMARY KEY DEFAULT nextval('Purchases_generator'),
    id_customer integer not null ,
    id_product integer not null ,
    purchases_date date not null ,
    quantity integer not null ,
    FOREIGN KEY (id_customer) REFERENCES Customers (id_customer) ON DELETE CASCADE ,
    FOREIGN KEY (id_product) REFERENCES  Products (id_product) ON DELETE CASCADE
);

insert into Customers(surname, name) values ('Агафонов', 'Константин');
insert into Customers(surname, name) values ('Белоусова', 'Виктория');
insert into Customers(surname, name) values ('Кудряшов', 'Роберт');
insert into Customers(surname, name) values ('Королёв', 'Дональд');
insert into Customers(surname, name) values ('Блинов', 'Роман');
insert into Customers(surname, name) values ('Логинова', 'Амира');
insert into Customers(surname, name) values ('Лобанова', 'Эжени');
insert into Customers(surname, name) values ('Шашкова', 'Триана');
insert into Customers(surname, name) values ('Ковалёв', 'Архип');
insert into Customers(surname, name) values ('Носков', 'Любомир');

insert into Products(product_title, price) values ('Манго',64.90);
insert into Products(product_title, price) values ('Голубика',119.00);
insert into Products(product_title, price) values ('Багет',39.99);
insert into Products(product_title, price) values ('Виноград',149.00);
insert into Products(product_title, price) values ('Лайм',79.99);
insert into Products(product_title, price) values ('Говядина',449.00);
insert into Products(product_title, price) values ('Редис',59.99);
insert into Products(product_title, price) values ('Колбаса',539.00);
insert into Products(product_title, price) values ('Голень цыпленка',179.00);
insert into Products(product_title, price) values ('Груша',159.00);
insert into Products(product_title, price) values ('Чиз-кейк',329.00);
insert into Products(product_title, price) values ('Капуста',69.99);
insert into Products(product_title, price) values ('Печенье',299.90);
insert into Products(product_title, price) values ('Свинина',219.00);
insert into Products(product_title, price) values ('Томаты',139.00);
insert into Products(product_title, price) values ('Сыр',1614.15);
insert into Products(product_title, price) values ('Апельсины',109.00);
insert into Products(product_title, price) values ('Коктейль',229.00);
insert into Products(product_title, price) values ('Чай',55.99);
insert into Products(product_title, price) values ('Мыло',89.99);
insert into Products(product_title, price) values ('Шампунь',86.90);

insert into Purchases(id_customer, id_product, purchases_date) values (1,16,date '2020-03-25');
insert into Purchases(id_customer, id_product, purchases_date) values (1,3,date '2020-03-25');

insert into Purchases(id_customer, id_product, purchases_date) values (2,20,date '2020-04-15');
insert into Purchases(id_customer, id_product, purchases_date) values (2,15,date '2020-04-15');

insert into Purchases(id_customer, id_product, purchases_date) values (3,7,date '2020-03-25');

insert into Purchases(id_customer, id_product, purchases_date) values (4,20,date '2019-08-08');
insert into Purchases(id_customer, id_product, purchases_date) values (4,6,date '2019-08-08');
insert into Purchases(id_customer, id_product, purchases_date) values (4,9,date '2019-08-08');

insert into Purchases(id_customer, id_product, purchases_date) values (5,18,date '2020-10-19');
insert into Purchases(id_customer, id_product, purchases_date) values (5,4,date '2020-10-19');

insert into Purchases(id_customer, id_product, purchases_date) values (6,2,date '2020-01-21');

insert into Purchases(id_customer, id_product, purchases_date) values (7,13,date '2019-03-17');
insert into Purchases(id_customer, id_product, purchases_date) values (7,9,date '2019-03-17');
insert into Purchases(id_customer, id_product, purchases_date) values (7,2,date '2019-03-17');
insert into Purchases(id_customer, id_product, purchases_date) values (7,7,date '2019-03-17');
insert into Purchases(id_customer, id_product, purchases_date) values (7,19,date '2019-03-17');

insert into Purchases(id_customer, id_product, purchases_date) values (8,14,date '2020-02-20');

insert into Purchases(id_customer, id_product, purchases_date) values (9,6,date '2019-07-03');
insert into Purchases(id_customer, id_product, purchases_date) values (9,3,date '2019-07-03');

insert into Purchases(id_customer, id_product, purchases_date) values (10,3,date '2020-03-03');
insert into Purchases(id_customer, id_product, purchases_date) values (10,14,date '2020-03-03');
insert into Purchases(id_customer, id_product, purchases_date) values (10,2,date '2020-03-03');
insert into Purchases(id_customer, id_product, purchases_date) values (10,8,date '2020-03-03');