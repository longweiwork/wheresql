package cn.lw.wheresql.pojo;

import cn.lw.wheresql.util.StringUtils;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @desc:
 * @author: longwei
 * @date: 2021/7/20
 */

@Data
@Builder
public class SearchColumn {

    @ApiParam("检索字段key")
    private String columnKey;

    @ApiParam("检索值")
    private String searchValue;

    @ApiParam("检索结束值,用户范围查找")
    private String searchEndValue;

    @ApiParam("检索集合值")
    private List<String> ranges;

    @ApiParam("比较操作符")
    private String compareOperation;


    public String getUnderlineColumnKey() {
        return StringUtils.humpToUnderline(columnKey);
    }
}
