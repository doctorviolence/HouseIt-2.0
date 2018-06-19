package HouseIt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "buildings")
public class Building implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "building_id", nullable = false)
    private long buildingId;

    @Column(name = "address")
    private String address;

    @Column(name = "floor_levels")
    private int floorLevels;

    @OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
    private List<Apartment> apartments;

    public Building() {

    }

    public Building(long buildingId) {
        this.buildingId = buildingId;
    }

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFloorLevels() {
        return floorLevels;
    }

    public void setFloorLevels(int floorLevels) {
        this.floorLevels = floorLevels;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

}
