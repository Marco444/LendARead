package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.UserReviewsDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
public class UserReviewsDaoJpa implements UserReviewsDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addReview(final UserReview newReview) {
        em.persist(newReview);
    }

    @Override
    public double getRating(final User user) {
        try {
            String jql = "SELECT AVG(r.rating) FROM UserReview r WHERE r.recipientId = :userId";
            return (Double) em.createQuery(jql)
                    .setParameter("userId", user.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0.0;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserReview> getUserReviewsAsRecipient(final User recipient) {
        String jql = "SELECT r FROM UserReview r WHERE r.recipientId = :userId";
        return (List<UserReview>) em.createQuery(jql).setParameter("userId", recipient.getId()).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserReview> getUserReviewsAsReviewer(final User reviewer) {
        String jql = "SELECT r FROM UserReview r WHERE r.reviewerId = :userId";
        return (List<UserReview>) em.createQuery(jql).setParameter("userId", reviewer.getId()).getResultList();
    }
}
