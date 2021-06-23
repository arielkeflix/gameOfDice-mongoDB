package es.ariel.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ariel.app.models.dao.IJugadorDao;
import es.ariel.app.models.documents.Jugador;





@Service("jpaUserDetailService")
public class JpaUserDetailService implements UserDetailsService{

	@Autowired
	private IJugadorDao jugadorDao;
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailService.class);
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Jugador jugador = jugadorDao.findByUsername(username);// cambiar por nombre
		
		
		
		if(jugador == null) {
			logger.error("Error login: no existe jugador '"+username +"'");
			throw new UsernameNotFoundException("username "+ username +" no existe");
		}
		
		List<GrantedAuthority> authorities = new ArrayList <GrantedAuthority> ();
		
		for(String role: jugador.getRoles()) {
			logger.info("Role: "+role);
			authorities.add(new SimpleGrantedAuthority(role));
		}
		if(authorities.isEmpty()) {
			System.out.println(" authorities.isEmpty !!! ");
			logger.error("Error login:  jugador '"+username +"' no tiene roles asignados");
			throw new UsernameNotFoundException("Error login:  jugador '"+username +"' no tiene roles asignados");
		}
		//return  new User("Ariel","$2a$10$6aEib01haLaDUxWn08GKa.hQ4Lzar8X2cLBsN5MQRBzgqZsUKubSy",true,true,true,true ,authorities);
		return  new User(jugador.getUsername(),jugador.getPassword(), jugador.getEnable(),true,true,true ,authorities);
	}

}
