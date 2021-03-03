package guiComponentes.admin_gestorCliente;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import historicos.HistoricoCliente;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import standard_value_object.Cliente;

public class HistoricoClienteDialog extends JDialog {


	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JLabel clienteRegistosLabel;


	public HistoricoClienteDialog() {

		setTitle("Histórico de Registos");
		setModal(true);
		setBounds(200, 100, 651, 300);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setResizable(false);
		contentPanel.setLayout(new BorderLayout(0, 0));

		// Painel de histórico 
		// history panel
		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel labelHistorico = new JLabel("Histórico do Cliente :");
		panel.add(labelHistorico);

		clienteRegistosLabel = new JLabel("");
		panel.add(clienteRegistosLabel);

		// Tabela e Scrollpane de histórico 
		//table and scrollpanel for the history
		
		JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);

		// Painel de rodapé - botão sair 
		// baseboard , leave button
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			JButton okButton = new JButton("Sair");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					dispose();
				}
			});
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}
	}		


	/**
	 * Método que preenche a tabela com o registos
	 * method that fills the table with data
	 * @param cliente
	 * @param historicoCliente
	 */
	public void preencherTable(Cliente cliente,List<HistoricoCliente> historicoCliente) {
		clienteRegistosLabel.setText(cliente.getNome());

		HistoricoClienteTableModel model = new HistoricoClienteTableModel(historicoCliente); 

		table.setModel(model);

		TableCellRenderer tableCellRenderer = new DateTimeCellRenderer();
		table.getColumnModel().getColumn(HistoricoClienteTableModel.DATA_COL).setCellRenderer(tableCellRenderer);		

	}

	/**
	 * Renderer da tabela
	 * table renderer
	 */
	private final class DateTimeCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 5466469925138317415L;
		SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if( value instanceof Date) {
				value = f.format(value);
			}
			return super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
		}
	}

} // end class
