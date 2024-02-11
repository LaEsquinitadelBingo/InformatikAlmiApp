import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class EventosLogin {
	private Login login;
	
	public EventosLogin(Login l) {
		login = l;
		registrarEventos();
	}
	
	public void registrarEventos() {
		login.getBtnEmpresa().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GestionComponentes g = new GestionComponentes(login);
				g.setUsuario("Empresa");
				g.setUsuarioDni("56789012E");
				g.setVisible(true);
				login.setVisible(false);
			}
		});
		
		login.getBtnLogin().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//if (BaseDatos.cuentaCorrecta(login.getTxtCuenta().getText(),login.getTxtPass().getText())) {
				String nombre = login.getBBDD().getEmpleado(login.getTxtCuenta().getText(), login.getTxtPass().getText());
				if (!nombre.equals("")) {		
					GestionComponentes g = new GestionComponentes(login);
					g.setUsuario(nombre);
					g.setUsuarioDni(login.getTxtCuenta().getText());
					g.setVisible(true);
					login.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(login, "Los datos de la cuenta no son correctos.");
				}
			}
		});
	}
}
