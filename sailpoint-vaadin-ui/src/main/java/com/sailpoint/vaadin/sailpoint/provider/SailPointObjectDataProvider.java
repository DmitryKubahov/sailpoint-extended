package com.sailpoint.vaadin.sailpoint.provider;

import com.sailpoint.extended.exception.SailPointContextNoFoundException;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.shared.Registration;
import lombok.extern.slf4j.Slf4j;
import sailpoint.api.SailPointContext;
import sailpoint.api.SailPointFactory;
import sailpoint.object.QueryOptions;
import sailpoint.object.SailPointObject;
import sailpoint.tools.GeneralException;

import java.util.stream.Stream;

/**
 * Vaadin data provider for sailpoint objects
 *
 * @param <T> - type of sailpoint object
 */
@Slf4j
public class SailPointObjectDataProvider<T extends SailPointObject> implements DataProvider<T, Void> {

    /**
     * Type of sailpoint object
     */
    private final Class<T> type;

    /**
     * Constructor with type of sailpoint object type
     *
     * @param type - sailpoint object type
     */
    public SailPointObjectDataProvider(Class<T> type) {
        this.type = type;
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
        try {
            return getContext().countObjects(type, null);
        } catch (GeneralException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Stream<T> fetch(Query<T, Void> query) {
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setFirstRow(query.getOffset());
        queryOptions.setResultLimit(query.getLimit());
        try {
            return getContext().getObjects(type, queryOptions).stream();
        } catch (GeneralException e) {
            e.printStackTrace();
        }
        return Stream.empty();
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

    /**
     * Try to get current context
     *
     * @return salipoint context of throws {@link SailPointContextNoFoundException}
     */
    private SailPointContext getContext() {
        log.debug("Try to get current sailpoint context");
        try {
            return SailPointFactory.getCurrentContext();
        } catch (GeneralException ex) {
            log.error("Could not found current sailpoint context");
            throw new SailPointContextNoFoundException();
        }
    }
}
