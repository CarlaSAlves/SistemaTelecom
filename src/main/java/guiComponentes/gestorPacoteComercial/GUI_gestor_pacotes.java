
package guiComponentes.gestorPacoteComercial;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import standard_value_object.PacoteComercial;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import servico.GestorDeDAO;

import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextArea;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JCheckBox;

public class GUI_gestor_pacotes extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton btVoltarGestorPacotes;
	private JPanel contentPane;
	private int numberRows;
	private JLabel lblResultados, lblUsernameLogged;
	private JButton botaoDesativarPacoteComercial;
	private JButton botaoEditarPacoteComercial;
	private int indices[];
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);
	private JTextField textFieldID;
	private JTextField textFieldNome;



	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_gestor_pacotes frame = new GUI_gestor_pacotes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI_gestor_pacotes() {

		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Pesquisa de Pacotes Comerciais");
		setFont(font);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1500, 900);
		contentPane.setBackground(SystemColor.inactiveCaption);

		JButton botaoCriarPacotes = new JButton("Criar Pacote Comercial");
		botaoCriarPacotes.setBounds(697, 179, 231, 43);
		botaoCriarPacotes.setFont(new Font("Dialog", Font.PLAIN, 15));
		botaoCriarPacotes.setBackground(SystemColor.activeCaption);
		botaoCriarPacotes.setFocusPainted(false);
		botaoCriarPacotes.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				CriarPacotesDialog dialog = new CriarPacotesDialog(GUI_gestor_pacotes.this);
				dialog.setVisible(true);
			}
		});
		getContentPane().add(botaoCriarPacotes);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(66, 296, 1366, 499);
		panel.setFont(font);
		getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 33, 1332, 660);
		panel.add(scrollPane);

		table = new JTable();
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(new Object[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}, {},}, new String[] {}));
		table.setForeground(SystemColor.desktop);
		table.setBackground(UIManager.getColor("CheckBox.light"));
		table.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		table.setRowHeight(20);


		scrollPane.setViewportView(table);


		lblResultados = new JLabel("Resultados: ");
		lblResultados.setFont(new Font("Dubai Light", Font.PLAIN, 15));
		lblResultados.setBounds(10, 4, 136, 25);
		panel.add(lblResultados);

		botaoEditarPacoteComercial = new JButton("Editar Pacote Comercial");
		botaoEditarPacoteComercial.setBounds(697, 125, 231, 43);
		botaoEditarPacoteComercial.setFont(new Font("Dialog", Font.PLAIN, 15));
		botaoEditarPacoteComercial.setBackground(SystemColor.activeCaption);
		botaoEditarPacoteComercial.setFocusPainted(false);
		botaoEditarPacoteComercial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(GUI_gestor_pacotes.this,
							"Por favor selecione um Pacote Comercial", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				PacoteComercial pacoteComercialTemp = (PacoteComercial) table.getValueAt(row, PacoteComercialPesquisaModelTable.OBJECT_COL);

				CriarPacotesDialog dialog = new CriarPacotesDialog(GUI_gestor_pacotes.this, pacoteComercialTemp, true);

				dialog.setVisible(true);
			}
		});
		getContentPane().add(botaoEditarPacoteComercial);

		botaoDesativarPacoteComercial = new JButton("Desativar Pacote Comercial");
		botaoDesativarPacoteComercial.setBounds(697, 71, 231, 43);
		botaoDesativarPacoteComercial.setFont(new Font("Dialog", Font.PLAIN, 15));
		botaoDesativarPacoteComercial.setBackground(SystemColor.activeCaption);
		botaoDesativarPacoteComercial.setFocusPainted(false);
		botaoDesativarPacoteComercial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					indices = table.getSelectedRows();

					if (indices.length < 0) {
						JOptionPane.showMessageDialog(GUI_gestor_pacotes.this,
								"Por favor selecione um Pacote", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					int resposta = JOptionPane.showConfirmDialog(GUI_gestor_pacotes.this,
							"Desativar este Pacote?", "Confirmar Desativar",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (resposta != JOptionPane.YES_OPTION) {
						return;
					}

					for(int i = 0; i < indices.length; i++) {
						PacoteComercial pacoteTemp = (PacoteComercial) table.getValueAt(indices[i], PacoteComercialPesquisaModelTable.OBJECT_COL);
						GestorDeDAO.getGestorDeDAO().desativarPacoteComercial(pacoteTemp.getId());

					}
					JOptionPane.showMessageDialog(GUI_gestor_pacotes.this,
							"Pacotes Desativado com sucesso", "Pacote Desativado",
							JOptionPane.INFORMATION_MESSAGE);

					refreshPacotesTable();
				} catch (Exception e1) {

				}
			}

		});
		getContentPane().add(botaoDesativarPacoteComercial);

		btVoltarGestorPacotes = new JButton("Voltar");
		btVoltarGestorPacotes.setBounds(76, 806, 119, 32);
		btVoltarGestorPacotes.setFont(font);
		btVoltarGestorPacotes.setBackground(SystemColor.activeCaption);
		btVoltarGestorPacotes.setFocusPainted(false);
		getContentPane().add(btVoltarGestorPacotes);

		JLabel lbFooter = new JLabel("");
		lbFooter.setIcon(new ImageIcon(GUI_gestor_pacotes.class.getResource("/guiComponentes/img/footer2.png")));
		lbFooter.setBounds(599, 802, 367, 59);
		contentPane.add(lbFooter);
		btVoltarGestorPacotes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {



			}
		});

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectedRowCount()>1) {
					botaoEditarPacoteComercial.setEnabled(false);
					botaoDesativarPacoteComercial.setEnabled(true);
				}
				else if (table.getSelectedRows().length==1) {
					botaoEditarPacoteComercial.setEnabled(true);
					botaoDesativarPacoteComercial.setEnabled(true);
				}
				else if (table.getSelectedRowCount()==0)
				{
					botaoEditarPacoteComercial.setEnabled(false);
					botaoDesativarPacoteComercial.setEnabled(false);
				}
			}
		});

		botaoEditarPacoteComercial.setEnabled(false);
		botaoDesativarPacoteComercial.setEnabled(false);
		
		JLabel lblCamposPesquisas = new JLabel("Campos Pesquisa");
		lblCamposPesquisas.setFont(new Font("Dialog", Font.BOLD, 20));
		lblCamposPesquisas.setBounds(66, 32, 294, 26);
		contentPane.add(lblCamposPesquisas);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaption);
		panel_1.setBounds(66, 69, 420, 172);
		contentPane.add(panel_1);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblNewLabelID = new JLabel("ID");
		lblNewLabelID.setFont(new Font("Dialog", Font.PLAIN, 13));
		panel_1.add(lblNewLabelID, "2, 2, left, default");
		
		textFieldID = new JTextField();
		panel_1.add(textFieldID, "4, 2, fill, default");
		textFieldID.setColumns(10);
		
		JLabel lblNewLabelNome = new JLabel("Nome");
		lblNewLabelNome.setFont(new Font("Dialog", Font.PLAIN, 13));
		panel_1.add(lblNewLabelNome, "2, 4, right, default");
		
		textFieldNome = new JTextField();
		panel_1.add(textFieldNome, "4, 4, fill, default");
		textFieldNome.setColumns(10);
		
		JCheckBox NewCheckBoxAtivo = new JCheckBox("Ativo");
		NewCheckBoxAtivo.setBackground(SystemColor.inactiveCaption);
		NewCheckBoxAtivo.setForeground(SystemColor.desktop);
		NewCheckBoxAtivo.setFont(new Font("Dialog", Font.PLAIN, 13));
		panel_1.add(NewCheckBoxAtivo, "4, 6, center, default");
		
		JButton btnNewButtonPesquisar = new JButton("Pesquisar");
		btnNewButtonPesquisar.setBackground(SystemColor.activeCaption);
		btnNewButtonPesquisar.setFont(new Font("Dialog", Font.PLAIN, 15));
		panel_1.add(btnNewButtonPesquisar, "4, 8");
		btnNewButtonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					String id = textFieldID.getText();
					List<PacoteComercial> pacotesComerciais = null;

					if (!id.isBlank()) {
						int id2 = Integer.parseInt(textFieldID.getText());
						pacotesComerciais = GestorDeDAO.getGestorDeDAO().pesquisaPacoteComercial(id2);
					} else  {
						pacotesComerciais = GestorDeDAO.getGestorDeDAO().getAllPacotesComerciais();
					}

					PacoteComercialPesquisaModelTable model = new PacoteComercialPesquisaModelTable(pacotesComerciais);
					table.setModel(model);
					numberRows = table.getRowCount();
					lblResultados.setText("Resultados: " + numberRows);

				} catch (Exception e1) {

				}

			}
		});

		
		JTextArea textAreaDescricao = new JTextArea();
		textAreaDescricao.setBounds(938, 71, 470, 151);
		contentPane.add(textAreaDescricao);
		
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUsernameLogged.setBounds(1252, 809, 159, 32);
		contentPane.add(lblUsernameLogged);
	}

	public void refreshPacotesTable() {
		
		try {
			List<PacoteComercial> pacotesComerciais = GestorDeDAO.getGestorDeDAO().getAllPacotesComerciais();
			PacoteComercialPesquisaModelTable model = new PacoteComercialPesquisaModelTable(pacotesComerciais);
			table.setModel(model);

		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public JButton btVoltarGestorPacotes() {
		return btVoltarGestorPacotes;
	}


	public void setBtVoltarGestorPacotes(JButton btnNewButtonVoltar2) {
		this.btVoltarGestorPacotes = btnNewButtonVoltar2;
	}

	public void setUsernameLoggedIn(String username) {
		lblUsernameLogged.setText("Logged in : " + username);

	}
	
	public JPanel returnPanel() {
		return (JPanel) getContentPane();
	}
}
