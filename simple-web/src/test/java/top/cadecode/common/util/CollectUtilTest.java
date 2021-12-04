package top.cadecode.common.util;

import org.junit.jupiter.api.Test;

/**
 * @author Cade Li
 * @date 2021/12/4
 * @description ToDo
 */
public class CollectUtilTest {

    @Test
    public void arrayContains() {
        Integer[] arr = {1, 2, 3};
        boolean contains = CollectUtil.contains(arr, 3);
        System.out.println(contains);
    }
}
