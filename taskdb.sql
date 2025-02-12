-- création de la base de données -param charset global
CREATE DATABASE TaskDB;
-- utiliser la base "TaskDB"
USE TaskDB;

-- création des tables - optional parem charset par table prio
-- table account
CREATE TABLE account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- table task
CREATE TABLE task (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    createAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    status TINYINT(1) DEFAULT 0,
    account_id INT
);

-- Table des catégories
CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- création de la table d'association
CREATE TABLE task_category (
    task_id INT,
    category_id INT,
    PRIMARY KEY (task_id, category_id)
);

-- ajouter les contraintes
ALTER TABLE task
ADD CONSTRAINT fk_task_account_id
FOREIGN KEY(account_id)
REFERENCES account(id)
ON DELETE CASCADE;

ALTER TABLE task_category
ADD CONSTRAINT fk_task_category_task
FOREIGN KEY(task_id)
REFERENCES task(id)
ON DELETE CASCADE;

ALTER TABLE task_category
ADD CONSTRAINT fk_task_category_category
FOREIGN KEY(category_id)
REFERENCES category(id)
ON DELETE CASCADE;