package br.com.authspringsecurity.service;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.authspringsecurity.entity.User;
import br.com.authspringsecurity.enumeration.UserRole;

@Service
public class UserDetailService implements UserDetailsService {
	
	public static final String USER_NAME_TEST = "user_name";
	public static final String PASSWORD_TEST = "12345";

	/*@Autowired
	private UserRepository userRepository;*/
	
	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
	
	@Override
	public final User loadUserByUsername(String userName) throws UsernameNotFoundException {
		final User user = /*userRepository.*/findByUserName(userName);
		if(user == null){
			throw new UsernameNotFoundException("user name not found");
		}
		detailsChecker.check(user);
		return user;
	}
	
	
	private User findByUserName(String username) {
		if (username.equals(USER_NAME_TEST)) {
			User user = new User();
			user.setUsername(username);
			user.setPassword(new BCryptPasswordEncoder().encode(PASSWORD_TEST));
			user.grantRole(UserRole.ADMIN);
			return user;
		}
		return null;
	}

}
