CREATE TABLE IF NOT EXISTS buildings (
  building_id  BIGINT(20) NOT NULL AUTO_INCREMENT,
  address      VARCHAR(100),
  floor_levels INT,
  PRIMARY KEY (building_id)
);

CREATE TABLE IF NOT EXISTS apartments (
  apartment_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  apartment_no VARCHAR(10),
  size         INT,
  floor_no     INT,
  rent         INT,
  building_id  BIGINT(20) NOT NULL,
  PRIMARY KEY (apartment_id),
  FOREIGN KEY (building_id) REFERENCES buildings (building_id)
);

CREATE TABLE IF NOT EXISTS tenants (
  tenant_id    BIGINT(20) NOT NULL AUTO_INCREMENT,
  first_name   VARCHAR(50),
  last_name    VARCHAR(50),
  phone_no     VARCHAR(30),
  apartment_id BIGINT(20) NOT NULL,
  PRIMARY KEY (tenant_id),
  FOREIGN KEY (apartment_id) REFERENCES apartments (apartment_id)
);

CREATE TABLE IF NOT EXISTS managers (
  manager_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (manager_id)
);

CREATE TABLE IF NOT EXISTS users (
  id         BIGINT(20) NOT NULL AUTO_INCREMENT,
  username   VARCHAR(20),
  pw         CHAR(60),
  role       VARCHAR(20),
  tenant_id  BIGINT(20),
  PRIMARY KEY (id),
  FOREIGN KEY (tenant_id) REFERENCES tenants (tenant_id)
);

CREATE TABLE IF NOT EXISTS cases (
  case_no     BIGINT(20) NOT NULL AUTO_INCREMENT,
  case_type   VARCHAR(20),
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

CREATE TABLE IF NOT EXISTS case_messages (
  message_no   BIGINT(20) NOT NULL AUTO_INCREMENT,
  message_text VARCHAR(150),
  case_no      BIGINT(20) NOT NULL,
  PRIMARY KEY (message_no),
  FOREIGN KEY (case_no) REFERENCES cases (case_no)
);
