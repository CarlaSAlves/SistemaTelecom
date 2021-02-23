package guiComponentes.cliente_pessoal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import guiComponentes.Admin_GUI_homepage;
import servico.GestorDeDAO;
import standard_value_object.Cliente;
import standard_value_object.PacoteComercial;
import javax.swing.JTextField;
import javax.swing.JScrollBar;

public class AreaCliente_VerPacotes extends JFrame {

	//private JPanel panel;
	private JPanel panelVerTodosPacotes;
	private JTextField textFieldNome;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AreaCliente_VerPacotes frame = new AreaCliente_VerPacotes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public AreaCliente_VerPacotes() {

		try {
			initialize();
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

	private void initialize() throws Exception {
		
		/**
		 * 
		 */
		ativarNimbusLookAndFeel();
		
		/**
		 * Define as caracteristicas dos painel base. 
		 */
		panelVerTodosPacotes = new JPanel();
		panelVerTodosPacotes.setLayout(null);
		setContentPane(panelVerTodosPacotes);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1384, 586);
		

		// Labels e textFieldNome da página 
		
		JLabel labelVerPacotes = new JLabel("Ver todos os Pacotes Comerciais");
		labelVerPacotes.setForeground(Color.WHITE);
		labelVerPacotes.setFont(new Font("Dubai Light", Font.BOLD, 22));
		labelVerPacotes.setBounds(66, 54, 318, 28);
		panelVerTodosPacotes.add(labelVerPacotes);
		
		JLabel labelPacoteNome = new JLabel("Nome");
		labelPacoteNome.setForeground(Color.WHITE);
		labelPacoteNome.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		labelPacoteNome.setBounds(319, 169, 62, 31);
		panelVerTodosPacotes.add(labelPacoteNome);
		
		JLabel labelPacoteDescricao = new JLabel("Descrição");
		labelPacoteDescricao.setForeground(Color.WHITE);
		labelPacoteDescricao.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		labelPacoteDescricao.setBounds(319, 223, 87, 23);
		panelVerTodosPacotes.add(labelPacoteDescricao);
		
		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		textFieldNome.setEditable(false);
		textFieldNome.setBounds(400, 169, 300, 31);
		panelVerTodosPacotes.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		textArea.setBounds(400, 222, 300, 104);
		panelVerTodosPacotes.add(textArea);
		
		// Jlist e ScrollBar
		
		String[] pacotes = new String[GestorDeDAO.getGestorDeDAO().getAllPacotesComerciais().size()];
		int i = 0;
		for (PacoteComercial pacote : GestorDeDAO.getGestorDeDAO().getAllPacotesComerciais()) {
			if (pacote.isAtivo()) {
			pacotes[i] = pacote.getNome();
			i++;
			}
		}
		
		
		JList<String> listVerPacotes = new JList<String>(pacotes);
		listVerPacotes.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		listVerPacotes.setBounds(66, 120, 226, 362);
		listVerPacotes.setSelectedIndex(0);
		listVerPacotes.setForeground(Color.BLACK);
		listVerPacotes.setLayoutOrientation( JList.HORIZONTAL_WRAP );
		listVerPacotes.setVisibleRowCount( -1 ); // -1 sig q ele é variavel

		listVerPacotes.setFixedCellHeight( 24 );
		listVerPacotes.setFixedCellWidth( 226 );
		panelVerTodosPacotes.add(listVerPacotes);
		
		String texto = listVerPacotes.getSelectedValue();
		textFieldNome.setText((texto));
		textArea.setText(texto);
		
		listVerPacotes.addListSelectionListener(new ListSelectionListener() {
		
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					String texto = listVerPacotes.getSelectedValue();
					try {
						textFieldNome.setText((texto));
						textArea.setText(texto);
					} catch (Exception ex) {

					}
					return;
				}
				
			}
		});
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(66, 120, 226, 362);
		panelVerTodosPacotes.add(scrollBar);
		
			
		/*
		 * Define a imagem de fundo através de uma label
		 */
		JLabel labelIconFundo = new JLabel("");
		labelIconFundo.setIcon(new ImageIcon(AreaCliente_VerPacotes.class.getResource("/guiComponentes/img/AltranClientes.png")));
		labelIconFundo.setBounds(0, -37, 1408, 586);
		getContentPane().add(labelIconFundo);


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


	public JPanel returnAreaClienteVerPacotes() {
		return (JPanel) getContentPane();
	}
}// end class
