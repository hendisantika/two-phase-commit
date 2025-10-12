# Two-Phase Commit Implementation

A demonstration of the Two-Phase Commit (2PC) protocol using Spring Boot microservices. This project implements a
distributed transaction coordinator that ensures atomicity across multiple services.

## Architecture

This project consists of three microservices:

1. **Coordinator Service** (Port 8080): Orchestrates the two-phase commit protocol
2. **Order Service** (Port 8081): Manages order transactions with H2 database
3. **Payment Service** (Port 8082): Manages payment transactions with H2 database

### Two-Phase Commit Protocol

The implementation follows the standard 2PC protocol:

**Phase 1: Prepare Phase**

- Coordinator sends prepare requests to all participants (Order and Payment services)
- Each participant validates the transaction and responds with success/failure
- If all participants respond successfully, proceed to Phase 2; otherwise, initiate rollback

**Phase 2: Commit Phase**

- If Phase 1 succeeded: Coordinator sends commit requests to all participants
- If Phase 1 failed: Coordinator sends rollback requests to all participants
- Each participant executes the requested action (commit or rollback)

## Prerequisites

- Java 21 or higher
- Maven 3.9+
- curl (for testing)

## Project Structure

```
two-phase-commit/
├── coordinator/       # Coordinator service
│   ├── src/
│   │   └── main/
│   │       ├── java/com/hendisantika/coordinator/
│   │       │   ├── config/
│   │       │   │   └── RestTemplateConfig.java
│   │       │   ├── controller/
│   │       │   │   └── CoordinatorController.java
│   │       │   ├── dto/
│   │       │   │   └── TransactionData.java
│   │       │   └── CoordinatorApplication.java
│   │       └── resources/
│   │           └── application.properties
│   └── pom.xml
├── order/             # Order service
│   ├── src/
│   │   └── main/
│   │       ├── java/com/hendisantika/order/
│   │       │   ├── controller/
│   │       │   │   └── OrderController.java
│   │       │   ├── dto/
│   │       │   │   └── TransactionData.java
│   │       │   ├── entity/
│   │       │   │   ├── Order.java
│   │       │   │   └── OrderPreparationStatus.java
│   │       │   ├── repository/
│   │       │   │   └── OrderRepository.java
│   │       │   └── OrderApplication.java
│   │       └── resources/
│   │           └── application.properties
│   └── pom.xml
└── payment/           # Payment service
    ├── src/
    │   └── main/
    │       ├── java/com/hendisantika/payment/
    │       │   ├── controller/
    │       │   │   └── PaymentController.java
    │       │   ├── dto/
    │       │   │   └── TransactionData.java
    │       │   ├── entity/
    │       │   │   ├── Payment.java
    │       │   │   └── PaymentStatus.java
    │       │   ├── repository/
    │       │   │   └── PaymentRepository.java
    │       │   └── PaymentApplication.java
    │       └── resources/
    │           └── application.properties
    └── pom.xml
```

## Building the Project

Build all three services:

```bash
# Build Coordinator
cd coordinator
mvn clean package -DskipTests
cd ..

# Build Order Service
cd order
mvn clean package -DskipTests
cd ..

# Build Payment Service
cd payment
mvn clean package -DskipTests
cd ..
```

## Running the Services

Start each service in a separate terminal:

```bash
# Terminal 1 - Start Order Service
cd order
java -jar target/order-0.0.1-SNAPSHOT.jar

# Terminal 2 - Start Payment Service
cd payment
java -jar target/payment-0.0.1-SNAPSHOT.jar

# Terminal 3 - Start Coordinator Service
cd coordinator
java -jar target/coordinator-0.0.1-SNAPSHOT.jar
```

Wait for all services to start (you should see "Started [ServiceName]Application" messages).

## API Endpoints

### Coordinator Service (Port 8080)

#### Initiate Two-Phase Commit

```
POST /initiate_2pc
Content-Type: application/json

{
  "orderNumber": "ORD-001",
  "item": "Laptop",
  "price": "1500",
  "paymentMode": "Credit Card"
}
```

**Response:**

- Success: `"Transaction committed successfully."`
- Failure: `"Transaction Rollback"`

### Order Service (Port 8081)

#### Prepare Order

```
POST /prepare_order
Content-Type: application/json
```

#### Commit Order

```
POST /commit_order
Content-Type: application/json
```

#### Rollback Order

```
POST /rollback_order
Content-Type: application/json
```

#### H2 Console

Access the H2 database console at: `http://localhost:8081/h2-console`

- JDBC URL: `jdbc:h2:mem:ordersdb`
- Username: `sa`
- Password: (leave empty)

### Payment Service (Port 8082)

#### Prepare Payment

```
POST /prepare_payment
Content-Type: application/json
```

#### Commit Payment

```
POST /commit_payment
Content-Type: application/json
```

#### Rollback Payment

```
POST /rollback_payment
Content-Type: application/json
```

#### H2 Console

Access the H2 database console at: `http://localhost:8082/h2-console`

- JDBC URL: `jdbc:h2:mem:paymentsdb`
- Username: `sa`
- Password: (leave empty)

## Testing the Application

### Successful Transaction

```bash
curl -X POST http://localhost:8080/initiate_2pc \
  -H "Content-Type: application/json" \
  -d '{
    "orderNumber": "ORD-001",
    "item": "Laptop",
    "price": "1500",
    "paymentMode": "Credit Card"
  }'
```

Expected output: `Transaction committed successfully.`

### Another Successful Transaction

```bash
curl -X POST http://localhost:8080/initiate_2pc \
  -H "Content-Type: application/json" \
  -d '{
    "orderNumber": "ORD-002",
    "item": "Smartphone",
    "price": "800",
    "paymentMode": "Debit Card"
  }'
```

Expected output: `Transaction committed successfully.`

## Verifying Transactions

You can verify the committed transactions by accessing the H2 console for each service:

1. **Order Service**: http://localhost:8081/h2-console
    - Check the `order_details` table
    - Look for records with `preparation_status = 'COMMITTED'`

2. **Payment Service**: http://localhost:8082/h2-console
    - Check the `payment` table
    - Look for records with `preparation_status = 'APPROVED'`

## Transaction States

### Order Service States

- `PREPARING`: Order is in prepare phase
- `COMMITTED`: Order has been committed
- `ROLLBACK`: Order has been rolled back

### Payment Service States

- `PENDING`: Payment is in prepare phase
- `APPROVED`: Payment has been committed
- `ROLLBACK`: Payment has been rolled back

## Technologies Used

- **Spring Boot 3.5.6**: Application framework
- **Spring Data JPA**: Data persistence
- **H2 Database**: In-memory database
- **Lombok**: Code generation for boilerplate code
- **RestTemplate**: HTTP client for inter-service communication
- **Jackson**: JSON serialization/deserialization
- **Maven**: Build tool

## How It Works

1. Client sends a transaction request to the Coordinator
2. Coordinator initiates Phase 1 (Prepare):
    - Sends prepare request to Order Service
    - Sends prepare request to Payment Service
3. Both services validate and save the transaction with "PREPARING"/"PENDING" status
4. If both services respond successfully:
    - Coordinator initiates Phase 2 (Commit)
    - Sends commit request to both services
    - Services update status to "COMMITTED"/"APPROVED"
5. If any service fails:
    - Coordinator initiates Rollback
    - Sends rollback request to both services
    - Services update status to "ROLLBACK"

## Error Handling

The implementation includes error handling for:

- Network failures during prepare phase
- Network failures during commit phase
- Service validation failures
- Invalid transaction data

## Limitations

This is a demonstration project and has some limitations:

- No persistent coordinator log
- No timeout handling
- No support for service recovery
- Simple error handling
- In-memory databases (data is lost on restart)

## Future Enhancements

Potential improvements could include:

- Persistent coordinator log for crash recovery
- Timeout mechanisms
- Retry logic
- More sophisticated error handling
- Integration with message queues (Kafka, RabbitMQ)
- Monitoring and observability
- Distributed tracing

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Author

Created by Hendi Santika

- Email: hendisantika@gmail.com
- Telegram: @hendisantika34
- Link: s.id/hendisantika

## Troubleshooting

### Port Already in Use

If you get "Port already in use" errors:

```bash
# Kill process on specific port
lsof -ti:8080 | xargs kill -9  # For coordinator
lsof -ti:8081 | xargs kill -9  # For order service
lsof -ti:8082 | xargs kill -9  # For payment service
```

### Build Failures

If you encounter Lombok-related compilation errors:

- Ensure Java 21+ is installed
- Clean and rebuild: `mvn clean install -DskipTests`
- Check that annotation processing is enabled in your IDE

### Services Not Communicating

- Verify all three services are running
- Check the logs for each service
- Ensure no firewall is blocking localhost connections
- Verify the correct ports (8080, 8081, 8082)
