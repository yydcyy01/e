package com.yydcyy.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-09-19
 */
public class QueryVoIds implements Serializable {
    private List<Integer> ids;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
