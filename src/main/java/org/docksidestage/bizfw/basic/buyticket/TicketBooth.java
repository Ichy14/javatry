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

// #1on1: 最近のプログラミング、コーディングの中での悩み、レビューができない (2025/11/26)
// 何を見る？レビューの観点。最近気付いた観点、テストの充足性。
// 
// まず基本、厳密にはレビューイーが、「レビューの観点」をレビューワーに伝える。
// あと、チームとしてこのチームでの「レビューの目的、観点、達成したいこと」を明確に。
//
// [間違い探し]
// o 実装バグを見つける
// o 業務仕様ミスを見つける ☆☆☆
// o (業務仕様の妥当性の確認) ☆☆
//
// [改善]
// o テストの充足性
// o 現場での実装ポリシー (現場固有の王道3つ)
// o 業務処理の実現方法 (以下の王道3つを総合的に)
//
// (王道3つ)
// o 可読性、コメント (一般的な)
// o エラーハンドリング
// o パフォーマンス
//

// #1on1: オーソドックスな開発プロセス
// o 要求仕様 (お客さんから、業務側から: こういうことしたい)
// o 要件定義 (開発側が: やりたいことはこういうことでいいんですね？)
// o 外部設計/基本設計 (画面系の設計など、外部から見える部分がどう振る舞えばいいか？)
// o 内部設計/詳細設計 (プログラムとしてどのように実現するか？)
// o 実装設計 (具体的にどんなクラス作って、どう実装していくか？)
// o 実装 (書く)
// o テスト設計... (どんな動作確認をするか？) // 実装より先にやることも
// o テスト実装... (書く)

// done ichikawa javadoc, author追加お願いします by jflute (2025/10/31)

// #1on1: ログ設計の話、現場のログ設定を見ながら参考に (2026/01/14)
//
// 参考までに↓
// // LastaFluteのアプリログ | LastaFlute
// https://dbflute.seasar.org/ja/lastaflute/howto/structure/applogging.html#envlogsettings
//

// #1on1: ロギングフレームワーク (2026/01/14)
//
// Log4j (1.x) → Logback (Slf4j)
//  ↓
// Log4j 2 (2.x)
//

/**
 * @author jflute
 * @author ichikara
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_QUANTITY = 10;
    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    private static final int TWO_DAY_PRICE = 13200;
    private static final int TWO_DAY_NIGHT_ONLY_PRICE = 7400;
    private static final int FOUR_DAY_PRICE = 22400;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // done ichikawa 元の quantity 変数の変数名をどうしたらいいか？を考えてみてください by jflute (2025/10/24)
    // ここを分けない方が実装が楽、の意味がようやくわかった、、、
//    private int oneDayPassQuantity = MAX_QUANTITY;
    // done ichikawa インスタンス自体がmutableで状態を変化させられるので、変数の再代入がないからfinalでOK by jflute (2025/11/26)
    private final Quantity oneDayPassQuantity = new Quantity(MAX_QUANTITY);
    private final Quantity twoDayPassQuantity = new Quantity(MAX_QUANTITY);
    private final Quantity twoDayNightOnlyPassQuantity = new Quantity(MAX_QUANTITY);
    private final Quantity fourDayPassQuantity = new Quantity(MAX_QUANTITY);
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
//        return doBuyPassport(handedMoney, ONE_DAY_PRICE, oneDayPassQuantity, TicketType.ONE_DAY).getTicket(); // 再利用版
        return doBuyPassport(handedMoney, oneDayPassQuantity, TicketType.ONE_DAY).getTicket(); // リファクタ版
    }

    public TicketBuyResult buyTwoDayPassport(Integer handedMoney) {
        // done ichikawa TicketDuration の enum が存在するのであれば、そもそもこの時点から... by jflute (2025/12/15)
        // 1とか2というリテラル数値ではなく、TicketDuration を指定してくなりますね。
        // done ichikawa new TicketBuyResultの部分もdoBuyに含めちゃってもいいのでは？ by jflute (2025/12/16)
        // done ichikawa 現状doBuyの引数で、TWO_DAYを意識した引数が３つある。 by jflute (2025/12/24)
        // Quantityに関しては状態を示すオブジェクトで、単なる参照情報ってわけじゃないから、そこは独立して良いとして...
        // なので、せめてTWO_DAYを意識した引数を２こにしたい。
        // (解決方法として関連するとぅどぅが、step5の showTicketIfNeeds() のところにあるので、一緒に考えてみるといいかも)
        // hint1: オブジェクト指向っぽいね

        // 表示価格とチケット種別は変動しない・関連した情報、渡されるお金は独立、残数は状態が変化するから別にしたい、、、
//        return doBuyPassport(handedMoney, TWO_DAY_PRICE, twoDayPassQuantity, TicketType.TWO_DAY);

        // doBuyの前にチケット作っちゃうと、TicketBuyResultの中でまたTicket作ることになるからズレる。だめだ
//        Ticket ticket = new Ticket(TWO_DAY_PRICE, TicketDuration.TWO_DAYS);
//        return doBuyPassport(handedMoney, twoDayPassQuantity, ticket);
        // TWO_DAY_PRICEを参照するということはdurationはTWO_DAYSのはずだから、それをロジックとしてもつ？
        
        // #1on1: publicメソッドのbuyは、買えるチケット種別のメニュー表みたいな役割で... (2026/01/28)
        // 内部的にはdoBuyPassport()で処理は一元管理されていて、TicketTypeは今買えるかどうかは気にせず全部の種別。
        return doBuyPassport(handedMoney, twoDayPassQuantity, TicketType.TWO_DAY);
    }

    public TicketBuyResult buyNightOnlyTwoDayPassport(Integer handedMoney) {
        // done ichikawa nightの情報もTicketType？ by jflute (2026/01/28)
//        return doBuyPassport(handedMoney, TWO_DAY_NIGHT_ONLY_PRICE, twoDayNightOnlyPassQuantity, TicketType.TWO_DAY, false);
        // doBuyPassportの引数が増えすぎたのでどうにかしたい
        // いくつかの意味単位でオブジェクトにまとめるとか？
        // #1on1: nightのときだけ増えてる, false の引数から、時間帯種別までに辿り着く思考の道 (2026/02/19)
        // 一歩ずつリファクタリングして(理想ではないとわかってても)、さらに次の一歩を探していくスタイル。
        // done TODO ichikawa [読み物課題] リファクタリングは思考のツール by jflute (2026/02/19)
        // https://jflute.hatenadiary.jp/entry/20121202/1354442627
        // 今回は、ラフスケッチなリファクタリングをしていって、一歩ずつマシにしていって、次のマシを見つけて最終的な理想に到達。
        // (ichikawa) 作業中は少しずつ変えていったが、それをcommitするのを忘れて一気に大きな変更のcommitをしてしまったorz
        // #1on1: プロトタイピング思考のお話も (2026/02/19)
        // done TODO ichikawa [読み物課題] jfluteのプログラマーオススメ五冊 by jflute (2026/02/19)
        // (ichikawa) commitに「何をした、その背景・理由は」を書いておく、それが簡潔に書けるレベルの変更に留める、というのも「気づかい」だよな、、、と思うなどしました
        // 質問：意図がわかるコード、てなんでしょう？？？
        // https://jflute.hatenadiary.jp/entry/20150727/fivebooks
        // done TODO ichikawa ", false" を無くしてみましょう by jflute (2026/02/19)
        // return doBuyPassport(handedMoney, twoDayNightOnlyPassQuantity, TicketType.TWO_DAY_NIGHT_ONLY, false);  // リファクタ版
        return doBuyPassport(handedMoney, twoDayNightOnlyPassQuantity, TicketType.TWO_DAY_NIGHT_ONLY);  // リファクタ版
    }

    public TicketBuyResult buyFourDayPassport(Integer handedMoney) {
//        return doBuyPassport(handedMoney, FOUR_DAY_PRICE, fourDayPassQuantity, TicketType.FOUR_DAY);
        return doBuyPassport(handedMoney, fourDayPassQuantity, TicketType.FOUR_DAY); // リファクタ版
    }

// 以前の実装
//    public Ticket buyOneDayPassport(Integer handedMoney) {
//        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
//        // done ichikawa checkしてreduceしてcountするって流れを再利用してみましょう by jflute (2025/11/14)
//        // 現状は、個別の処理は再利用されているけど、流れが再利用されていないので、流れの変更で複数箇所修正が必要になる。
//        // → 追加で追加の処理が発生した時、両方で同じ修正をしないといけなくなる → ミスが起きやすい
//        // _/_/_/_/
//        // チケットブースの視点で、チケット購入操作の時何をするか、が表現できると良いんだろうな
//        assertHandedMoneyEnough(handedMoney, ONE_DAY_PRICE);
//
//        //oneDayPassQuantity.reduce(); // ここだけちょっと毛色が違うの気になる
//        reduceTicketQuantity(oneDayPassQuantity);
////        oneDayPassQuantity = reduceTicketQuantity(oneDayPassQuantity); // Quantityをimmutableな設計に変更するなら、戻り値を受け取って再代入する必要がある
//
//        countSalesProceeds(ONE_DAY_PRICE);
//        // そもそもsalesProceedsの初期値を0にしておけば、nullチェックしなくて良いのでは？
//        // ただ、要件的に「まだ売上がない」ことをnullで表現したい気もする
//        // #1on1: 本当その通り。ここは要件は自分で決めてOK。
//
//        buyPassport(handedMoney, ONE_DAY_PRICE, oneDayPassQuantity); // 再利用版
//        return new Ticket(ONE_DAY_PRICE);
//
//        // これらのクラスはメソッドは業務ロジックを実行するユースケースに該当するのか？
//        // そういうことを考え始めると、売り上げ、チケット、金額あたりを値オブジェクトにする、みたいなことも考えてみたい。ここでエンティティにするべきものはあるか？（idで識別するべきものはなさそうに見える）
//        // 「チケット」はあった（それを知る前に↑を考えてた）。doInPark()メソッドで状態を変更しているが、たしかにチケットは値オブジェクトじゃなくてエンティティの方が適切か、、、（idで管理するうんぬんは一旦置いといて、今回の場合チケットは等価性より同一性で同じものかどうか判断されるべきだと思う）
//    }
//
//    public TicketBuyResult buyTwoDayPassport(Integer handedMoney) {
//        assertHandedMoneyEnough(handedMoney, TWO_DAY_PRICE);
//
////        twoDayPassQuantity.reduce(); // ここだけちょっと毛色が違うの気になる
//        reduceTicketQuantity(twoDayPassQuantity); // バケツリレーしたりインスタンスメソッドを使っていたら、これでうまくいく理由がわからなくなってしまった
//
//        countSalesProceeds(TWO_DAY_PRICE);
//
//        return new TicketBuyResult(handedMoney, TWO_DAY_PRICE);
//    }
    
    // done ichikawa 再利用、もう少しチャレンジしてみましょう (これ以上は無理かなってところまで) by jflute (2025/10/24)
    // 「これ以上無理」の基準はなんだろうか？まとめすぎても意味がわからなくなりそう
    // → 業務の一つ一つの操作を単位としてまとめるイメージを持った → どこまで一括りにすべき？というのは考えることになるも？

    // done ichikawa [小テクニック]privateのメソッドがbuy始まりだと補完時に視認しづらいので... by jflute (2025/11/26)
    // 区別するためにメソッド名を e.g. doBuyPassport(), internalBuyPassport()
    // 会話上も、buyメソッドが曖昧になるので、doBuyにすると区別しやすい。

    // #1on1: 引数多くてなんかなぁ by いちかわさん (2025/12/24)
    // 単なる引数クラスじゃなく、オブジェクト的に意味をうまく使って解決ができれば...
    // (TwoDayのbuyメソッドの方のとぅどぅで続きを...)

    private TicketBuyResult doBuyPassport(int handedMoney,Quantity quantity, TicketType ticketType) {
        assertHandedMoneyEnough(handedMoney, ticketType.getPrice());
        reduceTicketQuantity(quantity);
        countSalesProceeds(ticketType.getPrice());
        return new TicketBuyResult(handedMoney, ticketType.getPrice(), ticketType);
    }

    // リファクタ前のやつ（2026/02/25）
//    private TicketBuyResult doBuyPassport(int handedMoney,Quantity quantity, TicketType ticketType) {
//        assertHandedMoneyEnough(handedMoney, ticketType.getPrice());
//        reduceTicketQuantity(quantity);
//        countSalesProceeds(ticketType.getPrice());
//        return new TicketBuyResult(handedMoney, ticketType.getPrice(), ticketType);
//    }
//    private TicketBuyResult doBuyPassport(int handedMoney,Quantity quantity, TicketType ticketType, boolean isAvailableAllDay) {
//        assertHandedMoneyEnough(handedMoney, ticketType.getPrice());
//        reduceTicketQuantity(quantity);
//        countSalesProceeds(ticketType.getPrice());
//        return new TicketBuyResult(handedMoney, ticketType.getPrice(), ticketType, isAvailableAllDay);
//    }

    // リファクタ前のやつ（2026/01/28）
//    private TicketBuyResult doBuyPassport(int handedMoney, int price, Quantity quantity, TicketType availableDays) {
//        assertHandedMoneyEnough(handedMoney, price);
//        reduceTicketQuantity(quantity);
//        countSalesProceeds(price);
//        return new TicketBuyResult(handedMoney, price, availableDays);
//    }
//    private TicketBuyResult doBuyPassport(int handedMoney, int price, Quantity quantity, TicketType availableDays, boolean isAvailableAllDay) {
//        assertHandedMoneyEnough(handedMoney, price);
//        reduceTicketQuantity(quantity);
//        countSalesProceeds(price);
//        return new TicketBuyResult(handedMoney, price, availableDays, isAvailableAllDay);
//    }

    // done ichihara checkという言葉、どっちをチェックをするの？どっちで例外が発生するの？ by jflute (2025/11/14)
    // 正しい方をチェックするのか？間違った方をチェックするのか？どっちにも使える便利でありながら曖昧な言葉なので...
    // 明確な動詞を使うことが多い。 e.g. assert[正しいこと、期待されること]
    //  e.g. assertHandedMoneyEnough()
    // もし、間違ってる方にフォーカスを当てるなら... e.g. throwIfHandedMoneyShortage() とか。
    private void assertHandedMoneyEnough(int handedMoney, int price) {
        if (handedMoney < price) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
    }

    private void reduceTicketQuantity(Quantity quantity) {
        // done ichikawa 売り切れ例外に翻訳してあげましょう (JavaDocにもSoldOut例外をthrowするって書いてあるし) by jflute (2025/12/16)
        // #1on1: 例外の翻訳の話 (2025/12/24)
        // 例外の翻訳のデバッグ面でのコンセプト、クラス依存関係面でのコンセプト。
        // ただ、あくまで必須ではベターという感じ。
        try {
            quantity.reduce(); // Quantityをmutableな設計にするならこれでいい（参照先であるQuantityが内部でもつフィールドの値を変えることで状態変化を表現するから）
        } catch (Quantity.OutOfStockException e) {
            // done ichikawa 例外が途切れているので、引き継ぎましょう by jflute (2025/12/24)
            throw new TicketSoldOutException("Sold out ticket", e);
        }
    }
//    private Quantity reduceTicketQuantity(Quantity quantity) {
//        quantity = quantity.reduce(); // Quantityをimmutableな設計にするならこれ（新しいインスタンスを返すことで状態変化を表現するから）
//        return quantity;
//    }

    private void countSalesProceeds(int price) {
        if (salesProceeds != null) { // second or more purchase
            salesProceeds = salesProceeds + price;
        } else { // first purchase
            salesProceeds = price;
        }
    }

    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) { // 起点
            super(msg);
        }
        public TicketSoldOutException(String msg, Throwable cause) { // 翻訳
            super(msg, cause);
        }
    }

    // 例外ハンドリングせずに例外をif条件で例外を発生させるときなどは、causeを受け取るべきなんだろうか？？？
    // #1on1: catchの中じゃないthrowは、例外の起点になる場面なので、causeという概念がない。 (2026/01/14)
    // なのでコンストラクターが二つ用意されている。
    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
        public TicketShortMoneyException(String msg, Throwable cause) {
            super(msg, cause);
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

    // #1on1: enumも、レイヤーに寄って内容が変わる話 (2026/01/28)
    // DB側のTicketType: 全部入り (古い種別: 今売ってない種別も入る)
    // 画面側のTicketType: 今売っていい種別のみ → PurchasableTicketType と言える
    //
    // o DB側の区分値 (DB区分値)
    // o 画面側の区分値 (アプリ区分値)
    // 
    // // アプリ区分値の自動生成(appcls) | LastaFlute
    // https://dbflute.seasar.org/ja/lastaflute/howto/dbflute/appcls.html
    //
    // // 区分値のグルーピング (GroupingMap) | DBFlute
    // https://dbflute.seasar.org/ja/manual/function/genbafit/implfit/classification/groupingmap.html
    //
    // 現場のグルーピングも見てみた。
    
    public enum TicketType {
        ONE_DAY(1, ONE_DAY_PRICE, AvailableTimeType.ALL_DAY),
        TWO_DAY(2, TWO_DAY_PRICE, AvailableTimeType.ALL_DAY),
        TWO_DAY_NIGHT_ONLY(2, TWO_DAY_NIGHT_ONLY_PRICE, AvailableTimeType.NIGHT_ONLY),
        FOUR_DAY(4, FOUR_DAY_PRICE, AvailableTimeType.ALL_DAY);

        private final int availableDays;
        private final int price;
        private final AvailableTimeType availableTime;

        TicketType(int day, int price, AvailableTimeType availableTime) {
            this.availableDays = day;
            this.price = price;
            this.availableTime = availableTime;
        }

        public int getAvailableDays() {
            return availableDays;
        }

        public int getPrice() {
            return price;
        }

        public AvailableTimeType getAvailableTime() {
            return availableTime;
        }

        public static TicketType of(int day) {
            for (TicketType d : values()) {
                if (d.availableDays == day) {
                    return d;
                }
            }
            throw new IllegalArgumentException("Unknown day: " + day);
        }
    }
    public enum AvailableTimeType {
        ALL_DAY, NIGHT_ONLY
    }
}
