package cn.lw.wheresql.operator.impl;


import cn.hutool.core.util.StrUtil;
import cn.lw.wheresql.enumeration.OperatorEnum;
import cn.lw.wheresql.exception.ServiceException;
import cn.lw.wheresql.operator.Operator;
import cn.lw.wheresql.pojo.SearchColumn;
import org.springframework.util.CollectionUtils;

public class NotEqualsAnyJoinOperator implements Operator {
    @Override
    public String getName() {
        return OperatorEnum.noteqanyJoin.name();
    }

    @Override
    public void checkParam(SearchColumn searchColumn) {
        if (StrUtil.isBlank(searchColumn.getColumnKey())
                || CollectionUtils.isEmpty(searchColumn.getRanges())) {
            throw new ServiceException(
                    String.format("comparator param error, column[%s]", searchColumn.toString()));
        }
    }

    @Override
    public void doOperate(SearchColumn s, StringBuilder sb) {
        // 两个数组不存在交集，也就是第二个数组中的任意一个element在第一个数组中都不存在
        sb.append("(");
        sb.append(s.getUnderlineColumnKey()).append(" && ARRAY[");
        for (int i = 0; i < s.getRanges().size(); i++) {
            if (i == 0) {
                sb.append("'").append(s.getRanges().get(i)).append("' ::VARCHAR ");
            } else {
                sb.append(", '").append(s.getRanges().get(i)).append("' ::VARCHAR ");
            }
        }
        sb.append("] = 'f'");
        sb.append(" or ").append(s.getUnderlineColumnKey()).append(" is null )");
    }
}
