package es.ariel.app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.ariel.app.models.documents.Jugador;

import es.ariel.app.models.service.IJugadorService;
import es.ariel.app.models.service.RankingService;

@Controller
@SessionAttributes("jugador")

public class JugadorController {

	@Autowired
	private IJugadorService jugadorService ;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private RankingService rankingService;
	
	
	@GetMapping({ "/index", "", "/" })
	public String inicio(Model model) {
 
		model.addAttribute("titulo", "Bienvenido al  'GAME OF DICE !' ");

		return "index";
	}
	
	@GetMapping("/listar")
	public String listar(Model model,Authentication authentication ) {
		if(authentication != null) {
			model.addAttribute("username",authentication.getName());	
		}
		
		List<Jugador>jugadores = jugadorService.findAll();	
		
		model.addAttribute("jugadores",jugadores);
		model.addAttribute("titulo","Listado de jugadores");
		return "listar";
	}
	
	@GetMapping("/ranking")
	public String ranking(Model model ) {		
		
		model.addAttribute("titulo","Ranking general de jugadores");
		model.addAttribute("maxred",rankingService.maximo("red"));
		model.addAttribute("maxgreen",rankingService.maximo("green"));
		model.addAttribute("maxblue",rankingService.maximo("blue"));
		model.addAttribute("minred",rankingService.minimo("red"));
		model.addAttribute("mingreen",rankingService.minimo("green"));
		model.addAttribute("minblue",rankingService.minimo("blue"));
		model.addAttribute("promred",rankingService.promedio("red"));
		model.addAttribute("promgreen",rankingService.promedio("green"));
		model.addAttribute("promblue",rankingService.promedio("blue"));
		return "ranking";
	}
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") String id,Model model ) {
		
		Jugador jugador = jugadorService.findOne(id);		  	           
		
		model.addAttribute("jugador",jugador);
		model.addAttribute("titulo","Detalle jugador: ");
		return "jugador/ver";
	}
	
	@GetMapping("/form")
	public String crear( Model model, RedirectAttributes flash) {
		
		Jugador jugador = new Jugador();
		
		model.addAttribute("titulo", "Formulario de jugador  ");
		model.addAttribute("jugador", jugador);

		return "jugador/form";
	}
	@GetMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") String id, Model model, RedirectAttributes flash) {

		Jugador jugador = null;

		if (id !=null) {
			jugador = jugadorService.findOne(id);
			System.out.println("Jugador desde editar: " +jugador);
			if (jugador == null) {
				flash.addFlashAttribute("error", "El ID del jugador no existe en la BBDD!");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del jugador no puede ser cero!");
			return "redirect:/listar";
		}
		model.addAttribute("jugador", jugador);
		model.addAttribute("titulo", "Editar Jugador");
		return "jugador/form";
	}

	@PostMapping("/form") 
	public String guardar(@Valid Jugador jugador, BindingResult result, Model model,
			 RedirectAttributes flash, SessionStatus status) {
		
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de jugador  " );
			return "jugador/form";
		}
		jugador.setPassword(passwordEncoder.encode(jugador.getPassword()));
		jugador.setEnable(true);
		List<String> roles =  Arrays.asList("ROLE_USER");		
		jugador.setRoles(roles);
		jugadorService.save(jugador);
		return "redirect:listar" ;		
	}
	@GetMapping("/loginAnonimo") 
	public String guardarAnonimo(  Model model,
			 RedirectAttributes flash, SessionStatus status) {		
		
		Random r = new Random();
		boolean existeUsername = true;
		Integer clave=0;
		
		while (existeUsername) {
			 clave = r.nextInt(9999)+1;
			if( jugadorService.findByUsername("anonimo"+clave) == null )
				existeUsername = false;			
		}
		List<String> roles =  Arrays.asList("ROLE_USER");
		Jugador jugador =new Jugador("Anonimo"+clave,"anonimo"+clave, "$2a$10$6aEib01haLaDUxWn08GKa.hQ4Lzar8X2cLBsN5MQRBzgqZsUKubSy", 1, roles);	
		jugadorService.save(jugador);
		flash.addFlashAttribute("success"," ahora puedes ingreasar como usuario: '" + "anonimo"+ clave + "' El password: '1234' "+ "    LUEGO PUEDES CAMBIAR LOS DATOS");
		return "redirect:/login" ;		
	}
	
	@GetMapping({ "/dados/{colorDados}" })
	public String dados(@PathVariable(value = "colorDados") String colorDados, Model model,Authentication authentication) {	
		Jugador jugador=null ;
		 
		
		if (authentication != null) {
			jugador = jugadorService.findByUsername(authentication.getName() );
			model.addAttribute("titulo", "Hola "+  jugador.getNombre()  +".  Bienvenido al  'GAME OF DICE'! ");
		}else {
			model.addAttribute("titulo", "Hola usuario anonimo.  Bienvenido al  'GAME OF DICE'! ");
		}
		
		model.addAttribute("valorDado1", 5);
		model.addAttribute("valorDado2", 3);
		model.addAttribute("colorDados",colorDados);

		return "dados/dados";
	}
	
	@PostMapping("/formDados") 
	public String guardarJuego(@RequestParam("colorDados") String colorDados, @RequestParam("valorDado1") Integer valorDado1, 
			@RequestParam("valorDado2") Integer valorDado2, Model model, Authentication authentication   ) {
		Jugador jugador=null ;
		
		if (authentication != null) {
			  
				 jugador = jugadorService.findByUsername(authentication.getName() );     //.findByNombre(authentication.getName());
				
				 jugador.insertThrowDice( colorDados, valorDado1 +valorDado2 );
				 jugadorService.save(jugador);
				 model.addAttribute("titulo", "Hola "+  jugador.getNombre()  +".  Bienvenido al  'GAME OF DICE'! ");
		  }else {
			  System.out.println("Usuario anonimo ");
			  model.addAttribute("titulo", "Hola usuario anonimo.  Bienvenido al  'GAME OF DICE'! ");}					
		
		
		model.addAttribute("valorDado1", valorDado1);
		model.addAttribute("valorDado2", valorDado2);
		model.addAttribute("colorDados", colorDados);
		
		
		return  "dados/dados";
	}
	@GetMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") String id, RedirectAttributes flash) {

		if (id !=null) {
			Jugador jugador = jugadorService.findOne(id);
			if (jugador.getRoles().contains("ROLE_ADMIN")) {
				flash.addFlashAttribute("error", "No se puede eliminar a un administrador!");
				return "redirect:/listar";
			}
			System.out.println("desde eliminar: " + jugador);

			jugadorService.remove(jugador);
			flash.addFlashAttribute("success", "Jugador eliminado con éxito!");

			

		}
		return "redirect:/listar";
	}
	@GetMapping(value = "/eliminarPartidas/{id}")
	public String eliminarPartidas(@PathVariable(value = "id") String id, RedirectAttributes flash) {

		if (id !=null) {						

			jugadorService.deleteGames(id);			
		
			flash.addFlashAttribute("success", "Vaciado de juegos con exito");			

		}
		return "redirect:/listar";
	}
}
