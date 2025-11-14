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

// TODO ichikawa javadoc, author追加お願いします by jflute (2025/10/31)
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
    // done ichikawa 元の quantity 変数の変数名をどうしたらいいか？を考えてみてください by jflute (2025/10/24)
    // ここを分けない方が実装が楽、の意味がようやくわかった、、、
//    private int oneDayPassQuantity = MAX_QUANTITY;
    private Quantity oneDayPassQuantity = new Quantity(MAX_QUANTITY);
    private Quantity twoDayPassQuantity = new Quantity(MAX_QUANTITY);
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
    // done ichikawa javadoc, 追加した戻り値に関するコメントを書きましょう (@return) by jflute (2025/10/24)
    /**
     * Buy one-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @return The ticket object representing the one-day passport.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     */
    public Ticket buyOneDayPassport(Integer handedMoney) {
        // チケットブースの視点で、チケット購入操作の時何をするか、が表現できると良いんだろうな
        checkHandedMoneyShortage(handedMoney, ONE_DAY_PRICE);

//        oneDayPassQuantity.reduce(); // ここだけちょっと毛色が違うの気になる
        reduceTicketQuantity(oneDayPassQuantity); // バケツリレーしたりインスタンスメソッドを使っていたら、これでうまくいく理由がわからなくなってしまった

        countSalesProceeds(ONE_DAY_PRICE);
        // そもそもsalesProceedsの初期値を0にしておけば、nullチェックしなくて良いのでは？
        // ただ、要件的に「まだ売上がない」ことをnullで表現したい気もする
        // #1on1: 本当その通り。ここは要件は自分で決めてOK。

        return new Ticket(ONE_DAY_PRICE);

        // これらのクラスはメソッドは業務ロジックを実行するユースケースに該当するのか？
        // そういうことを考え始めると、売り上げ、チケット、金額あたりを値オブジェクトにする、みたいなことも考えてみたい。ここでエンティティにするべきものはあるか？（idで識別するべきものはなさそうに見える）
        // 「チケット」はあった（それを知る前に↑を考えてた）。doInPark()メソッドで状態を変更しているが、たしかにチケットは値オブジェクトじゃなくてエンティティの方が適切か、、、（idで管理するうんぬんは一旦置いといて、今回の場合チケットは等価性より同一性で同じものかどうか判断されるべきだと思う）
    }

    public TicketBuyResult buyTwoDayPassport(Integer handedMoney) {
        checkHandedMoneyShortage(handedMoney, TWO_DAY_PRICE);

//        twoDayPassQuantity.reduce(); // ここだけちょっと毛色が違うの気になる
        reduceTicketQuantity(twoDayPassQuantity); // バケツリレーしたりインスタンスメソッドを使っていたら、これでうまくいく理由がわからなくなってしまった

        countSalesProceeds(TWO_DAY_PRICE);

        return new TicketBuyResult(handedMoney, TWO_DAY_PRICE);
    }
    
    // TODO ichikawa 再利用、もう少しチャレンジしてみましょう (これ以上は無理かなってところまで) by jflute (2025/10/24)
    // 「これ以上無理」の基準はなんだろうか？まとめすぎても意味がわからなくなりそう
    // → 業務の一つ一つの操作を単位としてまとめるイメージを持った → どこまで一括りにすべき？というのは考えることになるも？

    private void checkHandedMoneyShortage(int handedMoney, int price) {
        if (handedMoney < price) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
    }

    private void reduceTicketQuantity(Quantity quantity) {
        quantity.reduce();
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
    public int getOneDayPassQuantity() {
        return oneDayPassQuantity.getQuantity();
    }

    // done ichikawa 突然ここだけ一行スタイル by jflute (2025/10/24)
    // intelliJが勝手に表示を変えてただけでした
    public int getTwoDayPassQuantity() {
        return twoDayPassQuantity.getQuantity();
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
