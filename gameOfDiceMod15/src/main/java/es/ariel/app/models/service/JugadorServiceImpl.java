package es.ariel.app.models.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;

import es.ariel.app.models.dao.IJugadorDao;
import es.ariel.app.models.documents.Jugador;


@Service
public class JugadorServiceImpl implements IJugadorService {
	
	@Autowired
	private IJugadorDao dao;
	
	@Autowired
	private MongoTemplate mongoTemplate ;

	@Override
	public List<Jugador> findAll() {
		
		return  dao.findAll();	
	}		

	@Override
	public Jugador findOne(String id) {
		
		return dao.findById(id).orElse(null)	;
	}	

	@Override
	public void save(Jugador jugador) {				
	
		dao.save(jugador);
	}

	@Override
	public Jugador findByNombre(String nombre) {
		
		return dao.findByNombre(nombre);
	}

	@Override
	public Jugador findByUsername(String username) {
		
		return dao.findByUsername(username);
	}

	@Override
	public void remove(Jugador jugador) {
		dao.delete(jugador);
		
	}

	@Override
	public void deleteGames(String id) {
	Integer [] val= {1,2,3,4,5,6,7,8,9,10,11,12};
	Query query = new Query(Criteria.where("id").is(id));
	
	Update u = new Update().pullAll("listaTiradasRojas",val );
	 UpdateResult wr = mongoTemplate.updateMulti(query, u, Jugador.class); 	
	 
	  u = new Update().pullAll("listaTiradasVerdes",val );
	  wr = mongoTemplate.updateMulti(query, u, Jugador.class); 	
	  
	  u = new Update().pullAll("listaTiradasAzules",val );
	  wr = mongoTemplate.updateMulti(query, u, Jugador.class); 	 	 

	}	

}
