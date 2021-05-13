package ca.sheriancollege.ghimirsh.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.provisioning.UserDetailsManager;

import ca.sheriancollege.ghimirsh.bean.User;
import ca.sheriancollege.ghimirsh.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Fetching user from database
		//User user = userRepository.getUserByUserName(username);
		User user = userRepository.findByUserName(username);
		System.out.println("Testing Log in User: " + user.toString());
		if(user == null) {
			System.out.println("User Not found: " + username);
			throw new UsernameNotFoundException("Could not find user");
		}
		
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		
		return customUserDetails;
	}

}
