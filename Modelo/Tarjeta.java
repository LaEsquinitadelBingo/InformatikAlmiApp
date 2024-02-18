import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tarjeta  extends JPanel {
    private JLabel nombre;
    private JLabel desc;
    private JLabel precio;
    private JLabel foto;
    private JLabel stock;
    private JButton comprar;
    private EventosTarjeta eventos;
    private PanelPrincipal panel;
    private Producto producto;

    // El panel principal de nuestro programa tiene 16 tarjetas, 1 por cada producto que tienen los datos de cada producto y se van rellenando con nueva informacion segun cambiemos de seccion
    // Por eso decidimos hacer una clase tarjeta que tiene todos los componentes de esa tarjeta, asi como el producto que esta en ella en el momento actual.
    public Tarjeta(PanelPrincipal p){
        panel = p;
			this.setLayout(null);
            this.setMinimumSize(new Dimension(300, 300));
            this.setMaximumSize(new Dimension(300, 300));
            this.setPreferredSize(new Dimension(300, 300));
            foto = new JLabel();
            foto.setBounds(40, 0, 180, 160);
            this.add(foto);
            nombre = new JLabel();
            nombre.setBounds(40, 160, 400, 20);
            nombre.setFont(new java.awt.Font("Arial", 0, 20));
            nombre.setAlignmentY(CENTER_ALIGNMENT);
			this.add(nombre);
			desc = new JLabel();
            desc.setBounds(40, 180, 400, 20);
            this.add(desc);
            stock = new JLabel();
            stock.setBounds(40, 200, 400, 20);
            this.add(stock);
			precio = new JLabel();
            precio.setBounds(40, 220, 400, 20);
            this.add(precio);
            comprar = new JButton("Añadir al carrito");
            comprar.setBounds(40, 240, 150, 30);
            comprar.setBackground(Color.YELLOW);
            this.add(comprar);
            this.setBorder(BorderFactory.createCompoundBorder(
                    this.getBorder(), 
                    BorderFactory.createEmptyBorder(0, 10, 0, 0)));
            eventos = new EventosTarjeta(this); 
    }
    
    public void vaciar() {
    	producto = null;
    	nombre.setText("");
    	desc.setText("");
    	precio.setText("");
    	foto.setIcon(null);
    	stock.setText("");
    	comprar.setVisible(false);
    	
    }

	public JLabel getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre.setText(nombre);
	}

	public JLabel getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc.setText(desc);
	}

	public JLabel getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio.setText(precio);
	}

	public JLabel getFoto() {
		return foto;
	}

	public void setFoto(Icon foto) {
		this.foto.setIcon(foto);
	}
	
	public JLabel getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock.setText(stock);
	}

	public JButton getComprar() {
		return comprar;
	}

	public void setComprar(JButton comprar) {
		this.comprar = comprar;
	}

	public PanelPrincipal getPanel() {
		return panel;
	}

	public void setPanel(PanelPrincipal panel) {
		this.panel = panel;
	}

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
