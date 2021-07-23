# The Expense Reimbursement System (ERS)

## Project Description

The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and past history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

## Technologies Used

* HTML, CSS, JavaScript
* Java Servlet
* AWS-RDS through Hibernate
* JUnit

## Features

* User can change their password
* User can update their profile
* Manager can approve/deny requests

To-do list:
* Better home page
* Better footer component to display company missions

## Getting Started

```bash
  $ git clone https://github.com/JW406/Expense-Reimbursement-System-ERS.git
  $ export rds_url=YOUR_RDS_URL
  $ export rds_username=YOUR_RDS_USERNAME
  $ export rds_pwd=YOUR_RDS_PWD
  $ cd Expense-Reimbursement-System-ERS
  $ mvn package -f ./pom.xml && mv target/*.war target/ROOT.war
  $ unzip ./target/ROOT.war -d $CATALINA_BASE/webapps/ROOT
  $ sh $CATALINA_BASE/bin/startup.sh
```

## Usage

![image](https://i.ibb.co/qBNbVVD/Screenshot-54.jpg)
![image](https://i.ibb.co/ZXZxcr7/Screenshot-55.jpg)
![image](https://i.ibb.co/Z1RLCDw/Screenshot-56.jpg)
![image](https://i.ibb.co/4Kn4Ssp/Screenshot-57.jpg)

## License

This project uses the following license: [MIT](https://opensource.org/licenses/MIT)
