package es.ariel.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ariel.app.models.documents.Jugador;
import es.ariel.app.models.service.IJugadorService;

@RestController
@RequestMapping("/api")
public class JugadorRestController {
	
	@Autowired
	private IJugadorService jugadorService ;
	
	@GetMapping("/listar")
	public List<Jugador> listar(){
		List<Jugador>jugadores = jugadorService.findAll();
		
		return jugadores;				
	}
}
