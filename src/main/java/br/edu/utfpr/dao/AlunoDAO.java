package br.edu.utfpr.dao;

import java.util.*;
import javax.persistence.*;
import javax.transaction.*;
import br.edu.utfpr.modelo.*;

public class AlunoDAO extends GenericoDAO<Aluno> {
	
	/* Retorna todos os alunos */
	@SuppressWarnings("unchecked")
	public List<Aluno> consultarTodos() {
		EntityManager em = getEM();
		List<Aluno> alunos;
		
		Query query = em.createNamedQuery("Aluno.consultarTodos");
		
		alunos = query.getResultList();
		
		return alunos;
	}
	
	/* Retorna aluno pelo cpf */
	public Aluno consultarPeloCpf(String cpf) {
		EntityManager em = getEM();
		Aluno aluno;
		
		Query query = em.createNamedQuery("Aluno.consultarPeloCpf");
		query.setParameter("cpf", cpf);
		aluno = (Aluno) query.getSingleResult();
		
		return aluno;
	}
	
	public Aluno consultarPeloUser(String user) {
		EntityManager em = getEM();
		Aluno aluno;
		try {
			Query query = em.createNamedQuery("Aluno.consultarUser");
			query.setParameter("user", user);
			aluno = (Aluno) query.getSingleResult();
			
			return aluno;
		} catch(NoResultException e) {
			return null;
		}
	}
	
	/* Retorna aluno de um determinado curso */
	@SuppressWarnings("unchecked")
	public List<Aluno> consultarAlunos(Integer id) {
		EntityManager em = getEM();
		
		List<Aluno> alunos;
		
		Query query = em.createNamedQuery("Aluno.consultarCurso");
		query.setParameter("id", id);
		
		alunos = query.getResultList();
		
		return alunos;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Aluno> consultarDisciplinas(Integer id) {
		EntityManager em = getEM();
		
		List<Aluno> alunos;
		
		Query query = em.createNamedQuery("Aluno.consultarMatriculaDisciplina");
		query.setParameter("id", id);
		
		alunos = query.getResultList();
		
		return alunos;
	}
	
	/* Consulta aluno com base no nome */
	@SuppressWarnings("unchecked")
	public List<Aluno> consultaPeloNome(String nome) {
		EntityManager em = getEM();
		List<Aluno> alunos;
		
		Query query = em.createNamedQuery("Aluno.consultarTodosNome");
		query.setParameter("nome", nome);
		
		alunos = query.getResultList();
		return alunos;
	}
	
	/* Atualiza os registros */
	public void update(Aluno a, String nome, Integer ano, String cpf, String email, Curso curso,
						Semestre semestre) {
		EntityManager em = getEM();
		
		em.getTransaction().begin();
		
		a = consultarPorId(Aluno.class, a.getId());
		
		a.setCpf(cpf);
		a.setCurso(curso);
		a.setSemestre(semestre);
		a.setNome(nome);
		a.setAnoEntrada(ano);
		a.setEmail(email);
		
		em.merge(a);
		em.getTransaction().commit();
	}
	
	/* Retorna as matriculas de um determinado aluno */
	@SuppressWarnings("unchecked")
	public List<Matricula> consultarMatricula(Integer id) {
		EntityManager em = getEM();
		List<Matricula> matriculas;
		
		Query query = em.createNamedQuery("Aluno.consultarMatricula");
		query.setParameter("id", id);
		
		matriculas = query.getResultList();
		
		return matriculas;
	}
	
	/* Insere no banco */
	@Transactional
	public void insertWithQuery(Aluno aluno) throws Exception {
		EntityManager em = getEM();
		
		Query query = em.createNativeQuery("INSERT INTO Aluno (anoEntrada, nome, cpf, email, curso_id, semestre_id)"
										+ " VALUES (?, ?, ?, ?, ?, ?)");
		em.getTransaction().begin();
		query.setParameter(1, aluno.getAnoEntrada());
		query.setParameter(2, aluno.getNome());
		query.setParameter(3, aluno.getCpf());
		query.setParameter(4, aluno.getEmail());
		query.setParameter(5, aluno.getCurso().getId());
		query.setParameter(6, aluno.getSemestre().getId());
		query.executeUpdate();
		em.getTransaction().commit();
	}
}
