package br.edu.utfpr.controller;

import java.util.List;

import br.edu.utfpr.dao.DisciplinaDAO;
import br.edu.utfpr.dao.ProfessorDAO;
import br.edu.utfpr.modelo.Disciplina;
import br.edu.utfpr.modelo.Professor;

public class ProfessorController {
	
	ProfessorDAO dao = new ProfessorDAO();
	
	public Professor validarLogin(String usuario, String senha) {
		Professor p = dao.consultarUser(usuario);
		
		if(p == null) {
			return null;
		}
		else if(p.getSenha().equals(senha)){
			return p;
		} else {
			return null;			
		}
	}
	
	public Professor consultarPeloId(Integer id) {
		return dao.consultarPorId(Professor.class, id);
	}
	
	public boolean inserirNoBanco(Integer idProf, Integer idDisciplina) {
		try {
			dao.insertWithQueryDisciplinaProfessor(idProf, idDisciplina);
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		
		return true;
	}
}
