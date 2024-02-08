import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatos {
    private static final String URL = "jdbc:oracle:thin:@//52.206.166.129:1521/ORCL";
    private static final String USER = "RETO2";
    private static final String PASS = "almi123";
    private Connection cn;


	public BaseDatos() {

	}
	
    public void conectar() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            cn = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getProductos() {
		String query = "SELECT id_producto, nombre, descripcion, precio, stock, imagen FROM ultimos_productos"; 
		ResultSet resultSet = null;

		PreparedStatement sentencia;
		try {
				sentencia = cn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			resultSet = sentencia.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en UltimosProductos");
		}

		return resultSet;
	}
    
	public ResultSet getProductos(int tipo) {
        String query = "";
        switch (tipo) {
        case 0:
        	query = "SELECT id_producto, nombre, descripcion, precio, stock, imagen FROM ultimos_productos";
        	break;
        case 1:
        	break;
        case 2:
        	break;
        case 3:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN pantalla ON producto.id_producto = pantalla.id_producto";
        	break;
        case 4:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN cpu ON producto.id_producto = cpu.id_producto";
        	break;
        case 5:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN placa_base ON producto.id_producto = placa_base.id_producto";
        	break;
        case 6:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN ram ON producto.id_producto = ram.id_producto";
        	break;
        case 7:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN tarjeta_grafica ON producto.id_producto = tarjeta_grafica.id_producto";
        	break;
        case 8:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN teclado ON producto.id_producto = teclado.id_producto";
        	break;
        case 9:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN raton ON producto.id_producto = raton.id_producto";
        	break;
        case 10:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN fuente_alimentacion ON producto.id_producto = fuente_alimentacion.id_producto";
        	break;
        case 11:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN cascos ON producto.id_producto = cascos.id_producto";
        	break;
        case 12:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN caja ON producto.id_producto = caja.id_producto";
        	break;
        case 13:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN ventilador ON producto.id_producto = ventilador.id_producto";
        	break;
        case 14:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN disco_duro ON producto.id_producto = disco_duro.id_producto";
        	break;
        }

		ResultSet resultSet = null;

		PreparedStatement sentencia;
		try {
				sentencia = cn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			resultSet = sentencia.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en getRatones");
		}

		return resultSet;
	}
	
	public ResultSet getDatos(int id,int tipo) {
		String query = "SELECT nombre, descripcion, precio, stock, imagen,inalambrico,peso FROM producto INNER JOIN raton ON raton.id_producto = producto.id_producto WHERE producto.id_producto = ?";
		switch (tipo) {
		case 0:
			query = "SELECT id_producto, nombre, descripcion, precio, stock, imagen FROM ultimos_productos";
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			query = "SELECT nombre, descripcion, precio, stock, imagen,dimension, puertos_video, tasa_refresco FROM producto INNER JOIN pantalla ON producto.id_producto = pantalla.id_producto WHERE producto.id_producto = ?";
			break;
		case 4:
			query = "SELECT nombre, descripcion, precio, stock, imagen,frecuencia, consumo, nucleos FROM producto INNER JOIN cpu ON producto.id_producto = cpu.id_producto WHERE producto.id_producto = ?";
			break;
		case 5:
			query = "SELECT nombre, descripcion, precio, stock, imagen, tamaño, grafica FROM producto INNER JOIN placa_base ON producto.id_producto = placa_base.id_producto WHERE producto.id_producto = ?";
			break;
		case 6:
			query = "SELECT nombre, descripcion, precio, stock, imagen, frecuencia, generacion, tamaño FROM producto INNER JOIN ram ON producto.id_producto = ram.id_producto WHERE producto.id_producto = ?";
			break;
		case 7:
			query = "SELECT nombre, descripcion, precio, stock, imagen,capacidad, consumo, puertos_video FROM producto INNER JOIN tarjeta_grafica ON producto.id_producto = tarjeta_grafica.id_producto WHERE producto.id_producto = ?";
			break;
		case 8:
			query = "SELECT nombre, descripcion, precio, stock, imagen,distribucion, peso, inalambrico FROM producto INNER JOIN teclado ON producto.id_producto = teclado.id_producto WHERE producto.id_producto = ?";
			break;
		case 9:
			query = "SELECT nombre, descripcion, precio, stock, imagen, inalambrico, peso FROM producto INNER JOIN raton ON producto.id_producto = raton.id_producto WHERE producto.id_producto = ?";
			break;
		case 10:
			query = "SELECT nombre, descripcion, precio, stock, imagen, capacidad, modular FROM producto INNER JOIN fuente_alimentacion ON producto.id_producto = fuente_alimentacion.id_producto WHERE producto.id_producto = ?";
			break;
		case 11:
			query = "SELECT nombre, descripcion, precio, stock, imagen, inalambrico, cancelacion_ruido, microfono FROM producto INNER JOIN cascos ON producto.id_producto = cascos.id_producto WHERE producto.id_producto = ?";
			break;
		case 12:
			query = "SELECT nombre, descripcion, precio, stock, imagen, dimension FROM producto INNER JOIN caja ON producto.id_producto = caja.id_producto WHERE producto.id_producto = ?";
			break;
		case 13:
			query = "SELECT nombre, descripcion, precio, stock, imagen, tamaño FROM producto INNER JOIN ventilador ON producto.id_producto = ventilador.id_producto WHERE producto.id_producto = ?";
			break;
		case 14:
			query = "SELECT nombre, descripcion, precio, stock, imagen, almacenamiento, velocidad, peso FROM producto INNER JOIN disco_duro ON producto.id_producto = disco_duro.id_producto WHERE producto.id_producto = ?";
			break;
		}
		PreparedStatement sentencia;
		try {
			sentencia = cn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_UPDATABLE);
			sentencia.setInt(1, id);
			ResultSet rs = sentencia.executeQuery();
			if (rs.first()) {
				return rs;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

    public String getEmpleado(String dni, String passPrograma) {
    	String sentSql = "SELECT * FROM empleado WHERE dni = ? AND passPrograma = ?";
    	PreparedStatement sentencia;
    	try {
    		sentencia = cn.prepareStatement(sentSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
    				ResultSet.CONCUR_UPDATABLE);
    		sentencia.setString(1, dni.trim());
    		sentencia.setString(2, passPrograma.trim());
    		ResultSet rs = sentencia.executeQuery();
    		if (rs.next()) {
    			return rs.getString("nombre") + " " + rs.getString("apellido_1");
    		} else {
    			return "";
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return "";
    }
}
