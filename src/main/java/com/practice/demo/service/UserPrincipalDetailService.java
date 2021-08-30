package com.practice.demo.service;

import java.nio.file.attribute.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.demo.modal.User;
import com.practice.demo.repository.UserRepository;
import com.practice.demo.security.UserPrinciple;

@Service
public class UserPrincipalDetailService implements UserDetailsService {
	
	@Autowired
	  private UserRepository userRepository;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserPrinciple userprinciple=null;
		
			User user=this.userRepository.findByusername(username);
			if(user!=null) {
			    userprinciple=new UserPrinciple(user);

			}else {
				throw new UsernameNotFoundException("username or password incorrect");

			}
		
		return userprinciple;
		
		
			
		
	}

}
