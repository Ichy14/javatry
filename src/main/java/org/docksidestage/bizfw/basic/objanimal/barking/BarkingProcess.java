package org.docksidestage.bizfw.basic.objanimal.barking;

import org.docksidestage.bizfw.basic.objanimal.Animal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BarkingProcess {
    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger logger = LoggerFactory.getLogger(BarkingProcess.class);

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // #1on1: 相互参照はいちかわさんも微妙だと思っている (2026/06/03)
    private Animal animal;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BarkingProcess(Animal animal) {
        this.animal = animal;
    }

    // #1on1: protectedのパッケージスコープ参照 (2026/06/03)
    // public :: みんな呼べる
    // protected :: 自分とサブクラスなら呼べる or 同じパッケージなら呼べる
    // (パッケージ) :: 同じパッケージなら呼べる
    // private :: 自分しか呼べない
    //
    // パッケージスコープに依存をしていると、物理的なファイル移動(パッケージ分け)のときに影響でたりする。
    // パッケージスコープだけ、物理世界に依存したスコープになる。そこ注意。
    //
    // リファクタリングで簡単にコンパイルエラーになってつまづくので個人的にはほぼ使わない。
    // (UnitTestとかだけは使う)
    //
    // 一方で、Javaのパッケージが物理依存している話もあるけど...
    // 個人的には物理依存で逆にファイルツリーがわかりやすくなるのでわりと好き。
    // (例えば、C#とかだと、namespaceとファイル構造は自由で切り離されていたりする)
    // 自由と規約のトレードオフ。
    // ===================================================================================
    //                                                                              Method
    //                                                                              ======
    public BarkedSound doBark(String barkWord) {
        breatheIn();
        prepareAbdominalMuscle();
        animal.downHitPoint();
        return new BarkedSound(barkWord);
    }

    public void breatheIn() { // actually depends on barking
        logger.debug("...Breathing in for barking"); // dummy implementation
        animal.downHitPoint();
    }

    // TODO ichikawa prepareAbdominalMuscle() は protected でも大丈夫かと by jflute (2026/06/03)
    public void prepareAbdominalMuscle() { // also actually depends on barking
        logger.debug("...Using my abdominal muscle for barking"); // dummy implementation
        animal.downHitPoint();
    }
}
