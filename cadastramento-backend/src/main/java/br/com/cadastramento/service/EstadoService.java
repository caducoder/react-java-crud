package br.com.cadastramento.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.cadastramento.dao.EstadoDAO;
import br.com.cadastramento.model.Estados;

@Stateless
public class EstadoService {

	@Inject
	private EstadoDAO dao1;
	
	public List<Estados> listarEstados() {
		return dao1.listar();
	}
}
