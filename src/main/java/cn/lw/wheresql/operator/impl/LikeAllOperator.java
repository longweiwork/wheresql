package cn.lw.wheresql.operator.impl;


import cn.hutool.core.util.StrUtil;
import cn.lw.wheresql.enumeration.OperatorEnum;
import cn.lw.wheresql.exception.ServiceException;
import cn.lw.wheresql.operator.Operator;
import cn.lw.wheresql.pojo.SearchColumn;
import org.springframework.util.CollectionUtils;

public class LikeAllOperator implements Operator {
    @Override
    public String getName() {
        return OperatorEnum.likeall.name();
    }

    @Override
    public void checkParam(SearchColumn searchColumn) {
        if (StrUtil.isBlank(searchColumn.getColumnKey()) || CollectionUtils.isEmpty(searchColumn.getRanges())) {
            throw new ServiceException(String.format("comparator param error, column[%s]", searchColumn.toString()));
        }
    }

    @Override
    public void doOperate(SearchColumn s, StringBuilder sb) {
        sb.append(" array_to_string(").append(s.getUnderlineColumnKey()).append(", '') like all (ARRAY[");
        for (int i = 0; i < s.getRanges().size(); i++) {
            if (i == 0)
                sb.append("'%").append(s.getRanges().get(i)).append("%' ::VARCHAR ");
            else
                sb.append(", ").append("'%").append(s.getRanges().get(i)).append("%' ::VARCHAR ");
        }
        sb.append("])");
    }
}
