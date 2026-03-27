package br.com.ccroccia.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.ccroccia.dao.generics.GenericDAO;
import br.com.ccroccia.dao.generics.IGenericDAO;
import br.com.ccroccia.domain.Client;

public interface IClientDao extends IGenericDAO<Client, Long>{


}
