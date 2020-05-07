package Model;

import java.util.LinkedList;
import java.util.List;

public interface IRestauratProcessing {
    /**
     * @param menuItem: The menu item to be added
     * @pre @forall k:[0 ... menu.size()-1 @ menuItem.name != menu.get(k).name
     * @pre menuItem!=null
     * @pre if(menuItem instanceOf BaseProduct) menuItem.price>0
     * @invariant iswellFormed()==true
     * @post menu.size()@pre+1==menu.size()
     */
    public void createMenuItem(MenuItem menuItem);

    /**
     * @param menuItem: the Menu item to be deleted
     * @pre menuItem!=null
     * @pre menu.contains(menuItem)
     * @invariant isWellFormed()==true
     * @post menu.size()@pre>menu.size()
     * @post !menu.contains(menuItem)
     */
    public void deleteMenuItem(MenuItem menuItem);

    /**
     * @param oldMenuItem: the Menu item to be updated
     * @param newMenuItem: the updated Menu item
     * @pre newMenuItem!=null
     * @pre oldMenuItem!=null
     * @pre menu.contains(oldMenuItem)
     * @pre !menu.contains(newMenuItem)
     * @invariant isWellFormed()==true
     * @post !menu.contains(oldMenuItem)
     * @post menu.contains(newMenuItem)
     */
    public void editMenuItem(MenuItem oldMenuItem,MenuItem newMenuItem);

    /**
     * @param order:the     new order
     * @param menuItemList: the items ordered in the order
     * @pre order!=null
     * @pre For each k: [1 ... menuItemsList.size()-1] menu.contains(menuItemList.get(k)
     * @pre !menuItemList.isEmpty();
     * @pre !orders.contains(order)
     * @post orders.contains(order)
     * @invariant isWellFormed()
     */
    public void createNewOrder(Order order, LinkedList<MenuItem> menuItemList);

    /**
     * @param order: the order for which we want to compute the price
     * @pre orders.keySet().contains(order)
     * @post No Change
     */
    public Double computePriceOrder(Order order);

    /**
     * @param order
     * @pre orders.containsKey(order)
     * @invariant isWellFormed
     */
    public void generateBill(Order order);
}
