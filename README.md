# jfms
java fast messaging service

### authentication microservice
Method | URL | Body | description
--- | --- | --- | ---
`POST` | `aaa/user/register` | {`mobileNumber`, `firstName`, `lastName`, `activationCodeLength`} | register a new user in system and send him an `activation code` via sms
