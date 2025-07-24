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
    // `exrends`キーワードを使うによりStep01VariableTestはPlainTestCaseを継承している
    // ===================================================================================
    //                                                                      Local Variable
    //                                                                      ==============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_variable_basic() { // example, so begin from the next method
        String sea = "mystic";
        log(sea); // your answer? => mystic
        // TODO: `log()`メソッドの実装を読解したい
        // TODO ichikawa [ふぉろー] よーし、1on1で一緒に読みますか(^^ by jflute (2025/07/24)
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
        // TODO: 変数の型を確認する方法は？
        // TODO ichikawa [ふぉろー] 演算中の型の理解はそれでOKです。Stringが含まれたらそこにどんどん引きずりこまれる感じですね by jflute (2025/07/24)
        // +演算子の型変換は暗黙で行われるので、IDE上でもあんまり確認する方法がないかもですね。
        // まあ例えばこう書いてみて...
        //  e.g. int a = 1 + "b";
        // → コンパイルエラーのメッセージに「Type mismatch: cannot convert from String to int」とあるので、
        // これで String になっていることを確認する、ってことはできそうです。

        // TODO jflute 1on1にて、null文字列に変換されることに関してフォロー予定 (2025/07/24)
        // (↑これはくぼ用のtodoということでそのまま残しておいてください)
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
        // TODO ichikawa [いいね] immutableという概念を理解されているの素晴らしいです by jflute (2025/07/24)
        // TODO ichikawa [ふぉろー] 1on1のときぜひsyntax sugarの話させてください by jflute (2025/07/24)

        // TODO jflute 1on1にてimmutable周りとJavaのsyntax sugarの話 (2025/07/24)
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
        // TODO ichikawa [ふぉろー] 初期値に関してはそれで認識大丈夫です by jflute (2025/07/24)
        // クラス内で宣言されたフィールドは「インスタンス変数」と呼び、特定の一つのインスタンスに紐づく変数となります。
        // そして、このtest_メソッドは文法的にはインスタンスメソッドと呼ばれて、
        // 同じく特定の一つのインスタンスに紐づくメソッドということでインスタンス変数が参照できます。
        // ということで、インスタンスメソッド内からインスタンス変数を直接参照しているということなので、
        // 特に関数を経由して取得しているとかではありません。
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
        // TODO ichikawa [ふぉろー] yes, そうなります。 by jflute (2025/07/24)
        // intのようなプリミティブ型と、Integerのようなオブジェクト型と二つありまして、
        // プリミティブ型は null という状態 (変数が何も指し示していない) が発生しないようになっています。
        // ゆえに、intの場合はデフォルトで0が暗黙的に入るのですが、Integer/Stringなどのオブジェクト型は、
        // (インスタンス変数としては) デフォルトで値が何も入らないということで null となります。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_via_method() {
        instanceBroadway = "bbb"; // BigBandBeat無くなるの悲しいです
        // TODO ichikawa [ざつだん] いやほんとその通り。It don't mean a thing がもう聴けない（＞＜ by jflute (2025/07/24)
        instanceMagiclamp = "magician";
        helpInstanceVariableViaMethod(instanceMagiclamp);
        String sea = instanceBroadway + "|" + instanceDockside + "|" + instanceHangar + "|" + instanceMagiclamp;
        log(sea); // your answer? => bbb|1|null|magician
        // ミュータブルな変数instanceDockside以外は、helpInstanceVariableViaMethodメソッドを呼び出して代入しても値は変わらないはず?
        // → 違った。なぜ？たぶん何かを勘違いしている。
        // TODO ichikawa [ふぉろー] インスタンス変数はどのインスタンスメソッドからも参照ができるので... by jflute (2025/07/24)
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
        log(sea); // your answer? => 
    }

    private void helpMethodArgumentImmutableMethodcall(String sea, int land) {
        ++land;
        String landStr = String.valueOf(land); // is "416"
        sea.concat(landStr);
    }

    // -----------------------------------------------------
    //                                   Mutable Method-call
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_mutable_methodcall() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentMethodcall(sea, land);
        log(sea); // your answer? => 
    }

    private void helpMethodArgumentMethodcall(StringBuilder sea, int land) {
        ++land;
        sea.append(land);
    }

    // -----------------------------------------------------
    //                                   Variable Assignment
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_variable_assignment() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentVariable(sea, land);
        log(sea); // your answer? => 
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
    public void test_variable_writing() {
        // define variables here
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
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    public void test_variable_yourExercise() {
        // write your code here
    }
}
