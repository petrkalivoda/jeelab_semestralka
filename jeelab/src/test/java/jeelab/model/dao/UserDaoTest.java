package jeelab.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.testng.annotations.BeforeTest;

import jeelab.exception.UserNotFoundException;
import jeelab.exception.UserUnavailableException;
import jeelab.model.builder.UserBuilder;
import jeelab.model.entity.User;
import jeelab.test.AbstractDeployableTest;
import jeelab.view.UserForm;

public class UserDaoTest extends AbstractDeployableTest {

	private @Inject UserDao userDao;
	private @Inject UserBuilder userBuilder;
	
	@BeforeTest
	public void clearBuilder() {
		userBuilder.clear();
	}

	@Test
	public void get() throws UserUnavailableException {
		User u = userBuilder.email("test-get@test.test").build();
		userDao.save(u);
		
		User created = userDao.getbyEmail("test-get@test.test");
		assertTrue(userDao.get(created.getId()) instanceof User);
		assertFalse(userDao.get(-1L) instanceof User);
	}

	@Test
	public void getUsers() throws UserUnavailableException {
		int initialSize = userDao.getUsers().size();
		
		User user = userBuilder.email("test-get-users@test.test").build();
		userDao.save(user);
		
		assertEquals(initialSize + 1, userDao.getUsers().size());
	}

	@Test
	public void getbyEmail() throws UserUnavailableException {
		User u = userBuilder.email("test-get-by-email@test.test").build();
		userDao.save(u);
		assertTrue(userDao.getbyEmail("test-get-by-email@test.test") instanceof User);
	}
	
	@Test
	public void getByNonexistentEmail() {
		assertNull(userDao.getbyEmail("nonexistent-email@test.test"));
	}

	@Test
	public void save() throws UserUnavailableException {
		User u = userBuilder.email("test@test.test").build();
		userDao.save(u);
	}
	
	@Test(expected = UserUnavailableException.class)
	public void saveDuplicite() throws UserUnavailableException {	
		User u = userBuilder.email("duplicite@test.test").build();
		User u2 = userBuilder.email("duplicite@test.test").build();
		userDao.save(u);
		userDao.save(u2);
	}

	@Test
	public void updateUser() throws UserUnavailableException, UserNotFoundException {
		User u = userBuilder.email("update@test.test").firstname("before").build();
		userDao.save(u);
		
		User created = userDao.getbyEmail("update@test.test");
		UserForm form = new UserForm();
		form.setEmail("update@test.test");
		form.setFirstName("after");
		form.setPassword("testtest");
		
		userDao.updateUser(created.getId(), form);
		assertEquals(form.getFirstName(), userDao.get(created.getId()).getFirstname());		
	}
	
	@Test
	public void updateDifferentEmail() throws UserUnavailableException, UserNotFoundException {
		User u = userBuilder.email("test-update-different-email@test.test").build();
		userDao.save(u);
		
		User created = userDao.getbyEmail("test-update-different-email@test.test");
		
		UserForm userForm = new UserForm();
		userForm.setEmail("test-update-different-email-2@test.test");
		userForm.setPassword("testtest");
		
		userDao.updateUser(created.getId(), userForm);
	}
	
	@Test(expected = UserNotFoundException.class)
	public void updateNonExistentUser() throws UserUnavailableException, UserNotFoundException {
		UserForm userForm = new UserForm();
		userForm.setEmail("nonexistent-update@test.test");
		userDao.updateUser(Long.MAX_VALUE, userForm);
	}
	
}
