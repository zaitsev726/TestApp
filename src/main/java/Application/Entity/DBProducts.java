package Application.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DBProducts {
    @Id
    @GeneratedValue(generator = "products_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "products_generator", sequenceName = "Products_generator", allocationSize = 1)
    private int id_product;

    @Column
    private String product_title;

    @Column
    private float price;

    @OneToMany(mappedBy = "DBProducts", fetch = FetchType.LAZY)
    private List<DBPurchases> DBPurchasesListForProduct = new ArrayList<>();

    public void addPurchase(DBPurchases DBPurchases) {
        addPurchase(DBPurchases, true);
    }

    void addPurchase(DBPurchases DBPurchases, boolean set) {
        if (DBPurchases != null) {
            if (getDBPurchasesListForProduct().contains(DBPurchases)) {
                DBPurchasesListForProduct.set(DBPurchasesListForProduct.indexOf(DBPurchases), DBPurchases);
            } else
                DBPurchasesListForProduct.add(DBPurchases);

            if (set) {
                DBPurchases.setProduct(this, false);
            }
        }
    }

    public List<DBPurchases> getDBPurchasesListForProduct() {
        return DBPurchasesListForProduct;
    }

    public void setDBPurchasesListForProduct(List<DBPurchases> DBPurchasesListForProduct) {
        this.DBPurchasesListForProduct = DBPurchasesListForProduct;
    }

    public DBProducts() {
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id_product=" + id_product +
                ", product_title='" + product_title + '\'' +
                ", price=" + price +
                '}';
    }
}
