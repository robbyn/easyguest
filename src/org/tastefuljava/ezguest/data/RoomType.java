package org.tastefuljava.ezguest.data;

import java.util.HashSet;
import java.util.Set;

public class RoomType {
    private int id;
    private String name;
    private String description;
    private double basePrice;
    private final Set<Room> rooms = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String newValue) {
        name = newValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String newValue) {
        description = newValue;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double newValue) {
        basePrice = newValue;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    @Override
    public int hashCode() {
        return id & 0x7FFFFFFF;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RoomType)) {
            return false;
        }
        return id == ((RoomType)other).id;
    }
}
