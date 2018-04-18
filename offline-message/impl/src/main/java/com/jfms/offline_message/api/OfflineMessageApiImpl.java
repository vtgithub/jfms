package com.jfms.offline_message.api;

import com.google.gson.Gson;
import com.jfms.offline_message.OfflineMessageApi;
import com.jfms.offline_message.model.OfflineMessage;
import com.jfms.offline_message.service.ReceiveService;
import com.jfms.offline_message.service.SendService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * Created by vahid on 4/16/18.
 */
@Controller
//@RequestMapping(value = "/offline", produces = "application/json", consumes = "application/json")
public class OfflineMessageApiImpl implements OfflineMessageApi{

    @Autowired
    private SendService sendService;
    @Autowired
    private ReceiveService receiveService;

//    @RequestMapping(value = "/produce", method = RequestMethod.POST)
    public @ResponseBody void produceMessage(@RequestBody OfflineMessage offlineMessage){
        sendService.send(offlineMessage.getTo(), offlineMessage);

    }

//    @RequestMapping(value = "/consume/{messageOwner}", method = RequestMethod.GET)
    public @ResponseBody List<OfflineMessage> consumeMessage(@PathVariable("messageOwner") String messageOwner){
            List<OfflineMessage> messageList = receiveService.receive(messageOwner);
        return messageList;
    }
}
