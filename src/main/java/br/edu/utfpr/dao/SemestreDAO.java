package br.edu.utfpr.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import br.edu.utfpr.modelo.Disciplina;
import br.edu.utfpr.modelo.Semestre;

public class SemestreDAO extends GenericoDAO<Semestre>{
	@SuppressWarnings("unchecked")
	
	/* Retorna todas as disciplinas de um determinado modelo */
	public List<Disciplina> consultarDisciplinaModelo(Integer id) {
		EntityManager em = getEM();
		List<Disciplina> disciplinas;
		
		Query query = em.createNamedQuery("Semestre.consultarDisciplina");
		query.setParameter("id", id);
		
		disciplinas = query.getResultList();
		
		return disciplinas;
	}
	
	/* Consulta todas os semestres */
	@SuppressWarnings("unchecked")
	public List<Semestre> consultarTodos() {
		EntityManager em = getEM();
		List<Semestre> semestres;
		
		Query query = em.createNamedQuery("Semestre.consultarTodos");
		
		semestres = query.getResultList();
		
		return semestres;
	}
	
	/* Retorna os alunos de um determinado semestre */
	@SuppressWarnings("unchecked")
	public List<Disciplina> consultarDisciplinaAluno(Integer id) {
		EntityManager em = getEM();
		List<Disciplina> disciplinas;
		
		Query query = em.createNamedQuery("Semestre.consultarAluno");
		query.setParameter("id", id);
		
		disciplinas = query.getResultList();
		
		return disciplinas;
	}
	
	/* Insere no banco */
	@Transactional
	public void insertWithQuery(Semestre semestre) throws Exception {
		EntityManager em = getEM();
		
		Query query = em.createNativeQuery("INSERT INTO Semstre (id)"
										+ " VALUES (?)");
		em.getTransaction().begin();
		query.setParameter(1, semestre.getId());
		query.executeUpdate();
		em.getTransaction().commit();
	}
}
