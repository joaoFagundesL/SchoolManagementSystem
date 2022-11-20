package br.edu.utfpr.visual;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import br.edu.utfpr.controller.AlunoController;
import br.edu.utfpr.controller.CursoController;
import br.edu.utfpr.controller.SemestreController;
import br.edu.utfpr.modelo.Aluno;
import br.edu.utfpr.modelo.Curso;
import br.edu.utfpr.modelo.Semestre;

public class AlunoView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/* Variavel de controle para o obter o aluno quando o usuario clicar em um registro da tabela */
	Aluno a;
	
	/* Elementos visuais */
	private JTextField nomeField;
	private JTextField cpfField;
	private JTextField ingressoField;
	private JTable table;
	private JTextField emailField;
	@SuppressWarnings("rawtypes")
	public JComboBox comboBox = new JComboBox();
	@SuppressWarnings("rawtypes")
	public JComboBox comboBox_1 = new JComboBox();;
	
	/* Construtor */
	public AlunoView() {
		comboBox.setBounds(157, 284, 213, 17);
		comboBox_1.setBounds(157, 313, 213, 17);
		
		initComponents();
	}
	
	public void initComponents() {
		
		/* Configurando elementos visuais */
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		
		add(comboBox);
		add(comboBox_1);
		popularComboBoxSemestre();
		
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setBounds(79, 197, 60, 17);
		add(nomeLabel);

		nomeField = new JTextField();
		nomeField.setBounds(157, 195, 213, 21);
		add(nomeField);
		nomeField.setColumns(10);

		JLabel cpfLabel = new JLabel("Cpf");
		cpfLabel.setBounds(79, 226, 60, 17);
		add(cpfLabel);

		cpfField = new JTextField();
		cpfField.setBounds(157, 224, 213, 21);
		add(cpfField);
		cpfField.setColumns(10);

		JLabel ingressoLabel = new JLabel("Ingresso");
		ingressoLabel.setBounds(79, 255, 60, 17);
		add(ingressoLabel);

		ingressoField = new JTextField();
		ingressoField.setBounds(157, 251, 213, 21);
		add(ingressoField);
		ingressoField.setColumns(10);
		
		/* Configurando botao enviar */
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/* Obtendo os campos informados pelo usuario */
				String nome = nomeField.getText();
				String email = emailField.getText();
				Integer ano = Integer.parseInt(ingressoField.getText());
				String cpf = cpfField.getText();
				
				/* Obtem o curso e o semestre do comboBox */
				Curso curso = obterValorComboBoxCurso();
				Semestre semestre = obterValorComboBoxSemestre();
				
				/* Variavel que vai armazenar o id do semestre */
				int qtd = (int) comboBox_1.getSelectedItem();
				
				/* Validando o semestre. Um curso de 4 anos nao pode ter mais de 8 semestres na hora da selecao */
				if(qtd > curso.getDuracao() * 2) {
					JFrame frame = new JFrame("Erro!");
					JOptionPane.showMessageDialog(frame, "Semestre inválido para esse curso!");
				} else {
					
					/* Criando um aluno */
					Aluno aluno = new Aluno();
					aluno.setEmail(email);
					aluno.setNome(nome);
					aluno.setCpf(cpf);
					aluno.setAnoEntrada(ano);
					aluno.setCurso(curso);
					aluno.setSemestre(semestre);
					
					/* Salva no banco */
					AlunoController alController = new AlunoController();
					alController.inserir(aluno);
					
					/* Frame de confirmacao */
					JFrame frame = new JFrame("Enviado!");
					JOptionPane.showMessageDialog(frame, "Registro Enviado!");
					
					/* Limpa a tela depois de envio e atualiza a JTable */
					limparTela();
					popularTabelaAluno();
				}	
			}
		});
		btnEnviar.setBounds(79, 372, 94, 27);
		add(btnEnviar);
		
		/* Configurando o botao remover */
		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/* a variavel "a" é quando o usuario clica em um registro da tabela, assim obtenho o aluno que vai ser removido */
				AlunoController alController = new AlunoController();
				
				if(alController.remover(Aluno.class, a.getId()) == false) {
					JFrame frame = new JFrame("Erro");
					JOptionPane.showMessageDialog(frame, "Id Invalido!");
				}
				
				else {					
					/* Frame de confirmacao */
					JFrame frame = new JFrame("JOptionPane exemplo");
					JOptionPane.showMessageDialog(frame, "Registro Excluído!");
				}
				
				/* Limpa tela e atualiza o JTable */
				limparTela();
				popularTabelaAluno();
			}
		});
		btnRemover.setBounds(185, 372, 94, 27);
		add(btnRemover);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparTela();
			}
		});
		btnLimpar.setBounds(397, 372, 98, 27);
		add(btnLimpar);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/* Obtendo os campos informados pelo usuario */
				Integer ano = Integer.parseInt(ingressoField.getText());
				String cpf = cpfField.getText();
				String nome = nomeField.getText();
				String email = emailField.getText();
				Curso curso = obterValorComboBoxCurso();
				Semestre semestre = obterValorComboBoxSemestre();
				
				AlunoController alController = new AlunoController();
				
				/* Obtendo o valor do semestre */
				int qtd = (int) comboBox_1.getSelectedItem();				
				
				/* Se o semestre infomado for maior que a duracao do curso */
				if(alController.verificarSemestre(qtd, curso.getDuracao() * 2)) {
					JFrame frame = new JFrame("Erro!");
					JOptionPane.showMessageDialog(frame, "Semestre inválido para esse curso!");
				} 
				
				else {
					/* Atualizando os registros */
					alController.update(a, nome, ano, cpf, email, curso, semestre);
					JFrame frame = new JFrame("Mensagem");
					JOptionPane.showMessageDialog(frame, "Atualizado com sucesso!");					
					popularTabelaAluno();
				}

			}
		});
		btnAtualizar.setBounds(287, 372, 98, 27);
		add(btnAtualizar);

		JLabel lblCadastroDeAluno = new JLabel("Gerenciamento de Aluno");
		lblCadastroDeAluno.setFont(new Font("Dialog", Font.BOLD, 18));
		lblCadastroDeAluno.setBounds(79, 99, 306, 17);
		add(lblCadastroDeAluno);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 436, 549, 190);
		add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				/* Obtendo os registros de acordo com a posicao da coluna da tabela */
				String nome = table.getValueAt(table.getSelectedRow(), 0).toString();
				String cpf = table.getValueAt(table.getSelectedRow(), 1).toString();
				String email = table.getValueAt(table.getSelectedRow(), 2).toString();
				String ano = table.getValueAt(table.getSelectedRow(), 3).toString();
				String cursoNome = table.getValueAt(table.getSelectedRow(), 4).toString();
				String semestreId = table.getValueAt(table.getSelectedRow(), 5).toString();
				
				/* Limpa a tela para colocar os novos valores */
				limparTela();

				/* Colocando os valores da tabela no campo certo */
				nomeField.setText(nome);
				emailField.setText(email);
				cpfField.setText(cpf);
				ingressoField.setText(ano);
				comboBox.setSelectedItem(cursoNome);
				comboBox_1.setSelectedItem(Integer.parseInt(semestreId));
				
				/* Sempre atualizando o aluno que o usuario clicou na tabela, assim posso usar em outras partes do programa */
				AlunoController alController = new AlunoController();
				
				a = alController.consultarPeloCpf(a, cpfField.getText());
				
				if(a == null) {
					JFrame frame = new JFrame("Erro!");
					JOptionPane.showMessageDialog(frame, "Aluno nao encontrado!");
				}
				
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		/* Definindo os titulos da tabela */
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "Cpf", "Email", "Ingresso", "Curso", "Semestre"
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(79, 168, 60, 17);
		add(lblEmail);
		
		emailField = new JTextField();
		emailField.setBounds(157, 162, 213, 21);
		add(emailField);
		emailField.setColumns(10);
		
		JLabel lblCurso = new JLabel("Curso");
		lblCurso.setBounds(79, 284, 60, 17);
		add(lblCurso);
		
		JLabel lblSemestre = new JLabel("Semestre");
		lblSemestre.setBounds(79, 313, 60, 17);
		add(lblSemestre);
		
		popularTabelaAluno();

	}
	
	/* Consulta todos os alunos e coloca na tabela */
	public void popularTabelaAluno() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();

		AlunoController alController = new AlunoController();
		List<Aluno> alunos = alController.consultarTodos();
		
		if (modelo.getRowCount() > 0) {
			modelo.setRowCount(0);
		}

		for (Aluno a : alunos) {
			Object[] arr = new Object[6];
			arr[0] = a.getNome();
			arr[1] = a.getCpf();
			arr[2] = a.getEmail();
			arr[3] = a.getAnoEntrada();
			arr[4] = a.getCurso().getNome();
			arr[5]= a.getSemestre().getId();
			modelo.addRow(arr);
		}
	}
		
	/* Atualiza o comboBox de acordo com os cursos criados/removidos */
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
	
	/* Preenche o comboBox com valores de 1 a 10 */
	@SuppressWarnings("unchecked")
	public void popularComboBoxSemestre() {
	SemestreController semestreController = new SemestreController();
		
		comboBox_1.addItem("Selecione");
	
		List<Semestre> semestres = semestreController.consultarTodos();
		for(Semestre s : semestres) {
			Integer semestreId = s.getId();
			comboBox_1.addItem(semestreId);
		}
	}
	
	/* Limpa a tela e seta o comboBox com a posicao "selecione" */
	public void limparTela() {
		nomeField.setText("");
		emailField.setText("");
		cpfField.setText("");
		ingressoField.setText("");
		
		comboBox.setSelectedIndex(0);
		comboBox_1.setSelectedIndex(0);
	}
	
	/* Vai retornar o curso do comboBox */
	public Curso obterValorComboBoxCurso() {
		CursoController cController = new CursoController(); 
		String cursoNome = (String) comboBox.getSelectedItem();
		Curso curso = cController.consultarPeloNome(cursoNome);
		return curso;	
	}
	
	/* Retorna o semestre do comboBox */
	public Semestre obterValorComboBoxSemestre() {
		SemestreController semestreController = new SemestreController (); 
		Integer semestreId = (Integer) comboBox_1.getSelectedItem();
		System.out.println(semestreId);
		Semestre semestre = semestreController.consultarPorId(Semestre.class, semestreId);
		return semestre;
		
	}
}
