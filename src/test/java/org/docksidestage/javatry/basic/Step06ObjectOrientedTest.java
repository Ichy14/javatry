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

import org.docksidestage.bizfw.basic.buyticket.Ticket;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth;
import org.docksidestage.bizfw.basic.buyticket.TicketType;
import org.docksidestage.bizfw.basic.objanimal.*;
import org.docksidestage.bizfw.basic.objanimal.loud.AlarmClock;
import org.docksidestage.bizfw.basic.objanimal.loud.Loudable;
import org.docksidestage.bizfw.basic.objanimal.runner.FastRunner;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of object-oriented. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author your_name_here
 */
public class Step06ObjectOrientedTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        About Object
    //                                                                        ============
    // -----------------------------------------------------
    //                                        Against Object
    //                                        --------------
    /**
     * Fix several mistakes (except simulation) in buying one-day passport and in-park process. <br>
     * (OneDayPassportを買って InPark する処理の中で、(simulationを除いて)間違いがいくつかあるので修正しましょう)
     */
    public void test_objectOriented_aboutObject_againstObject() {
        // simulation: actually these variables should be more wide scope
        int oneDayPrice = 7400;
        int quantity = 10;
        Integer salesProceeds = null;

        //
        // [buy one-day passport]
        //
        // simulation: actually this money should be from customer
        int handedMoney = 10000;
        if (quantity <= 0) {
            throw new IllegalStateException("Sold out");
        }
        if (handedMoney < oneDayPrice) {
            throw new IllegalStateException("Short money: handedMoney=" + handedMoney);
        }
        // #1on1: 一行だけ見て間違いとわかるバグと、複数行の関連性を見ないと間違いを見つけられないバグ (2026/04/08)
        // → 流れの可読性というが大事 (流れのバグは発見しづらいので)
        // → 書く側としては、もし間違ってたとしたら、すぐに見つけてもらえるようなコードを意識する by いちかわさん
        --quantity;  // チケットの残数だけでなく、手渡された金額の検証が通って初めてチケットを減らす処理を行うのが適切では？
        salesProceeds = oneDayPrice;  // 手渡された金額を売上金額にするのは不適切では？ (お釣りがある場合もあるので、チケットの価格を売上金額にするのが適切)

        //
        // [ticket info]
        //
        // simulation: actually these variables should be more wide scope
        int displayPrice = oneDayPrice;  // 現状だと割引とかもないし、チケットの金額と表示価格を分ける必要も今のところなさそう
        boolean alreadyIn = false;

        // other processes here...
        // ...
        // ...

        //
        // [do in park now!!!]
        //
        // simulation: actually this process should be called by other trigger
        if (alreadyIn) {
            // ここのエラーメッセージで表示金額を表示されたところで嬉しいのか微妙だと感じた、、、（「表示金額がこれと言うことは、このチケットか？」と言う思考の1ステップが必要？）
            throw new IllegalStateException("Already in park by this ticket: displayPrice=" + displayPrice);
        }
        alreadyIn = true;

        //
        // [final process]
        //
        // TODO done ichikawa 最後の間違いがここに by jflute (2026/04/08)
        // #1on1: ここでの学び (2026/04/08)
        //
        // 1. オブジェクト化することの実務的な意義 (間違いポイントが局所化される)
        // o まさしくタイプセーフ
        // o オブジェクトの単位/粒度
        // o DDD, Value Object
        // o 現状は、int,int,intになってるから、意味の違うものを簡単に代入できちゃう
        //
        // 2. もうint,intを呼ばざるを得ない時: 指差し確認 (5秒10秒)
        // o じゃあ、一行一行指差し確認するか？ → 現実きつい (日が暮れちゃう)
        // o じゃあ、間違えやすいところだけ指差し確認する → 間違えやすいところってどこ？
        // o ある程度は経験がないと判断できないところ (ベテランはその経験から間違えポイントを知ってる)
        // o ある程度はセオリーで判断できるところもあるし、経験がそのセオリーを積み重ねていって欲しい
        // o ものづくりスキル
        // o 一般的な間違いポイントと、自分ならではの間違いポイントもある
        //
        saveBuyingHistory(quantity, salesProceeds, displayPrice, alreadyIn);
    }

    private void saveBuyingHistory(int quantity, Integer salesProceeds, int displayPrice, boolean alreadyIn) {
        if (alreadyIn) {
            // simulation: only logging here (normally e.g. DB insert)
            showTicketBooth(quantity, salesProceeds);
            showYourTicket(displayPrice, alreadyIn);
        }
    }

    private void showTicketBooth(int quantity, Integer salesProceeds) {
        log("Ticket Booth: quantity={}, salesProceeds={}", quantity, salesProceeds);
    }

    private void showYourTicket(int displayPrice, boolean alreadyIn) {
        log("Ticket: displayPrice={}, alreadyIn={}", displayPrice, alreadyIn);
    }

    // -----------------------------------------------------
    //                                          Using Object
    //                                          ------------
    /**
     * Read (analyze) this code and compare with the previous test method, and think "what is object?". <br>
     * (このコードを読んで(分析して)、一つ前のテストメソッドと比べて、「オブジェクトとは何か？」を考えてみましょう)
     */
    public void test_objectOriented_aboutObject_usingObject() {
        //
        // [ticket booth info]
        //
        TicketBooth booth = new TicketBooth();

        // *booth has these properties:
        //int oneDayPrice = 7400;
        //int quantity = 10;
        //Integer salesProceeds = null;

        //
        // [buy one-day passport]
        //
        // if step05 has been finished, you can use this code by jflute (2019/06/15)
        Ticket ticket = booth.buyOneDayPassport(10000);
//        booth.buyOneDayPassport(10000); // as temporary, remove if you finished step05
//        Ticket ticket = new Ticket(7400, TicketType.ONE_DAY); // also here

        // *buyOneDayPassport() has this process:
        //if (quantity <= 0) {
        //    throw new TicketSoldOutException("Sold out");
        //}
        //if (handedMoney < oneDayPrice) {
        //    throw new TicketShortMoneyException("Short money: handedMoney=" + handedMoney);
        //}
        //--quantity;
        //salesProceeds = handedMoney;

        // *ticket has these properties:
        //int displayPrice = oneDayPrice;
        //boolean alreadyIn = false;

        // other processes here...
        // ...
        // ...

        //
        // [do in park now!!!]
        //
        ticket.doInPark();

        // *doInPark() has this process:
        //if (alreadyIn) {
        //    throw new IllegalStateException("Already in park by this ticket: displayPrice=" + displayPrice);
        //}
        //alreadyIn = true;

        //
        // [final process]
        //
        saveBuyingHistory(booth, ticket);
    }

    private void saveBuyingHistory(TicketBooth booth, Ticket ticket) {
        if (ticket.isAlreadyIn()) {
            // only logging here (normally e.g. DB insert)
            doShowTicketBooth(booth);
            doShowYourTicket(ticket);
        }
    }

    private void doShowTicketBooth(TicketBooth booth) {
        log("Ticket Booth: quantity={}, salesProceeds={}", booth.getOneDayPassQuantity(), booth.getSalesProceeds());
    }

    private void doShowYourTicket(Ticket ticket) {
        log("Your Ticket: displayPrice={}, alreadyIn={}", ticket.getDisplayPrice(), ticket.isAlreadyIn());
    }

    // write your memo here:
    // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
    // what is object?
    // 意味（情報と操作）のまとまりだと思う。
    // _/_/_/_/_/_/_/_/_/_/
    // #1on1: データ同士の意味的な関連性をしっかり見つけること (2026/04/08)
    //
    // オブジェクト指向の三大原則: 継承、ポリモーフィズム、カプセル化
    // よりも前に大事なこと。
    //

    // ===================================================================================
    //                                                              Polymorphism Beginning
    //                                                              ======================
    /**
     * What string is sea and land variable at the method end? <br>
     * (メソッド終了時の変数 sea, land の中身は？)
     */
    public void test_objectOriented_polymorphism_1st_concreteOnly() {
        Dog dog = new Dog();
        BarkedSound sound = dog.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => "wan"
        int land = dog.getHitPoint();
        log(land); // your answer? => 10
        // 吠えた後だからHPが減るってのを（Animalクラス読んだのに）忘れてた...orz
        // とはいえ、なんで3も減ってる？
        // -> bark()の中で呼び出されてるそれぞれのメソッドでdownHitPoint()してるからだ。理解。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_2nd_asAbstract() {
        Animal animal = new Dog();  // お、今度はAnimalクラスとしてDogを扱ってるな。とはいえ、その旨みはここにはなさそう？
        BarkedSound sound = animal.bark(); // #1on1: 超ミクロでは、1行目はDog依存だけど、2行目以降はDogに依存してない
        String sea = sound.getBarkWord();
        log(sea); // your answer? => "wan"
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_3rd_fromMethod() {
        // #1on1: Dogへの直接の依存はなくなった (DogをCatに変える時、このメソッド自体は1文字も修正が不要)
        // 1文字でもいじったらヒューマンエラーが発生する可能性があると捉えてテストしないといけない。
        Animal animal = createAnyAnimal();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => "wan"
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
    }

    private Animal createAnyAnimal() {
        return new Dog();
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_4th_toMethod() {
        Dog dog = new Dog();
        doAnimalSeaLand_for_4th(dog);
    }

    private void doAnimalSeaLand_for_4th(Animal animal) {
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => "wan"
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_5th_overrideWithSuper() {
        Animal animal = new Cat();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => "nya-"
        int land = animal.getHitPoint();
        log(land); // your answer? => 5  // 残HPが奇数の時に2減る仕様、謎すぎる、、、しかも一度奇数になったらずっと2減る、、、低燃費だ、、、
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_6th_overriddenWithoutSuper() {
        Animal animal = new Zombie();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => "uooo"
        int land = animal.getHitPoint();
        log(land); // your answer? => -1
    }

    /**
     * What is happy if you can assign Dog or Cat instance to Animal variable? <br>
     * (Animal型の変数に、DogやCatなどのインスタンスを代入できると何が嬉しいのでしょう？)
     */
    public void test_objectOriented_polymorphism_7th_whatishappy() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // what is happy?
        // 同じ型のオブジェクトとして扱えるので、一括処理（ListやMapに入れて処理するとか）ができるのが嬉しいと思う。
        // _/_/_/_/_/_/_/_/_/_/
        // #1on1: 3rd, 4thがポリモーフィズムの真骨頂。
        // 処理を繰り返し再利用できる。by いちかわさん
        // 特に流れの処理を再利用しやすくなるのがポイント by jflute
        // (引数で解決する再利用だと、再利用範囲が狭まりがち)
    }

    // TODO jflute 次回1on1, interface (2026/04/08)
    // ===================================================================================
    //                                                              Polymorphism Interface
    //                                                              ======================
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_dispatch() {
        Loudable loudable = new Zombie();
        String sea = loudable.soundLoudly();
        log(sea); // your answer? => "uooo"
        String land = ((Zombie) loudable).bark().getBarkWord();
        log(land); // your answer? => "uooo"
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_hierarchy() {
        Loudable loudable = new AlarmClock();
//        Loudable loudable = new Dog();
        String sea = loudable.soundLoudly();
        log(sea); // your answer? => "jiri jiri jiri---"
        boolean land = loudable instanceof Animal;
        log(land); // your answer? => false
        // Dog()をインスタンス化した時にはちゃんとtrueになった
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_partImpl() {
        Animal seaAnimal = new Cat();
        Animal landAnimal = new Zombie();
        boolean sea = seaAnimal instanceof FastRunner;
        log(sea); // your answer? => true
        boolean land = landAnimal instanceof FastRunner;
        log(land); // your answer? => false
    }

    /**
     * Make Dog class implement FastRunner interface. (the method implementation is same as Cat class) <br>
     * (DogもFastRunnerインターフェースをimplementsしてみましょう (メソッドの実装はCatと同じで))
     */
    public void test_objectOriented_polymorphism_interface_runnerImpl() {
        // your confirmation code here
        Dog dog = new Dog();
        boolean isDogFastRunner = dog instanceof FastRunner;
        log(isDogFastRunner);
        dog.run();
    }

    /**
     * What is difference as concept between abstract class and interface? <br>
     * (抽象クラスとインターフェースの概念的な違いはなんでしょう？)
     */
    public void test_objectOriented_polymorphism_interface_whatisdifference() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // what is difference?
        // 処理が定義されていないメソッドだけで構成されているクラスかどうか？
        // でもそれは概念的な違いというより、表現の違いのような気がする、、、
        // すでに定義ずみの処理をもつメソッドもオーバーライドできちゃうし、、、
        // is-aの関係とhas-aの関係の違い？
        // _/_/_/_/_/_/_/_/_/_/
        // #1on1: まず、抽象クラスとインターフェース、似ている？ (2026/04/30)
        //
        // 抽象クラス      :: ポリモーフィズム、具象実装もできる (変数定義もできる)
        // インターフェース :: ポリモーフィズム
        //
        // じゃあ、具象実装のない抽象クラスとインターフェース、ほぼ同じ!?
        // なんで、こんな似通った機能がJavaの中に混在するのか？
        // というか、抽象クラスがあればインターフェース要らなくない？
        //
        // なんか継承が関わってそうな気がする by いちかわさん {
        //   extendsとimplementsの違い？
        //   implementsだったら継承関係なく機能を持たすことができる？
        //   いくつでもできるし...
        // }
        //
        // 継承は、いくつでもでき...ない → Javaは多重継承禁止
        // なぜ禁止？
        // 多重継承できちゃうと、変なオブジェクトができちゃいそう、キメラ？
        // ただ、正しくオブジェクトを分析すれば、多重継承は正当にしても悪くはない。
        // オブジェクト指向の思想としては多重継承は普通のこと。
        // でもそれを実現したJavaの思想としては禁止にしている。
        // つまり、実務の世界で不都合が発生するだろうということ。
        // 正しくオブジェクトを分析すればって言っても、正しくできますか？
        // 正しくできなかったらなんでもダメだけど...その被害が大きいかどうか？
        // カオスな多重継承の複雑度が半端ない。
        // 多重継承ができるC++という言語で、よく話題になっていた。
        // そしてJavaは多重継承は禁止した。
        // だから、interfaceで補完。
        //
        // オブジェクト指向とインターフェースはそもそも哲学が別。
        //
        // オブジェクト指向は、モノ中心に分析して整理整頓する考え方。(誰が呼ぶかは一旦置いておいて)
        // インターフェースは、呼ばれ方(機能)に焦点を当てている考え方。(誰がどう呼ぶかが中心)
        //
        // それがJavaに混在している。だから似る部分もあるよねと。
        // 
        // コンセプトは違うんだけど、Javaは機能的にinterfaceで補完するので、
        // 若干interfaceの本来のコンセプトとはちょっと違う使われ方もする。
        //  e.g. Petインターフェース
        // Petは概念的にインターフェースなのか？
        // そこは気にしないか？広くとらえてPetという接地面と解釈するか？
        // なんにせよ、オブジェクト指向の肩代わりとしてのinterfaceも時々ある。
        //
        // ただ、多重継承という多重implementsできるinterfaceを使ったら、
        // 結局カオスにならないのだろうか？ (多重継承がカオスになるから禁止したのに)
        // 多重継承じゃなく多重implementsなので、多重ポリモーフィズムしてるだけ。
        // 多重実装継承してるわけではない。
        // (カオスの原因は、多重ポリモーフィズムじゃなく多重実装継承だった)
        //
        // コードの再利用の手段は、継承だけじゃない。普通にnewして呼ぶだけとかでもOK。
        // 多重実装継承のコードの再利用の代わりは、実装の工夫でどうにかできる。

        // #1on1: 業務コードでのinterfaceの話 (2026/04/30)
        // レイヤー間のinterfaceはあるけど、局所的なinterfaceはなかなかない。
        // ライブラリとかフレームワークでよく使う。
        
        // TODO jflute 次回、もっと踏み込んだ肩代わりinterfaceの話 (2026/04/30)
    }
    
    // ===================================================================================
    //                                                                 Polymorphism Making
    //                                                                 ===================
    /**
     * Make concrete class of Animal, which is not FastRunner, in "objanimal" package. (implementation is as you like) <br>
     * (FastRunnerではないAnimalクラスのコンクリートクラスをobjanimalパッケージに作成しましょう (実装はお好きなように))
     */
    public void test_objectOriented_polymorphism_makeConcrete() {
        // your confirmation code here
        Seal seal = new Seal();
        BarkedSound sound = seal.bark();
        log(sound.getBarkWord());
    }

    /**
     * Make interface implemented by part of Animal concrete class in new package under "objanimal" package. (implementation is as you like) <br>
     * (Animalクラスの一部のコンクリートクラスだけがimplementsするインターフェースをobjanimal配下の新しいパッケージに作成しましょう (実装はお好きなように))
     */
    public void test_objectOriented_polymorphism_makeInterface() {
        // your confirmation code here
        Seal seal = new Seal();
        seal.swim();
    }

    // TODO jflute 次回1on1ここから (2026/04/30)
    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Extract St6MySql, St6PostgreSql (basic.st6.dbms)'s process to abstract class (as super class and sub-class) <br>
     * (St6MySql, St6PostgreSql (basic.st6.dbms) から抽象クラスを抽出してみましょう (スーパークラスとサブクラスの関係に))
     */
    public void test_objectOriented_writing_generalization_extractToAbstract() {
        // your confirmation code here
    }

    /**
     * Extract St6OperationSystem (basic.st6.os)'s process to concrete classes (as super class and sub-class) <br>
     * (St6OperationSystem (basic.st6.os) からコンクリートクラスを抽出してみましょう (スーパークラスとサブクラスの関係に))
     */
    public void test_objectOriented_writing_specialization_extractToConcrete() {
        // your confirmation code here
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Extract Animal's bark() process as BarkingProcess class to also avoid large abstract class. <br>
     * (抽象クラス肥大化を抑制するためにも、Animalのbark()のプロセス(処理)をBarkingProcessクラスとして切り出しましょう)
     */
    public void test_objectOriented_writing_withDelegation() {
        // your confirmation code here
    }

    /**
     * Put barking-related classes, such as BarkingProcess and BarkedSound, into sub-package. <br>
     * (BarkingProcessやBarkedSoundなど、barking関連のクラスをサブパッケージにまとめましょう)
     * <pre>
     * e.g.
     *  objanimal
     *   |-barking
     *   |  |-BarkedSound.java
     *   |  |-BarkingProcess.java
     *   |-loud
     *   |-runner
     *   |-Animal.java
     *   |-Cat.java
     *   |-Dog.java
     *   |-...
     * </pre>
     */
    public void test_objectOriented_writing_withPackageRefactoring() {
        // your confirmation code here
    }

    /**
     * Is Zombie correct as sub-class of Animal? Analyze it in thirty seconds. (thinking only) <br>
     * (ゾンビは動物クラスのサブクラスとして適切でしょうか？30秒だけ考えてみましょう (考えるだけでOK))
     */
    public void test_objectOriented_zoo() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // is it correct?
        //
        // _/_/_/_/_/_/_/_/_/_/
    }
}
