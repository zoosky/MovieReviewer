# --- !Ups

INSERT INTO Items (id, price, numberOfDownloads) VALUES ('1', 2.5, 0);
INSERT INTO Items (id, price, numberOfDownloads) VALUES ('2', 3.5, 0);

INSERT INTO Tags (id, name) VALUES ('1', 'football');
INSERT INTO Tags (id, name) VALUES ('2', 'basketball');
INSERT INTO Tags (id, name) VALUES ('3', 'ball');

INSERT INTO Item_Tags (item_id, tag_id) VALUES ('1', '1');
INSERT INTO Item_Tags (item_id, tag_id) VALUES ('1', '3');
INSERT INTO Item_Tags (item_id, tag_id) VALUES ('2', '2');
INSERT INTO Item_Tags (item_id, tag_id) VALUES ('2', '3');

INSERT INTO Categories (id, name) VALUES ('1', 'Sports');

INSERT INTO Category_Items (category_id, item_id) VALUES ('1', '1');
INSERT INTO Category_Items (category_id, item_id) VALUES ('1', '2');

# --- !Downs

DELETE FROM Category_Items;
DELETE FROM Item_Tags;
DELETE FROM Items;
DELETE FROM Tags;
DELETE FROM Categories;
