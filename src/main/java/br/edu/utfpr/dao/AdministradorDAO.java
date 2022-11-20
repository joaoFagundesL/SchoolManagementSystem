package br.edu.utfpr.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.edu.utfpr.modelo.Administrador;

public class AdministradorDAO extends GenericoDAO<Administrador>{
	/* Retorna todos os alunos */
	@SuppressWarnings("unchecked")
	public List<Administrador> consultarTodos() {
		EntityManager em = getEM();
		List<Administrador> administradores;
		
		Query query = em.createNamedQuery("Administrador.consultarTodos");
		
		administradores = query.getResultList();
		
		return administradores;
	}
	
	public Administrador consultarPeloNome(String user) {
		EntityManager em = getEM();
		try {			
			Administrador admin;
			Query query = em.createNamedQuery("Administrador.consultarPeloNome");
			query.setParameter("user", user);
			
			admin = (Administrador) query.getSingleResult();
			
			return admin;
			
		} catch(NoResultException e) { 
			return null;
		}
	}
}
