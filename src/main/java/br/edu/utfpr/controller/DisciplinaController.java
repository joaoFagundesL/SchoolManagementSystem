package br.edu.utfpr.controller;

import br.edu.utfpr.dao.DisciplinaDAO;
import br.edu.utfpr.modelo.Disciplina;

public class DisciplinaController {
	DisciplinaDAO dao = new DisciplinaDAO();
	
	public Disciplina consultarId(Integer id) {
		return dao.consultarPorId(Disciplina.class, id);
	}
}
