INSERT INTO buildings VALUES (NULL, 'Test', 'Main St', '21536', '2018-05-19', '2013');

INSERT INTO buildings VALUES (NULL, 'Test2', 'Pitt St', '21536', '2019-12-31', '2019');

INSERT INTO buildings VALUES (NULL, 'Test3', 'Maroubra St', '21536', '2018-01-01', '2010');

INSERT INTO apartments VALUES (NULL, 'N202', 69, 2, 9751, 1);

INSERT INTO apartments VALUES (NULL, 'N201', 37, 2, 7451, 1);

INSERT INTO apartments VALUES (NULL, 'N203', 75, 4, 17451, 3);

INSERT INTO tenants VALUES (NULL, 'Test', 'Data', '1234', 'test@test.com', 1);

INSERT INTO tasks VALUES (NULL, 'Kitchen', 'No', '2018-06-19', 1, 1, 1);

INSERT INTO tasks VALUES (NULL, 'Bathroom', 'No', '2018-06-19', 1, 1, 1);

INSERT INTO task_messages VALUES (NULL, '2018-06-19', '2018-06-19 00:00:00', 'Water leaking from kitchen tap.', 1);

INSERT INTO task_messages VALUES (NULL, '2018-06-19', '2018-06-19 00:00:00', 'Water all over the floor. Come ASAP.', 1);

INSERT INTO users
VALUES (NULL, 'Test', '$2a$10$Q8LhsI6ybh7XowXtL8cJf.9uZg8XqjFm403HV.NEkDHqXUZH0VX26', 'ROLE_ADMIN', NULL, NULL);

INSERT INTO users
VALUES (NULL, 'Test2', '$2a$10$Q8LhsI6ybh7XowXtL8cJf.9uZg8XqjFm403HV.NEkDHqXUZH0VX26', 'ROLE_TENANT', 1, 1);

INSERT INTO users
VALUES (NULL, 'Test3', '$2a$10$Q8LhsI6ybh7XowXtL8cJf.9uZg8XqjFm403HV.NEkDHqXUZH0VX26', 'ROLE_TENANT', NULL, NULL);