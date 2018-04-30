package com.jfms.offline_message.api;

import com.jfms.offline_message.OfflineMessageApi;
import com.jfms.offline_message.model.OfflineMessage;
import com.jfms.offline_message.service.ReceiveService;
import com.jfms.offline_message.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
        sendService.send(offlineMessage.getOwner(), offlineMessage);

    }

//    @RequestMapping(value = "/consume/{messageOwner}", method = RequestMethod.GET)
    public @ResponseBody List<String> consumeMessage(@PathVariable("messageOwner") String messageOwner){
            List<String> messageList = receiveService.receive(messageOwner);
        return messageList;
    }
}
