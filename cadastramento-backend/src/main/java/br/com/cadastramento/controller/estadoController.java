package br.com.cadastramento.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.cadastramento.service.EstadoService;

@Path("/estados")
public class estadoController {

	@Inject
	private EstadoService estadoService;
	
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response listarEstados() {
		return Response.ok(estadoService.listarEstados()).build();
	}
}
