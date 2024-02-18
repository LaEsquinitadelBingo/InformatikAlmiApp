import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import oracle.jdbc.OracleConnection;

public class BaseDatos {
	//La clase en la que conecto a base de datos y tengo todas las funciones relacionadas con ella
	//Aqui tengo las 2 ips, la del servidor de aws y la que tengo yo en casa para hacer prouebas.
    private static final String URL = "jdbc:oracle:thin:@//52.206.166.129:1521/ORCL";    //Conexion Server
	//private static final String URL = "jdbc:oracle:thin:@//192.168.1.135/ORCLCDB";      //Conexion mi casa
    private static final String USER = "RETO2";
    private static final String PASS = "almi123";
    private Connection cn;


	public BaseDatos() {

	}
	
	//Funccion para conectar usando el driver jbdc de oracle
    public void conectar() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            cn = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Funcion que devuelve la lista de los 2 ultimos productos metidos de cada tipo, ultimos_productos es una vista creada y almacenada en oracle
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
    
    // Funcion que devuelve los datos de un cliente si este es socio(Los socios tienen el dni metido) para darles un 20% de descuento
    public ResultSet getUsuario(String numSocio) {
    	String query = "select * from cliente where dni_nif= ?";
		PreparedStatement sentencia;
		try {
            sentencia = cn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
            sentencia.setString(1, numSocio);
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
    
    public void crearCliente(String nombre, String apellido1, String apellido2, String dni, String direccion, String cp, String localidad, String telefono, String email) {
    	String sentSql = "INSERT INTO cliente(nombre, apellido_1, apellido_2, dni_nif, direccion, cp, localidad, telefono, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    	PreparedStatement sentencia;
		try {
			sentencia = cn.prepareStatement(sentSql);
			
			sentencia.setString(1, nombre);
			sentencia.setString(2, apellido1);
			sentencia.setString(3, apellido2);
			sentencia.setString(4, dni);
			sentencia.setString(5, direccion);
			sentencia.setString(6, cp);
			sentencia.setString(7, localidad);
			sentencia.setString(8, telefono);
			sentencia.setString(9, email);
			sentencia.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	public void modificarCliente(int id, String nombre, String apellido1, String apellido2, String direccion, String cp, String localidad, String telefono, String email) {
    	String sentSql = "UPDATE cliente SET nombre = ?, apellido_1 = ?, apellido_2 = ?, direccion = ?, cp = ?, localidad = ?, telefono = ?, email = ? WHERE id_cliente = ?";
    	PreparedStatement sentencia;
    	try {
    		sentencia = cn.prepareStatement(sentSql);
    		sentencia.setString(1, nombre);
    		sentencia.setString(2, apellido1);
    		sentencia.setString(3, apellido2);
    		sentencia.setString(4, direccion);
    		sentencia.setString(5, cp);
    		sentencia.setString(6, localidad);
    		sentencia.setString(7, telefono);
    		sentencia.setString(8, email);
    		sentencia.setInt(9, id);
    		System.out.println(sentencia);
    		
    		sentencia.executeUpdate();
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
	};
	
	public void darBajaCliente(int id) {
		String sql = "update CLIENTE set activo = 0 WHERE id_cliente = ?";

        try (PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public ResultSet buscarClienteDni(String dni) {
		String query = "select * from cliente where dni_nif like ?";
		PreparedStatement sentencia;
		try {
            sentencia = cn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
            sentencia.setString(1,"%" + dni + "%");
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
	
	public ResultSet buscarClienteNombre(String nombre) {
		String query = "SELECT * FROM cliente WHERE LOWER(nombre || ' ' || apellido_1 || ' ' || apellido_2) LIKE LOWER(?)";
		//String query = "select * from cliente where nombre like ?";
		PreparedStatement sentencia;
		try {
            sentencia = cn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
            sentencia.setString(1, "%" + nombre + "%");
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
	
	public ResultSet buscarClienteId(int id) {
		String query = "select * from cliente where id_cliente = ?";
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
	
    // Esta funcion llama a un procedimiento en plsql que se encarga de borrar el numero de pedido insertado, para ello primero borrara todas las relaciones de ese pedido con productos, y luego borrara el pedido.
    public void borrarPedido(int pedido) {
    	CallableStatement cstmt = null;
		try {
		    cstmt = cn.prepareCall("{call eliminar_pedido(?) }");

		    cstmt.registerOutParameter(1, Types.INTEGER);

		    cstmt.setInt(1, pedido);

	        cstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (cstmt != null) cstmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	}
    
    
    // Funcion usada en la busqueda de la aplicacion, Como parametros tiene el texto de busqueda y el tipo de articulo en el que tiene que buscar.q
    public ResultSet buscar(String texto, int tipo) {
    	String query = "";
    	
    	switch (tipo) {
        case 0:
        	query = "SELECT id_producto, nombre, descripcion, precio, stock, imagen FROM ultimos_productos";
        	break;
        case 1:
        	break;
        case 2:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN portatil ON producto.id_producto = portatil.id_producto WHERE disponible = 1";
        	break;
        case 3:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN pantalla ON producto.id_producto = pantalla.id_producto WHERE disponible = 1";
        	break;
        case 4:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN cpu ON producto.id_producto = cpu.id_producto WHERE disponible = 1";
        	break;
        case 5:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN placa_base ON producto.id_producto = placa_base.id_producto WHERE disponible = 1";
        	break;
        case 6:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN ram ON producto.id_producto = ram.id_producto WHERE disponible = 1";
        	break;
        case 7:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN tarjeta_grafica ON producto.id_producto = tarjeta_grafica.id_producto WHERE disponible = 1";
        	break;
        case 8:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN teclado ON producto.id_producto = teclado.id_producto WHERE disponible = 1";
        	break;
        case 9:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN raton ON producto.id_producto = raton.id_producto WHERE disponible = 1";
        	break;
        case 10:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN fuente_alimentacion ON producto.id_producto = fuente_alimentacion.id_producto WHERE disponible = 1";
        	break;
        case 11:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN cascos ON producto.id_producto = cascos.id_producto WHERE disponible = 1";
        	break;
        case 12:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN caja ON producto.id_producto = caja.id_producto WHERE disponible = 1";
        	break;
        case 13:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN ventilador ON producto.id_producto = ventilador.id_producto WHERE disponible = 1";
        	break;
        case 14:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN disco_duro ON producto.id_producto = disco_duro.id_producto WHERE disponible = 1";
        	break;
        }
    	query = query + " AND UPPER(nombre) LIKE ?";


		PreparedStatement sentencia;
		try {
			sentencia = cn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_UPDATABLE);
			sentencia.setString(1, "%" + texto.toUpperCase() + "%");
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
    
    // Esta funcion es la mas usada en el programa, nos devuelve  todos los articulos de la base de datos de determinado tipo.
	public ResultSet getProductos(int tipo) {
        String query = "";
        switch (tipo) {
        case 0:
        	query = "SELECT id_producto, nombre, descripcion, precio, stock, imagen FROM ultimos_productos";
        	break;
        case 1:
        	break;
        case 2:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN portatil ON producto.id_producto = portatil.id_producto WHERE disponible = 1";
        	break;
        case 3:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN pantalla ON producto.id_producto = pantalla.id_producto WHERE disponible = 1";
        	break;
        case 4:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN cpu ON producto.id_producto = cpu.id_producto WHERE disponible = 1";
        	break;
        case 5:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN placa_base ON producto.id_producto = placa_base.id_producto WHERE disponible = 1";
        	break;
        case 6:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN ram ON producto.id_producto = ram.id_producto WHERE disponible = 1";
        	break;
        case 7:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN tarjeta_grafica ON producto.id_producto = tarjeta_grafica.id_producto WHERE disponible = 1";
        	break;
        case 8:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN teclado ON producto.id_producto = teclado.id_producto WHERE disponible = 1";
        	break;
        case 9:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN raton ON producto.id_producto = raton.id_producto WHERE disponible = 1";
        	break;
        case 10:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN fuente_alimentacion ON producto.id_producto = fuente_alimentacion.id_producto WHERE disponible = 1";
        	break;
        case 11:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN cascos ON producto.id_producto = cascos.id_producto WHERE disponible = 1";
        	break;
        case 12:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN caja ON producto.id_producto = caja.id_producto WHERE disponible = 1";
        	break;
        case 13:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN ventilador ON producto.id_producto = ventilador.id_producto WHERE disponible = 1";
        	break;
        case 14:
        	query = "SELECT producto.id_producto, nombre, descripcion, precio, stock, imagen FROM producto INNER JOIN disco_duro ON producto.id_producto = disco_duro.id_producto WHERE disponible = 1";
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
	
	// Esta funcion nos devuelve todos los datos, tanto de la tabla producto como de la tabla de cada componente dependiendo de que tipo de componente pongamos.
	public ResultSet getDatos(int id,int tipo) {
		String query = "SELECT nombre, descripcion, precio, stock, imagen,inalambrico,peso FROM producto INNER JOIN raton ON raton.id_producto = producto.id_producto WHERE producto.id_producto = ?";
		switch (tipo) {
		case 0:
			query = "SELECT id_producto, nombre, descripcion, precio, stock, imagen FROM ultimos_productos";
			break;
		case 1:
			break;
		case 2:
			query = "SELECT nombre, descripcion, precio, stock, imagen,ram, grafica, procesador FROM producto INNER JOIN portatil ON producto.id_producto = portatil.id_producto WHERE producto.id_producto = ?";
			break;
		case 3:
			query = "SELECT nombre, descripcion, precio, stock, imagen,dimension, puertos_video, tasa_refresco FROM producto INNER JOIN pantalla ON producto.id_producto = pantalla.id_producto WHERE producto.id_producto = ?";
			break;
		case 4:
			query = "SELECT nombre, descripcion, precio, stock, imagen,frecuencia, consumo, nucleos FROM producto INNER JOIN cpu ON producto.id_producto = cpu.id_producto WHERE producto.id_producto = ?";
			break;
		case 5:
			query = "SELECT nombre, descripcion, precio, stock, imagen, tamano, grafica FROM producto INNER JOIN placa_base ON producto.id_producto = placa_base.id_producto WHERE producto.id_producto = ?";
			break;
		case 6:
			query = "SELECT nombre, descripcion, precio, stock, imagen, frecuencia, generacion, tamano FROM producto INNER JOIN ram ON producto.id_producto = ram.id_producto WHERE producto.id_producto = ?";
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
			query = "SELECT nombre, descripcion, precio, stock, imagen, tamano FROM producto INNER JOIN ventilador ON producto.id_producto = ventilador.id_producto WHERE producto.id_producto = ?";
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
	
	// Esta funcion nos devuelve todos los articulos que estan en un pedido para poder cargar el pedido.
	public ResultSet getPedido(int id_pedido) {
		String query = "select p.id_producto, p.nombre, p.descripcion, p.precio, p.imagen, p.stock, pp.cantidad from producto_pedido pp inner join producto p on p.id_producto = pp.id_producto where id_pedido_cliente = ?";
		PreparedStatement sentencia;
		try {
            sentencia = cn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
            sentencia.setInt(1, id_pedido);
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
	
	// Esta funcion llama a una funcion gestionar_pedido que se encarga de comprobar si el pedido esta creado ya o no y realizara o un insert, o updates a los articulos que ya tenia dependiendo de lo que necesite
	// El procedimiento devuelve la id del pedido ya sea si se hna creado como si ya existia.
	public int insertarPedido(int id_cliente, int[] id_productos, int[] cantidades, int numPedido) {
		CallableStatement cstmt = null;
		try {
		    cstmt = cn.prepareCall("{ ? = call gestionar_pedido(?, ?, ?, ?) }");

		    OracleConnection oracleConnection = cn.unwrap(OracleConnection.class);
		    cstmt.registerOutParameter(1, Types.INTEGER);

		    cstmt.setInt(2, id_cliente);
		    cstmt.setArray(3, oracleConnection.createOracleArray("NUMBERARRAY", id_productos));
		    cstmt.setArray(4, oracleConnection.createOracleArray("NUMBERARRAY", cantidades));
		    cstmt.setInt(5, numPedido);

		    cstmt.execute();

		    return cstmt.getInt(1);
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
		    if (cstmt != null) {
		        try {
		            cstmt.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		}
		return 0;

	}
	
	// Un insert muy sencillito, crea una venta a partir de un pedido ya existente.
	public void insertFactura(String empleado, int pedido) {
		PreparedStatement pstmt = null;

	    try {
	        // Suponiendo que tienes una conexión a tu base de datos llamada "conn"
	        String sql = "INSERT INTO venta(fecha, id_empleado, id_pedido_cliente) VALUES (sysdate, (SELECT id_empleado FROM empleado WHERE dni = ?), ?)";

	        pstmt = cn.prepareStatement(sql);
	        pstmt.setString(1, empleado);
	        pstmt.setInt(2, pedido);

	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	}

	// funcion para loguear, nuestros empleados tendran que usar su dni y contraseña
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
    
    public void cerrarConexion() {
    	try {
			cn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

