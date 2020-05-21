package Application.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DBCustomers {
    @Id
    @GeneratedValue(generator = "customers_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "customers_generator", sequenceName = "Customers_generator", allocationSize = 1)
    private int id_customer;

    @Column
    private String name;

    @Column
    private String surname;

    @OneToMany(mappedBy = "DBCustomers", fetch = FetchType.LAZY)
    private List<DBPurchases> DBPurchasesListForCustomer = new ArrayList<>();

    public void addPurchase(DBPurchases DBPurchases) {
        addPurchase(DBPurchases, true);
    }

    void addPurchase(DBPurchases DBPurchases, boolean set) {
        if (DBPurchases != null) {
            if (getDBPurchasesListForCustomer().contains(DBPurchases)) {
                DBPurchasesListForCustomer.set(DBPurchasesListForCustomer.indexOf(DBPurchases), DBPurchases);
            } else
                DBPurchasesListForCustomer.add(DBPurchases);

            if (set) {
                DBPurchases.setCustomer(this, false);
            }
        }
    }

    public List<DBPurchases> getDBPurchasesListForCustomer() {
        return DBPurchasesListForCustomer;
    }

    public void setDBPurchasesListForCustomer(List<DBPurchases> DBPurchasesListForCustomer) {
        this.DBPurchasesListForCustomer = DBPurchasesListForCustomer;
    }

    public DBCustomers() {
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
}
