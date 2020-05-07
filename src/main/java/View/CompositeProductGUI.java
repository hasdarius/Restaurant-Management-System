package View;

import Controller.Controller;
import Model.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CompositeProductGUI extends JFrame implements ActionListener {
    Controller controller;
    private JLabel ingredientsToAdd;
    private JLabel title;
    private JButton back;
    private JLabel name, newName;
    private JTextField nameBox, newNameBox;
    private JButton addCompositeItem, updateCompositeItem, addIngredient;
    private TextArea ingredients;
    private JComboBox<String> items;
    private JButton showExistingItems;

    public CompositeProductGUI(Controller controller) {
        this.getContentPane().setLayout(null);
        this.controller = controller;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(controller.getInitialGUI().getBounds());
        title = new JLabel("Composite Products");
        title.setFont(new Font("Tisa", Font.ITALIC, 25));
        title.setBounds(350, 60, 300, 100);
        getContentPane().add(title);

        name = new JLabel("Name: ");
        ingredientsToAdd = new JLabel("Ingredients to add:");
        nameBox = new JTextField();
        newName = new JLabel("(For Edit) New Name: ");
        newNameBox = new JTextField();
        back = new JButton("Back");
        showExistingItems = new JButton("Possible ingredients: ");
        addIngredient = new JButton("Add ingredient");
        addCompositeItem = new JButton("Add Composite Product");
        updateCompositeItem = new JButton("Update Composite Product");
        items = new JComboBox<String>();
        ingredients = new TextArea();
        ingredients.getScrollbarVisibility();
        ingredients.setEditable(false);

        name.setBounds(50, 300, 100, 50);
        nameBox.setBounds(150, 300, 100, 50);
        newName.setBounds(25, 500, 150, 50);
        addIngredient.setBounds(100, 800, 200, 50);
        showExistingItems.setBounds(100, 700, 200, 50);
        items.setBounds(400, 700, 200, 50);
        items.setEditable(false);
        newNameBox.setBounds(150, 500, 100, 50);
        ingredients.setBounds(300, 275, 650, 400);
        ingredientsToAdd.setBounds(450, 200, 200, 100);
        back.setBounds(700, 900, 200, 50);
        addCompositeItem.setBounds(100, 900, 200, 50);
        updateCompositeItem.setBounds(400, 900, 200, 50);

        addCompositeItem.addActionListener(this);
        showExistingItems.addActionListener(this);
        updateCompositeItem.addActionListener(this);
        addIngredient.addActionListener(this);
        back.addActionListener(this);

        getContentPane().add(back);
        getContentPane().add(name);
        getContentPane().add(newName);
        getContentPane().add(nameBox);
        getContentPane().add(newNameBox);
        getContentPane().add(addCompositeItem);
        getContentPane().add(updateCompositeItem);
        getContentPane().add(ingredients);
        getContentPane().add(addIngredient);
        getContentPane().add(showExistingItems);
        getContentPane().add(items);
        getContentPane().add(ingredientsToAdd);
    }

    private void showMenuItems() {
        for (MenuItem menuItem : controller.getRestaurant().getMenu()) {
            items.addItem(menuItem.getName());
        }
    }

    private void addIngredient() {
        if (items.getSelectedItem() != null) {
            if (ingredients.getText().equals(""))
                ingredients.append(items.getSelectedItem().toString());
            else
                ingredients.append("," + items.getSelectedItem());
            items.removeItem(items.getSelectedItem());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == back) {
            ingredients.setText("");
            items.removeAllItems();
            this.setVisible(false);
            controller.getAdministratorGUI().setVisible(true);
        }
        if (source == showExistingItems && ingredients.getText().equals("") && items.getItemAt(0) == null) {
            showMenuItems();
        }
        if (source == addIngredient) {
            addIngredient();
        }
        if (source == addCompositeItem) {
            List<String> newIngredients = new ArrayList<String>();
            if (ingredients.getText().equals(""))
                newIngredients = null;
            else
                newIngredients = Arrays.asList(ingredients.getText().split(","));
            controller.createMenuItem(nameBox.getText(), "", newIngredients);
            ingredients.setText("");

        }
        if (source == updateCompositeItem) {
            List<String> newIngredients = new ArrayList<>();
            if (ingredients.getText().equals(""))
                newIngredients = null;
            else
                newIngredients = Arrays.asList(ingredients.getText().split(","));
            controller.editMenuItem(nameBox.getText(), newNameBox.getText(), "", newIngredients);
        }
    }
}