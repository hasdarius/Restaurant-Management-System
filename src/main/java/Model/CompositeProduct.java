package Model;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CompositeProduct extends MenuItem {
    private Set<MenuItem> menuItemList;

    public void deleteIngredients(){
        this.menuItemList.clear();
    }
    public CompositeProduct(String name) {
        this.name = name;
        menuItemList= new LinkedHashSet<MenuItem>();
    }
    public void addIngredient(MenuItem menuItem){
        menuItemList.add(menuItem);
    }

    public Set<MenuItem> getMenuItemSet() {
        return menuItemList;
    }

    public Double computePrice() {
        Double price=0D;
        for(MenuItem iterator:menuItemList){
            price+=iterator.computePrice();
        }
        return price;
    }
}
