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

import java.util.ArrayList;
import java.util.List;

import org.docksidestage.unit.PlainTestCase;

// done ichikawa [読み物課題] 質問のコツその一: なんでその質問してるのか？も伝えよう by jflute (2025/09/01)
// https://jflute.hatenadiary.jp/entry/20170611/askingway1
// 質問者自身が「この質問・疑問、"いびつ"だな」と感じられるようになる訓練として、どんなことをやる・心掛けると良さそうか？ by n.ichikawa  (2025/09/29)
// #1on1: jfluteは、(特に昔)質問する前に2,3分!?質問のイメージトレーニングしていた。
// 先輩を練習台にしてOK。

// done ichikawa [読み物課題] プログラマーに求められるデザイン脳 by jflute (2025/09/01)
// https://jflute.hatenadiary.jp/entry/20170623/desigraming
// 命名も、構造も、コメントも、何かと「デザイン」するときにはそれだけを考えて解決できるもの（問題）ではないよな、、、と改めて感じた次第です。
// 個人的な感覚として、デザインはアートと違って何らかの問題を解決するための行為だと認識しているので、問題を解決したいなら、それ（目の前の困りごととか）を見ているだけでは当然解決できないだろうな、、、とも思いました。 by n.ichikawa  (2025/09/29)
// #1on1: デザインとアートの違い、デザインは全体バランスを整える作業と言っても過言ではない。

// done ichikawa @authorお願いします by jflute (2025/09/01)
/**
 * The test of if-for. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author Ichy14 (n.ichikawa@bizreach.co.jp)
 */
public class Step02IfForTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        if Statement
    //                                                                        ============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_if_basic() { // example, so begin from the next method
        int sea = 904;
        if (sea >= 904) {
            sea = 2001;
        }
        log(sea); // your answer? => 2001
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_else_basic() {
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else {
            sea = 7;
        }
        log(sea); // your answer? => 7Î
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_elseif_basic() {
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else if (sea >= 904) {
            sea = 7;
        } else if (sea >= 903) {
            sea = 8;
        } else {
            sea = 9;
        }
        log(sea); // your answer? => 7
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_elseif_nested() {
        boolean land = false;
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else if (land && sea >= 904) {
            sea = 7;
        } else if (sea >= 903 || land) {
            sea = 8;
            if (!land) {
                land = true;
            } else if (sea <= 903) {
                sea++;
            }
        } else if (sea == 8) {
            sea++;
            land = false;
        } else {
            sea = 9;
        }
        if (sea >= 9 || (sea > 7 && sea < 9)) {
            sea--;
        }
        if (land) {
            sea = 10;
        }
        log(sea); // your answer? => 8
        // よく見たら94行目からif elseじゃなくて独立したいif文になってた、ミスった
        // #1on1: まず漠然読みで構造だけ分解する。
        // 細かいところ読みながら構造も一緒にってなると、俯瞰がしづらくなるので構造把握ミスが起きやすい。
        //
        // その後、ギャンブルポイントを見つけてフォーカス読み。
        // 漠然読み/フォーカス読みの実践。
        //
        // 目的意識を明確に持って読むこと大事 by いちかわさん
        // でかいコードを読んでるときこそ何知りたいんだっけ？になりがち by いちかわさん
    }

    // ===================================================================================
    //                                                                       for Statement
    //                                                                       =============
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_inti_basic() {
        List<String> stageList = prepareStageList();
        String sea = null;
        for (int i = 0; i < stageList.size(); i++) {
            String stage = stageList.get(i);
            if (i == 1) {
                sea = stage;
            }
        }
        log(sea); // your answer? => dockside
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_foreach_basic() {
        List<String> stageList = prepareStageList();
        String sea = null;
        for (String stage : stageList) { // listの全ての要素に対して操作する時はこの書き方ができる。これはsyntax sugar？
            sea = stage;
        }
        log(sea); // your answer? => magiclamp

        // #1on1: 伝統的ないんとあいのfor文とすっきりした拡張for文の違い
        // 伝統的ないんとあいのfor文: 1995あたり
        // すっきりした拡張for文: 2005あたり
        //
        // 現場では、すっかり慣れて、拡張for文がメインになってきた。
        // (int indexって自分で変数宣言しちゃえば、代替もできるし)
        // こっちばっかりになってきたので、普通のfor文って言った時...拡張for文!?
        // 拡張for文って言葉、現場の会話であんまり出てこないかも。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_foreach_continueBreak() {
        List<String> stageList = prepareStageList();
        String sea = null;
        for (String stage : stageList) {
            if (stage.startsWith("br")) {
                continue;
            }
            sea = stage;
            if (stage.contains("ga")) {
                break;
            }
        }
        log(sea); // your answer? => hangar
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_listforeach_basic() {
        List<String> stageList = prepareStageList();
        StringBuilder sb = new StringBuilder();
        stageList.forEach(stage -> {
            if (sb.length() > 0) {
                return;
            }
            if (stage.contains("i")) {
                sb.append(stage);
            }
        });
        String sea = sb.toString();
        log(sea); // your answer? => dockside

        // #1on1: ３つ目のループ
        // 伝統的ないんとあいのfor文: 1995あたり
        // すっきりした拡張for文: 2005あたり
        // forEach()メソッド(文法ではない): 2015あたり
        //  → ローカル変数書き換えできない、continue/breakできない
        // なぜ、できないだらけのループが出てきた？理由もなく導入しないでしょうということで。
        // done ichikawa なぜをちょっと考えてみてください by jflute (2025/09/01)
        // 全く思いつかなかった＆調べてもそれらしい回答は見つけられなかった、、、
        // 「関数型インターフェース」を導入することの意義があるっぽいようなことを書いている記事は見つけたが、よくわからん（https://www.bold.ne.jp/engineer-club/java-foreach#i）
        // 関数型インターフェースもまだよくわからない
        // #1on1: 「関数型プログラミング」の視点から深堀りしていくと...
        // 関数型プログラミングのコンセプトとして、副作用を起こさないようにする、ってのがある。
        // 関数のコンセプトとしては、純粋に引数と戻り値だけの関係性で、他のデータに影響されない、影響しない、が特性。
        // メインの作用(計算結果の戻り値)に対して、副作用(別の結果)が生まれないように。
        // ここだと、seaという変数を書き換えるという副作用を発生させないように。
        //
        // "できない" というのは主観的な表現で、視点を変えると "やらない方がいいことを防いでる" とも言える。
        // 車の100kmボタンの例。
        // "できない" は、"安全/安心" に置き換えることもできる。
        // (ローカル変数書き換えやcontinue/breakを頻繁にやるか？やらないなら普段はできない方がいい)
        //
        // できないデザイン
        //
        // 一方で、細分化されて、選択肢が増えると、選ぶ(学ぶ)負荷が高くなる。
        // そのジレンマとの戦い。
        //
        // ちなみに、StringBuilder の append() 副作用 by いちかわさん
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Make list containing "a" from list of prepareStageList() and show it as log by loop. (without Stream API) <br>
     * (prepareStageList()のリストから "a" が含まれているものだけのリストを作成して、それをループで回してログに表示しましょう。(Stream APIなしで))
     */
    public void test_iffor_making() {
        // write if-for here
        List<String> stageList = prepareStageList(); // Stream APIを使えばこの時点で目的のリストが作れるらしい？
        List<String> stageListWithA = new ArrayList<>();
        stageList.forEach(stage -> {
            if (stage.contains("a")) {
                stageListWithA.add(stage);
            }
        });
        stageListWithA.forEach(stage -> {
            log(stage);
        });
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Change foreach statement to List's forEach() (keep result after fix) <br>
     * (foreach文をforEach()メソッドへの置き換えてみましょう (修正前と修正後で実行結果が同じになるように))
     */
    String sea = null;

    public void test_iffor_refactor_foreach_to_forEach() {
        List<String> stageList = prepareStageList();
        //        String sea = null;
        //        for (String stage : stageList) {
        //            if (stage.startsWith("br")) {
        //                continue;
        //            }
        //            sea = stage;
        //            if (stage.contains("ga")) {
        //                break;
        //            }
        //        }
        //        log(sea); // should be same as before-fix

        //        stageList.forEach(stage -> {
        //            sea = stage;
        //            if (stage.contains("ga")) {
        //                break;
        //            }
        //        });
        // forEach()メソッドはcontinueやbreakは使えない。
        // breakしたい時は例外を投げるようにする、と調べてわかったが、長くなるし、他の方法があるはず？
        // ローカル変数も使えないらしい。インスタンス変数ならいいらしいが、、、

        //        final String[] sea = {null}; // 参照型にしておく  <- 参照型にしておく、とは、、、？tab補完でやらせたけどコメントの意味がよくわからん
        //        stageList.forEach(stage -> {
        //            sea[0] = stage;
        //            if (stage.startsWith("ga")) {
        //                // ここからどうする？
        //            }
        //        });

        try {
            stageList.forEach(stage -> {
                if (stage.startsWith("br")) {
                    return;
                }
                sea = stage;
                if (stage.contains("ga")) {
                    throw new RuntimeException();
                }
            });
        } catch (RuntimeException e) {}
        log(sea);

        // #1on1: Lambda式で外側のローカル変数を代入できない理由について。
        // ローカル変数ってのは、そのメソッドの中だけで生きて使われる想定(コンセプト)のもの。
        // Lambda式ってのは、メソッドの中で宣言されているけど、(感覚的には)別クラスの別メソッドと言える。
        // 別メソッド間で互いのローカル変数を書き換えるってのはコンセプトからは外れる。
        // ということから、Javaはローカル変数は他のメソッドから書き換えられないようにしている。
        // (Lambda式についてさらに深掘りはstep8にて)
        //
        // インスタンス変数は、public/privateの制御が自分でできるので、参照さえできたら書き換えできるコンセプト。
        // 厳密にはLambda式がインナークラスで外側クラスのインスタンス変数を参照できるから書き換えもできる。

        // #1on1: インスタンス変数技は他のメソッドにも影響を与えるので、ちょっと豪快ではある。
        // ただ、mutable技もなんだかなー感はあるので、ぶっちゃけどっちもどっちで、パズルとしてどっちでもOK。

        // #1on1:  || sea.contains("ga") のやり方のふぉろー
        // 確かにクレバーな感じはありますが、パフォーマンスで言うと、無駄ループがあるので...
        // 例外をthrowするっていうある意味どストレートなやり方の方が良いかも。
    }

    /**
     * Make your original exercise as question style about if-for statement. <br>
     * (if文for文についてあなたのオリジナルの質問形式のエクササイズを作ってみましょう)
     * <pre>
     * _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
     * your question here (ここにあなたの質問を):
     *
     * 変数iの中身は？
     *
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    public void test_iffor_yourExercise() {
        // write your code here
        List<String> stageList = prepareStageList();
        List<String> newStageList = new ArrayList<>();
        for (int i = 0; i < stageList.size(); i++) {
            String stage = stageList.get(i);
            newStageList.add(stage);
            if (stage.length() <= 6) {
                newStageList.add("capecod");
                log(i);
            }
        }
    }
    // done ichikawa [いいね] ノイズがあって良い問題 by jflute (2025/09/01)

    // ===================================================================================
    //                                                                        Small Helper
    //                                                                        ============
    private List<String> prepareStageList() {
        List<String> stageList = new ArrayList<>();
        stageList.add("broadway");
        stageList.add("dockside");
        stageList.add("hangar");
        stageList.add("magiclamp");
        return stageList;
    }
}
