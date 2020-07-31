package example.micronaut;

import javax.validation.constraints.NotBlank;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class GenreSaveCommand {
	
	@NotBlank
	private String name;

	public GenreSaveCommand() {
	}

	public GenreSaveCommand(@NotBlank String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
