package employeesmonitor.persistence.repositories;

import employeesmonitor.model.Employee;
import employeesmonitor.model.Employer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class EmployersORMRepository implements EmployersDBRepository {
    private SessionFactory sessionFactory;

    public EmployersORMRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Employer entity) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public Employer findOne(Long entityID) {
        Employer employer;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            employer = session.find(Employer.class, entityID);
            session.getTransaction().commit();
        }

        return employer;
    }

    @Override
    public Iterable<Employer> findAll() {
        List<Employer> employers;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            employers = session.createQuery("from Employer", Employer.class).list();
            session.getTransaction().commit();
        }

        return employers;
    }

    @Override
    public void delete(Employer entity) {

    }

    @Override
    public void update(Employer entity) {

    }

    @Override
    public Employer findEmployerByEmail(String email) {
        Employer employer;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            employer = session.createQuery("from Employer where authCredentials.email =:email", Employer.class).setParameter("email", email)
                    .getSingleResult();

            session.getTransaction().commit();
        }

        return employer;
    }
}
