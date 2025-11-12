package org.docksidestage.bizfw.basic.buyticket;

public class Quantity {
    private int quantity;

    public Quantity(int quantity) {
        this.quantity = quantity;
    }

    public void reduce() {
        if (quantity <= 0) {
            throw new TicketBooth.TicketSoldOutException("Ticket is sold out.");
        }
        quantity--;
    }

    public int getQuantity() {
        return quantity;
    }
}
