package employeesmonitor;

import employeesmonitor.model.AuthCredentials;
import employeesmonitor.model.Employer;
import employeesmonitor.networking.utils.AbstractServer;
import employeesmonitor.networking.utils.EmployeesMonitorServer;
import employeesmonitor.networking.utils.ServerException;
import employeesmonitor.persistence.repositories.*;
import employeesmonitor.server.*;
import employeesmonitor.services.IController;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainServer {

    private static int DEFAULT_PORT = 55556;
    private static String DEFAULT_HOST = "localhost";

    private static SessionFactory sessionFactory;
    private static IController controller;
    private static AbstractServer server;


    private static void testOrm() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Employer employer = new Employer("alex", "qweqwe", "broasca");
            employer.setAuthCredentials(new AuthCredentials("qwe@mqwe", "qweqwe"));
            session.persist(employer);
            session.getTransaction().commit();
        }
    }

    public static void main(String[] args) {
//        testOrm();
//        configureApp();
        configureServer();

        try {
            server.start();
        } catch (ServerException e) {
            e.printStackTrace();
            closeSessionFactory();
        }

    }

    private static void configureServer() {
        initializeSessionFactory();
        controller = initializeController();
        server = new EmployeesMonitorServer(DEFAULT_PORT, controller);
    }

    private static IController initializeController() {
        EmployeesDBRepository employeesRepository = new EmployeesORMRepository(sessionFactory);
        EmployersDBRepository employersRepository = new EmployersORMRepository(sessionFactory);
        TasksDBRepository tasksRepository = new TasksORMRepository(sessionFactory);

        Authentication authentication = new Authentication(employeesRepository, employersRepository);
        EmployeesService employeesService = new EmployeesService(employeesRepository);
        EmployersService employersService = new EmployersService(employersRepository);
        TasksService tasksService = new TasksService(tasksRepository);

        return new Controller(authentication, employeesService, employersService, tasksService);
    }

    private static void configureApp(){
        Properties properties = System.getProperties();
        try {
            properties.load(new FileReader("src/db.config"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void initializeSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exceptie "+e.getMessage());
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    private static void closeSessionFactory() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }
}
