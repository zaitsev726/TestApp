package Application.Entity;

import javax.persistence.*;
import java.util.Date;


public class Purchase {
    @Id
    @GeneratedValue(generator = "purchases_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "purchases_generator", sequenceName = "Purchases_generator", allocationSize = 1)
    private Integer id_purchase;

    @Column
    private Integer id_customer;

    @Column
    private Integer id_product;

    @Column
    private Date purchase_date;

    public Purchase(){}

    public Integer getId_purchase() { return id_purchase; }

    public void setId_purchase(Integer id_purchase) { this.id_purchase = id_purchase; }

    public Integer getId_customer() { return id_customer; }

    public void setId_customer(Integer id_customer) { this.id_customer = id_customer; }

    public Integer getId_product() { return id_product; }

    public void setId_product(Integer id_product) { this.id_product = id_product; }

    public Date getPurchase_date() { return purchase_date; }

    public void setPurchase_date(Date purchase_date) { this.purchase_date = purchase_date; }

    @Override
    public String toString() {
        return "Purchase{" +
                "id_purchase=" + id_purchase +
                ", id_customer=" + id_customer +
                ", id_product=" + id_product +
                ", purchase_date=" + purchase_date +
                '}';
    }
}
