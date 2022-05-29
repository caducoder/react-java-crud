package br.com.cadastramento.controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.cadastramento.Secured;
import br.com.cadastramento.model.Cliente;
import br.com.cadastramento.service.ClienteService;

@Path("/clientes")
public class cadastroController {
	
	@Inject
	private ClienteService clienteService;
	
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response listar() {
		return Response.ok(clienteService.listar()).build();
	}

	@POST
	@Consumes(value = MediaType.APPLICATION_JSON)
	public Response cadastrar(Cliente cliente) {
		clienteService.cadastrar(cliente);
		return Response.status(201).build();
	}
	
	@PUT
	@Path("{id}/{siglaEstado}")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response atualizar(@PathParam("id") Long id, @PathParam("siglaEstado") String siglaEstado, Cliente cliente) {
		Cliente clienteAlterado = clienteService.atualizar(id, siglaEstado, cliente);
		if(clienteAlterado == null)
			return Response.status(404).build();
		return Response.ok(clienteAlterado).build();
	}
	
	@DELETE
	@Path("{id}")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response remover(@PathParam("id") Long id) {
		clienteService.remover(id);
		return Response.status(200).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response buscarPorId(@PathParam("id") Long id) {
		Cliente cliente = clienteService.buscarPorId(id);
		if(cliente == null)
			return Response.status(404).build();
		return Response.ok(cliente).build();
	}
	
}
