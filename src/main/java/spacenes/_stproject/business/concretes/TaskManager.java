package spacenes._stproject.business.concretes;

import java.util.List;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
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

}
