package Application.Repositories;

import Application.Entity.DBCustomers;
import Application.Entity.DBPurchases;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

public class PurchaseRepo {
    EntityManagerFactory emf;

    public PurchaseRepo(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void savePurchase(DBPurchases DBPurchases) {
      /*  EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Customers customers;
        Products products;
        try {
            customers = em.createQuery("select c from Customers c where c.id_customer = :id_customer", Customers.class)
                    .setParameter("id_customer", purchases.getId_customer())
                    .getSingleResult();
            products = em.createQuery("select p from Products p where p.id_product = :id_product", Products.class)
                    .setParameter("id_product", purchases.getId_product())
                    .getSingleResult();
        }catch (NoResultException e){
            return;
        }

        purchases.setCustomers(customers);
        purchases.setProducts(products);

        customers.addPurchase(purchases);
        products.addPurchase(purchases);

        em.persist(products);
        em.merge(customers);
        em.merge(products);
        em.getTransaction().commit();*/
    }

    public void deletePurchase(long id_purchase) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("delete from DBPurchases pur where pur.id_purchase = :id");
            query.setParameter("id", id_purchase);
            query.executeUpdate();
            em.getTransaction().commit();
            em.close();
        } catch (RollbackException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public DBPurchases updatePurchase(DBPurchases DBPurchases) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            DBPurchases = em.merge(DBPurchases);
            em.getTransaction().commit();
            em.close();
        } catch (RollbackException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        return DBPurchases;
    }

    public DBPurchases findByIdPurchase(long id_purchase) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select p from DBPurchases p where p.id_purchase = :id", DBPurchases.class)
                .setParameter("id", id_purchase)
                .getSingleResult();
    }

    public List<DBPurchases> findByIdCustomer(long id_customer){
        EntityManager em = emf.createEntityManager();
      /*  return em.createQuery("select p from Purchases p where p.id_customer = :id", Purchases.class)
                .setParameter("id", id_customer)
                .getResultList();*/
      return null;
    }

    public List<DBPurchases> findByIdProduct(long id_product){
        EntityManager em = emf.createEntityManager();
        /*return em.createQuery("select p from Purchases p where p.id_product = :id", Purchases.class)
                .setParameter("id", id_product)
                .getResultList();*/
        return null;
    }

    public List<DBPurchases> findAll(){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select p from DBPurchases p", DBPurchases.class)
                .getResultList();
    }

    public List<DBCustomers> findCustomersWithMoreQuantity(int quantity){
       /* EntityManager em = emf.createEntityManager();
        return em.createQuery("select distinct c from Customers c join Purchases p on c.id_customer = p.id_customer where  p.quantity >= :quantity", Customers.class)
                .setParameter("quantity", quantity)
                .getResultList();*/
        return null;
    }

    public List<DBCustomers> findCustomersWithProductAndMoreQuantity(long id_product, long quantity){
       /* EntityManager em = emf.createEntityManager();
        return em.createQuery("select distinct c from Customers c join Purchases p on c.id_customer = p.id_customer where  p.quantity >= :quantity and p.id_product = :id_product", Customers.class)
                .setParameter("quantity", quantity)
                .setParameter("id_product", id_product)
                .getResultList();*/
        return null;
    }

    public int getAllExpenses(long id_customer) {
      /*  EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Purchases> purchases =  em.createQuery("select distinct p from Purchases p where  p.id_customer = :id_customer", Purchases.class)
                .setParameter("id_customer", id_customer)
                .getResultList();
        int totalSum = 0;
        for(Purchases purchase : purchases){
            totalSum += purchase.getQuantity()*purchase.getProducts().getPrice();
        }
        em.getTransaction().commit();*/

      //  return totalSum;
        return 0;
    }
}
