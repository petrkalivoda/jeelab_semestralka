package jeelab.model.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "sports_centre_facility")
public class SportsCentreFacility implements JpaEntity {

    private static final long serialVersionUID = 2907932664849397798L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    private SportsCentre sportsCentre;
    
    @JoinTable(
  		   name = "facility_hours", 
  		   joinColumns = @JoinColumn(name = "facility_id"), 
  		   inverseJoinColumns = @JoinColumn(name = "hour_id")
  		 )
    @ManyToMany(targetEntity = BusinessHours.class, cascade = CascadeType.ALL)
    private Collection<BusinessHours> businessHours;
    
    @ManyToOne
    private FacilityType facilityType;
    
    @OneToMany(mappedBy = "sportsCentreFacility", targetEntity = Reservation.class)
    private Collection<Reservation> reservations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SportsCentre getSportsCentre() {
        return sportsCentre;
    }

    public void setSportsCentre(SportsCentre sportsCentre) {
        this.sportsCentre = sportsCentre;
    }

    public Collection<BusinessHours> getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(Collection<BusinessHours> businessHours) {
        this.businessHours = businessHours;
    }

    public FacilityType getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
    }

	public Collection<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Collection<Reservation> reservations) {
		this.reservations = reservations;
	}
}
