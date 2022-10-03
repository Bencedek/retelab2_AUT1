package hu.bme.aut.retelab2.repository;

import hu.bme.aut.retelab2.domain.Ad;
import javassist.NotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AdRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Ad save(Ad feedback) {
        return em.merge(feedback);
    }

   public List<Ad> findAll() {
        return em.createQuery("SELECT a FROM Ad a", Ad.class).getResultList();
    }

    public Ad findById(long id) {
        return em.find(Ad.class, id);
    }

    public List<Ad> findByPrice(int minPrice, int maxPrice) {
        return em.createQuery("SELECT a FROM Ad a WHERE a.price > ?1 AND a.price < ?2", Ad.class)
                .setParameter(1, minPrice)
                .setParameter(2, maxPrice)
                .getResultList();
    }

    @Transactional
    public Ad modify(Ad ad) throws NotFoundException{
        Ad toModify = findById(ad.getId());
        if (toModify.getSecretCode().equals(ad.getSecretCode())){
            toModify.setTitle(ad.getTitle());
            toModify.setDescription(ad.getDescription());
            toModify.setPrice(ad.getPrice());
            save(toModify);
            return toModify;
        }
        else throw new NotFoundException("Ad not found");
    }

    @Transactional
    @Scheduled(fixedDelay= 6000)
    public void deleteExpiredAds() {
        List<Ad> ads = findAll();
        for (Ad ad : ads) {
            if(ad.getExpirationDate().isBefore(java.time.LocalDateTime.now())) em.remove(ad);
        }
    }
}
