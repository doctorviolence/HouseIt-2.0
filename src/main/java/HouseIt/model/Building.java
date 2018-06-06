package HouseIt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "buildings")
public class Building implements Serializable {

    private static final long serialVersionUID = 1L;
    private long buildingId;
    private String address;
    private int floorLevels;
    private List<Apartment> apartments;

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
    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

}
