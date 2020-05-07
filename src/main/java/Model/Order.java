package Model;

import java.sql.Timestamp;

public class Order {
    public int OrderID;
    public Timestamp date;
    public int table;

    public Order(int orderID, int table) {
        OrderID = orderID;
        date = new Timestamp(System.currentTimeMillis());
        this.table = table;
    }

    @Override
    public int hashCode() {
        return 2 + 23 * OrderID + 13 * date.getNanos() + 31 * table;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        if (this.OrderID != ((Order) obj).OrderID || this.date.getTime() != ((Order) obj).date.getTime() || this.table != ((Order) obj).table)
            return false;
        return true;
    }
}
