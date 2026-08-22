package spacenes._stproject.business.abstracts;

import java.util.List;

import spacenes._stproject.core.utilities.results.DataResult;
import spacenes._stproject.core.utilities.results.Result;
import spacenes._stproject.entities.concretes.Task;

public interface TaskService {

	DataResult<List<Task>> getAllTasks ();
	
	DataResult<List<Task>> getAllTasks (int pageNo, int pageSize);
	
	DataResult<List<Task>> getAllTasksSorted ();
	
	Result add(Task task);
	
    DataResult<Task> getByTitle(String title);
	
	DataResult<List<Task>> getByTitleContains(String title);
}
