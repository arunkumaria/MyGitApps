CREATE TABLE users(
 id SERIAL PRIMARY KEY,
 email VARCHAR(100),
 password VARCHAR(255)
);

CREATE TABLE skill(
 id SERIAL PRIMARY KEY,
 name VARCHAR(100)
);

CREATE TABLE user_skill(
 id SERIAL PRIMARY KEY,
 user_id INT,
 skill_id INT,
 level VARCHAR(20),
 can_teach BOOLEAN
);

CREATE TABLE swap_request(
 id SERIAL PRIMARY KEY,
 requester_id INT,
 receiver_id INT,
 skill_offered INT,
 skill_wanted INT,
 status VARCHAR(20)
);