package becapp.menus.gestionBecas;

import java.awt.Color;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import becapp.tipo_beca;
import becapp.menus.Ficheros.Log;
import becapp.menus.Ficheros.Tipo_movimiento;
import becapp.menus.metodos.ImagenFondo;
import becapp.menus.metodos.Listado;
import becapp.menus.metodos.MetodosMenus;
import junit.extensions.TestSetup;
import becapp.Beca;
import becapp.Conexion_BBDD;

/**
 * 
 * @author edu
 *
 */

public class FormularioBeca extends JFrame {

	private JTextField nombre;
	private JTextField condiciones;
	private JTextField descripcion;
	private JTextField nombreProveedor;
	private JTextField contacto;
	private JRadioButton privada;
	private JRadioButton publica;
	private Beca beca;
	private Conexion_BBDD conexion;

	public FormularioBeca() {

		setTitle("FORMULARIO BECA");
		ImagenFondo fondo = new ImagenFondo("/imagenes/tabla.jpg");
		setContentPane(fondo);
		setBounds(500, 300, 800, 500);
		setResizable(false);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		JTextPane nombreC = new JTextPane();
		nombreC.setText("Nombre");
		nombreC.setBounds(100, 20, 114, 19);
		getContentPane().add(nombreC);
		nombreC.setEditable(false);
		nombreC.setOpaque(false);

		nombre = new JTextField();
		nombre.setBounds(100, 50, 150, 19);
		getContentPane().add(nombre);
		nombre.setColumns(10);
		nombre.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.ORANGE));

		JTextPane condicionesC = new JTextPane();
		condicionesC.setText("Condiciones");
		condicionesC.setBounds(100, 80, 114, 19);
		getContentPane().add(condicionesC);
		condicionesC.setEditable(false);
		condicionesC.setOpaque(false);

		condiciones = new JTextField();
		condiciones.setBounds(100, 110, 250, 19);
		getContentPane().add(condiciones);
		condiciones.setColumns(10);
		condiciones.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.ORANGE));

		JTextPane descripcionC = new JTextPane();
		descripcionC.setText("Descripcion");
		descripcionC.setBounds(100, 140, 114, 19);
		getContentPane().add(descripcionC);
		descripcionC.setEditable(false);
		descripcionC.setOpaque(false);

		descripcion = new JTextField();
		descripcion.setBounds(100, 170, 250, 19);
		getContentPane().add(descripcion);
		descripcion.setColumns(10);
		descripcion.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.ORANGE));

		JTextPane nombreProveedorC = new JTextPane();
		nombreProveedorC.setText("Nombre proveedor");
		nombreProveedorC.setBounds(450, 20, 250, 19);
		getContentPane().add(nombreProveedorC);
		nombreProveedorC.setEditable(false);
		nombreProveedorC.setOpaque(false);

		nombreProveedor = new JTextField();
		nombreProveedor.setBounds(450, 50, 250, 19);
		getContentPane().add(nombreProveedor);
		nombreProveedor.setColumns(10);
		nombreProveedor.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.ORANGE));

		JTextPane contactoC = new JTextPane();
		contactoC.setText("Contacto");
		contactoC.setBounds(450, 80, 114, 19);
		getContentPane().add(contactoC);
		contactoC.setEditable(false);
		contactoC.setOpaque(false);

		contacto = new JTextField();
		contacto.setBounds(450, 110, 250, 19);
		getContentPane().add(contacto);
		contacto.setColumns(10);
		contacto.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.ORANGE));

		JTextPane tipoBecaC = new JTextPane();
		tipoBecaC.setText("Tipo de beca");
		tipoBecaC.setBounds(450, 140, 114, 19);
		getContentPane().add(tipoBecaC);
		tipoBecaC.setEditable(false);
		tipoBecaC.setOpaque(false);

		privada = new JRadioButton("Privada");
		privada.setBounds(450, 170, 149, 23);
		getContentPane().add(privada);
		privada.setOpaque(false);

		publica = new JRadioButton("Publica");
		publica.setBounds(600, 170, 149, 23);
		getContentPane().add(publica);
		publica.setOpaque(false);

		ButtonGroup grupo1 = new ButtonGroup();
		grupo1.add(privada);
		grupo1.add(publica);

		JTextPane informacionC = new JTextPane();
		informacionC.setText("Ultima beca añadida");
		informacionC.setBounds(100, 240, 250, 30);
		getContentPane().add(informacionC);
		informacionC.setEditable(false);
		informacionC.setOpaque(false);

		JTextArea informacion = new JTextArea();
		informacion.setBounds(new Rectangle(100, 270, 600, 30));
		informacion.setEditable(false);
		informacion.setVisible(true);
		informacion.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.ORANGE));
		getContentPane().add(informacion);
		informacion.setEditable(false);

		JButton BBDD = new JButton("BBDD");
		BBDD.setBounds(350, 400, 100, 30);
		getContentPane().add(BBDD);
		BBDD.setBackground(Color.ORANGE);

		JButton limpiar = new JButton("LIMPIAR");
		limpiar.setBounds(100, 400, 100, 30);
		getContentPane().add(limpiar);
		limpiar.setBackground(Color.ORANGE);

		JButton aceptar = new JButton("ACEPTAR");
		aceptar.setBounds(600, 400, 100, 30);
		getContentPane().add(aceptar);
		aceptar.setBackground(Color.ORANGE);

		/**
		 * Accion del boton limpiar para resetear la entrada de datos
		 */

		limpiar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// reseteo de los campos
				nombre.setText("");
				condiciones.setText("");
				descripcion.setText("");
				contacto.setText("");
				nombreProveedor.setText("");
				grupo1.clearSelection();

			}

		});

		/**
		 * Accion del boton de consulta a la base de datos, genera una tabla con la
		 * informacion de la tabla
		 */

		BBDD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// columnas de la tabla
				String[] columnas = { "Codigo", "Proveedor", "Contacto", "Descripcion", "Nombre", "Condiciones",
						"Tipo de Beca" };
				// constructor que genera la tabla
				Listado listado = new Listado(false, columnas, "beca");

				listado.setTitle("Datos Becas");
				listado.pack();
				listado.setVisible(true);
			}

		});

		/**
		 * Accion del boton aceptar en cual validamos la entrada de los datos
		 * introducidos en lo correspondientes campos
		 */

		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				tipo_beca tipo = null;

				conexion = new Conexion_BBDD();
				//con esta variable cambiamos el mensaje de salida para las excepciones;
				String mensaje="";

				
				try {
					// comprobramos que no hay campos en blanco
					if (nombre.getText().isBlank() || condiciones.getText().isBlank() || descripcion.getText().isBlank()
							|| contacto.getText().isBlank() || nombreProveedor.getText().isBlank()) {
						// mostramos mensaje y cortamos la ejecucion del codigo
						mensaje="Atencion: Algun campo en blanco";
						throw new Exception();
					}
					// comprobamos que tipo esta seleccionado
					if (privada.isSelected() || publica.isSelected()) {
						if (privada.isSelected()) {
							tipo = tipo_beca.PRIVADA;
						} else {
							tipo = tipo_beca.PUBLICA;

						}

					} else {
						// en caso contrario levanto una expcecion y controlo la ejecucion del metodo
						mensaje= "Atencion: obligatorio elegir beca publica o privada";
						throw new Exception();

					}
					// completamos el objeto beca con los datos recogidos
					beca = new Beca(nombre.getText().toUpperCase(), condiciones.getText().toUpperCase(),
							descripcion.getText().toUpperCase(), contacto.getText().toUpperCase(),
							nombreProveedor.getText().toUpperCase(), tipo);

					conexion.conectar();
					informacion.setText(beca.toString());
					//
					if (conexion.aniadirBeca(beca)) {

						JOptionPane.showMessageDialog(null, "Beca añadida con exito");
						informacion.setText(beca.toString());
						// Registramos el tipo de moviemiento en nuestro log
						GregorianCalendar gc = new GregorianCalendar();
						Date fecha_hora = gc.getTime();

						try {

							Log metodos = new Log();
							// metodo de escritura en el fichero log
							metodos.escribirLog(Tipo_movimiento.INTRODUCIR_BECA, fecha_hora);
						} catch (IOException elog) {
							elog.getStackTrace();
						}

					} else {
						mensaje="Atencion: problemas a la hora de cargar la beca";
						throw new Exception();
					}

					nombre.setText("");
					condiciones.setText("");
					descripcion.setText("");
					contacto.setText("");
					nombreProveedor.setText("");

					grupo1.clearSelection();

					try {
						conexion.cerrar();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, mensaje);
					
				}

			}
		});

	}

}
