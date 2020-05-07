package View;

import Controller.Controller;
import Model.BaseProduct;
import Model.CompositeProduct;
import Model.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministratorGUI extends JFrame implements ActionListener {
    private Controller controller;
    private JButton back;
    private JLabel title;
    private JButton addBaseProduct;
    private JButton editBaseProduct;
    private JButton deleteBaseProduct;
    private JButton compositeProduct;
    private JButton seeMenu;
    private JLabel name;
    private JLabel price;
    private JTextField nameBox;
    private JTextField priceBox;
    private JLabel newName;
    private JLabel newPrice;
    private JTextField newNameBox;
    private JTextField newPriceBox;
    private JScrollPane scrollPane;
    private JTable menu;

    public AdministratorGUI(Controller controller) {
        this.getContentPane().setLayout(null);
        this.controller = controller;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(controller.getInitialGUI().getBounds());
        title = new JLabel("Administrator");
        title.setFont(new Font("Tisa", Font.ITALIC, 25));
        title.setBounds(400, 60, 200, 100);
        getContentPane().add(title);

        back = new JButton("Back");
        back.setBounds(800, 900, 150, 50);
        getContentPane().add(back);
        back.addActionListener(this);

        name = new JLabel("Name: ");
        price = new JLabel("Price: ");
        nameBox = new JTextField();
        priceBox = new JTextField();
        newName = new JLabel("(For Edit) New Name: ");
        newPrice = new JLabel("(For Edit) New Price: ");
        newNameBox = new JTextField();
        newPriceBox = new JTextField();
        addBaseProduct = new JButton("Add base Product");
        editBaseProduct = new JButton("Edit base Product");
        deleteBaseProduct = new JButton("Delete any Product");
        compositeProduct = new JButton("Add/Edit Composite Products");
        seeMenu = new JButton("See Menu");


        name.setBounds(50, 300, 100, 50);
        price.setBounds(50, 400, 100, 50);
        nameBox.setBounds(150, 300, 100, 50);
        priceBox.setBounds(150, 400, 100, 50);
        newName.setBounds(25, 500, 150, 50);
        newPrice.setBounds(25, 600, 150, 50);
        newNameBox.setBounds(150, 500, 100, 50);
        newPriceBox.setBounds(150, 600, 100, 50);
        addBaseProduct.setBounds(450, 800, 150, 50);
        editBaseProduct.setBounds(800, 800, 150, 50);
        seeMenu.setBounds(100, 800, 150, 50);
        compositeProduct.setBounds(100, 900, 150, 50);
        deleteBaseProduct.setBounds(450, 900, 150, 50);


        scrollPane = new JScrollPane();
        scrollPane.setBounds(300, 275, 650, 400);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setVisible(false);

        compositeProduct.addActionListener(this);
        addBaseProduct.addActionListener(this);
        editBaseProduct.addActionListener(this);
        deleteBaseProduct.addActionListener(this);
        seeMenu.addActionListener(this);


        getContentPane().add(scrollPane);
        getContentPane().add(name);
        getContentPane().add(price);
        getContentPane().add(nameBox);
        getContentPane().add(priceBox);
        getContentPane().add(addBaseProduct);
        getContentPane().add(editBaseProduct);
        getContentPane().add(deleteBaseProduct);
        getContentPane().add(compositeProduct);
        getContentPane().add(seeMenu);
        getContentPane().add(newName);
        getContentPane().add(newPrice);
        getContentPane().add(newNameBox);
        getContentPane().add(newPriceBox);
    }

    public void defineMenuTable() {
        String[] columns = {"Name", "Price", "Type", "Ingredients"};
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columns);
        String[] rows = new String[4];
        if (!controller.getRestaurant().getMenu().isEmpty()) {
            for (Model.MenuItem item : controller.getRestaurant().getMenu()) {
                rows[0] = item.getName();
                rows[1] = item.computePrice().toString();
                if (item instanceof BaseProduct) {
                    rows[2] = "Base Product";
                    rows[3] = "-";
                } else if (item instanceof CompositeProduct) {
                    rows[2] = "Composite Product";
                    rows[3] = "";
                    for (MenuItem ingredient : ((CompositeProduct) item).getMenuItemSet()) {
                        rows[3] = rows[3].concat(ingredient.getName() + ", ");
                    }
                    rows[3] = rows[3].substring(0, rows[3].lastIndexOf(","));
                }
                tableModel.addRow(rows);
            }
        }
        menu = new JTable(tableModel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == back) {
            this.setVisible(false);
            controller.getInitialGUI().setVisible(true);
        }
        if (source == seeMenu) {
            defineMenuTable();
            scrollPane.setViewportView(menu);
            scrollPane.setVisible(true);
        } else {
            scrollPane.setVisible(false);
        }
        if (source == compositeProduct) {
            this.setVisible(false);
            controller.getCompositeProductGUI().setVisible(true);
        }
        if (source == addBaseProduct) {
            controller.createMenuItem(nameBox.getText(), priceBox.getText(), null);
        }
        if (source == deleteBaseProduct) {
            controller.deleteMenuItem(nameBox.getText());
        }
        if (source == editBaseProduct) {
            controller.editMenuItem(nameBox.getText(), newNameBox.getText(), newPriceBox.getText(), null);
        }
    }
}