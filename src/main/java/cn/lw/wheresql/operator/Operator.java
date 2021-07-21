package cn.lw.wheresql.operator;

import cn.lw.wheresql.pojo.SearchColumn;

/**
 * @desc:
 * @author: longwei
 * @date: 2021/7/20
 */
public interface Operator {

    String getName();

    void checkParam(SearchColumn searchColumn);

    void doOperate(SearchColumn s, StringBuilder sb);
}
