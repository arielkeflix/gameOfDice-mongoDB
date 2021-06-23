package es.ariel.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ariel.app.models.documents.Jugador;


@Service
public class RankingService {
	@Autowired
	private IJugadorService jugadorService;

	public String maximo(String color) {
		List<Jugador> jugadores = jugadorService.findAll();
		String campeon = null;
		Integer max = 0;
		for (Jugador j : jugadores) {
			switch (color) {

				case "red":
					if (j.getPromedioRoja() > max) {
						campeon = j.getNombre();
						max = j.getPromedioRoja();
					}
					break;
				case "blue":
					if (j.getPromedioAzul() > max) {
						campeon = j.getNombre();
						max = j.getPromedioAzul();
					}
					break;
				case "green":
					if (j.getPromedioVerde() > max) {
						campeon = j.getNombre();
						max = j.getPromedioVerde();
					}
					break;
	
				default:
					break;
				}

		}

		return campeon;
	}
	public String minimo(String color) {
		List<Jugador> jugadores = jugadorService.findAll();
		String perdedor = null;
		Integer min = 100;
		for (Jugador j : jugadores) {
			switch (color) {

				case "red":
					if (j.getPromedioRoja() < min) {
						perdedor = j.getNombre();
						min = j.getPromedioRoja();
					}
					break;
				case "blue":
					if (j.getPromedioAzul() < min) {
						perdedor = j.getNombre();
						min = j.getPromedioAzul();
					}
					break;
				case "green":
					if (j.getPromedioVerde() < min) {
						perdedor = j.getNombre();
						min = j.getPromedioVerde();
					}
					break;
	
				default:
					break;
				}

		}

		return perdedor;
	}
	public Integer promedio(String color) {
		
		List<Jugador> jugadores = jugadorService.findAll();
		
		double aux = 0;
		double cont = 0;
		//Integer promedio = 0;
		
		for (Jugador j : jugadores) {
			switch (color) {

				case "red":				 						
						for (Integer t : j.getListaTiradasRojas()) {
							    cont++;
							if (t==7)
								aux++;
						}															
					break;
				case "blue":
					for (Integer t : j.getListaTiradasAzules()) {
					    cont++;
					if  (t==7)
						aux++;
				}	
					break;
				case "green":
					for (Integer t : j.getListaTiradasVerdes()) {
					    cont++;
					if  (t==7)
						aux++;
				}	
					break;
	
				default:
					break;
				}

		}

		return (int) ((aux / cont) * 100);
	}
}
