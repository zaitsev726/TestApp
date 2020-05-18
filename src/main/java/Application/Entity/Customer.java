package Application.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(generator = "customers_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "customers_generator", sequenceName = "Customers_generator", allocationSize = 1)
    private Integer id_customer;

    @Column
    private String name;

    @Column
    private String surname;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Purchase> purchaseListForCustomer = new ArrayList<>();

    public void addPurchase(Purchase purchase) {
        addPurchase(purchase, true);
    }

    void addPurchase(Purchase purchase, boolean set) {
        if (purchase != null) {
            if (getPurchaseListForCustomer().contains(purchase)) {
                purchaseListForCustomer.set(purchaseListForCustomer.indexOf(purchase), purchase);
            } else
                purchaseListForCustomer.add(purchase);

            if (set) {
                purchase.setCustomer(this, false);
            }
        }
    }

    public List<Purchase> getPurchaseListForCustomer() {
        return purchaseListForCustomer;
    }

    public void setPurchaseListForCustomer(List<Purchase> purchaseListForCustomer) {
        this.purchaseListForCustomer = purchaseListForCustomer;
    }

    public Customer() {
    }

    public Integer getId_customer() {
        return id_customer;
    }

    public void setId_customer(Integer id_customer) {
        this.id_customer = id_customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id_customer=" + id_customer +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
