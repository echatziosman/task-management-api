package spacenes._stproject.entities.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {

	private Long id;
	private String title;
	private String description;
	private boolean completed;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
