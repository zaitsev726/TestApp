package Application.Repositories;

import Application.Entity.Customer;
import Application.Entity.Purchase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

public class CustomerRepo {
    EntityManagerFactory emf;

    public CustomerRepo(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void saveCustomer(Customer DBCustomer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(DBCustomer);
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

    public Customer updateCustomer(Customer DBCustomer) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            DBCustomer = em.merge(DBCustomer);
            em.getTransaction().commit();
            em.close();
        } catch (RollbackException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        return DBCustomer;
    }

    public Customer findByIdCustomer(long id_customer) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select c from Customer c where c.id_customer = :id", Customer.class)
                .setParameter("id", id_customer)
                .getSingleResult();
    }

    public List<Purchase> findPurchase(long id_customer){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Customer DBCustomer =  em.createQuery("select c from Customer c where c.id_customer = :id", Customer.class)
                .setParameter("id", id_customer)
                .getSingleResult();
        List<Purchase> DBPurchaseList = DBCustomer.getPurchaseListForCustomer();
        em.getTransaction().commit();
        return DBPurchaseList;
    }

    public List<Customer> findBySurname(String surname){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select c from Customer c where c.surname = :surname", Customer.class)
                .setParameter("surname", surname)
                .getResultList();
    }

    public List<Customer> findAll(){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select c from Customer c", Customer.class)
                .getResultList();
    }

}
