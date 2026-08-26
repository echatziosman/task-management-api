package spacenes._stproject.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateRequest {

	private String title;
	private String description;
	private Boolean completed;
}
