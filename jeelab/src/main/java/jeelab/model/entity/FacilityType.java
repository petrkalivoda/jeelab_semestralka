package jeelab.model.entity;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "facility_type")
public class FacilityType implements JpaEntity {

    private static final long serialVersionUID = 2907932664849397798L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @OneToMany(mappedBy = "facilityType")
    private Collection<SportsCentreFacility> facilities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<SportsCentreFacility> getFacilities() {
        return facilities;
    }

    public void setFacilities(Collection<SportsCentreFacility> facilities) {
        this.facilities = facilities;
    }
}
