package com.main.sistema_moedas.model.usuario;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.main.sistema_moedas.model.Conta;

@Entity
public class Aluno extends Usuario {

	private static final long serialVersionUID = 1L;

	private String cpf;
	private String rg;
	@OneToOne
	private Conta conta;

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

}
