package com.jfms.offline_message.api;

import com.google.gson.Gson;
import com.jfms.offline_message.OfflineMessageApi;
import com.jfms.offline_message.model.OfflineMessage;
import com.jfms.offline_message.service.ReceiveService;
import com.jfms.offline_message.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


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
    private Gson gson= new Gson();

//    @RequestMapping(value = "/produce", method = RequestMethod.POST)
    public ResponseEntity produceMessage(@RequestBody OfflineMessage offlineMessage){
        try {
            sendService.send(offlineMessage.getTo(), gson.toJson(offlineMessage));
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            //todo log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @RequestMapping(value = "/consume/{messageOwner}", method = RequestMethod.GET)
    public ResponseEntity consumeMessage(@PathVariable("messageOwner") String messageOwner){
        try {
            List<String> messageList = receiveService.receive(messageOwner);
            return ResponseEntity.status(HttpStatus.OK).body(messageList);
        }catch (Exception e){
            //todo log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
