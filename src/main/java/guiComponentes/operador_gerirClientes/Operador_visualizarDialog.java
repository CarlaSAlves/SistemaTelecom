package guiComponentes.operador_gerirClientes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import standard_value_object.PacoteComercial;
import standard_value_object.Promocao;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class Operador_visualizarDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNome;
	private JTextArea textAreaDescricao;
	private JLabel labelTitulo ;
	private boolean modoPromocao = false;


	/**
	 * @param promocoes
	 * @param modoPromocao
	 * @param nome
	 */
	@SuppressWarnings("unchecked")
	public Operador_visualizarDialog(List<Promocao> promocoes, boolean modoPromocao, String nome) {
		this();
		this.modoPromocao = modoPromocao;
		if(this.modoPromocao) {

			textFieldNome.setVisible(false);
			@SuppressWarnings("rawtypes")
			JComboBox comboBox = new JComboBox();
			comboBox.setRenderer(new PromocaoComboRenderer());
			for(Promocao p : promocoes) {
				comboBox.addItem(p);
			}
			comboBox.setBounds(86, 54, 437, 28);
			contentPanel.add(comboBox);
			Promocao promocao = (Promocao) comboBox.getSelectedItem();
			textAreaDescricao.setText(promocao.getDescricao());	

			comboBox.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					Promocao promocao = (Promocao) comboBox.getSelectedItem();
					textAreaDescricao.setText(promocao.getDescricao());	
				}
			});

			setTitle("Visualizar Promoções");
			labelTitulo.setText("Promoções do Cliente: " + nome);

		}
	}

	/**
	 * Settings for visualization mode
	 */
	public Operador_visualizarDialog() {

		setBounds(500, 300, 549, 300);
		setResizable(false);
		setTitle("");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		labelTitulo = new JLabel("");
		labelTitulo.setForeground(Color.BLACK);
		labelTitulo.setFont(new Font("Dubai Light", Font.BOLD, 13));
		labelTitulo.setBounds(10, 11, 513, 32);
		contentPanel.add(labelTitulo);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		lblNome.setBounds(10, 53, 85, 28);
		contentPanel.add(lblNome);

		JLabel lblDescricao = new JLabel("Descrição");
		lblDescricao.setFont(new Font("Dubai Light", Font.PLAIN, 12));
		lblDescricao.setBounds(10, 86, 87, 28);
		contentPanel.add(lblDescricao);

		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 11));
		textFieldNome.setBounds(86, 54, 437, 28);
		textFieldNome.setEditable(false);
		contentPanel.add(textFieldNome);
		textFieldNome.setColumns(10);

		textAreaDescricao = new JTextArea();
		textAreaDescricao.setFont(new Font("Dubai Light", Font.PLAIN, 11));
		textAreaDescricao.setBounds(86, 92, 437, 125);
		textAreaDescricao.setEditable(false);
		contentPanel.add(textAreaDescricao);

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Color.WHITE);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton cancelButton = new JButton("Sair");
		cancelButton.setActionCommand("Cancelar");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();						
			}
		});
		buttonPane.add(cancelButton);
	}

	/**
	 * Settings for the pacote comercial visualization mode 
	 * @param pacoteComercial
	 * @param nome
	 */
	public Operador_visualizarDialog(PacoteComercial pacoteComercial, String nome) {
		this();
		textFieldNome.setText(pacoteComercial.getNome());
		textAreaDescricao.setText(pacoteComercial.getDescricao());
		setTitle("Visualizar Pacote Comercial");
		labelTitulo.setText("Pacote Comercial do Cliente: " + nome);
	}

	/**
	 * Creates the renderer for the list of promoção
	 *
	 */
	class PromocaoComboRenderer implements ListCellRenderer<Promocao> {

		private JLabel display;

		PromocaoComboRenderer(){
			display = new JLabel();
			display.setFont(new Font("Dubai Light", Font.PLAIN, 11));
			display.setOpaque( true );
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends Promocao> list, Promocao value, int index,
				boolean isSelected, boolean cellHasFocus) {
			display.setText("  " + value.getNome());
			if (isSelected) {
				display.setBackground(new Color(250,235,70));
			} else 
				display.setBackground(null);
			return display;
		}
	}
}

