import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class PanelPrincipal extends JPanel{
	public static final int MONITOR = 0;
    private JPanel pnlBusqueda;
    private JPanel tablePanel;
	private ArrayList<Producto> articulos;
	private ArrayList<Producto> ratones;
	private ArrayList<Tarjeta> tarjetas;
	private ArrayList<ArrayList<Producto>> productos;
	private JLabel titleLabel;
	private JPanel titleBar;
	private GestionComponentes gestion;
	private JPanel panel;
	private JPanel panel_1;
	private Label label;
	private JTextField txtBuscar;
	private JComboBox comboBox;
	private JPanel pnlPaginas;
	private JButton btnPaginaSiguiente;
	private JButton btnPaginaAnterior;
	private int paginaActual=0;
	private EventosPanelPrincipal eventos;
	private int tipo;

    public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	// Este es el panel principal, en el que estan todas las tarjetas con los articulos de nuestra base de datos y que va cambiando segun cambiemos de secciones
	public PanelPrincipal(GestionComponentes g) {
		gestion = g;


		
        rellenarArticulos(0);

        // El panel tiene un borderlayout con un titulo, con el nombre de la seccion y un panel de busqueda.
        // Y un panel al sur que tiene 2 botones para controlar la pagina en la que estamos.
		this.setLayout(new BorderLayout());

        titleBar = new JPanel(new BorderLayout());
        titleBar.setPreferredSize(new Dimension(titleBar.getPreferredSize().width, 50));
		titleLabel = new JLabel("                           Novedades");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
		pnlBusqueda = new JPanel();
        pnlBusqueda.setBorder(BorderFactory.createCompoundBorder(
        pnlBusqueda.getBorder(), 
        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        pnlBusqueda.setLayout(new BorderLayout(0, 0));

		
		titleBar.add(titleLabel, BorderLayout.CENTER);
		titleBar.add(pnlBusqueda, BorderLayout.EAST);
		
		panel = new JPanel();
		pnlBusqueda.add(panel, BorderLayout.WEST);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"-Ordenar-", "Dato1", "Dato2", "Dato3"}));
		panel.add(comboBox);
		
		panel_1 = new JPanel();
		pnlBusqueda.add(panel_1, BorderLayout.EAST);
		
		label = new Label("Buscar");
		panel_1.add(label);
		
		txtBuscar = new JTextField();
		txtBuscar.setFont(new Font("Arial", Font.PLAIN, 12));
		txtBuscar.setColumns(20);
		panel_1.add(txtBuscar);
		this.add(titleBar, BorderLayout.NORTH);

		tablePanel = new JPanel();

		this.add(tablePanel, BorderLayout.CENTER);
		tablePanel.setLayout(new GridLayout(3, 4, 0, 0));
		
		pnlPaginas = new JPanel();
		add(pnlPaginas, BorderLayout.SOUTH);
		pnlPaginas.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnPaginaAnterior = new JButton("<");
		pnlPaginas.add(btnPaginaAnterior);
		
		btnPaginaSiguiente = new JButton(">");
		pnlPaginas.add(btnPaginaSiguiente);
		
		tarjetas = new ArrayList<>();
		for (int i = 0; i < 18; i ++) {
			tarjetas.add(new Tarjeta(this));
		}
		rellenarTarjetas();
	
		añadirArticulos(4);
		
		eventos = new EventosPanelPrincipal(this);
    }
    
	
	// Este proceimiento se encarga de rellenar las tarjetas con los datos de los articulso, Dependiento del tipo hara una consulta a la base de datos y rellenara el array de articulos con todos los articulos de este tipo en la Base de DAtos
	public void rellenarArticulos(int tipo) {
		this.tipo=tipo;
        articulos = new ArrayList<>();
        
		try {
			ResultSet rs = gestion.getLogin().getBBDD().getProductos(tipo);
			int cont = 0;
			
			while (rs.next()) {
				Producto p = new Producto(rs.getInt("id_producto"),rs.getString("nombre"), rs.getString("descripcion"),rs.getString("imagen"), rs.getDouble("precio"), rs.getInt("stock"), tipo == 0 ? 3 +(cont/2) : tipo);
				articulos.add(p);
				cont++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en PanelPrincipal");
		}
    }
	
	// Este procedimiento hace lo mismo que el anterior pero en este caso lo hace con los articulos procedentes de una busqueda, haciendo la consulta de buscar.
	public void rellenarArticulos(String texto, int tipo) {
        articulos = new ArrayList<>();
        
		try {
			ResultSet rs = gestion.getLogin().getBBDD().buscar(texto,tipo);
			int cont = 0;

			while (rs.next()) {
				Producto p = new Producto(rs.getInt("id_producto"),rs.getString("nombre"), rs.getString("descripcion"),rs.getString("imagen"), rs.getDouble("precio"), rs.getInt("stock"), tipo);
				articulos.add(p);
				cont++;
			}	
		} catch (SQLException e) {
			System.out.println("Error en PanelPrincipal");
		} catch (NullPointerException n) {
			JOptionPane.showMessageDialog(this, "No se ha encontrado ningun articulo con ese nombre","Error",  1);
		}
    }
	
	// Este procedimiento rellena todos los campos de cada una de las tarjetas con los datos procedentes del array articulos, dependiendo de en que pagina este
	// Tambien se encarga de controlar los botones de paginas y el txt de busqueda.
	public void rellenarTarjetas() {
		if (tipo == 0) txtBuscar.setEnabled(false);
		else txtBuscar.setEnabled(true);
		if (paginaActual == 0) btnPaginaAnterior.setEnabled(false);
		else btnPaginaAnterior.setEnabled(true);
		if (articulos.size() < tarjetas.size() * (paginaActual + 1)) btnPaginaSiguiente.setEnabled(false);
		else btnPaginaSiguiente.setEnabled(true);
		for (int i = 0; i < tarjetas.size(); i ++) {
			
			if (i + (paginaActual * tarjetas.size()) >= articulos.size()) tarjetas.get(i).vaciar();
			else {
				tarjetas.get(i).getComprar().setVisible(true);
				tarjetas.get(i).setProducto(articulos.get(i + (paginaActual * tarjetas.size())));
				tarjetas.get(i).setNombre(articulos.get(i + (paginaActual * tarjetas.size())).getNombre());
				tarjetas.get(i).setDesc(articulos.get(i + (paginaActual * tarjetas.size())).getDesc());
				tarjetas.get(i).setPrecio(String.valueOf(articulos.get(i + (paginaActual * tarjetas.size())).getPrecio()) + " €");
				int stock = articulos.get(i + (paginaActual * tarjetas.size())).getStock();
				for (Producto c: gestion.getCarro()) {
					if (articulos.get(i).getId() == c.getId()) {
						stock = stock - c.getEnCarro() + c.getNoEnStock();
					}
				}
				ImageIcon icon;
				try {
					BufferedImage img;

						//img = ImageIO.read(getClass().getResource(articulos.get(i).getFoto()));
					img = ImageIO.read(new File("images",articulos.get(i + (paginaActual * tarjetas.size())).getFoto()));
					Image scaledImage = img.getScaledInstance(180, 160, Image.SCALE_SMOOTH);
					// En el caso de que el stock del articulo sea 0, le damos una tonalidad gris para dar a entender que no se puede añadir
					if (stock == 0) {
						BufferedImage grayscale = new BufferedImage(
								img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
						Graphics g = grayscale.getGraphics();
						g.drawImage(img, 0, 0, null);
						g.dispose();
						scaledImage = grayscale.getScaledInstance(180, 160, Image.SCALE_SMOOTH);

					}
					icon = new ImageIcon(scaledImage);
				} catch (IOException e) {
					icon = null;
				}
				if (stock == 0) {
					tarjetas.get(i).setStock("Agotado");
					tarjetas.get(i).getComprar().setEnabled(false);
				} else {
					tarjetas.get(i).setStock("Stock: " + stock);
					tarjetas.get(i).getComprar().setEnabled(true);
				}
				tarjetas.get(i).setFoto(icon);
			}
		}
		
	}
	
	// Por ultimo un procedimiento que ánade todas las tarjetas al tablePanel principal del panel, la x nos indica el espacio que tenemos en pantalla, dependiendo de si tenemos alguna de las barras laterales minimizadas.
	public void añadirArticulos(int x) {
		tablePanel.removeAll();
		for (int i = 0; i < x*3; i ++) {
			tablePanel.add(tarjetas.get(i));
			
		}
	}
	
    public int getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}

	public JButton getBtnPaginaSiguiente() {
		return btnPaginaSiguiente;
	}

	public void setBtnPaginaSiguiente(JButton btnPaginaSiguiente) {
		this.btnPaginaSiguiente = btnPaginaSiguiente;
	}

	public JButton getBtnPaginaAnterior() {
		return btnPaginaAnterior;
	}

	public void setBtnPaginaAnterior(JButton btnPaginaAnterior) {
		this.btnPaginaAnterior = btnPaginaAnterior;
	}

	public void setTxtBuscar(JTextField txtBuscar) {
		this.txtBuscar = txtBuscar;
	}

	public JPanel getPnlBusqueda() {
		return pnlBusqueda;
	}

	public void setPnlBusqueda(JPanel pnlBusqueda) {
		this.pnlBusqueda = pnlBusqueda;
	}

	public JPanel getTablePanel() {
		return tablePanel;
	}

	public void setTablePanel(JPanel tablePanel) {
		this.tablePanel = tablePanel;
	}

	public JLabel getTitleLabel() {
		return titleLabel;
	}

	public void setTitleLabel(JLabel titleLabel) {
		this.titleLabel = titleLabel;
	}

	public JTextField getTxtBuscar() {
		return txtBuscar;
	}

	public void setSearchField(JTextField txtBuscar) {
		this.txtBuscar = txtBuscar;
	}

	public JPanel getTitleBar() {
		return titleBar;
	}

	public void setTitleBar(JPanel titleBar) {
		this.titleBar = titleBar;
	}

	public GestionComponentes getGestion() {
		return gestion;
	}

	public void setGestion(GestionComponentes gestion) {
		this.gestion = gestion;
	}
}
