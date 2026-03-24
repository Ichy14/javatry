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

// TODO ichikawa ↑ZoneIdのunused by jflute (2026/03/24)
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
    // done ichikawa すでに TicketType は days だけじゃない概念なので、ticketType でいいかなと by jflute (2026/03/03)
    // done ichikawa availableTimeは TicketType から取れて、immutable なので、事前に確保してなくてもいいかも!? by jflute (2026/03/03)
    // (でもこれは若干ケースバイケースで、availableTime が重要人物で、何度も頻繁に利用するとかだったら話は別)
    // (ichikawa) availableTimeは、TicketTypeから取れる & 頻繁には利用しないので、除外。今後使うことになったとしても、リファクタは難しくはないはず。
    // done ichikawa インスタンス変数の定義順序、何かしらの指針でぱっと見で理解できるように工夫したいところ by jflute (2026/03/03)
    // e.g. immutable, mutableで分ける、業務的なカテゴリで分ける
    // DBFlute の LikeSearchOption の例で説明。
    private final TicketType ticketType;
    private final int displayPrice; // written on ticket, park guest can watch this

    private boolean alreadyIn; // true means this ticket is unavailable
    private int remainingUsage;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Ticket(int displayPrice, TicketType ticketType) {
        this.displayPrice = displayPrice;
        this.alreadyIn = false;
        this.remainingUsage = ticketType.getAvailableDays();
        this.ticketType = ticketType;
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
    // #1on1: JavaDoc, Good. 一方で、@throws とかあるよ話 (2026/03/03)
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
//        // night_onlyパスの時、時刻が夕方じゃなければremainingusageを減らさない＋alreadyInもtrueにしない、みたいなロジックが必要
//        // DateTimeを使うか？
//        if (ticketType.getAvailableTime() == AvailableTimeType.NIGHT_ONLY) {
//            ZonedDateTime jstNow = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
//            // done ichikawa 修行++: アフター6, スターライトパスポートとかを想像。夜の開始ニュアンスが若干違うケースがあるかも。 by jflute (2026/03/03)
//            // そういったNIGHT_ONLYのチケット種別が新しく追加されても、enumの修正だけで済むようにしたい。
//            // (NIGHT_ONLYというニュアンスに統一しても、違うものを導入しても、どちらでも。何にせよここから17を消したい)
//            if (jstNow.getHour() < 17) { // 夕方5時から夜パスが使えるとする
//                // done ichikawa 業務例外だとしても、バグきっかけで発生することもあるので、ticketTypeくらいは入れておきたい by jflute (2026/03/03)
//                // (業務例外でもデバッグ情報はある程度入れておいた方が良い)
//                throw new IllegalStateException("This ticket is not available until evening. TicketType=" + ticketType);
//            }
//        }

        // そもそもチケットがNIGHT_ONLYかどうかに関係なく、入園時間より前には入れないようにした
        ZonedDateTime jstNow = ZonedDateTime.now(TimeController.clock());
        // #1on1: 1文字の変数名のお話。文化にもよるがJavaの世界の傾向のお話も。 (2026/03/24)
        // 昔の時代の変数名の制約のお話、DBのテーブル名やカラム名のお話。
        // "単語の省略: entryTime → time" と、"略語単語: time → tm" の違い。
        // スコープが短い場合は、短めの名前を付けるのでは一般的なのでOK。
        // ただ、どっちで短くするか？
        // 略語、人によって変わってしまう問題: member → mb? mem? mbr?
        // (昔は、略語辞書を作ってる現場あった。今はそれを作るって現場はほぼない)
        // (一方で、CODE → CD とか FLAG → FLG とか浸透しているものはOK)
        // 
        // TODO ichikawa [読み物課題] SQLのエリアス名、頭文字省略は...うーん by jflute (2026/03/24)
        // https://jflute.hatenadiary.jp/entry/20140908/sqlalias
        int t = ticketType.getAvailableTime().getEntryTime();
        if (jstNow.getHour() < t) {
            // #1on1: t と ticketType を入れてるの素晴らしい (2026/03/24)
            // 一方で、jstNow も優先度は低いけど、デバッグ情報としては入れておきたい、差し替えが発生してるかもしれないし。
            throw new IllegalStateException("This ticket is not available until "+ t +":00. TicketType=" + ticketType);
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
    public int getDisplayPrice() {
        return displayPrice;
    }

    public boolean isAlreadyIn() {
        return alreadyIn;
    }

    public int getRemainingUsage() {
        return remainingUsage;
    }

    public TicketType getTicketType() {
        return ticketType;
    }
}
