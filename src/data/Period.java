/*
 * Period.java
 *
 * Created on 18 June 2002, 17:51
 */

package data;

import java.util.Date;

/**
 * @author  maurice
 */
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

    /** Getter for property fromDate.
     * @return Value of property fromDate.
     */
    public Date getFromDate() {
        return fromDate;
    }

    /** Setter for property fromDate.
     * @param fromDate New value of property fromDate.
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /** Getter for property toDate.
     * @return Value of property toDate.
     */
    public Date getToDate() {
        return toDate;
    }

    /** Setter for property toDate.
     * @param toDate New value of property toDate.
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    /** Getter for property tariff.
     * @return Value of property tariff.
     */
    public Tariff getTariff() {
        return tariff;
    }

    /** Setter for property tariff.
     * @param tariff New value of property tariff.
     */
    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }
}
