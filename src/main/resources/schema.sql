CREATE TABLE buildings (
  building_id  BIGINT(20) NOT NULL AUTO_INCREMENT,
  address      VARCHAR(100),
  floor_levels INT,
  PRIMARY KEY (building_id)
);

CREATE TABLE apartments (
  apartment_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  apartment_no INT,
  size         INT,
  floor_no     INT,
  rent         INT,
  building_id  BIGINT(20) NOT NULL,
  PRIMARY KEY (apartment_id),
  FOREIGN KEY (building_id) REFERENCES buildings (building_id)
);

CREATE TABLE tenants (
  tenant_id    BIGINT(20) NOT NULL AUTO_INCREMENT,
  fname        VARCHAR(50),
  lname        VARCHAR(50),
  phone_no     VARCHAR(30),
  apartment_id BIGINT(20) NOT NULL,
  PRIMARY KEY (tenant_id),
  FOREIGN KEY (apartment_id) REFERENCES apartments (apartment_id)
);

CREATE TABLE managers (
  manager_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (manager_id)
);

CREATE TABLE users (
  id       BIGINT(20) NOT NULL AUTO_INCREMENT,
  username VARCHAR(20),
  pw       VARCHAR(50),
  role     VARCHAR(20),
  PRIMARY KEY (id)
);

CREATE TABLE cases (
  case_no     BIGINT(20) NOT NULL AUTO_INCREMENT,
  case_type   VARCHAR(20),
  case_text   VARCHAR(100),
  case_status VARCHAR(20),
  resolved    VARCHAR(20),
  case_date   DATE,
  fix_date    DATE,
  manager_id  BIGINT(20) NOT NULL,
  tenant_id   BIGINT(20) NOT NULL,
  PRIMARY KEY (case_no),
  FOREIGN KEY (manager_id) REFERENCES managers (manager_id),
  FOREIGN KEY (tenant_id) REFERENCES tenants (tenant_id)
);

CREATE TABLE case_messages (
  message_no   BIGINT(20) NOT NULL AUTO_INCREMENT,
  message_text VARCHAR(150),
  case_no      BIGINT(20) NOT NULL,
  PRIMARY KEY (message_no),
  FOREIGN KEY (case_no) REFERENCES cases (case_no)
);
