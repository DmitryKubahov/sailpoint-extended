package com.sailpoint.vaadin.sailpoint.provider;

import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.shared.Registration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Vaadin data provider for spring data jpa repository
 *
 * @param <T>  - type repository entity
 * @param <ID> - identifier type of jpa entity
 */
@Slf4j
public class SpringDataJpaGridProvider<T, ID extends Serializable> implements DataProvider<T, Void> {

    /**
     * Repository instance
     */
    private final JpaRepository<T, ID> repository;

    /**
     * Constructor with repository instance
     *
     * @param repository - jpa repository instance
     */
    public SpringDataJpaGridProvider(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    /**
     * Not in memory data
     *
     * @return false
     */
    @Override
    public boolean isInMemory() {
        return false;
    }

    /**
     * Calculate size of all entities
     *
     * @param query - current query instance
     * @return size of data
     */
    @Override
    public int size(Query<T, Void> query) {
        return (int) this.repository.count();
    }

    @Override
    public Stream<T> fetch(Query<T, Void> query) {
        return this.repository.findAll(new PageRequest(query.getOffset() / query.getLimit(), query.getLimit()))
                .getContent().stream();
    }

    @Override
    public void refreshItem(T item) {

    }

    @Override
    public void refreshAll() {

    }

    @Override
    public Registration addDataProviderListener(DataProviderListener<T> listener) {
        return null;
    }
}
