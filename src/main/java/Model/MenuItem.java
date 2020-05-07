package Model;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {
    protected String name;
    protected Double price;

    public String getName() {
        return name;
    }
    public Double getPrice() {
        return price;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public abstract Double computePrice();
}
