package com.ruslan.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.ruslan.model.ChatMessage;
import com.ruslan.model.Message;
import com.ruslan.model.User;
import com.ruslan.service.MainService;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

@Controller
public class ChatController {

	@Autowired
	MainService mainService;
	
	List<String> activeUsers;
	
	@PostConstruct
	public void init() {
		activeUsers = new ArrayList<String>();
	}

	@MessageMapping("/chat.register")
	@SendTo("/topic/public")
	public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) 
	{
		
		String username = chatMessage.getUser();
		headerAccessor.getSessionAttributes().put("username", chatMessage.getUser());
		
		if (mainService.getUserService().findUserByName(username) == null) {
			mainService.getUserService().saveUser(new User(username));
		}else {
			if(activeUsers.contains(username)) {
				return null;
			}
		}
		activeUsers.add(username);
		return chatMessage;
	}


	@MessageMapping("/chat.leave")
	@SendTo("/topic/public")
	public ChatMessage leave(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		activeUsers.remove(chatMessage.getUser());
		return chatMessage;
	}
	
	@MessageMapping("/users")
	@SendTo("/topic/users")
	public String[] users(SimpMessageHeaderAccessor headerAccessor) {
		return activeUsers.toArray(new String[activeUsers.size()]);
	}
	
	@MessageMapping("/chat.send")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {

		String content = chatMessage.getContent();
		String username = chatMessage.getUser();

		if (!content.equals("")) {
			
			Message message = new Message(content);
			User user = mainService.getUserService().findUserByName(username);
			user.addMessage(message);
			message.setUser(user);
			mainService.getUserService().saveUser(user);
			
		}
		return chatMessage;
	}
}
