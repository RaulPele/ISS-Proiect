package employeesmonitor.persistence.repositories;

import employeesmonitor.model.Employee;
import employeesmonitor.model.Employer;
import employeesmonitor.model.Task;
import employeesmonitor.model.TaskStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class TasksORMRepository implements TasksDBRepository {
    private SessionFactory sessionFactory;

    public TasksORMRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Task entity) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public Task findOne(Long entityID) {
        Task task;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            task = session.find(Task.class, entityID);
            session.getTransaction().commit();
        }

        return task;
    }

    @Override
    public Iterable<Task> findAll() {
        List<Task> tasks;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            tasks = session.createQuery("from Task", Task.class).list();
            session.getTransaction().commit();
        }

        return tasks;
    }

    @Override
    public void delete(Task entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Task entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public Iterable<Task> getAllTasks(Employer tasksCreator) {
        List<Task> tasks;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            tasks = session.createQuery("from Task where taskCreator = :tasksCreator", Task.class)
                    .setParameter("tasksCreator", tasksCreator).list();
            session.getTransaction().commit();
        }

        return tasks;
    }

    @Override
    public Iterable<Task> getEmployeesTasks(Employee employee) {
        List<Task> tasks;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            tasks = session.createQuery("from Task where assignedEmployee = :assignedEmployee", Task.class)
                    .setParameter("assignedEmployee", employee).list();
            session.getTransaction().commit();
        }

        return tasks;
    }

    @Override
    public void unassignAllTasks(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createMutationQuery("UPDATE Task SET status = :status, assignedEmployee = null WHERE assignedEmployee = :assignedEmployee")
                    .setParameter("assignedEmployee", employee)
                    .setParameter("status", TaskStatus.UNASSIGNED)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

}
