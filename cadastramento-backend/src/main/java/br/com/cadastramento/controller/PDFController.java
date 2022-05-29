package br.com.cadastramento.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.com.cadastramento.service.PDFService;

@Path("/clientes/report")
public class PDFController {
	
	@Inject
	private PDFService pdfService;
	
	@GET
	@Produces("application/pdf")
	public Response gerarRelatorioPdf() {
		ResponseBuilder responseBuilder = Response.ok(pdfService.gerarRelatorio());
		responseBuilder.type("application/pdf");
		responseBuilder.header("Acces-Control-Allow-Origin", "*");
		responseBuilder.header("Access-Control-Allow-Methods", "GET");
	    responseBuilder.header(
	          "Access-Control-Allow-Headers",
	          "X-Requested-With,Host,User-Agent,Accept,Accept-Language,Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin");
		responseBuilder.header("Content-Disposition", "filename=relatorio.pdf");
		return responseBuilder.build();
	}
}
