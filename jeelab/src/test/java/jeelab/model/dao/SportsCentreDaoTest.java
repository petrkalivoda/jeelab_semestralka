package jeelab.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import jeelab.model.builder.SportsCentreBuilder;
import jeelab.model.builder.SportsCentreFacilityBuilder;
import jeelab.model.entity.FacilityType;
import jeelab.model.entity.SportsCentre;
import jeelab.model.entity.SportsCentreFacility;
import jeelab.test.AbstractDeployableTest;
import jeelab.view.SportCentreForm;

public class SportsCentreDaoTest extends AbstractDeployableTest {
	
	private @Inject SportsCentreDao sportsDao;
	private @Inject SportsCentreBuilder sportsBuilder;
	private @Inject SportsCentreFacilityBuilder facilityBuilder;
	
	@Before
	public void clearBuilders() {
		sportsBuilder.clear();
		facilityBuilder.clear();
	}

	@Test
	public void testSaveCentre() {
		SportsCentre centre = sportsBuilder.country("Romania").build();
		sportsDao.saveCentre(centre);
		assertTrue(centre.getId() instanceof Long);
	}

	@Test
	public void testUpdateCentre() {
		SportsCentre centre = sportsBuilder.country("Salamia").build();
		sportsDao.saveCentre(centre);
		
		SportCentreForm form = new SportCentreForm();
		form.setCountry("Bulgaria");
		
		sportsDao.updateCentre(centre.getId(), form);
		SportsCentre updated = sportsDao.getCentre(centre.getId());
		
		assertEquals(form.getCountry(), updated.getCountry());
	}

	@Test
	public void testDeleteCentre() {
		SportsCentre centre = sportsBuilder.country("Deletia").build();
		sportsDao.saveCentre(centre);
		
		sportsDao.deleteCentre(centre.getId());
		assertNull(sportsDao.getCentre(centre.getId()));
	}

	@Test
	public void testGetCentre() {
		SportsCentre centre = sportsBuilder.country("Savia").build();
		sportsDao.saveCentre(centre);
		
		SportsCentre gotten = sportsDao.getCentre(centre.getId());
		
		assertTrue(gotten instanceof SportsCentre);
		assertEquals(centre.getId(), gotten.getId());
		assertNull(sportsDao.getCentre(-1L));
	}

	@Test
	@Ignore
	public void testGetCompleteCentre() {
		//TODO Maybe implement later?
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllCentre() {
		List<SportsCentre> centres = sportsDao.getAllCentre();
		assertTrue(centres instanceof List);
	}

	@Test
	public void testGetCentreFacilites() {
		SportsCentre centre = sportsBuilder.country("Centrestan").build();
		
		SportsCentreFacility facility = facilityBuilder.build();
		sportsDao.saveFacility(facility);
		
		List<SportsCentreFacility> facilityList = new LinkedList<>();
		
		centre.setFacilities(facilityList);
		facility.setSportsCentre(centre);
		sportsDao.saveCentre(centre);
		
		assertEquals(facilityList.size(), sportsDao.getCentreFacilites(centre.getId()).size());
	}

	@Test
	public void testGetFacility() {
		SportsCentreFacility facility = facilityBuilder.build();
		sportsDao.saveFacility(facility);
		
		assertTrue(sportsDao.getFacility(facility.getId()) instanceof SportsCentreFacility);
	}

	@Test
	public void testSaveFacilityType() {
		FacilityType type = new FacilityType();
		type.setName("test");
		
		sportsDao.saveFacilityType(type);
		assertTrue(type.getId() instanceof Long);
	}

	@Test
	public void testUpdateFacilityTypeName() {
		FacilityType type = new FacilityType();
		type.setName("test");
		sportsDao.saveFacilityType(type);
		
		sportsDao.updateFacilityTypeName(type.getId(), "changed");
		FacilityType updated = sportsDao.getFacilityType(type.getId());
		assertEquals("changed", updated.getName());
	}

	@Test
	public void testDeleteFacilityType() {
		FacilityType type = new FacilityType();
		type.setName("test");
		sportsDao.saveFacilityType(type);
		sportsDao.deleteFacilityType(type.getId());
		
		assertNull(sportsDao.getFacilityType(type.getId()));
	}

	@Test
	public void testGetFacilityType() {
		FacilityType type = new FacilityType();
		type.setName("test");
		sportsDao.saveFacilityType(type);
		
		assertTrue(sportsDao.getFacilityType(type.getId()) instanceof FacilityType);
	}

	@Test
	public void testSaveFacility() {
		SportsCentreFacility facility = facilityBuilder.build();
		sportsDao.saveFacility(facility);
		
		assertTrue(facility.getId() instanceof Long);
	}

	@Test
	public void testGetFacilityTypes() {
		assertTrue(sportsDao.getFacilityTypes() instanceof List);
	}

}
