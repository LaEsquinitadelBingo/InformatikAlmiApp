import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PanelConfigurador extends JPanel {

	private static final long serialVersionUID = 1L;
	private GestionComponentes gestion;
	private ArrayList<ArrayList<Producto>> productos;
	private ArrayList<Producto> config;
	private ArrayList<JLabel> labels;
	private ArrayList<JLabel> imagenes;
	private ArrayList<String> originales;
	private JLabel lblTitulo;
	private JButton btnReiniciar;
	private JButton btnCargar;
	private JButton btnGuardar;
	private JLabel lblPrecio;
	private JButton btnCarrito;
	private JLabel lblPrecioProc;
	private JLabel lblProc;
	private JComboBox cbProc;
	private JLabel lblImgPlaca;
	private JLabel lblPrecioPlaca;
	private JLabel lblPlaca;
	private JComboBox cbPlaca;
	private JLabel lblImgCaja;
	private JLabel lblPrecioCaja;
	private JLabel lblCaja;
	private JComboBox cbCaja;
	private JLabel lblImgFuente;
	private JLabel lblPrecioFuente;
	private JLabel lblFuente;
	private JComboBox cbFuente;
	private JLabel lblImgVenti;
	private JLabel lblPrecioVenti;
	private JLabel lblVenti;
	private JComboBox cbVenti;
	private JLabel lblImgGrafica;
	private JLabel lblPrecioGrafica;
	private JLabel lblGrafica;
	private JComboBox cbGrafica;
	private JLabel lblImgDD;
	private JLabel lblPrecioDD;
	private JLabel lblDD;
	private JComboBox cbDD;
	private JLabel lblImgRAM;
	private JLabel lblPrecioRAM;
	private JLabel lblRAM;
	private JComboBox cbRAM;
	private EventosPanelConfiguracion eventos;
	private ArrayList<Integer> orden;
	private int estado = 0;
	private ArrayList<JComboBox> combos;
	private JButton btnVolver;

	// Esta clase es la clase que se encarga de crear el panel con el configurador de pcs y todas las funciones necesarias en el
	public PanelConfigurador(GestionComponentes g) {
		gestion = g;
		orden = new ArrayList<Integer>();
		orden.add(4);
		orden.add(5);
		orden.add(12);
		orden.add(10);
		orden.add(13);
		orden.add(7);
		orden.add(14);
		orden.add(6);
		// Para gestionar el orden en el que vamos creando nuestro pc, vamos a tener una serie de arraylists, todos con el mismo numero de items, de manera que en cada paso de la creacion del pc
		// Mediante esos arrays sabemos lo que tenemos que hacer y en que componente tenemos que hacerlo
		
		
		
		
		// El panel es un borderpanel con un titulo con los botones relacionados con l;a configuracion completa, una barra de abajo con el precio y los botones para terminar o salir
		// Y un panel en medio que es boxLayout y que tiene un JPanel por cada tipo de componente.
		// Los Componentes de los paneles se van desbloqueando en orden segun vamos eligiendo cosas.
		setBounds(100, 100, 912, 587);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));

		this.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlTitulo = new JPanel();
		this.add(pnlTitulo, BorderLayout.NORTH);
		pnlTitulo.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlTituloTexto = new JPanel();
		pnlTituloTexto.setBorder(new EmptyBorder(5, 15, 5, 5));
		pnlTitulo.add(pnlTituloTexto, BorderLayout.WEST);
		
		lblTitulo = new JLabel("Mi Configuracion");
		lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 22));
		pnlTituloTexto.add(lblTitulo);
		
		JPanel pnlBotones = new JPanel();
		pnlBotones.setBorder(new EmptyBorder(9, 5, 5, 15));
		pnlTitulo.add(pnlBotones, BorderLayout.EAST);
		
		btnReiniciar = new JButton("Reiniciar");
		btnReiniciar.setIcon(new ImageIcon(getClass().getResource("/eliminar.png")));
		pnlBotones.add(btnReiniciar);
		
		btnCargar = new JButton("Cargar");
		btnCargar.setIcon(new ImageIcon(getClass().getResource("/publicar.png")));
		pnlBotones.add(btnCargar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(getClass().getResource("/me-gusta.png")));
		pnlBotones.add(btnGuardar);
		
		JPanel pnlAbajo = new JPanel();
		this.add(pnlAbajo, BorderLayout.SOUTH);
		pnlAbajo.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlPrecio = new JPanel();
		pnlPrecio.setBorder(new EmptyBorder(5, 15, 5, 5));
		pnlAbajo.add(pnlPrecio, BorderLayout.WEST);
		
		lblPrecio = new JLabel("Mi Configuracion");
		lblPrecio.setFont(new Font("Arial Black", Font.BOLD, 22));
		pnlPrecio.add(lblPrecio);
		
		JPanel pnlCarro = new JPanel();
		pnlCarro.setBorder(new EmptyBorder(5, 5, 5, 15));
		pnlAbajo.add(pnlCarro, BorderLayout.EAST);
		
		btnCarrito = new JButton("Añadir al Carrito");
		btnCarrito.setFont(new Font("Tahoma", Font.BOLD, 20));
		pnlCarro.add(btnCarrito);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlAbajo.add(panel, BorderLayout.CENTER);
		
		btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(btnVolver);
		
		JPanel pnlGeneral = new JPanel();
		this.add(pnlGeneral, BorderLayout.CENTER);
		pnlGeneral.setLayout(new BoxLayout(pnlGeneral, BoxLayout.Y_AXIS));
		
		JPanel pnlProc = new JPanel();
		pnlProc.setMaximumSize(new Dimension(32767, 100));
		pnlGeneral.add(pnlProc);
		pnlProc.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlImagenProc = new JPanel();
		pnlImagenProc.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlProc.add(pnlImagenProc, BorderLayout.WEST);
		originales = new ArrayList<>();
		JLabel lblImgProc = new JLabel("");
		lblImgProc.setIcon(new ImageIcon(getClass().getResource("/procesador.png")));
		originales.add("/procesador.png");
		pnlImagenProc.add(lblImgProc);
		
		JPanel pnlPrecioProc = new JPanel();
		pnlPrecioProc.setBorder(new EmptyBorder(25, 0, 5, 5));
		pnlProc.add(pnlPrecioProc, BorderLayout.EAST);
		
		lblPrecioProc = new JLabel(".........\r\n");
		lblPrecioProc.setFont(new Font("Arial Black", Font.BOLD, 24));
		pnlPrecioProc.add(lblPrecioProc);
		labels = new ArrayList();
		labels.add(lblPrecioProc);
		
		JPanel pnlDatosProc = new JPanel();
		pnlProc.add(pnlDatosProc, BorderLayout.CENTER);
		pnlDatosProc.setLayout(new BoxLayout(pnlDatosProc, BoxLayout.Y_AXIS));
		
		JPanel pnlLAbelProc = new JPanel();
		pnlLAbelProc.setBorder(new EmptyBorder(5, 15, 5, 5));
		pnlLAbelProc.setAlignmentY(Component.TOP_ALIGNMENT);
		pnlDatosProc.add(pnlLAbelProc);
		pnlLAbelProc.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblProc = new JLabel("PROCESADOR");
		lblProc.setFont(new Font("Arial Black", Font.BOLD, 16));
		lblProc.setHorizontalAlignment(SwingConstants.LEFT);
		pnlLAbelProc.add(lblProc);
		
		JPanel pnlCBBProc = new JPanel();
		pnlCBBProc.setBorder(new EmptyBorder(10, 15, 10, 5));
		pnlDatosProc.add(pnlCBBProc);
		pnlCBBProc.setLayout(new GridLayout(0, 2, 0, 0));
		
		cbProc = new JComboBox();
		cbProc.setModel(new DefaultComboBoxModel(new String[] {"------------------------------------------------------------------------------------------"}));
		pnlCBBProc.add(cbProc);
		
		JPanel pnlPlaca = new JPanel();
		pnlPlaca.setMaximumSize(new Dimension(32767, 100));
		pnlGeneral.add(pnlPlaca);
		pnlPlaca.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlImagenPlaca = new JPanel();
		pnlImagenPlaca.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlPlaca.add(pnlImagenPlaca, BorderLayout.WEST);
		lblImgPlaca = new JLabel("");
		lblImgPlaca.setIcon(new ImageIcon(getClass().getResource("/placa.png")));
		originales.add("/placa.png");
		pnlImagenPlaca.add(lblImgPlaca);
		
		JPanel pnlPrecioPlaca = new JPanel();
		pnlPrecioPlaca.setBorder(new EmptyBorder(25, 0, 5, 5));
		pnlPlaca.add(pnlPrecioPlaca, BorderLayout.EAST);
		
		lblPrecioPlaca = new JLabel(".........");
		lblPrecioPlaca.setFont(new Font("Arial Black", Font.BOLD, 24));
		pnlPrecioPlaca.add(lblPrecioPlaca);
		labels.add(lblPrecioPlaca);
		
		JPanel pnlDatosPlaca = new JPanel();
		pnlPlaca.add(pnlDatosPlaca, BorderLayout.CENTER);
		pnlDatosPlaca.setLayout(new BoxLayout(pnlDatosPlaca, BoxLayout.Y_AXIS));
		
		JPanel pnlLAbelPlaca = new JPanel();
		pnlLAbelPlaca.setBorder(new EmptyBorder(5, 15, 5, 5));
		pnlLAbelPlaca.setAlignmentY(Component.TOP_ALIGNMENT);
		pnlDatosPlaca.add(pnlLAbelPlaca);
		pnlLAbelPlaca.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblPlaca = new JLabel("PLACA BASE");
		lblPlaca.setFont(new Font("Arial Black", Font.BOLD, 16));
		lblPlaca.setHorizontalAlignment(SwingConstants.LEFT);
		pnlLAbelPlaca.add(lblPlaca);
		
		JPanel pnlCBBPlaca = new JPanel();
		pnlCBBPlaca.setBorder(new EmptyBorder(10, 15, 10, 5));
		pnlDatosPlaca.add(pnlCBBPlaca);
		pnlCBBPlaca.setLayout(new GridLayout(0, 2, 0, 0));
		
		cbPlaca = new JComboBox();
		cbPlaca.setEnabled(false);
		cbPlaca.setModel(new DefaultComboBoxModel(new String[] {"------------------------------------------------------------------------------------------"}));
		pnlCBBPlaca.add(cbPlaca);
		
		
		JPanel pnlCaja = new JPanel();
		pnlCaja.setMaximumSize(new Dimension(32767, 100));
		pnlGeneral.add(pnlCaja);
		pnlCaja.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlImagenCaja = new JPanel();
		pnlImagenCaja.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlCaja.add(pnlImagenCaja, BorderLayout.WEST);
		
		lblImgCaja = new JLabel("");
		lblImgCaja.setIcon(new ImageIcon(getClass().getResource("/caja.png")));
		originales.add("/caja.png");
		pnlImagenCaja.add(lblImgCaja);
		
		JPanel pnlPrecioCaja = new JPanel();
		pnlPrecioCaja.setBorder(new EmptyBorder(25, 0, 5, 5));
		pnlCaja.add(pnlPrecioCaja, BorderLayout.EAST);
		
		lblPrecioCaja = new JLabel(".........");
		lblPrecioCaja.setFont(new Font("Arial Black", Font.BOLD, 24));
		pnlPrecioCaja.add(lblPrecioCaja);
		labels.add(lblPrecioCaja);
		
		JPanel pnlDatosCaja = new JPanel();
		pnlCaja.add(pnlDatosCaja, BorderLayout.CENTER);
		pnlDatosCaja.setLayout(new BoxLayout(pnlDatosCaja, BoxLayout.Y_AXIS));
		
		JPanel pnlLAbelCaja = new JPanel();
		pnlLAbelCaja.setBorder(new EmptyBorder(5, 15, 5, 5));
		pnlLAbelCaja.setAlignmentY(Component.TOP_ALIGNMENT);
		pnlDatosCaja.add(pnlLAbelCaja);
		pnlLAbelCaja.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblCaja = new JLabel("CAJA");
		lblCaja.setFont(new Font("Arial Black", Font.BOLD, 16));
		lblCaja.setHorizontalAlignment(SwingConstants.LEFT);
		pnlLAbelCaja.add(lblCaja);
		
		JPanel pnlCBBCaja = new JPanel();
		pnlCBBCaja.setBorder(new EmptyBorder(10, 15, 10, 5));
		pnlDatosCaja.add(pnlCBBCaja);
		pnlCBBCaja.setLayout(new GridLayout(0, 2, 0, 0));
		
		cbCaja = new JComboBox();
		cbCaja.setEnabled(false);
		cbCaja.setModel(new DefaultComboBoxModel(new String[] {"------------------------------------------------------------------------------------------"}));
		pnlCBBCaja.add(cbCaja);
		
		
		JPanel pnlFuente = new JPanel();
		pnlFuente.setMaximumSize(new Dimension(32767, 100));
		pnlGeneral.add(pnlFuente);
		pnlFuente.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlImagenFuente = new JPanel();
		pnlImagenFuente.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlFuente.add(pnlImagenFuente, BorderLayout.WEST);
		
		lblImgFuente = new JLabel("");
		lblImgFuente.setIcon(new ImageIcon(getClass().getResource("/fuente.png")));
		originales.add("/fuente.png");
		pnlImagenFuente.add(lblImgFuente);
		
		JPanel pnlPrecioFuente = new JPanel();
		pnlPrecioFuente.setBorder(new EmptyBorder(25, 0, 5, 5));
		pnlFuente.add(pnlPrecioFuente, BorderLayout.EAST);
		
		lblPrecioFuente = new JLabel(".........");
		lblPrecioFuente.setFont(new Font("Arial Black", Font.BOLD, 24));
		pnlPrecioFuente.add(lblPrecioFuente);
		labels.add(lblPrecioFuente);
		
		JPanel pnlDatosFuente = new JPanel();
		pnlFuente.add(pnlDatosFuente, BorderLayout.CENTER);
		pnlDatosFuente.setLayout(new BoxLayout(pnlDatosFuente, BoxLayout.Y_AXIS));
		
		JPanel pnlLAbelFuente = new JPanel();
		pnlLAbelFuente.setBorder(new EmptyBorder(5, 15, 5, 5));
		pnlLAbelFuente.setAlignmentY(Component.TOP_ALIGNMENT);
		pnlDatosFuente.add(pnlLAbelFuente);
		pnlLAbelFuente.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblFuente = new JLabel("FUENTE ALIMENTACION");
		lblFuente.setFont(new Font("Arial Black", Font.BOLD, 16));
		lblFuente.setHorizontalAlignment(SwingConstants.LEFT);
		pnlLAbelFuente.add(lblFuente);
		
		JPanel pnlCBBFuente = new JPanel();
		pnlCBBFuente.setBorder(new EmptyBorder(10, 15, 10, 5));
		pnlDatosFuente.add(pnlCBBFuente);
		pnlCBBFuente.setLayout(new GridLayout(0, 2, 0, 0));
		
		cbFuente = new JComboBox();
		cbFuente.setEnabled(false);
		cbFuente.setModel(new DefaultComboBoxModel(new String[] {"------------------------------------------------------------------------------------------"}));
		pnlCBBFuente.add(cbFuente);
		
		
		JPanel pnlVenti = new JPanel();
		pnlVenti.setMaximumSize(new Dimension(32767, 100));
		pnlGeneral.add(pnlVenti);
		pnlVenti.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlImagenVenti = new JPanel();
		pnlImagenVenti.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlVenti.add(pnlImagenVenti, BorderLayout.WEST);
		
		lblImgVenti = new JLabel("");
		lblImgVenti.setIcon(new ImageIcon(getClass().getResource("/venti.png")));
		originales.add("/venti.png");
		pnlImagenVenti.add(lblImgVenti);
		
		JPanel pnlPrecioVenti = new JPanel();
		pnlPrecioVenti.setBorder(new EmptyBorder(25, 0, 5, 5));
		pnlVenti.add(pnlPrecioVenti, BorderLayout.EAST);
		
		lblPrecioVenti = new JLabel(".........");
		lblPrecioVenti.setFont(new Font("Arial Black", Font.BOLD, 24));
		pnlPrecioVenti.add(lblPrecioVenti);
		labels.add(lblPrecioVenti);
		
		JPanel pnlDatosVenti = new JPanel();
		pnlVenti.add(pnlDatosVenti, BorderLayout.CENTER);
		pnlDatosVenti.setLayout(new BoxLayout(pnlDatosVenti, BoxLayout.Y_AXIS));
		
		JPanel pnlLAbelVenti = new JPanel();
		pnlLAbelVenti.setBorder(new EmptyBorder(5, 15, 5, 5));
		pnlLAbelVenti.setAlignmentY(Component.TOP_ALIGNMENT);
		pnlDatosVenti.add(pnlLAbelVenti);
		pnlLAbelVenti.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblVenti = new JLabel("VENTILACION");
		lblVenti.setFont(new Font("Arial Black", Font.BOLD, 16));
		lblVenti.setHorizontalAlignment(SwingConstants.LEFT);
		pnlLAbelVenti.add(lblVenti);
		
		JPanel pnlCBBVenti = new JPanel();
		pnlCBBVenti.setBorder(new EmptyBorder(10, 15, 10, 5));
		pnlDatosVenti.add(pnlCBBVenti);
		pnlCBBVenti.setLayout(new GridLayout(0, 2, 0, 0));
		
		cbVenti = new JComboBox();
		cbVenti.setEnabled(false);
		cbVenti.setModel(new DefaultComboBoxModel(new String[] {"------------------------------------------------------------------------------------------"}));
		pnlCBBVenti.add(cbVenti);
		
		
		JPanel pnlGrafica = new JPanel();
		pnlGrafica.setMaximumSize(new Dimension(32767, 100));
		pnlGeneral.add(pnlGrafica);
		pnlGrafica.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlImagenGrafica = new JPanel();
		pnlImagenGrafica.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlGrafica.add(pnlImagenGrafica, BorderLayout.WEST);
		
		lblImgGrafica = new JLabel("");
		lblImgGrafica.setIcon(new ImageIcon(getClass().getResource("/grafica.png")));
		originales.add("/grafica.png");
		pnlImagenGrafica.add(lblImgGrafica);
		
		JPanel pnlPrecioGrafica = new JPanel();
		pnlPrecioGrafica.setBorder(new EmptyBorder(25, 0, 5, 5));
		pnlGrafica.add(pnlPrecioGrafica, BorderLayout.EAST);
		
		lblPrecioGrafica = new JLabel(".........");
		lblPrecioGrafica.setFont(new Font("Arial Black", Font.BOLD, 24));
		pnlPrecioGrafica.add(lblPrecioGrafica);
		labels.add(lblPrecioGrafica);
		
		JPanel pnlDatosGrafica = new JPanel();
		pnlGrafica.add(pnlDatosGrafica, BorderLayout.CENTER);
		pnlDatosGrafica.setLayout(new BoxLayout(pnlDatosGrafica, BoxLayout.Y_AXIS));
		
		JPanel pnlLAbelGrafica = new JPanel();
		pnlLAbelGrafica.setBorder(new EmptyBorder(5, 15, 5, 5));
		pnlLAbelGrafica.setAlignmentY(Component.TOP_ALIGNMENT);
		pnlDatosGrafica.add(pnlLAbelGrafica);
		pnlLAbelGrafica.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblGrafica = new JLabel("TARJETA GRAFICA");
		lblGrafica.setFont(new Font("Arial Black", Font.BOLD, 16));
		lblGrafica.setHorizontalAlignment(SwingConstants.LEFT);
		pnlLAbelGrafica.add(lblGrafica);
		
		JPanel pnlCBBGrafica = new JPanel();
		pnlCBBGrafica.setBorder(new EmptyBorder(10, 15, 10, 5));
		pnlDatosGrafica.add(pnlCBBGrafica);
		pnlCBBGrafica.setLayout(new GridLayout(0, 2, 0, 0));
		
		cbGrafica = new JComboBox();
		cbGrafica.setEnabled(false);
		cbGrafica.setModel(new DefaultComboBoxModel(new String[] {"------------------------------------------------------------------------------------------"}));
		pnlCBBGrafica.add(cbGrafica);
		
		
		
		JPanel pnlDD = new JPanel();
		pnlDD.setMaximumSize(new Dimension(32767, 100));
		pnlGeneral.add(pnlDD);
		pnlDD.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlImagenDD = new JPanel();
		pnlImagenDD.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlDD.add(pnlImagenDD, BorderLayout.WEST);
		
		lblImgDD = new JLabel("");
		lblImgDD.setIcon(new ImageIcon(getClass().getResource("/dd.png")));
		originales.add("/dd.png");
		pnlImagenDD.add(lblImgDD);
		
		JPanel pnlPrecioDD = new JPanel();
		pnlPrecioDD.setBorder(new EmptyBorder(25, 0, 5, 5));
		pnlDD.add(pnlPrecioDD, BorderLayout.EAST);
		
		lblPrecioDD = new JLabel(".........");
		lblPrecioDD.setFont(new Font("Arial Black", Font.BOLD, 24));
		pnlPrecioDD.add(lblPrecioDD);
		labels.add(lblPrecioDD);
		
		JPanel pnlDatosDD = new JPanel();
		pnlDD.add(pnlDatosDD, BorderLayout.CENTER);
		pnlDatosDD.setLayout(new BoxLayout(pnlDatosDD, BoxLayout.Y_AXIS));
		
		JPanel pnlLAbelDD = new JPanel();
		pnlLAbelDD.setBorder(new EmptyBorder(5, 15, 5, 5));
		pnlLAbelDD.setAlignmentY(Component.TOP_ALIGNMENT);
		pnlDatosDD.add(pnlLAbelDD);
		pnlLAbelDD.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblDD = new JLabel("DISCO DURO");
		lblDD.setFont(new Font("Arial Black", Font.BOLD, 16));
		lblDD.setHorizontalAlignment(SwingConstants.LEFT);
		pnlLAbelDD.add(lblDD);
		
		JPanel pnlCBBDD = new JPanel();
		pnlCBBDD.setBorder(new EmptyBorder(10, 15, 10, 5));
		pnlDatosDD.add(pnlCBBDD);
		pnlCBBDD.setLayout(new GridLayout(0, 2, 0, 0));
		
		cbDD = new JComboBox();
		cbDD.setEnabled(false);
		cbDD.setModel(new DefaultComboBoxModel(new String[] {"------------------------------------------------------------------------------------------"}));
		pnlCBBDD.add(cbDD);
		
		
		
		JPanel pnlRAM = new JPanel();
		pnlRAM.setMaximumSize(new Dimension(32767, 100));
		pnlGeneral.add(pnlRAM);
		pnlRAM.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlImagenRAM = new JPanel();
		pnlImagenRAM.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlRAM.add(pnlImagenRAM, BorderLayout.WEST);
		
		lblImgRAM = new JLabel("");
		lblImgRAM.setIcon(new ImageIcon(getClass().getResource("/ram.png")));
		originales.add("/RAM.png");
		pnlImagenRAM.add(lblImgRAM);
		
		JPanel pnlPrecioRAM = new JPanel();
		pnlPrecioRAM.setBorder(new EmptyBorder(25, 0, 5, 5));
		pnlRAM.add(pnlPrecioRAM, BorderLayout.EAST);
		
		lblPrecioRAM = new JLabel(".........");
		lblPrecioRAM.setFont(new Font("Arial Black", Font.BOLD, 24));
		pnlPrecioRAM.add(lblPrecioRAM);
		labels.add(lblPrecioRAM);
		
		JPanel pnlDatosRAM = new JPanel();
		pnlRAM.add(pnlDatosRAM, BorderLayout.CENTER);
		pnlDatosRAM.setLayout(new BoxLayout(pnlDatosRAM, BoxLayout.Y_AXIS));
		
		JPanel pnlLAbelRAM = new JPanel();
		pnlLAbelRAM.setBorder(new EmptyBorder(5, 15, 5, 5));
		pnlLAbelRAM.setAlignmentY(Component.TOP_ALIGNMENT);
		pnlDatosRAM.add(pnlLAbelRAM);
		pnlLAbelRAM.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblRAM = new JLabel("MEMORIA RAM");
		lblRAM.setFont(new Font("Arial Black", Font.BOLD, 16));
		lblRAM.setHorizontalAlignment(SwingConstants.LEFT);
		pnlLAbelRAM.add(lblRAM);
		
		JPanel pnlCBBRAM = new JPanel();
		pnlCBBRAM.setBorder(new EmptyBorder(10, 15, 10, 5));
		pnlDatosRAM.add(pnlCBBRAM);
		pnlCBBRAM.setLayout(new GridLayout(0, 2, 0, 0));
		
		cbRAM = new JComboBox();
		cbRAM.setEnabled(false);
		cbRAM.setModel(new DefaultComboBoxModel(new String[] {"------------------------------------------------------------------------------------------"}));
		pnlCBBRAM.add(cbRAM);
		
		combos = new ArrayList<JComboBox>();
		combos.add(cbProc);
		combos.add(cbPlaca);
		combos.add(cbCaja);
		combos.add(cbFuente);
		combos.add(cbVenti);
		combos.add(cbGrafica);
		combos.add(cbDD);
		combos.add(cbRAM);
		productos = new ArrayList<>();
		for(int i = 0;i<8;i++) {
			ArrayList<Producto> aux = new ArrayList<Producto>();
			productos.add(aux);
		}
		imagenes = new ArrayList<JLabel>();
		imagenes.add(lblImgProc);
		imagenes.add(lblImgPlaca);
		imagenes.add(lblImgCaja);
		imagenes.add(lblImgFuente);
		imagenes.add(lblImgVenti);
		imagenes.add(lblImgGrafica);
		imagenes.add(lblImgDD);
		imagenes.add(lblImgRAM);
		
		btnCarrito.setEnabled(false);
		btnCarrito.setBackground(Color.YELLOW);
		
		config = new ArrayList<>();
		estado = nuevoPaso(estado);
		eventos = new EventosPanelConfiguracion(this);
	}
	
	// Esta funcion añade el producto elegido a la lista de configuracion completa, y cambia la imagen del panel correspondiente.
	public void añadir(Producto p) {
		config.add(p);
		int i =0;
		double suma = 0;
		for (Producto pro:config) {
			labels.get(i).setText(pro.getPrecio() + " €");
			BufferedImage img;
			try {
				URL url = new URL("http://18.209.156.132/tikalmi/img" + pro.getFoto());
				img = ImageIO.read(url);
				Image scaledImage = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
				imagenes.get(i).setIcon(new ImageIcon(scaledImage));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			i++;
			suma += pro.getPrecio();
		}
		lblPrecio.setText(suma + " €");
	}
	
	// Esta funcion es la que se encarga de saltar al siguiente paso, desbloqueando los componentes que tenga que desbloquear, y cargando la lista de articulos del tipo de componente que le toque
	public int nuevoPaso(int paso) {
	    	if (paso == 8) {
	    		btnCarrito.setEnabled(true);
	    		return paso;
	    	}
			try {
				ResultSet rs = gestion.getLogin().getBBDD().getProductos(orden.get(paso));
				
				while (rs.next()) {
					Producto p = new Producto(rs.getInt("id_producto"),rs.getString("nombre"), rs.getString("descripcion"),rs.getString("imagen"), rs.getDouble("precio"), rs.getInt("stock"), orden.get(paso));
					productos.get(paso).add(p);
					combos.get(paso).addItem(rs.getString("nombre"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error en PanelConfiguracion");
			}
			combos.get(paso).setEnabled(true);
			return paso + 1;
	}

	public GestionComponentes getGestion() {
		return gestion;
	}

	public void setGestion(GestionComponentes gestion) {
		this.gestion = gestion;
	}

	public ArrayList<ArrayList<Producto>> getProductos() {
		return productos;
	}

	public ArrayList<Producto> getConfig() {
		return config;
	}

	public void setConfig(ArrayList<Producto> config) {
		this.config = config;
	}

	public ArrayList<JLabel> getLabels() {
		return labels;
	}

	public void setLabels(ArrayList<JLabel> labels) {
		this.labels = labels;
	}

	public JLabel getLblTitulo() {
		return lblTitulo;
	}

	public void setLblTitulo(JLabel lblTitulo) {
		this.lblTitulo = lblTitulo;
	}

	public JButton getBtnReiniciar() {
		return btnReiniciar;
	}

	public void setBtnReiniciar(JButton btnReiniciar) {
		this.btnReiniciar = btnReiniciar;
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

	public JLabel getLblPrecio() {
		return lblPrecio;
	}

	public void setLblPrecio(JLabel lblPrecio) {
		this.lblPrecio = lblPrecio;
	}

	public JButton getBtnCarrito() {
		return btnCarrito;
	}

	public void setBtnCarrito(JButton btnCarrito) {
		this.btnCarrito = btnCarrito;
	}

	public JLabel getLblPrecioProc() {
		return lblPrecioProc;
	}

	public void setLblPrecioProc(JLabel lblPrecioProc) {
		this.lblPrecioProc = lblPrecioProc;
	}

	public JLabel getLblProc() {
		return lblProc;
	}

	public void setLblProc(JLabel lblProc) {
		this.lblProc = lblProc;
	}

	public JComboBox getCbProc() {
		return cbProc;
	}

	public void setCbProc(JComboBox cbProc) {
		this.cbProc = cbProc;
	}

	public JLabel getLblImgPlaca() {
		return lblImgPlaca;
	}

	public void setLblImgPlaca(JLabel lblImgPlaca) {
		this.lblImgPlaca = lblImgPlaca;
	}

	public JLabel getLblPrecioPlaca() {
		return lblPrecioPlaca;
	}

	public void setLblPrecioPlaca(JLabel lblPrecioPlaca) {
		this.lblPrecioPlaca = lblPrecioPlaca;
	}

	public JLabel getLblPlaca() {
		return lblPlaca;
	}

	public void setLblPlaca(JLabel lblPlaca) {
		this.lblPlaca = lblPlaca;
	}

	public JComboBox getCbPlaca() {
		return cbPlaca;
	}

	public void setCbPlaca(JComboBox cbPlaca) {
		this.cbPlaca = cbPlaca;
	}

	public JLabel getLblImgCaja() {
		return lblImgCaja;
	}

	public void setLblImgCaja(JLabel lblImgCaja) {
		this.lblImgCaja = lblImgCaja;
	}

	public JLabel getLblPrecioCaja() {
		return lblPrecioCaja;
	}

	public void setLblPrecioCaja(JLabel lblPrecioCaja) {
		this.lblPrecioCaja = lblPrecioCaja;
	}

	public JLabel getLblCaja() {
		return lblCaja;
	}

	public void setLblCaja(JLabel lblCaja) {
		this.lblCaja = lblCaja;
	}

	public JComboBox getCbCaja() {
		return cbCaja;
	}

	public void setCbCaja(JComboBox cbCaja) {
		this.cbCaja = cbCaja;
	}

	public JLabel getLblImgFuente() {
		return lblImgFuente;
	}

	public void setLblImgFuente(JLabel lblImgFuente) {
		this.lblImgFuente = lblImgFuente;
	}

	public JLabel getLblPrecioFuente() {
		return lblPrecioFuente;
	}

	public void setLblPrecioFuente(JLabel lblPrecioFuente) {
		this.lblPrecioFuente = lblPrecioFuente;
	}

	public JLabel getLblFuente() {
		return lblFuente;
	}

	public void setLblFuente(JLabel lblFuente) {
		this.lblFuente = lblFuente;
	}

	public JComboBox getCbFuente() {
		return cbFuente;
	}

	public void setCbFuente(JComboBox cbFuente) {
		this.cbFuente = cbFuente;
	}

	public JLabel getLblImgVenti() {
		return lblImgVenti;
	}

	public void setLblImgVenti(JLabel lblImgVenti) {
		this.lblImgVenti = lblImgVenti;
	}

	public JLabel getLblPrecioVenti() {
		return lblPrecioVenti;
	}

	public void setLblPrecioVenti(JLabel lblPrecioVenti) {
		this.lblPrecioVenti = lblPrecioVenti;
	}

	public JLabel getLblVenti() {
		return lblVenti;
	}

	public void setLblVenti(JLabel lblVenti) {
		this.lblVenti = lblVenti;
	}

	public JComboBox getCbVenti() {
		return cbVenti;
	}

	public void setCbVenti(JComboBox cbVenti) {
		this.cbVenti = cbVenti;
	}

	public JLabel getLblImgGrafica() {
		return lblImgGrafica;
	}

	public void setLblImgGrafica(JLabel lblImgGrafica) {
		this.lblImgGrafica = lblImgGrafica;
	}

	public JLabel getLblPrecioGrafica() {
		return lblPrecioGrafica;
	}

	public void setLblPrecioGrafica(JLabel lblPrecioGrafica) {
		this.lblPrecioGrafica = lblPrecioGrafica;
	}

	public JLabel getLblGrafica() {
		return lblGrafica;
	}

	public void setLblGrafica(JLabel lblGrafica) {
		this.lblGrafica = lblGrafica;
	}

	public JComboBox getCbGrafica() {
		return cbGrafica;
	}

	public void setCbGrafica(JComboBox cbGrafica) {
		this.cbGrafica = cbGrafica;
	}

	public JLabel getLblImgDD() {
		return lblImgDD;
	}

	public void setLblImgDD(JLabel lblImgDD) {
		this.lblImgDD = lblImgDD;
	}

	public JLabel getLblPrecioDD() {
		return lblPrecioDD;
	}

	public void setLblPrecioDD(JLabel lblPrecioDD) {
		this.lblPrecioDD = lblPrecioDD;
	}

	public JLabel getLblDD() {
		return lblDD;
	}

	public void setLblDD(JLabel lblDD) {
		this.lblDD = lblDD;
	}

	public JComboBox getCbDD() {
		return cbDD;
	}

	public void setCbDD(JComboBox cbDD) {
		this.cbDD = cbDD;
	}

	public EventosPanelConfiguracion getEventos() {
		return eventos;
	}

	public void setEventos(EventosPanelConfiguracion eventos) {
		this.eventos = eventos;
	}

	public ArrayList<Integer> getOrden() {
		return orden;
	}

	public void setOrden(ArrayList<Integer> orden) {
		this.orden = orden;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public ArrayList<JComboBox> getCombos() {
		return combos;
	}

	public void setCombos(ArrayList<JComboBox> combos) {
		this.combos = combos;
	}

	public ArrayList<JLabel> getImagenes() {
		return imagenes;
	}

	public void setImagenes(ArrayList<JLabel> imagenes) {
		this.imagenes = imagenes;
	}

	public ArrayList<String> getOriginales() {
		return originales;
	}

	public void setOriginales(ArrayList<String> originales) {
		this.originales = originales;
	}

	public void setProductos(ArrayList<ArrayList<Producto>> productos) {
		this.productos = productos;
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public void setBtnVolver(JButton btnVolver) {
		this.btnVolver = btnVolver;
	}

}
