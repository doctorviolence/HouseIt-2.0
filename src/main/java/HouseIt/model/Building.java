package HouseIt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "buildings")
public class Building implements Serializable {

    private static final long serialVersionUID = 1L;
    private long buildingId;
    private String address;
    private int floorLevels;
    private Set<Apartment> apartments;

    public Building() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "building_id", nullable = false)
    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "floor_levels")
    public int getFloorLevels() {
        return floorLevels;
    }

    public void setFloorLevels(int floorLevels) {
        this.floorLevels = floorLevels;
    }

    @OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
    public Set<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(Set<Apartment> apartments) {
        this.apartments = apartments;
    }

    @Override
    public int hashCode() {
        return (int) buildingId + address.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o && this.getClass() == o.getClass()) {
            return true;
        }
        else if (o == null && this.getClass() != o.getClass()) {
            return false;
        }

        Building b = (Building) o;
        return (buildingId == b.getBuildingId() && address.equals(b.getAddress()));
    }

}
