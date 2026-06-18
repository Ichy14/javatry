package org.docksidestage.bizfw.basic.objanimal.barking;

import org.docksidestage.bizfw.basic.objanimal.Animal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ichikawa
 * @author jflute
 */
public class BarkingProcess {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger logger = LoggerFactory.getLogger(BarkingProcess.class);

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // #1on1: 相互参照はいちかわさんも微妙だと思っている (2026/06/03)
    // downHitPoint()のpublic問題の解決方法の一つとして相互参照の解消もあるかも。
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

        // #1on1: まずベタに書くとこう。挙動的にはいったんこれで辻褄が合う。 (2026/06/18)
        // これがダメな理由はなんだろう？
        // BarkingProcessがZombieを知ってるのが... by いちかわさん
        // 知っちゃうと何が悪いんだろう？
        // Zombieを迂闊にいじれなくなる by いちかわさん
        // BarkingProcessからAnimalに対するポリモーフィズムが崩れてる、と言える。
        // あとは、キリがない。ここにif文がずらーっと並ぶ可能性がある。
        // 具象処理がAnimalの具象クラス(Dog,Cat)の中で閉じない。
        //if (animal instanceof Zombie) {
        //    // これは本来、Zombieの具象処理。これがZombieワールドから外に出ちゃってる。
        //    // Zombie固有の具象処理は、Zombieワールドに配置したい。
        //    ((Zombie) animal).getZombieDiary().countBreatheIn();
        //}
        // OSクラスとかの例を参考にすると、こういう具象処理のifを、
        // サブクラスを作ってオーバーライド方式に変えてきた。
        //
        // +------------------+ <-------------------+
        // |      Animal      | ------+             |
        // +------------------+       |     +---------------------+
        //          ^                 +---> |   BarkingProcess    |
        //          |                       |                     |
        //          |               +------ |    breatheIn()      |
        //          |     +---------+       +---------------------+
        //          |     | (↑これどうにかしたい)      ^
        //  -------/|\---/|\-----------------------/|\----- ↓Zombieワールド -------
        //          |     v                         |      
        // +------------------+                     |
        // |      Zombie      |             +---------------------------+
        // +------------------+             |  ZombieBarkingProcess     |
        //               |                  | override breatheIn() {    |
        //               +----------------> |   super.breatheIn()       |
        //                      new         |   (ぞんびの日記カウント)     |
        //                     橋渡し        | }                         |
        //  (createメソッドをオーバーライド)    +---------------------------+
        //
    }

    // done ichikawa prepareAbdominalMuscle() は protected でも大丈夫かと by jflute (2026/06/03)
    protected void prepareAbdominalMuscle() { // also actually depends on barking
        logger.debug("...Using my abdominal muscle for barking"); // dummy implementation
        animal.downHitPoint();
    }
}
