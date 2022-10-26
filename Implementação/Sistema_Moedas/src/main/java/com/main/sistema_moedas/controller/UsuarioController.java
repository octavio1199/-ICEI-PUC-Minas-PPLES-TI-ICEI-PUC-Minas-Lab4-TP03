package com.main.sistema_moedas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")

public class UsuarioController {
	@GetMapping("/new")
	public String newUsuario() {
		return "usuarios/new";
	}
	

}
