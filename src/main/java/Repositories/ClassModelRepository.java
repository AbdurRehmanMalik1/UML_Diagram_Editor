package Repositories;


import Models.ClassModel;
import Util.OrmUtil.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ClassModelRepository {

    public int save(ClassModel classModel) {
        EntityTransaction transaction = null;
        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(classModel);
            transaction.commit();
            return classModel.getId();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
