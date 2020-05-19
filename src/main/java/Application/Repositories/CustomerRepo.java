package Application.Repositories;

import Application.Entity.Customer;
import Application.Entity.Purchase;


import javax.persistence.*;
import java.util.List;

public class CustomerRepo {
    EntityManagerFactory emf;

    public CustomerRepo() {
        emf = Persistence.createEntityManagerFactory("model");
    }

    public void saveCustomer(Customer customer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();
    }

    public void deleteCustomer(long id_customer) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("delete from Customer c where c.id_customer= :id");
            query.setParameter("id", id_customer);
            query.executeUpdate();
            em.getTransaction().commit();
            em.close();
        } catch (RollbackException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public void deleteCustomer(String name, String surname) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("delete from Customer c where c.name= :name and c.surname = :surname");
            query.setParameter("name", name);
            query.setParameter("name", surname);
            query.executeUpdate();
            em.getTransaction().commit();
            em.close();
        } catch (RollbackException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public Customer updateCustomer(Customer customer) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            customer = em.merge(customer);
            em.getTransaction().commit();
            em.close();
        } catch (RollbackException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        return customer;
    }

    public Customer findByIdCustomer(long id_customer) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select c from Customer c where c.id_customer = :id", Customer.class)
                .setParameter("id", id_customer)
                .getSingleResult();
    }

    public List<Purchase> findPurchases(long id_customer){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Customer customer =  em.createQuery("select c from Customer c where c.id_customer = :id", Customer.class)
                .setParameter("id", id_customer)
                .getSingleResult();
        List<Purchase> purchaseList  = customer.getPurchaseListForCustomer();
        em.getTransaction().commit();
        return purchaseList;
    }


}
