package Util.OrmUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory entityManagerFactory;

    // Static initializer to create the EntityManagerFactory once
    static {
        try {
            // Create the EntityManagerFactory using the persistence unit "libraryPU"
            entityManagerFactory = Persistence.createEntityManagerFactory("UML_Persistence_Unit");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Initialization of EntityManagerFactory failed");
        }
    }

    // Get the EntityManagerFactory
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    // Get an EntityManager instance from the factory
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    // Close the EntityManagerFactory
    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
