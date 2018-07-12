package service;

import HouseIt.Application;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.model.Task;
import HouseIt.model.TaskMessage;
import HouseIt.service.ITenantService;
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
public class TenantServiceTest {

    @Autowired
    private ITenantService tenantService;

    @Test
    public void testThatTenantServiceIsNotNull() {
        assertThat(tenantService).isNotNull();
    }

    @Test
    public void whenFindingTasksByTenantId_ThenReturnTwoTasks() throws MyEntityNotFoundException {
        List<Task> tasks = tenantService.findTasksByTenantId(1);
        assertThat(tasks).hasSize(2);
    }

    @Test
    public void whenFindingTasksByNo_ThenReturnOneTask() throws MyEntityNotFoundException {
        Task test = tenantService.findTask(1);
        assertThat(test.getTaskNo()).isEqualTo(1);
        assertThat(test.getTaskType()).isEqualTo("Kitchen");
        assertThat(test.getTaskType()).isNotEqualTo("Bathroom");
    }

    @Test
    public void whenFindingTasksByInvalidNo_ThenReturnNull() throws MyEntityNotFoundException {
        Task test = null;
        try {
            test = tenantService.findTask(3);
        } catch (MyEntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Task no. 3 not found.");
        }
        assertThat(test).isNull();
    }

    @Test
    public void whenGettingMessagesByTask_thenReturnTwo() throws MyEntityNotFoundException {
        List<TaskMessage> taskMessages = tenantService.getTaskMessagesByTask(1);
        assertThat(taskMessages).hasSize(2);
    }

    @Test
    public void whenGettingMessagesByInvalidTask_thenReturnNull() throws MyEntityNotFoundException {
        List<TaskMessage> taskMessages = tenantService.getTaskMessagesByTask(2);
        assertThat(taskMessages).isNullOrEmpty();
    }

}