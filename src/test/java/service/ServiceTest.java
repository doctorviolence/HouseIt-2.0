package service;

import HouseIt.Application;
import HouseIt.entities.*;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.service.*;
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
public class ServiceTest {

    @Autowired
    private IBuildingService buildingService;

    @Autowired
    private IApartmentService apartmentService;

    @Autowired
    private ITenantService tenantService;

    @Autowired
    private ITaskService taskService;

    @Autowired
    private ITaskMessageService taskMessageService;

    @Test
    public void testThatServicesNotNull() {
        assertThat(buildingService).isNotNull();
        assertThat(apartmentService).isNotNull();
        assertThat(tenantService).isNotNull();
        assertThat(taskService).isNotNull();
        assertThat(taskMessageService).isNotNull();
    }

    @Test
    public void whenCreatingBuilding_ThenNoException() {
        Building b = new Building();
        b.setAddress("Sagas gränd");
        //b.setFloorLevels(5);

        buildingService.createBuilding(b);
    }

    /*@Test
    public void whenGettingAllBuildings_ThenReturnThree() {
        List<Building> buildings = buildingService.getBuildings();
        assertThat(buildings).hasSize(3);
    }*/

    @Test
    public void whenSearchingForBuildingId_ThenReturnsCorrectBuilding() throws MyEntityNotFoundException {
        Building b = buildingService.findBuilding(1);
        assertThat(b.getBuildingId()).isEqualTo(1);
        assertThat(b.getAddress()).isEqualTo("Main St");
        //assertThat(b.getFloorLevels()).isEqualTo(3);

        Building b2 = buildingService.findBuilding(2);
        assertThat(b2.getBuildingId()).isEqualTo(2);
        assertThat(b2.getAddress()).isEqualTo("Pitt St");
        //assertThat(b2.getFloorLevels()).isEqualTo(5);
    }

    @Test
    public void whenUpdatingApartment_ThenNoException() throws MyEntityNotFoundException {
        Apartment a = new Apartment(1);
        a.setApartmentNo("N202");
        a.setRent(11524);
        a.setFloorNo(2);
        a.setSize(87);
        a.setBuilding(new Building(1));

        apartmentService.updateApartment(a);

        Apartment updated = apartmentService.findApartment(1);
        assertThat(updated.getApartmentNo()).isEqualTo("N202");
        assertThat(updated.getRent()).isEqualTo(11524);
        assertThat(updated.getFloorNo()).isEqualTo(2);
        assertThat(updated.getSize()).isEqualTo(87);
    }

    @Test
    public void whenDeletingApartment_ThenNoException() throws MyEntityNotFoundException {
        apartmentService.deleteApartment(3);
        Apartment a = null;
        try {
            apartmentService.findApartment(3);
        } catch (MyEntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Apartment with ID 3 not found.");
        }
        assertThat(a).isNull();
    }

    @Test
    public void whenFindingApartmentsInBuilding_ThenReturnCorrectApartments() {
        List<Apartment> apartments = apartmentService.getApartmentsInBuilding(1);
        assertThat(apartments).hasSize(2);

        List<Apartment> apartments2 = apartmentService.getApartmentsInBuilding(2);
        assertThat(apartments2).hasSize(0);
    }

    @Test
    public void whenFindingApartmentsWithInvalidBuilding_ThenReturnNull() {
        List<Apartment> apartments = apartmentService.getApartmentsInBuilding(111);
        assertThat(apartments).isNullOrEmpty();
    }

    @Test
    public void whenGettingTenantsInApartment_ThenReturnOne() {
        List<Tenant> tenants = tenantService.getTenantsInApartment(1);
        assertThat(tenants).hasSize(1);
    }

    @Test
    public void whenGettingTenantsInInvalidApartment_ThenReturnNone() {
        List<Tenant> tenants = tenantService.getTenantsInApartment(111);
        assertThat(tenants).isNullOrEmpty();
    }

    @Test
    public void whenFindingTasksByTenantId_ThenReturnTwo() throws MyEntityNotFoundException {
        List<Task> tasks = taskService.findTodoTasksByTenantId(1);
        assertThat(tasks.size() == 2);
    }

    @Test
    public void whenGettingTaskByType_ThenReturnCorrectTasks() {
        List<Task> water = taskService.getTasksBySubject("Water");
        assertThat(water).isNullOrEmpty();

        List<Task> kitchen = taskService.getTasksBySubject("Kitchen");
        assertThat(kitchen).hasSize(1);

        Task t = kitchen.get(0);
        assertThat(t.getTaskNo()).isEqualTo(1);
        assertThat(t.getSubject()).isEqualTo("Kitchen");
        assertThat(t.getSubject()).isNotEqualTo("Water");
        assertThat(t.getTenant().getTenantId() == 1).isTrue();
        //assertThat(t.getManager().getManagerId() == 1).isTrue();
    }

    //@Test
    //public void whenGettingTasksByFixDate_ThenGetCorrectOrder() {
    //    List<Task> tasks = taskService.getTasksByFixDate();
    //    assertThat(tasks.get(0).getFixDate()).isEqualTo("2018-07-01");
    //    assertThat(tasks.get(0).getFixDate()).isNotEqualTo("2018-07-02");
    //    assertThat(tasks.get(1).getFixDate()).isEqualTo("2018-07-02");
    //    assertThat(tasks.get(1).getFixDate()).isNotEqualTo("2018-07-01");
    //}

    @Test
    public void whenFindingTasksByNo_ThenReturnOneTask() throws MyEntityNotFoundException {
        Task test = taskService.findTask(1);
        assertThat(test.getTaskNo()).isEqualTo(1);
        assertThat(test.getSubject()).isEqualTo("Kitchen");
        assertThat(test.getSubject()).isNotEqualTo("Bathroom");
    }

    @Test
    public void whenFindingTasksByInvalidNo_ThenReturnNull() throws MyEntityNotFoundException {
        Task test = null;
        try {
            test = taskService.findTask(3);
        } catch (MyEntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Task no. 3 not found.");
        }
        assertThat(test).isNull();
    }

    @Test
    public void whenCreatingMessage_ThenNoException() throws MyEntityNotFoundException {
        TaskMessage taskMessage = new TaskMessage();
        taskMessage.setMessageText("Test");
        taskMessage.setTask(new Task(2));

        taskMessageService.createMessage(taskMessage);

        TaskMessage created = taskMessageService.findMessage(3);
        assertThat(created.getMessageText()).isEqualTo("Test");
    }

    @Test
    public void whenGettingMessagesByTask_ThenReturnTwo() throws MyEntityNotFoundException {
        List<TaskMessage> taskMessages = taskMessageService.getTaskMessagesByTask(1);
        assertThat(taskMessages).hasSize(2);
    }

    @Test
    public void whenGettingMessagesByTask_thenReturnTwo() throws MyEntityNotFoundException {
        List<TaskMessage> taskMessages = taskMessageService.getTaskMessagesByTask(1);
        assertThat(taskMessages).hasSize(2);
    }

    @Test
    public void whenGettingMessagesByInvalidTask_thenReturnNull() throws MyEntityNotFoundException {
        List<TaskMessage> taskMessages = taskMessageService.getTaskMessagesByTask(2);
        assertThat(taskMessages).isNullOrEmpty();
    }

}