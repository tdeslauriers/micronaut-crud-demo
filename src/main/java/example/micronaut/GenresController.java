package example.micronaut;

import java.net.URI;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

import example.micronaut.domain.Genre;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@ExecuteOn(TaskExecutors.IO)
@Controller("/genres")
public class GenresController {
	
	protected final GenreRepository genreRepository;    
	
	protected URI location(Long id) {
        return URI.create("/genres/" + id);
    }

    protected URI location(Genre genre) {
        return location(genre.getId());
    }

	public GenresController(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}
	
	@Get("{id}")
	public Genre show(Long id) {
		
		return genreRepository.findById(id).orElse(null);
	}
	
	@Put
	public HttpResponse update(@Body @Valid GenreUpdateCommand command) {
		
		int numberOfEntitiesUpdated = genreRepository.update(command.getId(), command.getName());
		
		return HttpResponse.noContent().header(HttpHeaders.LOCATION, location(command.getId()).getPath());
	}
	
    @Get(value = "/list{?args*}") 
    public List<Genre> list(@Valid SortingAndOrderArguments args) {
        
    	return genreRepository.findAll(args);
    }

	@Post
	public HttpResponse<Genre> save(@Body @Valid GenreSaveCommand cmd){
		
		Genre genre = genreRepository.save(cmd.getName());
		
		return HttpResponse
				.created(genre)
				.headers(headers -> headers.location(location(genre.getId())));
	}
	
	@Post("/ex")
	public HttpResponse<Genre> saveExceptions(@Body @Valid GenreSaveCommand cmd){
		
		try {
			
			Genre genre = genreRepository.saveWithException(cmd.getName());
			
			return HttpResponse
					.created(genre)
					.headers(headers -> headers.location(location(genre.getId())));
		} catch (PersistenceException e) {
			
			return HttpResponse.noContent();
		}
	}
	
    @Delete("/{id}") 
    public HttpResponse delete(Long id) {
        genreRepository.deleteById(id);
        return HttpResponse.noContent();
    }
}
