package br.com.ccroccia.service;

import br.com.ccroccia.dao.IClientDao;
import br.com.ccroccia.domain.Client;

public class ClientService implements IClientService {

	private IClientDao clientDao;


	public ClientService(IClientDao clientDao) {
		this.clientDao = clientDao;
	}

	@Override
	public boolean save(Client client) throws Exception {
		clientDao.register(client);
		return true;

	}

	@Override
	public Client findByCPF(Long cpf) {
		Client client = new Client();
		client.setCpf(cpf);
		return client;
	}

	@Override
	public void delete(Long cpf) {

	}

	@Override
	public void update(Client client) {

	}

}
