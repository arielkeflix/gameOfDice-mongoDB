package es.ariel.app.models.service;

import java.util.List;

import es.ariel.app.models.documents.Jugador;


public interface IJugadorService {
	
	public List<Jugador> findAll();		
	
	public Jugador findOne(String id);		
	
	public void remove(Jugador jugador);		
	
	public  void save(Jugador jugador);		
	
	public Jugador findByNombre(String nombre);
	
	public Jugador findByUsername(String username);
	
	public void deleteGames(String id);		

}
