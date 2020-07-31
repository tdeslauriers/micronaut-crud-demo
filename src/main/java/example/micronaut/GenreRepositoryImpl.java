package example.micronaut;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import example.micronaut.domain.Genre;
import io.micronaut.transaction.annotation.ReadOnly;

@Singleton
public class GenreRepositoryImpl implements GenreRepository {
	
	private final EntityManager entityManager;
	private final ApplicationConfiguration applicationConfiguration;
	
	public GenreRepositoryImpl(EntityManager entityManager, ApplicationConfiguration applicationConfiguration) {
		this.entityManager = entityManager;
		this.applicationConfiguration = applicationConfiguration;
	}

	@Override
	@ReadOnly
	public Optional<Genre> findById(@NotNull Long id) {
		
		return Optional.ofNullable(entityManager.find(Genre.class, id));
	}

	@Override
	@Transactional
	public Genre save(@NotBlank String name) {
		
		Genre genre = new Genre(name);
		entityManager.persist(genre);
		
		return genre;
	}

	@Override
	@Transactional
	public Genre saveWithException(@NotBlank String name) {
		
		save(name);
		throw new PersistenceException();
	}

	@Override
	@Transactional
	public void deleteById(@NotNull Long id) {
		
		findById(id).ifPresent(entityManager::remove);
	}

	@Override
	@Transactional
	public int update(@NotNull Long id, @NotBlank String name) {
		
		return entityManager.createQuery(
				"UPDATE Genre g SET name = :name WHERE id = :id")
					.setParameter("name", name)
					.setParameter("id", id)
					.executeUpdate();
	}
	
	private static final List<String> VALID_PROPERTY_NAMES = 
			Arrays.asList("id", "name");

	@Override
	@ReadOnly
	public List<Genre> findAll(SortingAndOrderArguments args) {
		
		String qlString = "SELECT g FROM Genre as g";
		if(args.getOrder().isPresent() 
				&& VALID_PROPERTY_NAMES.contains(args.getSort().get())) {
			
			// not sql injection: see constraints on SortingAndOrderArguments: encapsulates
			qlString += " ORDER BY g." + args.getSort().get() + " " + args.getOrder().get().toLowerCase();
		}
		
		TypedQuery<Genre> query = entityManager.createQuery(qlString, Genre.class);
		query.setMaxResults(args.getMax().orElseGet(applicationConfiguration::getMax));
		args.getOffset().ifPresent(query::setFirstResult);
		
		return query.getResultList();
		
	}

}
