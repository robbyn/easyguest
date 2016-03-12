package org.tastefuljava.ezguest.data;

import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Tariff {
    private int id;
    private String name;
    private double factor;
    private Color color;
    private final Set<Period> periods = new HashSet<>();

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

    public double getFactor() {
        return factor;
    }

    public void setFactor(double newValue) {
        factor = newValue;
    }

    public int getIntColor() {
        return color == null ? Color.white.getRGB() : color.getRGB();
    }

    public void setIntColor(int newValue) {
        setColor(new Color(newValue));
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color newValue) {
        color = newValue;
    }

    public Set<Period> getPeriods() {
        return Collections.unmodifiableSet(periods);
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    public void removePeriod(Period period) {
        periods.remove(period);
    }

    public void removeAllPeriods() {
        periods.clear();
    }

    @Override
    public int hashCode() {
        return id & 0x7FFFFFFF;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Tariff)) {
            return false;
        }
        return id == ((Tariff)other).id;
    }
}
