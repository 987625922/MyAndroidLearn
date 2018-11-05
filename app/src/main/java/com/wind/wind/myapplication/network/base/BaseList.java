package com.wyt.hs.zxxtb.network.base;

import java.util.List;

/**
 * 外语通接口返回的数据经常有如下模式，此类用于替代每个类中list，T这样的数据
 * {
 *  msg:"",
 *  code:"200",
 *  data:"
 *      {
 *          total:20,
 *          list:
 *              { ... }
 *      }"
 * }
 * @param <E>
 */
public class BaseList<E> {
    /**
     * 总的资源数量
     */
    public int total;
    public List<E> list;

    //评论列表
    public float my_score;

    public boolean isEmptyList(){
        return list == null || list.size() == 0;
    }
}
