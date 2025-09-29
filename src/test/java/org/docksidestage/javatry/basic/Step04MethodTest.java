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

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of method. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author Ichy14 (n.ichikawa@bizreach.co.jp)
 */
public class Step04MethodTest extends PlainTestCase {

    // ===================================================================================
    //                                                                         Method Call
    //                                                                         ===========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_method_call_basic() {
        String sea = supplySomething();
        log(sea); // your answer? => "over"
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_call_many() {
        String sea = functionSomething("mystic");
        consumeSomething(supplySomething());
        runnableSomething();
        log(sea); // your answer? => "mysmys"
    }

    private String functionSomething(String name) {
        String replaced = name.replace("tic", "mys");
        log("in function: {}", replaced);
        return replaced;
    }

    private String supplySomething() {
        String sea = "over";
        log("in supply: {}", sea);
        return sea;
    }

    private void consumeSomething(String sea) {
        log("in consume: {}", sea.replace("over", "mystic"));
    }

    private void runnableSomething() {
        // #1on1: いいね！
        String sea = "outofshadow";  // このショー知らなかったです、この次のソングオブミラージュは一回だけ見たことがあるような気がします
        log("in runnable: {}", sea);
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_object() {
        St4MutableStage mutable = new St4MutableStage();
        int sea = 904;
        boolean land = false;
        helloMutable(sea - 4, land, mutable);
        if (!land) {
            sea = sea + mutable.getStageName().length();
        }
        log(sea); // your answer? => 905
        // mutableとimmutableなオブジェクト（インスタンス）の扱いをまだ正しく理解できていないっぽい（helloMutable()メソッドでセットした値が、メソッド外で実体化したインスタンスの中身になっているのが理解できていない）
        // ↑それはそれとして、なんで910になった？helloMutable()メソッドで最初の引数seaに900が渡されているからhelloMutable()メソッドでreturnされる値は901のはずでは？
        // ↑helloMutable()メソッドでreturnしているけれど受け取ってないな、、、→ seaは変わらず904

        // 変数mutableが持つのは参照先を指す値（ポインタ）である。javaは値渡ししかできないので、helloMutable()メソッドにmutableを渡すとローカル変数piariにmutableのポインタがコピーされる（＝代入される？）
        // mutableとpiariは同じインスタンスを指している（同じアドレスを持つ）ので、piari.setStageName()でインスタンスに生えているstageNameフィールドのアドレスを変更している
        // インスタンス自体のアドレスが共有されて、そのインスタンスのフィールドのアドレスが変更されている、というのが理解できていなかったっぽい。
    }

    private int helloMutable(int sea, Boolean land, St4MutableStage piari) {
        sea++;
        land = true;
        piari.setStageName("mystic");
        return sea;
    }

    private static class St4MutableStage {

        private String stageName;

        public String getStageName() {
            return stageName;
        }

        public void setStageName(String stageName) {
            this.stageName = stageName;
        }
    }

    // ===================================================================================
    //                                                                   Instance Variable
    //                                                                   =================
    private int inParkCount;
    private boolean hasAnnualPassport;

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_instanceVariable() {
        hasAnnualPassport = true;
        int sea = inParkCount;
        offAnnualPassport(hasAnnualPassport);
        for (int i = 0; i < 100; i++) {
            goToPark();
        }
        ++sea;
        sea = inParkCount;
        log(sea); // your answer? => 101
        // `++sea`しているものの、次の行でinParkCountを代入しているから100になる（ぱっと見で100にして+1するんだろうな、と思い込んでしまった。それをするなら順番を逆にする必要がある）
        // #1on1: 自分で学びのリカバリできてるのでOK (2025/09/29)
    }

    private void offAnnualPassport(boolean hasAnnualPassport) {
        hasAnnualPassport = false;
    }
    // 【疑問】インスタンス変数hasAnnualPassportを引数に受け取ってそれを使うにはどうする？
    // #1on1: インスタンス変数そのものを引数にすることはできない。
    // というか、呼び出し側がインスタンス変数だろうがローカル変数だろうが、変数自体をそのまま渡すことはできない。
    // あくまで呼び出し側がその変数の値(アドレス/参照)を取り出して、呼び出すメソッドの引数に渡している。
    // 呼び出された側からすると、渡された値(アドレス/参照)が、元々インスタンス変数だったのかローカル変数だったのかわからない。
    // 箱そのものを渡すのか？箱の中身を渡すのか？後者になる。

    private void goToPark() {
        if (hasAnnualPassport) {
            ++inParkCount;
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    // write instance variables here
    /**
     * Make private methods as followings, and comment out caller program in test method:
     * <pre>
     * o replaceAwithB(): has one argument as String, returns argument replaced "A" with "B" as String 
     * o replaceCwithB(): has one argument as String, returns argument replaced "C" with "B" as String 
     * o quote(): has two arguments as String, returns first argument quoted by second argument (quotation) 
     * o isAvailableLogging(): no argument, returns private instance variable "availableLogging" initialized as true (also make it separately)  
     * o showSea(): has one argument as String argument, no return, show argument by log()
     * </pre>
     * (privateメソッドを以下のように定義して、テストメソッド内の呼び出しプログラムをコメントアウトしましょう):
     * <pre>
     * o replaceAwithB(): 一つのString引数、引数の "A" を "B" に置き換えたStringを戻す 
     * o replaceCwithB(): 一つのString引数、引数の "C" を "B" に置き換えたStringを戻す 
     * o quote(): 二つのString引数、第一引数を第二引数(引用符)で囲ったものを戻す 
     * o isAvailableLogging(): 引数なし、privateのインスタンス変数 "availableLogging" (初期値:true) を戻す (それも別途作る)  
     * o showSea(): 一つのString引数、戻り値なし、引数をlog()で表示する
     * </pre>
     */
    public void test_method_making() {
        // use after making these methods
        String replaced = replaceCwithB(replaceAwithB("ABC"));
        String sea = quote(replaced, "'");
        if (isAvailableLogging()) {
            showSea(sea);
        }
    }

    // TODO jflute 次回1on1にて補足 (2025/09/29)
    
    // write methods here
    private boolean availableLogging = true;

    private String replaceAwithB(String str) {
        return str.replace("A", "B");
    }

    private String replaceCwithB(String str) {
        return str.replace("C", "B");
    }

    private String quote(String str, String quotation) {
        return quotation + str + quotation;
    }

    private boolean isAvailableLogging() {
        return availableLogging;
    }

    private void showSea(String str) {
        log(str);
    }
}
