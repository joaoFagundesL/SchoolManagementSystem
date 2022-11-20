package br.edu.utfpr.controller;

import java.util.List;

import br.edu.utfpr.dao.CursoDAO;
import br.edu.utfpr.modelo.Curso;

public class CursoController {
	
	CursoDAO dao = new CursoDAO();
	
	public List<Curso> consultarTodos() {

		return dao.consultarTodos();
	}
	
	public Curso consultarPeloNome(String nome) {
		return dao.consultarPeloNome(nome);
	}
	
	public Curso consultarPeloId(Class<Curso> clazz, Integer id) {
		return dao.consultarPorId(clazz, id);
	}
}
