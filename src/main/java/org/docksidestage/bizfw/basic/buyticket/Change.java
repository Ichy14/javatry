package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author n.ichikawa@bizreach.co.jp
 */
public class Change {
    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final int amount;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Change(int handedMoney, int displayPrice) {
        this.amount = handedMoney - displayPrice;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========

    public int getAmount() {
        return amount;
    }
}
