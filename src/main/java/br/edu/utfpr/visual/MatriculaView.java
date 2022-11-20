package br.edu.utfpr.visual;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.edu.utfpr.dao.AlunoDAO;
import br.edu.utfpr.dao.CursoDAO;
import br.edu.utfpr.dao.DisciplinaDAO;
import br.edu.utfpr.dao.MatriculaDAO;
import br.edu.utfpr.modelo.Aluno;
import br.edu.utfpr.modelo.Curso;
import br.edu.utfpr.modelo.Disciplina;
import br.edu.utfpr.modelo.Matricula;
import javax.swing.JComboBox;
import java.awt.Color;

public class MatriculaView extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable table;
	private JTextField disciplinaField;
	@SuppressWarnings("rawtypes")
	JComboBox comboBox = new JComboBox();

	public MatriculaView(Aluno al) {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 430, 506, 190);
		add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Nome" }));
		scrollPane.setViewportView(table);
		
		JLabel lblRealizaoDeMatrcula = new JLabel("Realização de Matrícula");
		lblRealizaoDeMatrcula.setFont(new Font("Dialog", Font.BOLD, 18));
		lblRealizaoDeMatrcula.setBounds(25, 152, 263, 17);
		add(lblRealizaoDeMatrcula);
		
		JButton btnNewButton_1 = new JButton("Cadastrar");
		btnNewButton_1.addActionListener(new ActionListener() {
			@SuppressWarnings({ "unused"})
			public void actionPerformed(ActionEvent e) {
				
				Integer codigoDisciplina = Integer.parseInt(disciplinaField.getText());
				
				AlunoDAO adao = new AlunoDAO();
				MatriculaDAO mdao = new MatriculaDAO();
				DisciplinaDAO ddao = new DisciplinaDAO();
				
				Matricula matricula = new Matricula();
				List<Matricula> matriculas = new ArrayList<>();
				
				Disciplina disciplina = ddao.consultarPorId(Disciplina.class, codigoDisciplina);
				
				if(disciplina == null) {
					JFrame frame = new JFrame("Erro");
					JOptionPane.showMessageDialog(frame, "Disciplina Inválida!");
				}
			
				
				//matricula.setId(444);
				matricula.setAtivo("Ativo");
				matricula.setAluno(al);
				matricula.setDisciplina(disciplina);
				matriculas.add(matricula);
				
				try {mdao.salvar(matricula);} 
				catch (Exception e1) {e1.printStackTrace();}
				
				al.setMatriculas(matriculas);
			}
		});
		btnNewButton_1.setBounds(182, 342, 106, 27);
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Limpar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparTudo();
			}
		});
		btnNewButton_2.setBounds(295, 342, 105, 27);
		add(btnNewButton_2);
		
		JLabel lblNewLabel_1 = new JLabel("Codigo da Disciplina");
		lblNewLabel_1.setBounds(25, 205, 166, 17);
		add(lblNewLabel_1);
		
		disciplinaField = new JTextField();
		disciplinaField.setBounds(182, 203, 217, 21);
		add(disciplinaField);
		disciplinaField.setColumns(10);
		
		JButton btnMostrarDisciplinas = new JButton("Mostrar Todas Disciplinas");
		btnMostrarDisciplinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				limparTabela();
					
				Curso c = al.getCurso();
				DisciplinaDAO dao = new DisciplinaDAO();
				List<Disciplina> disciplinas = c.getDisciplinas();
				
				mostrarRegistrosDisciplina(disciplinas);
				
			}
		});
		btnMostrarDisciplinas.setBounds(412, 202, 217, 22);
		add(btnMostrarDisciplinas);
		
		comboBox.setBounds(182, 234, 247, 22);
		add(comboBox);
		
		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarDisciplinaFiltrada();
				
			}
		});
		btnFiltrar.setBounds(439, 232, 105, 27);
		add(btnFiltrar);
		
		JLabel lblFiltrarPorDisciplina = new JLabel("Filtrar por Curso");
		lblFiltrarPorDisciplina.setBounds(25, 234, 134, 17);
		add(lblFiltrarPorDisciplina);
	}
	
	public void mostrarDisciplinaFiltrada() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		String nomeCurso = (String) comboBox.getSelectedItem();
		
		CursoDAO dao = new CursoDAO();
		Curso c = dao.consultarPeloNome(nomeCurso);
		Integer id = c.getId();
		
		DisciplinaDAO ddao = new DisciplinaDAO();
		
		List<Disciplina> disciplinas = ddao.consultaPorCurso(id);
		
		limparTabela();
		
		for(Disciplina d : disciplinas) {
			Object[] arr = new Object[2];
			arr[0] = d.getId();
			arr[1] = d.getNome();
			modelo.addRow(arr);
			
		}
		
	}

	public void mostrarRegistrosAluno(List<Aluno> alunos) {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();

		for (Aluno a : alunos) {
			Object[] arr = new Object[2];
			arr[0] = a.getId();
			arr[1] = a.getNome();
			modelo.addRow(arr);
		}
	}
	
	public void mostrarRegistrosDisciplina(List<Disciplina> disciplinas) {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();

		for (Disciplina d : disciplinas) {
			Object[] arr = new Object[2];
			arr[0] = d.getId();
			arr[1] = d.getNome();
			modelo.addRow(arr);
		}
	}
	
	public void limparTabela() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
	}
	
	@SuppressWarnings("unchecked")
	public void popularComboBoxCurso() {
		CursoDAO cdao = new CursoDAO();
		comboBox.removeAllItems();
		comboBox.addItem("Selecione");
	
		List<Curso> cursos = cdao.consultarTodos();
		for(Curso c : cursos) {
			String cursoNome = c.getNome();
			comboBox.addItem(cursoNome);
		}
	}
	
	public void limparTudo() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
		disciplinaField.setText("");
	}
}
