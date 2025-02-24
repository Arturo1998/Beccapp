package becapp;


public class Administrador extends Usuario {

	private String clave;

	private String estado;

	private String descripcion_puesto;

	private String fecha_inc;
	/**
	 * Contructor sin id_usuario para luego generarlo de manera correlativa 
	 * 
	 * @param dni
	 * @param nombre
	 * @param apellido
	 * @param nacionalidad
	 * @param email
	 * @param telf
	 * @param fecha_nac
	 * @param clave
	 * @param estado
	 * @param descripcion_puesto
	 */
	public Administrador(String dni, String nombre, String apellido, String nacionalidad, String email, int telf,
			String fecha_nac, String clave, String estado, String descripcion_puesto) {
		super(dni, nombre, apellido, nacionalidad, email, telf, fecha_nac);
		this.clave = clave;
		this.estado = estado;
		this.descripcion_puesto = descripcion_puesto;
	}
	public Administrador(int id_usuario,String dni, String nombre, String apellido, String nacionalidad, String email, int telf,
			String fecha_nac, String clave, String estado, String descripcion_puesto,String fecha_inc) {
		super(id_usuario,dni, nombre, apellido, nacionalidad, email, telf, fecha_nac);
		this.clave = clave;
		this.estado = estado;
		this.descripcion_puesto = descripcion_puesto;
		this.fecha_inc=fecha_inc;
	}
	

	public String getClave() {
		return clave;
	}

	public String getEstado() {
		return estado;
	}

	public String getDescripcion_puesto() {
		return descripcion_puesto;
	}

	public String getFecha_inc() {
		return fecha_inc;
	}
	@Override
	public String toString() {
		return "Administrador ["+ super.getNombre() + " " + super.getApellido() + " DNI: " + super.getDni() + "]";
	}
	

}
