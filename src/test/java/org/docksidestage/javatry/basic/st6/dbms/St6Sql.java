package org.docksidestage.javatry.basic.st6.dbms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// #1on1 MySQL, PostgreSQL の抽象概念がSQLになっている (2026/05/08)
// 他の具象クラスを思いつくか？ → e.g. Oracle
// SQLの機能を備えた何かだけど、SQLそのものかどうか？
// リレーショナルデータベース製品、RDBMS (Rを省略してDBMS)
// TODO ichikawa 抽象クラス名をrenameしてみましょう by jflute (2026/05/08)
/**
 * @author n.ichikawa
 */
public abstract class St6Sql {
    private static final Logger log = LoggerFactory.getLogger(St6Sql.class);

    abstract public String buildPagingQuery(int pageSize, int pageNumber);

    int calcOffset(int pageSize, int pageNumber) {
        return pageSize * (pageNumber - 1);
    }
}
