# Java Fast Messaging Service (JFMS)
Currently opensource chat engines have major problems. Some of them like matrix uses long polling that consumes almost most battery usage and also after gathering chat info on server side  some of problems has occured because of usage of relational data base. Some others like openfire uses xmpp protocol that causes most network traffic. Since I start JFMS project that uses microservice design with an orchastrator helps us easy distribution. Also I used cassandra nosql database for gathering message history and mongodb nosql database for saving other infoes that both of them can be sharded and distributed easyly. On the other hand I use RedisChannel concept for very fastly online message exchanging and messages brocker (until now kafka) for saving offline messages saving.   

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
| `DELETE` | `aaa/group/{gId}` | --- | --- |  --- | chage status of group info to deleted|

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
| `POST` | `history/p2p/{userId}` | {`messageId`, `sender`, `body`, `subject`, `time`} | --- | --- | saves `HistoryMessage` into p2p |
| `PUT` | `history/p2p/{userId}` | {`messageId`, `sender`, `body`, `subject`, `time`} | --- | --- | change status of seved message to updated and insert a field into `P2PUpdateEntity`. |
| `GET` | `history/p2p/{userId}/{rouster}` | --- | `pageNumber` ,`pageSize` as query params | [{`messageId`, `sender`, `body`, `subject`, `time`} , ...] | returns `pageSize` number of `P2PMessages` from reord multiple  of `pageNumber` and `pageSize` |
| `DELETE` | `history/p2p/{userId}` | [`messageId`, ...] | --- | --- | change status of saved message to deleted. |
| `POST` | `history/group/{groupId}` | {`messageId`, `sender`, `body`, `subject`, `time`} | --- | --- | saves `HistoryMessage` into group |
| `PUT` | `history/group/{groupId}` | {`messageId`, `sender`, `body`, `subject`, `time`} | --- | --- | change status of seved message to updated and insert a field into `GroupUpdateEntity`. |
| `GET` | `history/group/{groupId}` | --- | `pageNumber` ,`pageSize` as query params | [{`messageId`, `sender`, `body`, `subject`, `time`} , ...] | returns `pageSize` number of `HistoryMessages` from reord multiple  of `pageNumber` and `pageSize` |
| `DELETE` | `history/group/{groupId}` | [`messageId`, ...] | --- | --- | change status of saved message to deleted. |

## eureka-server
port number is `9090`

## engine
websocket port number is `4042`.

| type |  body | response | description |
| --- | --- | --- | --- |
| `JFMSClientLoginMessage` | {`method`, `userName`} | [`JFMSServerSendMessage`,...] | `JFMSClientLoginMessage` is sending to engine. The Session is adding to the `userSessionMap`.|
| `JFMSClientSendMessage` | {`method`, `from`, `to`, `body`, `subject`, `sendTime`} | {`id`} | `JFMSClientSendMessage` is sending to engine. `RedisChannelEntity` or {`id`, `from`, `to`, `message`, `subject`, `sendTime`} is creating from `JFMSClientSendMessage` and is adding to redis channel by name [`max(from , to)`+`min(from, to))`] and listener get it from channel, get session by `to` field from `userSessionMap` and send `JFMSServerSendMessage` or {`id`, `from`, `body`, `subject`, `sendTime`} to the `to` userName.|
| `JFMSClientEditMessage` | {`method`, `from`, `to`, `body`, `subject`, `editTime`, `id`} | --- | `JFMSClientEditMessage` is sending to engine. message history is updating previouse value of message by the `id`, convert it to `JFMSServerEditMessage` and send it to the `to` fileld |
| `JFMSClientDeleteMessage` | {`method`, `from` , `id`} | --- | `JFMSClientDeleteMessage` is sending to engine. The message is removing from history , convert it to `JFMSServerDeleteMessage` and send it to the `to` fileld |
| `JFMSClientIsTypingMessage` | {`method`, `from`, `to`} | --- | `JFMSClientIsTypingMessage` is sending to engine. Engine generate `JFMSClientIsTypingMessage` from that and sent it to the `to` field. |
| `JFMSClientPingMessage` | {`method`, `from`} | {`status`:`OK`} | `JFMSClientPingMessage` is sending to engine and engine send back {`status`:`OK`} |
| `JFMSClientConversationLeaveMessage` | {`method`, `from`, `to`, `leaveTime`} | --- | `JFMSClientConversationLeaveMessage` is sending to engine by user leaving conversation. Engine update value of `last_seen_hash` map and send `JFMSServerConversationMessage` to the `to` field. |
| `JFMSClientConversationInMessage` | {`method`, `from`, `to`} | {`from`, `leaveTime`} | `JFMSClientConversationInMessage` is sending to engine by user come into conversation. Engine get leave time of user `to` from `last_seen_hash` map and send back `JFMSServerConversationMessage`. |
| `JFMSClientSeenMessage` | {`method`, `from`, `to`, [`messageIdList`], `seenTime`} | --- | `JFMSClientSeenMessage` is sending to engine, `JFMSServerSeenMessage` generated from it and send to `to` field. | 
| `JFMSClientGroupCreationMessage` | {`method`, `displayName`, `creator`, `jfmsGroupMemberMap`, `type`} | {`groupId`} | create group using aaa microservice and also save it into redis hashset named `group_info` |
| `JFMSClientGroupInfoEditMessage` | {`method`, `id`, `displayName`, `creator`, `jfmsGroupMemberMap`, `type`} | --- | edit group info using aaa microservice and also save it into redis hashset named `group_info` |

* Note: For group send, edit, delete, isTyping, conversationLeave messages just send `JFMSClientSendMessage`, `JFMSClientEditMessage`, `JFMSClientDeleteMessage`, `JFMSClientIsTypingMessage`, `JFMSClientConversationLeaveMessage` with `method` values of `12`,`13`,`14`,`15`,`16` consequently and also place `to`= `groupId` and then other side memberGroups will receive `JFMSServerGroupSendMessage`,`JFMSServerGroupEditMessage`,`JFMSServerGroupDeleteMessage`,`JFMSServerGroupIsTypingMessage`,`JFMSServerGroupConversationMessage` consequently. 
