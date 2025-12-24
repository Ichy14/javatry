package org.docksidestage.bizfw.basic.buyticket;

// done ichikawa javadocお願い by jflute (2025/11/26)
/**
 * @author n.ichikawa
 */
public class Quantity {
    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int quantity; // Quantityをimmutableな設計として扱うなら、明示的にfinalにしても良いと思う。ただ、今回はmutableな設計の方がわかりやすいかも

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Quantity(int quantity) {
        this.quantity = quantity;
    }

    // ===================================================================================
//                                                                         Reduce Quantity
    //                                                                             =======
    // #1on1: immutableとmutableの使いどころのジレンマ話 (2025/11/14)
    // mutableは状態管理のリスクがあるけど、なんだかんだ便利なときもある。
    // (状態を変えるという必殺技で、プログラミングの問題を解決しやすいことも)
    // (ただ、mutableなものを作ったときは、状態管理しやすいコードを徹底)
    //
    // 実際のシステムだと (究極のmutableである) データベースを使うので、
    // TicketBoothの変数たちもプログラムからはなくなって、immutableしやすくなると思う。
    //
    // #1on1: mutableでうまくいっている理由をさらに言語化 (2025/11/14)
    // インスタンス変数の箱自体は引数で渡せない
    // --oneDayPassQuantity; // これはベタッと書けばできる
    //  or
    // reduceTicketQuantity(oneDayPassQuantity); // この中で--したいが...
    //  ↓
    // public void reduceTicketQuantity(int generalQuantity) {
    //     --generalQuantity; // これは引数の変数を-1してるだけで、インスタンス変数とは無関係
    //     // だから、最初はやりたくない、if文で分岐してインスタンス変数を選んでた
    //     if (isOneDay...) { --oneDayPassQuantity } else ...
    // }
    // 「インスタンス変数の再代入」で、「在庫の変化」を表現している限り、この問題が解決しない。
    // Quantityクラスを作ったとしても、immutableだと結局同じ話。
    //
    //  ↓↓↓ それが、「在庫の変化」のプログラミング表現を...
    // 「インスタンス変数に代入されているインスタンスはずっと同じままで...
    // そのインスタンスの中身を変化させることで表現するようにした」
    //
    // intのquantity       :: インスタンス変数の再代入 (--)
    // immutableのquantity :: インスタンス変数の再代入
    //
    // mutableのquantity   :: インスタンス変数の参照そのままでインスタンス自体を変化させる
    //  → 再利用privateメソッド内では、インスタンス変数との関係は途切れるわけだけど、
    //    単純に渡されたQuantityインスタンスを変化させれば、特定チケットの在庫の変化が表現できる。
    //

    // （自分用メモ 2025/11/26）
    // mutable, immutable, 変数の再代入（参照先の変更）がまたよくわからなくなってきたので整理
    // mutable   : インスタンスの状態が変化することを許容する。確保したメモリの中身を書き換えることができる。
    // immutable : インスタンスの状態が一度設定されたら変化しない。確保したメモリの中身を書き換えられない。
    // 変数の再代入: not finalなら可能。変数が指すメモリの場所を変更すること。
    //             新しいインスタンスを作成して、そのインスタンスのメモリを指すように変数を変更すること。
    //             immutableだとしても、finalでなければ変数の再代入は可能なので、見かけ上は変数の中身が変化したように見える。実際は新しいメモリを指すようになっただけ。
    // 具体例）
    // `int num = 1;`を宣言したときに`num = 3;`を実行することで変数numは別のint型数値が格納されたメモリを指すようになるが、
    //  `final int num = 1;`とすることで変数numは別のint型数値が格納されたメモリを指すこと（つまり再代入）ができなくなる
    // --------
    // Quantity.reduce()でフィールドをデクリメントする（1減らした値を再代入するのか）、新しいインスタンスを返すようにするのか問題
    // フィールドをデクリメント: Quantityをmutableな設計にする。Quantityインスタンスは同じままで中身が変化する。（インスタンスの状態が変化することを許容する）
    //                       → TicketBooth側でQuantityインスタンスを再代入せずそのまま使い続けられる。
    //                       状況に応じて使い分けたいところ、、、
    // 新しいインスタンスを返す: Quantityをimmutableな設計にする。Quantityインスタンスは変化しないが、新しいインスタンスを作成してそれを使うことで変化を表現する。（インスタンスの状態が一度設定されたら変化しない）
    //                       → TicketBooth側で新しいインスタンスを受け取って再代入する必要がある。

    public void reduce() {
        if (quantity <= 0) {
            // #1on1: 確かにその通りで、売ることを意識したメソッドなのか？純粋に在庫を減らすってメソッドなのか？
            // 汎用reduce()のつもりなのであれば、確かにここは「売り切れ」ではなく「もう在庫ないのに減らせない例外」をthrowが良い。
            // ここでは事実だけを伝えて処理を止める。それが売り切れという業務なのかどうかは、呼び出し側が決めること。
            // e.g. TicketBooth:
            //  try {
            //      quantity.reduce(); // Quantityをmutableな設計にするならこれでいい（参照先であるQuantityが内部でもつフィールドの値を変えることで状態変化を表現するから）
            //  } catch (在庫ないのに減らそうとした例外 e) {
            //      throw new SoldOut例外("xxxxxxx", e);
            //  }
            // 今回は、SoldOut例外がTicketBoothの中で定義してあったから依存がわかりやすかった。
            // でも、独立したTicketSoldOutExceptionだったら？売り切れという業務概念に依存していることに気付けるか？
            // done ichikawa ということで、何かしら納得のいく実装をしてみましょう by jflute (2025/11/26)
            // TODO done ichikawa ご自身で気付いた(^^。例外メッセージにまだ売り切れ概念が依存してる by jflute (2025/12/16)
            throw new OutOfStockException("Cannot reduce quantity: no stock"); // ここでTicketBoothの例外を呼び出しているのはどうなのか、、、依存性の方向
        }
        quantity--; // フィールドをデクリメントする → mutableな設計にするので、ちょい危険？
                    // → しかし、Quantityの参照先を変えないまま状態変化を表現できるので、TicketBooth側のコードがシンプルで再利用性と可読性が高くなる気がする
    }
//    public Quantity reduce() {
//        if (quantity <= 0) {
//            throw new TicketBooth.TicketSoldOutException("Sold out"); // ここでTicketBoothの例外を呼び出しているのはどうなのか、、、依存性の方向
//        }
//        return new Quantity(quantity - 1); // 新しいインスタンスを返すようにする → immutableな設計にするので、ちょい安全
//    }

    public static class OutOfStockException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public OutOfStockException(String msg) {
            super(msg);
        }
    }
    // この例外クラスとTicketBoothクラスの例外も合わせて、どこか一つのファイルとかにまとめたい気持ち

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getQuantity() {
        return quantity;
    }
}
