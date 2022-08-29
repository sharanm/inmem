package com.example.demo.queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.example.demo.api.Message;
import com.example.demo.exception.TopicAlreadyPresentException;
import com.example.demo.exception.TopicNotPresentException;

@Component
public class InMemoryQueue {

	private static final Logger logger = LoggerFactory.getLogger(InMemoryQueue.class);
	
	Map<String, Integer> consumerToOffset = new HashMap<String, Integer>();
	Map<String, List<Message>> topicToQueue = new HashMap<>();
	
	public void createTopic(String topic) throws TopicAlreadyPresentException {
        if(topicToQueue.containsKey(topic)) {
            throw new TopicAlreadyPresentException();
        }
        
        topicToQueue.put(topic, new ArrayList<Message>());
    }
	
	public void produce(Message message, String topic) throws TopicNotPresentException {
        if(!topicToQueue.containsKey(topic)) {
            throw new TopicNotPresentException();
        }
        
        List<Message> queue = topicToQueue.get(topic);
        queue.add(message);
	}
	
    public Message consume(String consumerId, String topic) throws TopicNotPresentException {
        
        
        if(!topicToQueue.containsKey(topic)) {
            throw new TopicNotPresentException();
        }
        
        
        List<Message> queue = topicToQueue.get(topic);
        
        int offset = -1;
        if(consumerToOffset.containsKey(consumerId)) {
            offset = consumerToOffset.get(consumerId);
        }
        
        System.out.println("co:" + consumerToOffset);
        System.out.println("off:" + offset);
        System.out.println("queue:" + queue);
        System.out.println("topicToQueue:" + topicToQueue);
        
        if(queue == null || queue.size() == 0 || queue.size() < (offset + 1)) {
            return null;
        }
        
        Message toReturn = queue.get(offset+1);
        consumerToOffset.put(consumerId, offset + 1);
        
        return toReturn;
    }
}














