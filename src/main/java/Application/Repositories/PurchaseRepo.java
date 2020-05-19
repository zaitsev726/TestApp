package Application.Repositories;

import Application.Entity.Customer;
import Application.Entity.Product;
import Application.Entity.Purchase;

import javax.persistence.*;
import java.util.List;

public class PurchaseRepo {
    EntityManagerFactory emf;

    public PurchaseRepo(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void savePurchase(Purchase purchase) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Customer customer;
        Product product;
        try {
            customer = em.createQuery("select c from Customer c where c.id_customer = :id_customer", Customer.class)
                    .setParameter("id_customer", purchase.getId_customer())
                    .getSingleResult();
            product = em.createQuery("select p from Product p where p.id_product = :id_product",Product.class)
                    .setParameter("id_product", purchase.getId_product())
                    .getSingleResult();
        }catch (NoResultException e){
            return;
        }

        purchase.setCustomer(customer);
        purchase.setProduct(product);

        customer.addPurchase(purchase);
        product.addPurchase(purchase);

        em.persist(product);
        em.merge(customer);
        em.merge(product);
        em.getTransaction().commit();
    }

    public void deletePurchase(long id_purchase) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("delete from Purchase pur where pur.id_purchase = :id");
            query.setParameter("id", id_purchase);
            query.executeUpdate();
            em.getTransaction().commit();
            em.close();
        } catch (RollbackException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public Purchase updatePurchase(Purchase purchase) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            purchase = em.merge(purchase);
            em.getTransaction().commit();
            em.close();
        } catch (RollbackException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        return purchase;
    }

    public Purchase findByIdPurchase(long id_purchase) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select p from Purchase p where p.id_purchase = :id", Purchase.class)
                .setParameter("id", id_purchase)
                .getSingleResult();
    }

    public List<Purchase> findByIdCustomer(long id_customer){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select p from Purchase p where p.id_customer = :id", Purchase.class)
                .setParameter("id", id_customer)
                .getResultList();
    }

    public List<Purchase> findByIdProduct(long id_product){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select p from Purchase p where p.id_product = :id", Purchase.class)
                .setParameter("id", id_product)
                .getResultList();
    }

    public List<Purchase> findAll(){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select p from Purchase p", Purchase.class)
                .getResultList();
    }

}
