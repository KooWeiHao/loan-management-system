# Loan Management System
> _A project built with [React], [Spring Boot], [PostgreSQL], [Docker] & [Fly.io]_.
> <br/>
> _Serving static React Web with Spring Boot_.

## Tech Stack
| Subject                                              | Description                                                          |
|------------------------------------------------------|----------------------------------------------------------------------|
| Client Application (loan-management-system-frontend) | React, Typescript, Zustand (State Management), Bootstrap, Linting    |
| Server Application (loan-management-system-backend)  | Spring Boot(2.7.10), Java 11, Maven, Spring Security, Flyway, Docker |
| Database                                             | PostgreSQL                                                           |
| Cloud Platform                                       | Fly.io                                                               |

## Links
| Subject            | Link                                                           |
|--------------------|----------------------------------------------------------------|
| Fly.io App         | https://eric-koo-lms.fly.dev/                                  |
| APIs Documentation | swagger (https://eric-koo-lms.fly.dev/swagger-ui/), postman (https://api.postman.com/collections/15877926-1e5f56f7-9e0a-45e1-9eb9-48e20eba7ae0?access_key=PMAT-01GWRGSJKFP7W3CZ2TH4KPVDWS) |

### Implementation / Business Logic
- To be honest, my knowledge about the business logic of a loan application is limited. Hence, the current implementation are not aligned with the practical application in the real world. For example, I did not include an interest rate in my credit facility because I am not sure of its relation to loan interest rates.
- There are two roles in Loan Management System
  - A Bank Staff (Back office user) 
    - There's only one bank staff, with the credentials {Username: bank-staff; Password: bank-staff-password}
    - to approve the account opening of a new applicant. Credit facility of a new applicant is created automatically upon approval from bank staff
    - to approve loan application
    - to review all applicants & loans
    - to configure credit limit & interest rate (incomplete implementation). A default credit limit & interest rate will be applied, if there's no credit limit & interest rate configured in back office.
  - An Applicant
    - to open a new account
    - to apply for a loan 
    - to make payment for the loan
    - to check all the applied loans & payment histories
- Technical parts
  - Database design - All the table schemas can be found in the following directory path: "/loan-management-system-backend/src/main/resources/db/migration".
  - Basic authentication is implemented using spring-security.
  - Unit testing has not been implemented due to time limitations. 