package es.ariel.app.models.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.ariel.app.models.documents.Jugador;


public interface IJugadorDao extends MongoRepository<Jugador, String> {

	public Jugador findByNombre(String nombre);
	public Jugador findByUsername(String username);	

}
