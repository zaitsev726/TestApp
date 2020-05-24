package Application.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(generator = "customers_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "customers_generator", sequenceName = "Customers_generator", allocationSize = 1)
    private int id_customer;

    @Column
    private String name;

    @Column
    private String surname;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Purchase> PurchaseListForCustomer = new ArrayList<>();

    public void addPurchase(Purchase purchase) {
        addPurchase(purchase, true);
    }

    void addPurchase(Purchase purchase, boolean set) {
        if (purchase != null) {
            if (PurchaseListForCustomer.contains(purchase)) {
                PurchaseListForCustomer.set(PurchaseListForCustomer.indexOf(purchase), purchase);
            } else
                PurchaseListForCustomer.add(purchase);

            if (set) {
                purchase.setCustomer(this, false);
            }
        }
    }

    public List<Purchase> getPurchaseListForCustomer() {
        return PurchaseListForCustomer;
    }

    public void setPurchaseListForCustomer(List<Purchase> PurchaseListForCustomer) {
        this.PurchaseListForCustomer = PurchaseListForCustomer;
    }

    public Customer() {
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id_customer == customer.id_customer &&
                Objects.equals(name, customer.name) &&
                Objects.equals(surname, customer.surname) &&
                Objects.equals(PurchaseListForCustomer, customer.PurchaseListForCustomer);
    }

}
