package HouseIt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "apartments")
public class Apartment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "apartment_id", nullable = false)
    private long apartmentId;

    @Column(name = "apartment_no")
    private String apartmentNo;

    @Column(name = "size")
    private int size;

    @Column(name = "floor_no")
    private int floorNo;

    @Column(name = "rent")
    private double rent;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "building_id", nullable = false)
    private Building building;

    @OneToMany(mappedBy = "apartment", fetch = FetchType.LAZY)
    private List<Tenant> tenants;

    public Apartment() {

    }

    public Apartment(long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getApartmentNo() {
        return apartmentNo;
    }

    public void setApartmentNo(String apartmentNo) {
        this.apartmentNo = apartmentNo;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

}
