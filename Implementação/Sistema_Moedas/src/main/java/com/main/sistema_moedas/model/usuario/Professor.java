package com.main.sistema_moedas.model.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "professores")
public class Professor extends Usuario {

	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String cpf;
	private String departamento;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

}
