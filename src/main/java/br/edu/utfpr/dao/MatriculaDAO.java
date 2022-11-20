package br.edu.utfpr.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.edu.utfpr.modelo.Disciplina;
import br.edu.utfpr.modelo.Matricula;

public class MatriculaDAO extends GenericoDAO<Matricula> {
	
	/* Retorna a disciplina de uma determinada matricula */
	public Disciplina consultarDisciplina(Integer id) {
		EntityManager em = getEM();
		Disciplina disciplina;
		
		Query query = em.createNamedQuery("Matricula.consultarDisciplina");
		query.setParameter("id", id);
		
		disciplina = (Disciplina) query.getSingleResult();
		
		return disciplina;
	}
}
