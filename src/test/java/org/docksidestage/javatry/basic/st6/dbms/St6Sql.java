package org.docksidestage.javatry.basic.st6.dbms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
