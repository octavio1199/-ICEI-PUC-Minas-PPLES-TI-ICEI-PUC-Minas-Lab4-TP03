package com.main.sistema_moedas.controller;

import java.util.ArrayList;
import java.util.List;

import com.main.sistema_moedas.model.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.main.sistema_moedas.model.Conta;
import com.main.sistema_moedas.model.Endereco;
import com.main.sistema_moedas.model.usuario.Aluno;
import com.main.sistema_moedas.model.usuario.Role;
import com.main.sistema_moedas.repository.RoleRepository;
import com.main.sistema_moedas.repository.UsuarioRepository;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

	@Autowired
	private RoleRepository rRepository;
	@Autowired
	private UsuarioRepository uRepository;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@PostMapping("/new")
	public String novo(Aluno a, Endereco e) {
		Role admin = rRepository.findByNameRole("ROLE_ADMIN").orElse(new Role("ROLE_ADMIN"));
		Role aluno = rRepository.findByNameRole("ROLE_ALUNO").orElse(new Role("ROLE_ALUNO"));
		List<Role> listaderoles = new ArrayList<>();
		listaderoles.add(admin);
		listaderoles.add(aluno);
		a.setRoles(listaderoles);
		a.setSenha(encoder.encode(a.getSenha()));
		a.setEndereco(e);
		// a.setConta(new Conta());
		uRepository.save(a);
		return ("redirect:/aluno/");
	}

	@GetMapping("")
	public ModelAndView homeAluno() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Aluno aluno = (Aluno) auth.getPrincipal();
		ModelAndView mv = new ModelAndView("aluno/aluno");
		mv.addObject("aluno", aluno);
		mv.addObject("conta", aluno.getConta());
		return mv;
	}

	@GetMapping("/editar")
	public ModelAndView editaAluno() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = (Usuario) auth.getPrincipal();
		ModelAndView mv = new ModelAndView("aluno/editar");
		mv.addObject("aluno", ((Aluno) user));
		mv.addObject("end", user.getEndereco());
		return mv;
	}

	@PostMapping("/editar")
	public String updateAluno(@Validated Aluno aluno, Endereco end, BindingResult result) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Aluno user = ((Aluno) auth.getPrincipal());
		Long id = user.getId();
		String senha = user.getSenha();
		List<Role> roles = user.getRoles();
		if (result.hasErrors()) {
			return "aluno/editar";
		}
		aluno.setId(id);
		aluno.setSenha(senha);
		aluno.setEndereco(end);
		aluno.setRoles(roles);
		uRepository.save(aluno);
		return "redirect:/login";
	}

	@GetMapping("/deletar")
	public String deletarUsuario() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = (Usuario) auth.getPrincipal();
		uRepository.delete(user);
		auth.setAuthenticated(false);
		return "redirect:/index";
	}
}
