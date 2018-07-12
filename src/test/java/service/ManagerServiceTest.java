package service;

import HouseIt.Application;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.model.*;
import HouseIt.service.IManagerService;
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
public class ManagerServiceTest {

    @Autowired
    private IManagerService managerService;

    @Test
    public void testThatManagerServiceIsNotNull() {
        assertThat(managerService).isNotNull();
    }

    @Test
    public void whenGettingAllBuildings_ThenReturnThree() {
        List<Building> buildings = managerService.getBuildings();
        assertThat(buildings).hasSize(3);
    }

    @Test
    public void whenGettingTenantsInApartment_ThenReturnOne() {
        List<Tenant> tenants = managerService.getTenantsInApartment(1);
        assertThat(tenants).hasSize(1);
    }

    @Test
    public void whenGettingTenantsInInvalidApartment_ThenReturnNone() {
        List<Tenant> tenants = managerService.getTenantsInApartment(111);
        assertThat(tenants).isNullOrEmpty();
    }

    @Test
    public void whenFindingTasksByTenantId_ThenReturnTwo() {
        List<Task> tasks = managerService.findTasksByTenantId(1);
        assertThat(tasks.size() == 2);
    }

    @Test
    public void whenFindingTasksByInvalidTenantId_ThenReturnNone() {
        List<Task> tasks = managerService.findTasksByTenantId(111);
        assertThat(tasks).isNullOrEmpty();
    }

    @Test
    public void whenGettingTaskByType_ThenReturnCorrectTasks() {
        List<Task> water = managerService.getTasksByType("Water");
        assertThat(water).isNullOrEmpty();

        List<Task> kitchen = managerService.getTasksByType("Kitchen");
        assertThat(kitchen).hasSize(1);

        Task t = kitchen.get(0);
        assertThat(t.getTaskNo()).isEqualTo(1);
        assertThat(t.getTaskType()).isEqualTo("Kitchen");
        assertThat(t.getTaskType()).isNotEqualTo("Water");
        assertThat(t.getTenant().getTenantId() == 1).isTrue();
        assertThat(t.getManager().getManagerId() == 1).isTrue();
    }

    @Test
    public void whenGettingTasksByFixDate_ThenGetCorrectOrder() {
        List<Task> tasks = managerService.getTasksByFixDate();
        assertThat(tasks.get(0).getFixDate()).isEqualTo("2018-07-01");
        assertThat(tasks.get(0).getFixDate()).isNotEqualTo("2018-07-02");
        assertThat(tasks.get(1).getFixDate()).isEqualTo("2018-07-02");
        assertThat(tasks.get(1).getFixDate()).isNotEqualTo("2018-07-01");
    }

    @Test
    public void whenCreatingMessage_ThenNoException() throws MyEntityNotFoundException {
        TaskMessage taskMessage = new TaskMessage();
        taskMessage.setMessageText("Test");
        taskMessage.setTask(new Task(2));

        managerService.createMessage(taskMessage);

        TaskMessage created = managerService.findMessage(3);
        assertThat(created.getMessageText()).isEqualTo("Test");
    }

    @Test
    public void whenGettingMessagesByTask_ThenReturnTwo() {
        List<TaskMessage> taskMessages = managerService.getTaskMessagesByTask(1);
        assertThat(taskMessages).hasSize(2);
    }

}