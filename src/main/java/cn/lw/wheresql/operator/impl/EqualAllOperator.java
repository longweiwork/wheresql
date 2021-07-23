package cn.lw.wheresql.operator.impl;

import cn.hutool.core.collection.CollectionUtil;
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
public class EqualAllOperator implements Operator {
    @Override
    public String getName() {
        return OperatorEnum.eqall.name();
    }

    @Override
    public void checkParam(SearchColumn searchColumn) {
        if (StrUtil.isBlank(searchColumn.getColumnKey()) || CollectionUtil.isEmpty(searchColumn.getRanges())) {
            throw new ServiceException(String.format("comparator param error, column[%s]", searchColumn.toString()));
        }
    }

    @Override
    public void doOperate(SearchColumn s, StringBuilder sb) {
        // 第一个数组包含第二个数组，即第二个数组中的所有element都能在第一个数组中找到
        sb.append(s.getUnderlineColumnKey()).append(" @> ARRAY[");
        for (int i = 0; i < s.getRanges().size(); i++) {
            if (i == 0)
                sb.append("'").append(s.getRanges().get(i)).append("' ::VARCHAR ");
            else
                sb.append(", '").append(s.getRanges().get(i)).append("' ::VARCHAR ");
        }
        sb.append("]");
    }
}
