CREATE TABLE qrcode_status (
  id int(11) NOT NULL AUTO_INCREMENT,
  isactive tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id (id),
  CONSTRAINT qrcode_status_ibfk_11 FOREIGN KEY (id) REFERENCES children (id)
);