package org.docksidestage.javatry.basic.st6.dbms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// #1on1 MySQL, PostgreSQL の抽象概念がSQLになっている (2026/05/08)
// 他の具象クラスを思いつくか？ → e.g. Oracle
// SQLの機能を備えた何かだけど、SQLそのものかどうか？
// リレーショナルデータベース製品、RDBMS (Rを省略してDBMS)
// done ichikawa 抽象クラス名をrenameしてみましょう by jflute (2026/05/08)
/**
 * @author n.ichikawa
 */
public abstract class St6DBMS {
    // done TODO ichikawa こちらunusedなので削除を by jflute (2026/06/03)

    public String buildPagingQuery(int pageSize, int pageNumber) {
        int offset = calcOffset(pageSize, pageNumber);
        String pagingQuery = doBuildPagingQuery(offset, pageSize);
        return pagingQuery;
    }
    
    int calcOffset(int pageSize, int pageNumber) {
        return pageSize * (pageNumber - 1);
    }

    protected abstract String doBuildPagingQuery(int offset, int pageSize);
}
