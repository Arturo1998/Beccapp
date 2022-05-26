package becapp.menus.metodos;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagenFondo extends JPanel{
	
	private String miembro;

	public ImagenFondo(String cadena) {
		
		miembro=cadena;
		
	}
		
	@Override
	public void paint(Graphics g) {
		
		ImageIcon imagen = new ImageIcon(getClass().getResource(miembro));
		g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
		setOpaque(false);
		super.paint(g);
	}
	
	
}
