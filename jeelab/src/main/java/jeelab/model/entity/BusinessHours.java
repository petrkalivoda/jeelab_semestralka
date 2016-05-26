package jeelab.model.entity;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
    private float close_time;
    
    @ManyToMany(targetEntity = SportsCentre.class)
    private Collection<SportsCentre> sportsCentres;
    
    @ManyToMany(targetEntity = SportsCentreFacility.class)
    private Collection<SportsCentreFacility> sportsCentreFacilities;

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

    public float getClose_time() {
        return close_time;
    }

    public void setClose_time(float close_time) {
        this.close_time = close_time;
    }

    public Collection<SportsCentre> getSportsCentres() {
        return sportsCentres;
    }

    public void setSportsCentres(Collection<SportsCentre> sportsCentres) {
        this.sportsCentres = sportsCentres;
    }

    public Collection<SportsCentreFacility> getSportsCentreFacilities() {
        return sportsCentreFacilities;
    }

    public void setSportsCentreFacilities(Collection<SportsCentreFacility> sportsCentreFacilities) {
        this.sportsCentreFacilities = sportsCentreFacilities;
    }
}
