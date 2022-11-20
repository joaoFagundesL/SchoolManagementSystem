package br.edu.utfpr.visual;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import br.edu.utfpr.controller.CursoController;
import br.edu.utfpr.dao.ProfessorDAO;
import br.edu.utfpr.modelo.Curso;
import br.edu.utfpr.modelo.Professor;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;

public class ProfessorView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	Professor p;
	private JTextField nomeProfessorField;
	private JTextField cpfProfessorField;
	private JTextField tituloField;
	private JTable table;
	private JTextField salarioField;
	private JTextField emailField;
	
	@SuppressWarnings("rawtypes")
	JComboBox comboBox = new JComboBox();
	
	public ProfessorView() {
		initComponents();
	}
	
	public void initComponents() {
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		
		JScrollPane scrollPane = new JScrollPane();
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "Email", "Cpf", "Titulo", "Salario", "Curso"
			}
		));
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				/* Obtendo todos os campos de acordo com a posicao da tabela */
				String email = table.getValueAt(table.getSelectedRow(), 1).toString();
				String nome =  table.getValueAt(table.getSelectedRow(), 0).toString();
				String cpf = table.getValueAt(table.getSelectedRow(), 2).toString();
				String titulo =  table.getValueAt(table.getSelectedRow(), 3).toString();
				String salario = table.getValueAt(table.getSelectedRow(), 4).toString();
				String curso = table.getValueAt(table.getSelectedRow(), 5).toString();

				limparTela();
				
				/* Os fields vao ser setados de acordo com os campos que o usuario clicou na tabela */
				
				nomeProfessorField.setText(nome);
				emailField.setText(email);
				cpfProfessorField.setText(cpf);
				tituloField.setText(titulo);
				salarioField.setText(salario);
				comboBox.setSelectedItem(curso);
				
				/* Dizendo para o objeto p que foi o professor que o usuario clicou */
				ProfessorDAO dao = new ProfessorDAO();
				p = dao.consultaPeloCpf(cpfProfessorField.getText());
			}
		});
		scrollPane.setBounds(12, 380, 549, 235);
		add(scrollPane);
		
		JLabel nomeLabelProfessor = new JLabel("Nome");
		nomeLabelProfessor.setBounds(64, 108, 60, 17);
		add(nomeLabelProfessor);
		
		nomeProfessorField = new JTextField();
		nomeProfessorField.setColumns(10);
		nomeProfessorField.setBounds(142, 104, 213, 21);
		add(nomeProfessorField);
		
		JLabel cpfLabel_1 = new JLabel("Cpf");
		cpfLabel_1.setBounds(64, 162, 60, 17);
		add(cpfLabel_1);
		
		cpfProfessorField = new JTextField();
		cpfProfessorField.setColumns(10);
		cpfProfessorField.setBounds(142, 160, 213, 21);
		add(cpfProfessorField);
		
		JLabel tituloLabel = new JLabel("Titulo ");
		tituloLabel.setBounds(64, 193, 60, 17);
		add(tituloLabel);
		
		tituloField = new JTextField();
		tituloField.setColumns(10);
		tituloField.setBounds(142, 191, 213, 21);
		add(tituloField);
		
		JButton btnEnviar_1 = new JButton("Enviar");
		btnEnviar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/* Obtendo os campos que o usuario informou */
				String nome = nomeProfessorField.getText();
				String email = emailField.getText();
				String titulo = tituloField.getText();
				String cpf = cpfProfessorField.getText();
				Double salario = Double.parseDouble(salarioField.getText());
				Curso curso = obterValorComboBoxCurso();
				
				/* Criando um professor com as informacoes que o usuario passou */
				Professor professor = new Professor();
				professor.setNome(nome);
				professor.setCpf(cpf);
				professor.setTitulo(titulo);
				professor.setEmail(email);
				professor.setSalario(salario);
				professor.setCurso(curso);
				
				/* Inserindo no banco */
				try {
					ProfessorDAO pdao = new ProfessorDAO();
					pdao.insertWithQuery(professor);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				/* Frame de confirmacao de envio */
				JFrame frame = new JFrame("JOptionPane exemplo");
				JOptionPane.showMessageDialog(frame, "Registro Enviado!");
				
				limparTela();
					
				/* Coloca o professor criado na tabela instanteneamente  */
				popularTabelaProfessor();
				
			}
		});
		btnEnviar_1.setBounds(64, 313, 94, 27);
		add(btnEnviar_1);
		
		JButton btnRemover_1 = new JButton("Remover");
		btnRemover_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/* Pega o registro que o usuario selecionou na tabela e remove */
				ProfessorDAO pDao = new ProfessorDAO();
				pDao.remover(Professor.class, p.getId());
				
				/* Confirmando a exclusao */
				JFrame frame = new JFrame("JOptionPane exemplo");
				JOptionPane.showMessageDialog(frame, "Registro Excluído!");
				
				limparTela();
				
				/* Remove o registro da JTable */
				popularTabelaProfessor();
				
			}
		});
		btnRemover_1.setBounds(170, 313, 94, 27);
		add(btnRemover_1);
		
		JButton btnLimpar_1 = new JButton("Limpar");
		btnLimpar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparTela();
			}
		});
		btnLimpar_1.setBounds(386, 313, 98, 27);
		add(btnLimpar_1);
		
		JButton btnAtualizar_1 = new JButton("Atualizar");
		btnAtualizar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/* Obtem os campos que o usuario informou */
				String titulo = tituloField.getText();
				String email = emailField.getText();
				String cpf = cpfProfessorField.getText();
				String nome = nomeProfessorField.getText();
				String salario = salarioField.getText();
				Curso curso = obterValorComboBoxCurso();
				
				/* Atualiza */
				ProfessorDAO dao = new ProfessorDAO();
				dao.update(p, nome, titulo, cpf, Double.parseDouble(salario), email, curso);
				
				/* Confirmando atualizacao */
				JFrame frame = new JFrame("Mensagem");
				JOptionPane.showMessageDialog(frame, "Atualizado com sucesso!");
				
				/* Atualizando a JTable */
				popularTabelaProfessor();
			}
		});
		btnAtualizar_1.setBounds(276, 313, 98, 27);
		add(btnAtualizar_1);
		
		JLabel lblCadastroDeAluno_1 = new JLabel("Gerenciamento de Professor");
		lblCadastroDeAluno_1.setFont(new Font("Dialog", Font.BOLD, 18));
		lblCadastroDeAluno_1.setBounds(64, 77, 306, 17);
		add(lblCadastroDeAluno_1);
		
		salarioField = new JTextField();
		salarioField.setColumns(10);
		salarioField.setBounds(142, 220, 213, 21);
		add(salarioField);
		
		JLabel lblSalario = new JLabel("Salario");
		lblSalario.setBounds(64, 224, 60, 17);
		add(lblSalario);
		
		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setBounds(64, 135, 60, 17);
		add(lblNewLabel);
		
		emailField = new JTextField();
		emailField.setBounds(142, 133, 213, 21);
		add(emailField);
		emailField.setColumns(10);
		
		comboBox.setBounds(142, 251, 213, 17);
		add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Curso");
		lblNewLabel_1.setBounds(64, 253, 45, 13);
		add(lblNewLabel_1);
		
		popularTabelaProfessor();
		
	}
	
	public void limparTela() {
		nomeProfessorField.setText("");
		emailField.setText("");
		tituloField.setText("");
		cpfProfessorField.setText("");
		salarioField.setText("");
	}
	
	public Curso obterValorComboBoxCurso() {
		CursoController cController = new CursoController(); 
		String cursoNome = (String) comboBox.getSelectedItem();
		System.out.println(cursoNome);
		Curso curso = cController.consultarPeloNome(cursoNome);
		return curso;	
	}
	
	@SuppressWarnings("unchecked")
	public void popularComboBoxCurso() {
		CursoController cController = new CursoController();
		comboBox.removeAllItems();
		comboBox.addItem("Selecione");
	
		List<Curso> cursos = cController.consultarTodos();
		for(Curso c : cursos) {
			String cursoNome = c.getNome();
			comboBox.addItem(cursoNome);
		}
	}
	
	public void popularTabelaProfessor() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		
		/* Consultando todos os professores do banco de dados */
		ProfessorDAO pdao = new ProfessorDAO();
		List<Professor> professores = pdao.consultarTodos(); 
		
		if (modelo.getRowCount() > 0) {
			modelo.setRowCount(0);
		}
		
		/* Colocando os registros em cada posicao */
		for(Professor p : professores) {
			Object[] arr = new Object[6];
			arr[0] = p.getNome();
			arr[1] = p.getEmail();
			arr[2] = p.getCpf();
			arr[3] = p.getTitulo();
			arr[4] = p.getSalario();
			arr[5] = p.getCurso().getNome();
			modelo.addRow(arr);
		}
	}
}
