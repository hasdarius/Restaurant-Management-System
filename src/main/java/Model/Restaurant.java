package Model;

import DataLayer.MyFileWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Restaurant extends Observable implements IRestauratProcessing, Serializable {

    transient private Map<Order, LinkedList<MenuItem>> orders;
    private Set<MenuItem> menu;

    public Restaurant() {
        orders = new HashMap<>();
        menu = new LinkedHashSet<>();
    }

    public void setOrders(Map<Order, LinkedList<MenuItem>> orders) {
        this.orders = orders;
    }

    public Set<MenuItem> getMenu() {
        return menu;
    }

    public Map<Order, LinkedList<MenuItem>> getOrders() {
        return orders;
    }

    public boolean isWellFormed() {
        if (menu != null) {
            for (Map.Entry<Order, LinkedList<MenuItem>> ordersIterator : orders.entrySet()) {
                List<MenuItem> order = ordersIterator.getValue();
                for (MenuItem orderIterator : order) {
                    if (!menu.contains(orderIterator))
                        return false;
                }
            }
        } else if (orders != null)
            return false;
        return true;

    }

    public Order getOrder(int number) {
        if (orders.keySet().isEmpty())
            return null;
        for (Order order : orders.keySet())
            if (order.OrderID == number)
                return order;
        return null;
    }

    public boolean checkItemContains(MenuItem menuItem, MenuItem toDelete) {
        if (menuItem instanceof CompositeProduct) {
            if (((CompositeProduct) menuItem).getMenuItemSet().contains(toDelete))
                return true;
            else {
                for (MenuItem item : ((CompositeProduct) menuItem).getMenuItemSet()) {
                    if (checkItemContains(item, toDelete))
                        return true;
                }
            }
        }
        return menuItem == toDelete;
    }

    public void updateCompositeProduct(MenuItem menuItem, MenuItem toUpdate, MenuItem updated) {
        if (menuItem instanceof CompositeProduct) {
            if (((CompositeProduct) menuItem).getMenuItemSet().contains(toUpdate)) {
                ((CompositeProduct) menuItem).getMenuItemSet().remove(menuItem);
                ((CompositeProduct) menuItem).getMenuItemSet().add(updated);
            } else {
                for (MenuItem item : ((CompositeProduct) menuItem).getMenuItemSet()) {
                    updateCompositeProduct(item, toUpdate, updated);
                }
            }
        }
    }


    public MenuItem getMenuItem(String name) {
        if (menu.isEmpty())
            return null;
        for (MenuItem menuItem : this.getMenu()) {
            if (menuItem.getName().equalsIgnoreCase(name))
                return menuItem;
        }
        return null;
    }


    public void createMenuItem(MenuItem menuItem) {
        assert menuItem != null;
        for (MenuItem item : menu) {
            assert !(menuItem.getName().equalsIgnoreCase(item.getName()));
        }
        assert !(menuItem instanceof BaseProduct) || menuItem.getPrice() > 0;
        int preSize = menu.size() + 1;
        menu.add(menuItem);
        assert isWellFormed();
        assert preSize == menu.size();
    }


    public void deleteMenuItem(MenuItem menuItem) {
        assert menuItem != null;
        assert menu.contains(menuItem);
        int preSize = menu.size();
        List<MenuItem> toRemove = new LinkedList<>();
        for (MenuItem item : menu) {
            if (checkItemContains(item, menuItem)) {
                toRemove.add(item);
                if (!orders.isEmpty()) {
                    List<Order> ordersToDelete = new LinkedList<>();
                    for (Order order : orders.keySet()) {
                        for (MenuItem itemOrdered : orders.get(order)) {
                            if (checkItemContains(itemOrdered, item)) {
                                ordersToDelete.add(order);
                                break;
                            }
                        }
                    }
                    orders.keySet().removeAll(ordersToDelete);
                }
            }
        }
        menu.removeAll(toRemove);
        assert isWellFormed();
        assert !menu.contains(menuItem);
        assert preSize > menu.size();
    }


    public void editMenuItem(MenuItem oldMenuItem, MenuItem newMenuItem) {
        assert oldMenuItem != null;
        assert newMenuItem != null;
        assert menu.contains(oldMenuItem);
        assert !menu.contains(newMenuItem);
        if (menu.contains(oldMenuItem)) {
            menu.remove(oldMenuItem);
            menu.add(newMenuItem);
        }
        for (Order order : orders.keySet()) {
            for (MenuItem item : orders.get(order)) {
                if (item instanceof CompositeProduct) {
                    updateCompositeProduct(item, oldMenuItem, newMenuItem);
                }
            }
        }
        assert isWellFormed();
        assert !menu.contains(oldMenuItem);
        assert menu.contains(newMenuItem);
    }


    public void createNewOrder(Order order, LinkedList<MenuItem> menuItemList) {
        assert order != null;
        assert !menuItemList.isEmpty();
        assert !orders.containsKey(order);
        for (MenuItem item : menuItemList) {
            assert menu.contains(item);
        }
        orders.put(order, menuItemList);
        for (MenuItem item : menuItemList) {
            if (item instanceof CompositeProduct) {
                setChanged();
                notifyObservers(orders.get(order));
                break;
            }
        }
        assert isWellFormed();
        assert orders.containsKey(order);
    }


    public Double computePriceOrder(Order order) {
        Double totalPrice = 0D;
        assert order != null;
        assert orders.keySet().contains(order);
        for (MenuItem iterator : orders.get(order)) {
            totalPrice += iterator.computePrice();
        }
        return totalPrice;
    }


    public void generateBill(Order order) {
        assert orders.containsKey(order);
        assert isWellFormed();
      new MyFileWriter().generateBill(order.OrderID,order.date,order.table,orders.get(order),this.computePriceOrder(order));

    }
}
