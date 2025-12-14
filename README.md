# Recipe Management System

Σύστημα διαχείρισης συνταγών.

## 📋 Απαιτήσεις Συστήματος

- Java 17 ή νεότερη έκδοση
- Maven 3.6+
- Docker και Docker Compose

## 🚀 Οδηγίες Εκτέλεσης

### Βήμα 1: Αντιγραφή του Repository
```bash
git clone https://github.com/alexPalladis/recipe-management-system.git
cd recipe-management-system
```

### Βήμα 2: Δημιουργία Αρχείων Ρυθμίσεων
Αντιγράψτε τα template αρχεία για να δημιουργήσετε τις ρυθμίσεις:

```bash
# Αντιγραφή ρυθμίσεων βάσης δεδομένων
cp docker-compose.yml.example docker-compose.yml

# Αντιγραφή ρυθμίσεων εφαρμογής  
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

### Βήμα 3: Εκκίνηση Βάσης Δεδομένων
```bash
docker-compose up -d
```

Περιμένετε 30-60 δευτερόλεπτα για να ξεκινήσει η MySQL. Μπορείτε να ελέγξετε την κατάσταση με:
```bash
docker ps
```

### Βήμα 4: Εκτέλεση της Εφαρμογής
```bash
mvn spring-boot:run
```

Περιμένετε μέχρι να δείτε το μήνυμα: "Started RecipeManagementSystemApplication"

### Βήμα 5: Δοκιμή της Εφαρμογής
Ανοίξτε έναν browser ή χρησιμοποιήστε curl:

```bash
# Δοκιμή API
curl http://localhost:8080/api/recipes/all

# Swagger UI (για εύκολη δοκιμή)
Ανοίξτε: http://localhost:8080/swagger-ui.html
```

## 🚨 Αντιμετώπιση Προβλημάτων

### "Connection refused" ή database errors:
```bash
# Ελέγξτε αν τρέχει η MySQL
docker ps

# Επανεκκίνηση αν χρειάζεται
docker-compose restart

# Δείτε τα logs της βάσης
docker logs mysql-recipes
```

### "Port already in use":
```bash
# Δείτε τι χρησιμοποιεί την θύρα 3307
lsof -i :3307

# Ή σταματήστε άλλες MySQL instances
sudo service mysql stop
```

### "Cannot resolve dependencies":
```bash
mvn clean install
```

## 📁 Δομή Project

```
src/
├── main/
│   ├── java/.../
|   |   ├── config/          #Configuration files
│   │   ├── controllers/     # REST API Controllers
│   │   ├── dtos/           # Data Transfer Objects  
│   │   ├── entities/       # Database Entities
│   │   ├── enums/          # Enumerations
│   │   ├── mappers/        # Mappers for Entities-Dtos
│   │   ├── services/       # Business Logic
│   │   ├── repositories/   # Data Access
│   │   └── exceptions/     # Custom Exceptions
│   └── resources/
│       └── application.properties # Ρυθμίσεις εφαρμογής
│       └── database_migration
│   │    |  ├── database.sql    # Δειγματικά δεδομένα
├── docker-compose.yml      # Ρυθμίσεις image MySQL
```

##  Επιβεβαίωση Επιτυχούς Εκτέλεσης

Η εφαρμογή λειτουργεί σωστά όταν:
- ✅ Το `docker ps` δείχνει τον mysql-recipes container
- ✅ Το `mvn spring-boot:run` ξεκινάει χωρίς σφάλματα
- ✅ Το `curl http://localhost:8080/api/recipes/all` επιστρέφει JSON
- ✅ Το Swagger UI είναι προσβάσιμο στο http://localhost:8080/swagger-ui.html


## Προβλήματα

Αν αντιμετωπίζετε προβλήματα, ελέγξτε:
1. Τα logs της εφαρμογής στο terminal
2. Τα logs της MySQL: `docker logs mysql-recipes`
3. Ότι οι θύρες 8080 και 3307 είναι ελεύθερες

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

This project includes interactive API documentation using OpenAPI 3.0 (Swagger).

### Accessing the Documentation

After starting the application, you can access the API documentation at:

- **Swagger UI (Interactive)**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON Spec**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Features

- **Interactive Documentation** - Test endpoints directly from the browser
- **Search and Filter** - Find specific endpoints quickly
- **Request/Response Examples** - See expected data formats

### How to Use

1. **Start the application**:
```bash
   mvn spring-boot:run
```

2. **Open your browser** and navigate to the Swagger UI:
```
   http://localhost:8080/swagger-ui.html
```

3. **Explore the endpoints** organized by categories:
   - **Ingredients** - Manage recipe ingredients
   - **Recipes** - CRUD operations for recipes
   - **Recipe Execution** - Cook and track recipe progress
   - **Photos** - Upload and manage images

4. **Test endpoints**:
   - Click on any endpoint to expand it
   - Click **"Try it out"**
   - Fill in required parameters
   - Click **"Execute"** to make a real API call
   - View the response in real-time

### Example API Calls

#### Get All Recipes
```http
GET /api/recipes/all
```

#### Start Recipe Execution
```http
POST /api/recipe-execution/start?recipeId=1
```

#### Upload Photo for Recipe
```http
POST /api/photos/recipe/upload
Content-Type: multipart/form-data
```

### API Groups

The documentation is organized into the following groups:
- **Ingredients API** - `/api/ingredients/**`
- **Recipes API** - `/api/recipes/**` 
- **Recipe Execution API** - `/api/recipe-execution/**`
- **Photos API** - `/api/photos/**`
- **Steps API** - `/api/steps/**`
- **Recipe Ingredients API** - `/api/recipe-ingredients/**`
- **Step Ingredients API** - `/api/step-ingredients/**`

### For Developers

If you're integrating with this API:
- Use the **OpenAPI JSON** spec for code generation
- All endpoints return JSON responses
- CORS is configured for development

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

Send invalid data to see validation errors:

```bash
curl -X POST http://localhost:8080/api/recipes \
  -H "Content-Type: application/json" \
  -d '{
    "name": "",
    "totalDuration": -5,
    "category": null
  }'
```

This will trigger validation errors for the invalid fields.

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

