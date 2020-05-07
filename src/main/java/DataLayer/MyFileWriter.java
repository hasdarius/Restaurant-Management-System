package DataLayer;

import Model.MenuItem;
import Model.Order;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

public class MyFileWriter {
    public void generateBill(Integer orderID, Timestamp date, Integer table, LinkedList<MenuItem> menuItems, Double totalPrice){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BILL FOR THE ORDER NUMBER: ").append(orderID);
        stringBuilder.append("\nTable: ").append(table).append("\nDate: ").append(date.toLocalDateTime());
        for (MenuItem menuItem : menuItems) {
            stringBuilder.append("\nProduct ordered: ").append(menuItem.getName()).append("\tPrice: ").append(menuItem.computePrice());
        }
        stringBuilder.append("\n\nTotal price to pay: ").append(totalPrice);
        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter("bill.txt"));
            myWriter.write(String.valueOf(stringBuilder));
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
