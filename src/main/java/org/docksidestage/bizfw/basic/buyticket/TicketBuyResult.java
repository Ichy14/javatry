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
package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author n.ichikawa@bizreach.co.jp
 */
public class TicketBuyResult {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // done TODO ichikawa unused警告になっている (Constructorで消費しておしまい) by jflute (2025/10/31)
    // done TODO ichikawa 変数名がTwoDayPassportになっているけど、TwoDay限定？ by jflute (2025/10/31)
    // IntelliJのrenameを使ってrenameしましょう。
    private Ticket ticket;
    private Change change;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBuyResult(int handedMoney, int displayPrice) {
        // お釣りをreturnしようとした時にhandedMoneyがdisplayPrice未満になることを防ぐためにここで例外ハンドリングするべきのような気がするが、呼び出しもとのbuyTwoDayPassportでやってるから大丈夫？
        // #1on1: このResultクラスが、Domain Entity的なニュアンスで概念化されるのか？
        // それとも、青いトレーのように一時的に受け渡しで使うためだけの入れ物クラスというニュアンスなのか？
        // DTO？ by いちかわさん
        // yes, DTOの種類の一つ。DTOに具体的なニュアンス(色)を付けるかどうか？ってところ。
        // (ちょいDDDとの絡みの話)
        // done TODO ichikawa チケットの発行と、お釣りの計算をresultがやるかどうか？ by jflute (2025/10/31)
        this.ticket = new Ticket(displayPrice);
        this.change = new Change(handedMoney, displayPrice);
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public Ticket getTicket() {
        return ticket;
    }

    public Change getChange() {
        return change;
    }
}
