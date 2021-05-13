package ca.sheriancollege.ghimirsh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import ca.sheriancollege.ghimirsh.bean.User;

public interface UserRepository extends JpaRepository<User,Long> {
	//@Query("from User where user_name =:user_name")
	//public User getUserByUserName(@Param("user_name") String user_name);
	
	//public User findByUserName(String userName);

	public User findByUserName(String userName);
} 
