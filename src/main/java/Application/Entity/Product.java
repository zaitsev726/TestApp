package Application.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(generator = "products_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "products_generator", sequenceName = "Products_generator", allocationSize = 1)
    private int id_product;

    @Column
    private String product_title;

    @Column
    private float price;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Purchase> PurchaseListForProduct = new ArrayList<>();

    public void addPurchase(Purchase purchase) {
        addPurchase(purchase, true);
    }

    void addPurchase(Purchase purchase, boolean set) {
        if (purchase != null) {
            if (getPurchaseListForProduct().contains(purchase)) {
                PurchaseListForProduct.set(PurchaseListForProduct.indexOf(purchase), purchase);
            } else
                PurchaseListForProduct.add(purchase);

            if (set) {
                purchase.setProduct(this, false);
            }
        }
    }

    public List<Purchase> getPurchaseListForProduct() {
        return PurchaseListForProduct;
    }

    public void setPurchaseListForProduct(List<Purchase> DBPurchasesListForProduct) {
        this.PurchaseListForProduct = PurchaseListForProduct;
    }

    public Product() {
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
