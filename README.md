# Recipe Management System

Σύστημα διαχείρισης συνταγών με Spring Boot και MySQL.

## 📋 Απαιτήσεις Συστήματος

- **Docker Desktop** (για Windows/Mac) ή **Docker Engine** (για Linux)
- **Git**
- **Java 17 και Maven 3.9 ΔΕΝ απαιτούνται για την εκτέλεση της εφαρμογής (χρησιμοποιούνται μόνο για development).**


## 🚀 Οδηγίες Εκτέλεσης(Συνιστάται με Docker)

### Βήμα 1: Αντιγραφή του Repository
```bash
git clone https://github.com/alexPalladis/recipe-management-system.git
cd recipe-management-system
```

### Βήμα 2: Δημιουργία αρχείων ρυθμίσεων από τα examples
**Το παρακάτω αρχείο δεν υπάρχει στο repo για να μην εκτεθούν credentials. Πρέπει να δημιουργηθεί από εσάς όπως το .env.example**

- Δημιουργία .env
```bash
cp .env.example .env
```

### Βήμα 3: Ρύθμιση credentials
- Επεξεργαστείτε το .env με τα credentials σας για την τοπική βάση δεδομένων σας MySQL:
```bash
MYSQL_ROOT_PASSWORD=your_database_password_goes_here
MYSQL_DATABASE=recipesdb
SPRING_DATASOURCE_USERNAME=app_user  **Αυτό αφορά τον χρήστη εφαρμογής που έχουμε ορίσει στο .sql αρχείο, κανονικά είναι κρυφό**
SPRING_DATASOURCE_PASSWORD=strong_password_here  **Αυτό αφορά τον χρήστη εφαρμογής που έχουμε ορίσει στο .sql αρχείο, κανονικά είναι κρυφό**
```

### Βήμα 4: ΣΗΜΑΝΤΙΚΟ - Εκκίνηση Docker Desktop 🐳
**ΠΡΟΣΟΧΗ: Αυτό το βήμα είναι ΥΠΟΧΡΕΩΤΙΚΟ!!**
**Σε περίπτωση που δεν έχετε το Docker εγκατεστημένο, θα βρείτε σχετικές οδηγίες παρακάτω σε επόμενο κεφάλαιο.**

#### Για Windows:
1. **Ανοίξτε το Docker Desktop** από το Start Menu
2. **Περιμένετε** μέχρι να εμφανιστεί το πράσινο εικονίδιο στο system tray
3. **Επιβεβαιώστε** ότι είναι ενεργό: το Docker Desktop δείχνει "Docker Desktop is running"

#### Για Mac:
1. **Ανοίξτε το Docker Desktop** από το Applications folder
2. **Περιμένετε** μέχρι το εικονίδιο στη μπάρα menu να γίνει πράσινο
3. **Επιβεβαιώστε**: Κλικ στο εικονίδιο → "Docker Desktop is running"

#### Για Linux:
```bash
# Ξεκινήστε την υπηρεσία Docker
sudo systemctl start docker

# Επιβεβαιώστε ότι τρέχει
sudo systemctl status docker
```

### Βήμα 5: Εκκίνηση Backend + Database
- **Στο Terminal του root folder**
```bash
docker compose up --build
```
**Την πρώτη φορά ίσως χρειαστούν 2-3 λεπτά!**

### Πρόσβαση
- **Backend API: http://localhost:8080**
- **Swagger UI: http://localhost:8080/swagger-ui.html**

---

## 🛑 Διαχείριση Containers

### Τερματισμός Εφαρμογής
```bash
docker compose down
```

### Επανεκκίνηση μετά από νέο pull
```bash
git pull
docker compose up --build
```

### Πλήρες reset βάσης δεδομένων
```bash
docker compose down -v
docker compose up --build
```

---
## 🧑‍💻 Local Development (προαιρετικό) - Μόνο η βάση δεδομένων σε Container

# 📋 Απαιτήσεις Συστήματος
- **Git**
- **Docker Desktop** (για Windows/Mac) ή **Docker Engine** (για Linux)
- **Java 17 και Maven 3.9**

### Βήμα 1: Αντιγραφή του Repository
```bash
git clone https://github.com/alexPalladis/recipe-management-system.git
cd recipe-management-system
```

### Βήμα 2: Δημιουργία αρχείων ρυθμίσεων από τα examples
**Τα παρακάτω αρχεία δεν υπάρχουν στο repo για να μην εκτεθούν credentials. Πρέπει να δημιουργηθούν από τα αντίστοιχα .example.**

- Δημιουργία docker-compose.yml
```bash
cp docker-compose.yml.example docker-compose.yml
```

- Δημιουργία application.properties
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

- Δημιουργία .env
```bash
cp .env.example .env
```

### Βήμα 3: Ρύθμιση credentials
- Επεξεργαστείτε το .env με τα credentials σας:
```bash
MYSQL_ROOT_PASSWORD=your_database_password
MYSQL_DATABASE=recipesdb
SPRING_DATASOURCE_USERNAME=your_db_username
SPRING_DATASOURCE_PASSWORD=your_db_password

### Βήμα 4: ΣΗΜΑΝΤΙΚΟ - Εκκίνηση Docker Desktop 🐳
**ΠΡΟΣΟΧΗ: Αυτό το βήμα είναι ΥΠΟΧΡΕΩΤΙΚΟ!!**
**Σε περίπτωση που δεν έχετε το Docker εγκατεστημένο, θα βρείτε σχετικές οδηγίες παρακάτω σε επόμενο κεφάλαιο.**

#### Για Windows:
1. **Ανοίξτε το Docker Desktop** από το Start Menu
2. **Περιμένετε** μέχρι να εμφανιστεί το πράσινο εικονίδιο στο system tray
3. **Επιβεβαιώστε** ότι είναι ενεργό: το Docker Desktop δείχνει "Docker Desktop is running"

#### Για Mac:
1. **Ανοίξτε το Docker Desktop** από το Applications folder
2. **Περιμένετε** μέχρι το εικονίδιο στη μπάρα menu να γίνει πράσινο
3. **Επιβεβαιώστε**: Κλικ στο εικονίδιο → "Docker Desktop is running"

#### Για Linux:
```bash
# Ξεκινήστε την υπηρεσία Docker
sudo systemctl start docker

# Επιβεβαιώστε ότι τρέχει
sudo systemctl status docker
```

#### Δοκιμή Docker:
```bash
# Αυτή η εντολή πρέπει να δουλέψει χωρίς σφάλματα
docker --version
docker-compose --version
```

### Βήμα 4: Εκκίνηση Βάσης Δεδομένων
```bash
# Ξεκινήστε τη MySQL με Docker Compose από το terminal του ΙDE στον root folder
docker compose up -d db


# Επιβεβαιώστε ότι τρέχει
docker ps
```

**Πρέπει να δείτε:**
```
CONTAINER ID   IMAGE       PORTS                    NAMES
xxxxxxxxx      mysql:8.0   0.0.0.0:3307->3306/tcp   mysql-recipes
```

**Περιμένετε 30-60 δευτερόλεπτα** για να ξεκινήσει πλήρως η MySQL.

#### Έλεγχος της Βάσης:
```bash
# Δείτε τα logs της βάσης
docker logs mysql-recipes

# Πρέπει να δείτε κάτι σαν: "MySQL init process done. Ready for start up."
```

### Βήμα 5: Εκτέλεση της Εφαρμογής
```bash
# Εγκατάσταση dependencies και εκτέλεση
mvn clean compile
mvn spring-boot:run
```

**Περιμένετε** μέχρι να δείτε το μήνυμα:
```
Started RecipeManagementSystemApplication in X.XXX seconds (process running on PID XXXX)
```

### Βήμα 6: Δοκιμή της Εφαρμογής

#### Δοκιμή API:
```bash
# Δοκιμή βασικού endpoint
curl http://localhost:8080/api/recipes/all

# Πρέπει να επιστρέψει JSON με συνταγές
```

#### Swagger UI:
**Ανοίξτε** στον browser: http://localhost:8080/swagger-ui.html

## 🚨 Αντιμετώπιση Κοινών Προβλημάτων

### ❌ "Cannot connect to the Docker daemon"
**Αιτία:** Το Docker Desktop δεν τρέχει

**Λύση:**
1. **Ανοίξτε το Docker Desktop**
2. **Περιμένετε** μέχρι να εμφανιστεί "Docker Desktop is running"
3. **Δοκιμάστε ξανά:** `docker --version`

### ❌ "Connection refused" ή database errors
**Αιτία:** Η MySQL δεν έχει ξεκινήσει ακόμα

**Λύση:**
```bash
# Ελέγξτε την κατάσταση
docker ps

# Αν δεν βλέπετε mysql-recipes, ξεκινήστε ξανά
docker-compose up -d

# Περιμένετε και δείτε τα logs
docker logs mysql-recipes -f
```

### ❌ "Port 3307 already in use"
**Αιτία:** Άλλη MySQL instance χρησιμοποιεί τo port

**Λύση:**
```bash
# Δείτε τι χρησιμοποιεί την port
sudo lsof -i :3307

# Σταματήστε άλλες MySQL instances
sudo service mysql stop

# Ή αλλάξτε τo port στο docker-compose.yml
# "3308:3306" αντί για "3307:3306"
```

### ❌ "Cannot resolve dependencies"
**Λύση:**
```bash
mvn clean install -U
```

### ❌ Application.properties not found
**Αιτία:** Δεν δημιουργήσατε το αρχείο

**Λύση:** Δημιουργήστε το αρχείο `src/main/resources/application.properties` όπως στο Βήμα 3.2

## ✅ Επιβεβαίωση Επιτυχούς Εκτέλεσης

Η εφαρμογή λειτουργεί σωστά όταν:
- ✅ `docker ps` δείχνει το `mysql-recipes` container
- ✅ `mvn spring-boot:run` ξεκινάει χωρίς σφάλματα
- ✅ `curl http://localhost:8080/api/recipes/all` επιστρέφει JSON
- ✅ http://localhost:8080/swagger-ui.html είναι προσβάσιμο

## 📁 Δομή Project

```
src/
├── main/
│   ├── java/.../
|   |   ├── config/         #Configuration files
│   │   ├── controllers/    # REST API Controllers
│   │   ├── dtos/           # Data Transfer Objects  
│   │   ├── entities/       # Database Entities
│   │   ├── enums/          # Enumerations
│   │   ├── mappers/        # Mappers for Entities-Dtos
│   │   ├── services/       # Business Logic
│   │   ├── repositories/   # Data Access
│   │   └── exceptions/     # Custom Exceptions
│   └── resources/
│       └── application.properties # Ρυθμίσεις εφαρμογής
├── docker-compose.yml      # Ρυθμίσεις image MySQL
├── sample_data_simple.sql  # Φορτώνει στη βάση δεδομένων δειγματικά δεδομένα κατά την πρώτη εκκίνηση του Docker container
```

## Προβλήματα

Αν αντιμετωπίζετε προβλήματα, ελέγξτε:
1. Τα logs της εφαρμογής στο terminal
2. Τα logs της MySQL: `docker logs mysql-recipes`
3. Ότι οι θύρες 8080(Java Spring Boot) και 3307(MySQL) είναι ελεύθερες

---

# 🐳 Docker Desktop Εγκατάσταση για Windows

Οδηγίες για εγκατάσταση Docker Desktop με WSL 2 backend στα Windows.

## 📋 Απαιτήσεις

- Windows 10 ή Windows 11
- Διαθέσιμος χώρος: ~2GB για Docker Desktop + WSL
- Administrator privileges

## 🚀 Εγκατάσταση

### Βήμα 1: Εγκατάσταση WSL 2
1. **Ανοίξτε PowerShell ως Administrator**
   - Πατήστε `Win + X` → επιλέξτε **"Windows PowerShell (Admin)"**
   - Ή: Start Menu → γράψτε "PowerShell" → Right-click → **"Run as administrator"**

2. **Εκτελέστε μία εντολή:**
   ```powershell
   wsl --install
   ```
   
   Αυτή η εντολή θα:
   - ✅ Ενεργοποιήσει το WSL feature
   - ✅ Εγκαταστήσει WSL 2 kernel  
   - ✅ Κατεβάσει Ubuntu Linux

3. **Επανεκκινήστε** όταν σας το ζητήσει

### Βήμα 2: Έλεγχος WSL
Μετά την επανεκκίνηση, ελέγξτε ότι δουλεύει:
```powershell
wsl --version
```

Θα πρέπει να δείτε κάτι σαν:
```
WSL version: 2.x.x.x
Kernel version: 5.x.x
```

### Βήμα 3: Εγκατάσταση Docker Desktop

1. **Κατέβασμα:**
   - Πηγαίνετε στο: https://docs.docker.com/desktop/install/windows-install/
   - Κλικ **"Download Docker Desktop for Windows"**

2. **Εγκατάσταση:**
   - Τρέξτε το installer (Docker Desktop Installer.exe)
   - Στην οθόνη **"Configuration"** βεβαιωθείτε ότι είναι επιλεγμένο:
     - ✅ **"Use WSL 2 instead of Hyper-V"**
   - Κλικ **"Ok"** και περιμένετε την εγκατάσταση

3. **Επανεκκίνηση:**
   - Το installer θα σας ζητήσει logout
   - Κάντε logout και login ξανά

### Βήμα 4: Έναρξη Docker
1. **Ανοίξτε Docker Desktop** από το Start Menu
2. Περιμένετε να ξεκινήσει (2-3 λεπτά την πρώτη φορά)
3. Θα δείτε: **"Docker Desktop is running"**

### Βήμα 5: Δοκιμή
Ανοίξτε Command Prompt ή PowerShell και δοκιμάστε:
```cmd
docker --version
docker-compose --version
```

Θα πρέπει να δείτε:
```
Docker version 24.x.x
Docker Compose version v2.x.x
```

## ✅ Επιτυχής Εγκατάσταση

Η εγκατάσταση είναι επιτυχής όταν:
- ✅ Το Docker Desktop ξεκινάει χωρίς σφάλματα
- ✅ Οι εντολές `docker --version` λειτουργούν)

## 🚨 Αντιμετώπιση Προβλημάτων

### "WSL 2 is not installed" ή παρόμοια σφάλματα:
```powershell
# Εκτελέστε σε PowerShell ως Administrator:
wsl --update
wsl --set-default-version 2
```

### "Virtualization is not enabled":
1. Κλείστε τον υπολογιστή εντελώς
2. Κατά την εκκίνηση πατήστε `F2`, `F12`, ή `Delete` για να μπείτε στο BIOS
3. Βρείτε "Virtualization Technology" ή "Intel VT-x" ή "AMD-V"
4. Ενεργοποιήστε το (`Enabled`)
5. Save & Exit από το BIOS

### "Docker command not found":
- Βεβαιωθείτε ότι το Docker Desktop τρέχει
- Επανεκκινήστε το Command Prompt
- Δοκιμάστε να κλείσετε και να ανοίξετε ξανά το Docker Desktop

### "Docker Desktop won't start":
1. Ελέγξτε: `wsl --list --verbose` (πρέπει να δείτε Ubuntu σε version 2)
2. Στο Docker Desktop: Settings → General → **"Use the WSL 2 based engine"** ✅
3. Apply & Restart

### Χρήσιμες εντολές WSL:
```powershell
# Δείτε εγκατεστημένες distributions:
wsl --list --verbose

# Ξεκινήστε specific distribution:
wsl -d Ubuntu

# Ενημερώστε WSL:
wsl --update
```

## 🎯 Επόμενα Βήματα

Μετά την επιτυχή εγκατάσταση Docker:
1. Συνεχίστε με την εγκατάσταση του recipe management project
2. Χρησιμοποιήστε την εντολή `docker-compose up -d` για MySQL
3. Τρέξτε την εφαρμογή με `mvn spring-boot:run`

---

## 📚 API Documentation

Αυτό το project εμπεριέχει διαδραστικό API documentation χρησιμοποιώντας OpenAPI 3.0 (Swagger).

### Πρόσβαση στο Documentation

Αφού εκκινήσετε τον server, αποκτάτε πρόσβαση στο API documentation στα:

- **Swagger UI (Διαδραστικό)**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON Spec**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Χαρακτηριστικά

- **Διαδραστικότητα** - Ελέγχετε endpoints απευθείας από τον browser
- **Search and Filter** - Βρίσκετε συγκεκριμένα endpoints γρήγορα
- **Request/Response Παραδείγματα** - Βλέπετε τις αναμενόμενες μορφές δεδομένων

### Χρήση

1. **Εκκινήστε τον server**:
```bash
   mvn spring-boot:run
```

2. **Aνοίξτε τον browser** και πλοηγηθείτε στο Swagger UI:
```
   http://localhost:8080/swagger-ui.html
```

3. **Ανακαλύψτε τα endpoints** σε κατηγορίες:
   - **Ingredients** - Διαχείριση υλικών συνταγής
   - **Recipes** - CRUD operations για συνταγές
   - **Recipe Execution** - Μαγείρεμα σε βήματα και έλεγχος προοόδου με βάση τον χρόνο εκτέλεσης
   - **Photos** - Ανεβάστε και διαχειριστείτε φωτογραφίες
   - **Steps** - CRUD operations για βήματα συνταγής

4. **Test endpoints**:
   - Click σε οποιοδόποτε endpoint και expand 
   - Click **"Try it out"**
   - Συμπληρώστε τις απαιτούμενες παραμέτρους
   - Click **"Execute"** για να κάνετε μια κλήση API
   - Δείτε το response σε παργματικό χρόνο

### Παραδείγματα API κλήσεων

#### Ανάκτηση όλων των Recipes
```http
GET /api/recipes/all
```

#### Έναρξη Recipe Execution
```http
POST /api/recipe-execution/start?recipeId=1
```

#### Ανεβάστε Photo για Recipe
```http
POST /api/photos/recipe/upload
Content-Type: multipart/form-data
```

### API Groups

Υπάρχουν σε κατηγορίες τα εξής groups:
- **Ingredients API** - `/api/ingredients/**`
- **Recipes API** - `/api/recipes/**` 
- **Recipe Execution API** - `/api/recipe-execution/**`
- **Photos API** - `/api/photos/**`
- **Steps API** - `/api/steps/**`
- **Recipe Ingredients API** - `/api/recipe-ingredients/**`
- **Step Ingredients API** - `/api/step-ingredients/**`
---

# 🔒Validation

### Recipe (Συνταγή):
- Name: Υποχρεωτικό, 2-30 χαρακτήρες
- Difficulty: Υποχρεωτικό
- Duration: Υποχρεωτικό, 1-1440 λεπτά
- Category: Υποχρεωτικό
- Description: Προαιρετικό, μέγιστο 500 χαρακτήρες

### Step (Βήμα):
- Title: Υποχρεωτικό, 2-100 χαρακτήρες
- Description: Υποχρεωτικό, 5-500 χαρακτήρες
- Order: Υποχρεωτικό, ελάχιστο 1
- Duration: Υποχρεωτικό, 1-480 λεπτά

### Ingredient (Υλικό):
- Name: Υποχρεωτικό, 2-40 χαρακτήρες
- Description: Προαιρετικό, μέγιστο 200 χαρακτήρες

### StepIngredient (Υλικό Βήματος):
- Quantity: Υποχρεωτικό, μεταξύ 1 και 10000
- MeasurementUnit: Υποχρεωτικό
- StepId: Υποχρεωτικό
- IngredientId: Υποχρεωτικό

### RecipeIngredient (Υλικό Συνταγής):
- Quantity: Υποχρεωτικό, μεταξύ 1 και 10000
- MeasurementUnit: Υποχρεωτικό
- StepId: Υποχρεωτικό
- IngredientId: Υποχρεωτικό

### Photo (Φωτογραφία):
- File name: Υποχρεωτικό, 1-200 χαρακτήρες
- MIME type: Υποχρεωτικό, έγκυρη μορφή εικόνας
- Image data: Υποχρεωτικό, 1 byte - 50MB


## Test Validation:

Στείλτε λανθασμένα δεδομένα για να δείτε τα validation errors:

```bash
curl -X POST http://localhost:8080/api/recipes \
  -H "Content-Type: application/json" \
  -d '{
    "name": "",
    "totalDuration": -5,
    "category": null
  }'
```

Αυτό θα καταλήξει σε validation errors για τα άκυρα πεδία.

# 🔴Error Handling

###  **Exception Classes:**
- **ResourceNotFoundException** - Για 404 errors (δεν βρέθηκε)
- **BadRequestException** - Για 400 errors (λανθασμένο αίτημα)

###  **GlobalExceptionHandler:**
Χειρίζεται όλους τους τύπους σφαλμάτων:

#### 1. **Validation Errors (400)**
- `MethodArgumentNotValidException` - από @Valid στα DTOs
- `ConstraintViolationException` - από @NotNull, @Positive στους controllers
- Μήνυμα: "Αποτυχία Επικύρωσης"

#### 2. **File Upload Errors (413)**  
- `MaxUploadSizeExceededException` - πολύ μεγάλο αρχείο
- Μήνυμα: "Το μέγεθος του αρχείου υπερβαίνει το μέγιστο επιτρεπτό όριο"

#### 3. **Type Mismatch Errors (400)**
- `MethodArgumentTypeMismatchException` - λάθος τύπος δεδομένων
- Μήνυμα: "Άκυρη τιμή 'X' για την παράμετρο 'Y'. Αναμενόμενος τύπος: Z"

#### 4. **Custom Validation Errors (400)**
- `IllegalArgumentException` - από τη custom validation στα photos
- Μήνυμα: "Λανθασμένη Παράμετρος"

#### 5. **Resource Not Found (404)**
- `ResourceNotFoundException` - όταν δεν βρίσκεται κάτι
- Μήνυμα: "Δεν Βρέθηκε"

#### 6. **Generic Errors (500)**
- `Exception` - για όλα τα άλλα σφάλματα
- Μήνυμα: "Εσωτερικό Σφάλμα Διακομιστή"

###  **Standardized Error Response:**

```json
{
  "timestamp": "2024-12-14T15:30:45",
  "status": 400,
  "error": "Αποτυχία Επικύρωσης",
  "message": "Παρασχέθηκαν άκυρα δεδομένα",
  "path": "/api/recipes",
  "validationErrors": [
    {
      "field": "name",
      "rejectedValue": "",
      "message": "Το όνομα της συνταγής είναι υποχρεωτικό"
    },
    {
      "field": "totalDuration",
      "rejectedValue": -5,
      "message": "Η συνολική διάρκεια πρέπει να είναι τουλάχιστον 1 λεπτό"
    }
  ]
}
```

###  **Test Error Handling:**

```bash
# Test validation error
curl -X POST http://localhost:8080/api/recipes \
  -H "Content-Type: application/json" \
  -d '{
    "name": "",
    "totalDuration": -5
  }'
```

