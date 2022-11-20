package br.edu.utfpr.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class DiaSemana implements Entidade {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(name = "Disciplina_DiaSemana", joinColumns = @JoinColumn(name = "diaid"),
				inverseJoinColumns = @JoinColumn(name = "disciplinaid"))
	private List<Disciplina> discs;	

	public DiaSemana(String nome, Integer id) {
		this.nome = nome;
		this.id = id;
	}
	
	public DiaSemana() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Disciplina> getDisciplinas() {
		return discs;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.discs = disciplinas;
	}

	
}
