package jeelab.model.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "business_hours")
public class BusinessHours implements JpaEntity {

    private static final long serialVersionUID = 2907932664849397798L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "day_of_week") //day is reserved sql keyword
    private int day;
    
    @Column(name = "open_time")
    private float openTime;
    
    @Column(name = "close_time")
    private float closeTime;
    
    @ManyToOne
    private SportsCentre sportsCentres;
    
    @ManyToOne
    private SportsCentreFacility sportsCentreFacility;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public float getOpenTime() {
        return openTime;
    }

    public void setOpenTime(float openTime) {
        this.openTime = openTime;
    }

    public float getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(float closeTime) {
        this.closeTime = closeTime;
    }

	public SportsCentre getSportsCentres() {
		return sportsCentres;
	}

	public void setSportsCentres(SportsCentre sportsCentres) {
		this.sportsCentres = sportsCentres;
	}

	public SportsCentreFacility getSportsCentreFacility() {
		return sportsCentreFacility;
	}

	public void setSportsCentreFacility(SportsCentreFacility sportsCentreFacility) {
		this.sportsCentreFacility = sportsCentreFacility;
	}

    
}
