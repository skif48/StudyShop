package stubs;

import com.shop.domain.entity.AttributeValue;
import com.shop.repository.products.AttributeValueRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vladyslav Usenko on 23.08.2016.
 */
public class AttributeValueRepositoryStub implements AttributeValueRepository {
    private final Map<Long, AttributeValue> attributeValueMap = new HashMap<>();

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity
     * @return the saved entity
     */
    @Override
    public <S extends AttributeValue> S save(S entity) {
        Long count = new Long(attributeValueMap.size());
        attributeValueMap.put(count + 1, entity);
        return entity;
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param aLong must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    @Override
    public AttributeValue findOne(Long aLong) {
        return attributeValueMap.get(aLong);
    }

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param aLong must not be {@literal null}.
     * @return true if an entity with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    @Override
    public boolean exists(Long aLong) {
        return attributeValueMap.containsKey(aLong);
    }

    @Override
    public List<AttributeValue> findAll() {
        return new ArrayList<>(attributeValueMap.values());

    }

    @Override
    public List<AttributeValue> findAll(Sort sort) {
        return null;
    }

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable
     * @return a page of entities
     */
    @Override
    public Page<AttributeValue> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<AttributeValue> findAll(Iterable<Long> longs) {
        return null;
    }

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    @Override
    public long count() {
        return attributeValueMap.size();
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param aLong must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    @Override
    public void delete(Long aLong) {
        attributeValueMap.remove(aLong);
    }

    /**
     * Deletes a given entity.
     *
     * @param entity
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    @Override
    public void delete(AttributeValue entity) {
        attributeValueMap.values().remove(entity);
    }

    /**
     * Deletes the given entities.
     *
     * @param entities
     * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
     */
    @Override
    public void delete(Iterable<? extends AttributeValue> entities) {

    }

    /**
     * Deletes all entities managed by the repository.
     */
    @Override
    public void deleteAll() {
        attributeValueMap.clear();
    }

    /**
     * Flushes all pending changes to the database.
     */
    @Override
    public void flush() {

    }

    @Override
    public void deleteInBatch(Iterable<AttributeValue> entities) {

    }

    /**
     * Deletes all entities in a batch call.
     */
    @Override
    public void deleteAllInBatch() {

    }

    /**
     * Returns a reference to the entity with the given identifier.
     *
     * @param aLong must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     * @see EntityManager#getReference(Class, Object)
     */
    @Override
    public AttributeValue getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends AttributeValue> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends AttributeValue> List<S> findAll(Example<S> example) {
        return null;
    }

    /**
     * Saves an entity and flushes changes instantly.
     *
     * @param entity
     * @return the saved entity
     */
    @Override
    public <S extends AttributeValue> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends AttributeValue> List<S> save(Iterable<S> entities) {
        return null;
    }

    /**
     * Returns a single entity matching the given {@link Example} or {@literal null} if none was found.
     *
     * @param example can be {@literal null}.
     * @return a single entity matching the given {@link Example} or {@literal null} if none was found.
     * @throws IncorrectResultSizeDataAccessException if the Example yields more than one result.
     */
    @Override
    public <S extends AttributeValue> S findOne(Example<S> example) {
        return null;
    }

    /**
     * Returns a {@link Page} of entities matching the given {@link Example}. In case no match could be found, an empty
     * {@link Page} is returned.
     *
     * @param example  can be {@literal null}.
     * @param pageable can be {@literal null}.
     * @return a {@link Page} of entities matching the given {@link Example}.
     */
    @Override
    public <S extends AttributeValue> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    /**
     * Returns the number of instances matching the given {@link Example}.
     *
     * @param example the {@link Example} to count instances for, can be {@literal null}.
     * @return the number of instances matching the {@link Example}.
     */
    @Override
    public <S extends AttributeValue> long count(Example<S> example) {
        return 0;
    }

    /**
     * Checks whether the data store contains elements that match the given {@link Example}.
     *
     * @param example the {@link Example} to use for the existence check, can be {@literal null}.
     * @return {@literal true} if the data store contains elements that match the given {@link Example}.
     */
    @Override
    public <S extends AttributeValue> boolean exists(Example<S> example) {
        return false;
    }
}
