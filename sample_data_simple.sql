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
quantity DOUBLE NULL,
measurement_unit ENUM('GRAMS', 'KILOGRAMS', 'MILLILITERS', 'LITERS', 'CUPS', 'TABLESPOONS', 'TEASPOONS', 'PIECES', 'SLICES', 'PINCH') NULL,
FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE,
FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS photos (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
file_name VARCHAR(200) NOT NULL,
mime_type VARCHAR(100) NOT NULL,
image_data LONGBLOB NOT NULL,
description VARCHAR(500),
recipe_id BIGINT,
step_id BIGINT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE,
FOREIGN KEY (step_id) REFERENCES steps(id) ON DELETE CASCADE,

CONSTRAINT chk_photo_parent CHECK (
(recipe_id IS NOT NULL AND step_id IS NULL) OR
(recipe_id IS NULL AND step_id IS NOT NULL)
)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS step_ingredients (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
step_id BIGINT NOT NULL,
ingredient_id BIGINT NOT NULL,
quantity DOUBLE NULL,
measurement_unit ENUM('GRAMS', 'KILOGRAMS', 'MILLILITERS', 'LITERS', 'CUPS', 'TABLESPOONS', 'TEASPOONS', 'PIECES', 'SLICES', 'PINCH') NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (step_id) REFERENCES steps(id) ON DELETE CASCADE,
FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

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
(18, 'Άνηθος', 'Φρέσκος άνηθος', NOW(), NOW()),
(19, 'Ρύζι καρολίνα', 'Ρύζι καρολίνα για γεμιστά', NOW(), NOW()),
(20, 'Μελιτζάνα', 'Φρέσκια μελιτζάνα', NOW(), NOW()),
(21, 'Κολοκύθι', 'Φρέσκο κολοκύθι', NOW(), NOW()),
(22, 'Κιμάς μοσχαρίσιος', 'Φρέσκος κιμάς μοσχαρίσιος', NOW(), NOW()),
(23, 'Κανέλα', 'Τριμμένη κανέλα', NOW(), NOW()),
(24, 'Γαρύφαλλο', 'Τριμμένο γαρύφαλλο', NOW(), NOW()),
(25, 'Ντοματοπελτές', 'Συμπυκνωμένος ντοματοπελτές', NOW(), NOW()),
(26, 'Πατάτα', 'Πατάτα μεσαίου μεγέθους', NOW(), NOW()),
(27, 'Θυμάρι', 'Ξερό θυμάρι', NOW(), NOW()),
(28, 'Μοσχοκάρυδο', 'Τριμμένο μοσχοκάρυδο', NOW(), NOW()),
(29, 'Γάλα', 'Φρέσκο πλήρες γάλα', NOW(), NOW()),
(30, 'Βούτυρο', 'Αγελαδινό βούτυρο', NOW(), NOW()),
(31, 'Αλεύρι', 'Αλεύρι για όλες τις χρήσεις', NOW(), NOW()),
(32, 'Αυγό', 'Φρέσκο αυγό', NOW(), NOW());


INSERT INTO recipes (id, name, difficulty, total_duration, category, description, created_at, updated_at) VALUES
(1, 'Σπαγγέτι με ντομάτα', 'EASY', 35, 'MAIN_COURSE', 'Κλασική ιταλική συνταγή με φρέσκια ντομάτα και βασιλικό', NOW(), NOW()),
(2, 'Ελληνική σαλάτα', 'EASY', 15, 'SALAD', 'Παραδοσιακή χωριάτικη σαλάτα', NOW(), NOW()),
(3, 'Μουσακάς', 'HARD', 135, 'MAIN_COURSE', 'Παραδοσιακός ελληνικός μουσακάς με μελιτζάνες', NOW(), NOW()),
(4, 'Κοτόπουλο λεμονάτο', 'MEDIUM', 45, 'MAIN_COURSE', 'Κοτόπουλο ψημένο με λεμόνι και μπαχαρικά', NOW(), NOW()),
(5, 'Τζατζίκι', 'EASY', 10, 'APPETIZER', 'Παραδοσιακό ελληνικό τζατζίκι', NOW(), NOW()),
(6, 'Γεμιστά', 'MEDIUM', 90, 'MAIN_COURSE','Παραδοσιακά γεμιστά με ρύζι και μυρωδικά', NOW(), NOW()),
(7, 'Μακαρόνια καρμπονάρα', 'MEDIUM', 20, 'MAIN_COURSE','Κλασική ιταλική καρμπονάρα χωρίς κρέμα', NOW(), NOW()),
(8, 'Πατάτες φούρνου με λεμόνι', 'EASY', 60, 'APPETIZER','Τραγανές πατάτες φούρνου με λεμόνι και ρίγανη', NOW(), NOW());


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
(20, 3, 1, 'Προετοιμασία λαχανικών','Κόψτε μελιτζάνες και πατάτες σε φέτες και τηγανίστε ή ψήστε ελαφρά', 30, NOW(), NOW()),
(21, 3, 2, 'Σάλτσα κιμά','Σοτάρετε κρεμμύδι και σκόρδο, προσθέστε κιμά, πελτέ και μπαχαρικά', 25, NOW(), NOW()),
(22, 3, 3, 'Μπεσαμέλ','Λιώστε το βούτυρο, προσθέστε αλεύρι, γάλα, αυγά και μοσχοκάρυδο', 20, NOW(), NOW()),
(23, 3, 4, 'Στήσιμο','Στρώστε πατάτες, μελιτζάνες, κιμά και μπεσαμέλ σε ταψί', 15, NOW(), NOW()),
(24, 3, 5, 'Ψήσιμο','Ψήστε στους 180°C μέχρι να ροδίσει η επιφάνεια', 45, NOW(), NOW());


INSERT INTO steps (id, recipe_id, step_order, title, description, duration, created_at, updated_at) VALUES
(8, 4, 1, 'Μαρινάρισμα', 'Μαρινάρετε το κοτόπουλο με λεμόνι, ελαιόλαδο και μπαχαρικά', 15, NOW(), NOW()),
(9, 4, 2, 'Ψήσιμο', 'Ψήνετε το κοτόπουλο στον φούρνο για 30 λεπτά', 30, NOW(), NOW());


INSERT INTO steps (id, recipe_id, step_order, title, description, duration, created_at, updated_at) VALUES
(10, 5, 1, 'Προετοιμασία αγγουριού', 'Τρίψτε το αγγούρι και στραγγίστε το για να φύγει το νερό', 5, NOW(), NOW()),
(11, 5, 2, 'Ανάμειξη', 'Αναμείξτε το γιαούρτι με το αγγούρι, σκόρδο, ελαιόλαδο και άνηθο', 5, NOW(), NOW());

INSERT INTO steps (id, recipe_id, step_order, title, description, duration, created_at, updated_at) VALUES
(12, 6, 1, 'Προετοιμασία λαχανικών','Κόψτε τα καπάκια από ντομάτες και πιπεριές και αδειάστε τα', 20, NOW(), NOW()),
(13, 6, 2, 'Γέμιση','Αναμείξτε ρύζι, κρεμμύδι, μυρωδικά, ελαιόλαδο και ντομάτα', 15, NOW(), NOW()),
(14, 6, 3, 'Γέμισμα και ψήσιμο','Γεμίστε τα λαχανικά και ψήστε στον φούρνο στους 180°C', 55, NOW(), NOW());

INSERT INTO steps (id, recipe_id, step_order, title, description, duration, created_at, updated_at) VALUES
(15, 7, 1, 'Βράσιμο μακαρονιών','Βράστε τα μακαρόνια σε αλατισμένο νερό', 10, NOW(), NOW()),
(16, 7, 2, 'Προετοιμασία σάλτσας','Ανακατέψτε αυγό, παρμεζάνα και πιπέρι', 5, NOW(), NOW()),
(17, 7, 3, 'Ολοκλήρωση','Ενώστε μακαρόνια και σάλτσα εκτός φωτιάς', 5, NOW(), NOW());

INSERT INTO steps (id, recipe_id, step_order, title, description, duration, created_at, updated_at) VALUES
(18, 8, 1, 'Κόψιμο πατάτας','Καθαρίστε και κόψτε τις πατάτες σε κυδωνάτες', 15, NOW(), NOW()),
(19, 8, 2, 'Ψήσιμο','Ψήστε με ελαιόλαδο, λεμόνι και ρίγανη στους 200°C', 45, NOW(), NOW());


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
(3, 20, 3, 'PIECES'),
(3, 26, 3, 'PIECES'),
(3, 22, 500, 'GRAMS'),
(3, 2, 1, 'PIECES'),
(3, 3, 2, 'PIECES'),
(3, 25, 2, 'TABLESPOONS'),
(3, 23, 0.5, 'TEASPOONS'),
(3, 24, 0.25, 'TEASPOONS'),
(3, 4, 50, 'MILLILITERS'),
(3, 5, 1, 'TEASPOONS'),
(3, 6, 0.5, 'TEASPOONS'),
(3, 30, 80, 'GRAMS'),
(3, 31, 80, 'GRAMS'),
(3, 29, 1, 'LITERS'),
(3, 32, 2, 'PIECES'),
(3, 28, 0.5, 'TEASPOONS'),
(3, 8, 100, 'GRAMS');



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

INSERT INTO recipe_ingredients (recipe_id, ingredient_id, quantity, measurement_unit) VALUES
(6, 1, 6, 'PIECES'),
(6, 19, 250, 'GRAMS'),
(6, 2, 1, 'PIECES'),
(6, 4, 80, 'MILLILITERS'),
(6, 14, 1, 'TABLESPOONS'),
(6, 5, 1, 'TEASPOONS');

INSERT INTO recipe_ingredients (recipe_id, ingredient_id, quantity, measurement_unit) VALUES
(7, 7, 400, 'GRAMS'),
(7, 8, 120, 'GRAMS'),
(7, 6, 1, 'TEASPOONS'),
(7, 5, 1, 'PINCH');

INSERT INTO recipe_ingredients (recipe_id, ingredient_id, quantity, measurement_unit) VALUES
(8, 26, 1, 'KILOGRAMS'),
(8, 16, 2, 'PIECES'),
(8, 4, 70, 'MILLILITERS'),
(8, 14, 1, 'TABLESPOONS');

SELECT 'Sample data loaded automatically via Docker!' as message;
SELECT COUNT(*) as ingredients_created FROM ingredients;
SELECT COUNT(*) as recipes_created FROM recipes;
SELECT COUNT(*) as steps_created FROM steps;
SELECT COUNT(*) as recipe_ingredients_created FROM recipe_ingredients;



