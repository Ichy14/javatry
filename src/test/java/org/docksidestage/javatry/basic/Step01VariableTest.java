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
package org.docksidestage.javatry.basic;

import java.math.BigDecimal;

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of variable. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author Ichy14 (n.ichikawa@bizreach.co.jp)
 */
public class Step01VariableTest extends PlainTestCase {
    // `extends`キーワードを使うによりStep01VariableTestはPlainTestCaseを継承している
    // ===================================================================================
    //                                                                      Local Variable
    //                                                                      ==============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_variable_basic() { // example, so begin from the next method
        String sea = "mystic";
        RuntimeException exception = new RuntimeException();
        log(sea, exception); // your answer? => mystic
        // done: `log()`メソッドの実装を読解したい
        // done ichikawa [ふぉろー] よーし、1on1で一緒に読みますか(^^ by jflute (2025/07/24)
        // #1on1: command+click でコードジャンプ、そのメソッドやクラスのソースコードへ飛ぶ
        // #1on1: ソースコードリーディングのコツ: 漠然読みで構造をだけ把握して、目的に合うフォーカス読みしていく
        // ichikawaさんの基礎力が上がったら、またlog()一緒に読んでみましょう。
        // #1on1: IntelliJでのとぅどぅの探し方、右のLINEとshift+shiftのとぅどぅ
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_initial() {
        String sea = "mystic";
        Integer land = 8;
        String piari = null;
        String dstore = "mai";
        sea = sea + land + piari + ":" + dstore;
        log(sea); // your answer? => 型が違うものどうしの足し算なのでエラーになるのでは？
        // 正常に？出力されてしまった。
        // intの8がstringにキャストされた？stringで宣言したnullが"null"という文字列として変換されているのはなぜ?
        // `+`演算子を実行するときは左側の型に合わせて演算される＆String型が左右どちらかに来たらString型として処理する、みたいなことになってそう？
        // done: 変数の型を確認する方法は？
        // done ichikawa [ふぉろー] 演算中の型の理解はそれでOKです。Stringが含まれたらそこにどんどん引きずりこまれる感じですね by jflute (2025/07/24)
        // +演算子の型変換は暗黙で行われるので、IDE上でもあんまり確認する方法がないかもですね。
        // まあ例えばこう書いてみて...
        //  e.g. int a = 1 + "b";
        // → コンパイルエラーのメッセージに「Type mismatch: cannot convert from String to int」とあるので、
        // これで String になっていることを確認する、ってことはできそうです。
        // #1on1: 自分が思っている以上に、期間が空くと忘れちゃうので、コメント書くときもちょい細かめに
        // 細切れの仕事ができるようになるためのコツ。javatryでシミュレーション。

        // done jflute 1on1にて、null文字列に変換されることに関してフォロー予定 (2025/07/24)
        // (↑これはくぼ用のtodoということでそのまま残しておいてください)
        // #1on1 例えば、C#だと空文字になります。JavaScriptだとnullとかundefinedとかが文字列になる。
        // 昔のインターネット画面だと、こんにちは null さんって表示されることもあった。
        // メールだと最近でも null を見かける。
        // エラーの優先度のお話、なんでもかんでも細かいところまで落とせば良いというわけでもない。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_basic() {
        String sea = "mystic";
        String land = "oneman";
        sea = land;
        land = land + "'s dreams";
        log(sea); // your answer? => oneman
        // log(land);
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_int() {
        int sea = 94;
        int land = 415;
        sea = land;
        land++;
        log(sea); // your answer? => 415
        log(land);
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_BigDecimal() {
        BigDecimal sea = new BigDecimal(94);
        BigDecimal land = new BigDecimal(415);
        sea = land;
        sea = land.add(new BigDecimal(1));
        // log(sea);
        sea.add(new BigDecimal(1));
        log(sea); // your answer? => 417
        // BigDecimalはimmutable → seaの値は変わらない。Python書いてたときにもこんなのがあった気がする
        // Javaのsyntax sugarのこともうちょい知りたい
        // done ichikawa [いいね] immutableという概念を理解されているの素晴らしいです by jflute (2025/07/24)
        // done ichikawa [ふぉろー] 1on1のときぜひsyntax sugarの話させてください by jflute (2025/07/24)
        // #1on1: Javaのsyntax sugarのバランスについて。sugarあるけど、他の言語に比べたらない方かも。
        // sugarなのであれば良いというものではなく、バランスが重要。言語によってsugarの価値観が違う。
        //for (String stage : stageList) {
        //    log(stage);
        //}
        //stageList.forEach(stage -> log(stage));

        // #1on1: 変数seaと94BigDecimalインスタンスの関係性:
        // 変数seaが、94BigDecimalインスタンスの置き場所(アドレス)を持っていて参照している。 
        // seaインスタンスという言葉: 厳密ではない表現だけど、ただ理解しやすいので、便宜上使うみたいな感じ
        // #1on1: 調べ方の選択肢:
        // 1: IntelliJでBigDecimal/add()にカーソルを当ててクラス/メソッドのJavaDocを表示
        // 2: IntelliJでメソッド補完時はcontrol+JでJavaDocを表示

        // done jflute 1on1にてimmutable周りとJavaのsyntax sugarの話 (2025/07/24)
        // done jflute 1on1にてソースコードリーディング (2025/07/25)
        // #1on1: log()から再びadd()で「漠然読みで構造だけ把握して、当てをつけてフォーカス読み」 (2025/08/06)
        // #1on1: 何を知りたかったんだっけ？にならないように by ichikawaさん
        // #1on1: 「My Favorite Book: 仮説思考」に通じるかも!?
        // https://jflute.hatenadiary.jp/entry/20150111/kasetsu

        // #1on1: immutableのメリット/デメリットは？ (2025/08/06)
        // 0というインスタンスを突然1に書き換えられたら大変!? (別の性質になる) by ichikawaさん
        //BigDecimal land開園日 = new BigDecimal(415);
        //land開園日.add(new BigDecimal(1)); // 仮にmutableなら
        //log(land開園日); // 416
        // ↓
        //BigDecimal land開園日 = new BigDecimal(415);
        //BigDecimal land開園日の次の日 = land開園日.add(new BigDecimal(1));
        //log(land開園日); // 415
        //log(land開園日の次の日); // 416
        //
        // o 可読性
        // o 安全性
        // o 変わらない方が世話ない (mutableだと人間の管理が追っつかない)
        //  → 人都合
        // #1on1 immutableの歴史 (2025/08/06)
        // done jflute 次回1on1で、Javaでなんでぜんぶimmutableにしないの？ (2025/08/06)
        // #1on1: ここまでのセオリーとしては、immutableはいいことばっかり。
        // 現実は、Javaだとけっこう混ざってる。
        // o Javaができたのは1995年、まだ全然immutable指向な世の中じゃないとき
        //  i に言語デザインされているので、mutableなクラスや思想もたくさん残ってる
        //  i それでも、ちょっとずつimmutableなクラスは増えているけど緩やか
        // o mutableなクラスの方がちゃちゃっと書けることもある
        //  i immutableにすることで少しプログラミングの手間が掛かることもある
        // o ↑の原因の一つとして、Javaの文法がimmutable全推しってわけじゃない
        //  i immutableを実現するのに文法がまだちょっと足りない
        //  i じゃあJavaはそれをどんどん付け足していくのか？はまだわからない
        //  i 強力な文法をてんこ盛りにするのが良いのかどうか？(Javaとして)
        //
        // ちなみに別の言語だと、immutable全推しの言語もある。e.g. Scala
        // (全推しではないけど、Javaよりは推してるのが Kotlin)
    }

    // ===================================================================================
    //                                                                   Instance Variable
    //                                                                   =================
    private String instanceBroadway;
    private int instanceDockside;
    private Integer instanceHangar;
    private String instanceMagiclamp;

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_String() {
        String sea = instanceBroadway;
        log(sea); // your answer? => よくわからん、、、
        // クラス内で宣言されたフィールドを取得しようとする関数？
        // instanceBroadwayという変数を宣言したものの、初期化していないので、型に合わせて自動的に初期値がセットされる
        // その初期値はString型ならnullになるので、nullが出力されるっぽい
        // done ichikawa [ふぉろー] 初期値に関してはそれで認識大丈夫です by jflute (2025/07/24)
        // クラス内で宣言されたフィールドは「インスタンス変数」と呼び、特定の一つのインスタンスに紐づく変数となります。
        // そして、このtest_メソッドは文法的にはインスタンスメソッドと呼ばれて、
        // 同じく特定の一つのインスタンスに紐づくメソッドということでインスタンス変数が参照できます。
        // ということで、インスタンスメソッド内からインスタンス変数を直接参照しているということなので、
        // 特に関数を経由して取得しているとかではありません。
        // #1on1 インスタンスとは？インスタンス変数とは？インスタンスメソッドとは？
        // 一軒家の設計図から一軒家インスタンスを作ったら話。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_int() {
        int sea = instanceDockside;
        log(sea); // your answer? => 0
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_Integer() {
        Integer sea = instanceHangar;
        log(sea); // your answer? => nul
        // 基本型のintと違って、Integerはnullが許容されるっぽい？
        // done ichikawa [ふぉろー] yes, そうなります。 by jflute (2025/07/24)
        // intのようなプリミティブ型と、Integerのようなオブジェクト型と二つありまして、
        // プリミティブ型は null という状態 (変数が何も指し示していない) が発生しないようになっています。
        // ゆえに、intの場合はデフォルトで0が暗黙的に入るのですが、Integer/Stringなどのオブジェクト型は、
        // (インスタンス変数としては) デフォルトで値が何も入らないということで null となります。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_via_method() {
        instanceBroadway = "bbb"; // BigBandBeat無くなるの悲しいです
        // done ichikawa [ざつだん] いやほんとその通り。It don't mean a thing がもう聴けない（＞＜ by jflute (2025/07/24)
        instanceMagiclamp = "magician";
        helpInstanceVariableViaMethod(instanceMagiclamp);
        String sea = instanceBroadway + "|" + instanceDockside + "|" + instanceHangar + "|" + instanceMagiclamp;
        log(sea); // your answer? => bbb|1|null|magician
        // ミュータブルな変数instanceDockside以外は、helpInstanceVariableViaMethodメソッドを呼び出して代入しても値は変わらないはず?
        // → 違った。なぜ？たぶん何かを勘違いしている。
        // done ichikawa [ふぉろー] インスタンス変数はどのインスタンスメソッドからも参照ができるので... by jflute (2025/07/24)
        // instanceBroadway = "bigband"; にて値が差し替わります。
        // test_ も help も instanceBroadway は共有している同じ変数なので。
    }

    private void helpInstanceVariableViaMethod(String instanceMagiclamp) {
        instanceBroadway = "bigband";
        ++instanceDockside;
        instanceMagiclamp = "burn";
    }

    // ===================================================================================
    //                                                                     Method Argument
    //                                                                     ===============
    // -----------------------------------------------------
    //                                 Immutable Method-call
    //                                 ---------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_immutable_methodcall() {
        String sea = "harbor";
        int land = 415;
        helpMethodArgumentImmutableMethodcall(sea, land);
        log(sea); // your answer? => harbor (o)
        // #1on1:
        // o Stringはimmutable
        // o String@concat()は、あたらしく連結した文字列を戻す
        //  i なので、戻して受け取ってtest側のseaを差し替えない限り変わらない
        // 今となっては、helpメソッドの中を読まなくても結果はわかる。=> 可読性
    }

    private void helpMethodArgumentImmutableMethodcall(String sea, int land) {
        ++land;
        String landStr = String.valueOf(land); // is "416"
        sea.concat(landStr); // concat(): harbor416
    }

    // -----------------------------------------------------
    //                                   Mutable Method-call
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_mutable_methodcall() {
        StringBuilder sea = new StringBuilder("harbor"); // sea = 6 + 16 <-違う
        int land = 415;
        helpMethodArgumentMethodcall(sea, land);
        log(sea); // your answer? => 416 + 22 => 438
        // StringBuilder("harbor")が文字数を取ってくるものだと思ってしまったがそうではないらしい
        // immutableなString型の"harbor"がmutableなものとして扱えている、驚き
    }

    private void helpMethodArgumentMethodcall(StringBuilder sea, int land) {
        ++land;
        sea.append(land);
        // #1on1: hashも同じ
        //StringBuilder appended = sea.append(land);
        //log(sea.hashCode(), appended.hashCode());
        // #1on1: thisを戻している理由
        // sea.append(land).append("oneman"); というふうに書きたいから。
        // 業務的には戻り値は不要だけど、書き方の工夫の選択肢のために戻してる。
        // なので、immutableの戻り値と全然質が違う戻り値。
        //
        // return thisのときは、(必要なければ)受け取らなくても良い。by いちかわさん
        // (Javaが、戻り値の受け取りを必須にしてないことで成立)
    }

    // -----------------------------------------------------
    //                                   Variable Assignment
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_variable_assignment() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentVariable(sea, land);
        log(sea); // your answer? => "harbor"
        // TODO ichikawa [自己理解課題] 1on1での説明をさらに頭の中で整理してみてください by jflute (2025/08/18)
        // 変数seaとlandはローカル変数であり、helpMethodArgumentVariableメソッドに渡されているのは値だけ
        // そのため、テストメソッド内で宣言された変数seaとlandがmutableかどうかなどは気にせず、値は変わらないと言える（わざわざヘルプメソッド内部の実装を確認せずとも、今回の場合は変数seaの値が変わっていないことがわかる）
    }

    private void helpMethodArgumentVariable(StringBuilder sea, int land) {
        ++land;
        String seaStr = sea.toString(); // is "harbor"
        sea = new StringBuilder(seaStr).append(land);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Define variables as followings:
     * <pre>
     * o local variable named sea typed String, initial value is "mystic"
     * o local variable named land typed Integer, initial value is null
     * o instance variable named piari typed int, without initial value
     * o show all variables by log() as comma-separated
     * </pre>
     * (変数を以下のように定義しましょう):
     * <pre>
     * o ローカル変数、名前はsea, 型はString, 初期値は "mystic"
     * o ローカル変数、名前はland, 型はInteger, 初期値は null
     * o インスタンス変数、名前はpiari, 型はint, 初期値なし
     * o すべての変数をlog()でカンマ区切りの文字列で表示
     * </pre>
     */
    int piari;

    public void test_variable_writing() {
        // define variables here
        String sea = "mystic";
        Integer land = null;
        log(sea, land, piari);

        // #1on1: sample変数名
        // foo, bar, baz, qux, quux, corge...
        // hoge, fuga, ...
        //
        // jflute流:
        // sea, land, piari, bonvo, dstore, amba, miraco, dohotel
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Make your original exercise as question style about variable. <br>
     * (変数についてあなたのオリジナルの質問形式のエクササイズを作ってみましょう)
     * <pre>
     * _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
     * your question here (ここにあなたの質問を):
     *
     * 以下の出力はどうなるか
     *
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    // もうちょい面白い問題作りたい
    // javaだとローカル変数を参照渡しできない
    // done ichikawa [いいね] Goodなエクササイズです by jflute (2025/08/18)
    int num1 = 1;

    public void test_variable_yourExercise() {
        // write your code here
        int num2 = 2;
        add_num(num2);
        log(num1, num2); // your answer? =>
    }

    private void add_num(int num2) {
        num1++;
        num2++;
    }
}

// #1on1: 文法用語
// クラス変数: staticが付いている変数 (特定のインスタンスに紐付かない、共通的)
// クラスメソッド: staticが付いているメソッド (特定のインスタンスに紐付かない、共通的)
//  → 実際の現場では、クラス変数というよりは、みんなstatic変数、staticメソッドって呼ぶ
//
// インスタンス変数: 特定のインスタンスに属している変数
// インスタンスメソッド: 特定のインスタンスに属しているメソッド (インスタンス変数が参照できる)
//  → 単にメソッドって言うと、インスタンスメソッドを指し示すことが多い、という感覚

// クラス変数の挙動の確認
class Main {
    public static void main(String[] args) {
        Sample sampleA = new Sample();
        Sample sampleB = new Sample();
        Sample sampleC = new Sample();

        sampleA.setNum(30);
        sampleB.setNum(40);
        sampleC.b = 50; // Sampleクラスのstaticフィールドbに直接アクセス
        System.out.println(sampleA.getNum());
        System.out.println(sampleB.getNum());
        System.out.println(sampleC.b);
    }
}

class Sample {

    // #1on1: publicだから直接アクセスができちゃうだけで、privateにすれば直接アクセスはできないようにできる
    // setterがあるんだったら、変数はprivateにするのがオーソドックスではある。
    public static int b;

    public void setNum(int value) {
        b = value;
    }

    public int getNum() {
        return b;
    }
}
