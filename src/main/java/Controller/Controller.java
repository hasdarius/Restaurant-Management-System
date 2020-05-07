package Controller;


import DataLayer.RestaurantSerializator;
import Model.*;
import View.*;

import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Controller {
    private AdministratorGUI administratorGUI;
    private ChefGUI chefGUI;
    private WaiterGUI waiterGUI;
    private CompositeProductGUI compositeProductGUI;
    private InitialUI initialGUI;
    private Restaurant restaurant;
    private RestaurantSerializator restaurantSerializator;

    public Controller(String fileToDeserializeFrom) {
        restaurantSerializator = new RestaurantSerializator();
        restaurant = restaurantSerializator.readObject(fileToDeserializeFrom);
        restaurant.setOrders(new HashMap<>());
        initialGUI = new InitialUI(this);
        initialGUI.setVisible(true);
        administratorGUI = new AdministratorGUI(this);
        chefGUI = new ChefGUI(this);
        waiterGUI = new WaiterGUI(this);
        compositeProductGUI = new CompositeProductGUI(this);
        restaurant.addObserver(chefGUI);

    }

    public Controller() {

    }

    public InitialUI getInitialGUI() {
        return initialGUI;
    }

    public AdministratorGUI getAdministratorGUI() {
        return administratorGUI;
    }

    public ChefGUI getChefGUI() {
        return chefGUI;
    }

    public WaiterGUI getWaiterGUI() {
        return waiterGUI;
    }

    public CompositeProductGUI getCompositeProductGUI() {
        return compositeProductGUI;
    }

    public void invalidOrder() {
        JOptionPane.showMessageDialog(null, "Please introduce a valid order!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void invalidMenuItem() {
        JOptionPane.showMessageDialog(null, "Please introduce a valid menu item!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void nonExistentMenuItem() {
        JOptionPane.showMessageDialog(null, "This Menu item does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void nonExistentOrder() {
        JOptionPane.showMessageDialog(null, "This Order does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void alreadyExistsMenuItem() {
        JOptionPane.showMessageDialog(null, "A Menu item with this name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void alreadyExistsOrder() {
        JOptionPane.showMessageDialog(null, "An order with this ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void createMenuItem(String name, String price, List<String> ingredients) {
        if (restaurant.getMenuItem(name) != null) {
            alreadyExistsMenuItem();
            return;
        }
        MenuItem newMenuItem;
        if (ingredients == null) {
            if (name.equals("") || price.equals("") || !(price.matches("[1-9](\\d+)?(\\.\\d+)?"))) {
                invalidMenuItem();
                return;
            }
            newMenuItem = new BaseProduct(name, Double.parseDouble(price));
        } else {
            if (name.equals("")) {
                invalidMenuItem();
                return;
            }
            for (String ingredient : ingredients) {
                if (restaurant.getMenuItem(ingredient) == null) {
                    invalidMenuItem();
                    return;
                }
            }
            newMenuItem = new CompositeProduct(name);
            for (String ingredient : ingredients) {
                ((CompositeProduct) newMenuItem).addIngredient(restaurant.getMenuItem(ingredient));
            }
        }
        restaurant.createMenuItem(newMenuItem);
        JOptionPane.showMessageDialog(null, "Item created successfully!", "Added",  JOptionPane.INFORMATION_MESSAGE);
        restaurantSerializator.writeObject(this.getRestaurant());
    }

    public void deleteMenuItem(String name) {
        MenuItem itemToDelete;
        itemToDelete = restaurant.getMenuItem(name);
        if (itemToDelete != null) {
            restaurant.deleteMenuItem(itemToDelete);
            JOptionPane.showMessageDialog(null, "Item deleted successfully!", "deleted",  JOptionPane.INFORMATION_MESSAGE);
            restaurantSerializator.writeObject(this.getRestaurant());
        } else {
            nonExistentMenuItem();
        }


    }

    public void editMenuItem(String oldName, String newName, String newPrice, List<String> newIngredients) {
        MenuItem itemToEdit = restaurant.getMenuItem(oldName);
        if (itemToEdit == null) {
            nonExistentMenuItem();
            return;
        }
        MenuItem newItem;
        newItem = itemToEdit;
        if (!newName.equals(""))
            newItem.setName(newName);
        if (!newPrice.equals("")) {
            if (!(newPrice.matches("[1-9](\\d+)?(\\.\\d+)?"))) {
                invalidMenuItem();
                return;
            }
            newItem.setPrice(Double.parseDouble(newPrice));
        }
        if (itemToEdit instanceof CompositeProduct && newIngredients != null) {
            for (String ingredient : newIngredients) {
                if (restaurant.getMenuItem(ingredient) == null) {
                    invalidMenuItem();
                    return;
                }
            }
            ((CompositeProduct) newItem).deleteIngredients();
            for (String ingredient : newIngredients) {
                ((CompositeProduct) newItem).addIngredient(restaurant.getMenuItem(ingredient));
            }
        }
        newItem.setPrice(newItem.computePrice());
        restaurant.editMenuItem(itemToEdit, newItem);
        JOptionPane.showMessageDialog(null, "Item edited successfully!", "Edited",  JOptionPane.INFORMATION_MESSAGE);
        restaurantSerializator.writeObject(this.getRestaurant());
    }

    public void createNewOrder(String id, String table, List<String> itemsOrdered) {
        if (!(id.matches("[1-9](\\d+)?")) || !(table.matches("[1-9](\\d+)?"))) {
            invalidOrder();
            return;
        }

        if (itemsOrdered == null) {
            invalidOrder();
            return;
        }
        for (String menuItem : itemsOrdered)
            if (restaurant.getMenuItem(menuItem) == null) {
                invalidOrder();
                return;
            }
        if (restaurant.getOrder(Integer.parseInt(id)) != null) {
            alreadyExistsOrder();
            return;
        }
        Order newOrder = new Order(Integer.parseInt(id), Integer.parseInt(table));
        LinkedList<MenuItem> orders = new LinkedList<>();
        for (String menuItem : itemsOrdered) {
            orders.add(restaurant.getMenuItem(menuItem));
        }
        restaurant.createNewOrder(newOrder, orders);
        JOptionPane.showMessageDialog(null, "Order created successfully!", "Added",  JOptionPane.INFORMATION_MESSAGE);
    }

    public Double computePriceOrder(String id) {
        if (!(id.matches("[1-9](\\d+)?"))) {
            invalidOrder();
            return null;
        }
        Order order = restaurant.getOrder(Integer.parseInt(id));
        if (order == null) {
            nonExistentOrder();
            return null;
        }
        JOptionPane.showMessageDialog(null, "The price for the order: " + id + " is " + restaurant.computePriceOrder(order) + ".\n Thank you!", "Price", JOptionPane.INFORMATION_MESSAGE);
        return restaurant.computePriceOrder(order);
    }

    public void generateBill(String id) {
        if (!(id.matches("[1-9](\\d+)?"))) {
            invalidOrder();
            return;
        }
        Order order = restaurant.getOrder(Integer.parseInt(id));
        if (order == null) {
            nonExistentOrder();
            return;
        }
        JOptionPane.showMessageDialog(null, "The price for the order: " + id + " was generated.\n Thank you!", "Price", JOptionPane.INFORMATION_MESSAGE);
        restaurant.generateBill(order);


    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}
