package br.edu.utfpr.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.edu.utfpr.modelo.Curso;
import br.edu.utfpr.modelo.Disciplina;
import br.edu.utfpr.modelo.Matricula;
import br.edu.utfpr.modelo.Professor;

public class MatriculaDAO extends GenericoDAO<Matricula> {
	
	/* Retorna a disciplina de uma determinada matricula */
	public Disciplina consultarDisciplina(Integer id) {
		EntityManager em = getEM();
		Disciplina disciplina;
		
		Query query = em.createNamedQuery("Disciplina.consultarDisciplina");
		query.setParameter("id", id);
		
		disciplina = (Disciplina) query.getSingleResult();
		
		return disciplina;
	}
	
	/* Atualiza os registros */
	public void update(Matricula m, double notaFinal) {
		EntityManager em = getEM();
		
		em.getTransaction().begin();
		
		m = consultarPorId(Matricula.class, m.getId());
		
		m.setNotaFinal(notaFinal);;
		
		em.merge(m);
		em.getTransaction().commit();
	}
}
