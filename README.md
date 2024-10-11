<h1>Spring Boot Book Library Backend (Exercise)</h1>
<p>This Spring Boot application serves as a backend for managing a book library. It offers features such as user management, book management, and integration with both relational (MySQL) and NoSQL (MongoDB) databases.</p>
<h2>Key Features</h2>
    <ul>
        <li><strong>User Management</strong>: Handles user registration, login, and authentication using JWT tokens.</li>
        <li><strong>Book Management</strong>: Allows users to add, view, update, and delete books.</li>
        <li><strong>Database Integration</strong>:
            <ul>
                <li>MySQL: Stores user information using JPA.</li>
                <li>MongoDB: Stores book information using Spring Data MongoDB.</li>
            </ul>
        </li>
        <li><strong>Session Management</strong>: Implements stateless sessions using JWT tokens.</li>
        <li><strong>CORS Configuration</strong>: Allows cross-origin requests from the `books.client` application.</li>
    </ul>

<h2>Technologies Used</h2>
    <ul>
        <li>Spring Boot</li>
        <li>Spring Data JPA</li>
        <li>Spring Data MongoDB</li>
        <li>JWT</li>
        <li>MySQL</li>
        <li>MongoDB</li>
    </ul>

<h2>Getting Started</h2>
    <ol>
        <li>Clone the repository: `git clone https://github.com/oscarivanrs/exercise.books.server.git`</li>
        <li>Build the project: `./gradlew build` or `mvn clean package`</li>
        <li>Configure environment variables: in resources/application.properties</li>
        <li>Run the application: `java -jar target/your-application.jar`</li>
    </ol>

<h2>API Endpoints</h2>
    <ul>
        <li><strong>User endpoints</strong>: `/auth/login`, `/auth/signup`, `/books/me`</li>
        <li><strong>Book endpoints</strong>: `/books/list`, `/books/add`, `/books/delete`</li>
    </ul>
