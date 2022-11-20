package br.edu.utfpr.modelo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@NamedQueries({

	/* Consulta as disciplinas de uma determinada matricula */
	@NamedQuery(name = "Disciplina.consultarDisciplina",
	query = "SELECT d FROM Disciplina d JOIN FETCH d.matriculas m WHERE m.id = :id"),
	
	/* Retorna todas as disciplinas */
	@NamedQuery(name = "Disciplina.consultarTodos",
	query = "SELECT d FROM Disciplina d"),
	
	/* Retorna todas as disciplinas de um curso */
	@NamedQuery(name = "Disciplina.disciplinaCurso",
				query = "SELECT d FROM Disciplina d JOIN d.curso c WHERE c.id = :id"),
	
	/* Consulta a disciplina pelo nome */
	@NamedQuery(name = "Disciplina.consultarPorNome",
	query = "SELECT d FROM Disciplina d WHERE d.nome = :nome"),
	
	/* Retorna as disciplinas que um professor pode dar */
	@NamedQuery(name = "Disciplina.consultarProfessores",
	query = "SELECT d FROM Disciplina d JOIN FETCH d.professores p WHERE p.id = :id")
})
public class Disciplina implements Serializable, Entidade { 

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Integer cargaHoraria;
	
	
	/* Uma disciplina pertence apenas a um curso */
	@ManyToOne
	@JoinColumn(name = "curso_id")
	private Curso curso;
	
	/* Uma disciplina tem varias matriculas */
	@OneToMany(mappedBy = "disciplina")
	private List<Matricula> matriculas;
	
	@ManyToMany(mappedBy = "discs")
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
	private List<DiaSemana> dias;

	/* Um professor pode dar varias disciplinas */
	@ManyToMany(mappedBy = "disciplinas")
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
	private List<Professor> professores;
	
	/* Uma disciplina tem apenas um modelo (Hibrido, remoto) */
	@ManyToOne
	@JoinColumn(name = "modelo_id	")
	private ModeloDisciplina modelo;
	
	/* A disciplina é de um semestre específico */
	@ManyToOne
	@JoinColumn(name = "semestre_id")
	private Semestre semestre;
	
	
	/* Construtores */
	public Disciplina() {
	}

	public Disciplina(Integer id, String nome, Integer cargaHoraria) {
		super();
		this.id = id;
		this.nome = nome;
		this.cargaHoraria = cargaHoraria;
	}
	
	/* Getters e Setters */
	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<DiaSemana> getDias() {
		return dias;
	}

	public void setDias(List<DiaSemana> dias) {
		this.dias = dias;
	}

	public Integer getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Integer cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}
	
	public List<Professor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public ModeloDisciplina getModelo() {
		return modelo;
	}

	public void setModelo(ModeloDisciplina modelo) {
		this.modelo = modelo;
	}

	public Semestre getSemestre() {
		return semestre;
	}

	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}

	
	/* Equals e HashCode */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disciplina other = (Disciplina) obj;
		return Objects.equals(id, other.id);
	}
}
