import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;

public class EventosGestionClientes {
	private GestionClientes gestionC;
	public EventosGestionClientes(GestionClientes g) {
		gestionC = g;
		registrarEventos(); 
	}

	// En esta funcion se crean los eventos de la ventanita de busqueda cuando sale.
	public void crearEventosBusqueda(JTextField txt1, JTextField txt2, JTextField txt3, DefaultListModel<String> lista, JButton btn1, JButton btn2, JButton btn3) {
		//Busqueda por nombre, llama a la funcion y actualiza la tabla con el resultado.
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!txt1.getText().equals("")) {
					ResultSet rs = gestionC.getGestion().getLogin().getBBDD().buscarClienteNombre(txt1.getText());
					gestionC.actualizarTabla(rs, lista);
				}
			}
		});
		//Busqueda por dni, llama a la funcion y actualiza la tabla con el resultado.
		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!txt2.getText().equals("")) {
					ResultSet rs = gestionC.getGestion().getLogin().getBBDD().buscarClienteDni(txt2.getText());
					gestionC.actualizarTabla(rs, lista);
				}
			}
		});
		//Busqueda por id, llama a la funcion y actualiza la tabla con el resultado.
		btn3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!txt3.getText().equals("")) {
					ResultSet rs = gestionC.getGestion().getLogin().getBBDD().buscarClienteId(Integer.valueOf(txt3.getText()));
					gestionC.actualizarTabla(rs, lista);
				}	
			}
		});
	}

	// En esta funcion se crean los eventos de la lista de busqueda cuando sale. Al hacer doble click, rellenara los campos de la ventana principal.
	// Y se mostrara la ventana principal removiendo este panel de busqueda y mostrando el panel principal.
	public void crearEventosLista(JList<String> lista) {
		lista.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				//tarjeta.getPanel().setVisible(false);
				if (evt.getClickCount() == 2) {
					ResultSet rs = gestionC.getGestion().getLogin().getBBDD().getUsuario(lista.getSelectedValue().split(" - ")[0]);
					gestionC.actualizarCampos(rs);
					gestionC.add(gestionC.getPanelPrincipal(),BorderLayout.CENTER);
					gestionC.remove(gestionC.getPanelBusqueda());
					gestionC.revalidate();
					gestionC.repaint();
					gestionC.getBtnModificar().setEnabled(true);
					gestionC.getBtnBorrar().setEnabled(true);
					gestionC.getBtnCrear().setEnabled(false);
					gestionC.getTxtDni().setEditable(false);
					gestionC.getBtnVolver().setEnabled(false);
				}
			}
		});
	}

	// Por ultimo los eventos generales de la ventana principal. Buscar que muestra el panel de busqueda, nuevo que vacia todos los campos
	// Y los tipicos de crear, modificar y borrar. Que realizan dichas funciones en la base de datos.
	// En nuestro caso Borrar no hace un delete si no que lo poen como activo, ya que tenemos que guardar la informacion de nuestros clientes para sus ventas.
	public void registrarEventos() {
		gestionC.getBtnBuscar().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gestionC.remove(gestionC.getPanelPrincipal());
				gestionC.setPanelBusqueda(gestionC.crearPanelBusqueda());
				gestionC.add(gestionC.getPanelBusqueda(),BorderLayout.CENTER);
				gestionC.revalidate();
				gestionC.repaint();
				gestionC.getBtnModificar().setEnabled(false);
				gestionC.getBtnBorrar().setEnabled(false);
				gestionC.getBtnCrear().setEnabled(false);
				gestionC.getBtnVolver().setEnabled(true);

			}
		});

		gestionC.getBtnNuevo().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gestionC.limpiarCampos();
				gestionC.getBtnModificar().setEnabled(false);
				gestionC.getBtnBorrar().setEnabled(false);
				gestionC.getBtnCrear().setEnabled(true);
				gestionC.getTxtDni().setEditable(true);
			}
		});

		gestionC.getBtnCrear().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (gestionC.validarCampos()) {
					gestionC.getGestion().getLogin().getBBDD().crearCliente(gestionC.getTxtNombre().getText(), gestionC.getTxtApellido1().getText(), gestionC.getTxtApellido2().getText(), gestionC.getTxtDni().getText(),  gestionC.getTxtDireccion().getText(), gestionC.getTxtCp().getText(), gestionC.getTxtLocalidad().getText(),  gestionC.getTxtTelefono().getText(), gestionC.getTxtEmail().getText());
					gestionC.limpiarCampos();
					gestionC.getBtnModificar().setEnabled(false);
					gestionC.getBtnBorrar().setEnabled(false);
					gestionC.getBtnCrear().setEnabled(true);
					gestionC.getTxtDni().setEditable(true);;
				}
			}
		});

		gestionC.getBtnModificar().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (gestionC.validarCampos()) {
					gestionC.getGestion().getLogin().getBBDD().modificarCliente(Integer.valueOf(gestionC.getTxtId().getText()) ,gestionC.getTxtNombre().getText(), gestionC.getTxtApellido1().getText(), gestionC.getTxtApellido2().getText(), gestionC.getTxtDireccion().getText(), gestionC.getTxtCp().getText(), gestionC.getTxtLocalidad().getText(),  gestionC.getTxtTelefono().getText(), gestionC.getTxtEmail().getText());
				}
			}
		});

		gestionC.getBtnBorrar().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gestionC.getGestion().getLogin().getBBDD().darBajaCliente(Integer.valueOf(gestionC.getTxtId().getText()));
				gestionC.limpiarCampos();
				gestionC.getBtnModificar().setEnabled(false);
				gestionC.getBtnBorrar().setEnabled(false);
				gestionC.getBtnCrear().setEnabled(true);
			}
		});
		
		gestionC.getBtnVolver().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gestionC.add(gestionC.getPanelPrincipal(),BorderLayout.CENTER);
				gestionC.remove(gestionC.getPanelBusqueda());
				gestionC.revalidate();
				gestionC.repaint();
				gestionC.getBtnModificar().setEnabled(true);
				gestionC.getBtnBorrar().setEnabled(true);
				gestionC.getBtnCrear().setEnabled(false);
				gestionC.getTxtDni().setEditable(false);
				gestionC.getBtnVolver().setEnabled(false);
			}
		});

	}

}
