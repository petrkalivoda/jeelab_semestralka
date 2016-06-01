package jeelab.model.dao;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import jeelab.model.builder.RoleBuilder;
import jeelab.model.entity.Role;
import jeelab.setup.RolesInitializer;
import jeelab.test.AbstractDeployableTest;

/**
 * Test pro RoleDao
 * @author Petr Kalivoda
 *
 */
public class RoleDaoTest extends AbstractDeployableTest {
	
	private @Inject RoleDao roleDao;
	private @Inject RoleBuilder roleBuilder;
	
	public @Before void clearBuilder() {
		this.roleBuilder.clear();
	}

	@Test
	public void testGetByName() {
		Role r = roleDao.getByName(RolesInitializer.ROLE_ADMIN);
		assertTrue(r instanceof Role);
		
		Role r2 = roleDao.getByName("this role sure as hell doesn't exist");
		assertNull(r2);		
	}

	@Test
	public void testSave() {
		Role r = roleBuilder.name("testSave").build();
		roleDao.save(r);
		
		Role created = roleDao.getByName("testSave");
		assertTrue(created instanceof Role);
	}

	@Test
	public void testGet() {
		Role r = roleBuilder.name("testGet").build();
		roleDao.save(r);
		
		Role created = roleDao.getByName("testGet");
		Role actual = roleDao.get(created.getId());
		assertTrue(actual instanceof Role);
		assertEquals(created.getId(), actual.getId());
		
		assertNull(roleDao.get(-1L));
	}

}
