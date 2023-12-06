CREATE TABLE city (
  id BIGINT PRIMARY KEY NOT NULL UNIQUE,
  name varchar_ignorecase(255) ,
  region varchar_ignorecase(30) ,
  province varchar_ignorecase(2) ,
  population INTEGER
);

ALTER table city add unique(name, region, province);

CREATE TABLE geo (
  id BIGINT PRIMARY KEY NOT NULL UNIQUE,
  name varchar_ignorecase(255) ,
  lng DECIMAL(10,8),
  lat DECIMAL(10,8) ,
  foreign key(id) references city(id) ON DELETE CASCADE ON UPDATE CASCADE
);
