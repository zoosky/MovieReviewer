# --- !Ups

CREATE TABLE Items (
  id                VARCHAR(255) NOT NULL PRIMARY KEY,
  price             DECIMAL,
  numberOfDownloads INT
);

CREATE TABLE Tags (
  id   VARCHAR(255) NOT NULL PRIMARY KEY,
  name VARCHAR(64),
);

CREATE TABLE Item_Tags (
  item_id VARCHAR(255) NOT NULL,
  tag_id  VARCHAR(255) NOT NULL,
  PRIMARY KEY (item_id, tag_id)
);

ALTER TABLE Item_Tags
  ADD CONSTRAINT 'fk_item_tags_item' FOREIGN KEY (item_id) REFERENCES Items (id);

ALTER TABLE Item_Tags
  ADD CONSTRAINT 'fk_item_tags_tag' FOREIGN KEY (tag_id) REFERENCES Tags (id);

CREATE TABLE Categories (
  id   VARCHAR(255) NOT NULL PRIMARY KEY,
  name VARCHAR(64)
);

CREATE TABLE Category_Items (
  category_id VARCHAR(255) NOT NULL,
  item_id     VARCHAR(255) NOT NULL,
  PRIMARY KEY (category_id, item_id)
);

ALTER TABLE Category_Items
  ADD CONSTRAINT 'fk_category_items_category' FOREIGN KEY (category_id) REFERENCES Categories (id);

ALTER TABLE Category_Items
  ADD CONSTRAINT 'fk_category_items_item' FOREIGN KEY (item_id) REFERENCES Items (id);

# --- !Downs

DROP TABLE Item_Tags;
DROP TABLE Category_Items;
DROP TABLE Categories;
DROP TABLE Tags;
DROP TABLE Items;
