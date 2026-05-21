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
package org.docksidestage.javatry.basic.st6.dbms;

/**
 * @author jflute, n.ichikawa
 */
public class St6MySql extends St6DBMS {

    // 一応抽象クラスは作ったけれど、何かしっくりこない
    // #1on1: 何がしっくりこない？もっと綺麗にできるかな？ by いちかわさん
    // #1on1: 綺麗の内訳は？ (2026/05/08)
    // コードの重複がない by いちかわさん
    // 綺麗の要素の一つとして重複の話は大きい。
    // 親子の役割分担がうまく切り分けられてない？ by いちかわさん
    // まずは、コードの重複にフォーカス当ててみよう。
    //
    // calcOffset()の再利用はできているのはGood。
    // もう重複はないか？
    // 構造が重複しているような気がする by いちかわさん
    // limit/offsetの文字列の生成方法はDMBSごとに全然違うのでそこは再利用できない。
    //
    // 重複がないコードは何が嬉しい？
    // ロジックの書いてる場所が一つ、変更したときに一箇所で済む。
    // o Offsetの計算の仕方が変わったら、calcOffset()を直せば良い
    // o (limitの文字列生成のところが変わったら、個別のDBMSのコードを直せば良い)
    // o offset計算後、文字列を作る前の、記録を残すためにログ出力する
    //    → これ二箇所修正しないといけない
    // 2stepの流れが重複している。
    //  1. offset計算して結果を保持
    //  2. offset結果を次の行に渡して文字列を生成
    //
    // その流れに変更があったときに、二箇所修正が必要になった。
    // 流れを再利用できるか？
    // step5で流れを再利用していた。ただあっちは引数で抽象化をして再利用していた。
    // こっちは、もうすでに抽象化された概念がある。それをうまく使えないか？
    // done TODO ichikawa 流れを再利用できる形にしてみましょう by jflute (2026/05/08)
    // #1on1: hint1 既存の抽象/具象のコードを「流れの再利用」という視点で見てみたら？ (2026/05/20)
    // hint2: Animalのbark()と1行1行比較してみたら、いちかわさんは悔しがった (2026/05/20)
    // #1on1: ようやく抽象と具象の処理の移り変わりが感覚的に沁み込んだ by いちかわさん (2026/05/20)
    // まさしく、こういうデザイン的な部分は一度じっくり悩んで感覚で捉えないと使える知識にならない。
    @Override
    public String doBuildPagingQuery(int offset, int pageSize) {
        return "limit " + offset + ", " + pageSize;
    }
    // TODO ichikawa [宿題] テンプレートメソッドパターンを調べてみてください by jflute (2026/05/20)
    // done ichikawa [読み物課題]デバッグパターン: うごかない、ほかうごくなら、ただひかく by jflute (2026/05/20)
    // https://jflute.hatenadiary.jp/entry/20180811/comparingdebug

    // PostgreSQLの方:
    //@Override
    //public String buildPagingQuery(int pageSize, int pageNumber) {
    //    int offset = calcOffset(pageSize, pageNumber);
    //    return "offset " + offset + " limit " + pageSize;
    //}
}
