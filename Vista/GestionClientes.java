import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class GestionClientes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtId, txtNombre, txtApellido1, txtApellido2, txtDni;
    private JTextField txtDireccion, txtCp, txtTelefono, txtNumSocio, txtEmail, txtLocalidad;
    private JButton btnCrear, btnModificar, btnBorrar, btnVolver;
    private JButton btnNuevo;
    private JButton btnBuscar;
    private GestionComponentes gestion;
    private EventosGestionClientes eventos;
    private JPanel panelPrincipal;
    private JPanel panelBusqueda;

    // Este JFrame es una ventana extra que se abre si quieres gestionar los lientes, Añadir, modificar o dar de baja, asi como buscar los datos.
	public GestionClientes(GestionComponentes g) {
		gestion = g;
		setBounds(100, 100, 570, 400);
		setTitle("Gestión de Clientes");
        getContentPane().setLayout(new BorderLayout());
        
        // Usamos grid bag layout para el panel principa para crear lo que vendria a ser un formulario con los datos del cliente
        // Panel para campos de texto
        JPanel panelCampos = new JPanel();
        GridBagLayout gbl_panelCampos = new GridBagLayout();
        gbl_panelCampos.columnWidths = new int[] {15, 100, 129, 30, 129, 129, 30};
        gbl_panelCampos.rowHeights = new int[] {30, 30, 30, 30, 30, 30, 30, 30};
        gbl_panelCampos.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE, 0.0, 0.0, 0.0, 0.0};
        gbl_panelCampos.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        panelCampos.setLayout(gbl_panelCampos);
        GridBagConstraints gbc_lblId = new GridBagConstraints();
        gbc_lblId.anchor = GridBagConstraints.WEST;
        gbc_lblId.fill = GridBagConstraints.VERTICAL;
        gbc_lblId.insets = new Insets(0, 0, 5, 5);
        gbc_lblId.gridx = 1;
        gbc_lblId.gridy = 0;
        JLabel lblId = new JLabel("ID Cliente:");
        panelCampos.add(lblId, gbc_lblId);

        // Una cosa que hemos hecho en los textField es modificar su  DocumentFilter para que tengan un limite de caracteres.
        // Mas abajo lo veremos en detalle en la funcion setLimite
        txtId = new JTextField();
        txtId.setEditable(false);
        GridBagConstraints gbc_txtId = new GridBagConstraints();
        gbc_txtId.fill = GridBagConstraints.BOTH;
        gbc_txtId.insets = new Insets(0, 0, 5, 5);
        gbc_txtId.gridx = 2;
        gbc_txtId.gridy = 0;
        panelCampos.add(txtId, gbc_txtId);

        btnNuevo = new JButton("Nuevo");
        GridBagConstraints gbc_btnNuevo = new GridBagConstraints();
        gbc_btnNuevo.insets = new Insets(0, 0, 5, 5);
        gbc_btnNuevo.gridx = 4;
        gbc_btnNuevo.gridy = 0;
        panelCampos.add(btnNuevo, gbc_btnNuevo);

        btnBuscar = new JButton("Buscar");
        GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
        gbc_btnBuscar.insets = new Insets(0, 0, 5, 5);
        gbc_btnBuscar.gridx = 5;
        gbc_btnBuscar.gridy = 0;
        panelCampos.add(btnBuscar, gbc_btnBuscar);
        GridBagConstraints gbc_labelNombre = new GridBagConstraints();
        gbc_labelNombre.anchor = GridBagConstraints.WEST;
        gbc_labelNombre.fill = GridBagConstraints.VERTICAL;
        gbc_labelNombre.insets = new Insets(0, 0, 5, 5);
        gbc_labelNombre.gridx = 1;
        gbc_labelNombre.gridy = 1;
        JLabel labelNombre = new JLabel("Nombre:");
        panelCampos.add(labelNombre, gbc_labelNombre);

        txtNombre = new JTextField();
        GridBagConstraints gbc_txtNombre = new GridBagConstraints();
        gbc_txtNombre.gridwidth = 3;
        gbc_txtNombre.fill = GridBagConstraints.BOTH;
        gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
        gbc_txtNombre.gridx = 2;
        gbc_txtNombre.gridy = 1;
        setLimite(txtNombre, 50);
        panelCampos.add(txtNombre, gbc_txtNombre);
        GridBagConstraints gbc_lblApellido1 = new GridBagConstraints();
        gbc_lblApellido1.anchor = GridBagConstraints.WEST;
        gbc_lblApellido1.fill = GridBagConstraints.VERTICAL;
        gbc_lblApellido1.insets = new Insets(0, 0, 5, 5);
        gbc_lblApellido1.gridx = 1;
        gbc_lblApellido1.gridy = 2;
        JLabel lblApellido1 = new JLabel("Primer Apellido:");
        panelCampos.add(lblApellido1, gbc_lblApellido1);

        txtApellido1 = new JTextField();
        GridBagConstraints gbc_txtApellido1 = new GridBagConstraints();
        gbc_txtApellido1.gridwidth = 3;
        gbc_txtApellido1.fill = GridBagConstraints.BOTH;
        gbc_txtApellido1.insets = new Insets(0, 0, 5, 5);
        gbc_txtApellido1.gridx = 2;
        gbc_txtApellido1.gridy = 2;
        setLimite(txtApellido1, 50);
        panelCampos.add(txtApellido1, gbc_txtApellido1);
        GridBagConstraints gbc_lblApellido2 = new GridBagConstraints();
        gbc_lblApellido2.anchor = GridBagConstraints.WEST;
        gbc_lblApellido2.fill = GridBagConstraints.VERTICAL;
        gbc_lblApellido2.insets = new Insets(0, 0, 5, 5);
        gbc_lblApellido2.gridx = 1;
        gbc_lblApellido2.gridy = 3;
        JLabel lblApellido2 = new JLabel("Segundo Apellido:");
        panelCampos.add(lblApellido2, gbc_lblApellido2);

        txtApellido2 = new JTextField();
        GridBagConstraints gbc_txtApellido2 = new GridBagConstraints();
        gbc_txtApellido2.gridwidth = 3;
        gbc_txtApellido2.fill = GridBagConstraints.BOTH;
        gbc_txtApellido2.insets = new Insets(0, 0, 5, 5);
        gbc_txtApellido2.gridx = 2;
        gbc_txtApellido2.gridy = 3;
        setLimite(txtApellido2, 50);
        panelCampos.add(txtApellido2, gbc_txtApellido2);
        GridBagConstraints gbc_lblNumSocio = new GridBagConstraints();
        gbc_lblNumSocio.anchor = GridBagConstraints.WEST;
        gbc_lblNumSocio.fill = GridBagConstraints.VERTICAL;
        gbc_lblNumSocio.insets = new Insets(0, 0, 5, 5);
        gbc_lblNumSocio.gridx = 1;
        gbc_lblNumSocio.gridy = 4;
        JLabel lblNumSocio = new JLabel("DNI/NIF:");
        panelCampos.add(lblNumSocio, gbc_lblNumSocio);

        txtDni = new JTextField();
        GridBagConstraints gbc_txtDni = new GridBagConstraints();
        gbc_txtDni.fill = GridBagConstraints.BOTH;
        gbc_txtDni.insets = new Insets(0, 0, 5, 5);
        gbc_txtDni.gridx = 2;
        gbc_txtDni.gridy = 4;
        setLimite(txtDni, 9);
        panelCampos.add(txtDni, gbc_txtDni);
        GridBagConstraints gbc_lblDni = new GridBagConstraints();
        gbc_lblDni.anchor = GridBagConstraints.WEST;
        gbc_lblDni.fill = GridBagConstraints.VERTICAL;
        gbc_lblDni.insets = new Insets(0, 0, 5, 5);
        gbc_lblDni.gridx = 4;
        gbc_lblDni.gridy = 4;
        JLabel lblDni = new JLabel("Número de Socio:");
        panelCampos.add(lblDni, gbc_lblDni);

        txtNumSocio = new JTextField();
        txtNumSocio.setEditable(false);
        GridBagConstraints gbc_txtNumSocio = new GridBagConstraints();
        gbc_txtNumSocio.fill = GridBagConstraints.BOTH;
        gbc_txtNumSocio.insets = new Insets(0, 0, 5, 5);
        gbc_txtNumSocio.gridx = 5;
        gbc_txtNumSocio.gridy = 4;
        panelCampos.add(txtNumSocio, gbc_txtNumSocio);
        GridBagConstraints gbc_lblLocalicad = new GridBagConstraints();
        gbc_lblLocalicad.anchor = GridBagConstraints.WEST;
        gbc_lblLocalicad.fill = GridBagConstraints.VERTICAL;
        gbc_lblLocalicad.insets = new Insets(0, 0, 5, 5);
        gbc_lblLocalicad.gridx = 1;
        gbc_lblLocalicad.gridy = 5;
        JLabel lblLocalicad = new JLabel("Teléfono:");
        panelCampos.add(lblLocalicad, gbc_lblLocalicad);

        txtTelefono = new JTextField();
        GridBagConstraints gbc_txtTelefono = new GridBagConstraints();
        gbc_txtTelefono.fill = GridBagConstraints.BOTH;
        gbc_txtTelefono.insets = new Insets(0, 0, 5, 5);
        gbc_txtTelefono.gridx = 2;
        gbc_txtTelefono.gridy = 5;
        setLimite(txtTelefono, 15);
        panelCampos.add(txtTelefono, gbc_txtTelefono);
        GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
        gbc_lblTelefono.anchor = GridBagConstraints.WEST;
        gbc_lblTelefono.fill = GridBagConstraints.VERTICAL;
        gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
        gbc_lblTelefono.gridx = 4;
        gbc_lblTelefono.gridy = 5;
        JLabel lblTelefono = new JLabel("Email:");
        panelCampos.add(lblTelefono, gbc_lblTelefono);

        txtEmail = new JTextField();
        GridBagConstraints gbc_txtEmail = new GridBagConstraints();
        gbc_txtEmail.fill = GridBagConstraints.BOTH;
        gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
        gbc_txtEmail.gridx = 5;
        gbc_txtEmail.gridy = 5;
        panelCampos.add(txtEmail, gbc_txtEmail);

        // Este es el panel con los botones que interactuan con los clientes.
        JPanel panelBotones = new JPanel();

        btnCrear = new JButton("Crear Cliente");
        btnModificar = new JButton("Modificar");
        btnBorrar = new JButton("Dar de Baja");
		btnVolver = new JButton("Volver");

        panelBotones.add(btnCrear);
        panelBotones.add(btnModificar);
        panelBotones.add(btnBorrar);
        panelBotones.add(btnVolver);

        // Añadir paneles al frame
        getContentPane().add(panelCampos, BorderLayout.CENTER);
        GridBagConstraints gbc_lblEmail = new GridBagConstraints();
        gbc_lblEmail.anchor = GridBagConstraints.WEST;
        gbc_lblEmail.fill = GridBagConstraints.VERTICAL;
        gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
        gbc_lblEmail.gridx = 1;
        gbc_lblEmail.gridy = 6;
        JLabel lblEmail = new JLabel("Localidad:");
        panelCampos.add(lblEmail, gbc_lblEmail);

        txtLocalidad = new JTextField();
        GridBagConstraints gbc_txtLocalidad = new GridBagConstraints();
        gbc_txtLocalidad.fill = GridBagConstraints.BOTH;
        gbc_txtLocalidad.insets = new Insets(0, 0, 5, 5);
        gbc_txtLocalidad.gridx = 2;
        gbc_txtLocalidad.gridy = 6;
        setLimite(txtLocalidad, 50);
        panelCampos.add(txtLocalidad, gbc_txtLocalidad);
        GridBagConstraints gbc_lblCp = new GridBagConstraints();
        gbc_lblCp.anchor = GridBagConstraints.WEST;
        gbc_lblCp.fill = GridBagConstraints.VERTICAL;
        gbc_lblCp.insets = new Insets(0, 0, 5, 5);
        gbc_lblCp.gridx = 4;
        gbc_lblCp.gridy = 6;
        JLabel lblCp = new JLabel("Código Postal:");
        panelCampos.add(lblCp, gbc_lblCp);

        txtCp = new JTextField();
        GridBagConstraints gbc_txtCp = new GridBagConstraints();
        gbc_txtCp.fill = GridBagConstraints.BOTH;
        gbc_txtCp.insets = new Insets(0, 0, 5, 5);
        gbc_txtCp.gridx = 5;
        gbc_txtCp.gridy = 6;
        setLimite(txtCp, 5);
        panelCampos.add(txtCp, gbc_txtCp);
        GridBagConstraints gbc_lblDireccion = new GridBagConstraints();
        gbc_lblDireccion.anchor = GridBagConstraints.WEST;
        gbc_lblDireccion.fill = GridBagConstraints.VERTICAL;
        gbc_lblDireccion.insets = new Insets(0, 0, 5, 5);
        gbc_lblDireccion.gridx = 1;
        gbc_lblDireccion.gridy = 7;
        JLabel lblDireccion = new JLabel("Dirección:");
        panelCampos.add(lblDireccion, gbc_lblDireccion);

        txtDireccion = new JTextField();
        GridBagConstraints gbc_txtDireccion = new GridBagConstraints();
        gbc_txtDireccion.insets = new Insets(0, 0, 0, 5);
        gbc_txtDireccion.gridheight = 2;
        gbc_txtDireccion.gridwidth = 4;
        gbc_txtDireccion.fill = GridBagConstraints.BOTH;
        gbc_txtDireccion.gridx = 2;
        gbc_txtDireccion.gridy = 7;
        setLimite(txtDireccion, 50);
        panelCampos.add(txtDireccion, gbc_txtDireccion);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
        panelPrincipal = panelCampos;
        btnModificar.setEnabled(false);
        btnBorrar.setEnabled(false);

        setVisible(true);

        eventos = new EventosGestionClientes(this);
	}

	// Esta funcion se encarga de actualizar los campos del formulario con los datos del cliente seleccionado recibidos de la base de Datos
	public void actualizarCampos(ResultSet rs) {
		try {
			txtId.setText(rs.getString("id_cliente"));
			txtNombre.setText(rs.getString("nombre"));
			txtApellido1.setText(rs.getString("apellido_1"));
			txtApellido2.setText(rs.getString("apellido_2"));
			txtDni.setText(rs.getString("dni_nif"));
			txtDireccion.setText(rs.getString("direccion"));
			txtCp.setText(rs.getString("cp"));
			txtLocalidad.setText(rs.getString("localidad"));
			txtTelefono.setText(rs.getString("telefono"));
			txtEmail.setText(rs.getString("email"));
			txtNumSocio.setText(rs.getString("numero_socio"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Esta funcion crea el panel de busqueda de clientes, es muy sencillo, tres opciones de busqueda, por nombre, dni o id. y luego una JList que se rellenara con los resultados de la busqueda
	// Haciendo doble click sobre un resultado se cargaran los datos en el formulario para poder modificarlos o dar de baja al cliente.
	public JPanel crearPanelBusqueda() {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel busquedaCamposPanel = new JPanel(new GridLayout(3, 2, 10, 0));
		panel.setBorder(new EmptyBorder(30, 30, 30, 30));
		JTextField txtBuscarNombre = new JTextField();
		JButton btnBuscarNombre = new JButton("Buscar por Nombre");

		JTextField txtBuscarDni = new JTextField();
		JButton btnBuscarDni = new JButton("Buscar por DNI");

		JTextField txtBuscarId = new JTextField();
		JButton btnBuscarId = new JButton("Buscar por ID");

		busquedaCamposPanel.add(new JLabel("Nombre:"));
		busquedaCamposPanel.add(txtBuscarNombre);
		busquedaCamposPanel.add(btnBuscarNombre);

		busquedaCamposPanel.add(new JLabel("DNI:"));
		busquedaCamposPanel.add(txtBuscarDni);
		busquedaCamposPanel.add(btnBuscarDni);

		busquedaCamposPanel.add(new JLabel("ID:"));
		busquedaCamposPanel.add(txtBuscarId);
		busquedaCamposPanel.add(btnBuscarId);

		panel.add(busquedaCamposPanel, BorderLayout.NORTH);

		// JList para mostrar resultados
		
		DefaultListModel<String> modeloLista;
		modeloLista = new DefaultListModel<>();
		JList<String> listaResultados = new JList<>(modeloLista);
		listaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(listaResultados);
		panel.add(scrollPane, BorderLayout.CENTER);
		eventos.crearEventosBusqueda(txtBuscarNombre, txtBuscarDni, txtBuscarId, modeloLista, btnBuscarNombre, btnBuscarDni, btnBuscarId);
		eventos.crearEventosLista(listaResultados);

		return panel;
	}
	
	// Esta funcion se encarga de actualizar la JList con los resultados de la busqueda, cada vez que se realiza una busqueda se vacia y rellena con el ResultSet recibido
	public void actualizarTabla(ResultSet rs, DefaultListModel<String> lista) {
		lista.removeAllElements();
		try {
			do {
				if (rs.getInt("activo") == 1) {
					lista.addElement(rs.getString("dni_nif") + " - " + rs.getString("nombre") + " " + (rs.getString("apellido_1") == null ? "" : rs.getString("apellido_1")) + " " + (rs.getString("apellido_2") == null ? "" : rs.getString("apellido_2")) + " - " + rs.getString("id_cliente"));
				}
			} while (rs.next());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "No se ha encontrado ningún cliente con esos datos.");
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(this, "No se ha encontrado ningún cliente con esos datos.");
		}
	}

	// Esta funcion se encarga de limpiar los campos del formulario cada vez que queramos vacialors para crear un nuevo cliente.
	public void limpiarCampos() {
		txtId.setText("");
		txtNombre.setText("");
		txtApellido1.setText("");
		txtApellido2.setText("");
		txtDni.setText("");
		txtDireccion.setText("");
		txtCp.setText("");
		txtLocalidad.setText("");
		txtTelefono.setText("");
		txtEmail.setText("");
		txtNumSocio.setText("");
	}
	
	// Esta funcion se encarga de limitar el numero de caracteres que se pueden introducir en un JTextField
	// Estamos modificando un objeto DocumentFilter que tienen los JTextFields y cambiandole las funciones en las que se insertan o reemplazan datos, para hacer que si se sobrepa
	// no se realice la accion.
	public void setLimite(JTextField textField, int limit) {
	    DocumentFilter filter = new DocumentFilter() {
	        @Override
	        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
	            if (string == null) {
	                return;
	            }
	            if ((fb.getDocument().getLength() + string.length()) <= limit) {
	                super.insertString(fb, offset, string, attr);
	            }
	        }

	        @Override
	        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
	            if (text == null) {
	                return;
	            }
	            if ((fb.getDocument().getLength() + text.length() - length) <= limit) {
	                super.replace(fb, offset, length, text, attrs);
	            }
	        }
	    };

	    ((AbstractDocument) textField.getDocument()).setDocumentFilter(filter);
	}
	
	// Por ultimo una funcion que se encarga de asegurarse de que los campos necesarios para la base de datos esten rellenos
	// Y ademas se encarga de comprobar el formato de algunos de los campos, como el DNI, el telefono, el email y el codigo postal
	// Para que correspondan con los formatos aceptados por la tienda.
	public boolean validarCampos() {
	    // Validar que los campos no estén vacíos
	    if (txtNombre.getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "El campo 'Nombre' no puede estar vacío.");
	        txtNombre.requestFocus();
	        return false;
	    }
	    if (txtDni.getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "El campo 'DNI' no puede estar vacío.");
	        txtDni.requestFocus();
	        return false;
	    }
	    if (txtTelefono.getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "El campo 'Telefono' no puede estar vacío.");
	        txtTelefono.requestFocus();
	        return false;
	    }
	    if (txtEmail.getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "El campo 'Email' no puede estar vacío.");
	        txtEmail.requestFocus();
	        return false;
	    }
	    if (txtDireccion.getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "El campo 'Dirección' no puede estar vacío.");
	        txtDireccion.requestFocus();
	        return false;
	    }
	    if (txtCp.getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "El campo 'Código Postal' no puede estar vacío.");
	        txtCp.requestFocus();
	        return false;
	    }

	    // Validar formato de DNI_NIF
	    if (!txtDni.getText().matches("\\d{8}[A-Za-z]") && // Formato DNI: 8 dígitos + 1 letra
	    		!txtDni.getText().matches("[A-Za-z]\\d{7}[A-Za-z]")) { // Formato CIF: 1 letra + 7 dígitos + 1 letra
	    	JOptionPane.showMessageDialog(null, "El formato del Dni/Nif no es válido.");
	    	txtDni.setText("");
	    	txtDni.requestFocus();
	    	return false;
	    }
	    
	    // Validar formato del teléfono, Solo telefonod españoles
	    Pattern patronTelefono =  Pattern.compile("(\\+34|0034|34)?[ -]*(6|7)[ -]*([0-9][ -]*){8}");
	    if (!patronTelefono.matcher(txtTelefono.getText()).matches()) {
	        JOptionPane.showMessageDialog(null, "El formato del teléfono no es válido.");
	        txtTelefono.setText("");
	        txtTelefono.requestFocus();
	        return false;
	    }

	    // Validar formato del email, Hemos deicido solo aceptar dominios  .com y .es
	    Pattern patronEmail = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com|es)$");
	    if (!patronEmail.matcher(txtEmail.getText()).matches()) {
	        JOptionPane.showMessageDialog(null, "El formato del email no es válido.");
	        txtEmail.setText("");
	        txtEmail.requestFocus();
	        return false;
	    }
	    
	    // Validar formato del código postal
	    Pattern patronCp = Pattern.compile("^[0-9]{5}$");
	    if (!patronCp.matcher(txtCp.getText()).matches()) {
	        JOptionPane.showMessageDialog(null, "El formato del Codigo Postal no es válido.");
	        txtCp.setText("");
	        txtCp.requestFocus();
	        return false;
	    }

	    return true;
	}

	public JTextField getTxtId() {
		return txtId;
	}

	public void setTxtId(JTextField txtId) {
		this.txtId = txtId;
	}

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(JTextField txtNombre) {
		this.txtNombre = txtNombre;
	}

	public JTextField getTxtApellido1() {
		return txtApellido1;
	}

	public void setTxtApellido1(JTextField txtApellido1) {
		this.txtApellido1 = txtApellido1;
	}

	public JTextField getTxtApellido2() {
		return txtApellido2;
	}

	public void setTxtApellido2(JTextField txtApellido2) {
		this.txtApellido2 = txtApellido2;
	}

	public JTextField getTxtDni() {
		return txtDni;
	}

	public void setTxtDni(JTextField txtDni) {
		this.txtDni = txtDni;
	}

	public JTextField getTxtDireccion() {
		return txtDireccion;
	}

	public void setTxtDireccion(JTextField txtDireccion) {
		this.txtDireccion = txtDireccion;
	}

	public JTextField getTxtCp() {
		return txtCp;
	}

	public void setTxtCp(JTextField txtCp) {
		this.txtCp = txtCp;
	}

	public JTextField getTxtNumSocio() {
		return txtNumSocio;
	}

	public void setTxtNumSocio(JTextField txtNumSocio) {
		this.txtNumSocio = txtNumSocio;
	}

	public JButton getBtnCrear() {
		return btnCrear;
	}

	public void setBtnCrear(JButton btnCrear) {
		this.btnCrear = btnCrear;
	}

	public JButton getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(JButton btnModificar) {
		this.btnModificar = btnModificar;
	}

	public JButton getBtnBorrar() {
		return btnBorrar;
	}

	public void setBtnBorrar(JButton btnBorrar) {
		this.btnBorrar = btnBorrar;
	}

	public JButton getBtnNuevo() {
		return btnNuevo;
	}

	public void setBtnNuevo(JButton btnNuevo) {
		this.btnNuevo = btnNuevo;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(JButton btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public GestionComponentes getGestion() {
		return gestion;
	}

	public void setGestion(GestionComponentes gestion) {
		this.gestion = gestion;
	}

	public JTextField getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(JTextField txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public JTextField getTxtEmail() {
		return txtEmail;
	}

	public void setTxtEmail(JTextField txtEmail) {
		this.txtEmail = txtEmail;
	}

	public JTextField getTxtLocalidad() {
		return txtLocalidad;
	}

	public void setTxtLocalidad(JTextField txtLocalidad) {
		this.txtLocalidad = txtLocalidad;
	}

	public EventosGestionClientes getEventos() {
		return eventos;
	}

	public void setEventos(EventosGestionClientes eventos) {
		this.eventos = eventos;
	}

	public JPanel getPanelPrincipal() {
		return panelPrincipal;
	}

	public void setPanelPrincipal(JPanel panelPrincipal) {
		this.panelPrincipal = panelPrincipal;
	}

	public JPanel getPanelBusqueda() {
		return panelBusqueda;
	}

	public void setPanelBusqueda(JPanel panelBusqueda) {
		this.panelBusqueda = panelBusqueda;
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public void setBtnVolver(JButton btnVolver) {
		this.btnVolver = btnVolver;
	}

}
