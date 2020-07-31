package example.micronaut;

import javax.validation.constraints.NotNull;

public class ApplicationConfigurationProperties implements ApplicationConfiguration {
	
	protected final Integer DEFAULT_MAX = 10;
	
	@NotNull
	private Integer max = DEFAULT_MAX;
	
	@Override
	@NotNull
	public @NotNull Integer getMax() {
		
		return max;
	}

	public void setMax(Integer max) {
		
		if(max != null) {
						
			this.max = max;
		}
	}
	
}
