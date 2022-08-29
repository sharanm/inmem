package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.Message;
import com.example.demo.exception.TopicAlreadyPresentException;
import com.example.demo.exception.TopicNotPresentException;
import com.example.demo.queue.InMemoryQueue;

@RestController
public class QueueController {

	private static final Logger logger = LoggerFactory.getLogger(QueueController.class);
	
	InMemoryQueue queue;
	
	QueueController(InMemoryQueue queue){
	    this.queue = queue;
	}
	
    @PostMapping(
            path = "/createTopic",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    void createTopic(@RequestParam MultiValueMap<String,String> paramMap) throws TopicAlreadyPresentException {
        queue.createTopic(paramMap.get("topic").get(0));
    }
    
    @PostMapping(
            path = "/produce",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
      void produce(@RequestParam MultiValueMap<String,String> paramMap) throws TopicNotPresentException {
          queue.produce(new Message(paramMap.get("message").get(0)) , paramMap.get("topic").get(0));
      }

	  @PostMapping("/consume")
	  String newEmployee(String consumerId, String topic) throws TopicNotPresentException {
	      Message message = queue.consume(consumerId, topic);
	      if(message == null) {
	          return "No new messages to read";
	      }
	    return "CosumerId: " + message.getPayload();
	  }
}
