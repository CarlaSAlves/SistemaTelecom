package guiComponentes.cliente_pessoal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import guiComponentes.Admin_GUI_homepage;
import guiComponentes.GUI_total;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class AreaCliente extends JFrame {


	private static final long serialVersionUID = 1L;
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);	
	private JButton btAtualizarDados, btAtualizaDados;
	private JPanel panel;
	private JLabel lblUsernameLogged,lblTempoSessao,lblHoraSistema;
	private JPanel pane;
	private JPanel panelMeusDados;
	private JPanel panelDados2;
	private JPanel panelDados3;
	private JPanel panelDados4;
	private JTextField textFieldNome;
	private JTextField textFieldNIF;
	private JTextField textFieldLogin;
	private JTextField textFieldMorada;
	private JPanel panelProdutos; 

	private JTextArea textArea_2;
	private JLabel lblNewLabel_8;

	private JLabel lblNewLabel_6_2;
	private JButton btVoltarAreaCliente;
	private JButton btTerminarSessao;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//GUI_total guit = new GUI_total();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AreaCliente frame = new AreaCliente(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the frame.
	 * @param guit 
	 */
	public AreaCliente(GUI_total guit) {
		initialize(guit);
		ativarNimbusLookAndFeel();

	}

	private void initialize(GUI_total guit) {


		ativarNimbusLookAndFeel(); 


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(null);
		getContentPane().setFont(new Font("Dubai", Font.PLAIN, 12));
		getContentPane().setBackground(SystemColor.text);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		getContentPane().setLayout(null);

		// Label portal cliente

		JLabel lblPortalCliente = new JLabel("Portal Do Cliente");
		lblPortalCliente.setBounds(16, 44, 508, 33);
		lblPortalCliente.setForeground(new Color(70,74,101));
		lblPortalCliente.setFont(new Font("Dubai", Font.BOLD, 40));
		getContentPane().add(lblPortalCliente);

		JLabel lblBemVindo = new JLabel("Bem Vindo");
		lblBemVindo.setForeground(new Color(70,74,101));
		lblBemVindo.setFont(new Font("Dubai Light", Font.BOLD, 17));
		lblBemVindo.setBounds(16, 6, 104, 33);
		panel.add(lblBemVindo);


		// Construção JTabbedPane

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		tabbedPane.setBounds(0, 89, 1384, 586);
		panel.add(tabbedPane);

		// Separador os meus dados

		//Cliente_Dados clienteDados = new ClienteDados();
		//JPanelMeusDados = clienteDados.returnPenel
		//clienteDados.returnPenel() 
		panelMeusDados = new JPanel();
		tabbedPane.addTab("Meus Dados",null,  panelMeusDados);
		panelMeusDados.setLayout(null);
		panelMeusDados.setForeground(Color.BLUE);
		panelMeusDados.setFont(new Font("Dubai", Font.PLAIN, 12 ));


		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblNome.setBounds(76, 77, 69, 36);
		panelMeusDados.add(lblNome);

		textFieldNome = new JTextField();
		textFieldNome.setBounds(170, 77, 246, 36);
		panelMeusDados.add(textFieldNome);
		textFieldNome.setColumns(10);

		JLabel lblNIF = new JLabel("NIF:");
		lblNIF.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblNIF.setBounds(76, 138, 69, 36);
		panelMeusDados.add(lblNIF);

		textFieldNIF = new JTextField();
		textFieldNIF.setColumns(10);
		textFieldNIF.setBounds(170, 138, 246, 36);
		panelMeusDados.add(textFieldNIF);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblLogin.setBounds(76, 205, 69, 36);
		panelMeusDados.add(lblLogin);

		textFieldLogin = new JTextField();
		textFieldLogin.setColumns(10);
		textFieldLogin.setBounds(170, 205, 246, 36);
		panelMeusDados.add(textFieldLogin);

		JLabel lblMorada = new JLabel("Morada");
		lblMorada.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblMorada.setBounds(76, 268, 81, 36);
		panelMeusDados.add(lblMorada);

		textFieldMorada = new JTextField();
		textFieldMorada.setColumns(10);
		textFieldMorada.setBounds(170, 272, 246, 36);
		panelMeusDados.add(textFieldMorada);

		btAtualizarDados = new JButton("Atualizar dados");
		btAtualizarDados.setBounds(200, 362, 180, 50);
		panelMeusDados.add(btAtualizarDados);
		btAtualizarDados.setForeground(Color.DARK_GRAY);
		btAtualizarDados.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btAtualizarDados.setFocusPainted(false);

		//Imagem de fundo - Provisoria

		JLabel imagemDados = new JLabel("");
		imagemDados.setBounds(6, -41, 1394, 598);
		panelMeusDados.add(imagemDados);
		imagemDados.setBackground(new Color(240, 240, 240));
		imagemDados.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/fundoAltran.png")));


		/****************************/
		// OS meus produtos
		/****************************/

		JPanel panelMeusProdutos = new JPanel();
		tabbedPane.addTab("Os Meus produtos",null,  panelMeusProdutos);
		panelMeusProdutos.setLayout(null);

		JComboBox comboBoxPromocoes = new JComboBox();
		comboBoxPromocoes.setBounds(472, 157, 234, 41);
		panelMeusProdutos.add(comboBoxPromocoes);

		JLabel lblAsSuasPromoes = new JLabel("As suas Promoções");
		lblAsSuasPromoes.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		lblAsSuasPromoes.setBounds(472, 109, 219, 36); 
		panelMeusProdutos.add(lblAsSuasPromoes);

		JTextArea textAreaDescricaoPromocoes = new JTextArea();
		textAreaDescricaoPromocoes.setBounds(472, 218, 234, 92);
		panelMeusProdutos.add(textAreaDescricaoPromocoes);

		JTextArea textAreaDescricaoPacote = new JTextArea();
		textAreaDescricaoPacote.setBounds(157, 218, 234, 92);
		panelMeusProdutos.add(textAreaDescricaoPacote);

		JTextArea textAreaNomePacote = new JTextArea();
		textAreaNomePacote.setBounds(157, 161, 234, 41);
		panelMeusProdutos.add(textAreaNomePacote);

		JLabel lblDescricaoPacote = new JLabel("Descrição:");
		lblDescricaoPacote.setBounds(55, 214, 95, 36);
		lblDescricaoPacote.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		panelMeusProdutos.add(lblDescricaoPacote);

		JLabel lblNomePacote = new JLabel("Nome:");
		lblNomePacote.setBounds(55, 157, 69, 36);
		lblNomePacote.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		panelMeusProdutos.add(lblNomePacote);

		JLabel lblSeuPacote = new JLabel("O seu Pacote Comercial:");
		lblSeuPacote.setBounds(55, 109, 219, 36);
		lblSeuPacote.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		panelMeusProdutos.add(lblSeuPacote);

		//Imagem de fundo - Provisoria
		JLabel imagemProdutos = new JLabel("");
		imagemProdutos.setBounds(6, -41, 1394, 598);
		panelMeusProdutos.add(imagemProdutos);
		imagemProdutos.setBackground(new Color(240, 240, 240));
		imagemProdutos.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/fundoAltran.png")));



		/****************************/
		// Separador Pacotes
		/****************************/


		JPanel panelVerTodosPacotes = new JPanel();
		tabbedPane.addTab("Ver todos os pacotes comercial",null,  panelVerTodosPacotes);
		panelVerTodosPacotes.setLayout(null);
		
		JPanel panel_VerTodosPacotes = new JPanel();
		panel_VerTodosPacotes.setBounds(86, 131, 246, 342);
		panelVerTodosPacotes.add(panel_VerTodosPacotes);
		panel_VerTodosPacotes.setLayout(null);
		
		JScrollPane scrollPaneVerTodosPacotes = new JScrollPane();
		scrollPaneVerTodosPacotes.setBounds(0, 0, 244, 343);
		panel_VerTodosPacotes.add(scrollPaneVerTodosPacotes);
		
		JList listVerTodoosPacotes = new JList();
		listVerTodoosPacotes.setBounds(0, 0, 1, 1);
		panel_VerTodosPacotes.add(listVerTodoosPacotes);
		
		JPanel panel_VerTodosPacotes_1 = new JPanel();
		panel_VerTodosPacotes_1.setLayout(null);
		panel_VerTodosPacotes_1.setBounds(0, 0, 246, 342);
		panel_VerTodosPacotes.add(panel_VerTodosPacotes_1);
		
		JScrollPane scrollPaneVerTodosPacotes_1 = new JScrollPane();
		scrollPaneVerTodosPacotes_1.setBounds(0, 0, 244, 343);
		panel_VerTodosPacotes_1.add(scrollPaneVerTodosPacotes_1);
		
		JList listVerTodoosPacotes_1 = new JList();
		listVerTodoosPacotes_1.setBounds(0, 0, 1, 1);
		panel_VerTodosPacotes_1.add(listVerTodoosPacotes_1);
		
		JLabel lblDescricaoVerTodosPacotes = new JLabel("Descrição:");
		lblDescricaoVerTodosPacotes.setBounds(406, 193, 83, 32);
		panelVerTodosPacotes.add(lblDescricaoVerTodosPacotes);
		lblDescricaoVerTodosPacotes.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		
		JLabel lblNomeVerTodosPacotes = new JLabel("Nome:");
		lblNomeVerTodosPacotes.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblNomeVerTodosPacotes.setBounds(406, 136, 69, 36);
		panelVerTodosPacotes.add(lblNomeVerTodosPacotes);
		
		JLabel lblVerTodosPacotesComerciais = new JLabel("Ver todas os Pacotes Comerciais:");
		lblVerTodosPacotesComerciais.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		lblVerTodosPacotesComerciais.setBounds(88, 83, 270, 36);
		panelVerTodosPacotes.add(lblVerTodosPacotesComerciais);
		
		JTextArea textAreaVerDescricaoPacote = new JTextArea();
		textAreaVerDescricaoPacote.setBounds(488, 195, 234, 141);
		panelVerTodosPacotes.add(textAreaVerDescricaoPacote);
		
		JTextArea textAreaNomeVerPacote = new JTextArea();
		textAreaNomeVerPacote.setBounds(488, 131, 234, 41);
		panelVerTodosPacotes.add(textAreaNomeVerPacote);

		//Imagem de fundo - Provisoria
		JLabel imagemVerPacotes = new JLabel("");
		imagemVerPacotes.setBounds(6, -41, 1394, 598);
		panelVerTodosPacotes.add(imagemVerPacotes);
		imagemVerPacotes.setBackground(new Color(240, 240, 240));
		imagemVerPacotes.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/fundoAltran.png")));



		/****************************/
		// Separador Promoções
		/****************************/


		JPanel panelVerTodasPromocoes = new JPanel();
		tabbedPane.addTab("Ver todas as promoções",null,  panelVerTodasPromocoes);
		panelVerTodasPromocoes.setLayout(null);
				
				JTextArea textAreaNomeVerPacote_1 = new JTextArea();
				textAreaNomeVerPacote_1.setBounds(500, 147, 234, 41);
				panelVerTodasPromocoes.add(textAreaNomeVerPacote_1);
				
				JTextArea textAreaVerDescricaoPromocoes = new JTextArea();
				textAreaVerDescricaoPromocoes.setBounds(500, 216, 234, 141);
				panelVerTodasPromocoes.add(textAreaVerDescricaoPromocoes);
				
				JLabel lblVerTodasPromocoes = new JLabel("Ver todas as Promoções:");
				lblVerTodasPromocoes.setBounds(103, 88, 246, 36);
				panelVerTodasPromocoes.add(lblVerTodasPromocoes);
				lblVerTodasPromocoes.setFont(new Font("Dubai Light", Font.PLAIN, 20));
				
				JLabel lblNomeVerTodasPromocoes = new JLabel("Nome:");
				lblNomeVerTodasPromocoes.setFont(new Font("Dubai Light", Font.PLAIN, 18));
				lblNomeVerTodasPromocoes.setBounds(405, 143, 69, 36);
				panelVerTodasPromocoes.add(lblNomeVerTodasPromocoes);
				
				JLabel lblDescricaoVerTodasPromocoes = new JLabel("Descrição:");
				lblDescricaoVerTodasPromocoes.setFont(new Font("Dubai Light", Font.PLAIN, 18));
				lblDescricaoVerTodasPromocoes.setBounds(405, 214, 83, 32);
				panelVerTodasPromocoes.add(lblDescricaoVerTodasPromocoes);
				
				JPanel panel_VerTodasPromocoes = new JPanel();
				panel_VerTodasPromocoes.setLayout(null);
				panel_VerTodasPromocoes.setBounds(103, 143, 246, 342);
				panelVerTodasPromocoes.add(panel_VerTodasPromocoes);
				
				JList listVerTodoosPacotes_2 = new JList();
				listVerTodoosPacotes_2.setBounds(0, 0, 1, 1);
				panel_VerTodasPromocoes.add(listVerTodoosPacotes_2);
				
				JPanel panel_VerTodosPacotes_1_1 = new JPanel();
				panel_VerTodosPacotes_1_1.setLayout(null);
				panel_VerTodosPacotes_1_1.setBounds(0, 0, 246, 342);
				panel_VerTodasPromocoes.add(panel_VerTodosPacotes_1_1);
				
				JList listVerTodoosPacotes_1_1 = new JList();
				listVerTodoosPacotes_1_1.setBounds(0, 0, 1, 1);
				panel_VerTodosPacotes_1_1.add(listVerTodoosPacotes_1_1);
		
				//Imagem de fundo - Provisoria
				JLabel imagemVerPromocoes = new JLabel("");
				imagemVerPromocoes.setBounds(6, -41, 1394, 598);
				panelVerTodasPromocoes.add(imagemVerPromocoes);
				imagemVerPromocoes.setBackground(new Color(240, 240, 240));
				imagemVerPromocoes.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/fundoAltran.png")));
				
				JScrollPane scrollPaneVerTodosPacotes_2 = new JScrollPane();
				scrollPaneVerTodosPacotes_2.setBounds(91, 123, 244, 343);
				panelVerTodasPromocoes.add(scrollPaneVerTodosPacotes_2);
				
				JScrollPane scrollPaneVerTodosPacotes_1_1 = new JScrollPane();
				scrollPaneVerTodosPacotes_1_1.setBounds(91, 123, 244, 343);
				panelVerTodasPromocoes.add(scrollPaneVerTodosPacotes_1_1);




		// Botão Atualiza dados 

		//		btAtualizaDados = new JButton("Atualizar Dados");
		//		btAtualizaDados.setBounds(468, 27, 268, 50);
		//		btAtualizaDados.setForeground(Color.DARK_GRAY);
		//		btAtualizaDados.setToolTipText("Atualize os seus dados aqui.");	
		//		btAtualizaDados.setFocusPainted(false);
		//		btAtualizaDados.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		//		getContentPane().add(btAtualizaDados);




		/* RUDAPÉ */
		//Botão Termina sessão

		btTerminarSessao = new JButton("Terminar Sessão");
		btTerminarSessao.setForeground(Color.DARK_GRAY);
		btTerminarSessao.setBounds(16, 687, 180, 50);
		btTerminarSessao.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btTerminarSessao.setFocusPainted(false);
		getContentPane().add(btTerminarSessao);


		// Logotipo no rodapé

		JLabel lblFooter = new JLabel("");
		lblFooter.setForeground(new Color(0, 0, 0));
		lblFooter.setBounds(599, 690, 214, 65);
		lblFooter.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/Altran1.1.png")));
		getContentPane().add(lblFooter);
		
		setupTempoSessao();



		//		JLabel icon = new JLabel("");
		//		icon.setBounds(0, 89, 1394, 586);
		//		icon.setBackground(new Color(240, 240, 240));
		//		icon.setIcon(new ImageIcon(AreaCliente.class.getResource("/guiComponentes/img/AltranClientes.png")));
		//		getContentPane().add(icon);


		//TODO criar os action listener Atualizar dados/ Ver Pacotes / Ver Promoções


		btTerminarSessao.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});


	}//end initialize method



	// TODO  Coloca a lable de boas vindas

	protected void setupTempoSessao() {
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setForeground(Color.BLACK);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setBounds(1215, 699, 166, 16);
		panel.add(lblUsernameLogged);
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 10));

		lblTempoSessao = new JLabel();
		lblTempoSessao.setForeground(Color.BLACK);
		lblTempoSessao.setText("Sessao:");
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		panel.add(lblTempoSessao);
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 10));

		lblHoraSistema = new JLabel();
		lblHoraSistema.setForeground(Color.BLACK);
		lblHoraSistema.setBounds(1215, 737, 159, 18);
		panel.add(lblHoraSistema);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 10));


	}


	public void setLableBoasVindas(String name) {
		//Passos a fazer: guitOTAL - SETAR A LBL DE BOAS VINDAS no método labelUsernameNavegaPaginas
		//Funcionario DAO copiar o método pesquisar funcionarioAdmin - Copiar e modificar para pesquisa funcionario por login ()
		//Acrescentar o novo método gestor dao
		//GUItotal altear pesquisafuncionario
		// funcionario f = gestorDeDaoget...pesquisaFuncionario  Admin

	}



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
	
	
	
	
	
	/*Metodos usados pelo GUI Total para o funcionameto dos botões*/
	/**
     * @return the labelBoasVindas
     */
//    public void setLabelBoasVindas(String username) {
//        labelBoasVindas.setText( username );;
//    }

 
    public JPanel returnPanel() {
        return (JPanel) getContentPane();
    }

    public void setUsernameLoggedIn(String username) {
        lblUsernameLogged.setText("Username: " + username);
    }

    public void setLblTempoSessao(Duration temporizador) {
        lblTempoSessao.setText("Sessao: " + temporizador.toMinutesPart() + ":" + temporizador.toSecondsPart()); ;
    }

    public void setLblHoraSistema(String agora) {
        lblHoraSistema.setText("Data: " + agora);

 

    }
}
