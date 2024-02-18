import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventosPanelPrincipal {
	private PanelPrincipal panel;
	public EventosPanelPrincipal(PanelPrincipal pp) {
		panel = pp;
		
		registrarEventos();
	}
	
	public void registrarEventos() {
		// Estos 2 botones se encargar de cambiar la pagina actual y volver a rellenar las tarjetas con las de la pagina que toque
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
		
		// Cuando se pulse enter en el textBox buscar se rellenaran los articulos con los procedentes de la busqueda y se rellenaran las tarjetas.
		panel.getTxtBuscar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Llama a tu función de base de datos aquí
				// Suponiendo que tu función se llama buscarEnBaseDeDatos
				if (!panel.getTxtBuscar().getText().equals("")) {
					panel.rellenarArticulos(panel.getTxtBuscar().getText(),panel.getTipo());
					panel.rellenarTarjetas();
				}

			}
		});
		
		panel.getBtnBuscar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Llama a tu función de base de datos aquí
				// Suponiendo que tu función se llama buscarEnBaseDeDatos
				if (!panel.getTxtBuscar().getText().equals("")) {
					panel.rellenarArticulos(panel.getTxtBuscar().getText(), panel.getTipo());
					panel.rellenarTarjetas();
				}
			}
		});
	}
}
