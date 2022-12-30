package application.products;

import java.util.Date;

public class Product {
    private final String id;
    private String name;
    private double price;
    private Date expirationDate;

    public Product(String id, String name, double price, Date expirationDate){
        this.id = id;
        this.name = name;
        this.price = price;
        this.expirationDate = expirationDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Product copy(){// retourne une copie du produit
        return new Product(id, name, price, expirationDate);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Product)){
            return false;
        }

        Product p = (Product)o;
        if(p.getId().equals(((Product) o).getId())){
            return true;
        }

        return false;
    }

    @Override
    public String toString(){
        return "[id : " + id + "][nom : " + name + "][prix : " + price + "][date limite : " + expirationDate + "]";
    }
}
