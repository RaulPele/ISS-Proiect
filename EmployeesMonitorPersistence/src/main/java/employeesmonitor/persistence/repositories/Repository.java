package employeesmonitor.persistence.repositories;

import employeesmonitor.model.Entity;

public interface Repository<ID, E extends Entity<ID>> {

    void save(E entity);

    E findOne(ID entityID);

    Iterable<E> findAll();

    void delete(E entity);

    void update(E entity);
}
