package cn.lw.wheresql;

import cn.lw.wheresql.operator.OperatorFactory;
import cn.lw.wheresql.pojo.SearchColumn;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc:
 * @author: longwei
 * @date: 2021/7/23
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class OperatorTest {

    @Test
    public void testOperator() {
        List<SearchColumn> searchColumns = new ArrayList<>();
        searchColumns.add(SearchColumn.builder().columnKey("name").searchValue("longwei").compareOperation("eq").build());
        String whereSql = OperatorFactory.buildOperatorChain(searchColumns).jointAndSearchParam();
        System.out.println(whereSql);
    }
}
