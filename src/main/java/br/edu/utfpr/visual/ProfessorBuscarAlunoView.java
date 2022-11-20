package br.edu.utfpr.visual;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.edu.utfpr.controller.SemestreController;
import br.edu.utfpr.dao.AlunoDAO;
import br.edu.utfpr.dao.DisciplinaDAO;
import br.edu.utfpr.modelo.Aluno;
import br.edu.utfpr.modelo.Disciplina;
import br.edu.utfpr.modelo.Matricula;
import br.edu.utfpr.modelo.Professor;
import br.edu.utfpr.modelo.Semestre;

import java.awt.Color;

public class ProfessorBuscarAlunoView extends JPanel {
	
	/* View do professor */
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	@SuppressWarnings("rawtypes")
	JComboBox comboBox = new JComboBox();
	
	/* Construtor */
	public ProfessorBuscarAlunoView(Professor p) {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);

		JButton buscarBtn = new JButton("Buscar");
		buscarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AlunoDAO alDao = new AlunoDAO();
				DisciplinaDAO ddao = new DisciplinaDAO();
				String type = (String) comboBox.getSelectedItem(); 
				
				Disciplina d = ddao.consultaPorNome(type);
				
				List<Matricula> matriculas = d.getMatriculas(); 
				popularTabelaDisciplinaNome(matriculas);
				

			}
		});
		buscarBtn.setBounds(186, 264, 105, 27);
		add(buscarBtn);

		JButton limparBuscarBtn = new JButton("Limpar");
		limparBuscarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel modelo = (DefaultTableModel) table.getModel();
				modelo.setRowCount(0);
				comboBox.setSelectedIndex(0);
				
			}
		});
		limparBuscarBtn.setBounds(326, 264, 105, 27);
		add(limparBuscarBtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 420, 549, 190);
		add(scrollPane);

		table = new JTable();
		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Ra", "Matricula", "Aluno", 
																		"Nota", "Status Matricula", "Semestre Aluno", "Curso", 
																		"Semestre da Disciplina", "Situação"}));
		scrollPane.setViewportView(table);
		
		comboBox.setBounds(166, 172, 208, 17);
		add(comboBox);
		
		JLabel lblConsultarPor = new JLabel("Disciplina");
		lblConsultarPor.setFont(new Font("Dialog", Font.BOLD, 14));
		lblConsultarPor.setBounds(44, 172, 111, 17);
		add(lblConsultarPor);
		
		JLabel lblConsultaDeAlunos = new JLabel("Consulta de Alunos");
		lblConsultaDeAlunos.setFont(new Font("Dialog", Font.BOLD, 18));
		lblConsultaDeAlunos.setBounds(44, 115, 247, 17);
		add(lblConsultaDeAlunos);
		
		popularComboBox(p);
	}
	
	
	public void popularTabelaDisciplinaNome(List<Matricula> matriculas) {
		
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		
		for(Matricula m : matriculas) {
			Object[] arr = new Object[9];
			arr[0] = m.getAluno().getId();
			arr[1] = m.getId();
			arr[2] = m.getAluno().getNome();
			arr[3] = m.getNotaFinal();
			arr[4] = m.getAtivo();
			arr[5] = m.getAluno().getSemestre().getId();
			arr[6] = m.getAluno().getCurso().getNome();
			arr[7] = m.getDisciplina().getSemestre().getId();
			arr[8] = m.getAluno().getSenha();
			modelo.addRow(arr);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void popularComboBox(Professor p) {
		DisciplinaDAO disciplinaDao = new DisciplinaDAO();
		
		comboBox.addItem("Selecione");
	
		List<Disciplina> disciplinas = disciplinaDao.consultarDisciplinas(p.getId());
		for(Disciplina d : disciplinas) {
			String nomeDisciplina = d.getNome();
			comboBox.addItem(nomeDisciplina);
		}
	}
	
	public void limparTabela() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
		comboBox.setSelectedIndex(0);
	}
}
