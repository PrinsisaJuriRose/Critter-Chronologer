-- Create the 'critter' database
CREATE DATABASE critter;

-- Create user 'nedaa' with password 'password@1234'
CREATE USER 'nedaa'@'localhost' IDENTIFIED BY 'password@1234';

-- Grant all privileges on the 'critter' database to user 'nedaa'
GRANT ALL PRIVILEGES ON critter.* TO 'nedaa'@'localhost';

-- Apply the changes
FLUSH PRIVILEGES;