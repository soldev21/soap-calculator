DROP TABLE IF EXISTS log_parent;
DROP TABLE IF EXISTS log_child;
CREATE TABLE log_parent (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  insert_date timestamp
);

CREATE TABLE log_child (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  parent_id int,
  insert_date timestamp,
  value VARCHAR
);