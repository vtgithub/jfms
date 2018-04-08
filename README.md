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
| --- | --- | --- |
| {`method`, `userName`} | --- | `JFMSClientLoginMessage` is sending to engine. The Session is adding to the `userSessionMap`.|
| {`method`, `from`, `to`, `body`, `subject`, `sendTime`} | --- | `JFMSClientSendMessage` is sending to engine. `RedisChannelEntity` or {`id`, `from`, `to`, `message`, `subject`, `sendTime`} is creating from `JFMSClientSendMessage` and is adding to redis channel by name [`max(from , to)`+`min(from, to))`] and listener get it from channel, get session by `to` field from `userSessionMap` and send `JFMSServerSendMessage` or {`id`, `from`, `body`, `subject`, `sendTime`} to the `to` userName.|
| {`method`, `from`, `to`, `body`, `subject`, `editTime`, `id`} | --- | `JFMSClientEditMessage` is sending to engine. message history is updating previouse value of message by the `id`, convert it to `JFMSServerEditMessage` and send it to the `to` fileld |
