import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventosPanelPrincipal {
	private PanelPrincipal panel;
	public EventosPanelPrincipal(PanelPrincipal pp) {
		panel = pp;
		
		registrarEventos();
	}
	
	public void registrarEventos() {
		panel.getBtnPaginaAnterior().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.setPaginaActual(panel.getPaginaActual() - 1);
				panel.rellenarTarjetas();
			}
		});
		
		panel.getBtnPaginaSiguiente().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.setPaginaActual(panel.getPaginaActual() + 1);
				panel.rellenarTarjetas();
			}
		});

		panel.getTxtBuscar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Llama a tu función de base de datos aquí
				// Suponiendo que tu función se llama buscarEnBaseDeDatos
				panel.rellenarArticulos(panel.getTxtBuscar().getText(),panel.getTipo());
				panel.rellenarTarjetas();
			}
		});
	}
}
