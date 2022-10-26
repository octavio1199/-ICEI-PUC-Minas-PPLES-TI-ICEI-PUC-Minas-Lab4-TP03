package com.main.sistema_moedas.config;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.main.sistema_moedas.exception.UserNotFoundException;
import com.main.sistema_moedas.model.usuario.Usuario;
import com.main.sistema_moedas.repository.UsuarioRepository;


@Repository
@Transactional
public class ImplementsUserDetailService implements UserDetailsService {
	
	@Autowired
	UsuarioRepository uRepository;

	@Override
	public Usuario loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<Usuario> user = uRepository.findByEmail(email);
				
		return user.orElseThrow(UserNotFoundException::new);
	}

}
