package cn.lw.wheresql.operator.impl;


import cn.hutool.core.util.StrUtil;
import cn.lw.wheresql.enumeration.OperatorEnum;
import cn.lw.wheresql.exception.ServiceException;
import cn.lw.wheresql.operator.Operator;
import cn.lw.wheresql.pojo.SearchColumn;

public class BetweenOperator implements Operator {
    @Override
    public String getName() {
        return OperatorEnum.between.name();
    }

    @Override
    public void checkParam(SearchColumn searchColumn) {
        if (StrUtil.isBlank(searchColumn.getColumnKey()) || StrUtil.isBlank(searchColumn.getSearchValue()) || StrUtil.isBlank(searchColumn.getSearchEndValue())) {
            throw new ServiceException(String.format("comparator param error, column[%s]", searchColumn.toString()));
        }
    }

    @Override
    public void doOperate(SearchColumn s, StringBuilder sb) {
        sb.append("(").append(s.getUnderlineColumnKey());
        sb.append(" between ").append("'").append(s.getSearchValue()).append("'").append(" and ").append("'").append(s.getSearchEndValue()).append("'");
        sb.append(")");
    }
}
