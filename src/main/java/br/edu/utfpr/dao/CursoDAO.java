package br.edu.utfpr.dao;

import java.util.*;
import javax.persistence.*;
import javax.transaction.*;

import br.edu.utfpr.modelo.*;

public class CursoDAO extends GenericoDAO<Curso> { 
	
	/* Retorna todos os cursos */
	@SuppressWarnings("unchecked")
	public List<Curso> consultarTodos() {
		EntityManager em = getEM();
		List<Curso> cursos;
		
		Query query = em.createNamedQuery("Curso.consultarTodos");
		
		cursos = query.getResultList();
		
		return cursos;
	}
	
	/* Retorna um curso pelo nome */
	public Curso consultarPeloNome(String nome) {
		EntityManager em = getEM();
		
		Curso curso;
		Query query = em.createNamedQuery("Curso.consultarPeloNome");
		query.setParameter("nome", nome);
		
		curso = (Curso) query.getSingleResult();
		
		return curso;
	}
	
	/* Funcao que vai inserir no banco */
	@Transactional
	public void insertWithQuery(Curso curso) throws Exception {
		EntityManager em = getEM();
		
		Query query = em.createNativeQuery("INSERT INTO Curso (nome, duracao) VALUES (?, ?)");
		em.getTransaction().begin();
		query.setParameter(1, curso.getNome());
		query.setParameter(2, curso.getDuracao());
		query.executeUpdate();
		em.getTransaction().commit();
	}
	
	/* Atualiza os registros */
	public void update(Curso c, String nome, Integer anos) {
		EntityManager em = getEM();
		
		em.getTransaction().begin();
		
		c = consultarPorId(Curso.class, c.getId());
		c.setDuracao(anos);
		c.setNome(nome);
		
		em.merge(c);
		em.getTransaction().commit();
	}
}
