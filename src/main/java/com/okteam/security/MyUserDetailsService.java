package com.okteam.security;

import com.okteam.dao.AdminRepository;
import com.okteam.dao.CtvRepository;
import com.okteam.dao.NccRepository;
import com.okteam.entity.Admin;
import com.okteam.entity.Ctv;
import com.okteam.entity.Ncc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	NccRepository nccdao;

	@Autowired
	AdminRepository addao;

	@Autowired
	CtvRepository ctvdao;

	@Autowired
	PasswordEncoder pe;

	@Override
	public UserDetails loadUserByUsername(String username) {

		var ctv = ctvdao.findById(username);
		if (ctv.isPresent()) {
			Ctv o = ctv.get();
			return User.withUsername(o.getUsername()).password(pe.encode(o.getPassword())).roles("CTV").build();
		}
		var ncc = nccdao.findById(username);
		if (ncc.isPresent()) {
			Ncc o = ncc.get();
			return User.withUsername(o.getUsername()).password(pe.encode(o.getPassword())).roles("NCC").build();
		}
		var admin = addao.findById(username);
		if (admin.isPresent()) {
			Admin o = admin.get();
			return User.withUsername(o.getUsername()).password(pe.encode(o.getPassword())).roles("ADMIN").build();
		}

		throw new UsernameNotFoundException(":( Ngoai le Khong tim thay username");

	}
}
