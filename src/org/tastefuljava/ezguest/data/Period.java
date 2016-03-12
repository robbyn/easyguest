package org.tastefuljava.ezguest.data;

import java.util.Date;

public class Period {
    private int id;
    private Date fromDate;
    private Date toDate;
    private Tariff tariff;

    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }
}
