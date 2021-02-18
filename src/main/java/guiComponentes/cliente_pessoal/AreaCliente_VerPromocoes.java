package guiComponentes.cliente_pessoal;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JTextField;
import javax.swing.JScrollBar;

@SuppressWarnings("serial")
public class AreaCliente_VerPromocoes extends JFrame {

	//private JPanel panel;
	private JPanel panelVerTodasPromo;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AreaCliente_VerPromocoes frame = new AreaCliente_VerPromocoes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AreaCliente_VerPromocoes() {

		initialize();

	}

	private void initialize() {
		
		/**
		 * 
		 */
		ativarNimbusLookAndFeel();
		
		/**
		 * Define as caracteristicas dos painel base. 
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		panelVerTodasPromo = new JPanel();
		setContentPane(panelVerTodasPromo);
		panelVerTodasPromo.setLayout(null);
		getContentPane().setFont(new Font("Dubai", Font.PLAIN, 12));
		getContentPane().setBackground(SystemColor.text);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1384, 586);
		getContentPane().setLayout(null);
		
	
		
		// Labels e textField da página 
		JLabel labelVerPromo= new JLabel("Ver todas as Promoções:");
		labelVerPromo.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		labelVerPromo.setBounds(57, 101, 248, 23);
		panelVerTodasPromo.add(labelVerPromo);
		
		JLabel labelPacoteNome = new JLabel("Nome:");
		labelPacoteNome.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		labelPacoteNome.setBounds(360, 189, 71, 23);
		panelVerTodasPromo.add(labelPacoteNome);
		
		JLabel labelPromoDescricao = new JLabel("Descrição:");
		labelPromoDescricao.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		labelPromoDescricao.setBounds(360, 252, 99, 23);
		panelVerTodasPromo.add(labelPromoDescricao);
		
		textField = new JTextField();
		textField.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textField.setEditable(false);
		textField.setBounds(462, 191, 170, 29);
		panelVerTodasPromo.add(textField);
		textField.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textArea.setBounds(462, 252, 170, 103);
		panelVerTodasPromo.add(textArea);
		
		// Jlist e ScrollBar
		JList listVerPromo = new JList();
		listVerPromo.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		listVerPromo.setBounds(57, 162, 225, 328);
		panelVerTodasPromo.add(listVerPromo);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(57, 162, 225, 328);
		panelVerTodasPromo.add(scrollBar);
		
		

		
		/*
		 * Define a imagem de fundo através de uma label
		 */
		JLabel labelIconFundo = new JLabel("");
		labelIconFundo.setIcon(new ImageIcon(AreaCliente_VerPromocoes.class.getResource("/guiComponentes/img/AltranClientes.png")));
		labelIconFundo.setBounds(0, 0, 1368, 547);
		panelVerTodasPromo.add(labelIconFundo);


	}//end initialize



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


	public JPanel returnAreaClienteVerPromo() {
		return  panelVerTodasPromo;
	}
}// end class
