package Application.Repositories;

import Application.Entity.DBCustomers;
import Application.Entity.DBPurchases;


import javax.persistence.*;
import java.util.List;

public class CustomerRepo {
    EntityManagerFactory emf;

    public CustomerRepo(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void saveCustomer(DBCustomers DBCustomers) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(DBCustomers);
        em.getTransaction().commit();
    }

    public void deleteCustomer(long id_customer) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("delete from DBCustomers c where c.id_customer= :id");
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
            Query query = em.createQuery("delete from DBCustomers c where c.name= :name and c.surname = :surname");
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

    public DBCustomers updateCustomer(DBCustomers DBCustomers) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            DBCustomers = em.merge(DBCustomers);
            em.getTransaction().commit();
            em.close();
        } catch (RollbackException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        return DBCustomers;
    }

    public DBCustomers findByIdCustomer(long id_customer) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select c from DBCustomers c where c.id_customer = :id", DBCustomers.class)
                .setParameter("id", id_customer)
                .getSingleResult();
    }

    public List<DBPurchases> findPurchases(long id_customer){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        DBCustomers DBCustomers =  em.createQuery("select c from DBCustomers c where c.id_customer = :id", DBCustomers.class)
                .setParameter("id", id_customer)
                .getSingleResult();
        List<DBPurchases> DBPurchasesList = DBCustomers.getDBPurchasesListForCustomer();
        em.getTransaction().commit();
        return DBPurchasesList;
    }

    public List<DBCustomers> findBySurname(String surname){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select c from DBCustomers c where c.surname = :surname", DBCustomers.class)
                .setParameter("surname", surname)
                .getResultList();
    }

    public List<DBCustomers> findAll(){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select c from DBCustomers c", DBCustomers.class)
                .getResultList();
    }

}
