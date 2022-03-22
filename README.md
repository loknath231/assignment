# assignment
This is for first assignment - Interest calculation application
1. Install basic plugin lombok and devtool
2. you can check read db using below details
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto = update
3. To Open Account -> 
  URL - http://localhost:8080/api/v1/account/open
  body -> {
    "bsb": 182182,
    "identification": 111222333,
    "openingDate" : "2021-09-13"
}

resonse - 200 OK

4. To save transaction and calculate daily interest. 
  URL - http://localhost:8080/api/v1/account/save 
    Body -> {
    "balanceDate" : "2021-09-19",
    "transaction":[
        {
        "bsb": 182182,
        "identification": 111222333,
        "balance": 123.34
        },
        {
        "bsb": 182182,
        "identification": 222000111,
        "balance": 12.34
        },
        {
        "bsb": 182182,
        "identification": 222000999,
        "balance": 0.00
        }
    ]
}

  resonse -> {
    "bsb": 182182,
    "identification": 111222333,
    "openingDate": "2021-09-19",
    "balance": 135.68,
    "interestAmount": 6.784000000000001,
    "totalBal": 142.464
}

5. To delete Account - 
  URL - http://localhost:8080/api/v1/account/delete/182182
6. To get monthly interest account wise 
  URL - http://localhost:8080/api/v1/account/monthly/year/2021/month/09
  



