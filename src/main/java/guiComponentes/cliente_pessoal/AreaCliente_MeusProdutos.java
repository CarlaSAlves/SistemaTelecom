package guiComponentes.cliente_pessoal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class AreaCliente_MeusProdutos extends JFrame {

	private JPanel panelMeusProdutos;
	private JTextArea textAreaNomePacote;
	private JTextArea textAreaDescricaoPacote;

	private JTextArea textAreaDescricaoPromocoes;

	private String userName;
	private JComboBox<Promocao> comboBoxPromocoes;



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

		// Área Pacote Comercial

		JLabel lblSeuPacote = new JLabel("O seu Pacote Comercial");
		lblSeuPacote.setBounds(55, 109, 219, 36);
		lblSeuPacote.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		panelMeusProdutos.add(lblSeuPacote);

		JLabel lblNomePacote = new JLabel("Nome:");
		lblNomePacote.setBounds(55, 157, 69, 36);
		lblNomePacote.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		panelMeusProdutos.add(lblNomePacote);

		textAreaNomePacote = new JTextArea();
		textAreaNomePacote.setBounds(157, 161, 234, 41);
		panelMeusProdutos.add(textAreaNomePacote);

		JLabel lblDescricaoPacote = new JLabel("Descrição");
		lblDescricaoPacote.setBounds(55, 214, 95, 36);
		lblDescricaoPacote.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		panelMeusProdutos.add(lblDescricaoPacote);

		textAreaDescricaoPacote = new JTextArea();
		textAreaDescricaoPacote.setBounds(157, 218, 234, 92);
		panelMeusProdutos.add(textAreaDescricaoPacote);



		// Promoções
		JLabel lblAsSuasPromoces = new JLabel("As suas Promoções");
		lblAsSuasPromoces.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		lblAsSuasPromoces.setBounds(472, 109, 219, 36); 
		panelMeusProdutos.add(lblAsSuasPromoces);

		textAreaDescricaoPromocoes = new JTextArea();
		textAreaDescricaoPromocoes.setBounds(472, 218, 234, 92);
		panelMeusProdutos.add(textAreaDescricaoPromocoes);

		comboBoxPromocoes = new JComboBox<Promocao>();
		comboBoxPromocoes.setBounds(472, 161, 234, 41);
		panelMeusProdutos.add(comboBoxPromocoes);
		
		//Imagem fundo

		JLabel imagemDados = new JLabel("");
		imagemDados.setBounds(0, 0, 1432, 547);
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
				display.setFont(new Font("Tahoma", Font.PLAIN, 11));
				display.setOpaque( true );
			}
	
			@Override
			public Component getListCellRendererComponent(JList<? extends Promocao> list, Promocao value, int index,
					boolean isSelected, boolean cellHasFocus) {
				display.setText("  " + value.getNome());
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
		
		
		comboBoxPromocoes.setRenderer(new PromocaoComboRenderer());
		


		try {

			Cliente cliente = GestorDeDAO.getGestorDeDAO().pesquisaClienteLogin(username);
			if(cliente.getId_pacote_cliente()!= 0) {
				PacoteComercial pacoteCliente = GestorDeDAO.getGestorDeDAO().getPacoteClienteInfo(cliente.getId_pacote_cliente());
				List<Promocao> promocoesCliente = GestorDeDAO.getGestorDeDAO().getPacoteClientePromocaoInfo(cliente.getId_pacote_cliente());

				textAreaNomePacote.setText(pacoteCliente.getNome());
				textAreaDescricaoPacote.setText(pacoteCliente.getDescricao());


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


			}
		} catch (Exception e) {

			e.printStackTrace();

		}


	}

}// end class
