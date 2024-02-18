
public class Producto {
	private int id;
	private String nombre;
	private String desc;
	private double precio;
	private String foto;
	private int stock;
	private int enCarro;
	private int tipo;
	private int noEnStock;
	
	// Esta es la clase producto que tiene todos los datos interesantes que necesitamos para realizar operaciones con los productos.
	// Como variables interesantes tenemos enCarro que nos indica la cantidad de ese producto actualmente en el carro, el tipo para saber que tipo de producto es,
	// y noEnStock que nos indica articulos que el stock del pedido ya este reducido en la base de datos porque esa parte del pedido ya esta guardada.
	public Producto(int id,String nombre,String desc, String foto,double precio,int stock, int tipo) {
		this.id = id;
		this.nombre = nombre;
		this.desc = desc;
		this.precio = precio;
		this.foto = "/" + foto;
		this.stock = stock;
		this.tipo = tipo;
		noEnStock=0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getEnCarro() {
		return enCarro;
	}

	public void setEnCarro(int enCarro) {
		this.enCarro = enCarro;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getNoEnStock() {
		return noEnStock;
	}

	public void setNoEnStock(int noEnStock) {
		this.noEnStock = noEnStock;
	}
}
