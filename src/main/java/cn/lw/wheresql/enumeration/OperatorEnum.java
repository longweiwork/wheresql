package cn.lw.wheresql.enumeration;

/**
 * @desc:
 * @author: longwei
 * @date: 2021/7/23
 */
public enum OperatorEnum {

    eq("eq", "等于"),
    multiple("multiple", ""),
    ne("ne", "不等于"),
    ge("ge", "大于等于"),
    gt("gt", "大于"),
    le("le", "小于等于"),
    lt("lt", "小于"),
    in("in", "在列表值中"),

    like("like", "类似"),
    notlike("notlike", "不类似"),

    contains("contains", "包含"),
    notContains("notContains", "不包含"),

    isnull("isnull", ""),
    isnotnull("isnotnull", ""),

    and("and", "并集"),
    or("or", "交集"),

    eqall("eqall", "数组全部包含"),
    eqany("eqany", "数组交集"),
    likeall("likeall", "数组模糊匹配全部"),
    likeany("likeany", "数组模糊匹配任一"),
    noteqall("noteqall", "数组至少有一个不包含"),
    noteqany("noteqany", "数组不包含任意一个"),
    noteqanyJoin("noteqanyJoin", "数组不包含任意一个,适用于左右连接操作查询为空的情况"),

    likearray("likearray", "字段和数组内任一元素相似"),
    notlikearray("notlikearray", "字段和数组内任一元素不相似"),

    between("between", "在...和...之间"),
    before("before", "在...之前"),
    after("after", "在...之后"),
    ;

    private String code;

    private String label;

    OperatorEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}
