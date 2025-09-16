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
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of data type. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author Ichy14 (n.ichikawa@bizreach.co.jp)
 */
public class Step03DataTypeTest extends PlainTestCase {

    // ===================================================================================
    //                                                                          Basic Type
    //                                                                          ==========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_datatype_basicType() {
        String sea = "mystic";
        Integer land = 416;
        // #1on1: Date/DateTimeという言葉、日付と日時
        // LocalDate: 2015年くらいから入ったもの。
        // 昔は、java.util.Date というクラスがあった。
        // #1on1: 日付の脱線おもしろ話。宇宙の話。
        LocalDate piari = LocalDate.of(2001, 9, 4);
        LocalDateTime bonvo = LocalDateTime.of(2001, 9, 4, 12, 34, 56);
        Boolean dstore = true;
        BigDecimal amba = new BigDecimal("9.4");

        piari = piari.plusDays(1);
        land = piari.getYear();
        // ↑型が違うはずなのに代入できているのはなぜ？ intをIntegerに変換できているのは、そういう設計がされているということ？
        // #1on1: オートボクシングという機能で、プリミティブとオブジェクト型をスムーズに行き来できる。 (2025/09/16)
        // 2005年くらい(Java5)から入ったので...それまでは、land = Integer.valueOf(piari.getYear());
        // コンパイラーがどんどん発展している？ by いちかわさん => yes (Lambda引数の型推論の例も)
        //
        // しかも、どうやら使われていないらしい（そういう警告が出ている）。どういうこと？
        // → 下の方でlandに対して代入が行われている＋ここで代入されたlandは使われていないので警告されているということか？IDE賢いなあ。。。
        //
        bonvo = bonvo.plusMonths(1);
        land = bonvo.getMonthValue();
        land--;
        if (dstore) {
            BigDecimal addedDecimal = amba.add(new BigDecimal(land));
            sea = String.valueOf(addedDecimal);
        }
        log(sea); // your answer? => "18.4" <-当たってた嬉しい
    }

    // ===================================================================================
    //                                                                           Primitive
    //                                                                           =========
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_datatype_primitive() {
        // #1on1: 現実使われている数値型の話。intがほとんどで、大きければlong
        byte sea = 127; // max
        short land = 32767; // max
        int piari = 1;
        long bonvo = 9223372036854775807L; // max

        // #1on1: 誤差の問題もあるので、最初からBigDecimal。
        // プリミティブ型って本当にコンピューターに近いプリミティブ。
        float dstore = 1.1f; // long, float, doubleは末尾にl, fやdをつけるのは可読性のため？
        double amba = 2.3d;

        // #1on1: 通常は String でほとんど。内部の処理ではcharはよく使われる。
        char miraco = 'a';
        
        // #1on1: 逆に、Booleanよりもほとんど boolean が使われる
        // (時々、JSONの項目でBooleanものを扱う時に、厳密にはnullがあり得るからBooleanもある)
        boolean dohotel = miraco == 'a'; // この書き方面白い
        if (dohotel && dstore >= piari) { // 型が違っていても数値の大小比較はできるらしい？
            bonvo = sea;
            land = (short) bonvo;
            bonvo = piari;
            sea = (byte) land;
            if (amba == 2.3D) {
                sea = (byte) amba;
            }
        }
        if ((int) dstore > piari) {
            sea = 0;
        }
        log(sea); // your answer? => 2 <-当たってた嬉しい
        // #1on1: こういう縮小変換とか一瞬迷いそうなコードは極力書かないってのが教訓 (2025/09/16)
    }

    // ===================================================================================
    //                                                                              Object
    //                                                                              ======
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_datatype_object() {
        St3ImmutableStage stage = new St3ImmutableStage("hangar");
        String sea = stage.getStageName();
        log(sea); // your answer? => "hangar"
    }

    // #1on1: JavaのImmutableクラスの基本形
    // DDD の Value Object のJavaにおける実現方法の一つ。
    // 消費税計算をどこでやる？話。
    // String と BigDecimal も ImmutableStage と同じ構造をしている。
    private static class St3ImmutableStage {

        // #1on1: 変数もimmutable, インスタンスもimmutable
        private final String stageName;

        public St3ImmutableStage(String stageName) {
            this.stageName = stageName;
        }
        
        public String getStageName() {
            return stageName;
        }
    }
}
