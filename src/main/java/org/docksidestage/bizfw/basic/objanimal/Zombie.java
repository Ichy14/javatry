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

/**
 * The object for zombie(ゾンビ).
 * @author jflute
 */
public class Zombie extends Animal {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final ZombieDiary zombieDiary = new ZombieDiary();

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Zombie() {
    }

    @Override
    protected int getInitialHitPoint() {
        return -1; // magic number for infinity hit point
    }

    public static class ZombieDiary {

        private int breatheInCount;

        public void countBreatheIn() {
            ++breatheInCount;
        }

        public int getBreatheInCount() {
            return breatheInCount;
        }
    }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    // TODO ichikawa 修行++: Zombieって、bark()したら、bark()の中のbreatheInで、diaryにcountするという挙動 by jflute (2026/06/03)
    // 元々、AnimalのbreatheIn()をオーバーライドして、countBreatheIn()を付け足していた。
    // いま、↓このbreatheIn()は、barkingProcessのbreatheInと連動していない。
    // ZombieのbreatheIn()が呼ばれたら、barkingProcessのbreatheIn()が呼ばれるけど...
    // barkingProcessのbreatheIn()が呼ばれても、ZombieのbreatheIn()は呼ばれない。
    // 元々の実装は、後者で連動する挙動を期待していた。今逆になってる。
    // (オーバーライドで処理の差し替えが発生していたが、いま素のメソッドなので誰からも呼ばれてない)
    //
    // barkingProcessのbreatheIn()が呼ばれたら、ZombieのbreatheIn()も呼ばれるようにしたい。
    // (ZombieのbreatheIn()というか、とにかくbarkingProcessのbreatheIn()が呼ばれた時、
    // countBreatheIn()の処理を追加されるようにしたい: 具体的な実装方法はいくつか選択肢あり)
    protected void breatheIn() {
        // TODO ichikawa super.は不要です。superの変数も継承して来ているのでthis.扱い by jflute (2026/06/03)
        super.barkingProcess.breatheIn();
        zombieDiary.countBreatheIn();
    }

    @Override
    protected String getBarkWord() {
        return "uooo"; // what in English?
    }

    // ===================================================================================
    //                                                                           Hit Point
    //                                                                           =========
    @Override
    public void downHitPoint() {
        // do nothing, infinity hit point
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public ZombieDiary getZombieDiary() {
        return zombieDiary;
    }
}
