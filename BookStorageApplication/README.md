# 📚 Book Storage API

A RESTful API built with **Spring Boot** for managing a simple digital library of Books and Authors. This application demonstrates clean layered architecture, DTO pattern implementation, and advanced JPA relationships. Everything in this repo is built while learning, and at the same time I added some extra features on my own!

## 🚀 Technologies Used

* **Java**: Core language.
* **Spring Boot**: Application framework.
* **Spring Data JPA**: For database abstraction and repository management.
* **Lombok**: To reduce boilerplate code (Getters, Setters, Builders).
* **ModelMapper**: For seamless object mapping between Entities and DTOs.
* **H2/PostgreSQL**: Database storage.

## 🏗 Architecture

The project follows a standard Controller-Service-Repository pattern:

1. **Controller Layer**: Handles HTTP requests and maps DTOs.
2. **Service Layer**: Contains business logic (e.g., partial updates, author association).
3. **Repository Layer**: Extends `JpaRepository` for direct database interaction.
4. **Domain**: Separated into **Entities** and **DTOs**. Which then mapped using ModelMapper library!

## ✨ Key Features

### 1. Smart Mapping
The application uses `ModelMapper` with a **LOOSE** matching strategy to automatically map between nested objects (like `AuthorEntity` inside `BooksEntity`) and their DTO counterparts.

### 2. Full & Partial Updates
* **PUT**: Replaces the entire resource.
* **PATCH**: Performs distinct partial updates. The logic manually checks for non-null fields in the DTO and only updates those specific fields in the database, preserving existing data.

### 3. Author-Book Relationship
* Books are linked to Authors via a `ManyToOne` relationship.
* **Intelligent Linking**: When creating or updating a book, the service checks if the provided Author exists by **ID** or **Name**. If found, it links the existing author; otherwise, it handles the new data.

## 🔌 API Endpoints

### 👤 Authors

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/authors` | Create a new Author. |
| `GET` | `/api/v1/authors` | Retrieve a list of all Authors. |
| `GET` | `/api/v1/author/{id}` | Retrieve a specific Author by ID. |
| `PUT` | `/api/v1/authors/{id}` | Full update of an Author. |
| `PATCH` | `/api/v1/authors/{id}` | Partial update (e.g., update only age). |
| `DELETE` | `/api/v1/authors/{id}` | Remove an Author. |

### 📖 Books

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `PUT` | `/api/v1/books/{isbn}` | Create or Full Update a Book by ISBN. |
| `GET` | `/api/v1/books` | Retrieve a list of all Books. |
| `GET` | `/api/v1/books/{isbn}` | Retrieve a specific Book by ISBN. |
| `PATCH` | `/api/v1/books/{isbn}` | Partial update (e.g., update title only). |
| `DELETE` | `/api/v1/books/{isbn}` | Remove a Book. |

## 💻 Usage Examples

**Create an Author**
```json
POST /api/v1/authors
{
    "name": "J.R.R. Tolkien",
    "age": 81
}
```

**Create a Book(linked to Author)**
```json
PUT /api/v1/books/978-0547928227 
{
  "isbn" : "978-0547928227"
  "title" : "The Hobbit"
  "author" : {
      "name" : "J.R.R. Tolkien"
  }
}
```

## ⚙️ Setup & Run

1. Clone the repository.
```Bash
git clone https://github.com/notsohan/BookStorageApplication.git
```
2. Navigate to the project directory.
```Bash
cd bookStorageApplication
```
4. Run the application using Maven/Gradle wrapper or the main class `BookStorageApplication`.

```bash
./mvnw spring-boot:run
```
