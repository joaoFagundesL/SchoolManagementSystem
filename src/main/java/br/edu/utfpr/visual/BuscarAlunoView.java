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

import br.edu.utfpr.dao.AlunoDAO;
import br.edu.utfpr.modelo.Aluno;
import br.edu.utfpr.modelo.Matricula;
import java.awt.Color;

public class BuscarAlunoView extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField buscarIdField;
	private JTable table;
	@SuppressWarnings("rawtypes")
	JComboBox comboBox = new JComboBox();
	
	/* Construtor */
	public BuscarAlunoView() {
		setBackground(Color.LIGHT_GRAY);
		initComponents();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initComponents() {
		setLayout(null);

		JLabel lblInformeORa = new JLabel("Informe ");
		lblInformeORa.setFont(new Font("Dialog", Font.BOLD, 14));
		lblInformeORa.setBounds(48, 206, 148, 17);
		add(lblInformeORa);

		buscarIdField = new JTextField();
		buscarIdField.setBounds(148, 204, 208, 21);
		add(buscarIdField);
		buscarIdField.setColumns(10);

		JButton buscarBtn = new JButton("Buscar");
		buscarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AlunoDAO alDao = new AlunoDAO();
				
				/* type vai ser a String do item do comboBox */
				String type = (String) comboBox.getSelectedItem(); 
				
				/* Consultando o aluno a partir do ra informado pelo usuario,
				 * depois consulto as matriculas dele e coloco tudo no JTable */
				
				if(type.equals("Ra")) {
					Aluno aluno;
					Integer id = Integer.parseInt(buscarIdField.getText());
					aluno = alDao.consultarPorId(Aluno.class, id);
					popularTabelaDisciplinaId(aluno);	
				
					
				/* Se for pelo nome, pode retornar mais de um aluno,
				 * sendo assim, para cada aluno, eu pego as matriculas que ele possui
				 * e atualizo no JTable */
				} else if (type.equals("Nome")) {
					List<Aluno> alunos;
					alunos = alDao.consultaPeloNome(buscarIdField.getText());
					popularTabelaDisciplinaNome(alunos);
					
				}
			}
		});
		buscarBtn.setBounds(186, 264, 105, 27);
		add(buscarBtn);

		JButton limparBuscarBtn = new JButton("Limpar");
		limparBuscarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel modelo = (DefaultTableModel) table.getModel();
				buscarIdField.setText("");
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
																		"Disciplina", "Status Matricula", "Semestre Aluno", "Curso", 
																		"Semestre da Disciplina"}));
		scrollPane.setViewportView(table);
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Selecione", "Nome", "Ra"}));
		comboBox.setBounds(148, 172, 208, 17);
		add(comboBox);
		
		JLabel lblConsultarPor = new JLabel("Consultar por");
		lblConsultarPor.setFont(new Font("Dialog", Font.BOLD, 14));
		lblConsultarPor.setBounds(44, 172, 111, 17);
		add(lblConsultarPor);
		
		JButton btnNewButton = new JButton("Consultar Todos");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					popularTabelaDisciplina();	
			}
		});
		btnNewButton.setBounds(363, 167, 130, 27);
		add(btnNewButton);
		
		JLabel lblConsultaDeAlunos = new JLabel("Consulta de Alunos");
		lblConsultaDeAlunos.setFont(new Font("Dialog", Font.BOLD, 18));
		lblConsultaDeAlunos.setBounds(44, 115, 247, 17);
		add(lblConsultaDeAlunos);
	}
	
	// Recebe apenas um id, já que cada aluno tem seu próprio id
	public void popularTabelaDisciplinaId(Aluno aluno) {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		
		List<Matricula> matriculas;
		AlunoDAO adao = new AlunoDAO();
		
		Object[] arr = new Object[8];
		Integer id = aluno.getId();
		arr[0] = aluno.getId();
		arr[2] = aluno.getNome(); 
		arr[5] = aluno.getSemestre().getId();
		arr[6] = aluno.getCurso().getNome();
		
		matriculas = adao.consultarMatricula(id); 
		
		if(matriculas.size() <= 0) {
			modelo.addRow(arr);				
		}
		
		for (Matricula m : matriculas) {
			arr[1] = m.getId();
			arr[3] = m.getDisciplina().getNome();
			arr[4] = m.getAtivo();
			arr[7] = m.getDisciplina().getSemestre().getId();
			modelo.addRow(arr);
		}
	}
	
	public void popularTabelaDisciplinaNome(List<Aluno> alunos) {
		
		/*
		 * Esse método é referente a consulta com like,
		 * caso o usuario coloque "Joao" e haja mais de um aluno
		 * com esse nome, o meu List<Matricula> vai receber todas as
		 * matriculas de todos os alunos com nome "Joao", 
		 * podendo ser de um aluno ou mais de um alumo
		 * */
		
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		
		AlunoDAO adao = new AlunoDAO();
		
		/* Como um alunos tem varias matriculas, uso um list */
		List<Matricula> matriculas;
		
		/* Para cada aluno, eu consulto o id dele e a partir verifico quais sao as matriculas dele,
		 * depois disso o JTable é populado. O ciclo continua para todos alunos */
		for(Aluno a : alunos) {	
			Object[] arr = new Object[8];
			Integer id = a.getId();
			arr[0] = a.getId();
			arr[2] = a.getNome(); 
			arr[5] = a.getSemestre().getId();
			arr[6] = a.getCurso().getNome();
			
			matriculas = adao.consultarMatricula(id); 
			
			if(matriculas.size() <= 0) {
				modelo.addRow(arr);				
			}
			
			for (Matricula m : matriculas) {
				arr[1] = m.getId();
				arr[3] = m.getDisciplina().getNome();
				arr[4] = m.getAtivo();
				arr[7] = m.getDisciplina().getSemestre().getId();
				modelo.addRow(arr);
			}
		}	
	}

	public void popularTabelaDisciplina() {
		
		/*
		 * Esse metodo recebe as matriculas apenas de um aluno,
		 * caso eu precise usar
		 * */

		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		
		AlunoDAO adao = new AlunoDAO();
		List<Aluno> alunos = adao.consultarTodos();
		
		/* Como um alunos tem varias matriculas, uso um list */
		List<Matricula> matriculas;
		
		/* Para cada aluno, eu consulto o id dele e a partir verifico quais sao as matriculas dele,
		 * depois disso o JTable é populado. O ciclo continua para todos alunos */
		for(Aluno a : alunos) {	
			Object[] arr = new Object[8];
			Integer id = a.getId();
			arr[0] = a.getId();
			arr[2] = a.getNome(); 
			arr[5] = a.getSemestre().getId();
			arr[6] = a.getCurso().getNome();
			
			matriculas = adao.consultarMatricula(id); 
			
			if(matriculas.size() <= 0) {
				modelo.addRow(arr);				
			}
			
			for (Matricula m : matriculas) {
				System.out.println(m.getAluno().getNome());
				arr[1] = m.getId();
				arr[3] = m.getDisciplina().getNome();
				arr[4] = m.getAtivo();
				arr[7] = m.getDisciplina().getSemestre().getId();
				modelo.addRow(arr);
			}
		}	
	}
	
	public void limparTabela() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
		buscarIdField.setText("");
		comboBox.setSelectedIndex(0);
	}
}
