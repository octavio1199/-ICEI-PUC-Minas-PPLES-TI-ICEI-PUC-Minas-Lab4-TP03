package com.main.sistema_moedas.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.main.sistema_moedas.model.Endereco;
import com.main.sistema_moedas.model.usuario.Empresa;
import com.main.sistema_moedas.model.usuario.Role;
import com.main.sistema_moedas.model.usuario.Usuario;
import com.main.sistema_moedas.repository.RoleRepository;
import com.main.sistema_moedas.repository.UsuarioRepository;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

	@Autowired
	private RoleRepository rRepository;
	@Autowired
	private UsuarioRepository uRepository;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@PostMapping("/new")
	public String novo(Empresa emp, Endereco e) {

		Role admin = rRepository.findByNameRole("ROLE_ADMIN").orElse(new Role("ROLE_ADMIN"));
		Role empresa = rRepository.findByNameRole("ROLE_EMPRESA").orElse(new Role("ROLE_EMPRESA"));
		List<Role> listaderoles = new ArrayList<>();
		listaderoles.add(admin);
		listaderoles.add(empresa);
		emp.setSenha(encoder.encode(emp.getSenha()));
		emp.setRoles(listaderoles);
		emp.setEndereco(e);
		uRepository.save(emp);
		return ("redirect:/empresa/");
	}

	@GetMapping("")
	public ModelAndView homeEmpresa() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Empresa empresa = (Empresa) auth.getPrincipal();
		ModelAndView mv = new ModelAndView("empresa/empresa");
		mv.addObject("empresa", empresa);
		return mv;
	}

	@GetMapping("/editar")
	public ModelAndView editaEmpresa() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = (Usuario) auth.getPrincipal();
		ModelAndView mv = new ModelAndView("empresa/editar");
		mv.addObject("empresa", ((Empresa) user));
		mv.addObject("end", user.getEndereco());
		return mv;
	}

	@PostMapping("/editar")
	public String updateEmpresa(@Validated Empresa empresa, Endereco end, BindingResult result) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Empresa user = ((Empresa) auth.getPrincipal());
		Long id = user.getId();
		String senha = user.getSenha();
		List<Role> roles = user.getRoles();
		if (result.hasErrors()) {
			return "empresa/editar";
		}
		empresa.setId(id);
		empresa.setSenha(senha);
		empresa.setEndereco(end);
		empresa.setRoles(roles);
		uRepository.save(empresa);
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
