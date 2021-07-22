package cn.lw.wheresql.operator;

import cn.lw.wheresql.pojo.SearchColumn;

import java.util.List;

/**
 * @desc:
 * @author: longwei
 * @date: 2021/7/22
 */
public class OperatorChain {

    private List<Operator> operators;

    private List<SearchColumn> searchColumns;

    public OperatorChain(List<Operator> operators, List<SearchColumn> searchColumns) {
        // 参数校验
        if (operators.size() != searchColumns.size()) {
            throw new RuntimeException("operator chain 构造参数数量不正确");
        }
        for (int i = 0; i < operators.size(); i++) {
            operators.get(i).checkParam(searchColumns.get(i));
        }

        this.operators = operators;
        this.searchColumns = searchColumns;
    }

    public String jointAndSearchParam() {
        return jointSearchParam("and");
    }

    public String jointOrSearchParam() {
        return jointSearchParam("or");
    }

    private String jointSearchParam(String joiner) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < operators.size(); i++) {
            sb.append(joiner).append(" ");
            operators.get(i).doOperate(searchColumns.get(i), sb);
            sb.append(" ");
        }

        // for gc
        operators = null;
        searchColumns = null;

        return sb.substring(3);
    }
}
