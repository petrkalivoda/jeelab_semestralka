package jeelab.model.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "sports_centre")
public class SportsCentre implements JpaEntity {

    private static final long serialVersionUID = 2907932664849397798L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;
    
    @Column(name = "country")
    private String country;

    @Column(name = "building_number")
    private String buildingNumber;

    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "gps_lng")
    private Float longitude;
    
    @Column(name = "gps_lat")
    private Float latitude;
    
    
    @OneToMany(mappedBy = "sportsCentre", cascade= CascadeType.ALL)
    private Collection<SportsCentreFacility> facilities;
    
    @ManyToMany(targetEntity = BusinessHours.class, cascade = CascadeType.PERSIST)
    private Collection<BusinessHours> businessHours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Collection<SportsCentreFacility> getFacilities() {
        return facilities;
    }

    public void setFacilities(Collection<SportsCentreFacility> facilities) {
        this.facilities = facilities;
    }

    public Collection<BusinessHours> getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(Collection<BusinessHours> businessHours) {
        this.businessHours = businessHours;
    }

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
}
