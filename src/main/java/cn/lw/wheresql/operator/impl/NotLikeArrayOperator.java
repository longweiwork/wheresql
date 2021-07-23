package cn.lw.wheresql.operator.impl;


import cn.hutool.core.util.StrUtil;
import cn.lw.wheresql.enumeration.OperatorEnum;
import cn.lw.wheresql.exception.ServiceException;
import cn.lw.wheresql.operator.Operator;
import cn.lw.wheresql.pojo.SearchColumn;
import org.springframework.util.CollectionUtils;

public class NotLikeArrayOperator implements Operator {
    @Override
    public String getName() {
        return OperatorEnum.notlikearray.name();
    }

    @Override
    public void checkParam(SearchColumn searchColumn) {
        if (StrUtil.isBlank(searchColumn.getColumnKey()) || CollectionUtils.isEmpty(searchColumn.getRanges())) {
            throw new ServiceException(String.format("comparator param error, column[%s]", searchColumn.toString()));
        }
    }

    @Override
    public void doOperate(SearchColumn s, StringBuilder sb) {
        sb.append(" (");
        for (int i = 0; i < s.getRanges().size(); i++) {
            if (i != 0)
                sb.append(" and ");
            sb.append(s.getUnderlineColumnKey()).append(" not like '%").append(s.getRanges().get(i)).append("%'");
        }
        sb.append(" )");
    }
}
