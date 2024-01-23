DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS user_favorite_item;
DROP TABLE IF EXISTS order_table;
DROP TABLE IF EXISTS USER_ORDER;

CREATE TABLE user (
     id int(11) unsigned NOT NULL AUTO_INCREMENT,
     first_name VARCHAR(300) NOT NULL DEFAULT '',
     last_name VARCHAR(300) NOT NULL DEFAULT '',
     email VARCHAR(300) NOT NULL DEFAULT '',
     phone VARCHAR(20) NOT NULL DEFAULT '',
     address VARCHAR(300) NOT NULL DEFAULT '',
     username varchar(20) NOT NULL DEFAULT '',
     password varchar(20) NOT NULL DEFAULT '',
     PRIMARY KEY (id)
);



CREATE TABLE item (
    id int(11) unsigned NOT NULL AUTO_INCREMENT,
    title VARCHAR(300) NOT NULL DEFAULT '',
    photo_url VARCHAR(300) NOT NULL DEFAULT '',
    price_usd DOUBLE NOT NULL,
    available_stock INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_favorite_item (
    user_id INT(11) UNSIGNED,
    item_id INT(11) UNSIGNED,
    PRIMARY KEY (user_id, item_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (item_id) REFERENCES item(id)
);


CREATE TABLE order_table (
    order_id int(11) unsigned NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    order_date DATE,
    shipping_address VARCHAR(300) NOT NULL DEFAULT '',
    total_price DOUBLE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT '',
    PRIMARY KEY (order_id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);


CREATE TABLE USER_ORDER (
    order_id int(11) unsigned,
    item_id INT,
    user_id INT,
    PRIMARY KEY (order_id, item_id, user_id),
    FOREIGN KEY (order_id) REFERENCES order_table(order_id),
    FOREIGN KEY (item_id) REFERENCES item(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

INSERT INTO user (id, first_name, last_name, email, phone, address, username, password)
VALUES (1, 'odai', 'sharif', 'odaisharif99@gmail.com', '0506909778', 'Tel-aviv', 'odai99', 'password');

INSERT INTO item (id, title, photo_url, price_usd, available_stock)
VALUES (1, 'alexander mcqueen', 'https://assetsprx.matchesfashion.com/img/1507137/1/large.jpg', 200.00, 14);

INSERT INTO item (id, title, photo_url, price_usd, available_stock)
VALUES (2, 'jacket', 'https://assetsprx.matchesfashion.com/img/1558858/1/large.jpg', 120.00, 12);

INSERT INTO item (id, title, photo_url, price_usd, available_stock)
VALUES (3, 'watch decoration', 'https://assetsprx.matchesfashion.com/img/1455130/1/large.jpg', 250.00, 0);

INSERT INTO item (id, title, photo_url, price_usd, available_stock)
VALUES (4, 'hoodie', 'https://assetsprx.matchesfashion.com/img/1573317/1/large.jpg', 150.00, 18);

INSERT INTO item (id, title, photo_url, price_usd, available_stock)
VALUES (5, 'bag rail', 'https://assetsprx.matchesfashion.com/img/1567149/1/large.jpg', 350.00, 11);

INSERT INTO item (id, title, photo_url, price_usd, available_stock)
VALUES (6, 'jeans havana', 'https://assetsprx.matchesfashion.com/img/1530450/1/large.jpg', 250.00, 0);

INSERT INTO item (id, title, photo_url, price_usd, available_stock)
VALUES (7, 'shirt', 'https://assetsprx.matchesfashion.com/img/1572606/1/large.jpg', 450.00, 19);

INSERT INTO item (id, title, photo_url, price_usd, available_stock)
VALUES (8, 'ur choice', 'https://assetsprx.matchesfashion.com/img/1541042/1/large.jpg', 350.00, 24);

INSERT INTO item (id, title, photo_url, price_usd, available_stock)
VALUES (9, 'cats', 'https://assetsprx.matchesfashion.com/img/1572604/1/large.jpg', 250.00, 24);

INSERT INTO item (id, title, photo_url, price_usd, available_stock)
VALUES (10, 'rail cage', 'https://assetsprx.matchesfashion.com/img/1572504/1/large.jpg', 150.00, 24);

INSERT INTO item (id, title, photo_url, price_usd, available_stock)
VALUES (11, 'freaking', 'https://assetsprx.matchesfashion.com/img/1580919/1/large.jpg', 200.00, 24);

INSERT INTO item (id, title, photo_url, price_usd, available_stock)
VALUES (12, 'molding', 'https://assetsprx.matchesfashion.com/img/1572483/1/large.jpg', 200.00, 0);
