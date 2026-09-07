package spacenes._stproject.business.concretes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import spacenes._stproject.business.concretes.TaskManager;
import spacenes._stproject.core.utilities.exceptions.TaskNotFoundException;
import spacenes._stproject.core.utilities.results.DataResult;
import spacenes._stproject.dataAccess.abstracts.TaskRepository;
import spacenes._stproject.entities.concretes.Task;
import spacenes._stproject.entities.dtos.TaskRequest;
import spacenes._stproject.entities.dtos.TaskResponse;

@ExtendWith(MockitoExtension.class)
public class TaskManagerTest {

	@Mock
	private TaskRepository taskRepository;
	
	@InjectMocks
	private TaskManager taskManager;
	
	@Test
	void shouldReturnTasksWhenTitleExists () {
		
		Task task = new Task();
		
		task.setId(1L);
		task.setTitle("Test Task");
		task.setDescription("Test Description");
		task.setCompleted(false);
		
		//Arrange - PostgreSQL yerine Mockito Repository'e gitmesini söylüyoruz
		when(taskRepository.getByTitle("Test Task"))
		    .thenReturn(List.of(task));
		
		//Act - Gerçek TaskManager sınıfını çalıştırır
		DataResult<List<Task>> result = taskManager.getByTitle("Test Task");
		
		//Assert
		assertTrue(result.isSuccess());
		assertEquals(1, result.getData().size());
		assertEquals("Test Task", result.getData().get(0).getTitle());
		
		//Verify - TaskManager bu metodu getByTitle() çağırdı mı kontrol ediyoruz
		verify(taskRepository).getByTitle("Test Task");
		
		
	}
	
	@Test
	void shouldThrowExceptionWhenTitleDoesNotExist() {
		
		//Arrange
		when(taskRepository.getByTitle("Unknown Task"))
		       .thenReturn(List.of());
		
		//Act & Assert
		assertThrows(TaskNotFoundException.class, () -> taskManager.getByTitle("Unknown Task"));
	}
	
	@Test
	void shouldCreateTaskWithDto() {
		
		//Arrange
		TaskRequest request = new TaskRequest();
		
		request.setTitle("New Task");
		request.setDescription("New Description");
		
		Task savedTask = new Task();
		
		savedTask.setId(1L);
		savedTask.setTitle("New Task");
		savedTask.setDescription("New Description");
		savedTask.setCompleted(false);
		
		when(taskRepository.save(any(Task.class)))
		      .thenReturn(savedTask);
		
		//Act
		DataResult<TaskResponse> result = taskManager.createTaskWithDto(request);
		
		//Assert
		assertTrue(result.isSuccess());
		assertEquals(1L, result.getData().getId());
		assertEquals("New Task", result.getData().getTitle());
		assertEquals("New Description", result.getData().getDescription());
		assertEquals(false, result.getData().isCompleted());
		
		//Verify
		verify(taskRepository).save(any(Task.class));
		
		
	}
}
