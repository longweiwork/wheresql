package cn.lw.wheresql.operator.impl;

import cn.hutool.core.util.StrUtil;
import cn.lw.wheresql.enumeration.OperatorEnum;
import cn.lw.wheresql.exception.ServiceException;
import cn.lw.wheresql.operator.Operator;
import cn.lw.wheresql.pojo.SearchColumn;

/**
 * @desc:
 * @author: longwei
 * @date: 2021/7/23
 */
public class EqualsOperator implements Operator {
    @Override
    public String getName() {
        return OperatorEnum.eq.name();
    }

    @Override
    public void checkParam(SearchColumn searchColumn) {
        if (StrUtil.isBlank(searchColumn.getColumnKey()) || StrUtil.isBlank(searchColumn.getSearchValue())) {
            throw new ServiceException(String.format("comparator param error, column[%s]", searchColumn.toString()));
        }
    }

    @Override
    public void doOperate(SearchColumn s, StringBuilder sb) {
        sb.append(s.getUnderlineColumnKey()).append(" = ").append("'").append(s.getSearchValue()).append("'");
    }
}
