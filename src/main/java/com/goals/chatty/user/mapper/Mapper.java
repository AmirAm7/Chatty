package com.goals.chatty.user.mapper;

import java.util.ArrayList;
import java.util.List;

public interface Mapper<T,E> {

    T mapEntity(E entity);
    E mapDomainObject(T domainObject);

    default List<T> mapEntities(List<E> entities){
        List<T> domainObjects = new ArrayList<>(entities.size());
        for (E entity: entities) {
            domainObjects.add(mapEntity(entity));
        }
        return domainObjects;
    }

    default List<E> mapDomainObjects(List<T> domainObjects){
        List<E> entities = new ArrayList<>(domainObjects.size());
        for (T dto: domainObjects) {
            entities.add(mapDomainObject(dto));
        }
        return entities;
    }
}
