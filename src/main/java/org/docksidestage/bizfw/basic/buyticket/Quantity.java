package org.docksidestage.bizfw.basic.buyticket;

public class Quantity {
    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int quantity;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Quantity(int quantity) {
        this.quantity = quantity;
    }

    // ===================================================================================
//                                                                         Reduce Quantity
    //                                                                             =======
    public void reduce() {
        if (quantity <= 0) {
            throw new TicketBooth.TicketSoldOutException("Sold out"); // ここでTicketBoothの例外を呼び出しているのはどうなのか、、、依存性の方向
        }
        quantity--;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getQuantity() {
        return quantity;
    }
}
