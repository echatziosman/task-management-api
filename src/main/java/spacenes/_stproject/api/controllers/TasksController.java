package spacenes._stproject.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spacenes._stproject.business.abstracts.TaskService;
import spacenes._stproject.core.utilities.results.DataResult;
import spacenes._stproject.core.utilities.results.Result;
import spacenes._stproject.entities.concretes.Task;


@RestController
@RequestMapping("/api/tasks")
public class TasksController {
	
	private TaskService taskService;
	
    @Autowired
	public TasksController(TaskService taskService) { 
		super();
		this.taskService = taskService;
	}


    @GetMapping("/getall")
	public DataResult<List<Task>> getAllTasks()
	{
		return this.taskService.getAllTasks(); 
	}
    
    @PostMapping("/add")
    public Result add(@RequestBody Task task) {
    	
    	return this.taskService.add(task);
    }
    
    @GetMapping("/getByTitle")
    public DataResult<Task> getByTitle(@RequestParam  String title){
    	
    	return this.taskService.getByTitle(title);
    }
    
    @GetMapping("/getByTitleContains")
    public DataResult<List<Task>> getByTitleContains (@RequestParam String title){
    	return this.taskService.getByTitleContains(title);
    }
    
    @GetMapping("/getAllByPage")
    public DataResult<List<Task>> getAllTasks (int PageNo, int PageSize){
    	return this.taskService.getAllTasks(PageNo, PageSize);
    }
    
    @GetMapping("/getAllSorted")
    public DataResult<List<Task>> getAllTasksSorted (){
    	
    	return this.taskService.getAllTasksSorted();
    }
}
