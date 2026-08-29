package spacenes._stproject.entities.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {

	@NotBlank(message = "Title cannot be empty")
	@Size(max=50, message = "Title cannot exceed 50 characters")
	private String title;
	
	@Size(max=250, message = "Description cannot exceed 250 characters")
	private String description;
}
