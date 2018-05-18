--ENTRY LIST:
--ENTRY 3: users and roles

--3-1: insert roles
insert into role (name) values ('ROLE_USER')

--3-2: insert two users 21-2: password is password
insert into user (username, enabled, password, role_id) values ('user', true, '$2a$10$NFEGe5mEJllbzYRxgDkXQOn8hvLT1Hot44g9LgQ9wi6grbM1PTxPq', 1)
insert into user (username, enabled, password, role_id) values ('user2', true, '$2a$10$NFEGe5mEJllbzYRxgDkXQOn8hvLT1Hot44g9LgQ9wi6grbM1PTxPq', 1)

-- Insert tasks entry 17-1: add user id to each new task insert
---21-1 Hash the password (use online resources from Google search keyword: BCrypt hash generator strength 10)
insert into task (complete,description, user_id) values (true,'Code Task entity', 1);
insert into task (complete,description, user_id) values (false,'Discuss users and roles', 1);
insert into task (complete,description, user_id) values (false,'Enable Spring Security', 2);
insert into task (complete,description, user_id) values (false,'Test application', 2);