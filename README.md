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

