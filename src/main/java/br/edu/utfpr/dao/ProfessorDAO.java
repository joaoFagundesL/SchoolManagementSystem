package br.edu.utfpr.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.edu.utfpr.modelo.Curso;
import br.edu.utfpr.modelo.Disciplina;
import br.edu.utfpr.modelo.Professor;

public class ProfessorDAO extends GenericoDAO<Professor> {
	
	/* Consulta todos os professores */
	@SuppressWarnings("unchecked")
	public List<Professor> consultarTodos() {
		EntityManager em = getEM();
		List<Professor> professores;
		
		Query query = em.createNamedQuery("Professor.consultarTodos");
		
		professores = query.getResultList();
		
		return professores;
	}
	
	/* Consulta professsor pelo nome */
	@SuppressWarnings("unchecked")
	public List<Professor> consultaPeloNome(String nome) {
		EntityManager em = getEM();
		List<Professor> professores;
		
		Query query = em.createNamedQuery("Professor.consultarTodosNome");
		query.setParameter("nome", nome);
		
		professores = query.getResultList();
		return professores;
	}
	
	/* Consulta professor pelo cpf */
	public Professor consultaPeloCpf(String cpf) {
		EntityManager em = getEM();
		Professor professor = new Professor();
		
		Query query = em.createNamedQuery("Professor.consultarPeloCpf");
		query.setParameter("cpf", cpf);
		
		professor = (Professor) query.getSingleResult();
		
		return professor;
	}
	
	public Professor consultarUser(String user) {
		EntityManager em = getEM();
		Professor professor = new Professor();
		
		try {
			Query query = em.createNamedQuery("Professor.consultarUser");
			query.setParameter("user", user);
			
			professor = (Professor) query.getSingleResult();
	
			return professor;
			
		} catch(NoResultException e) {
			return null;
		}
	}
	
	/* Atualiza os registros */
	public void update(Professor p, String nome, String titulo, String cpf, Double salario, String email, Curso curso) {
		EntityManager em = getEM();
		
		em.getTransaction().begin();
		
		p = consultarPorId(Professor.class, p.getId());
		
		p.setCpf(cpf);
		p.setNome(nome);
		p.setTitulo(titulo);
		p.setSalario(salario);
		p.setEmail(email);
		p.setCurso(curso);
		
		em.merge(p);
		em.getTransaction().commit();
	}
	
	/* Retorna todos os professores de uma determinada disciplina */
	@SuppressWarnings("unchecked")
	public List<Professor> consultarDisciplinas(Integer id) {
		EntityManager em = getEM();
		List<Professor> professores;
		
		Query query = em.createNamedQuery("Professor.consultarDisciplinas");
		query.setParameter("id", id);
		
		professores = query.getResultList();
		
		return professores;
	}
	
	/* Insere no banco */
	@Transactional
	public void insertWithQuery(Professor professor) throws Exception {
		EntityManager em = getEM();
		
		Query query = em.createNativeQuery("INSERT INTO Professor (titulo, nome, cpf, salario, email, curso_id) VALUES (?, ?, ?, ?, ?, ?)");
		em.getTransaction().begin();
		query.setParameter(1, professor.getTitulo());
		query.setParameter(2, professor.getNome());
		query.setParameter(3, professor.getCpf());
		query.setParameter(4, professor.getSalario());
		query.setParameter(5, professor.getEmail());
		query.setParameter(6, professor.getCurso());
		query.executeUpdate();
		em.getTransaction().commit();
	    
	}
	
	public void insertWithQueryDisciplinaProfessor(Integer idDisciplina, Integer idProfessor) throws Exception {
		EntityManager em = getEM();
		
		Query query = em.createNativeQuery("INSERT INTO Disciplina_Professor (professorid, disciplinaid) VALUES (?, ?)");
		em.getTransaction().begin();
		query.setParameter(1, idDisciplina);
		query.setParameter(2, idProfessor);
		query.executeUpdate();
		em.getTransaction().commit();
	    
	}
	
	
}
