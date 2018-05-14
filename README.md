# jfms
java fast messaging service

## AAA microservice
port number is `6070`.

| Method | URL | Body | Header | Return value | description |
| --- | --- | --- | --- | --- | -- |
| `POST` | `aaa/user/register` | {`mobileNumber`, `firstName`, `lastName`, `activationCodeLength`} | --- | --- | register a new user in system and send him an `activation code` via sms |
| `POST` | `aaa/user/activate` | {`activationCode`, `mobileNumber`} | --- | {`token`} | return a jwt token to user |
| `POST` | `aaa/user/activation/code` | {`mobileNumber`, `activationCodeLength`} | --- | --- | send an `activation code` to user vis sms |
| `GET` | `aaa/user/reactivate` | --- | `auth` | {`token`} | return a jwt token to user |
| `POST` | `aaa/group` | {`id`, `displayName`, `owner`, `memberList`:[{`userName`,`admin`}, ...]} | --- | `groupId` | register a new group in system and return groupId. |
| `PUT` | `aaa/group` | {`id`, `displayName`, `owner`, `memberList`:[{`userName`,`admin`}, ...]} | --- | --- | update group info. |
| `GET` | `aaa/group/{gId}` | --- | --- |  {`displayName`, `owner`, `memberList`:[{`userName`,`admin`}, ...]} | returns `groupInfo` belongs to the `gId`. |
| `DELETE` | `aaa/group/{gId}` | --- | --- |  --- | delete group info |

## offline-message
port number is `7070`.

| Method | URL | Body | Header | Return value | description |
| --- | --- | --- | --- | --- | -- |
| `POST` | `offline/produce` | {`owner`, `message`} | --- | --- | Saves `OfflineMessage` into message broker |
| `POST` | `offline/consume/{messageOwner}` | --- |  |  [`message`,...]  | returns list of `OfflineMessage` belongs to `messageOwner` |

## message-history
port number is `7080`.

| Method | URL | Body | Header | Return value | description |
| --- | --- | --- | --- | --- | -- |
| `POST` | `history/p2p/{userId}` | {`messageId`, `sender`, `body`, `subject`, `time`} | --- | --- | saves `P2PMessage` |
| `PUT` | `history/p2p/{userId}` | {`messageId`, `sender`, `body`, `subject`, `time`} | --- | --- | change status of seved message to updated and insert a field into `P2PUpdateEntity`. |
| `POST` | `history/p2p/{userId}/{rouster}` | {`pageNumber`, `pageSize`} | --- | [{`messageId`, `sender`, `body`, `subject`, `time`} , ...] | returns `pageSize` number of `P2PMessages` from reord multiple  of `pageNumber` and `pageSize` |
| `DELETE` | `history/p2p/{userId}` | [`messageId`, ...] | --- | --- | change status of saved message to deleted. |

## engine
websocket port number is `4042`.

|  body | response | description |
| --- | --- | --- |
| {`method`, `userName`} | [`JFMSServerSendMessage`,...] | `JFMSClientLoginMessage` is sending to engine. The Session is adding to the `userSessionMap`.|
| {`method`, `from`, `to`, `body`, `subject`, `sendTime`} | {`id`} | `JFMSClientSendMessage` is sending to engine. `RedisChannelEntity` or {`id`, `from`, `to`, `message`, `subject`, `sendTime`} is creating from `JFMSClientSendMessage` and is adding to redis channel by name [`max(from , to)`+`min(from, to))`] and listener get it from channel, get session by `to` field from `userSessionMap` and send `JFMSServerSendMessage` or {`id`, `from`, `body`, `subject`, `sendTime`} to the `to` userName.|
| {`method`, `from`, `to`, `body`, `subject`, `editTime`, `id`} | --- | `JFMSClientEditMessage` is sending to engine. message history is updating previouse value of message by the `id`, convert it to `JFMSServerEditMessage` and send it to the `to` fileld |
| {`method`, `from` , `id`} | --- | `JFMSClientDeleteMessage` is sending to engine. The message is removing from history , convert it to `JFMSServerDeleteMessage` and send it to the `to` fileld |
| {`method`, `from`, `to`} | --- | `JFMSClientIsTypingMessage` is sending to engine. Engine generate `JFMSClientIsTypingMessage` from that and sent it to the `to` field. |
| {`method`, `from`} | {`status`:`OK`} | `JFMSClientPingMessage` is sending to engine and engine send back {`status`:`OK`} |
| {`method`, `from`, `to`, `leaveTime`} | --- | `JFMSClientConversationLeaveMessage` is sending to engine by user leaving conversation. Engine update value of `last_seen_hash` map and send `JFMSServerConversationMessage` to the `to` field. |
| {`method`, `from`, `to`} | {`from`, `leaveTime`} | `JFMSClientConversationInMessage` is sending to engine by user come into conversation. Engine get leave time of user `to` from `last_seen_hash` map and send back `JFMSServerConversationMessage`. |
| {`method`, `from`, `to`, [`messageIdList`], `seenTime`} | --- | `JFMSClientSeenMessage` is sending to engine, `JFMSServerSeenMessage` generated from it and send to `to` field. | 

* Note: `groupSendMessage`, `groupEditMessage`, `groupDeleteMessage`, `groupIsTypingMessage`, `groupLeaveMessage` are just like above p2p messages but by other `method` field values. 
