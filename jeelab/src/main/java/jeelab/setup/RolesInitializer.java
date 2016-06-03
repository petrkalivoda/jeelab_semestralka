package jeelab.setup;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import jeelab.exception.UserUnavailableException;
import jeelab.model.builder.RoleBuilder;
import jeelab.model.builder.UserBuilder;
import jeelab.model.dao.RoleDao;
import jeelab.model.dao.UserDao;
import jeelab.model.entity.Role;
import jeelab.model.entity.User;

/**
 * Start-up initializer that creates roles if necessary.
 * 
 * @author Petr Kalivoda
 *
 */
@Singleton
@Startup
public class RolesInitializer {

	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	private static final String DEFAULT_ADMIN_EMAIL = "admin@jeelab.cz";
	private static final String DEFAULT_ADMIN_NAME = "Initial";
	private static final String DEFAULT_ADMIN_SURNAME = "Admin";

	private @Inject RoleDao roleDao;
	private @Inject UserDao userDao;

	private @Inject UserBuilder userBuilder;

	@PostConstruct
	private void startup() {
		Role user = roleDao.getByName(ROLE_USER);
		if (user == null) {
			roleDao.save((new RoleBuilder()).name(ROLE_USER).build());
		}

		Role admin = roleDao.getByName(ROLE_ADMIN);
		if (admin == null) {
			roleDao.save((new RoleBuilder()).name(ROLE_ADMIN).build());

			Logger logger = Logger.getLogger(RolesInitializer.class);
			String password = generatePassword();

			try {
				User adminUser = userBuilder.firstname(DEFAULT_ADMIN_NAME).lastname(DEFAULT_ADMIN_SURNAME)
						.email(DEFAULT_ADMIN_EMAIL).setRole(ROLE_ADMIN).setRole(ROLE_USER).password(password).build();
				userDao.save(adminUser);
				logger.info(String.format("Created initial admin with credentials %s:%s", DEFAULT_ADMIN_EMAIL, password));
				
			} catch (UserUnavailableException e) {
				logger.error("Failed to create initial admin user.");
			}
		}
	}

	/**
	 * Generates a strong password.
	 * @return
	 */
	private String generatePassword() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

}
