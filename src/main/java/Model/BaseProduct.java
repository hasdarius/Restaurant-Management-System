package Model;

public class BaseProduct extends MenuItem {
    public BaseProduct(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Double computePrice() {
        return this.getPrice();
    }
}
