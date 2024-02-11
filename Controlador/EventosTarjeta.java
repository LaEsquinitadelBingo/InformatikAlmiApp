import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class EventosTarjeta {
    private Tarjeta tarjeta;
    public EventosTarjeta(Tarjeta t) {
        tarjeta = t;
        registrarEventos(); 
    }

    public void registrarEventos() {
        tarjeta.getComprar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                tarjeta.getPanel().getGestion().modificarCarro(tarjeta.getProducto());
                tarjeta.getPanel().getGestion().actualizarCarro();
                JOptionPane.showMessageDialog(tarjeta.getPanel().getGestion(), "Producto añadido al carrito.");
            }
        });
        tarjeta.getFoto().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //tarjeta.getPanel().setVisible(false);
                tarjeta.getPanel().getGestion().getContentPane().remove(tarjeta.getPanel());
                try {
                	tarjeta.getPanel().getGestion().setPanelProducto(new PanelProducto(tarjeta.getProducto(), tarjeta.getPanel().getGestion()));
					tarjeta.getPanel().getGestion().getContentPane().add(tarjeta.getPanel().getGestion().getPanelProducto(), BorderLayout.CENTER);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                tarjeta.getPanel().getGestion().setMiniaturas(false);
                tarjeta.getPanel().getGestion().getContentPane().revalidate();
                tarjeta.getPanel().getGestion().getContentPane().repaint();
            }
        });
    }
}
