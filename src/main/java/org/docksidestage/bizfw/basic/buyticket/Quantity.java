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
