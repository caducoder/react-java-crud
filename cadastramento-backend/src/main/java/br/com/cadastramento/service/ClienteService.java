package br.com.cadastramento.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.cadastramento.dao.ClienteDAO;
import br.com.cadastramento.model.Cliente;

@Stateless
public class ClienteService {

	@Inject
	private ClienteDAO dao;
	
	public void cadastrar(Cliente cliente) {
		dao.cadastrar(cliente);
	}
	
	public List<Cliente> listar() {
		return dao.listar();
	}
	
	public void remover(Long id) {
		dao.remover(id);
	}
	
	public Cliente buscarPorId(Long id) {
		return dao.buscarPorId(id);
	}

	public Cliente atualizar(Long id, String sigla, Cliente cliente) {

		return dao.atualizar(id, sigla, cliente);

	}
}
