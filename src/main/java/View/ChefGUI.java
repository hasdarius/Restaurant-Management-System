package View;

import Controller.Controller;
import Model.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class ChefGUI extends JFrame implements Observer, ActionListener {
    Controller controller;
    private JLabel title;
    private JButton back;
    private JScrollPane scrollPaneOrders;
    private JTable orders;
    private JTextArea newOrders;
    private JButton showOrders, orderFinalized;
    private JLabel textForNewOrders;
    private DefaultTableModel tableModel;
    private Integer nrOfRows;

    public ChefGUI(Controller controller) {
        this.getContentPane().setLayout(null);
        this.controller = controller;
        nrOfRows=0;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(controller.getInitialGUI().getBounds());
        title = new JLabel("Chef");
        title.setFont(new Font("Tisa", Font.ITALIC, 25));
        title.setBounds(400, 60, 200, 100);
        getContentPane().add(title);


        back = new JButton("Back");
        textForNewOrders=new JLabel("The newest order that needs to be cooked:");
        showOrders = new JButton("Show Orders");
        orderFinalized = new JButton("Finalized an order!");
        newOrders = new JTextArea("Here a new Order will be displayed!");
        newOrders.setLineWrap(true);
        newOrders.setWrapStyleWord(true);
        newOrders.setEditable(false);
        scrollPaneOrders = new JScrollPane();
        scrollPaneOrders.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneOrders.setVisible(false);
        String[] columns = {"Order Number", "Ingredients"};
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columns);
        orders = new JTable(tableModel);
        scrollPaneOrders.setViewportView(orders);

        back.setBounds(800, 900, 150, 50);
        showOrders.setBounds(100, 900, 150, 50);
        orderFinalized.setBounds(450, 900, 150, 50);
        textForNewOrders.setBounds(100,150,300,50);
        newOrders.setBounds(100, 200, 300, 400);
        scrollPaneOrders.setBounds(450, 200, 500, 400);


        back.addActionListener(this);
        orderFinalized.addActionListener(this);
        showOrders.addActionListener(this);

        getContentPane().add(back);
        getContentPane().add(newOrders);
        getContentPane().add(showOrders);
        getContentPane().add(orderFinalized);
        getContentPane().add(scrollPaneOrders);
        getContentPane().add(textForNewOrders);
    }

    @Override
    public void update(Observable o, Object arg) {
        LinkedList<MenuItem> ingredients = (LinkedList<MenuItem>) arg;
        StringBuilder stringBuilder = new StringBuilder();
        for (MenuItem ingredient : ingredients) {
            stringBuilder.append(ingredient.getName()).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        newOrders.setText(stringBuilder.toString());
        nrOfRows++;
        tableModel.addRow(new String[]{nrOfRows.toString(), String.valueOf(stringBuilder)});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == back) {
            this.setVisible(false);
            controller.getInitialGUI().setVisible(true);
        }
        if (source == showOrders) {
            scrollPaneOrders.setVisible(true);
        } else {
            scrollPaneOrders.setVisible(false);
        }
        if(source==orderFinalized){
            tableModel.removeRow(0);
        }
    }
}
