package Application.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(generator = "purchases_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "purchases_generator", sequenceName = "Purchases_generator", allocationSize = 1)
    private int id_purchase;

    @Column(name = "id_product")
    private int id_product;

    @Column(name = "id_customer")
    private int id_customer;


    @Column
    private Date purchase_date;

    @Column
    private int quantity;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_customer", updatable = false, nullable = false, insertable = false)
    private Customer customer;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_product", updatable = false, nullable = false, insertable = false)
    private Product product;

    public void setCustomer(Customer customer) {
        setCustomer(customer, true);
    }

    void setCustomer(Customer customer, boolean add) {
        this.customer = customer;
        if (customer != null && add) {
            customer.addPurchase(this, false);
        }
    }

    public Customer getCustomers() {
        return customer;
    }

    public void setProduct(Product product) {
        setProduct(product, true);
    }

    void setProduct(Product product, boolean add) {
        this.product = product;
        if (product != null && add) {
            product.addPurchase(this, false);
        }
    }

    public Product getProduct() {
        return product;
    }


    public Purchase() {
    }

    public int getId_purchase() {
        return id_purchase;
    }

    public void setId_purchase(int id_purchase) {
        this.id_purchase = id_purchase;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id_purchase=" + id_purchase +
                //   ", id_customer=" + id_customer +
                //    ", id_product=" + id_product +
                ", purchase_date=" + purchase_date +
                ", quantity=" + quantity +
                '}';
    }
}
