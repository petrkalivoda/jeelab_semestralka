package jeelab.auth;

import java.security.acl.Group;

import javax.inject.Inject;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;

import org.jboss.security.auth.spi.DatabaseServerLoginModule;

import jeelab.CdiHelper;
import jeelab.model.dao.UserDao;
import jeelab.model.entity.User;

public class JeeLoginModule extends DatabaseServerLoginModule  {
	
	@Inject
	private UserDao userDao;

	@Override
	protected String getUsersPassword() throws LoginException {
		inject();	
		User user = userDao.getbyEmail(this.getUsername());
		if (user == null)
			throw new LoginException();
		return user.getPassword();
	}

	@Override
	protected Group[] getRoleSets() throws LoginException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	  protected boolean validatePassword(String inputPassword, String expectedPassword) {
		return true;
	}

	private void inject() {
		if (this.userDao == null) {
			try {
				CdiHelper.programmaticInjection(JeeLoginModule.class, this);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	}
	
}
