CREATE TABLE IF NOT EXISTS buildings (
  building_id     BIGINT(20) NOT NULL AUTO_INCREMENT,
  name            VARCHAR(30),
  address         VARCHAR(100),
  zip_code        INT,
  inspection_date DATE,
  year_built      INT(4),
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
  email        VARCHAR(100),
  apartment_id BIGINT(20) NOT NULL,
  PRIMARY KEY (tenant_id),
  FOREIGN KEY (apartment_id) REFERENCES apartments (apartment_id)
);

CREATE TABLE IF NOT EXISTS users (
  id           BIGINT(20) NOT NULL AUTO_INCREMENT,
  username     VARCHAR(20),
  pw           CHAR(60),
  role         VARCHAR(20),
  tenant_id    BIGINT(20),
  apartment_id BIGINT(20),
  PRIMARY KEY (id),
  FOREIGN KEY (tenant_id) REFERENCES tenants (tenant_id),
  FOREIGN KEY (apartment_id) REFERENCES apartments (apartment_id)
);

CREATE TABLE IF NOT EXISTS tasks (
  task_no      BIGINT(20) NOT NULL AUTO_INCREMENT,
  task_type    VARCHAR(20),
  resolved     VARCHAR(20),
  task_date    DATE,
  tenant_id    BIGINT(20) NOT NULL,
  apartment_id BIGINT(20) NOT NULL,
  building_id  BIGINT(20) NOT NULL,
  PRIMARY KEY (task_no),
  FOREIGN KEY (tenant_id) REFERENCES tenants (tenant_id),
  FOREIGN KEY (apartment_id) REFERENCES apartments (apartment_id),
  FOREIGN KEY (building_id) REFERENCES buildings (building_id)
);

CREATE TABLE IF NOT EXISTS task_messages (
  message_no   BIGINT(20) NOT NULL AUTO_INCREMENT,
  post_date    DATE,
  time_posted  TIMESTAMP,
  message_text VARCHAR(150),
  task_no      BIGINT(20) NOT NULL,
  PRIMARY KEY (message_no),
  FOREIGN KEY (task_no) REFERENCES tasks (task_no)
);
