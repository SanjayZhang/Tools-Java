package com.sanjay.util;

/**
 * Created by zsj_09@hotmail.com on 2015/2/16.
 */
public class Array {

    /**
     * 删除 数组中的一项。数组不能改变大小，这边实质是新建了数组
     *
     * @param type  数组的类型
     * @param t     原始数组
     * @param index 删除的位置
     * @param <T>
     * @return 删除一项后的数组
     */
    public static <T> T[] delete(Class<T> type, T[] t, int index) {
        if (t == null || index >= t.length || index <= 0) {
            return t;
        }

        //泛型不能直接通过 new T[]建立，需要通过以下方式新建
        T[] tmp = (T[]) java.lang.reflect.Array.newInstance(type, t.length - 1);

        if (0 < index - 1)
            System.arraycopy(t, 0, tmp, 0, index - 1);

        if (index - 1 < tmp.length)
            System.arraycopy(t, index, tmp, index - 1, tmp.length - (index - 1));
        return tmp;
    }
}
