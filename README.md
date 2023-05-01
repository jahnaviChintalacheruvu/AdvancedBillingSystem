# AdvancedBillingSystem

The Advanced Billing System is a software application designed to help businesses monitor and sustain their inventories, enabling them to fulfill both customer demand and supply chain requirements.

## Softwares Used

- Intellij IDE
- mySQL

## Built With

This project has been built using the following technologies:

- Java
- Jsp
- Spring Boot
- Bootstrap

## MVC Architecture
The Advanced Billing System follows the MVC (Model-View-Controller) architecture, which is a widely used design pattern for building scalable and maintainable software applications. In this architecture, the application is divided into three main components:

# Model: 
Represents the business logic and data of the application. In this project, the model includes classes such as Employee, Bill, Customer, Shift, etc.

# View: 
Represents the user interface of the application. In this project, the view includes JSP files and HTML templates that are rendered using Thymeleaf.

# Controller: 
Acts as an intermediary between the model and the view, handling user requests and updating the model accordingly. In this project, the controller includes classes such as EmployeeController, BillController, CustomerController, ShiftController, etc.

By separating the application into these three components, we can easily make changes to one component without affecting the others. This makes the application more modular and easier to maintain over time.

## APIs Used

The following APIs are used in this project:

| API Endpoint          | Description                                        |
| ---------------------| -------------------------------------------------- |
| GET /actuator         | Returns health and metrics information for the application |
| GET /actuator/mappings| Returns a list of all the HTTP endpoints in the application |
| GET /employee         | Returns information about employees                |
| POST /updateInventory | Updates inventory information                       |
| [/error]              | Handles errors in the application                  |
| POST /deleteProfile/{id} | Deletes a user profile                           |
| GET /showbills        | Displays all bills for the user                    |
| POST /searchBills     | Searches for bills by a given criterion            |
| GET /shifts           | Returns information about shifts                   |
| GET /register         | Registers a new user                                |
| POST /updateEmployee  | Updates employee information                        |
| [/error] (produces [text/html]) | Handles errors in the application (for HTML requests) |
| POST /saveNewPassword | Saves a new password for a user                     |
| [/destroy]           | Destroys a session                                  |
| POST /searchCustomer  | Searches for customer information by a given criterion |
| GET /editEmployee/{id}| Edits employee information                          |
| GET /rewards          | Displays rewards information for the user           |
| GET /batchClose       | Closes a batch of transactions                      |
| POST /saveCustomer    | Saves customer information                          |
| POST /searchEmployee  | Searches for employee information by a given criterion |
| GET /                 | Displays the homepage                               |
| POST /deleteShift/{id}| Deletes a shift for a given ID                      |
| POST /saveAdmin       | Saves administrator information                     |

## Installation

1. Install Intellij IDEA and mySQL.
2. Clone the repository.
3. Import the project into Intellij IDEA.
4. Run the application.
5. Make sure you have properly configured the host and port details in the application.prop file

## Conclusion and Discussion
we faced many challenges with the roles.there are three roles we faced issues in maintaining the roles.and also we faced issue with the grids.at the we faced the issue 
with maintaing the data.where it was not reflecting in the screens

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

We would like to thank the following individuals and organizations for their contributions to this project:

- Open Source Initiative
- Spring Boot community
- Thymeleaf community
- Bootstrap community
