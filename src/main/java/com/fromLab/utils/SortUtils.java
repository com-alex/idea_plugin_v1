package com.fromLab.utils;

import com.fromLab.entity.Task;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * @Auther: JIN KE
 * @Date: 2019/12/11 21:24
 */
public class SortUtils {


        /**
         * 对list的元素按照多个属性名称排序,
         * list元素的属性可以是数字(byte?short?int?long?float?double等,支持正数?负数?0)?char?String?java.util.Date
         *
         * @param list
         * @param sortnameArr list元素的属性名称
         * @param isAsc    true升序,false降序
         */
        public static <E> void sort(List<E> list, final boolean isAsc, final String... sortnameArr) {
            Collections.sort(list, new Comparator<E>() {

                @Override
                public int compare(E a, E b) {
                    int ret = 0;
                    try {
                        for (int i = 0; i < sortnameArr.length; i++) {
                            ret = SortUtils.compareObject(sortnameArr[i], isAsc, a, b);
                            if (0 != ret) {
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return ret;
                }
            });
        }

        /**
         * 给list的每个属性都指定是升序还是降序
         *
         * @param list
         * @param sortnameArr 参数数组
         * @param typeArr     每个属性对应的升降序数组, true升序,false降序
         */

        public static <E> void sort(List<E> list, final String[] sortnameArr, final boolean[] typeArr) {
            if (sortnameArr.length != typeArr.length) {
                throw new RuntimeException("属性数组元素个数和升降序数组元素个数不相等");
            }
            Collections.sort(list, new Comparator<E>() {
                @Override
                public int compare(E a, E b) {
                    int ret = 0;
                    try {
                        for (int i = 0; i < sortnameArr.length; i++) {
                            ret = SortUtils.compareObject(sortnameArr[i], typeArr[i], a, b);
                            if (0 != ret) {
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return ret;
                }
            });
        }

        /**
         * 对2个对象按照指定属性名称进行排序
         *
         * @param sortname 属性名称
         * @param isAsc    true升序,false降序
         * @param a
         * @param b
         * @return
         * @throws Exception
         */
        private static <E> int compareObject(final String sortname, final boolean isAsc, E a, E b) throws Exception {
            int ret;
            Object value1 = SortUtils.forceGetFieldValue(a, sortname);
            Object value2 = SortUtils.forceGetFieldValue(b, sortname);
            //有空值防止报错
            if (value1!=null&&value2!=null){
                String str1 = value1.toString();
                String str2 = value2.toString();
                if (value1 instanceof Number && value2 instanceof Number) {
                    int maxlen = Math.max(str1.length(), str2.length());
                    str1 = SortUtils.addZero2Str((Number) value1, maxlen);
                    str2 = SortUtils.addZero2Str((Number) value2, maxlen);
                } else if (value1 instanceof Timestamp && value2 instanceof Timestamp) {
                    long time1 = ((Timestamp) value1).getTime();
                    long time2 = ((Timestamp) value2).getTime();
                    int maxlen = Long.toString(Math.max(time1, time2)).length();
                    str1 = SortUtils.addZero2Str(time1, maxlen);
                    str2 = SortUtils.addZero2Str(time2, maxlen);
                }
                if (isAsc) {
                    ret = str1.compareTo(str2);
                } else {
                    ret = str2.compareTo(str1);
                }
                return ret;
            }
            return 0;
        }

    /**
     * 给数字对象按照指定长度在左侧补0.
     * <p>
     * 使用案例: addZero2Str(11,4) 返回 "0011", addZero2Str(-18,6)返回 "-000018"
     *
     * @param numObj 数字对象
     * @param length 指定的长度
     * @return
     */
    public static String addZero2Str(Number numObj, int length) {
        NumberFormat nf = NumberFormat.getInstance();
        // 设置是否使用分组
        nf.setGroupingUsed(false);
        // 设置最大整数位数
        nf.setMaximumIntegerDigits(length);
        // 设置最小整数位数
        nf.setMinimumIntegerDigits(length);
        return nf.format(numObj);
    }

    /**
     * 获取指定对象的指定属性值(去除private,protected的限制)
     *
     * @param obj       属性名称所在的对象
     * @param fieldName 属性名称
     * @return
     * @throws Exception
     */
    public static Object forceGetFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        Object object = null;
        boolean accessible = field.isAccessible();
        if (!accessible) {
            // 如果是private,protected修饰的属性,需要修改为可以访问的
            field.setAccessible(true);
            object = field.get(obj);
            // 还原private,protected属性的访问性质
            field.setAccessible(accessible);
            return object;
        }
        object = field.get(obj);
        return object;
    }
    public static void printfTaskInfoList(List<Task> list) {
        for (Task task : list) {
            System.out.println(task.toString());
        }
    }

    }


