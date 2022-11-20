package br.edu.utfpr.controller;

import java.util.List;

import br.edu.utfpr.dao.AlunoDAO;
import br.edu.utfpr.modelo.Aluno;
import br.edu.utfpr.modelo.Curso;
import br.edu.utfpr.modelo.Semestre;

public class AlunoController {
	AlunoDAO dao = new AlunoDAO();
	
	public void inserir(Aluno aluno) {
		try {
			dao.insertWithQuery(aluno);
		} 
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}	
	
	public boolean remover(Class<Aluno> aluno, Integer id) {

		if(id == null) {
			return false;
		}
		
		else {
			dao.remover(Aluno.class, id);			
		}
		
		return true;
	}
	
	public boolean verificarSemestre(int semestreAluno, int semestreCurso) {

		return semestreAluno > semestreCurso * 2;
	}
	
	public void update(Aluno aluno, String nome, 
			Integer ano, String cpf, String email, Curso curso, 
			Semestre semestre) {
		
		dao.update(aluno, nome, ano, cpf, email, curso, semestre);
	}
	
	public Aluno consultarPeloCpf(Aluno aluno, String cpf) {
		
		aluno = dao.consultarPeloCpf(cpf);
		
		if(aluno == null) {
			return null;
		}
		
		else {
			return aluno;
		}
	}
	
	public List<Aluno> consultarTodos() {
		return dao.consultarTodos();
	}
	
	public Aluno validarLogin(String usuario, String senha) {
		
		Aluno a = dao.consultarPeloUser(usuario);
		
		if(a == null) {
			return null;
		}
		else if(a.getSenha().equals(senha)){
			return a;
		} else {
			return null;			
		}
	}
	
}
