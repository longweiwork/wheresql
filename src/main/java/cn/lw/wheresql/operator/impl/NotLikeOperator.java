package cn.lw.wheresql.operator.impl;


import cn.hutool.core.util.StrUtil;
import cn.lw.wheresql.enumeration.OperatorEnum;
import cn.lw.wheresql.exception.ServiceException;
import cn.lw.wheresql.operator.Operator;
import cn.lw.wheresql.pojo.SearchColumn;

public class NotLikeOperator implements Operator {
    @Override
    public String getName() {
        return OperatorEnum.notlike.name();
    }

    @Override
    public void checkParam(SearchColumn searchColumn) {
        if (StrUtil.isBlank(searchColumn.getColumnKey()) || StrUtil.isBlank(searchColumn.getSearchValue())){
            throw new ServiceException(String.format("comparator param error, column[%s]", searchColumn.toString()));
        }
    }

    @Override
    public void doOperate(SearchColumn s, StringBuilder sb) {
        sb.append(s.getUnderlineColumnKey()).append(" not like '%").append(s.getSearchValue()).append("%'");
    }
}
