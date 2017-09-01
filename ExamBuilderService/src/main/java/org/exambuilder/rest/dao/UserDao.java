package org.exambuilder.rest.dao;

import java.util.List;

import org.exambuilder.rest.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="userDao")
@Transactional
public class UserDao extends BaseDao{

	public void saveUser(User user) {
		getSession().persist(user);
	} 
	
	public User findUserByUserName(String userName) {
		Criteria criteria = getSession()
								.createCriteria(User.class)
								.add(Restrictions.eqOrIsNull("username", userName));
		
		List<User> results = criteria.list();
		return results.get(0);
	}
	
}
