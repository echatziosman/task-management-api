package spacenes._stproject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import spacenes._stproject.business.abstracts.TaskService;
import spacenes._stproject.core.utilities.results.DataResult;
import spacenes._stproject.core.utilities.results.Result;
import spacenes._stproject.core.utilities.results.SuccessDataResult;
import spacenes._stproject.core.utilities.results.SuccessResult;
import spacenes._stproject.dataAccess.abstracts.TaskRepository;
import spacenes._stproject.entities.concretes.Task;
import spacenes._stproject.entities.dtos.TaskRequest;
import spacenes._stproject.entities.dtos.TaskResponse;
import spacenes._stproject.entities.dtos.TaskUpdateRequest;

@Service
public class TaskManager implements TaskService{
	
	private TaskRepository taskRepository;

	@Autowired
	public TaskManager(TaskRepository taskRepository) {
		super();
		this.taskRepository = taskRepository;
	}

	@Override
	public DataResult<List<Task>> getAllTasks() {
		
		return new SuccessDataResult<List<Task>>(this.taskRepository.findAll(), "Data Listelendi");
				
	}

	@Override
	public Result add(Task task) {
		
		this.taskRepository.save(task);
		return new SuccessResult("Task eklendi") ;
	}

	@Override
	public DataResult<Task> getByTitle(String title) {
		
		return new SuccessDataResult<Task>(this.taskRepository.getByTitle(title), "Task getirildi");
	}

	@Override
	public DataResult<List<Task>> getByTitleContains(String title) {
		
		return new SuccessDataResult<List<Task>>(this.taskRepository.getByTitleContains(title) , "Task'lar getirildi");
	}

	@Override
	public DataResult<List<Task>> getAllTasks(int pageNo, int pageSize) {
	
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		
		return new SuccessDataResult<List<Task>>(this.taskRepository.findAll(pageable).getContent(), "Sayfa getirildi");
	}

	@Override
	public DataResult<List<Task>> getAllTasksSorted() {
		
		Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
		
		return new SuccessDataResult<List<Task>>(this.taskRepository.findAll(sort), "Veriler sıralı getirildi");
	}

	@Override
	public DataResult<TaskResponse> createTaskWithDto(TaskRequest request) {
		
		Task task = new Task();
		
	    task.setTitle(request.getTitle());
	    task.setDescription(request.getDescription());
	    
	    Task savedTask = this.taskRepository.save(task);
	    
	    TaskResponse response = new TaskResponse();
	    
	    response.setId(savedTask.getId());
	    response.setTitle(savedTask.getTitle());
	    response.setDescription(savedTask.getDescription());
	    response.setCompleted(savedTask.isCompleted());
	    response.setCreatedAt(savedTask.getCreatedAt());
	    response.setUpdatedAt(savedTask.getUpdatedAt());
	    
		return new SuccessDataResult<TaskResponse>(response, "dto'lu Task eklendi");
	}

	@Override
	public DataResult<TaskResponse> updateTaskWithDto(Long id, TaskUpdateRequest request) {
		
		Task task = this.taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
		
		if(request.getTitle() != null) {
			
			task.setTitle(request.getTitle());
		}
		
		if(request.getDescription() != null) {
			
			task.setDescription(request.getDescription());
		}
		
		if(request.getCompleted() != null) {
			
			task.setCompleted(request.getCompleted());
		}
		
		Task updatedTask = this.taskRepository.save(task);
		
		TaskResponse response = new TaskResponse();
		
		response.setId(updatedTask.getId());
		response.setTitle(updatedTask.getTitle());
		response.setDescription(updatedTask.getDescription());
		response.setCompleted(updatedTask.isCompleted()); 
		
		return new SuccessDataResult<TaskResponse>(response, "Task Update edildi");
	}

}
