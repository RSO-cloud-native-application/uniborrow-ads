package si.fri.rso.uniborrow.ads.services.beans;

import org.eclipse.microprofile.metrics.annotation.Counted;
import si.fri.rso.uniborrow.ads.models.entities.Ad;
import si.fri.rso.uniborrow.ads.models.entities.TargetAudience;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Random;


@RequestScoped
public class AdDataProviderBean {

    @Inject
    private EntityManager em;

    @Counted
    public Ad getRandomAd() {
        Query countQuery = em.createQuery("SELECT COUNT(ad) from Ad ad");
        long count = (Long) countQuery.getSingleResult();
        Random random = new Random();
        int number = random.nextInt((int) count);
        return em.createQuery("SELECT ad from Ad ad", Ad.class)
                .setFirstResult(number)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Counted
    public Ad getRandomTargetedAd(TargetAudience targetAudience) {
        Query countQuery = em.createQuery("SELECT COUNT(ad) from Ad ad WHERE ad.targetAudience = :value1").setParameter("value1", targetAudience);
        long count = (Long) countQuery.getSingleResult();
        Random random = new Random();
        int number = random.nextInt((int) count);
        return em.createQuery("SELECT ad from Ad ad WHERE ad.targetAudience = :value1", Ad.class)
                .setParameter("value1", targetAudience)
                .setFirstResult(number)
                .setMaxResults(1)
                .getSingleResult();

    }

    public Ad addAd(Ad ad) {
        try {
            beginTx();
            em.persist(ad);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
            return null;
        }
        return ad;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

}