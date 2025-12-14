
CREATE DATABASE IF NOT EXISTS recipesdb
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE recipesdb;

CREATE TABLE IF NOT EXISTS recipes (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
category VARCHAR(100),
difficulty ENUM('EASY', 'MEDIUM', 'HARD') DEFAULT 'EASY',
total_time_minutes INT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS recipe_steps (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
recipe_id BIGINT NOT NULL,
step_order INT NOT NULL,
title VARCHAR(255),
description TEXT,
duration_minutes INT,
CONSTRAINT fk_step_recipe FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS recipe_ingredients (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
recipe_id BIGINT NOT NULL,
name VARCHAR(255) NOT NULL,
quantity VARCHAR(50),
CONSTRAINT fk_ing_recipe FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS photos (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
file_name VARCHAR(255) NOT NULL,
mime_type VARCHAR(100) NOT NULL,
image_data LONGBLOB NOT NULL,
description VARCHAR(500),
recipe_id BIGINT NULL,
step_id BIGINT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE,
FOREIGN KEY (step_id) REFERENCES recipe_steps(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS ingredients (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL UNIQUE,
description VARCHAR(500),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


-- indexes for better performance
CREATE INDEX IF NOT EXISTS idx_recipe_name ON recipes(name);
CREATE INDEX IF NOT EXISTS idx_recipe_category ON recipes(category);
CREATE INDEX IF NOT EXISTS idx_recipe_difficulty ON recipes(difficulty);
CREATE INDEX IF NOT EXISTS idx_step_recipe ON recipe_steps(recipe_id);
CREATE INDEX IF NOT EXISTS idx_step_order ON recipe_steps(recipe_id, step_order);
CREATE INDEX IF NOT EXISTS idx_ing_recipe ON recipe_ingredients(recipe_id);