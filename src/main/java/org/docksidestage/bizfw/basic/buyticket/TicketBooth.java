/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author jflute
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_QUANTITY = 10;
    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    private static final int TWO_DAY_PRICE = 13200;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int quantity = MAX_QUANTITY;
    private int quantity_twoDay = MAX_QUANTITY;
    private Integer salesProceeds; // null allowed: until first purchase
    // 1-dayパスと2-dayパスの売上を分けるべきか？実際には別れていた方が嬉しそうな気はする

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    // you can rewrite comments for your own language by jflute
    // e.g. Japanese
    // /**
    // * 1Dayパスポートを買う、パークゲスト用のメソッド。
    // * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
    // * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
    // * @throws TicketShortMoneyException 買うのに金額が足りなかったら
    // */
    /**
     * Buy one-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     */
    public Ticket buyOneDayPassport(Integer handedMoney) {
        if (handedMoney < ONE_DAY_PRICE) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
        if (quantity <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
        --quantity;
        countSalesProceeds(ONE_DAY_PRICE);
        // そもそもsalesProceedsの初期値を0にしておけば、nullチェックしなくて良いのでは？
        // ただ、要件的に「まだ売上がない」ことをnullで表現したい気もする

        return new Ticket(ONE_DAY_PRICE);

        // これらのクラスはメソッドは業務ロジックを実行するユースケースに該当するのか？
        // そういうことを考え始めると、売り上げ、チケット、金額あたりを値オブジェクトにする、みたいなことも考えてみたい。ここでエンティティにするべきものはあるか？（idで識別するべきものはなさそうに見える）
        // 「チケット」はあった（それを知る前に↑を考えてた）。doInPark()メソッドで状態を変更しているが、たしかにチケットは値オブジェクトじゃなくてエンティティの方が適切か、、、（idで管理するうんぬんは一旦置いといて、今回の場合チケットは等価性より同一性で同じものかどうか判断されるべきだと思う）
    }

    public Integer buyTwoDayPassport(Integer handedMoney) {
        if (handedMoney < TWO_DAY_PRICE) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
        if (quantity_twoDay <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
        --quantity_twoDay;
        countSalesProceeds(TWO_DAY_PRICE);

        return (handedMoney - TWO_DAY_PRICE) > 0 ? (handedMoney - TWO_DAY_PRICE) : null;
    }

    private void countSalesProceeds(int price) {
        if (salesProceeds != null) { // second or more purchase
            salesProceeds = salesProceeds + price;
        } else { // first purchase
            salesProceeds = price;
        }
    }

    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getQuantity() {
        return quantity;
    }

    public int getQuantity_twoDay() {return quantity_twoDay;}

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
