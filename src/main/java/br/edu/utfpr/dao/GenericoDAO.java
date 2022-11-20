package br.edu.utfpr.dao;

import javax.persistence.*;
import br.edu.utfpr.modelo.*;

/*
 * Implementacao de um DAO generico para nao ficar repetindo o codigo
 * 
 * Entidade e uma interface que implementa o metodo getId(), por isso em cada classe comum
 * existe um @Override no metodo getId()
 * 
 * As classes comuns: Professor, Aluno.. devem fazer a implementacao da Interface entidade
 * 
 * No outros DAOS basta fazer um extends e passar a classe correspondente para o generics T (s)
 * */

public class GenericoDAO<T extends Entidade> {
	
	/* Criacao do emf com base no arquivo persistence.xml */
	public EntityManager getEM() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-project");
		return emf.createEntityManager();
	}
	
	/* Vai salvar no banco de dados */
	public T salvar(T t) throws Exception {
		EntityManager em = getEM();
		
		try {
			em.getTransaction().begin();
			
			if(t.getId() == null) {em.persist(t);}
			else {t = em.merge(t);}
			
			em.getTransaction().commit();
		
		} finally {em.close(); }
	
		return t;
	}
	
	/* Remove do banco de dados com base em um id */
	public void remover(Class<T> clazz, Integer id) {
		EntityManager em = getEM();
		try {
			em.getTransaction().begin();
			
			T t = em.find(clazz, id);
			
			em.remove(t);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
	
	/* Consulta por meio de um id */
	public T consultarPorId(Class<T> clazz, Integer id) {
		EntityManager em = getEM();
		T t = null;
		
		try {t = em.find(clazz, id);}
		
		finally {em.close();}
		
		return t;
	}
}
