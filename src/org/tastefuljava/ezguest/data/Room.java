package org.tastefuljava.ezguest.data;

public class Room implements Comparable<Room> {
    private int id;
    private Hotel hotel;
    private int number;
    private RoomType type;

    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }

    public Hotel getHotel() {
        return hotel;
    }

    void setHotel(Hotel newValue) {
        hotel = newValue;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int newValue) {
        number = newValue;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType newValue) {
        if (type != null) {
            type.getRooms().remove(this);
        }
        type = newValue;
        if (type != null) {
            type.getRooms().add(this);
        }
    }

    @Override
    public int compareTo(Room other) {
        if (hotel.getId() < other.hotel.getId()) {
            return -1;
        } else if (hotel.getId() > other.hotel.getId()) {
            return 1;
        } else if (number < other.number) {
            return -1;
        } else if (number > other.number) {
            return 1;
        } else {
            return 0;
        }
    }
}
