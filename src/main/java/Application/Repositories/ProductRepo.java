package Application.Repositories;

import Application.Entity.Product;
import Application.Entity.Purchase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

public class ProductRepo {
    EntityManagerFactory emf;

    public ProductRepo(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void saveProduct(Product product) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    public void deleteProduct(long id_product) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("delete from Product p where p.id_product= :id");
            query.setParameter("id", id_product);
            query.executeUpdate();
            em.getTransaction().commit();
            em.close();
        } catch (RollbackException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public void deleteProduct(String title) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("delete from Product p where p.product_title= :title");
            query.setParameter("title", title);
            query.executeUpdate();
            em.getTransaction().commit();
            em.close();
        } catch (RollbackException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public Product updateProduct(Product product) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            product = em.merge(product);
            em.getTransaction().commit();
            em.close();
        } catch (RollbackException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        return product;
    }

    public Product findByIdProduct(long id_product) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select p from Product p where p.id_product = :id", Product.class)
                .setParameter("id", id_product)
                .getSingleResult();
    }

    public List<Purchase> findPurchases(long id_product){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Product product =  em.createQuery("select p from Product p where p.id_product = :id", Product.class)
                .setParameter("id", id_product)
                .getSingleResult();
        List<Purchase> purchaseList  = product.getPurchaseListForProduct();
        em.getTransaction().commit();
        return purchaseList;
    }
}
