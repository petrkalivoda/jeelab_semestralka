package jeelab.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import jeelab.exception.ReservationUnavailableException;
import jeelab.exception.UserUnavailableException;
import jeelab.model.builder.ReservationBuilder;
import jeelab.model.builder.SportsCentreBuilder;
import jeelab.model.builder.SportsCentreFacilityBuilder;
import jeelab.model.builder.UserBuilder;
import jeelab.model.entity.BusinessHours;
import jeelab.model.entity.Reservation;
import jeelab.model.entity.SportsCentreFacility;
import jeelab.model.entity.User;
import jeelab.test.AbstractDeployableTest;
import jeelab.view.ReservationForm;

/**
 * Test pro rezervace
 * @author Petr Kalivoda
 *
 */
public class ReservationDaoTest extends AbstractDeployableTest {
	
	private @Inject ReservationDao reservationDao;
	private @Inject SportsCentreDao sportsDao;
	private @Inject UserDao userDao;
	
	private @Inject ReservationBuilder reservationBuilder;
	private @Inject SportsCentreBuilder sportsCentreBuilder;
	private @Inject UserBuilder userBuilder;
	private @Inject SportsCentreFacilityBuilder facilityBuilder;
	
	private static final Date SOME_MONDAY;
	
	static {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		SOME_MONDAY = c.getTime();
		System.out.println(SOME_MONDAY);
	}
	
	@Before
	public void clearBuilders() {
		reservationBuilder.clear();
		sportsCentreBuilder.clear();
		userBuilder.clear();
		facilityBuilder.clear();
	}
	

	@Test
	public void testSave() throws ReservationUnavailableException {
		BusinessHours hours = new BusinessHours();
		hours.setDay(Calendar.MONDAY);
		hours.setOpenTime(1f);
		hours.setCloseTime(23f);
		
		List<BusinessHours> hoursList = new LinkedList<>();
		hoursList.add(hours);
		
		SportsCentreFacility facility = facilityBuilder.hoursList(hoursList).build();
		List<SportsCentreFacility> facilityList = new LinkedList<>();
		facilityList.add(facility);
		hours.setSportsCentreFacilities(facilityList);
		sportsDao.saveFacility(facility);
		
		Reservation reservation = reservationBuilder.date(SOME_MONDAY).sportsCentreFacility(facility.getId()).from(1f).to(2f).build();
		reservationDao.save(reservation);
		
		assertTrue(reservation.getId() instanceof Long);
	}
	
	@Test(expected = ReservationUnavailableException.class)
	public void testSaveUnavailable() throws ReservationUnavailableException {
		BusinessHours hours = new BusinessHours();
		hours.setDay(Calendar.MONDAY);
		hours.setOpenTime(1f);
		hours.setCloseTime(23f);
		
		List<BusinessHours> hoursList = new LinkedList<>();
		hoursList.add(hours);
		
		SportsCentreFacility facility = facilityBuilder.hoursList(hoursList).build();
		List<SportsCentreFacility> facilityList = new LinkedList<>();
		facilityList.add(facility);
		hours.setSportsCentreFacilities(facilityList);
		sportsDao.saveFacility(facility);
		
		Reservation reservation = reservationBuilder.date(SOME_MONDAY).sportsCentreFacility(facility.getId()).from(1f).to(2f).build();
		reservationDao.save(reservation);
		
		Reservation reservation2 = reservationBuilder.build();
		reservationDao.save(reservation2);
	}
	
	@Test(expected = ReservationUnavailableException.class)
	public void testSaveOutOfHours() throws ReservationUnavailableException {
		BusinessHours hours = new BusinessHours();
		hours.setDay(Calendar.MONDAY);
		hours.setOpenTime(3f);
		hours.setCloseTime(23f);
		
		List<BusinessHours> hoursList = new LinkedList<>();
		hoursList.add(hours);
		
		SportsCentreFacility facility = facilityBuilder.hoursList(hoursList).build();
		List<SportsCentreFacility> facilityList = new LinkedList<>();
		facilityList.add(facility);
		hours.setSportsCentreFacilities(facilityList);
		sportsDao.saveFacility(facility);
		
		Reservation reservation = reservationBuilder.date(SOME_MONDAY).sportsCentreFacility(facility.getId()).from(1f).to(2f).build();
		reservationDao.save(reservation);
	}

	@Test
	public void testUpdateReservation() throws ReservationUnavailableException, UserUnavailableException {
		BusinessHours hours = new BusinessHours();
		hours.setDay(Calendar.MONDAY);
		hours.setOpenTime(1f);
		hours.setCloseTime(23f);
		
		List<BusinessHours> hoursList = new LinkedList<>();
		hoursList.add(hours);
		
		SportsCentreFacility facility = facilityBuilder.hoursList(hoursList).build();
		List<SportsCentreFacility> facilityList = new LinkedList<>();
		facilityList.add(facility);
		hours.setSportsCentreFacilities(facilityList);
		sportsDao.saveFacility(facility);
		
		Reservation reservation = reservationBuilder.date(SOME_MONDAY).sportsCentreFacility(facility.getId()).from(1f).to(2f).build();
		reservationDao.save(reservation);
		
		User user = userBuilder.email("testUpdateReservation@test.test").build();
		userDao.save(user);
		
		ReservationForm form = new ReservationForm();
		form.setCentreFacility(facility.getId());
		form.setUser(user.getId());
		form.setFrom(3f);
		form.setTo(4f);
		form.setDate(reservation.getDate());
		
		reservationDao.updateReservation(reservation.getId(), form);
		Reservation updated = reservationDao.getReservation(reservation.getId());
		
		assertEquals(form.getFrom(), updated.getFrom());
		assertEquals(form.getTo(), updated.getTo());
	}
	
	/**
	 * Restuje situaci kdy přepisuji vlastní rezervaci 
	 * (mám rezervováno od 1 do 3, updatuji od 1 do 4)
	 * @throws ReservationUnavailableException
	 * @throws UserUnavailableException
	 */
	@Test
	public void testUpdateOverwriteSameReservation() throws ReservationUnavailableException, UserUnavailableException {
		BusinessHours hours = new BusinessHours();
		hours.setDay(Calendar.MONDAY);
		hours.setOpenTime(1f);
		hours.setCloseTime(23f);
		
		List<BusinessHours> hoursList = new LinkedList<>();
		hoursList.add(hours);
		
		SportsCentreFacility facility = facilityBuilder.hoursList(hoursList).build();
		List<SportsCentreFacility> facilityList = new LinkedList<>();
		facilityList.add(facility);
		hours.setSportsCentreFacilities(facilityList);
		sportsDao.saveFacility(facility);
		
		Reservation reservation = reservationBuilder.date(SOME_MONDAY).sportsCentreFacility(facility.getId()).from(1f).to(2f).build();
		reservationDao.save(reservation);
		
		User user = userBuilder.email("testUpdateOverwriteSameReservation@test.test").build();
		userDao.save(user);
		
		ReservationForm form = new ReservationForm();
		form.setCentreFacility(facility.getId());
		form.setUser(user.getId());
		form.setFrom(1.5f);
		form.setTo(4f);
		form.setDate(reservation.getDate());
		
		reservationDao.updateReservation(reservation.getId(), form);
		Reservation updated = reservationDao.getReservation(reservation.getId());
		
		assertEquals(form.getFrom(), updated.getFrom());
		assertEquals(form.getTo(), updated.getTo());
	}
	
	
	@Test(expected = ReservationUnavailableException.class)
	public void testUpdateOutOfHours() throws ReservationUnavailableException, UserUnavailableException {
		BusinessHours hours = new BusinessHours();
		hours.setDay(Calendar.MONDAY);
		hours.setOpenTime(3f);
		hours.setCloseTime(23f);
		
		List<BusinessHours> hoursList = new LinkedList<>();
		hoursList.add(hours);
		
		SportsCentreFacility facility = facilityBuilder.hoursList(hoursList).build();
		List<SportsCentreFacility> facilityList = new LinkedList<>();
		facilityList.add(facility);
		hours.setSportsCentreFacilities(facilityList);
		sportsDao.saveFacility(facility);
		
		Reservation reservation = reservationBuilder.date(SOME_MONDAY).sportsCentreFacility(facility.getId()).from(3f).to(4f).build();
		reservationDao.save(reservation);
		
		User user = userBuilder.email("testUpdateOutOfHours@test.test").build();
		userDao.save(user);
		
		ReservationForm form = new ReservationForm();
		form.setCentreFacility(facility.getId());
		form.setUser(user.getId());
		form.setFrom(1f);
		form.setTo(2f);
		form.setDate(reservation.getDate());
		
		reservationDao.updateReservation(reservation.getId(), form);
		Reservation updated = reservationDao.getReservation(reservation.getId());
		
		assertEquals(form.getFrom(), updated.getFrom());
		assertEquals(form.getTo(), updated.getTo());
	}
	
	/**
	 * Testujeme update do jiné rezervace
	 * @throws ReservationUnavailableException
	 * @throws UserUnavailableException
	 */
	@Test(expected = ReservationUnavailableException.class)
	public void testUpdateUnavailable() throws ReservationUnavailableException, UserUnavailableException {
		BusinessHours hours = new BusinessHours();
		hours.setDay(Calendar.MONDAY);
		hours.setOpenTime(1f);
		hours.setCloseTime(23f);
		
		List<BusinessHours> hoursList = new LinkedList<>();
		hoursList.add(hours);
		
		SportsCentreFacility facility = facilityBuilder.hoursList(hoursList).build();
		List<SportsCentreFacility> facilityList = new LinkedList<>();
		facilityList.add(facility);
		hours.setSportsCentreFacilities(facilityList);
		sportsDao.saveFacility(facility);
		
		Reservation reservation = reservationBuilder.date(SOME_MONDAY).sportsCentreFacility(facility.getId()).from(3f).to(4f).build();
		reservationDao.save(reservation);
		
		reservationBuilder.clear();
		Reservation reservation2 = reservationBuilder.date(SOME_MONDAY).sportsCentreFacility(facility.getId()).from(1f).to(2f).build();
		reservationDao.save(reservation2);
		
		User user = userBuilder.email("testUpdateUnavailable@test.test").build();
		userDao.save(user);
		
		ReservationForm form = new ReservationForm();
		form.setCentreFacility(facility.getId());
		form.setUser(user.getId());
		form.setFrom(1f);
		form.setTo(2f);
		form.setDate(reservation.getDate());
		
		reservationDao.updateReservation(reservation.getId(), form);
		Reservation updated = reservationDao.getReservation(reservation.getId());
		
		assertEquals(form.getFrom(), updated.getFrom());
		assertEquals(form.getTo(), updated.getTo());
	}

	@Test
	public void testDeleteReservation() throws ReservationUnavailableException {
		BusinessHours hours = new BusinessHours();
		hours.setDay(Calendar.MONDAY);
		hours.setOpenTime(1f);
		hours.setCloseTime(23f);
		
		List<BusinessHours> hoursList = new LinkedList<>();
		hoursList.add(hours);
		
		SportsCentreFacility facility = facilityBuilder.hoursList(hoursList).build();
		List<SportsCentreFacility> facilityList = new LinkedList<>();
		facilityList.add(facility);
		hours.setSportsCentreFacilities(facilityList);
		sportsDao.saveFacility(facility);
		
		Reservation reservation = reservationBuilder.date(SOME_MONDAY).sportsCentreFacility(facility.getId()).from(1f).to(2f).build();
		reservationDao.save(reservation);
		reservationDao.deleteReservation(reservation.getId());
		
		assertNull(reservationDao.getReservation(reservation.getId()));
	}

	@Test
	public void testGetReservation() throws ReservationUnavailableException {
		BusinessHours hours = new BusinessHours();
		hours.setDay(Calendar.MONDAY);
		hours.setOpenTime(1f);
		hours.setCloseTime(23f);
		
		List<BusinessHours> hoursList = new LinkedList<>();
		hoursList.add(hours);
		
		SportsCentreFacility facility = facilityBuilder.hoursList(hoursList).build();
		List<SportsCentreFacility> facilityList = new LinkedList<>();
		facilityList.add(facility);
		hours.setSportsCentreFacilities(facilityList);
		sportsDao.saveFacility(facility);
		
		Reservation reservation = reservationBuilder.date(SOME_MONDAY).sportsCentreFacility(facility.getId()).from(1f).to(2f).build();
		reservationDao.save(reservation);
		
		Reservation gotten = reservationDao.getReservation(reservation.getId());
		assertNotNull(gotten);
		assertEquals(reservation.getId(), gotten.getId());
		assertNull(reservationDao.getReservation(-1L));
	}

	@Test
	public void testGetReservations() {
		assertTrue(reservationDao.getReservations(1L, 0L) instanceof List);
	}

	@Test
	public void testGetReservationsCount() throws ReservationUnavailableException {
		Long before = reservationDao.getReservationsCount();
		assertNotNull(before);
		
		BusinessHours hours = new BusinessHours();
		hours.setDay(Calendar.MONDAY);
		hours.setOpenTime(1f);
		hours.setCloseTime(23f);
		
		List<BusinessHours> hoursList = new LinkedList<>();
		hoursList.add(hours);
		
		SportsCentreFacility facility = facilityBuilder.hoursList(hoursList).build();
		List<SportsCentreFacility> facilityList = new LinkedList<>();
		facilityList.add(facility);
		hours.setSportsCentreFacilities(facilityList);
		sportsDao.saveFacility(facility);
		
		Reservation reservation = reservationBuilder.date(SOME_MONDAY).sportsCentreFacility(facility.getId()).from(1f).to(2f).build();
		reservationDao.save(reservation);
		
		Long after = reservationDao.getReservationsCount();
		assertNotNull(after);
		assertEquals(new Long(before + 1), after);
	}

	@Test
	public void testGetUserReservations() throws ReservationUnavailableException, UserUnavailableException {	
		User user = userBuilder.email("testGetUserReservations@test.test").build();
		userDao.save(user);
		
		assertTrue(reservationDao.getUserReservations(user.getId(), 1L, 0L) instanceof List);
	}

	@Test
	public void testGetUserReservationsCount() throws UserUnavailableException, ReservationUnavailableException {
		User user = userBuilder.email("testGetUserReservationsCount@test.test").build();
		userDao.save(user);
		
		assertEquals(new Long(0), reservationDao.getUserReservationsCount(user.getId()));
		
		BusinessHours hours = new BusinessHours();
		hours.setDay(Calendar.MONDAY);
		hours.setOpenTime(1f);
		hours.setCloseTime(23f);
		
		List<BusinessHours> hoursList = new LinkedList<>();
		hoursList.add(hours);
		
		SportsCentreFacility facility = facilityBuilder.hoursList(hoursList).build();
		List<SportsCentreFacility> facilityList = new LinkedList<>();
		facilityList.add(facility);
		hours.setSportsCentreFacilities(facilityList);
		sportsDao.saveFacility(facility);
		
		Reservation reservation = reservationBuilder.date(SOME_MONDAY).user(user.getId()).sportsCentreFacility(facility.getId()).from(1f).to(2f).build();
		reservationDao.save(reservation);
		
		assertEquals(new Long(1), reservationDao.getUserReservationsCount(user.getId()));
	}

}
