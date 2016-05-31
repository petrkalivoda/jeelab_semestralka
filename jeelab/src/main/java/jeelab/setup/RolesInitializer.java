package jeelab.setup;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import jeelab.model.builder.RoleBuilder;
import jeelab.model.dao.RoleDao;
import jeelab.model.entity.Role;

/**
 * Start-up initializer that creates roles if necessary.
 * @author Petr Kalivoda
 *
 */
@Singleton
@Startup
public class RolesInitializer {
	
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	private @Inject RoleDao roleDao;
	
	@PostConstruct
	private void startup() {
		Role user = roleDao.getByName(ROLE_USER);
		if(user == null) {
			roleDao.save((new RoleBuilder()).name(ROLE_USER).build());
		}
		
		Role admin = roleDao.getByName(ROLE_ADMIN);
		if(admin == null) {
			roleDao.save((new RoleBuilder()).name(ROLE_ADMIN).build());
		}
	}

}
