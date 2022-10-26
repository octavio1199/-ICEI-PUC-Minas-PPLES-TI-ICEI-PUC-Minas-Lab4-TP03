package com.main.sistema_moedas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.main.sistema_moedas.model.usuario.Aluno;
import com.main.sistema_moedas.model.usuario.Empresa;
import com.main.sistema_moedas.model.usuario.Professor;
import com.main.sistema_moedas.model.usuario.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query(value = "SELECT * FROM usuario u WHERE u.dtype = Aluno", nativeQuery = true)
	List<Aluno> findAllAlunos();
	@Query(value = "SELECT * FROM usuario u WHERE u.dtype = Professor", nativeQuery = true)
	List<Professor> findAllProfessores();
	@Query(value = "SELECT * FROM usuario u WHERE u.dtype = Empresa", nativeQuery = true)
	List<Empresa> findAllEmpresas();
	
	Optional<Usuario> findByEmail(String email);
		
}
