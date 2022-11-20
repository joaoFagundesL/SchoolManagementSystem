package br.edu.utfpr.controller;

import br.edu.utfpr.dao.AdministradorDAO;
import br.edu.utfpr.modelo.Administrador;

public class AdminController {
	AdministradorDAO dao = new AdministradorDAO();
	
	public Administrador validarLogin(String usuario, String senha) {
		
		Administrador a = dao.consultarPeloNome(usuario);
		
		if(a == null) {
			return null;
		}
		else if(a.getSenha().equals(senha)){
			return a;
		} else {
			return null;			
		}
	}
}
