package becapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Conexion_BBDD {

	private String bd = "XE";
	private String login = "BANCO";
	private String password = "BANCO";
	private String url = "jdbc:oracle:thin:@localhost:1521:" + bd;
	java.sql.ResultSet rs = null;
	Connection connection = null;

	/**
	 * Método de conexión a la Base de Datos
	 */
	public void conectar() {

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(url, login, password);
			if (connection != null) {
				System.out.println("Conexion realizada correctamente");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método de desconexión de Base de Datos
	 * 
	 * @throws SQLException
	 */
	public void cerrar() throws SQLException {

		if (rs != null)
			rs.close();
		if (connection != null)
			connection.close();

	}

	/**
	 * @author Eduardo
	 */

	/**
	 * En este método recibimos los datos correspondientes a la beca que queremos
	 * dar de alta en un objeto beca, a excepción del número de beca, que
	 * conseguimos en la primera parte del método de la tabla beca.cod y le sumamos
	 * +1 para que siga el orden correlativo. En la segunda parte del método hacemos
	 * un insert con todos los datos recopilados con el método consulta preparada.
	 * 
	 * @param nombre          datos beca
	 * @param condiciones     datos beca
	 * @param descripción     datos beca
	 * @param contacto        datos beca
	 * @param nombreProveedor datos beca
	 * @param tipo_beca       datos beca
	 * @return true en caso de éxito, false en caso contrario
	 */
	public boolean aniadirBeca(Beca b) {

		boolean alta = false;
		// convertimos el enum a String
		String tBeca = b.getTipo_beca().toString();
		int cod = 0;

		try {
			PreparedStatement ps = connection.prepareStatement("select max(codigo) from becas");
			rs = ps.executeQuery();

			while (rs.next()) {
				cod = rs.getInt(1) + 1;
			}

			ps = connection.prepareStatement("insert into becas values(?,?,?,?,?,?,?)");
			ps.setInt(1, cod);
			ps.setString(2, b.getNombreProveedor());
			ps.setString(3, b.getContacto());
			ps.setString(4, b.getDescripcion());
			ps.setString(5, b.getCondiciones());
			ps.setString(6, b.getNombre());
			ps.setString(7, tBeca);
			ps.executeUpdate();

			alta = true;

		} catch (SQLException e) {
			System.out.println("No se encuentran los datos");
			e.printStackTrace();
			alta = false;
			return alta;
		}

		return alta;
	}

	/**
	 * @author edu
	 * 
	 *         Método destinado a borrar becas. Empieza comprobando si el dato está
	 *         en blanco o vacío en cuyo caso lanza una excepción preparada con un
	 *         mensaje de diálogo. A continuación utiliza el método buscar datos
	 *         para sacar el filtro de la consulta. Finalmente añadimos toda la
	 *         información recogida a una variable String
	 * 
	 * @param dato         la información de referencia para el borrado
	 * @param condicion    numeración interna que nos da la culumna de filtro
	 * @param borradoTabla utilizaremos false para este método ya que no queremos
	 *                     ejecutar la consulta que está dentro del método
	 * @return true si es correcto, false en caso contrario
	 */
	public boolean borrarBeca(String dato, int condicion, boolean borradoTabla) {

		boolean borrado = false;

		PreparedStatement ps;

		try {

			if (borradoTabla) {
				if (dato.isBlank() || dato.isEmpty()) {

					throw new Exception();
				}
			}

			String filtro = buscarDatos(dato, condicion, "becas", false);

			ps = connection.prepareStatement("delete from becas where " + filtro + " like upper('%" + dato + "%')");
			ps.executeQuery();

			borrado = true;

		} catch (SQLException e) {
			e.printStackTrace();
			borrado = false;
			return borrado;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Atención: seleccione un campo");
		}

		return borrado;
	}

	/**
	 * @author edu
	 * 
	 *         Con este método podremos actualizar determinados datos de las
	 *         distintas becas de nuestra BBDD
	 * 
	 * @param columna       campo del que queremos hacer la actualización
	 * @param cod           código del cliente al que se le hace el cambio
	 * @param actualizacion dato que se cambia en la columna
	 * @return true en caso de éxito, false en caso contrario
	 */
	public boolean modificarBeca(String columna, int cod, String actualizacion) {

		boolean modificado = false;

		PreparedStatement ps;

		try {

			ps = connection.prepareStatement(
					"update becas set " + columna + " = '" + actualizacion + "' where codigo= " + cod);
			int up = ps.executeUpdate();

			if (up == 2) {
				modificado = false;
			} else {
				modificado = true;
			}

		} catch (SQLException e) {
			System.out.println("No se ha podido realizar la actualizaciónn de la beca");
			e.printStackTrace();
			modificado = false;
			return modificado;
		}

		return modificado;
	}
	/*
	 * pendiente de borrar
	 * 
	 * /** Este método nos saca de la base de datos toda la información relativa a
	 * nuestras becas
	 * 
	 * @return devuelve un string con la informacion solicita o un mensaje de error
	 * en caso de fallo
	 *//*
		 * 
		 * public String listarBecas() {
		 * 
		 * PreparedStatement ps; String lista = "";
		 * 
		 * try { ps = connection.prepareStatement("select * from becas order by 1"); rs
		 * = ps.executeQuery(); while (rs.next()) {
		 * 
		 * lista += "Codigo beca= " + rs.getInt(1) + " nombre beca = " + rs.getString(2)
		 * + " condiciones= " + rs.getString(3) + " descripcion= " + rs.getString(4) +
		 * " contacto= " + rs.getString(5) + " nombre proveedor= " + rs.getString(6) +
		 * " tipo de beca= " + rs.getString(7) + "\n";
		 * 
		 * }
		 * 
		 * } catch (SQLException e) {
		 * 
		 * return "La lista no se ha podido cargar"; }
		 * 
		 * return lista; }
		 */

	/**
	 * @author edu
	 * 
	 *         Método con el cual rescatamos la información necesaria para crear y
	 *         rellenar un arraylist de becas
	 * 
	 * @return arraylist de becas
	 */

	public ArrayList<Beca> listarBecasArray() {

		ArrayList<Beca> datos = new ArrayList<Beca>();

		PreparedStatement ps;

		try {
			ps = connection.prepareStatement("select * from becas order by 1");
			rs = ps.executeQuery();

			while (rs.next()) {
				String tipo = rs.getString(7);
				tipo_beca tp = null;

				if (tipo.equals("PRIVADA")) {
					tp = tp.PRIVADA;
				} else {
					tp = tp.PUBLICA;

				}

				Beca b = new Beca(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), tp);
				datos.add(b);
			}

		} catch (SQLException e) {

			System.out.println("La lista no se ha podido cargar");
		}

		return datos;
	}

	/**
	 * @author edu
	 * 
	 *         Método con el cual rescatamos la información necesaria para crear y
	 *         rellenar un arraylist de administradores
	 * 
	 * @return arraylist de administradores
	 */
	public ArrayList<Administrador> listarAdminitradoresArray() {

		ArrayList<Administrador> datos = new ArrayList<Administrador>();

		PreparedStatement ps;

		try {
			ps = connection.prepareStatement("select * from administradores order by 1");
			rs = ps.executeQuery();

			while (rs.next()) {
				String fecha_cumple = rs.getDate(8).toString();
				String fecha_ini = rs.getDate(12).toString();

				Administrador a = new Administrador(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getInt(7), fecha_cumple, rs.getString(9), rs.getString(10),
						rs.getString(11), fecha_ini);
				datos.add(a);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("La lista no se ha podido cargar");
		}

		return datos;
	}

	/**
	 * @author edu
	 * 
	 *         Método diseñado para obtener información de la base de datos. Tiene
	 *         varias opciones, una de ellas es según la condición nos dará el
	 *         filtro del where y hará la búsqueda que devolverá en un Stream y otra
	 *         opción es simplemente que nos devuelva el filtro
	 * 
	 * @param dato      información de igualado del where
	 * @param condicion numeración interna del filtro
	 * @param tabla     en la que queremos hacer la búsqueda (becas y
	 *                  administradores)
	 * @param buscar    true o false según si queremos o no hacer uso de la consulta
	 * @return String con la información recogida
	 */

	public String buscarDatos(String dato, int condicion, String tabla, boolean buscar) {

		String lista = "";
		String filtro = "";

		PreparedStatement ps;

		try {

			switch (condicion) {
			case 1:

				filtro = "codigo";

				if (buscar) {
					ps = connection.prepareStatement("select * from becas where " + filtro + " = " + dato);
					// pendiente de evaluar ps.setString(1, filtro);
					// ps.setString(2, dato);
					rs = ps.executeQuery();
				}

				break;

			case 2:
				filtro = "nombre_proveedor";

				if (buscar) {

					ps = connection
							.prepareStatement("select * from becas where " + filtro + " like upper('%" + dato + "%')");
					rs = ps.executeQuery();
				}
				break;
			case 3:

				filtro = "id_usuario";

				if (buscar) {
					ps = connection.prepareStatement(
							"select * from administradores " + tabla + " where " + filtro + " = " + dato);
					// pendiente de evaluar ps.setString(1, filtro);
					// ps.setString(2, dato);
					rs = ps.executeQuery();

				}
				break;
			case 4:
				filtro = "dni";
				if (buscar) {
					ps = connection.prepareStatement(
							"select * from administradores where " + filtro + " like upper('%" + dato + "%')");
					rs = ps.executeQuery();

				}

				break;

			default:
				break;

			}

			if (buscar && tabla.equals("becas")) {
				while (rs.next()) {

					lista += "Beca [codigo = " + rs.getInt(1) + " Nombre Proveedor = " + rs.getString(2) + "]" + "\n";

				}

			} else if (buscar && tabla.equals("administradores")) {
				while (rs.next()) {

					lista += "Administrador [" + rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " "
							+ rs.getString(4) + "]" + "\n";
				}

			}

			else {
				lista = filtro;
			}

		} catch (Exception e) {

		}
		return lista;

	}

	/**
	 * @author edu
	 * 
	 * Método para comprobar la información que se quiere actualizar antes de realizar la actualización 
	 *
	 * @param cod número de código para la búsqueda de información
	 * @param columna dato del que queremos saber qué contiene
	 * @return String con la información recogida
	 * @throws SQLException
	 */
	public String informacionActualizacion(int cod, String columna) throws SQLException {

		PreparedStatement ps;
		String lista = "";

		ps = connection.prepareStatement("select * from becas where codigo=" + cod);
		rs = ps.executeQuery();

		while (rs.next()) {

			switch (columna) {
			case "nombre":
				lista += "Codigo beca: " + rs.getInt(1) + " Beca: " + rs.getString(6);
				break;
			case "condiciones":
				lista += "Codigo beca: " + rs.getInt(1) + " Condiciones: " + rs.getString(5);
				break;
			case "descripcion":
				lista += "Codigo beca: " + rs.getInt(1) + " Descripcion: " + rs.getString(4);
				break;
			case "contacto":
				lista += "Codigo beca: " + rs.getInt(1) + " Contacto: " + rs.getString(3);
				break;
			case "nombre_proveedor":
				lista += "Codigo beca: " + rs.getInt(1) + " Proveedor: " + rs.getString(2);
				break;
			case "tipo":
				lista += "Codigo beca: " + rs.getInt(1) + " Tipo: " + rs.getString(7);
				break;
			default:
				break;
			}

		}

		return lista;
	}

	/**
	 * @author edu
	 * 
	 * Con este método daremos de alta administradores en la tabla de
	 * administradores, a partir de un objeto administrador que hereda de usuario
	 * 
	 * @param a Objeto de la clase administrador con todos los datos
	 * @return true en caso de exito, false en caso contrario
	 */

	public boolean darAltaAdmin(Administrador a) {

		boolean alta = false;
		int cod = 0;

		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("select max(id_usuario)from administradores");
			rs = ps.executeQuery();

			while (rs.next()) {

				cod = rs.getInt(1) + 1;
			}

			ps = connection.prepareStatement("insert into administradores values(?,?,?,?,?,?,?,?,?,?,?,sysdate)");
			ps.setInt(1, cod);
			ps.setString(2, a.getDni());
			ps.setString(3, a.getNombre());
			ps.setString(4, a.getApellido());
			ps.setString(5, a.getNacionalidad());
			ps.setString(6, a.getEmail());
			ps.setInt(7, a.getTelf());
			ps.setString(8, a.getFecha_nac());
			ps.setString(9, a.getClave());
			ps.setString(10, a.getEstado());
			ps.setString(11, a.getDescripcion_puesto());
			ps.executeUpdate();

			alta = true;

		} catch (SQLException e) {
			System.out.println("No se han insertado datos");
			e.printStackTrace();
			alta = false;
			return alta;
		}

		return alta;

	}

	
/**
 * @author edu
 * 
 * Método para dar de baja a un administrador. Recurriremos al método buscar datos para obtener el filtro del borrado en caso de excepción lanzará una ventana de diálogo
 * 
 * @param dato criterio para dar de baja al administrador
 * @param condicion número interno para obtener el where
 * @return true en caso de éxito, false en caso contrario
 */

	public boolean darBajaAdmin(String dato, int condicion) {

		boolean borrado = false;
		String filtro = "";

		PreparedStatement ps;

		try {
			filtro = buscarDatos(dato, condicion, "administradores", false);
			ps = connection
					.prepareStatement("delete from administradores where " + filtro + " like upper('%" + dato + "%')");
			ps.executeQuery();

			borrado = true;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Atención: Introduzca algún criterio");

			borrado = false;
			return borrado;
		}

		return borrado;
	}

	/**
	 * En proceso de eliminacion!!! Metodo que devuelve una lista completa de todo
	 * los administradores que tenemos en la tabla administradores
	 * 
	 * @return variable String con toda la informacion
	 */
	/*
	 * public String mostrarAdmin() {
	 * 
	 * PreparedStatement ps; String lista = "";
	 * 
	 * try { ps = connection.prepareStatement("select * from administradores"); rs =
	 * ps.executeQuery();
	 * 
	 * while (rs.next()) {
	 * 
	 * lista += "Codigo administrador " + rs.getInt(1) + " dni = " + rs.getString(2)
	 * + " nombre= " + rs.getString(3) + " apellido= " + rs.getString(4) +
	 * " nacionalidad= " + rs.getString(5) + " email= " + rs.getString(6) +
	 * " telfono= " + rs.getInt(7) + " fecha nacimiento= " + rs.getDate(8) +
	 * " clave= " + rs.getString(9) + " estado= " + rs.getString(10) +
	 * " Descripcion puesto= " + rs.getString(11) + " fecha inicio= " +
	 * rs.getDate(12) + "\n";
	 * 
	 * }
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); return
	 * "La lista no se ha podido cargar"; }
	 * 
	 * return lista; }
	 */

}