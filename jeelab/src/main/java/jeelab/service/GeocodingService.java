package jeelab.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import jeelab.exception.GeocodingErrorException;
import jeelab.model.entity.SportsCentre;

/**
 * Služba pro geokódování lokalit
 * @author Petr Kalivoda
 *
 */
public class GeocodingService {

	private static final String GOOGLE_GEO_KEY = "AIzaSyDkbmg2ziMvcLU4KPCM1huDfcdefvofiIU";

	/**
	 * Geokoduje sportovni centrum
	 * @param centre
	 * @throws GeocodingErrorException
	 */
	public void geocode(SportsCentre centre) {
		try {
			GeoApiContext context = new GeoApiContext().setApiKey(GOOGLE_GEO_KEY);
			GeocodingResult[] results = GeocodingApi.geocode(context, String.format("%s %s, %s, %s", centre.getStreet(),
					centre.getBuildingNumber(), centre.getCity(), centre.getCountry())).await();

			if(results.length == 0) {
				throw new GeocodingErrorException();
			}
			
			GeocodingResult result = results[0];
			centre.setLatitude((float) result.geometry.location.lat);
			centre.setLongitude((float) result.geometry.location.lng);

		} catch (Exception ignore) {
			
		}
	}
}
