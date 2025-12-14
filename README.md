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
