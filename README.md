# jfms
java fast messaging service

### authentication microservice
port number is `6070`.

| Method | URL | Body | Header | Return value | description |
| --- | --- | --- | --- | --- |
| `POST` | `aaa/user/register` | {`mobileNumber`, `firstName`, `lastName`, `activationCodeLength`} | --- |--- | register a new user in system and send him an `activation code` via sms |
| `POST` | `aaa/user/activate` | {`activationCode`, `mobileNumber`} | --- | {`token`} | return a jwt token to user |
| `POST` | `aaa/user/activation/code` | {`mobileNumber`, `activationCodeLength`} | --- | --- | send an `activation code` to user vis sms |
| `GET` | `aaa/user/reactivate` | --- | `auth` | {`token`} | return a jwt token to user |
