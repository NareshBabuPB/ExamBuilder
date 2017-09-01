package org.exambuilder.rest.component;

import java.util.Arrays;

import org.exambuilder.rest.dao.UserDao;
import org.exambuilder.rest.model.Role;
import org.exambuilder.rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitialContextLoader 
	implements ApplicationListener<ContextRefreshedEvent>{
	
	private boolean initialized = false;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		
		if(initialized)
			return;
		
//		createAdminUser();
		
		initialized = true;
	}

	private void createAdminUser() {
		User user = new User();
		
		user.setUsername("admin");
		user.setEmail("admin@admin.com");
		user.setFirstName("Admin");
		user.setLastName("Admin");
		user.setEnabled(true);
		user.setPassword("admin");
		user.setRoles(Arrays.asList(Role.ADMIN, Role.USER));
		
		userDao.saveUser(user);
	}
}
