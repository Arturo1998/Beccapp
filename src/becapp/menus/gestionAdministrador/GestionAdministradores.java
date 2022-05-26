package becapp.menus.gestionAdministrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import becapp.menus.gestionBecas.BorradoBecas;
import becapp.menus.gestionBecas.FormularioBeca;
import becapp.menus.gestionBecas.ListadoBecas;
import becapp.menus.gestionBecas.ModificarBeca;
import becapp.menus.metodos.ImagenFondo;
import becapp.menus.metodos.MetodosMenus;

public class GestionAdministradores extends JFrame {

	public GestionAdministradores() {

		setTitle("AÑADIR ADMINISTRADOR");
		ImagenFondo fondo = new ImagenFondo("/imagenes/tabla");
		setContentPane(fondo);
		setBounds(600, 300, 600, 450);
		getContentPane().setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				MetodosMenus mm = new MetodosMenus();
				mm.confirmarSalida();
			}
		});
		
		JButton aniadirAdministrador = new JButton("Añadir Administrador");
		aniadirAdministrador.setBounds(100, 50, 400, 30);
		getContentPane().add(aniadirAdministrador);
		
		aniadirAdministrador.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormularioAdministrador fa=new FormularioAdministrador();
				
				fa.setVisible(true);
				dispose();
			}
		});
		
		JButton borrarAdminitrador = new JButton("Borrar Administrador");
		borrarAdminitrador.setBounds(100, 125, 400, 30);
		getContentPane().add(borrarAdminitrador);
		borrarAdminitrador.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BorradoAdministrador ba = new BorradoAdministrador();
				ba.setVisible(true);
				dispose();

			}
		});
		
		JButton mostrarAdministrador = new JButton("Mostrar Administradores");
		mostrarAdministrador.setBounds(100, 200, 400, 30);
		getContentPane().add(mostrarAdministrador);
		mostrarAdministrador.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				ListadoAdministrador la=new ListadoAdministrador(false);
				la.setVisible(true);
				la.setTitle("Datos administradores");
				la.pack();
				

			}
		});
		
		JButton atras = new JButton("ATRAS");
		atras.setBounds(100, 350, 100, 30);
		getContentPane().add(atras);

		atras.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();
				MenuGestion aplicacion = new MenuGestion();
				aplicacion.setVisible(true);

			}
		});
		

	}

}
