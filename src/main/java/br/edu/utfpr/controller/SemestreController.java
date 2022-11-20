package br.edu.utfpr.controller;

import java.util.List;

import br.edu.utfpr.dao.SemestreDAO;
import br.edu.utfpr.modelo.Semestre;

public class SemestreController {
	SemestreDAO dao = new SemestreDAO();
	
	public List<Semestre> consultarTodos() {
		return dao.consultarTodos();
	}
	
	public Semestre consultarPorId(Class<Semestre> clazz, Integer id) {
		return dao.consultarPorId(clazz, id);
	}
}
