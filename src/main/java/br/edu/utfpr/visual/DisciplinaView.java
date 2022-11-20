package br.edu.utfpr.visual;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.edu.utfpr.dao.CursoDAO;
import br.edu.utfpr.dao.DisciplinaDAO;
import br.edu.utfpr.dao.ModeloDisciplinaDAO;
import br.edu.utfpr.dao.SemestreDAO;
import br.edu.utfpr.modelo.Curso;
import br.edu.utfpr.modelo.Disciplina;
import br.edu.utfpr.modelo.ModeloDisciplina;
import br.edu.utfpr.modelo.Semestre;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import java.awt.Color;

public class DisciplinaView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTextField nomeField;
	private JTextField cargaField;
	private JTable table_1;
	
	/* Criacao de todos os comboBox que vao ser utilizados*/
	@SuppressWarnings("rawtypes")
	JComboBox comboBox = new JComboBox();
	@SuppressWarnings("rawtypes")
	JComboBox comboBox_1 = new JComboBox();
	
	@SuppressWarnings("rawtypes")
	JComboBox comboBox_2 = new JComboBox();
	
	/* Quando o usario clicar na tabela, a disciplina correspondente vai ser atribuida nesse objeto */
	Disciplina d;

	public DisciplinaView() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		/* Assim que entra o comboBox ja vai ser preenchido com os valores de 1 a 10, correspondente ao semestre */
		popularComboBoxSemestre();
		
		/* Configurando o botao enviar */
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/* Assim que clica para enviar eu pego os dados que o usuario preencheu em cada campo */
				String nome = nomeField.getText();
				Integer carga = Integer.parseInt(cargaField.getText());
				
				/* Obtendo o valor do semestre que o usuario informou no comboBox */
				Semestre semestre = obterValorComboBoxSemestre(comboBox);
				
				ModeloDisciplina md = obterValorComboBoxModelo();
				
				/* Obtendo o nome do curso que o usuario informou no comboBox */
				String nomeCurso = (String) comboBox_1.getSelectedItem();
				
				/* Consultando o curso por meio do nome */
				CursoDAO cdao = new CursoDAO();
				Curso c = cdao.consultarPeloNome(nomeCurso);
				
				/* Criando uma nova disciplina */
				DisciplinaDAO dao = new DisciplinaDAO();
				Disciplina d = new Disciplina();
				
				/* Configurando a nova disciplina */
				d.setNome(nome);
				d.setCurso(c);
				d.setSemestre(semestre);
				d.setCargaHoraria(carga);
				d.setModelo(md);
				
				/* Inserindo no banco */
				try {
					dao.insertWithQuery(d);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				/* Frame de confirmação */
				JFrame frame = new JFrame("JOptionPane exemplo");
				JOptionPane.showMessageDialog(frame, "Registro Enviado!");
				
				/* Depois que envia o registro limpa toda tela */
				limparTela();
				
				/* Vai mostrar na JTable a nova disciplina cadastrada */
				popularTabelaDisciplina();
			}
		});
		btnNewButton.setBounds(66, 312, 105, 27);
		add(btnNewButton);
		
		
		/* Configurando o botao remover */
		JButton btnNewButton_1 = new JButton("Remover");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/* O usario clica no registro por meio do JTable, ai eu pego a disciplina do JTable
				 *  e armazeno na variavel d 
				 */
				DisciplinaDAO dao = new DisciplinaDAO();
				dao.remover(Disciplina.class, d.getId());
				
				/* Confirmando a remoção */
				JFrame frame = new JFrame("JOptionPane exemplo");
				JOptionPane.showMessageDialog(frame, "Registro Excluído!");
				
				/* Limpa toda tela */
				limparTela();
				
				/* Atualiza a JTable */
				popularTabelaDisciplina();
			}
		});
		btnNewButton_1.setBounds(183, 312, 105, 27);
		add(btnNewButton_1);

		/* Configurando o botao de atualizar */
		JButton btnNewButton_2 = new JButton("Atualizar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/* Obtendo todos os campos */
				Integer carga = Integer.parseInt(cargaField.getText());
				String nome = nomeField.getText();
				Semestre semestre = obterValorComboBoxSemestre(comboBox);
						
				ModeloDisciplina md = obterValorComboBoxModelo();
				
				/* Pega o nome do curso do comboBox */
				String nomeCurso = (String) comboBox_1.getSelectedItem();
				
				/* Consulta o curso por meio do nome */
				CursoDAO cdao = new CursoDAO();
				Curso c = cdao.consultarPeloNome(nomeCurso);
				
				/* Atualiza todos os campos de acordo com o informado pelo usuario */
				DisciplinaDAO dao = new DisciplinaDAO();
				dao.update(d, nome, carga, semestre, md, c);
				
				/* JFrame de confirmacao */
				JFrame frame = new JFrame("Mensagem");
				JOptionPane.showMessageDialog(frame, "Atualizado com sucesso!");
				
				/* Atualiza a JTable */
				popularTabelaDisciplina();
			}
		});
		
		/* Configuracao de alguns labels */
		btnNewButton_2.setBounds(300, 312, 105, 27);
		add(btnNewButton_2);

		JLabel lblNewLabel = new JLabel("Gerenciamento de Disciplina");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		lblNewLabel.setBounds(111, 87, 306, 17);
		add(lblNewLabel);

		JLabel lblNome_1 = new JLabel("Nome");
		lblNome_1.setBounds(111, 138, 60, 17);
		add(lblNome_1);

		JLabel lblCargaHoaria = new JLabel("Carga Hoŕaria");
		lblCargaHoaria.setBounds(111, 169, 98, 17);
		add(lblCargaHoaria);

		nomeField = new JTextField();
		nomeField.setColumns(10);
		nomeField.setBounds(237, 136, 213, 21);
		add(nomeField);

		cargaField = new JTextField();
		cargaField.setColumns(10);
		cargaField.setBounds(237, 171, 213, 21);
		add(cargaField);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 412, 549, 238);
		add(scrollPane);
		
		/* Configura o que fazer quando o usuario clica em algum registro da tabela */
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				/* Obtem os campos da tabela e armazena em uma variavel */
				String nome = table_1.getValueAt(table_1.getSelectedRow(), 1).toString();
				String carga = table_1.getValueAt(table_1.getSelectedRow(), 2).toString();
				String id = table_1.getValueAt(table_1.getSelectedRow(), 3).toString();
				String nomeCurso = table_1.getValueAt(table_1.getSelectedRow(), 4).toString();
				
				/* Limpa os fields que tinham anteriormente para atualizar com os novos */
				limparTela();	
				
				/* Cada field vai ser preenchido com o valor selecionado pelo usuario */
				nomeField.setText(nome);
				cargaField.setText(carga);
				comboBox.setSelectedItem(Integer.parseInt(id));
				comboBox_1.setSelectedItem(nomeCurso);
				
				/* Atualiza a variavel d, pois assim ela sempre vai armazenar o valor da disciplina atual */
				DisciplinaDAO dao = new DisciplinaDAO();
				d = dao.consultaPorNome(nomeField.getText());
			}
		});
		scrollPane.setViewportView(table_1);
		table_1.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Codigo", "Nome", "Carga Horaria", "Semestre", "Curso"}));
		
		JLabel lblSemestre = new JLabel("Semestre");
		lblSemestre.setBounds(111, 204, 60, 17);
		add(lblSemestre);
		
		comboBox.setBounds(237, 204, 213, 17);
		add(comboBox);
		
		JLabel lblCurso = new JLabel("Curso");
		lblCurso.setBounds(111, 233, 60, 17);
		add(lblCurso);
		
		comboBox_1.setBounds(237, 233, 213, 17);
		add(comboBox_1);
		
		JButton btnNewButton_3 = new JButton("Limpar");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparTela();
			}
		});
		btnNewButton_3.setBounds(415, 312, 105, 27);
		add(btnNewButton_3);
		
		JLabel lblNewLabel_1 = new JLabel("Modelo");
		lblNewLabel_1.setBounds(111, 261, 45, 13);
		add(lblNewLabel_1);
		
		comboBox_2.setBounds(237, 260, 213, 17);
		add(comboBox_2);
		
		/* Assim que entra nesse panel o JTable é preenchido com as disciplinas */
		popularTabelaDisciplina();
	}

	public void popularTabelaDisciplina() {

		DefaultTableModel modelo = (DefaultTableModel) table_1.getModel();
		
		/* Consultando todas as disciplinas que foram cadastradas */
		DisciplinaDAO dao = new DisciplinaDAO();
		List<Disciplina> disciplinas = dao.consultarTodos();
		
		/* Removando todas as linhas para nao baguncar a tabela */
		if (modelo.getRowCount() > 0) {
			modelo.setRowCount(0);
		}
		
		/* Preenchendo a tabela */
		for (Disciplina d : disciplinas) {
			Object[] arr = new Object[5];
			arr[0] = d.getId();
			arr[1] = d.getNome();
			arr[2] = d.getCargaHoraria();
			arr[3] = d.getSemestre().getId();
			arr[4] = d.getCurso().getNome();
			modelo.addRow(arr);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void popularComboBoxCurso() {
		/* Atualizando os cursos */
		CursoDAO cdao = new CursoDAO();
		
		/* Considerando que novos cursos podem ser adicionados, para nao repetir eu removo e depois preencho os cursos*/
		comboBox_1.removeAllItems();
		comboBox_1.addItem("Selecione");
		
		/* Preenchendo o comboBox */
		List<Curso> cursos = cdao.consultarTodos();
		for(Curso c : cursos) {
			String cursoNome = c.getNome();
			comboBox_1.addItem(cursoNome);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void popularComboBoxModelo() {
		/* Atualizando os cursos */
		ModeloDisciplinaDAO cdao = new ModeloDisciplinaDAO();
		
		/* Considerando que novos cursos podem ser adicionados, para nao repetir eu removo e depois preencho os cursos*/
		comboBox_2.removeAllItems();
		comboBox_2.addItem("Selecione");
		
		/* Preenchendo o comboBox */
		List<ModeloDisciplina> modelos = cdao.consultarTodos();
		for(ModeloDisciplina md : modelos) {
			String modeloNome = md.getNome();
			comboBox_2.addItem(modeloNome);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void popularComboBoxSemestre() {
		
		/* Nao preciso remover todos os itens porque sempre vai haver apenas 10 semestres, que é o limite */
		SemestreDAO dao = new SemestreDAO();
	
		comboBox.addItem("Selecione");
		
		/* Preenchendo o comboBox */
		List<Semestre> semestres = dao.consultarTodos();
		for(Semestre s : semestres) {
			Integer semestreId = s.getId();
			comboBox.addItem(semestreId);
		}
	}
	
	public ModeloDisciplina obterValorComboBoxModelo() {
		ModeloDisciplina modeloDisciplina = null;
		ModeloDisciplinaDAO dao = new ModeloDisciplinaDAO();
		String nome = (String)comboBox_2.getSelectedItem();
		List<ModeloDisciplina> md = dao.consultarTodos();
		
		
		
		for(ModeloDisciplina modelo : md) {
			if(modelo.getNome().equals(nome)) {
				modeloDisciplina = modelo;
			} 
		}
		
		return modeloDisciplina;
	}
	
	public Semestre obterValorComboBoxSemestre(@SuppressWarnings("rawtypes") JComboBox comboBox) {
		
		/* Obtenho o id do semestre e passa como parametro esse id para minha query */
		SemestreDAO dao = new SemestreDAO(); 
		Integer semestreId = (Integer) comboBox.getSelectedItem();
		Semestre semestre = dao.consultarPorId(Semestre.class, semestreId);
		return semestre;
		
	}

	public void limparTela() {
		/* Os comboBox ficam na opcao "Selecione" e os fields ficam em branco */
		comboBox.setSelectedIndex(0);
		comboBox_1.setSelectedIndex(0);
		nomeField.setText("");
		cargaField.setText("");
	}
}
