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
            if (classModel.getId() == 0) {
                Integer maxModelId = (Integer) em.createQuery("SELECT MAX(c.id) FROM ClassModel c")
                        .getSingleResult();
                if (maxModelId != null) {
                    classModel.setModelId(maxModelId + 1);
                } else {
                    classModel.setModelId(1);
                }
                em.persist(classModel);
            } else {
                em.merge(classModel);
            }

            transaction.commit();
            return classModel.getId();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            // Close EntityManager safely if it's open
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
