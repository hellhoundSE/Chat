package com.ruslan.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruslan.dao.UserDAO;
import com.ruslan.model.User;

import lombok.Data;
import lombok.Getter;

@Data
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;
	
	@Getter
	List<String> activeUsers;
	
	@PostConstruct
	public void init() {
		activeUsers = new ArrayList<String>();
	}
	
	@Override
	@Transactional
	public void saveUser(User user) {
		userDAO.saveUser(user);

	}

	@Override
	@Transactional
	public User getUserById(int id) {
		return userDAO.getUserById(id);
	}

	@Override
	@Transactional
	public void deleteUserById(int id) {
		userDAO.deleteUserById(id);
	}

	@Override
	@Transactional
	public User findUserByName(String name) {
		return userDAO.findUserByName(name);
	}

	@Override
	@Transactional
	public List<User> findAll() {
		return userDAO.findAll();
	}

}
