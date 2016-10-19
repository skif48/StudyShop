package stubs;

import com.shop.domain.entity.AttributeValue;
import com.shop.domain.entity.Product;
import com.shop.domain.entity.ProductType;
import com.shop.repository.products.AttributeRepository;
import com.shop.repository.products.AttributeValueRepository;
import com.shop.repository.products.ProductRepository;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * Created by Vladyslav Usenko on 22.08.2016.
 */
public class ProductRepositoryStub implements ProductRepository {
    private final Map<Long, Product> repository = new HashMap<>();
    private final AttributeRepository attributeRepository;
    private final AttributeValueRepository attributeValueRepository;

    public ProductRepositoryStub(AttributeRepository attributeRepository, AttributeValueRepository attributeValueRepository) {
        this.attributeRepository = attributeRepository;
        this.attributeValueRepository = attributeValueRepository;
    }

    @Override
    public Product findByUuid(@Param("uuid") String uuid) {
        for (Product p : repository.values()) {
            if (p.getUuid().equals(uuid))
                return p;
        }

        return null;
    }

    @Override
    public Object deleteByUuid(String uuid) {
        Integer index = 1;
        for (Product p : repository.values()) {
            if (p.getUuid().equals(uuid))
                repository.remove(index, p);
            index++;
        }
        return -1;
    }

    @Override
    public List<Object[]> getFullInfoByUuid(@Param("uuid") String uuid) {
        List<Object[]> list = new ArrayList<>();
        Product product = findByUuid(uuid);
        for (AttributeValue attributeValue : attributeValueRepository.findAll()) {
            if (attributeValue.getProduct().equals(product)){
                list.add(new Object[]{attributeValue.getAttribute().getName(), attributeValue.getValue()});
            }
        }
        return list;
    }

    @Override
    public List<Product> findByType(@Param("type") ProductType type) {
        List<Product> list = new ArrayList<>();
        for (Product product : repository.values()) {
            if (product.getType().equals(type)) {
                list.add(product);
            }
        }
        return list;
    }

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity
     * @return the saved entity
     */
    @Override
    public <S extends Product> S save(S entity) {
        Long count = new Long(repository.size());
        repository.put(count + 1, entity);
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
    public Product findOne(Long aLong) {
        return repository.get(aLong);
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
        return repository.containsKey(aLong);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public List<Product> findAll(Sort sort) {
        return null;
    }

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable
     * @return a page of entities
     */
    @Override
    public Page<Product> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Product> findAll(Iterable<Long> longs) {
        return null;
    }

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    @Override
    public long count() {
        return repository.size();
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param aLong must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    @Override
    public void delete(Long aLong) {
        repository.remove(aLong);
    }

    /**
     * Deletes a given entity.
     *
     * @param entity
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    @Override
    public void delete(Product entity) {
        Long key = new Long(1);
        for (Product product : repository.values()) {
            if (product.equals(entity)) {
                repository.remove(key);
            } else {
                key++;
            }
        }
    }

    /**
     * Deletes the given entities.
     *
     * @param entities
     * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
     */
    @Override
    public void delete(Iterable<? extends Product> entities) {

    }

    /**
     * Deletes all entities managed by the repository.
     */
    @Override
    public void deleteAll() {
        repository.clear();
    }

    /**
     * Flushes all pending changes to the database.
     */
    @Override
    public void flush() {

    }


    @Override
    public void deleteInBatch(Iterable<Product> entities) {

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
    public Product getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends Product> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Product> List<S> findAll(Example<S> example) {
        return null;
    }

    /**
     * Saves an entity and flushes changes instantly.
     *
     * @param entity
     * @return the saved entity
     */
    @Override
    public <S extends Product> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Product> List<S> save(Iterable<S> entities) {
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
    public <S extends Product> S findOne(Example<S> example) {
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
    public <S extends Product> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    /**
     * Returns the number of instances matching the given {@link Example}.
     *
     * @param example the {@link Example} to count instances for, can be {@literal null}.
     * @return the number of instances matching the {@link Example}.
     */
    @Override
    public <S extends Product> long count(Example<S> example) {
        return 0;
    }

    /**
     * Checks whether the data store contains elements that match the given {@link Example}.
     *
     * @param example the {@link Example} to use for the existence check, can be {@literal null}.
     * @return {@literal true} if the data store contains elements that match the given {@link Example}.
     */
    @Override
    public <S extends Product> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public List<Product> findByLabel(@Param("label") String label) {
        return null;
    }
}
