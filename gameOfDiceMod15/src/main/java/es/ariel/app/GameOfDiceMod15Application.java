package es.ariel.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

import es.ariel.app.models.dao.IJugadorDao;
import es.ariel.app.models.documents.Jugador;



@SpringBootApplication
public class GameOfDiceMod15Application implements CommandLineRunner{
	
	
	
	@Autowired
	private MongoTemplate mongoTemplate ;

	public static void main(String[] args) {
		SpringApplication.run(GameOfDiceMod15Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		mongoTemplate.dropCollection("jugadores");//   .dropCollection("productos").subscribe();
		
		List<String> roles1= Arrays.asList("ROLE_USER");
		List<String> roles2= Arrays.asList("ROLE_USER","ROLE_ADMIN");
		
		Jugador a =new Jugador("Ariel","ariel", "$2a$10$6aEib01haLaDUxWn08GKa.hQ4Lzar8X2cLBsN5MQRBzgqZsUKubSy", 1, roles1);
		Jugador b =new Jugador("Jorge","Jorge", "$2a$10$6aEib01haLaDUxWn08GKa.hQ4Lzar8X2cLBsN5MQRBzgqZsUKubSy", 1, roles1);
		Jugador c =new Jugador("Maria","admin", "$2a$10$6aEib01haLaDUxWn08GKa.hQ4Lzar8X2cLBsN5MQRBzgqZsUKubSy", 1, roles2);
		
		a.insertThrowDice("red", 6);
		a.insertThrowDice("red", 7);
		a.insertThrowDice("red", 8);
		a.insertThrowDice("red", 7);
		
		a.insertThrowDice("green", 6);
		a.insertThrowDice("green", 7);
		a.insertThrowDice("green", 8);
		
		b.insertThrowDice("red", 6);
		b.insertThrowDice("red", 7);
		b.insertThrowDice("red", 8);
		b.insertThrowDice("blue", 5);
		
		c.insertThrowDice("blue", 7);
		
		
		List<Jugador> jugadores = Arrays.asList( a,b,c);					
		
		
		mongoTemplate.insertAll(jugadores);
		
		
		
		

		

	}

}


//	Usuario usuario1 = new Usuario("Ariel", "$2a$10$6aEib01haLaDUxWn08GKa.hQ4Lzar8X2cLBsN5MQRBzgqZsUKubSy", 1,roles1);
//Usuario usuario2 = new Usuario("admin", "$2a$10$6aEib01haLaDUxWn08GKa.hQ4Lzar8X2cLBsN5MQRBzgqZsUKubSy", 1,roles2);
//
//List<Usuario> Usuarios = Arrays.asList(usuario1,usuario2);					
//
//
//mongoTemplate.insertAll(Usuarios);
