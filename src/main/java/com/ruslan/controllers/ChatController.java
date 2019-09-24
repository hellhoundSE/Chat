package com.ruslan.controllers;



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

@Controller
public class ChatController {

	@Autowired
	MainService mainService;
	
	@MessageMapping("/chat.register")
	@SendTo("/topic/public")
	public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) 
	{
		String username = chatMessage.getUser();
		headerAccessor.getSessionAttributes().put("username", chatMessage.getUser());
		
		if (mainService.getUserService().findUserByName(username) == null) {
			mainService.getUserService().saveUser(new User(username));
		}else {
			if(mainService.getUserService().getActiveUsers().contains(username)) {
				return null;
			}
		}
		mainService.getUserService().getActiveUsers().add(username);
		return chatMessage;
	}


	@MessageMapping("/chat.leave")
	@SendTo("/topic/public")
	public ChatMessage leave(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		mainService.getUserService().getActiveUsers().remove(chatMessage.getUser());
		return chatMessage;
	}
	
	@MessageMapping("/users")
	@SendTo("/topic/users")
	public String[] users(SimpMessageHeaderAccessor headerAccessor) {
		return mainService.getUserService().getActiveUsers()
				.toArray(
						new String[mainService.getUserService().getActiveUsers().size()]);
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
