package example.micronaut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import example.micronaut.domain.Genre;
import io.micronaut.scheduling.annotation.Scheduled;

@Singleton
public class Task {
	
	private final GenreRepository genreRepository;
	
	public Task(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	List<String> titles = new ArrayList<String>(Arrays.asList("Dogs", "Cats", "Birds", "Bats"));
	 
	@Scheduled(fixedDelay = "10s")
	void loadTheDB() {
		
		for (int i = 0; i < titles.size(); i++) {
			
			genreRepository.save(titles.get(i));
		}
	}
}
