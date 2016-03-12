package org.tastefuljava.ezguest.data;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.hibernate.Hibernate;

public class Hotel {
    private int id;
    private String rate;
    private String chain;
    private String name;
    private String address1;
    private String address2;
    private String zip;
    private String city;
    private String state;
    private String country;
    private String telephone;
    private String fax;
    private String email;
    private String web;
    private String logoName;
    private Blob logoBytes;
    private final Map<Integer,Room> rooms = new HashMap<>();

    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }

   public String getRate() {
        return rate;
    }

    public void setRate(String newValue) {
        rate = newValue;
    }
    
    public String getChain() {
        return chain;
    }

    public void setChain(String newValue) {
        chain = newValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String newValue) {
        name = newValue;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String newValue) {
        address1 = newValue;
    }    
    
   public String getAddress2() {
        return address2;
    }

    public void setAddress2(String newValue) {
        address2 = newValue;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String newValue) {
        zip = newValue;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String newValue) {
        city = newValue;
    }

    public String getState() {
        return state;
    }

    public void setState(String newValue) {
        state = newValue;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String newValue) {
        country = newValue;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String newValue) {
        telephone = newValue;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String newValue) {
        fax = newValue;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newValue) {
        email = newValue;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String newValue) {
        web = newValue;
    }
    
    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String newValue) {
        logoName = newValue;
    }    

    public BufferedImage getLogoImage() {
        if (getLogoBytes() == null) {
            return null;
        } else {
            try {
                InputStream in = getLogoBytes().getBinaryStream();
                try {
                    return ImageIO.read(in);
                } finally {
                    in.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public void setLogoImage(BufferedImage newValue) {
        if (newValue == null) {
            setLogoBytes(null);
        } else {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(newValue, "png", out);
                out.close();
                setLogoBytes(Hibernate.createBlob(out.toByteArray()));
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public Blob getLogoBytes() {
        return logoBytes;
    }

    public void setLogoBytes(Blob newValue) {
        logoBytes = newValue;
    }

    public Collection<Room> getRooms() {
        return rooms.values();
    }

    public Room getRoom(int number) {
        return rooms.get(number);
    }

    public void addRoom(Room room) {
        room.setHotel(this);
        rooms.put(room.getNumber(), room);
    }

    public void removeRoom(int number) {
        Room r = rooms.remove(number);
        r.setHotel(null);
    }
}
