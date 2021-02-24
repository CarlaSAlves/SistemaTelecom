package guiComponentes.cliente_pessoal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import guiComponentes.Admin_GUI_homepage;
import guiComponentes.GUI_total;
import servico.GestorDeDAO;
import standard_value_object.Cliente;
import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.JButton;

public class AreaCliente_MeusProdutos extends JFrame {

	private JPanel panelMeusProdutos;
	private JTextArea textAreaNomePacote;
	private JTextArea textAreaDescricaoPacote;
	private JTextArea textAreaDescricaoPromocoes;
	private JComboBox<Promocao> comboBoxPromocoes;
	private JLabel lblAsSuasPromoces, lblSeuPacote;
	private String userName;
	private JButton btnPesquisarPacotes;
	private JButton btnPesquisarPromocoes;



	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public AreaCliente_MeusProdutos(){	
		initialize();	
	}



	@SuppressWarnings("null")
	private void initialize() {

		ativarNimbusLookAndFeel();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1384, 586);
		panelMeusProdutos = new JPanel();
		panelMeusProdutos.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelMeusProdutos);
		panelMeusProdutos.setLayout(null);
		setResizable(false);
		
		// Área Pacote Comercial

		lblSeuPacote = new JLabel("Pacote Comercial");
		lblSeuPacote.setForeground(Color.WHITE);
		lblSeuPacote.setBounds(66, 122, 315, 36);
		lblSeuPacote.setFont(new Font("Dialog", Font.PLAIN, 15));
		panelMeusProdutos.add(lblSeuPacote);

		textAreaNomePacote = new JTextArea();
		textAreaNomePacote.setEditable(false);
		textAreaNomePacote.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		textAreaNomePacote.setBounds(66, 169, 300, 31);
		panelMeusProdutos.add(textAreaNomePacote);

		textAreaDescricaoPacote = new JTextArea();
		textAreaDescricaoPacote.setEditable(false);
		textAreaDescricaoPacote.setLineWrap(true);
		textAreaDescricaoPacote.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		textAreaDescricaoPacote.setBounds(66, 222, 300, 104);
		panelMeusProdutos.add(textAreaDescricaoPacote);



		// Promoções
		lblAsSuasPromoces = new JLabel("Promoções");
		lblAsSuasPromoces.setForeground(Color.WHITE);
		lblAsSuasPromoces.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblAsSuasPromoces.setBounds(400, 122, 315, 36); 
		panelMeusProdutos.add(lblAsSuasPromoces);

		textAreaDescricaoPromocoes = new JTextArea();
		textAreaDescricaoPromocoes.setEditable(false);
		textAreaDescricaoPromocoes.setLineWrap(true);
		textAreaDescricaoPromocoes.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		textAreaDescricaoPromocoes.setBounds(400, 222, 300, 104);
		panelMeusProdutos.add(textAreaDescricaoPromocoes);

		comboBoxPromocoes = new JComboBox<Promocao>();
		comboBoxPromocoes.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		comboBoxPromocoes.setBounds(400, 169, 300, 31);
		panelMeusProdutos.add(comboBoxPromocoes);

		btnPesquisarPromocoes = new JButton("Pesquisar Promoções");
		btnPesquisarPromocoes.setFont(new Font("Dialog Light", Font.PLAIN, 12));
		btnPesquisarPromocoes.setBounds(400, 169, 247, 32);
		btnPesquisarPromocoes.setVisible(false);
		panelMeusProdutos.add(btnPesquisarPromocoes);

		btnPesquisarPacotes = new JButton("Pesquisar Pacotes Comerciais");
		btnPesquisarPacotes.setFont(new Font("Dialog Light", Font.PLAIN, 12));
		btnPesquisarPacotes.setBounds(66, 168, 264, 32);
		btnPesquisarPacotes.setVisible(false);
		panelMeusProdutos.add(btnPesquisarPacotes);


		JLabel lblOsSeusProdutos = new JLabel("Os Seus Produtos");
		lblOsSeusProdutos.setForeground(Color.WHITE);
		lblOsSeusProdutos.setFont(new Font("Dubai Light", Font.BOLD, 22));
		lblOsSeusProdutos.setBounds(66, 55, 234, 28);
		panelMeusProdutos.add(lblOsSeusProdutos);

		//Imagem fundo

		JLabel imagemDados = new JLabel("");
		imagemDados.setBounds(0, -37, 1408, 586);
		panelMeusProdutos.add(imagemDados);
		imagemDados.setBackground(new Color(240, 240,240 ));
		imagemDados.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/AltranClientes.png")));

	}


	/**
	 * Cria o renderer da ComboBox
	 * 
	 *
	 */

	private class PromocaoComboRenderer implements ListCellRenderer<Promocao> {

		private JLabel display;

		PromocaoComboRenderer(){
			display = new JLabel();
			display.setFont(new Font("Dubai Light", Font.PLAIN, 14));
			display.setOpaque( true );
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends Promocao> list, Promocao value, int index,
				boolean isSelected, boolean cellHasFocus) {
			display.setText("  " + value.getNome());
			display.isFocusable();

			return display;

		}
	}


	/**
	 * Activa o Nimbus Look and Feel
	 * 
	 */

	private void ativarNimbusLookAndFeel() {
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

	public JPanel returnAreaClienteMeusProdutos() {
		return  (JPanel) getContentPane();
	}

	public void enviarUsernameMeusProdutos(String username) {
		this.userName = username;

		try {

			Cliente cliente = GestorDeDAO.getGestorDeDAO().pesquisaClienteLogin(username);


			if(cliente != null) {

				if( cliente.getId_pacote_cliente()!= 0) {

					textAreaNomePacote.setVisible(true);
					textAreaDescricaoPacote.setVisible(true);
					lblSeuPacote.setText("Pacote Comercial");
					btnPesquisarPacotes.setVisible(false);

					PacoteComercial pacoteCliente = GestorDeDAO.getGestorDeDAO().getPacoteClienteInfo(cliente.getId_pacote_cliente());
					textAreaNomePacote.setText(pacoteCliente.getNome());
					textAreaDescricaoPacote.setText(pacoteCliente.getDescricao());

					List<Promocao> promocoesCliente = GestorDeDAO.getGestorDeDAO().getPacoteClientePromocaoInfo(cliente.getId_pacote_cliente());

					if(promocoesCliente.size() > 0 ) {

						textAreaDescricaoPromocoes.setVisible(true);
						comboBoxPromocoes.setVisible(true);
						lblAsSuasPromoces.setText("Promoções");
						btnPesquisarPromocoes.setVisible(false);

						comboBoxPromocoes.setRenderer(new PromocaoComboRenderer());
						for(Promocao pro : promocoesCliente) {
							comboBoxPromocoes.addItem(pro);
						}			
						Promocao promocao = (Promocao) comboBoxPromocoes.getSelectedItem();
						textAreaDescricaoPromocoes.setText(promocao.getDescricao());
						comboBoxPromocoes.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								Promocao promocao = (Promocao) comboBoxPromocoes.getSelectedItem();
								textAreaDescricaoPromocoes.setText(promocao.getDescricao());
							}
						});

					} else {
						textAreaDescricaoPromocoes.setVisible(false);
						comboBoxPromocoes.setVisible(false);
						lblAsSuasPromoces.setText("Não tem nenhuma Promoção atribuida");
						btnPesquisarPromocoes.setVisible(true);
					}

				}else {

					textAreaNomePacote.setVisible(false);
					textAreaDescricaoPacote.setVisible(false);
					lblSeuPacote.setText("Não tem nenhum Pacote Comercial atribuido");
					btnPesquisarPacotes.setVisible(true);

					textAreaDescricaoPromocoes.setVisible(false);
					comboBoxPromocoes.setVisible(false);
					lblAsSuasPromoces.setText("Não tem nenhuma Promoção atribuida");
					btnPesquisarPromocoes.setVisible(true);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public JButton getBtnPesquisarPacotes() {
		return btnPesquisarPacotes;
	}

	public JButton getBtnPesquisarPromocoes() {
		return btnPesquisarPromocoes;
	}


}// end class
