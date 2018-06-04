package HouseIt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "apartments")
public class Apartment implements Serializable {

    private static final long serialVersionUID = 1L;
    private long apartmentId;
    private String apartmentNo;
    private int size;
    private int floorNo;
    private double rent;
    private Building building;
    private Set<Tenant> tenants;

    public Apartment() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "apartment_id", nullable = false)
    public long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(long apartmentId) {
        this.apartmentId = apartmentId;
    }

    @Column(name = "apartment_no")
    public String getApartmentNo() {
        return apartmentNo;
    }

    public void setApartmentNo(String apartmentNo) {
        this.apartmentNo = apartmentNo;
    }

    @Column(name = "size")
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Column(name = "floor_no")
    public int getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    @Column(name = "rent")
    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "building_id", nullable = false)
    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @OneToMany(mappedBy = "apartment", fetch = FetchType.LAZY)
    public Set<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(Set<Tenant> tenants) {
        this.tenants = tenants;
    }

    @Override
    public int hashCode() {
        return (int) apartmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o && this.getClass() == o.getClass()) {
            return true;
        } else if (o == null && this.getClass() != o.getClass()) {
            return false;
        }

        Apartment a = (Apartment) o;
        return (apartmentId == a.getApartmentId());
    }

}
