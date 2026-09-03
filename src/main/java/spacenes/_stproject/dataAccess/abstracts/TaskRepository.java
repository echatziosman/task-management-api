package spacenes._stproject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import spacenes._stproject.entities.concretes.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

	List<Task> getByTitle(String title);
	
	List<Task> getByTitleContains(String title);
}
