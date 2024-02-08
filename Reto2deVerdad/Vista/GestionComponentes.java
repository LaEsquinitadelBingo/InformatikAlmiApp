import java.awt.EventQueue;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.imageio.ImageIO;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;

import javax.swing.*;
import javax.swing.DefaultListCellRenderer.UIResource;
import java.awt.ScrollPane;
import java.awt.Panel;
import net.miginfocom.swing.MigLayout;

public class GestionComponentes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel label_1;
	private JLabel label_2;
	private JMenuBar menuBar;
	private JMenu mnLogin;
	private JMenuItem mnNuevo;
	private JMenu mnCargar;
	private JMenu mnGestionar;
	private JMenu mnFacturar;
	private JMenu mnGuardar;
	private JMenu mnCrear;
	private JMenu mnFunciones;
	private JPanel pnlLista;
	private JList<String> listaTipos;
	private JPanel tituloLista;
	private JLabel lblLista;
	private JButton btnMinLista;
	private JScrollPane scrCarro;
	private JPanel tituloCarro;
	private JLabel lblCarro;
	private JButton btnMinCarro;
	private JPanel pnlCarroObjetos;
	private PanelPrincipal mainPanel;
	private JPanel pnlCarro;
	private String usuario;
	private JPanel pnlCarroMin;
	private JButton btnCarroMinMin;
	private JPanel pnlListaMin;
	private JButton btnListMinMin;
	private EventosGestion eventos;
	private ArrayList<Producto> carro;
	private JScrollPane scrollPane;
	private Login login;
	private JMenuItem loginItem;
	private JMenuItem exitItem;
	private Boolean guardado = false;
	private JButton btnNuevo;
	private JButton btnCargar;
	private JButton btnGuardar;
	private JButton btnGestionar;
	private JButton btnFacturar;
	private JButton btnCrear;
	private ArrayList<JComponent> componentesMenu;
	private JPanel pnlCarroAbajo;
	private JButton btnTramitar;
	private JLabel lblTotal;
	private ArrayList<JButton> botonesMenu;
	private JPanel pnlListado;
	private ArrayList<String> items;
	private JPanel pnlPedido;
	private JPanel pnlPedidoProducto;
	private JPanel pnlPedidoBorde;
	private JPanel pnImg;
	private JPanel pnlDatos;
	private JPanel pnlBotones;
	private JLabel lbImg;
	private JLabel lblNombre;
	private JLabel lblStock;
	private JLabel lblPrecio;
	private JPanel pnlBorrar;
	private JPanel pnlCantidad;
	private JButton btnBorrar;
	private JButton btnMenos;
	private JButton btnMas;
	private JTextField txtCantidad;
	private boolean estoyGestion;
	private JPanel pnlPedidoCuerpo;
	private JPanel pnlDatosPedido;
	private JLabel lnlCliente;
	private JLabel lblNumSocio;
	private JLabel lblMail;
	private JLabel lblTelefono;
	private JTextField txtCliente;
	private JTextField txtNumSocio;
	private JTextField txtMail;
	private JTextField txtTelefono;
	private JButton btnGuardarPedido;
	private JButton btnCancelarPedido;
	private JButton btnAtrasPedido;
	private JScrollPane scrPedido;
	private boolean cambiado = true;
	private PanelProducto panelProducto;
	private boolean miniaturas = true;


	/**
	 * Create the frame.
	 */
	public GestionComponentes(Login l) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());

		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setMinimumSize(new Dimension(1600, 1000));

		
		login = l;
		carro = new ArrayList<>();
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("icono.png"));
		this.setTitle("INFORMATIC-ALMI - Ventas");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 478);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		menuBar = new JMenuBar();

		mnLogin = new JMenu("Cuenta");
		mnLogin.setIcon(new ImageIcon("images/menu.png"));
		loginItem = new JMenuItem("Login");
		loginItem.setIcon(new ImageIcon("images/login.png"));
		exitItem = new JMenuItem("Salir");
		exitItem.setIcon(new ImageIcon("images/salir.png"));
		mnLogin.add(loginItem);
		mnLogin.add(exitItem);
		mnFunciones = new JMenu("Funciones");
		mnFunciones.setIcon(new ImageIcon("images/funciones.png"));
		menuBar.add(mnLogin);
		btnNuevo = new JButton("Nuevo", new ImageIcon("images/nuevo.png"));
		btnNuevo.setBorderPainted(false);
		menuBar.add(btnNuevo);
		btnCargar = new JButton("Cargar", new ImageIcon("images/cargar.png"));
		btnCargar.setBorderPainted(false);
		btnCargar.setFocusPainted(false);
		menuBar.add(btnCargar);
		btnGuardar = new JButton("Guardar", new ImageIcon("images/guardar.png"));
		btnGuardar.setBorderPainted(false);
		btnGuardar.setFocusPainted(false);
		menuBar.add(btnGuardar);
		btnGestionar = new JButton("Gestionar", new ImageIcon("images/detalles.png"));
		btnGestionar.setBorderPainted(false);
		menuBar.add(btnGestionar);
		btnFacturar = new JButton("Facturar", new ImageIcon("images/factura.png"));
		btnFacturar.setBorderPainted(false);
		menuBar.add(btnFacturar);
		menuBar.add(mnFunciones);
		menuBar.add(Box.createHorizontalGlue());
		
		

		componentesMenu = new ArrayList<>();
		componentesMenu.add(btnNuevo);
		componentesMenu.add(btnCargar);
		componentesMenu.add(btnGuardar);
		componentesMenu.add(btnGestionar);
		componentesMenu.add(btnFacturar);
		componentesMenu.add(mnFunciones);
		componentesMenu.add(mnLogin);
		componentesMenu.add(loginItem);
		componentesMenu.add(exitItem);

		

		setJMenuBar(menuBar);
		

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		pnlLista = new JPanel();

		contentPane.add(pnlLista, BorderLayout.WEST);



		pnlLista.setLayout(new BorderLayout(0, 0));
		// Crear un ListCellRenderer para cambiar la fuente de los elementos

		pnlLista.setMaximumSize(new Dimension(Integer.MAX_VALUE, pnlLista.getMaximumSize().height));

		pnlCarro = new JPanel();
		pnlCarro.setLayout(new BorderLayout(0, 0));
		// Crear la barra de título para pnlCarro
		tituloCarro = new JPanel(new BorderLayout());
		tituloCarro.setBorder(new EmptyBorder(4, 5, 4, 0));
		tituloCarro.setMaximumSize(new Dimension(2147483647, 30));
		tituloCarro.setPreferredSize(new Dimension(0, 30));
		lblCarro = new JLabel("Pedido nº 1");
		lblCarro.setFont(new Font("Arial", Font.BOLD, 16));
		lblCarro.setHorizontalAlignment(JLabel.CENTER);
		tituloCarro.add(lblCarro, BorderLayout.CENTER);

		btnMinCarro = new JButton(">");
		btnMinCarro.setFont(new Font("Tahoma", Font.PLAIN, 6));
		btnMinCarro.setPreferredSize(new Dimension(31, 23));
		tituloCarro.add(btnMinCarro, BorderLayout.WEST);

		pnlCarro.add(tituloCarro, BorderLayout.NORTH);
		Integer[] numeros =new Integer[20];
		for (int i = 1; i <= 20; i++) {
			numeros[i - 1] = i;
		}
		

		scrCarro = new JScrollPane(pnlCarro);
		scrCarro.setBorder(null);
		contentPane.add(scrCarro, BorderLayout.EAST);

		pnlLista.setPreferredSize(new Dimension(300, pnlLista.getHeight()));
		
		pnlCarro.setPreferredSize(new Dimension(300, pnlCarro.getHeight()));
		
		pnlCarroObjetos = new JPanel();
		scrollPane = new JScrollPane(pnlCarroObjetos);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pnlCarro.add(scrollPane, BorderLayout.CENTER);
		pnlCarroObjetos.setLayout(new BoxLayout(pnlCarroObjetos, BoxLayout.Y_AXIS));

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		pnlCarroAbajo = new JPanel();
		pnlCarro.add(pnlCarroAbajo, BorderLayout.SOUTH);
		pnlCarroAbajo.setBorder(new EmptyBorder(4, 5, 4, 5));
		pnlCarroAbajo.setMaximumSize(new Dimension(2147483647, 30));
		pnlCarroAbajo.setPreferredSize(new Dimension(0, 30));
		pnlCarroAbajo.setLayout(new BorderLayout(0, 0));
		
		lblTotal = new JLabel("Precio Total:");
		pnlCarroAbajo.add(lblTotal, BorderLayout.WEST);
		
		btnTramitar = new JButton("Tramitar Pedido");
		btnTramitar.setBackground(Color.YELLOW);
		pnlCarroAbajo.add(btnTramitar, BorderLayout.EAST);

		
		tituloLista = new JPanel((LayoutManager) null);
		tituloLista.setBorder(new EmptyBorder(4, 0, 4, 5));
		tituloLista.setPreferredSize(new Dimension(0, 30));
		tituloLista.setMaximumSize(new Dimension(2147483647, 40));
		pnlLista.setBorder(new EmptyBorder(0, 0, 0, 31));
		pnlLista.add(tituloLista, BorderLayout.NORTH);
		tituloLista.setLayout(new BorderLayout());
		
		lblLista = new JLabel("Lista de Componentes");
		lblLista.setFont(new Font("Arial", Font.BOLD, 16));
		lblLista.setHorizontalAlignment(SwingConstants.CENTER);
		tituloLista.add(lblLista, BorderLayout.CENTER);
		
		btnMinLista = new JButton("<");
		btnMinLista.setPreferredSize(new Dimension(31, 23));
		btnMinLista.setFont(new Font("Tahoma", Font.PLAIN, 6));
		tituloLista.add(btnMinLista, BorderLayout.EAST);
		
		pnlListado = new JPanel();
		pnlLista.add(pnlListado, BorderLayout.CENTER);
		botonesMenu = new ArrayList<JButton>();
		
		items = new ArrayList<>();
		items.add("Novedades");
		items.add("Portatiles");
		items.add("Packs Pc");
        items.add("Monitores");
        items.add("CPU");
        items.add("Placa Base");
        items.add("RAM");
        items.add("Tarjetas Graficas");
        items.add("Teclados");
        items.add("Ratones");
        items.add("Fuentes Alimentacion");
        items.add("Cascos");
        items.add("Torres");
        items.add("Ventiladores");
        items.add("Discos Duros");
		
		for (String item : items) {
			JButton boton = new JButton(item);
			boton.setPreferredSize(new Dimension(300, 50));
			boton.setFont(new Font("Arial", Font.PLAIN, 26));
			boton.setFocusPainted(false);
			pnlListado.add(boton);
			botonesMenu.add(boton);
		}

		setContentPane(contentPane);
		
		scrPedido = new JScrollPane();
		scrPedido.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrPedido, BorderLayout.CENTER);
		
		pnlCarroMin = new JPanel();
		
		btnCarroMinMin = new JButton("<");
		btnCarroMinMin.setPreferredSize(new Dimension(31, 23));
		btnCarroMinMin.setFont(new Font("Tahoma", Font.PLAIN, 6));
		pnlCarroMin.add(btnCarroMinMin);
		
		pnlListaMin = new JPanel();
		
		btnListMinMin = new JButton(">");
		btnListMinMin.setPreferredSize(new Dimension(31, 23));
		btnListMinMin.setFont(new Font("Tahoma", Font.PLAIN, 6));
		pnlListaMin.add(btnListMinMin);
		
		estoyGestion = false;
		btnGestionar.setEnabled(false);
		btnTramitar.setEnabled(false);
		btnFacturar.setEnabled(false);
		btnNuevo.setEnabled(false);
		btnGuardar.setEnabled(false);

		mainPanel = new PanelPrincipal(this);
		contentPane.add(mainPanel, BorderLayout.CENTER);
		eventos = new EventosGestion(this);
	}
	
	public void crearPedido(ArrayList<Producto> productos) {
		pnlPedido = new JPanel();
		pnlPedidoCuerpo = new JPanel();
		pnlPedidoCuerpo.setLayout(new BorderLayout(0, 0));
		
		scrPedido = new JScrollPane(pnlPedido);
		pnlPedidoCuerpo.add(scrPedido, BorderLayout.CENTER);
		
		pnlPedido.setLayout(new BoxLayout(pnlPedido, BoxLayout.Y_AXIS));
		
		for (Producto producto : productos) {
			pnlPedidoProducto = new JPanel();
			pnlPedidoProducto.setMaximumSize(new Dimension(32767, 250));
			pnlPedidoProducto.setBorder(new EmptyBorder(15, 200, 15, 200));
			pnlPedidoBorde = new JPanel();
			pnlPedidoBorde.setMaximumSize(new Dimension(32767, 250));
			pnlPedidoBorde.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
			
			pnlPedido.add(pnlPedidoProducto);
			pnlPedidoBorde.setLayout(new BorderLayout(0, 0));
			pnlPedidoProducto.setLayout(new BorderLayout(0, 0));
			pnlPedidoProducto.add(pnlPedidoBorde);
			
			pnImg = new JPanel();
			pnlPedidoBorde.add(pnImg, BorderLayout.WEST);
			
			lbImg = new JLabel("");
			pnImg.setBorder(new EmptyBorder(15, 15, 15, 15));
			ImageIcon icon;
	        try {
	            BufferedImage img = ImageIO.read(getClass().getResource(producto.getFoto()));
	            Image scaledImage = img.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
	            icon = new ImageIcon(scaledImage);
	        } catch (IOException e) {
	            icon = null;
	        }
	        lbImg.setIcon(icon);
			pnImg.add(lbImg);
			
			pnlDatos = new JPanel();
			pnlDatos.setBorder(new EmptyBorder(15, 15, 15, 15));
			pnlPedidoBorde.add(pnlDatos, BorderLayout.CENTER);
			pnlDatos.setLayout(new GridLayout(3, 1, 0, 0));
			
			lblNombre = new JLabel(producto.getNombre());
			lblNombre.setMinimumSize(new Dimension(400, 14));
			pnlDatos.add(lblNombre);
			
			lblStock = new JLabel("Stock: "+producto.getStock()+" uds.");
			pnlDatos.add(lblStock);
			
			lblPrecio = new JLabel("Precio: " + producto.getPrecio() + " €");
			pnlDatos.add(lblPrecio);
			
			pnlBotones = new JPanel();
			pnlBotones.setBorder(new EmptyBorder(15, 15, 15, 15));
			pnlPedidoBorde.add(pnlBotones, BorderLayout.EAST);
			pnlBotones.setLayout(new BorderLayout(0, 0));
			
			pnlBorrar = new JPanel();
			pnlBorrar.setBorder(new EmptyBorder(0, 0, 0, 15));
			FlowLayout fl_pnlBorrar = (FlowLayout) pnlBorrar.getLayout();
			pnlBotones.add(pnlBorrar, BorderLayout.WEST);
			
			btnBorrar = new JButton("");
			btnBorrar.setPreferredSize(new Dimension(30, 30));
			btnBorrar.setMaximumSize(new Dimension(30, 30));
			btnBorrar.setMinimumSize(new Dimension(30, 30));
			pnlBorrar.add(btnBorrar);
			
			pnlCantidad = new JPanel();
			pnlBotones.add(pnlCantidad);
			
			btnMenos = new JButton("-");
			btnMenos.setPreferredSize(new Dimension(30, 30));
			btnMenos.setMinimumSize(new Dimension(30, 30));
			btnMenos.setMaximumSize(new Dimension(30, 30));
			pnlCantidad.add(btnMenos);
			
			txtCantidad = new JTextField();
			txtCantidad.setHorizontalAlignment(SwingConstants.CENTER);
			txtCantidad.setText("1");
			txtCantidad.setMaximumSize(new Dimension(30, 30));
			txtCantidad.setMinimumSize(new Dimension(30, 30));
			txtCantidad.setPreferredSize(new Dimension(30, 30));
			txtCantidad.setText(producto.getEnCarro()+"");
			pnlCantidad.add(txtCantidad);
			txtCantidad.setColumns(10);
			
			btnMas = new JButton("+");
			btnMas.setPreferredSize(new Dimension(30, 30));
			btnMas.setMinimumSize(new Dimension(30, 30));
			btnMas.setMaximumSize(new Dimension(30, 30));
			pnlCantidad.add(btnMas);
			
			eventos.crearEventosPedido(btnBorrar, btnMenos, btnMas, txtCantidad, producto);
		}
		estoyGestion();
		
		pnlDatosPedido = new JPanel();
		pnlDatosPedido.setBorder(new EmptyBorder(20, 150, 20, 150));
		pnlDatosPedido.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10, 10, 10, 10);
		
		lblNombre = new JLabel("Cliente:");
		c.gridx = 0; 
		c.gridy = 0; 
		c.gridwidth = 1; 
		pnlDatosPedido.add(lblNombre,c);
		txtCliente = new JTextField();
		c.gridx = 1; 
		c.gridy = 0; 
		c.gridwidth = 3; 
		txtCliente.setPreferredSize(new Dimension(320, 30));
		pnlDatosPedido.add(txtCliente,c);
		btnGuardarPedido = new JButton("Confirmar Pedido");
		btnGuardarPedido.setBackground(Color.green);
		c.gridx = 7; 
		c.gridy = 0; 
		c.gridwidth = 1; 
		btnGuardarPedido.setPreferredSize(new Dimension(140, 30));
		pnlDatosPedido.add(btnGuardarPedido,c);
		lblNumSocio = new JLabel("Nº Socio:");
		c.gridx = 0; 
		c.gridy = 1; 
		c.gridwidth = 1; 
		pnlDatosPedido.add(lblNumSocio,c);
		txtNumSocio = new JTextField();
		c.gridx = 1; 
		c.gridy = 1; 
		c.gridwidth = 3;
		pnlDatosPedido.add(txtNumSocio,c);
		txtNumSocio.setPreferredSize(new Dimension(320, 30));
		pnlDatosPedido.add(new JLabel(""));
		btnCancelarPedido = new JButton("Cancelar Pedido");
		btnCancelarPedido.setBackground(Color.RED);
		c.gridx = 7; 
		c.gridy = 1; 
		c.gridwidth = 1; 
		btnCancelarPedido.setPreferredSize(new Dimension(140, 30));
		pnlDatosPedido.add(btnCancelarPedido,c);
		lblMail = new JLabel("Mail:");
		c.gridx = 0; 
		c.gridy = 2; 
		c.gridwidth = 1;
		pnlDatosPedido.add(lblMail,c);
		c.gridx = 1; 
		c.gridy = 2; 
		c.gridwidth = 3;
		txtMail = new JTextField();
		txtMail.setPreferredSize(new Dimension(320, 30));
		pnlDatosPedido.add(txtMail,c);
		pnlDatosPedido.add(new JLabel(""));
		pnlDatosPedido.add(new JLabel(""));
		lblTelefono = new JLabel("Teléfono:");
		c.gridx = 0; 
		c.gridy = 3; 
		c.gridwidth = 1;
		pnlDatosPedido.add(lblTelefono,c);
		txtTelefono = new JTextField();
		c.gridx = 1; 
		c.gridy = 3; 
		c.gridwidth = 3;
		txtTelefono.setPreferredSize(new Dimension(320, 30));
		pnlDatosPedido.add(txtTelefono,c);
		pnlDatosPedido.add(new JLabel(""));
		btnAtrasPedido = new JButton("Volver Atras");
		btnAtrasPedido.setBackground(new Color(128, 255, 255));
		c.gridx = 7; 
		c.gridy = 3; 
		c.gridwidth = 1; 
		btnAtrasPedido.setPreferredSize(new Dimension(140, 30));
		pnlDatosPedido.add(btnAtrasPedido,c);
		pnlPedidoCuerpo.add(pnlDatosPedido, BorderLayout.SOUTH);
		System.out.println(miniaturas);
		if (!miniaturas) {
			contentPane.remove(panelProducto);
		} else {
			contentPane.remove(mainPanel);
		}
		
		scrPedido.setLayout(new ScrollPaneLayout());
		scrPedido.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrPedido.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		//pnlPedidoCuerpo.add(scrPedido, BorderLayout.CENTER);
		eventos.crearEventosCompra(btnGuardarPedido, btnCancelarPedido, btnAtrasPedido, txtNumSocio, txtMail, txtCliente, txtCantidad);
		contentPane.add(pnlPedidoCuerpo, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
		
	}
	
	public void estoyGestion () {
		btnMinLista.setEnabled(!estoyGestion);
		btnMinCarro.setEnabled(!estoyGestion);
		for (int i = 0; i < carro.size(); i++) {
			for (Component boton : ((JPanel) pnlCarroObjetos.getComponent(i)).getComponents()) {
				if (boton instanceof JButton) {
					((JButton) boton).setEnabled(!estoyGestion);
				}
				if (boton instanceof JComboBox) {
					((JComboBox) boton).setEnabled(!estoyGestion);
				}
			}
		}
		btnFacturar.setEnabled(guardado);
		btnGestionar.setEnabled(!estoyGestion);
		btnTramitar.setEnabled(!estoyGestion);
		
	}
	
	public void modificarCarro(Producto producto) {
		cambiado = true;
		if (producto.getEnCarro() == 0) {
			carro.add(producto);
			producto.setEnCarro(1);
		} else {
			producto.setEnCarro(producto.getEnCarro()+1);
		}
		calcularTotal();
	}

	public void calcularTotal() {
		double total = 0;
		for (Producto producto : carro) {
			total += producto.getPrecio()*producto.getEnCarro();
		}
		lblTotal.setText("Precio Total: "+total+" €");
	}

	public void actualizarCarro() {
		pnlCarroObjetos.removeAll();
		for (Producto producto : carro) {
			JPanel panel = new JPanel();
			panel.setPreferredSize(new Dimension(270, 130));
			panel.setMaximumSize(new Dimension(270, 130));
			panel.setBorder(new EmptyBorder(10, 5, 20, 5));
			pnlCarroObjetos.add(panel);
			panel.setLayout(new GridLayout(4, 2, 5, 5));
			
			JLabel lblArticulo = new JLabel(producto.getNombre());
			panel.add(lblArticulo);
			
			JLabel lblNewLabel = new JLabel("");
			panel.add(lblNewLabel);
			
			JLabel lblTipo = new JLabel(producto.getDesc());
			panel.add(lblTipo);
			
			JLabel lblNewLabel1 = new JLabel("");
			panel.add(lblNewLabel1);
			
			JComboBox<Integer> comboBox = new JComboBox<>();
				for (int i = 1; i <= 10; i++) {
					comboBox.addItem(i);
				}
			comboBox.setSelectedItem(producto.getEnCarro());
			comboBox.setPreferredSize(new Dimension(30, 17));
			comboBox.setMinimumSize(new Dimension(30, 17));
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cambiado = true;
					producto.setEnCarro((int) comboBox.getSelectedItem());
					actualizarCarro();
					calcularTotal();
				}
			});

			panel.add(comboBox);
			
			JButton btnQuitar = new JButton("Quitar");
			btnQuitar.setPreferredSize(new Dimension(89, 15));
			btnQuitar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cambiado = true;
					producto.setEnCarro(0);
					carro.remove(producto);
					actualizarCarro();
					calcularTotal();
				}
			});
			panel.add(btnQuitar);
			
			JLabel lblPrecio = new JLabel(producto.getPrecio()+" €");
			panel.add(lblPrecio);
			
			JLabel lblTotal = new JLabel(producto.getPrecio()*producto.getEnCarro()+" €");
			panel.add(lblTotal);
		}
		if (carro.size() == 0) {
			cambiado = true;
			btnGestionar.setEnabled(false);
			btnFacturar.setEnabled(false);
			btnNuevo.setEnabled(false);
			btnGuardar.setEnabled(false);
		} else {
			btnNuevo.setEnabled(true);
			if (guardado) {
				btnFacturar.setEnabled(true);
			} else {
				btnGestionar.setEnabled(true);
			}
			if (cambiado) {
				btnGestionar.setEnabled(true);
			}
		}
		estoyGestion();
		pnlCarroObjetos.revalidate();
		pnlCarroObjetos.repaint();
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
		this.mnLogin.setText(usuario);
	}


	public JLabel getLabel_1() {
		return label_1;
	}


	public void setLabel_1(JLabel label_1) {
		this.label_1 = label_1;
	}


	public JLabel getLabel_2() {
		return label_2;
	}


	public void setLabel_2(JLabel label_2) {
		this.label_2 = label_2;
	}



	public JMenu getMnLogin() {
		return mnLogin;
	}


	public void setMnLogin(JMenu mnLogin) {
		this.mnLogin = mnLogin;
	}


	public JMenuItem getMnNuevo() {
		return mnNuevo;
	}


	public void setMnNuevo(JMenu mnNuevo) {
		this.mnNuevo = mnNuevo;
	}


	public JMenu getMnCargar() {
		return mnCargar;
	}


	public void setMnCargar(JMenu mnCargar) {
		this.mnCargar = mnCargar;
	}


	public JMenu getMnGestionar() {
		return mnGestionar;
	}


	public void setMnGestionar(JMenu mnGestionar) {
		this.mnGestionar = mnGestionar;
	}


	public JMenu getMnFacturar() {
		return mnFacturar;
	}


	public void setMnFacturar(JMenu mnFacturar) {
		this.mnFacturar = mnFacturar;
	}


	public JMenu getMnGuardar() {
		return mnGuardar;
	}


	public void setMnGuardar(JMenu mnGuardar) {
		this.mnGuardar = mnGuardar;
	}


	public JMenu getMnCrear() {
		return mnCrear;
	}


	public void setMnCrear(JMenu mnCrear) {
		this.mnCrear = mnCrear;
	}


	public JMenu getMnFunciones() {
		return mnFunciones;
	}


	public void setMnFunciones(JMenu mnFunciones) {
		this.mnFunciones = mnFunciones;
	}


	public JPanel getPnlLista() {
		return pnlLista;
	}


	public void setPnlLista(JPanel pnlLista) {
		this.pnlLista = pnlLista;
	}


	public JList<String> getListaTipos() {
		return listaTipos;
	}


	public void setListaTipos(JList<String> listaTipos) {
		this.listaTipos = listaTipos;
	}


	public JPanel getTituloLista() {
		return tituloLista;
	}


	public void setTituloLista(JPanel tituloLista) {
		this.tituloLista = tituloLista;
	}


	public JLabel getLblLista() {
		return lblLista;
	}


	public void setLblLista(JLabel lblLista) {
		this.lblLista = lblLista;
	}


	public JButton getBtnMinLista() {
		return btnMinLista;
	}


	public void setBtnMinLista(JButton btnMinLista) {
		this.btnMinLista = btnMinLista;
	}


	public JScrollPane getScrCarro() {
		return scrCarro;
	}


	public void setScrCarro(JScrollPane scrCarro) {
		this.scrCarro = scrCarro;
	}


	public JPanel getTituloCarro() {
		return tituloCarro;
	}


	public void setTituloCarro(JPanel tituloCarro) {
		this.tituloCarro = tituloCarro;
	}


	public JLabel getLblCarro() {
		return lblCarro;
	}


	public void setLblCarro(JLabel lblCarro) {
		this.lblCarro = lblCarro;
	}


	public JButton getBtnMinCarro() {
		return btnMinCarro;
	}


	public void setBtnMinCarro(JButton btnMinCarro) {
		this.btnMinCarro = btnMinCarro;
	}


	public JPanel getPnlCarroObjetos() {
		return pnlCarroObjetos;
	}


	public void setPnlCarroObjetos(JPanel pnlCarroObjetos) {
		this.pnlCarroObjetos = pnlCarroObjetos;
	}


	public PanelPrincipal getMainPanel() {
		return mainPanel;
	}




	public JPanel getPnlCarro() {
		return pnlCarro;
	}


	public void setPnlCarro(JPanel pnlCarro) {
		this.pnlCarro = pnlCarro;
	}


	public JPanel getPnlCarroMin() {
		return pnlCarroMin;
	}


	public void setPnlCarroMin(JPanel pnlCarroMin) {
		this.pnlCarroMin = pnlCarroMin;
	}


	public JButton getBtnCarroMinMin() {
		return btnCarroMinMin;
	}


	public void setBtnCarroMinMin(JButton btnCarroMinMin) {
		this.btnCarroMinMin = btnCarroMinMin;
	}


	public JPanel getPnlListaMin() {
		return pnlListaMin;
	}


	public void setPnlListaMin(JPanel pnlListaMin) {
		this.pnlListaMin = pnlListaMin;
	}


	public JButton getBtnListMinMin() {
		return btnListMinMin;
	}


	public void setBtnListMinMin(JButton btnListMinMin) {
		this.btnListMinMin = btnListMinMin;
	}


	public ArrayList<Producto> getCarro() {
		return carro;
	}


	public void setProducto(ArrayList<Producto> carro) {
		this.carro = carro;
	}

	public Login getLogin() {
		return login;
	}

	public JMenuItem getLoginItem() {
		return loginItem;
	}

	public void setLoginItem(JMenuItem loginItem) {
		this.loginItem = loginItem;
	}

	public JMenuItem getExitItem() {
		return exitItem;
	}

	public void setExitItem(JMenuItem exitItem) {
		this.exitItem = exitItem;
	}

	public Boolean isGuardado() {
		return guardado;
	}

	public void setGuardado(Boolean guardado) {
		this.guardado = guardado;
	}

	public JButton getBtnNuevo() {
		return btnNuevo;
	}

	public void setBtnNuevo(JButton btnNuevo) {
		this.btnNuevo = btnNuevo;
	}

	public JButton getBtnCargar() {
		return btnCargar;
	}

	public void setBtnCargar(JButton btnCargar) {
		this.btnCargar = btnCargar;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(JButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public JButton getBtnGestionar() {
		return btnGestionar;
	}

	public void setBtnGestionar(JButton btnGestionar) {
		this.btnGestionar = btnGestionar;
	}

	public JButton getBtnFacturar() {
		return btnFacturar;
	}

	public void setBtnFacturar(JButton btnFacturar) {
		this.btnFacturar = btnFacturar;
	}

	public JButton getBtnCrear() {
		return btnCrear;
	}

	public void setBtnCrear(JButton btnCrear) {
		this.btnCrear = btnCrear;
	}

	public ArrayList<JComponent> getComponentesMenu() {
		return componentesMenu;
	}

	public void setComponentesMenu(ArrayList<JComponent> componentesMenu) {
		this.componentesMenu = componentesMenu;
	}

	public ArrayList<JButton> getBotonesMenu() {
		return botonesMenu;
	}

	public void setBotonesMenu(ArrayList<JButton> botonesMenu) {
		this.botonesMenu = botonesMenu;
	}

	public ArrayList<String> getItems() {
		return items;
	}

	public boolean isEstoyGestion() {
		return estoyGestion;
	}

	public void setEstoyGestion(boolean estoyGestion) {
		this.estoyGestion = estoyGestion;
	}

	public JButton getBtnTramitar() {
		return btnTramitar;
	}

	public void setBtnTramitar(JButton btnTramitar) {
		this.btnTramitar = btnTramitar;
	}

	public Boolean getGuardado() {
		return guardado;
	}

	public boolean isCambiado() {
		return cambiado;
	}

	public void setCambiado(boolean cambiado) {
		this.cambiado = cambiado;
	}

	public PanelProducto getPanelProducto() {
		return panelProducto;
	}

	public void setPanelProducto(PanelProducto panelProducto) {
		this.panelProducto = panelProducto;
	}
	
	public void setMiniaturas(boolean miniaturas) {
		this.miniaturas = miniaturas;
	}
	
	public boolean isMiniaturas() {
		return miniaturas;
	}
}
