package br.edu.utfpr.visual;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import br.edu.utfpr.dao.DisciplinaDAO;
import br.edu.utfpr.dao.ProfessorDAO;
import br.edu.utfpr.modelo.Disciplina;
import br.edu.utfpr.modelo.Professor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;

public class BuscarProfessorView extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTable table_1;
	@SuppressWarnings("rawtypes")
	JComboBox comboBox = new JComboBox();

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BuscarProfessorView() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Localizar por ");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNewLabel.setBounds(64, 224, 157, 17);
		add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(172, 253, 349, 21);
		add(textField);
		textField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 411, 552, 206);
		add(scrollPane);

		table_1 = new JTable();
		table_1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		scrollPane.setViewportView(table_1);
		table_1.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Id Professor" ,"Nome Professor", "Id Disciplina", "Nome Disciplina", "Carga Horaria" , "Curso" }));

		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Selecione", "Nome", "Id" }));
		comboBox.setBounds(172, 224, 162, 17);
		add(comboBox);

		JLabel lblNewLabel_1 = new JLabel("Informe");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNewLabel_1.setBounds(64, 255, 60, 17);
		add(lblNewLabel_1);

		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
							
				List<Professor> professoresAux;
				ProfessorDAO ddao = new ProfessorDAO();

				// Primeiro pega a opcao selecionada (Id/Nome)
				String type = (String) comboBox.getSelectedItem();

				if (type.equals("Id")) {
					popularTabelaDisciplina(Integer.parseInt(textField.getText()));

				} else if (type.equals("Nome")) {
					
					professoresAux = ddao.consultaPeloNome(textField.getText());

					popularTabelaDisciplinaPeloNome(professoresAux);
				
				}
			}
		});
		btnNewButton.setBounds(172, 319, 105, 27);
		add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Limpar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparTabela();
			}
		});
		btnNewButton_1.setBounds(295, 319, 105, 27);
		add(btnNewButton_1);

		JLabel lblConsultaDeProfessores = new JLabel("Consulta de Professores");
		lblConsultaDeProfessores.setFont(new Font("Dialog", Font.BOLD, 18));
		lblConsultaDeProfessores.setBounds(64, 165, 227, 17);
		add(lblConsultaDeProfessores);

		JButton btnMostrarTodos = new JButton("Mostrar Todos");
		btnMostrarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparTabela();
				ProfessorDAO pdao = new ProfessorDAO();
				List<Professor> professores = pdao.consultarTodos();

				professores = pdao.consultarTodos();

				popularMostrarTodos(professores);

			}
		});
		btnMostrarTodos.setBounds(346, 219, 118, 27);
		add(btnMostrarTodos);

	}
	
	public void limparTabela() {
		DefaultTableModel modelo = (DefaultTableModel) table_1.getModel();
		modelo.setRowCount(0);
		textField.setText("");
		comboBox.setSelectedIndex(0);
	}
	
	// essa funcao vai ser chamada quando o botao "Mostrar Todos" for pressionado
	public void popularMostrarTodos(List<Professor> professores) {
		
		limparTabela();
		
		DefaultTableModel modelo = (DefaultTableModel) table_1.getModel();

		DisciplinaDAO ddao = new DisciplinaDAO();

		List<Disciplina> disciplinas;
		
		for (Professor p : professores) {
			Object[] arr = new Object[6];
			arr[1] = p.getNome();
			arr[0] = p.getId();
			arr[5] = p.getCurso().getNome();
			
			//para cada professor ele pega as disciplinas
			
			disciplinas = p.getDisciplinas();
			
			//mesmo o professor nao tendo disiciplina vai ser mostrado
			if(disciplinas.size() <= 0) {
				modelo.addRow(arr);
			}
			
			for(Disciplina d : disciplinas) {
				arr[2] = d.getId();
				arr[3] = d.getNome();
				arr[4] = d.getCargaHoraria();
				modelo.addRow(arr);
			}
		}
	}

	// Essa funcao vai ser chamada quando o comboBox estiver na opcao de id
	// selecionado
	public void popularTabelaDisciplina(Integer id) {
		
		limparTabela();
		
		DefaultTableModel modelo = (DefaultTableModel) table_1.getModel();

		ProfessorDAO dao = new ProfessorDAO();

		Professor p = dao.consultarPorId(Professor.class, id);

		if (modelo.getRowCount() > 0) {
			modelo.setRowCount(0);
		}
		
		List<Disciplina> disciplinas = p.getDisciplinas();
		
		if(disciplinas.size() <= 0) {			
			Object[] arr = new Object[6];
			arr[0] = p.getId();
			arr[1] = p.getNome();
			arr[5] = p.getCurso().getNome();
			modelo.addRow(arr);
		}
		else {			
			for (Disciplina d : disciplinas) {
				Object[] arr = new Object[6];
				arr[0] = p.getId();
				arr[1] = p.getNome();
				arr[2] = d.getId();
				arr[3] = d.getNome();
				arr[4] = d.getCargaHoraria();
				arr[5] = p.getCurso().getNome();
				modelo.addRow(arr);
			}
		}
	}

	// Essa funcao vai ser chamada quando a opcao selecionada do comboBox for o nome
	public void popularTabelaDisciplinaPeloNome(List<Professor> professores) {
		
		limparTabela();
		
		DefaultTableModel modelo = (DefaultTableModel) table_1.getModel();
		List<Disciplina> disciplinas;
		
		for(Professor p : professores) {
			disciplinas = p.getDisciplinas();
			
			if(disciplinas.size() <= 0) {
				Object[] arr = new Object[6];
				arr[0] = p.getId();
				arr[1] = p.getNome();
				arr[5] = p.getCurso().getNome();
				modelo.addRow(arr);	
			}
			
			else {
				for(Disciplina d : disciplinas) {
					Object[] arr = new Object[6];
					arr[0] = p.getId();
					arr[1] = p.getNome();
					arr[2] = d.getId();
					arr[3] = d.getNome();
					arr[4] = d.getCargaHoraria();
					arr[5] = p.getCurso().getNome();
					modelo.addRow(arr);					
				}
			}
		}		
	}
}
