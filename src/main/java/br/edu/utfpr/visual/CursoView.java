package br.edu.utfpr.visual;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import br.edu.utfpr.controller.CursoController;
import br.edu.utfpr.dao.AlunoDAO;
import br.edu.utfpr.dao.CursoDAO;
import br.edu.utfpr.exceptions.DuracaoCursoInvalida;
import br.edu.utfpr.modelo.Aluno;
import br.edu.utfpr.modelo.Curso;
import java.awt.Color;
import java.awt.Font;


public class CursoView extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField nomeField;
	private JTextField tempoField;
	private JTable table;
	Curso c;

	/**
	 * Create the panel.
	 */
	public CursoView() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Codigo", "Nome", "Duracao (anos)"
			}
		));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 388, 515, 221);
		add(scrollPane);
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				/* Obtem os campos de acordo com a posicao da linha da tabela */
				String id = table.getValueAt(table.getSelectedRow(), 0).toString();
				String nome = table.getValueAt(table.getSelectedRow(), 1).toString();
				String anos = table.getValueAt(table.getSelectedRow(), 2).toString();

				limparTela();
				
				/* Muda os campos com os valores que o usuario clicou na tabela */
				nomeField.setText(nome);
				tempoField.setText(anos);
				
				/* Atualizando o objeto que vai ser usado durante todo o programa */
				
				/* Apenas testando chamar o dao por meio de um pacote diferente */
				CursoController cursoController = new CursoController();
				c = cursoController.consultarPeloId(Curso.class, Integer.parseInt(id));
				
			}
		});
	
		popularTabelaCurso();
		
		JLabel nomeLbl = new JLabel("Nome");
		nomeLbl.setBounds(61, 168, 60, 17);
		add(nomeLbl);
		
		nomeField = new JTextField();
		nomeField.setBounds(178, 166, 192, 21);
		add(nomeField);
		nomeField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Duracao (anos)");
		lblNewLabel_1.setBounds(61, 197, 114, 17);
		add(lblNewLabel_1);
		
		tempoField = new JTextField();
		tempoField.setBounds(178, 199, 192, 21);
		add(tempoField);
		tempoField.setColumns(10);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/* Obtendo os campos que o usuario informou */
				String nome = nomeField.getText();
				Integer anos = Integer.parseInt(tempoField.getText());
				
				/* Onde criar minha validacao? */
				
				Curso curso = new Curso();
				try {
					if(anos > 5) {
						throw new DuracaoCursoInvalida("Duracao Invalida");
					} else {						
						/* Criando o curso */
						curso.setNome(nome);
						curso.setDuracao(anos);
						
						CursoDAO dao = new CursoDAO();
						try {dao.insertWithQuery(curso);;} 
						catch (Exception e1) {e1.printStackTrace();} 
						
						JFrame frame = new JFrame("Enviado");
						JOptionPane.showMessageDialog(frame, "Registro Enviado!");
					}
						
				} catch (DuracaoCursoInvalida e1){
					JFrame frame = new JFrame("Erro");
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
				
				/* Frame de confirmacao */
				
				
				limparTela();
				
				/* Atualizando o JTable */
				popularTabelaCurso();
				
			}
		});
		btnEnviar.setBounds(82, 286, 72, 27);
		add(btnEnviar);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/* Obtendo os campos que o usuario informou */
				Integer anos = Integer.parseInt(tempoField.getText());
				String nome = nomeField.getText();
				
				/* Atualizando os registros */
				CursoDAO dao = new CursoDAO();
				dao.update(c, nome, anos);
				
				/* Frame de confirmacao */
				JFrame frame = new JFrame("Mensagem");
				JOptionPane.showMessageDialog(frame, "Atualizado com sucesso!");
				
				/* Atualizando a JTable */
				popularTabelaCurso();
			}
		});
		btnAtualizar.setBounds(166, 286, 89, 27);
		add(btnAtualizar);
		
		JButton btnRemovr = new JButton("Remover");
		btnRemovr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/* Fazendo uma consulta para saber se o curso possui algum aluno */
				AlunoDAO dao = new AlunoDAO();
				List<Aluno> alunos = dao.consultarAlunos(c.getId());	
				
				/* Caso o curso possua algum aluno nao vai ser possivel remove-lo */
				if(alunos.size() > 0) {					
					JFrame frame = new JFrame("JOptionPane exemplo");
					JOptionPane.showMessageDialog(frame, "Operacao não permitida!");
				} else {
					
					/* Remove se o curso nao possuir alunos */
					CursoDAO cdao = new CursoDAO();
					cdao.remover(Curso.class, c.getId());
					
				}

				limparTela();
				
				/* Atualiza JTable */
				popularTabelaCurso();
				
			}
		});
		btnRemovr.setBounds(267, 286, 89, 27);
		add(btnRemovr);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparTela();
			}
		});
		btnLimpar.setBounds(368, 286, 89, 27);
		add(btnLimpar);
		
		JLabel lblGerenciamentoDeCurso = new JLabel("Gerenciamento de Curso");
		lblGerenciamentoDeCurso.setFont(new Font("Dialog", Font.BOLD, 18));
		lblGerenciamentoDeCurso.setBounds(61, 126, 255, 17);
		add(lblGerenciamentoDeCurso);
	
	}
	public void limparTela() {
		nomeField.setText("");
		tempoField.setText("");
	}
	
	/* Populando a JTable de acordo com os cursos registrados */
	public void popularTabelaCurso() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		
		CursoDAO dao = new CursoDAO();
		List<Curso> cursos = dao.consultarTodos();

		if (modelo.getRowCount() > 0) {
			modelo.setRowCount(0);
		}

		for (Curso c : cursos) {
			Object[] arr = new Object[3];
			arr[0] = c.getId();
			arr[1] = c.getNome();
			arr[2] = c.getDuracao();
			modelo.addRow(arr);
		}
	}
}
