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
package org.docksidestage.bizfw.basic.objanimal;

import org.docksidestage.bizfw.basic.objanimal.barking.BarkedSound;
import org.docksidestage.bizfw.basic.objanimal.barking.BarkingProcess;
import org.docksidestage.bizfw.basic.objanimal.loud.Loudable;

/**
 * The object for animal(動物).
 * @author jflute
 */
public abstract class Animal implements Loudable {
    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // #1on1: 変数のfinalの話、immutable/mutableで定義位置調整の話 (2026/06/18)
    // -----------------------------------------------------
    //                                                 Basic
    //                                                 -----
    // done ichikawa もう固定的なインスタンスであれば、finalを付けてそれを示しておきたい by jflute (2026/06/03)
    protected final String barkWord;
    protected final BarkingProcess barkingProcess;
    
    // -----------------------------------------------------
    //                                                 State
    //                                                 -----
    protected int hitPoint; // is HP
    
    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Animal() {
        hitPoint = getInitialHitPoint();
        barkWord = getBarkWord();  // 思ったこと：成長段階によって鳴き声が変わることもしばしばある。それをどう実装するのか考えるのも面白そう。
        // #1on1: ↑BarkWordもオブジェクト化して、成長段階を階層で表現したり年齢的な時系列の要素を入れたり... (2026/06/03)

        // #1on1: これもいったん踊り場。このifをオーバーライドに変えるのは簡単 (2026/06/18)
        //if (this instanceof Zombie) {
        //    barkingProcess = new ZombieBarkingProcess(this);
        //} else {
        //    barkingProcess = new BarkingProcess(this);
        //}
        barkingProcess = createBarkingProcess();
    }

    // Zombieでnewするところだけオーバーライドできるように小分けメソッド
    // (Factoryメソッドパターン)
    protected BarkingProcess createBarkingProcess() {
        return new BarkingProcess(this);
    }

    protected int getInitialHitPoint() {
        return 10; // as default
    }

    protected abstract String getBarkWord();  //この意図ならgetよりsetBarkWordの方がいいのか？

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    public BarkedSound bark() {
        // やりたいこと：処理をBarkingProcessに委譲する
        BarkedSound barkedSound = barkingProcess.doBark(barkWord);
        return barkedSound;
    }

    // ===================================================================================
    //                                                                           Hit Point
    //                                                                           =========
    // TODO ichikawa 修行#: その通り、内部メソッドをpublicにしちゃうのはカプセル化を壊している by jflute (2026/06/03)
    // しかも、内部的なリファクタリング都合でpublicになっちゃった...
    // hint1: 相互参照を外すことが解決の糸口にもなるかも。(2026/06/03)
    // #1on1: 卒業しても残ってて気持ち悪いというのを使って頑張ろう (2026/06/18)
    public void downHitPoint() {  // このメソッドpublicにしたくないな、、、
        --hitPoint;
        if (hitPoint <= 0) {
            throw new IllegalStateException("I'm very tired, so I want to sleep" + getBarkWord());
        }
    }

    // ===================================================================================
    //                                                                               Loud
    //                                                                              ======
    @Override
    public String soundLoudly() {
        return bark().getBarkWord();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getHitPoint() {
        return hitPoint;
    }
}
