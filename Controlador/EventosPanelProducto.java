import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class EventosPanelProducto {
	private PanelProducto panel;
	
	public EventosPanelProducto(PanelProducto p) {
		panel = p;
		registrarEventos();
	}
	
	public void registrarEventos() {
		
		// Boton para añadir al carrito dependiendo de la cantidad seleccionada en el comboBox
		panel.getBtnComprar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (panel.getCbCantidad().getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(panel.getGestion(), "Seleccione una cantidad.");
					return;
				}
				for (int i = 0; i < panel.getCbCantidad().getSelectedIndex(); i++) {
					panel.getGestion().modificarCarro(panel.getProducto());
				}
				panel.getGestion().actualizarCarro();
				
				JOptionPane.showMessageDialog(panel.getGestion(), "Producto añadido al carrito.");
			}
		});
		
		// Boton para  volver al panel principal
		panel.getBtnAtras().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//panel.getGestion().getMainPanel().setVisible(true);
				panel.getGestion().setMiniaturas(false);
				panel.getGestion().getContentPane().remove(panel);
				panel.getGestion().getContentPane().add(panel.getGestion().getMainPanel());
				panel.getGestion().getContentPane().revalidate();
				panel.getGestion().getContentPane().repaint();
				panel.getGestion().getContentPane().revalidate();
				panel.getGestion().getMainPanel().repaint();
			}
		});
		

	}
}
