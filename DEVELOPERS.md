# User API Dev Guide

## Building
./gradlew build

## Testing
./gradlew clean test


## deploying using docker
./docker-compose up

### HOST
http://<host>:9091
### List of APIs
#### Create User
#####  POST /api/v1/user
###### Request Body
###### {
	"firstName" : "Aravinda",
	"lastName" : "Udupa",
	"email" : "ara91@gmail.com",
	"monthlySalary" : 2245.00,
	"monthlyExpense" : 1200.00
}
#### Get user by email
###### GET /api/v1/user/by-email?email=<email>
#### Get all users
###### GET /api/v1/user/all

#### Create Account
#####  POST /api/v1/account
###### Request Body
###### {
	"userId" : 4,
	"type" : "CURRENT",
	"balance" : 50000
}
#### Get account by user ID
###### GET /api/v1/account/by-user-id?user-id=<userID>
#### Get all account
###### GET /api/v1/account/all

## Additional Information