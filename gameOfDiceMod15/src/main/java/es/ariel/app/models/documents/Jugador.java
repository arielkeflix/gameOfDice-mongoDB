package es.ariel.app.models.documents;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import es.ariel.app.models.service.IJugadorService;

@Document(collection = "jugadores")

public class Jugador {

	@Id
	private String id;

	private String nombre;
	private Date fechaIngreso;
	private String username;
	private String password;
	private Boolean enable;

	private List<String> roles;

	private List<Integer> listaTiradasRojas= new ArrayList<Integer>();
	private List<Integer> listaTiradasVerdes= new ArrayList<Integer>();
	private List<Integer> listaTiradasAzules= new ArrayList<Integer>();

	public Jugador() {
	}
	

	public Jugador( String nombre, String username, String password, int i, List<String> roles) {
		this.nombre = nombre;
		fechaIngreso = new Date();		
		this.username = username;
		this.password = password;
		this.enable = true;
		this.roles = roles;	
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void insertThrowDice( String colorDados, Integer valor) {		
		
		switch (colorDados) {

			case "red":
				listaTiradasRojas.add( valor);
			
				break;
			case "blue":
				listaTiradasAzules.add(valor);
			
				break;
			case "green":
				listaTiradasVerdes.add(valor);
			
				break;

		default:
			break;
		}
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public List<Integer> getListaTiradasRojas() {
		return listaTiradasRojas;
	}

	public void setListaTiradasRojas(List<Integer> listaTiradasRojas) {
		this.listaTiradasRojas = listaTiradasRojas;
	}

	public List<Integer> getListaTiradasVerdes() {
		return listaTiradasVerdes;
	}

	public void setListaTiradasVerdes(List<Integer> listaTiradasVerdes) {
		this.listaTiradasVerdes = listaTiradasVerdes;
	}

	public List<Integer> getListaTiradasAzules() {
		return listaTiradasAzules;
	}

	public void setListaTiradasAzules(List<Integer> listaTiradasAzules) {
		this.listaTiradasAzules = listaTiradasAzules;
	}

	public Integer getPromedioRoja() {

		double aux = 0;
		double cont = 0;
		Integer promedio = 0;

		for (Integer t : listaTiradasRojas) {
			cont++;
			if (t == 7)
				aux++;
		}
		if (aux > 0)
			promedio = (int) ((aux / cont) * 100);

		return promedio;
	}

	public Integer getPromedioVerde() {
		double aux = 0;
		double cont = 0;
		Integer promedio = 0;

		for (Integer t : listaTiradasVerdes) {
			cont++;
			if (t == 7)
				aux++;
		}
		if (aux > 0)
			promedio = (int) ((aux / cont) * 100);

		return promedio;
	}	

	public Integer getPromedioAzul() {
		double aux = 0;
		double cont = 0;
		Integer promedio = 0;

		for (Integer t : listaTiradasAzules) {
			cont++;
			if (t == 7)
				aux++;
		}
		if (aux > 0)
			promedio = (int) ((aux / cont) * 100);

		return promedio;
	}
	

	@Override
	public String toString() {
		return "Jugador [id=" + id + ", nombre=" + nombre + ", fechaIngreso=" + fechaIngreso + ", username=" + username
				+ ", password=" + password + ", enable=" + enable + ", roles=" + roles;
	}

}
