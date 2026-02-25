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

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * {@code Ticket}は「チケット」オブジェクトを定義するクラスです。<br>
 * チケットの状態とチケット自体の情報を持ちます。<br>
 * - チケットの状態：チケットがすでに入園されているかどうか、残りの使用回数を表します。<br>
 * - チケットの情報：表示価格、利用可能な日数、利用可能な時間帯を表します。<br>
 *
 * @author jflute
 * @author n.ichikawa
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final int displayPrice; // written on ticket, park guest can watch this
    private boolean alreadyIn; // true means this ticket is unavailable
    private int remainingUsage;
    private final TicketBooth.TicketType availableDays;
    private final TicketBooth.AvailableTimeType availableTime;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Ticket(int displayPrice, TicketBooth.TicketType ticketType) {
        this.displayPrice = displayPrice;
        this.alreadyIn = false;
        this.remainingUsage = ticketType.getAvailableDays();
        this.availableDays = ticketType;
        this.availableTime = ticketType.getAvailableTime();
    }

    // リファクタ前（2026/02/25）
//    public Ticket(int displayPrice, TicketBooth.TicketType availableDays, boolean isAvailableAllDay) {
//        this.displayPrice = displayPrice;
//        this.remainingUsage = availableDays.getAvailableDays();
//        this.availableDays = availableDays;
//        this.isAvailableAllDay = isAvailableAllDay;
//    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    /**
     * チケットを入園済み状態に更新するメソッドです。<br>
     * 以下の場合に例外をスローします。<br>
     * - チケットがすでに入園済み状態の時に呼び出された場合
     * - 夜間専用チケットが夕方以降でない時に呼び出された場合
     */
    public void doInPark() {
        if (alreadyIn) {
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + displayPrice);
        }
        // night_onlyパスの時、時刻が夕方じゃなければremainingusageを減らさない＋alreadyInもtrueにしない、みたいなロジックが必要
        // DateTimeを使うか？
        if (availableTime == TicketBooth.AvailableTimeType.NIGHT_ONLY) {
            ZonedDateTime jstNow = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
            if (jstNow.getHour() < 17) { // 夕方5時から夜パスが使えるとする
                throw new IllegalStateException("This ticket is not available until evening.");
            }
        }

        // done ichikawa (よほどバグってなければ)マイナスにはならないし、0のときもalreadyInのifでここには来ない by jflute (2025/12/16)
        remainingUsage--;
        if (remainingUsage == 0) {
            alreadyIn = true;
        }
    }

//    public void doOutPark() {
//        if (!alreadyIn) {
//            throw new IllegalStateException("Not yet in park by this ticket: displayedPrice=" + displayPrice);
//        }
//        alreadyIn = false;
//    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public TicketBooth.AvailableTimeType getAvailableTime() {
        return availableTime;
    }

    public int getDisplayPrice() {
        return displayPrice;
    }

    public boolean isAlreadyIn() {
        return alreadyIn;
    }

    public int getRemainingUsage() {
        return remainingUsage;
    }

    public TicketBooth.TicketType getAvailableDays() {
        return availableDays;
    }
}
