package com.example.demo.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Message {

	private static final Logger logger = LoggerFactory.getLogger(Message.class);

	String payload;
	

    public Message(String payload) {
        super();
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
    
}
