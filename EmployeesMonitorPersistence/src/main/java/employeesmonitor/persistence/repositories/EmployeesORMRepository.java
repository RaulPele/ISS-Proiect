package employeesmonitor.persistence.repositories;

import employeesmonitor.model.Employee;
import employeesmonitor.model.Employer;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class EmployeesORMRepository implements EmployeesDBRepository {
    private SessionFactory sessionFactory;

    public EmployeesORMRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Employee entity) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public Employee findOne(Long entityID) {
        Employee employee;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            employee = session.find(Employee.class, entityID);
            session.getTransaction().commit();
        }

        return employee;
    }

    @Override
    public Iterable<Employee> findAll() {
        List<Employee> employees;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            employees = session.createQuery("from Employee", Employee.class).list();
            session.getTransaction().commit();
        }

        return employees;
    }

    @Override
    public void delete(Employee entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Employee entity) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Employee findEmployeeByEmail(String email) {
        Employee employee;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            employee = session.createQuery("from Employee where authCredentials.email =:email", Employee.class).setParameter("email", email)
                    .getSingleResult();

            session.getTransaction().commit();
        }

        return employee;
    }

    @Override
    public Iterable<Employee> getAllEmployees(Employer employer) {
        List<Employee> employees;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            employees = session.createQuery("from Employee where employer =:employer", Employee.class)
                    .setParameter("employer", employer).getResultList();
            session.getTransaction().commit();
        }

        return employees;
    }
}
