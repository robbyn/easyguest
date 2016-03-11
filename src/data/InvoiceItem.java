/*
 * InvoiceItem.java
 *
 * Created on 02 December 2002, 17:32
 */

package data;

/**
 * @author  Maurice Perry
 */
public class InvoiceItem {
    private int id;
    private Invoice invoice;
    private Article article;
    private double price;
    private double quantity;

    public InvoiceItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice newValue) {
        if (invoice != null) {
            invoice.removeItem(this);
        }
        invoice = newValue;
        if (invoice != null) {
            invoice.addItem(this);
        }
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article newValue) {
        article = newValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double newValue) {
        price = newValue;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double newValue) {
        quantity = newValue;
    }

    public double getAmount() {
        return quantity*price;
    }
}
