package br.edu.utfpr.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.edu.utfpr.modelo.Curso;
import br.edu.utfpr.modelo.Disciplina;
import br.edu.utfpr.modelo.ModeloDisciplina;
import br.edu.utfpr.modelo.Semestre;

public class DisciplinaDAO extends GenericoDAO<Disciplina> {
	
	/* Funcao que vai retornar a disciplina que está cadastrada em uma matricula*/
	public Disciplina consultarDisciplinaMatricula(Integer id) {
		EntityManager em = getEM();
		Disciplina disciplina;
		
		Query query = em.createNamedQuery("Disciplina.consultarDisciplina");
		query.setParameter("id", id);
		
		/* Como uma matricula só pode ter um disciplina cadastrada vai retornar apenas um registro */
		disciplina = (Disciplina) query.getSingleResult();
		
		return disciplina;
	}
	
	/* Funcao que vai consultar a disciplina por meio do nome */
	public Disciplina consultaPorNome(String nome) {
		EntityManager em = getEM();
		Disciplina disciplina;
		
		Query query = em.createNamedQuery("Disciplina.consultarPorNome");
		query.setParameter("nome", nome);
		
		/* Considerando que não existe disciplina com o mesmo nome, vai retornar apenas uma disciplina */
		disciplina = (Disciplina) query.getSingleResult();
	
		return disciplina;
	}
	
	/* Retorna todas as disciplinas de um determinado curso */
	public List<Disciplina> consultaPorCurso(Integer id) {
		EntityManager em = getEM();
		
		Query query = em.createNamedQuery("Disciplina.disciplinaCurso");
		query.setParameter("id", id);
		
		/* Como um curso tem varias disciplina, vai retornar uma lista */
		@SuppressWarnings("unchecked")
		List<Disciplina> d = query.getResultList();
		
		return d;
	}
	
	/* Atualizando os registros da disciplina */
	public void update(Disciplina d, String nome, Integer carga, Semestre semestre, ModeloDisciplina md ,Curso curso) {
		EntityManager em = getEM();
		
		em.getTransaction().begin();
		
		d = consultarPorId(Disciplina.class, d.getId());
		d.setCargaHoraria(carga);
		d.setSemestre(semestre);
		d.setNome(nome);
		d.setCurso(curso);
		d.setModelo(md);
		
		em.merge(d);
		em.getTransaction().commit();
	}
	
	/* Funcao que vai retornar todas as disciplinas que um professor pode dar */
	@SuppressWarnings("unchecked")
	public List<Disciplina> consultarDisciplinas(Integer id) {
		EntityManager em = getEM();
		List<Disciplina> disciplinas;
		
		Query query = em.createNamedQuery("Disciplina.consultarProfessores");
		query.setParameter("id", id);
		
		disciplinas = query.getResultList();
		
		return disciplinas;
	}
	
	/* Funcao que vai retornar todas as disciplinas cadastradas */
	@SuppressWarnings("unchecked")
	public List<Disciplina> consultarTodos() {
		EntityManager em = getEM();
		List<Disciplina> disciplinas;
		
		Query query = em.createNamedQuery("Disciplina.consultarTodos");
		
		disciplinas = query.getResultList();
		
		return disciplinas;
	}
	
	/* Funcao que esta associada ao botao de enviar na view que vai mandar para o banco os registros */
	@Transactional
	public void insertWithQuery(Disciplina disciplina) throws Exception {
		EntityManager em = getEM();
		
		Query query = em.createNativeQuery("INSERT INTO Disciplina (nome, cargaHoraria, curso_id , modelo_id, semestre_id) VALUES (?, ?, ?, ?, ?)");
		em.getTransaction().begin();
		query.setParameter(1, disciplina.getNome());
		query.setParameter(2, disciplina.getCargaHoraria());
		query.setParameter(3, disciplina.getCurso());
		query.setParameter(4, disciplina.getModelo());
		query.setParameter(5, disciplina.getSemestre());
		query.executeUpdate();
		em.getTransaction().commit();
	    
	}
}
