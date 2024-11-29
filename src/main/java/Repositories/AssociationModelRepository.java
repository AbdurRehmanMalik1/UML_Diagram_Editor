package Repositories;

import Models.AssociationModel;
import Util.OrmUtil.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AssociationModelRepository {
    public int save(AssociationModel associationModel) {
        EntityTransaction transaction = null;
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            if (associationModel.getId() == 0) {
                em.persist(associationModel);
            } else {
                em.merge(associationModel);
            }

            transaction.commit();
            return associationModel.getId();

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
