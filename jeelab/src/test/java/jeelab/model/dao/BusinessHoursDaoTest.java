package jeelab.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import jeelab.model.builder.SportsCentreBuilder;
import jeelab.model.builder.SportsCentreFacilityBuilder;
import jeelab.model.entity.BusinessHours;
import jeelab.model.entity.SportsCentreFacility;
import jeelab.test.AbstractDeployableTest;
import jeelab.view.HoursForm;

/**
 * Test pro business hours dao
 * @author Petr Kalivoda
 *
 */
public class BusinessHoursDaoTest extends AbstractDeployableTest {

	private @Inject BusinessHoursDao hoursDao;
	private @Inject SportsCentreDao sportsDao;
	
	private @Inject SportsCentreFacilityBuilder facilityBuilder;
	private @Inject SportsCentreBuilder sportsBuilder;
	
	@Before
	public void clearBuilder() {
		sportsBuilder.clear();
		facilityBuilder.clear();
	}

	@Test
	public void testSave() {
		BusinessHours hours = new BusinessHours();
		hours.setDay(1);
		hours.setOpenTime(1f);
		hours.setCloseTime(12f);
		hoursDao.save(hours);
	}

	@Test
	public void testUpdateBusinessHours() {
		// 1. ensure creation of some hours
		BusinessHours hours = new BusinessHours();
		hours.setDay(1);
		hours.setOpenTime(1f);
		hours.setCloseTime(12f);
		hoursDao.save(hours);

		// 2. actual test
		HoursForm form = new HoursForm();
		form.setDay(1);
		form.setOpen(1f);
		form.setClose(2f);

		BusinessHours savedHours = hoursDao.getBusinessHours(1L, 0L).get(0);
		hoursDao.updateBusinessHours(savedHours.getId(), form);

		BusinessHours updatedHours = hoursDao.getBusinessHours(savedHours.getId());
		assertEquals(form.getClose(), new Float(updatedHours.getCloseTime()));
		;
	}

	@Test
	public void testDeleteBusinessHours() {
		// 1. ensure creation of some hours
		BusinessHours hours = new BusinessHours();
		hours.setDay(1);
		hours.setOpenTime(1f);
		hours.setCloseTime(12f);
		hoursDao.save(hours);

		// 2. actual test
		BusinessHours savedHours = hoursDao.getBusinessHours(1L, 0L).get(0);
		hoursDao.deleteBusinessHours(savedHours.getId());
		assertNull(hoursDao.getBusinessHours(savedHours.getId()));
	}

	@Test
	public void testGetBusinessHoursLong() {
		// 1. ensure creation of some hours
		BusinessHours hours = new BusinessHours();
		hours.setDay(1);
		hours.setOpenTime(1f);
		hours.setCloseTime(12f);
		hoursDao.save(hours);

		// 2. actual test
		BusinessHours savedHours = hoursDao.getBusinessHours(1L, 0L).get(0);
		assertTrue(hoursDao.getBusinessHours(savedHours.getId()) instanceof BusinessHours);
		assertNull(hoursDao.getBusinessHours(-1L));
	}

	@Test
	public void testGetBusinessHoursLongLong() {
		List<BusinessHours> hoursList = hoursDao.getBusinessHours(1L, 0L);
		assertTrue(hoursList instanceof List);
	}

	@Test
	public void testGetBusinessHoursCount() {
		Long countBefore = hoursDao.getBusinessHoursCount();
		assertTrue(countBefore instanceof Long);
		
		BusinessHours hours = new BusinessHours();
		hours.setDay(1);
		hours.setOpenTime(1f);
		hours.setCloseTime(12f);
		hoursDao.save(hours);
		
		Long countAfter = hoursDao.getBusinessHoursCount();
		
		assertEquals(new Long(countBefore + 1), countAfter);
	}

	@Test
	public void testGetFacilityHoursForDay() {
		BusinessHours hours = new BusinessHours();
		hours.setDay(1);
		hours.setOpenTime(1f);
		hours.setCloseTime(12f);
		
		List<BusinessHours> hoursList = new LinkedList<>();
		hoursList.add(hours);
		
		SportsCentreFacility facility = facilityBuilder.hoursList(hoursList).build();
		List<SportsCentreFacility> facilityList = new LinkedList<>();
		facilityList.add(facility);
		hours.setSportsCentreFacilities(facilityList);
		
		sportsDao.saveFacility(facility);
		
		BusinessHours facilityHours = hoursDao.getFacilityHoursForDay(facility.getId(), 1);
		assertEquals(hours.getId(), facilityHours.getId());
		assertNull(hoursDao.getFacilityHoursForDay(facility.getId(), 8));
	}

}
