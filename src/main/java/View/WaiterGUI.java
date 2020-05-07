package View;

import Controller.Controller;
import Model.BaseProduct;
import Model.CompositeProduct;
import Model.MenuItem;
import Model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaiterGUI extends JFrame implements ActionListener {
    private Controller controller;
    private JLabel title;
    private JButton back;
    private JButton menu, addItems, seeOrders, createOrder, generateBill, computePrice;
    private JLabel orderID, table;
    private JTextField orderIDBox, tableBox;
    private DefaultListModel<String> menuItemsModel;
    private JList<String> menuItems;
    private JScrollPane scrollPaneMenu, scrollPaneOrders,scrollPaneItemsSelected;
    private JTextArea itemsSelected;
    private JTable orders;

    public WaiterGUI(Controller controller) {
        this.getContentPane().setLayout(null);
        this.controller = controller;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(controller.getInitialGUI().getBounds());
        title = new JLabel("WAITER");
        title.setFont(new Font("Tisa", Font.ITALIC, 25));
        title.setBounds(400, 60, 200, 100);

        menuItemsModel = new DefaultListModel<String>();
        menuItems = new JList<String>(menuItemsModel);
        menuItems.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        scrollPaneMenu = new JScrollPane();
        scrollPaneMenu.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneOrders = new JScrollPane();
        scrollPaneOrders.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneOrders.setVisible(false);
        scrollPaneMenu.setVisible(false);
        scrollPaneItemsSelected=new JScrollPane();
        scrollPaneItemsSelected.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneItemsSelected.setVisible(false);
        itemsSelected = new JTextArea();
        itemsSelected.setLineWrap(true);
        itemsSelected.setWrapStyleWord(true);
        itemsSelected.setEditable(false);
        menu = new JButton("Show menu!");
        addItems = new JButton("Add Items selected!");
        back = new JButton("Back");
        seeOrders = new JButton("See Orders");
        table = new JLabel("Table:");
        orderID = new JLabel("Order ID:");
        orderIDBox = new JTextField();
        tableBox = new JTextField();
        createOrder = new JButton("Create Order");
        generateBill = new JButton("Generate Bill");
        computePrice = new JButton("Compute Price");


        menu.setBounds(50, 725, 150, 50);
        addItems.setBounds(400, 725, 200, 50);
        scrollPaneMenu.setBounds(200, 600, 200, 100);
        scrollPaneOrders.setBounds(350, 200, 600, 400);
        back.setBounds(800, 900, 150, 50);
        scrollPaneItemsSelected.setBounds(750,700,200,100);
        seeOrders.setBounds(50, 200, 150, 50);
        orderID.setBounds(100, 400, 100, 50);
        table.setBounds(100, 500, 100, 50);
        orderIDBox.setBounds(250, 400, 100, 50);
        tableBox.setBounds(250, 500, 100, 50);
        createOrder.setBounds(50, 900, 150, 50);
        computePrice.setBounds(400, 900, 200, 50);
        generateBill.setBounds(50, 300, 150, 50);


        addItems.addActionListener(this);
        back.addActionListener(this);
        menu.addActionListener(this);
        seeOrders.addActionListener(this);
        createOrder.addActionListener(this);
        computePrice.addActionListener(this);
        generateBill.addActionListener(this);


        getContentPane().add(addItems);
        getContentPane().add(menu);
        getContentPane().add(title);
        getContentPane().add(scrollPaneMenu);
        getContentPane().add(scrollPaneOrders);
        getContentPane().add(back);
        getContentPane().add(seeOrders);
        getContentPane().add(orderID);
        getContentPane().add(table);
        getContentPane().add(orderIDBox);
        getContentPane().add(tableBox);
        getContentPane().add(createOrder);
        getContentPane().add(computePrice);
        getContentPane().add(generateBill);
        getContentPane().add(scrollPaneItemsSelected);

    }

    public void showMenu() {
        menuItemsModel.clear();
        if (!controller.getRestaurant().getMenu().isEmpty()) {
            for (MenuItem item : controller.getRestaurant().getMenu()) {
                menuItemsModel.addElement(item.getName());
            }
        }
    }

    public void addItems() {
        for (String newIngredient : menuItems.getSelectedValuesList()) {
            if (itemsSelected.getText().equals("")) {
                itemsSelected.append(newIngredient);
            } else {
                itemsSelected.append("," + newIngredient);
            }
        }
    }

    public void defineOrdersTable() {

        String[] columns = {"OrderID", "Table", "Date", "Items Ordered", "Price"};
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columns);
        String[] rows = new String[5];
        if (!controller.getRestaurant().getOrders().isEmpty()) {
            for (Order order : controller.getRestaurant().getOrders().keySet()) {
                rows[0] = String.valueOf(order.OrderID);
                rows[1] = String.valueOf(order.table);
                rows[2] = String.valueOf(order.date);
                rows[3] = "";
                rows[4] = controller.getRestaurant().computePriceOrder(order).toString();
                for (MenuItem item : controller.getRestaurant().getOrders().get(order)) {
                    rows[3] = rows[3].concat(item.getName() + ",");
                }
                rows[3] = rows[3].substring(0, rows[3].lastIndexOf(","));
                tableModel.addRow(rows);
            }
        }
        orders = new JTable(tableModel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == back) {
            this.setVisible(false);
            controller.getInitialGUI().setVisible(true);
        }
        if (source == menu) {
            itemsSelected.setText("");
            showMenu();
            scrollPaneMenu.setViewportView(menuItems);
            scrollPaneMenu.setVisible(true);

        } else {
            scrollPaneMenu.setVisible(false);
        }
        if (source == seeOrders) {
            defineOrdersTable();
            scrollPaneOrders.setViewportView(orders);
            scrollPaneOrders.setVisible(true);

        } else {
            scrollPaneOrders.setVisible(false);
        }
        if (source == addItems) {

            addItems();
            scrollPaneItemsSelected.setViewportView(itemsSelected);
            scrollPaneItemsSelected.setVisible(true);
        }
        else{
            scrollPaneItemsSelected.setVisible(false);
        }
        if (source == createOrder) {
            List<String> items = new ArrayList<>();
            if (itemsSelected.getText().equals(""))
                items = null;
            else
                items = Arrays.asList(itemsSelected.getText().split(","));
            controller.createNewOrder(orderIDBox.getText(), tableBox.getText(), items);
        }
        if (source == generateBill) {
            controller.generateBill(orderIDBox.getText());
        }
        if (source == computePrice) {
            controller.computePriceOrder(orderIDBox.getText());
        }
    }
}

