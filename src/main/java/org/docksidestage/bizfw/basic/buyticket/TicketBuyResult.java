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
    private final int handedMoney;
    private final int displayPrice; // written on ticket, park guest can watch this
    private Ticket twoDayPassport;
    private int change;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBuyResult(int handedMoney, int displayPrice) {
        // お釣りをreturnしようとした時にhandedMoneyがdisplayPrice未満になることを防ぐためにここで例外ハンドリングするべきのような気がするが、呼び出しもとのbuyTwoDayPassportでやってるから大丈夫？
        this.displayPrice = displayPrice;
        this.handedMoney = handedMoney;
        this.twoDayPassport = new Ticket(displayPrice);
        this.change = handedMoney - displayPrice;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public Ticket getTicket() {
        return twoDayPassport;
    }

    public int getChange() {
        return change;
    }
}
