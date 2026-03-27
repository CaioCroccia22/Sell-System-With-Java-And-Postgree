package br.com.ccroccia;


import org.junit.jupiter.api.*;

import br.com.ccroccia.dao.ClientDAO;
import br.com.ccroccia.dao.IClientDao;
import br.com.ccroccia.domain.Client;

public class ClientTest {

	
	private IClientDao clientDAO;
	
	
	@Test
	public void saveTest() throws Exception {
		clientDAO = new ClientDAO();

		Client client = new Client();
		client.setCpf(121212121L);
		client.setName("Caio Croccia");
		client.setAge("25");
		client.setPhone(11999999999L);
		client.setAddress("Rua Teste");
		client.setNumber(100);
		client.setCity("São Paulo");
		client.setState("SP");
		Boolean registered = clientDAO.register(client);
		Assertions.assertTrue(registered);

		Client clientBD = clientDAO.find(121212121L);
		Assertions.assertNotNull(clientBD);
		Assertions.assertEquals(client.getCpf(), clientBD.getCpf());
		Assertions.assertEquals(client.getName(), clientBD.getName());

		Boolean deleted = clientDAO.delete(client.getCpf());
		Assertions.assertTrue(deleted);
	}
	
	@Test
	public void buscarTest() throws Exception {
		clientDAO = new ClientDAO();

		Client client = new Client();
		client.setCpf(323232323L);
		client.setName("Caio Croccia");
		client.setAge("25");
		client.setPhone(11999999999L);
		client.setAddress("Rua Teste");
		client.setNumber(200);
		client.setCity("São Paulo");
		client.setState("SP");
		Boolean registered = clientDAO.register(client);
		Assertions.assertTrue(registered);

		Client clientBD = clientDAO.find(323232323L);
		Assertions.assertNotNull(clientBD);
		Assertions.assertEquals(client.getCpf(), clientBD.getCpf());
		Assertions.assertEquals(client.getName(), clientBD.getName());

		Boolean deleted = clientDAO.delete(323232323L);
		Assertions.assertTrue(deleted);
	}
	
	@Test
	public void excluirTest() throws Exception {
		clientDAO = new ClientDAO();

		Client client = new Client();
		client.setCpf(323232323L);
		client.setName("Rodrigo Pires");
		client.setAge("30");
		client.setPhone(11888888888L);
		client.setAddress("Rua Exemplo");
		client.setNumber(300);
		client.setCity("Rio de Janeiro");
		client.setState("RJ");
		Boolean registered = clientDAO.register(client);
		Assertions.assertTrue(registered);

		Client clientBD = clientDAO.find(323232323L);
		Assertions.assertNotNull(clientBD);
		Assertions.assertEquals(client.getCpf(), clientBD.getCpf());
		Assertions.assertEquals(client.getName(), clientBD.getName());

		Boolean deleted = clientDAO.delete(323232323L);
		Assertions.assertTrue(deleted);
	}
	
	
	@Test
	public void atualizarTest() throws Exception {
		clientDAO = new ClientDAO();

		Client client = new Client();
		client.setCpf(786755765L);
		client.setName("Caio Croccia");
		client.setAge("25");
		client.setPhone(11999999999L);
		client.setAddress("Rua Teste");
		client.setNumber(400);
		client.setCity("São Paulo");
		client.setState("SP");
		Boolean registered = clientDAO.register(client);
		Assertions.assertTrue(registered);

		Client clientBD = clientDAO.find(786755765L);
		Assertions.assertNotNull(clientBD);
		Assertions.assertEquals(client.getCpf(), clientBD.getCpf());
		Assertions.assertEquals(client.getName(), clientBD.getName());

		Boolean updated = clientDAO.update(clientBD.getCpf());
		Assertions.assertTrue(updated);

		Boolean deleted = clientDAO.delete(786755765L);
		Assertions.assertTrue(deleted);
	}
}