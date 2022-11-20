package br.edu.utfpr.visual;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.edu.utfpr.dao.CursoDAO;
import br.edu.utfpr.dao.DisciplinaDAO;
import br.edu.utfpr.modelo.Aluno;
import br.edu.utfpr.modelo.Curso;
import br.edu.utfpr.modelo.Disciplina;
import java.awt.Font;

public class BuscarDisciplinaView extends JPanel {

	private JTable table;
	private JTextField disciplinaField;
	@SuppressWarnings("rawtypes")
	JComboBox comboBox = new JComboBox();

	private static final long serialVersionUID = 1L;

	public BuscarDisciplinaView() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 430, 506, 190);
		add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Nome", "Modelo", "Semestre", "Carga", "Curso" }));
		scrollPane.setViewportView(table);

		JButton btnNewButton_2 = new JButton("Limpar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparTudo();
			}
		});
		btnNewButton_2.setBounds(295, 342, 105, 27);
		add(btnNewButton_2);

		JLabel lblNewLabel_1 = new JLabel("Codigo da Disciplina");
		lblNewLabel_1.setBounds(25, 205, 125, 17);
		add(lblNewLabel_1);

		disciplinaField = new JTextField();
		disciplinaField.setBounds(167, 203, 114, 21);
		add(disciplinaField);
		disciplinaField.setColumns(10);

		JButton btnMostrarDisciplinas = new JButton("Mostrar Todas Disciplinas");
		btnMostrarDisciplinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				limparTabela();

				DisciplinaDAO dao = new DisciplinaDAO();
				List<Disciplina> disciplinas = dao.consultarTodos();

				mostrarRegistrosDisciplina(disciplinas);

			}
		});
		btnMostrarDisciplinas.setBounds(291, 202, 187, 22);
		add(btnMostrarDisciplinas);

		comboBox.setBounds(129, 276, 247, 22);
		add(comboBox);

		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarDisciplinaFiltrada();
			}
		});
		btnFiltrar.setBounds(393, 274, 105, 27);
		add(btnFiltrar);

		JLabel lblFiltrarPorDisciplina = new JLabel("Filtrar por Curso");
		lblFiltrarPorDisciplina.setBounds(25, 279, 134, 17);
		add(lblFiltrarPorDisciplina);
		
		JLabel lblNewLabel = new JLabel("Consulta de Disciplinas");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(25, 150, 237, 27);
		add(lblNewLabel);
		
		JButton enviarBtn = new JButton("Enviar");
		enviarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarDisciplinaId(Integer.parseInt(disciplinaField.getText()));
			}
		});
		enviarBtn.setBounds(177, 345, 85, 21);
		add(enviarBtn);
	}
	
	/* Arrumar caso a disciplina nao exista */
	public void mostrarDisciplinaId(Integer id) {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		DisciplinaDAO dao = new DisciplinaDAO();
		limparTabela();
		
		Disciplina d = dao.consultarPorId(Disciplina.class, id);
		Object[] arr = new Object[6];
		arr[0] = d.getId();
		arr[1] = d.getNome();
		arr[2] = d.getModelo().getNome();
		arr[3] = d.getSemestre().getId();
		arr[4] = d.getCargaHoraria();
		arr[5] = d.getCurso().getNome();
		modelo.addRow(arr);
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

		for (Disciplina d : disciplinas) {
			Object[] arr = new Object[6];
			arr[0] = d.getId();
			arr[1] = d.getNome();
			arr[2] = d.getModelo().getNome();
			arr[3] = d.getSemestre().getId();
			arr[4] = d.getCargaHoraria();
			arr[5] = d.getCurso().getNome();
			modelo.addRow(arr);

		}
	}

	public void mostrarRegistrosDisciplina(List<Disciplina> disciplinas) {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();

		for (Disciplina d : disciplinas) {
			Object[] arr = new Object[6];
			arr[0] = d.getId();
			arr[1] = d.getNome();
			arr[2] = d.getModelo().getNome();
			arr[3] = d.getSemestre().getId();
			arr[4] = d.getCargaHoraria();
			arr[5] = d.getCurso().getNome();
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
		for (Curso c : cursos) {
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
