package br.com.cadastramento.controller;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.cadastramento.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Path("/login")
public class authController {
	
	public static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(Usuario usuario) {
		try {
			if (usuario.getEmail().equals("teste@gmail.com") && usuario.getSenha().equals("teste123")) {
				String jwtToken = Jwts.builder().setSubject(usuario.getEmail()).setIssuer("localhost:8080")
						.setIssuedAt(new Date())
						.setExpiration(Date
								.from(LocalDateTime.now().plusMinutes(15L).atZone(ZoneId.systemDefault()).toInstant()))
						.signWith(key).compact();

				return Response.status(Response.Status.OK).entity(jwtToken).build();
			} else
				return Response.status(Response.Status.UNAUTHORIZED).entity("Usuário e/ou senha inválidos").build();
		} catch (Exception ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
		}
	}
}
