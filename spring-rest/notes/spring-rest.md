# What is REST?

REST stands for the **RE**presentational **S**tate **T**ransfer.
It provides a mechanism for communication between applications.
In the REST architecture, the client and server are implemented independently and they do not depend on one another.
REST is language independent, so the client and server applications can use different programming languages.
This gives REST applications a lot of flexibility.

![](imgs/rest.svg)

The REST architecture is stateless meaning that the client only manages session state and the server only manages the resource state.
The communication between the client and server is such that every request contains all the information necessary to interpret it without any previous context.

Both the client and server know the communication format and are able to understand the message sent by the other side.
REST calls can be made over HTTP. The client can send HTTP request message to the server where it is processed and an HTTP response is sent back.

![](imgs/request_response.svg)

The request message has three parts:

1. The request line contains the HTTP method like (GET or POST etc.)
2. The request header contains data with additional information about the request.
3. The request body contains the contents of the message, e.g., if it is a POST request, the request body will contain the contents of the entity to be created.

The response message also has three parts:

1. The response line contains the status code for success or redirection etc.
2. The response header contains additional information about the response like the content type and the size of the response. The client can render the data based on the content type so if it is text/html, it is displayed according to the HTML tags and if it is application/json or application/xml, it is processed accordingly.
3. The response body contains the actual message sent in response to the request.

The HTTP methods for CRUD operations are:

1. POST for creating a resource
2. GET for reading a resource
3. PUT for updating a resource
4. DELETE for deleting a resource

![](imgs/http_methods.svg)

# JSON Data Binding

The most commonly used data formats in a REST application are JSON and XML. JSON stands for **J**ava**S**cript **O**bject **N**otation. It is a plain text data format used for exchanging data between applications.

JSON is a collection of name-value pairs, which the application processes as a string. So, instead of using HTML or JSP to send data, it is passed as a String and the application can process and render the data accordingly. JSON is language independent and can be used with any programming language.

## Syntax

```json
{
    "id": "1",
    "fname": "Sara",
    "lname": "Adams",
    "hobbies": [
        "singing",
        "painting"
    ],
    "address": {
        "city": "Adelaide",
        "country": "Australia"
    }
}
```

- A JSON object is defined between curly braces (`{ }`).
- The object consists of members in the form of comma separated name-value pairs.
- The names and values are separated by colon (`:`).
- Names are provided in double quotes and are on the left side of the colon.
- The values are on the right side of the colon.
- If the value is a string, it is written in double quotes.
- JSON also supports arrays written within square brackets (`[ ]`) that contains a comma separated list of values.
- An object can contain nested objects.
- JSON objects can have `null` value.
- Boolean values true and `false` are also allowed.

## Java - JSON data binding

![](imgs/binding.svg)

A Java object (POJO) can be converted into a JSON object and vice versa through a process called data binding. We can read JSON and use it to populate a Java object. In the same manner, we can use a Java object to create JSON.

## Jackson Project

Jackson Project handles data binding between Java and JSON. It also provides support for data binding with XML. Spring framework uses the Jackson project for data binding. The Jackson data binding API is present in the `com.fasterxml.jackson.databind` package.

The following maven dependency adds Jackson support to the project:

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>{version}</version>
</dependency>
```

Jackson handles the conversion between JSON and POJOs by making use of the getter and setter methods of a class. To convert a JSON object to POJO, the setter methods are called. Vice versa, to create a JSON object from a POJO, the getters methods are used. Jackson has access to the getters and setters only, not the private fields of the class.

The figure below illustrates how Jackson converts JSON data to a Java object. It calls the corresponding setter methods to populate the class members. If a setter method matching a JSON property is not found, an exception is thrown.

![](imgs/json_jackson.svg)
Creating a POJO from JSON

The Jackson annotation ``@JsonIgnoreProperties`` can be used to bypass the exception by setting the `IgnoreUnknown` attribute to `true`. This feature is useful when the JSON file contains more properties but we are only interested in a few of them.

---

## `@RestController`
This annotation is an extension of `@Controller` annotation. The `@RestController` annotation has support for REST requests and responses and automatically handles the data binding between the Java POJOs and JSON.

### `@RestController` vs `@Controller`

In Spring MVC and Spring Boot, `@Controller` and `@RestController` are two annotations used to define the behavior of classes that handle web requests. Both annotations are used to mark a class as a web controller, but they have different purposes and use cases:

1. `@Controller`:

    - `@Controller` is a traditional Spring MVC annotation used to mark a class as a web controller.
    - When using `@Controller`, you typically return a view name (e.g., the name of a Thymeleaf, JSP, or other template) from your methods, and the Spring MVC framework resolves the view and renders the HTML content.
    - To return a JSON or other non-HTML response from a method in a `@Controller` class, you need to use the `@ResponseBody` annotation on that method. This tells Spring MVC to skip the view resolution and directly write the returned object as the HTTP response body after converting it to the appropriate format (e.g., JSON).

2. `@RestController`:

    - `@RestController` is a more recent annotation introduced in Spring 4 and is a convenient alternative to `@Controller` when building RESTful APIs.
    - It is a combination of `@Controller` and `@ResponseBody`. When you use `@RestController`, all methods in the class are treated as if they have the `@ResponseBody` annotation applied, which means they will return data directly as the HTTP response body.
    - `@RestController` is used when you want to return JSON, XML, or other non-HTML responses from your controller methods, which is common when building RESTful APIs.

## `@GetMapping`

The client sends an HTTP request to the REST service. The **dispatcher servlet** handles the request and if the request has JSON data, the `HttpMessageConverter` converts it to Java objects. The request is mapped to a controller which calls service layer methods. The service layer delegates the call to repository and returns the data as POJO. The `MessageConverter` converts the data to JSON and it is sent back to the client. The flow of request is shown below:

![](imgs/get.svg)

## `@PathVariable`

Path variables are a way of parameterizing the path or endpoint to accept data. Path variables are written in curly braces. When the client sends a request, it passes a value in place of the path variable.

`@PathVariable` is an annotation in Spring MVC and Spring Boot that allows you to extract values from the URI path of an incoming HTTP request and bind them to method parameters in your controller. This is a common technique used in RESTful APIs, where the path of a resource is used to convey information about the requested resource.

Here's an example of how to use `@PathVariable`:

1. Create a simple `Book` class:

```Java
public class Book {
    private long id;
    private String title;
    private String author;

    public Book(long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // Getters and setters
    // ...
}
```

2. Create a `BookController` class and use `@PathVariable` to extract the `id` value from the request URI:

```Java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable("id") long id) {
        // For simplicity, we return a dummy book based on the id
        return new Book(id, "Example Book Title", "Example Author");
    }
}
```

In this example, the `BookController` class is annotated with `@RestController`, indicating that it's a RESTful API controller. The `getBookById()` method is responsible for handling the `/books/{id}` endpoint, where `{id}` is a placeholder for the book's ID. The `@PathVariable("id")` annotation is used to bind the value of the `id` path variable to the `id` parameter of the method.

Now, when you run the application and make a GET request to a URI like `/books/42`, the server will extract the value 42 from the path and pass it to the `getBookById()` method. The method will then return a `Book` object with the specified ID:

```json
{
  "id": 42,
  "title": "Example Book Title",
  "author": "Example Author"
}
```

Using `@PathVariable` makes it easy to create flexible, self-descriptive APIs that follow the principles of REST.

## `@PostMapping`

`@PostMapping` is an annotation in Spring MVC and Spring Boot used to handle HTTP POST requests. It's a shorthand for `@RequestMapping(method = RequestMethod.POST)`. You can use `@PostMapping` to create an endpoint in your controller that accepts POST requests and processes the incoming data.

## `save` method

The `JpaRepository` interface inherits a method from the `CrudRepository` called `save`. This method handles both inserts and updates. To distinguish between an ***INSERT*** and ***UPDATE*** operation, the `save` method checks the primary key of the object that is being passed to it. If the primary key is **empty** or `null`, an ***INSERT*** operation is performed, otherwise an ***UPDATE*** to an existing record is performed.

## `@RequestBody`

`@RequestBody` is an annotation in Spring MVC and Spring Boot that is used to bind the body of an incoming HTTP request to a method parameter in your controller. It is typically used in combination with `@PostMapping`, `@PutMapping`, or other HTTP request mapping annotations to handle requests that contain a payload, such as JSON or XML data.

When a request with a payload is received, Spring uses an appropriate `HttpMessageConverter` to convert the payload data into a Java object based on the content type of the request (e.g., `application/json` or `application/xml`). The `@RequestBody` annotation tells Spring to bind the deserialized object to the annotated method parameter.

## How data gets mapped to java object from HTTP request?

When an HTTP request is received by a Spring Boot application, the request data is mapped to a Java object using `HttpMessageConverter` instances. These converters are responsible for converting the request body data (e.g., JSON or XML) to a Java object and vice versa. Spring Boot automatically configures a set of default converters based on the libraries available in the classpath.

Here's an overview of how the data gets mapped from an HTTP request to a Java object in Spring Boot:

1. The application receives an HTTP request with a payload (e.g., JSON or XML).

2. The request is processed by the Spring Boot `DispatcherServlet`, which identifies the appropriate controller method to handle the request based on the request mapping annotations (`@GetMapping`, `@PostMapping`, etc.).

3. If the controller method has a parameter annotated with `@RequestBody`, Spring looks for a suitable `HttpMessageConverter` to convert the payload data into a Java object.

4. The `Content-Type` header in the HTTP request is used to determine the payload's format (e.g., `application/json` or `application/xml`). Spring then selects a matching `HttpMessageConverter` based on the content type.

5. The selected `HttpMessageConverter` reads the request body, deserializes the data, and creates a Java object. Some common converters used by Spring Boot include:

    1. `MappingJackson2HttpMessageConverter` for JSON, which uses the Jackson library for data binding.
    2. `Jaxb2RootElementHttpMessageConverter` for XML, which uses JAXB for data binding.

6. The Java object created by the `HttpMessageConverter` is then passed as an argument to the controller method.

The controller method processes the Java object and generates a response, which is then serialized back into the appropriate format (e.g., JSON or XML) using the corresponding `HttpMessageConverter` before being sent as the HTTP response.

In summary, the data mapping process in Spring Boot relies on `HttpMessageConverter` instances to convert the payload data between the HTTP request and Java objects. The selection of the appropriate converter is based on the request's Content-Type header and the availability of data binding libraries in the application's classpath.

## `@PutMapping`

`@PutMapping` is an annotation in the Spring Framework, specifically in Spring Web MVC, that is used to map HTTP PUT requests to specific handler methods in your controller classes. It is a composed annotation that acts as a shortcut for `@RequestMapping(method = RequestMethod.PUT)`.

The `@PutMapping` annotation is used to define a handler method for updating resources in RESTful web services. When you annotate a method with `@PutMapping`, Spring will route incoming HTTP PUT requests to that method, based on the specified URI pattern.

## `@PatchMapping`

`@PatchMapping` is an annotation in the Spring Framework, specifically in Spring Web MVC, used to map HTTP PATCH requests to specific handler methods in your controller classes. It is a composed annotation that acts as a shortcut for `@RequestMapping(method = RequestMethod.PATCH)`.

The `@PatchMapping` annotation is used to define a handler method for partially updating resources in RESTful web services. When you annotate a method with `@PatchMapping`, Spring will route incoming HTTP PATCH requests to that method based on the specified URI pattern.

## `@PutMapping` vs `@PatchMapping`

Both `@PutMapping` and `@PatchMapping` are annotations in the Spring Web MVC framework used to handle HTTP requests for updating resources in a RESTful web service. They have different use cases and semantics when it comes to updating resources:

1. `@PutMapping`:

- Maps to the HTTP PUT method.
- Used for a complete update of a resource, i.e., replacing the entire resource with new data.
- The client must send all the fields of the resource in the request payload, even if only a few fields need to be updated.
- The server replaces the current resource with the new data provided in the request payload.
- If the resource does not exist, the server may create a new resource with the provided data, depending on the implementation.
- Idempotent: making the same PUT request multiple times will have the same result as making it once.

2. `@PatchMapping`:

- Maps to the HTTP PATCH method.
- Used for a partial update of a resource, i.e., modifying only specific fields of the resource.
- The client sends only the fields that need to be updated in the request payload.
- The server applies the changes provided in the request payload to the existing resource.
- If the resource does not exist, the server should return an error, as PATCH is not meant for creating new resources.
- Not idempotent: making the same PATCH request multiple times may have different results, depending on the fields being updated.

In summary, use `@PutMapping` when you want to update an entire resource by replacing it with new data, and use `@PatchMapping` when you want to perform a partial update by modifying specific fields of a resource.

---

While PATCH is generally not idempotent, there are cases when it can be. The idempotency of a PATCH request depends on how the server processes the request and the type of changes being made. Let's look at an example where PATCH is not idempotent.

Consider a simple RESTful web service that manages bank account transactions. A `Transaction` resource has an `amount` field that represents the transaction amount. We provide an endpoint to increment the transaction amount by a specified value using a PATCH request:

```json
PATCH /transactions/1
{
    "incrementAmount": 10
}
```

Here's the implementation of the `incrementTransactionAmount` method in the controller:

```java
@RestController
public class TransactionController {

    // Other methods...

    @PatchMapping("/transactions/{id}")
    public Transaction incrementTransactionAmount(@PathVariable Long id, @RequestBody TransactionUpdateRequest updateRequest) {
        // Implement the logic for incrementing the transaction amount by the specified value.
        // For example, increment the 'amount' field of the transaction in the database by the 'incrementAmount' value from the updateRequest object.
        Transaction transaction = incrementTransactionAmountInDatabase(id, updateRequest.getIncrementAmount());
        return transaction;
    }
}
```

Now, suppose the current transaction amount is 100. If we send the PATCH request mentioned above, the server increments the transaction amount by 10, making it 110. If we send the same PATCH request again, the server will increment the transaction amount by 10 again, making it 120.

As you can see, sending the same PATCH request multiple times leads to different results, which means it is not idempotent in this case. If we were using a PUT request to set the transaction amount explicitly, it would be idempotent, as sending the same PUT request multiple times would always result in the same transaction amount.

## `@Modifying`

`@Modifying` is an annotation used in Spring Data JPA to indicate that a query method in a repository is meant to perform modifications on the underlying data, such as updates or deletes. By default, Spring Data JPA query methods are assumed to be read-only, and they don't modify any data in the database. The `@Modifying` annotation informs Spring Data JPA that the query method will change the state of the data, and thus, it should clear the underlying persistence context after executing the query to avoid inconsistencies.

Typically, you would use `@Modifying` in combination with the `@Query` annotation, which allows you to provide custom JPQL or SQL queries for your repository methods.

Here's an example of using the `@Modifying` annotation in a Spring Data JPA repository:

```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.active = ?2 WHERE u.id = ?1")
    int updateUserActiveStatus(Long id, boolean active);
}
```

In this example, the `UserRepository` extends the `JpaRepository` interface, which provides basic CRUD operations for the `User` entity. The `updateUserActiveStatus` method is annotated with `@Modifying`, `@Transactional`, and `@Query`. The `@Query` annotation defines a custom JPQL query to update the `active` field of a `User` entity with a specific `id`. The `@Modifying` annotation indicates that this method modifies the data, and the `@Transactional` annotation ensures that the method is executed within a transaction to maintain data integrity.

When the `updateUserActiveStatus` method is called, it will execute the custom JPQL query and update the `active` status of the specified user in the database.

## `@Transactional`

`@Transactional` is an annotation provided by the Spring Framework that is used to declaratively manage transactions in your application. When you annotate a method or a class with `@Transactional`, Spring ensures that any database operations executed within the method's scope are part of a single transaction. This helps maintain data integrity and consistency by either committing all the changes together if everything is successful, or rolling back the changes if any error occurs.

Here's how `@Transactional` works under the hood:

1. When a method annotated with `@Transactional` is called, Spring checks if there is an ongoing transaction.
2. If there is no ongoing transaction, Spring starts a new transaction before executing the method.
3. If there is an ongoing transaction, Spring will join the existing transaction, depending on the propagation behavior defined in the `@Transactional` annotation.
4. Spring executes the method, and any database operations performed within the method are part of the transaction.
5. If the method completes without any exceptions, Spring commits the transaction, making all the changes permanent in the database.
6. If an exception occurs during the method execution, Spring rolls back the transaction, undoing any changes made within the transaction's scope.

To enable transaction management in Spring, you need to add the `@EnableTransactionManagement` annotation to your configuration class:

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class AppConfig {
    // ...
}
```

Here's an example of using the `@Transactional` annotation in a service class:

```java
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void updateUserEmail(Long id, String newEmail) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setEmail(newEmail);
        userRepository.save(user);
    }
}
```

In this example, the `updateUserEmail` method is annotated with `@Transactional`. When this method is called, Spring ensures that both the `findById` and `save` operations are executed within the same transaction. If any exception occurs during these operations, the transaction will be rolled back, and the database state will remain unchanged. If the method completes successfully, the transaction will be committed, and the user's email will be updated in the database.

### What is/are the propagation behavior defined in the `@Transactional` annotation?

The propagation behavior in the `@Transactional` annotation determines how transactions should be managed when a transactional method is called from another transactional method. The propagation behavior is defined using the propagation attribute of the `@Transactional` annotation, and its possible values are from the Propagation enumeration.

#### Here are the different propagation behaviors:

1. **REQUIRED** (default): If there is an ongoing transaction, the current method will join that transaction. If there is no ongoing transaction, a new one will be started. This is the most common propagation behavior.

```java
@Transactional(propagation = Propagation.REQUIRED)
public void someMethod() {
    // ...
}
```

2. **SUPPORTS**: If there is an ongoing transaction, the current method will join that transaction. If there is no ongoing transaction, the method will be executed without a transaction. It's useful when the method can work with or without a transaction.

```java
@Transactional(propagation = Propagation.SUPPORTS)
public void someMethod() {
    // ...
}
```

3. **MANDATORY**: The current method must be executed within an ongoing transaction. If there is no ongoing transaction, an exception will be thrown. This ensures that a transaction must be started before calling this method.

```java
@Transactional(propagation = Propagation.MANDATORY)
public void someMethod() {
    // ...
}
```

4. **REQUIRES_NEW**: A new transaction will always be started for the current method. If there is an ongoing transaction, it will be suspended and resumed after the new transaction completes. This ensures that the method always runs in a separate transaction.

```java
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void someMethod() {
    // ...
}
```

5. **NOT_SUPPORTED**: The current method must be executed without a transaction. If there is an ongoing transaction, it will be suspended and resumed after the method completes. This is useful when you want to execute a piece of code outside of a transaction.

```java
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public void someMethod() {
    // ...
}
```

6. **NEVER**: The current method must be executed without a transaction. If there is an ongoing transaction, an exception will be thrown. This ensures that a transaction must not be active when calling this method.

```java
@Transactional(propagation = Propagation.NEVER)
public void someMethod() {
    // ...
}
```

7. **NESTED**: If there is an ongoing transaction, the current method will be executed within a nested transaction. If there is no ongoing transaction, a new one will be started. Nested transactions are a part of the outer transaction but can be committed or rolled back independently. If the outer transaction is rolled back, all the nested transactions will be rolled back as well. Nested transactions are not supported by all transaction managers.

```java
@Transactional(propagation = Propagation.NESTED)
public void someMethod() {
    // ...
}
```

### Please provide an example for REQUIRED propogation level.

Here's an example that demonstrates the `REQUIRED` propagation level with a simple banking application that transfers money between two accounts. We'll create two services - `AccountService` and `BankingService`. The `AccountService` will have methods to debit and credit accounts, and the `BankingService` will have a method to transfer money between accounts. We'll use the `REQUIRED` propagation level to ensure that the money transfer operation is atomic.

`AccountService.java`:

```java
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    // Assume this is a JpaRepository
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void debit(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void credit(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }
}
```

`BankingService.java`:

```java
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankingService {

    private final AccountService accountService;

    public BankingService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void transferMoney(Long fromAccountId, Long toAccountId, double amount) {
        accountService.debit(fromAccountId, amount);
        accountService.credit(toAccountId, amount);
    }
}
```

In this example, we have `debit` and `credit` methods in the `AccountService` class, and both are annotated with `@Transactional(propagation = Propagation.REQUIRED)`. The `transferMoney` method in the `BankingService` class is also annotated with `@Transactional(propagation = Propagation.REQUIRED)`.

When we call the `transferMoney` method, it will start a new transaction because there's no existing transaction. Then, it calls the `debit` and `credit` methods from the `AccountService`. Since both of these methods have the `REQUIRED` propagation level, they will join the existing transaction started by the `transferMoney` method.

If the `debit` and `credit` operations are successful, the transaction will commit, and the changes will be saved to the database. If any exception occurs during these operations, the transaction will roll back, and none of the changes will be saved. This ensures that the money transfer operation is atomic and consistent.

`@DeleteMapping`

`@DeleteMapping` is an annotation in the Spring Web MVC framework, used to map HTTP DELETE requests to specific handler methods in your controller classes. It is a composed annotation that acts as a shortcut for `@RequestMapping(method = RequestMethod.DELETE)`.

The `@DeleteMapping` annotation is used to define a handler method for deleting resources in RESTful web services. When you annotate a method with `@DeleteMapping`, Spring will route incoming HTTP DELETE requests to that method based on the specified URI pattern.

The HTTP DELETE request deletes a record. The primary key of the record to be deleted can be sent as part of the request URI or the record itself can be sent as part of the request body.

`JpaRepository` inherits two methods of the `CrudRepository` for deleting a record. One is the delete method which takes an entity to be deleted, and the other is `deleteById` which takes the primary key of the entity to be deleted. They both have the same function, and internally the `deleteById` method calls the `delete` method:

```java
void deleteById(ID id) {
    delete(findById(id));
}
```

The difference lies in the way both methods respond when the entity to be deleted is not found. The `deleteById` method throws the `EmptyResultDataAccessException` while delete throws a `NoSuchElementException`.

# Exception Handling

## @ControllerAdvice

A best practice in exception handling, is to have centralized exception handlers that can be used by all controllers in the REST API. Since exception handling is a cross cutting concern, Spring provides the `@ControllerAdvice` annotation. This annotation intercepts requests going to the controller and responses coming from controllers.

![](imgs/exception_handling.svg)

The `@ControllerAdvice` annotation can be used as an interceptor of exceptions thrown by methods annotated with `@RequestMapping` or any of its shortcut annotations.

## `@ExceptonHandler`
The `@ExceptonHandler` annotation on a method, marks it as a method that will handle exceptions. Spring automatically checks all methods marked with this annotation when an exception is thrown. If it finds a method whose input type matches the exception thrown, the method will be executed.

# ResponseEntity

`ResponseEntity` is a class in the Spring Web MVC framework that represents an HTTP response, including the HTTP status code, headers, and body. It is used to build a complete HTTP response with more control over the returned content compared to simply returning an object or a `@ResponseBody` from a controller method.

`ResponseEntity` is a generic class, where the type parameter represents the type of the response body. You can create a `ResponseEntity` instance using its constructors or the static factory methods provided by the class, such as `ResponseEntity.ok()`, `ResponseEntity.notFound()`, etc.

```java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        // Implement the logic for retrieving the user with the specified 'id'.
        // For example, fetch the user from the database.
        User user = getUserFromDatabase(id);

        if (user == null) {
            // Return a 404 Not Found response if the user is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Return a 200 OK response with the user object as the response body
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
```

In this example, the `getUser` method returns a `ResponseEntity<User>` object. If the specified user is found, a `ResponseEntity` with a `200 OK` status and the user object as the response body is returned. If the user is not found, a `ResponseEntity` with a `404 Not Found` status is returned. This provides more control over the HTTP response compared to simply returning a `User` object from the method.

You can also use `ResponseEntity` to set custom headers, modify the content type, or perform other modifications to the response before sending it to the client.

The `ResponseEntity` class in Spring Web MVC provides several constructors to create a new instance with various combinations of status codes, headers, and body content. Here are the most commonly used constructors:

1. `ResponseEntity(HttpStatus status)`: Creates a new `ResponseEntity` with the given HTTP status code and an empty body.

```java
ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
```

2. `ResponseEntity(T body, HttpStatus status)`: Creates a new `ResponseEntity` with the given body content and HTTP status code.

```java
User user = new User("John", "Doe");
ResponseEntity<User> response = new ResponseEntity<>(user, HttpStatus.OK);
```

3. `ResponseEntity(MultiValueMap<String, String> headers, HttpStatus status)`: Creates a new `ResponseEntity` with the given headers and HTTP status code.

```java
MultiValueMap<String, String> headers = new HttpHeaders();
headers.add("Custom-Header", "customHeaderValue");
ResponseEntity<String> response = new ResponseEntity<>(headers, HttpStatus.OK);
```

4. `ResponseEntity(T body, MultiValueMap<String, String> headers, HttpStatus status)`: Creates a new `ResponseEntity` with the given body content, headers, and HTTP status code.

```java
User user = new User("John", "Doe");
MultiValueMap<String, String> headers = new HttpHeaders();
headers.add("Custom-Header", "customHeaderValue");
ResponseEntity<User> response = new ResponseEntity<>(user, headers, HttpStatus.OK);
```

These constructors give you the flexibility to create `ResponseEntity` instances with different combinations of status codes, headers, and body content. You can choose the constructor that best suits your needs for a specific response.