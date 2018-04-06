# jfms
java fast messaging service

## authentication microservice
port number is `6070`.

| Method | URL | Body | Header | Return value | description |
| --- | --- | --- | --- | --- | -- |
| `POST` | `aaa/user/register` | {`mobileNumber`, `firstName`, `lastName`, `activationCodeLength`} | --- | --- | register a new user in system and send him an `activation code` via sms |
| `POST` | `aaa/user/activate` | {`activationCode`, `mobileNumber`} | --- | {`token`} | return a jwt token to user |
| `POST` | `aaa/user/activation/code` | {`mobileNumber`, `activationCodeLength`} | --- | --- | send an `activation code` to user vis sms |
| `GET` | `aaa/user/reactivate` | --- | `auth` | {`token`} | return a jwt token to user |

## engine
websocket port number is `4042`.

| body | response | description |
| {`method`, `userName`} | --- | `JFMSLoginMessage` is sending to engine. The Session is adding to the `userSessionMap`.|
| {`method`, `from`, `to`, `body`, `subject`} | --- | `JFMSSendMessage` is sending to engine. `RedisChannelEntity` or {`from`, `to`, `message`, `subject`} is creating from `JFMSSendMessage` and is adding to redis channel by name [`max(from , to)`+`min(from, to))`] and listener get it from channel, get session by `to` field from `userSessionMap` and send `JFMSRecieveMessage` or {`from`, `body`, `subject`} to the `to` userName.|
