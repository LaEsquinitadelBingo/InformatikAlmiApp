import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.IntStream;
import javax.swing.border.EmptyBorder;

public class PanelProducto extends JPanel {
	private JButton btnAtras;
	private JButton btnComprar;
	private GestionComponentes gestion;
	private Producto producto;
	JComboBox<Integer> cbCantidad;
	
    public PanelProducto(Producto producto, GestionComponentes g) throws SQLException {
    	gestion = g;
    	this.producto = producto;
    	setMinimumSize(new Dimension(800, 800));
        setLayout(new BorderLayout());
        ResultSet datos = gestion.getLogin().getBBDD().getDatos(producto.getId(), producto.getTipo());
        ArrayList<String> campos = generarCampos(datos, producto.getTipo());
        int i = 0;
        // Panel de imagen
		JPanel imagePanel = new JPanel();
		JLabel imageLabel = new JLabel(new ImageIcon(new ImageIcon("images/" + producto.getFoto()).getImage().getScaledInstance(700, 600, Image.SCALE_DEFAULT)));
		imagePanel.add(imageLabel);

		// Panel de información
		JPanel infoPanel = new JPanel();
		infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		infoPanel.setBorder(new EmptyBorder(100, 0, 0, 0));
		infoPanel.setLayout(new BorderLayout(0, 0));


		// Panel inferior
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(new EmptyBorder(0, 0, 50, 0));
		bottomPanel.setLayout(new BorderLayout(0, 0));
		infoPanel.add(bottomPanel, BorderLayout.SOUTH);

		JPanel panel_2 = new JPanel();
		bottomPanel.add(panel_2, BorderLayout.WEST);

		JLabel priceLabel = new JLabel(campos.get(i++));
		priceLabel.setForeground(Color.RED);
		priceLabel.setFont(new Font("Arial", Font.BOLD, 22));
		panel_2.add(priceLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(15, 15, 15, 15));
		panel_2.add(panel_1);

		cbCantidad = new JComboBox<Integer>();
		cbCantidad.setModel(new DefaultComboBoxModel(new String[] {"-Cantidad-", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
		panel_1.add(cbCantidad);

		btnComprar = new JButton("Comprar");
		btnComprar.setBackground(Color.YELLOW);
		panel_2.add(btnComprar);

		JPanel panel_3 = new JPanel();
		bottomPanel.add(panel_3, BorderLayout.EAST);

		// Agregar los paneles al panel principal
		add(imagePanel, BorderLayout.WEST);
		add(infoPanel, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		infoPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblTitulo = new JLabel(campos.get(i++));
		lblTitulo.setPreferredSize(new Dimension(600, 150));
		lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
		panel.add(lblTitulo);

		JLabel dato1 = new JLabel(campos.get(i++));
		dato1.setHorizontalAlignment(SwingConstants.LEFT);
		dato1.setHorizontalTextPosition(SwingConstants.LEFT);
		dato1.setPreferredSize(new Dimension(600, 30));
		dato1.setFont(new Font("Arial", Font.PLAIN, 18));
		dato1.setBorder(new EmptyBorder(15, 0, 0, 0));
		panel.add(dato1);

		JLabel dato2 = new JLabel(campos.get(i++));
		dato2.setPreferredSize(new Dimension(600, 30));
		dato2.setFont(new Font("Arial", Font.PLAIN, 18));
		dato2.setBorder(new EmptyBorder(15, 0, 0, 0));
		panel.add(dato2);

		JLabel dato3 = new JLabel(campos.get(i++));
		dato3.setPreferredSize(new Dimension(600, 30));
		dato3.setFont(new Font("Arial", Font.PLAIN, 18));
		dato3.setBorder(new EmptyBorder(15, 0, 0, 0));
		panel.add(dato3);

		JLabel descLabel = new JLabel(campos.get(i++));
		descLabel.setHorizontalAlignment(SwingConstants.LEFT);
		descLabel.setPreferredSize(new Dimension(600, 200));
		descLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		panel.add(descLabel);

		JLabel stockLabel = new JLabel(campos.get(i++));
		stockLabel.setPreferredSize(new Dimension(600, 30));
		stockLabel.setForeground(Color.GRAY);
		stockLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		panel.add(stockLabel);
        
        JPanel panelBotom = new JPanel();
        panelBotom.setBorder(new EmptyBorder(50, 0, 100, 0));
        add(panelBotom, BorderLayout.SOUTH);
        
        btnAtras = new JButton("Volver a la lista");
        btnAtras.setBackground(new Color(128, 255, 255));
        btnAtras.setPreferredSize(new Dimension(400, 50));
        panelBotom.add(btnAtras);
        
        JPanel pnlTop = new JPanel();
        pnlTop.setBorder(new EmptyBorder(50, 0, 50, 0));
        add(pnlTop, BorderLayout.NORTH);
        EventosPanelProducto eventos = new EventosPanelProducto(this);
    }
    
    public ArrayList<String> generarCampos(ResultSet datos, int tipo) {
    	ArrayList<String> campos = new ArrayList<>();
    	try {
			campos.add(datos.getString("precio") + " €");
			campos.add("<html><p style='text-align:left;'>" + datos.getString("nombre")+ "</p></html>");
			switch (tipo) {
				case 1:
					break;
				case 2:
					campos.add("<html><p style='text-align:left;'>\u2022 " + "RAM: " + datos.getString("ram")+ "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Grafica: "+ datos.getString("grafica") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Procesador: " + datos.getString("procesador") + " Hz</p></html>");
					break;
				case 3:
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Dimension de Pantalla: " + datos.getString("dimension")+ "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Puertos de video: "+ datos.getInt("puertos_video") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Tasa de Refresco: " + datos.getInt("tasa_refresco") + " Hz</p></html>");
					
					break;
				case 4:
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Frecuencia de CPU: " + datos.getInt("frecuencia") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Consumo: "+ datos.getInt("consumo") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Cantidad de Nucleos: " + datos.getInt("nucleos") + "</p></html>");
					
					break;
				case 5:
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Tarjeta Grafica Integrada: " + (datos.getInt("grafica")==0 ? "No" : "Si") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Tamaño de Placa Base: "+ datos.getString("tamano") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "" + "</p></html>");
					
					break;
				case 6:
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Generacion de RAM: " + datos.getString("generacion") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Tamaño de la RAM: "+ datos.getInt("tamano") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Frecuencia de la RAM: " + datos.getInt("frecuencia") +"</p></html>");
					
					break;
				case 7:
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Capacidad de Tarjeta: " + datos.getInt("capacidad") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Consumo: "+ datos.getInt("consumo") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Puertos de Video: " + datos.getInt("puertos_video") + "</p></html>");
					
					break;
				case 8:
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Tipo de Teclado: " + (datos.getInt("inalambrico")==0 ? "Con Cable" : "Inalambrico") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Peso: "+ datos.getInt("peso") + " gramos</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Distribuicion de Teclas: " + datos.getString("distribucion") + "</p></html>");
					
					break;
				case 9:
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Tipo de Raton: " + (datos.getInt("inalambrico")==0 ? "Con Cable" : "Inalambrico") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Peso: "+ datos.getInt("peso") + " gramos</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Dato 3: " + "</p></html>");
					
					break;
				case 10:
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Modular: " + (datos.getInt("modular")==0 ? "No" : "Si") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Capacidad: "+ datos.getInt("capacidad") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "" + "</p></html>");
					
					break;
				case 11:
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Inalambrico: " + (datos.getInt("inalambrico")==0 ? "No" : "Si") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Cancelacion de Ruido: "+ (datos.getInt("cancelacion_ruido")==0 ? "No" : "Si") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Microfono incorporado: " + (datos.getInt("microfono")==0 ? "No" : "Si") + "</p></html>");
					
					break;
				case 12:
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Dimension de la torre: " + datos.getString("dimension") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "" + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "" + "</p></html>");
					
					break;
				case 14:
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Almacenamiento Disco Duro: " + datos.getInt("almacenamiento") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Velocidad de Disco Duro: " + datos.getInt("velocidad") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Peso:" + datos.getInt("peso") + " gramos</p></html>");
					break;
				case 13:
					campos.add("<html><p style='text-align:left;'>\u2022 " + "Tamaño del Ventilador: " + datos.getInt("tamano") + "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + ""+ "</p></html>");
					campos.add("<html><p style='text-align:left;'>\u2022 " + "" + "</p></html>");
					break;
			}
			campos.add("<html><p style='text-align:left;'>" + datos.getString("descripcion")+ "</p></html>");
			campos.add("Stock: " + datos.getInt("stock") + " unidades disponibles.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return campos;
    }
    
	public JButton getBtnAtras() {
		return btnAtras;
	}
	public void setBtnAtras(JButton btnAtras) {
		this.btnAtras = btnAtras;
	}
	public JButton getBtnComprar() {
		return btnComprar;
	}
	public void setBtnComprar(JButton btnComprar) {
		this.btnComprar = btnComprar;
	}
	public GestionComponentes getGestion() {
		return gestion;
	}
	public void setGestion(GestionComponentes gestion) {
		this.gestion = gestion;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public JComboBox<Integer> getCbCantidad() {
		return cbCantidad;
	}
	public void setCbCantidad(JComboBox<Integer> cbCantidad) {
		this.cbCantidad = cbCantidad;
	}
}