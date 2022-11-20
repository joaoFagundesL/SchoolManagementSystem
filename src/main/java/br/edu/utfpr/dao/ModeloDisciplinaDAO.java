package br.edu.utfpr.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.edu.utfpr.modelo.Disciplina;
import br.edu.utfpr.modelo.ModeloDisciplina;

public class ModeloDisciplinaDAO extends GenericoDAO<ModeloDisciplina> {
	
	/* Retorna todas as disciplinas de um determinado modelo */
	@SuppressWarnings("unchecked")
	public List<Disciplina> consultarDisciplinaModelo(Integer id) {
		EntityManager em = getEM();
		List<Disciplina> disciplinas;
		
		Query query = em.createNamedQuery("ModeloDisciplina.consultarDisciplina");
		query.setParameter("id", id);
		
		disciplinas = query.getResultList();
		
		return disciplinas;
	}
	
	@SuppressWarnings("unchecked")
	public List<ModeloDisciplina> consultarTodos() {
		EntityManager em = getEM();
		List<ModeloDisciplina> modelos;
		
		Query query = em.createNamedQuery("ModeloDisciplina.consultarTodos");

	
		modelos = query.getResultList();
		
		return modelos;
	}
}
