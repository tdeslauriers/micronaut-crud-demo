package example.micronaut;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class GenreUpdateCommand {
	
	@NotNull
	private Long id;
	
	@NotBlank
	private String name;

	public GenreUpdateCommand() {
	}

	public GenreUpdateCommand(@NotNull Long id, @NotBlank String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
