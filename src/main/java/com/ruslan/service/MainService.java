package com.ruslan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Service
public class MainService{
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserService userService;
	
}
