package DataLayer;

import Model.MenuItem;
import Model.Restaurant;

import java.io.*;
import java.util.Set;

public class RestaurantSerializator {
    public void writeObject(Restaurant restaurant) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("restaurant.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(restaurant);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Restaurant readObject(String fileToDeserializeFrom) {
        Restaurant restaurant = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(fileToDeserializeFrom);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            restaurant = (Restaurant) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return restaurant;
    }
}
