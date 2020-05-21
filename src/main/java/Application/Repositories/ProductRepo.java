package Application.Repositories;

import Application.Entity.DBProducts;
import Application.Entity.DBPurchases;

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

    public void saveProduct(DBProducts DBProducts) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(DBProducts);
        em.getTransaction().commit();
    }

    public void deleteProduct(long id_product) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("delete from DBProducts p where p.id_product= :id");
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
            Query query = em.createQuery("delete from DBProducts p where p.product_title= :title");
            query.setParameter("title", title);
            query.executeUpdate();
            em.getTransaction().commit();
            em.close();
        } catch (RollbackException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public DBProducts updateProduct(DBProducts DBProducts) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            DBProducts = em.merge(DBProducts);
            em.getTransaction().commit();
            em.close();
        } catch (RollbackException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        return DBProducts;
    }

    public DBProducts findByIdProduct(long id_product) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select p from DBProducts p where p.id_product = :id", DBProducts.class)
                .setParameter("id", id_product)
                .getSingleResult();
    }

    public List<DBPurchases> findPurchases(long id_product){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        DBProducts DBProducts =  em.createQuery("select p from DBProducts p where p.id_product = :id", DBProducts.class)
                .setParameter("id", id_product)
                .getSingleResult();
        List<DBPurchases> DBPurchasesList = DBProducts.getDBPurchasesListForProduct();
        em.getTransaction().commit();
        return DBPurchasesList;
    }

    public DBProducts findByTitle(String title){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select p from DBProducts p where p.product_title = :title", DBProducts.class)
                .setParameter("title", title)
                .getSingleResult();
    }

}
