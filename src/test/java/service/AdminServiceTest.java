package service;

import HouseIt.Application;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.entities.Apartment;
import HouseIt.entities.Building;
import HouseIt.entities.Manager;
import HouseIt.service.IAdminService;
import config.TestDatabaseConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, TestDatabaseConfig.class})
@ActiveProfiles("test")
public class AdminServiceTest {

    @Autowired
    private IAdminService adminService;

    @Test
    public void testThatAdminServiceIsNotNull() {
        assertThat(adminService).isNotNull();
    }

    @Test
    public void whenCreatingBuilding_ThenNoException() {
        Building b = new Building();
        b.setAddress("Sagas gränd");
        b.setFloorLevels(5);

        adminService.createBuilding(b);
    }

    @Test
    public void whenUpdatingApartment_ThenNoException() throws MyEntityNotFoundException {
        Apartment a = new Apartment(1);
        a.setApartmentNo("N202");
        a.setRent(11524);
        a.setFloorNo(2);
        a.setSize(87);
        a.setBuilding(new Building(1));

        adminService.updateApartment(a);

        Apartment updated = adminService.findApartment(1);
        assertThat(updated.getApartmentNo()).isEqualTo("N202");
        assertThat(updated.getRent()).isEqualTo(11524);
        assertThat(updated.getFloorNo()).isEqualTo(2);
        assertThat(updated.getSize()).isEqualTo(87);
    }

    @Test
    public void whenDeletingApartment_ThenNoException() throws MyEntityNotFoundException {
        adminService.deleteApartment(3);
        Apartment a = null;
        try {
            adminService.findApartment(3);
        } catch (MyEntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Apartment with ID 3 not found.");
        }
        assertThat(a).isNull();
    }

    @Test
    public void whenFindingApartmentsInBuilding_ThenReturnCorrectApartments() {
        List<Apartment> apartments = adminService.getApartmentsInBuilding(1);
        assertThat(apartments).hasSize(2);

        List<Apartment> apartments2 = adminService.getApartmentsInBuilding(2);
        assertThat(apartments2).hasSize(0);
    }

    @Test
    public void whenFindingApartmentsWithInvalidBuilding_ThenReturnNull() {
        List<Apartment> apartments = adminService.getApartmentsInBuilding(111);
        assertThat(apartments).isNullOrEmpty();
    }

    @Test
    public void whenSearchingForBuildingId_ThenReturnsCorrectBuilding() throws MyEntityNotFoundException {
        Building b = adminService.findBuilding(1);
        assertThat(b.getBuildingId()).isEqualTo(1);
        assertThat(b.getAddress()).isEqualTo("Hyllie allé");
        assertThat(b.getFloorLevels()).isEqualTo(3);

        Building b2 = adminService.findBuilding(2);
        assertThat(b2.getBuildingId()).isEqualTo(2);
        assertThat(b2.getAddress()).isEqualTo("Drottninggatan");
        assertThat(b2.getFloorLevels()).isEqualTo(5);
    }

    @Test
    public void whenGettingAllManagers_ThenReturnOne() {
        List<Manager> managers = adminService.getManagers();
        assertThat(managers).hasSize(1);
    }

}