USE recipesdb;


CREATE TABLE IF NOT EXISTS ingredients (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
description TEXT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS recipes (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(150) NOT NULL,
difficulty ENUM('EASY', 'MEDIUM', 'HARD') NOT NULL,
total_duration INT NOT NULL,
category ENUM('APPETIZER', 'MAIN_COURSE', 'DESSERT', 'SALAD', 'SNACK') NOT NULL,
description TEXT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS steps (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
recipe_id BIGINT NOT NULL,
step_order INT NOT NULL,
title VARCHAR(200),
description TEXT,
duration INT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS recipe_ingredients (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
recipe_id BIGINT NOT NULL,
ingredient_id BIGINT NOT NULL,
quantity DOUBLE NOT NULL,
measurement_unit ENUM('GRAMS', 'KILOGRAMS', 'MILLILITERS', 'LITERS', 'CUPS', 'TABLESPOONS', 'TEASPOONS', 'PIECES', 'SLICES', 'PINCH') NOT NULL,
FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE,
FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


INSERT INTO ingredients (id, name, description, created_at, updated_at) VALUES
(1, 'Ντομάτα', 'Φρέσκια κόκκινη ντομάτα', NOW(), NOW()),
(2, 'Κρεμμύδι', 'Κίτρινο κρεμμύδι μεσαίου μεγέθους', NOW(), NOW()),
(3, 'Σκόρδο', 'Φρέσκο σκόρδο', NOW(), NOW()),
(4, 'Ελαιόλαδο', 'Εξαιρετικό παρθένο ελαιόλαδο', NOW(), NOW()),
(5, 'Αλάτι', 'Θαλασσινό αλάτι', NOW(), NOW()),
(6, 'Πιπέρι', 'Μαύρο πιπέρι τριμμένο', NOW(), NOW()),
(7, 'Μακαρόνια σπαγγέτι', 'Μακαρόνια σπαγγέτι από σκληρό σιτάρι', NOW(), NOW()),
(8, 'Παρμεζάνα', 'Παρμεζάνα τριμμένη', NOW(), NOW()),
(9, 'Βασιλικός', 'Φρέσκος βασιλικός', NOW(), NOW()),
(10, 'Αγγούρι', 'Φρέσκο αγγούρι', NOW(), NOW()),
(11, 'Φέτα', 'Ελληνική φέτα', NOW(), NOW()),
(12, 'Ελιές', 'Ελιές καλαμών', NOW(), NOW()),
(13, 'Ξύδι', 'Ξύδι κρασιού', NOW(), NOW()),
(14, 'Ρίγανη', 'Ελληνική ρίγανη', NOW(), NOW()),
(15, 'Κοτόπουλο', 'Φρέσκο κοτόπουλο', NOW(), NOW()),
(16, 'Λεμόνι', 'Φρέσκα λεμόνια', NOW(), NOW()),
(17, 'Γιαούρτι', 'Στραγγιστό γιαούρτι', NOW(), NOW()),
(18, 'Άνηθος', 'Φρέσκος άνηθος', NOW(), NOW());


INSERT INTO recipes (id, name, difficulty, total_duration, category, description, created_at, updated_at) VALUES
(1, 'Σπαγγέτι με ντομάτα', 'EASY', 30, 'MAIN_COURSE', 'Κλασική ιταλική συνταγή με φρέσκια ντομάτα και βασιλικό', NOW(), NOW()),
(2, 'Ελληνική σαλάτα', 'EASY', 15, 'SALAD', 'Παραδοσιακή χωριάτικη σαλάτα', NOW(), NOW()),
(3, 'Μουσακάς', 'HARD', 120, 'MAIN_COURSE', 'Παραδοσιακός ελληνικός μουσακάς με μελιτζάνες', NOW(), NOW()),
(4, 'Κοτόπουλο λεμονάτο', 'MEDIUM', 45, 'MAIN_COURSE', 'Κοτόπουλο ψημένο με λεμόνι και μπαχαρικά', NOW(), NOW()),
(5, 'Τζατζίκι', 'EASY', 10, 'APPETIZER', 'Παραδοσιακό ελληνικό τζατζίκι', NOW(), NOW());


INSERT INTO steps (id, recipe_id, step_order, title, description, duration, created_at, updated_at) VALUES
(1, 1, 1, 'Προετοιμασία υλικών', 'Κόψτε τις ντομάτες σε κυβάκια, το κρεμμύδι σε λεπτές φέτες και τσακίστε το σκόρδο', 10, NOW(), NOW()),
(2, 1, 2, 'Σωτάρισμα', 'Σε τηγάνι με ελαιόλαδο σοτάρετε το κρεμμύδι και το σκόρδο μέχρι να μαλακώσουν', 5, NOW(), NOW()),
(3, 1, 3, 'Προσθήκη ντομάτας', 'Προσθέστε τις ντομάτες, αλάτι και πιπέρι. Αφήστε να μαγειρευτεί για 10 λεπτά', 10, NOW(), NOW()),
(4, 1, 4, 'Βράσιμο μακαρονιών', 'Βράστε τα μακαρόνια σε αλατισμένο νερό σύμφωνα με τις οδηγίες συσκευασίας', 8, NOW(), NOW()),
(5, 1, 5, 'Σερβίρισμα', 'Σερβίρετε τα μακαρόνια με τη σάλτσα, παρμεζάνα και φρέσκο βασιλικό', 2, NOW(), NOW());


INSERT INTO steps (id, recipe_id, step_order, title, description, duration, created_at, updated_at) VALUES
(6, 2, 1, 'Προετοιμασία λαχανικών', 'Κόψτε τις ντομάτες σε μεγάλα κομμάτια, το αγγούρι σε ροδέλες και το κρεμμύδι σε φέτες', 8, NOW(), NOW()),
(7, 2, 2, 'Ανάμειξη και σερβίρισμα', 'Αναμείξτε όλα τα υλικά, προσθέστε φέτα, ελιές, ελαιόλαδο, ξύδι και ρίγανη', 7, NOW(), NOW());


INSERT INTO steps (id, recipe_id, step_order, title, description, duration, created_at, updated_at) VALUES
(8, 4, 1, 'Μαρινάρισμα', 'Μαρινάρετε το κοτόπουλο με λεμόνι, ελαιόλαδο και μπαχαρικά', 15, NOW(), NOW()),
(9, 4, 2, 'Ψήσιμο', 'Ψήνετε το κοτόπουλο στον φούρνο για 30 λεπτά', 30, NOW(), NOW());


INSERT INTO steps (id, recipe_id, step_order, title, description, duration, created_at, updated_at) VALUES
(10, 5, 1, 'Προετοιμασία αγγουριού', 'Τρίψτε το αγγούρι και στραγγίστε το για να φύγει το νερό', 5, NOW(), NOW()),
(11, 5, 2, 'Ανάμειξη', 'Αναμείξτε το γιαούρτι με το αγγούρι, σκόρδο, ελαιόλαδο και άνηθο', 5, NOW(), NOW());


INSERT INTO recipe_ingredients (recipe_id, ingredient_id, quantity, measurement_unit) VALUES
(1, 1, 4, 'PIECES'),
(1, 2, 1, 'PIECES'),
(1, 3, 2, 'PIECES'),
(1, 4, 50, 'MILLILITERS'),
(1, 5, 1, 'PINCH'),
(1, 6, 1, 'PINCH'),
(1, 7, 400, 'GRAMS'),
(1, 8, 100, 'GRAMS'),
(1, 9, 10, 'PIECES');


INSERT INTO recipe_ingredients (recipe_id, ingredient_id, quantity, measurement_unit) VALUES
(2, 1, 3, 'PIECES'),
(2, 10, 1, 'PIECES'),
(2, 2, 0.5, 'PIECES'),
(2, 11, 200, 'GRAMS'),
(2, 12, 100, 'GRAMS'),
(2, 4, 30, 'MILLILITERS'),
(2, 13, 15, 'MILLILITERS'),
(2, 14, 1, 'TEASPOONS');


INSERT INTO recipe_ingredients (recipe_id, ingredient_id, quantity, measurement_unit) VALUES
(4, 15, 1, 'KILOGRAMS'),
(4, 16, 2, 'PIECES'),
(4, 4, 60, 'MILLILITERS'),
(4, 14, 1, 'TABLESPOONS'),
(4, 5, 1, 'TEASPOONS'),
(4, 6, 0.5, 'TEASPOONS');


INSERT INTO recipe_ingredients (recipe_id, ingredient_id, quantity, measurement_unit) VALUES
(5, 17, 500, 'GRAMS'),
(5, 10, 1, 'PIECES'),
(5, 3, 2, 'PIECES'),
(5, 4, 30, 'MILLILITERS'),
(5, 18, 1, 'TABLESPOONS'),
(5, 5, 1, 'PINCH');

-- Log completion message
SELECT 'Sample data loaded automatically via Docker init script!' as message;
SELECT COUNT(*) as ingredients_created FROM ingredients;
SELECT COUNT(*) as recipes_created FROM recipes;
SELECT COUNT(*) as steps_created FROM steps;
SELECT COUNT(*) as recipe_ingredients_created FROM recipe_ingredients;



