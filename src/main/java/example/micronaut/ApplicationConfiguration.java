package example.micronaut;

import javax.validation.constraints.NotNull;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("application")
public interface ApplicationConfiguration {
	
	@NotNull Integer getMax();
}
