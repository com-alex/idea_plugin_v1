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
         * Sort the elements of list according to multiple attribute names,
         * The attributes of the list element can be numbers ，char，String and java.util.Date
         *
         * @param list
         * @param sortnameArr List element attribute name
         * @param isAsc    true ascending, false descending
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
         * For each attribute of the list, specify whether it is ascending or descending
         *
         * @param list
         * @param sortnameArr Parameter array
         * @param typeArr     Array of descending order corresponding to each property, true ascending, false descending
         */

        public static <E> void sort(List<E> list, final String[] sortnameArr, final boolean[] typeArr) {
            if (sortnameArr.length != typeArr.length) {
                throw new RuntimeException("The number of elements in the attribute array and the number " +
                        "of elements in the descending array are not equal");
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
         * Sort 2 objects according to the specified attribute name
         *
         * @param sortname Attribute name
         * @param isAsc    true ascending, false descending
         * @param a
         * @param b
         * @return
         * @throws Exception
         */
        private static <E> int compareObject(final String sortname, final boolean isAsc, E a, E b) throws Exception {
            int ret;
            Object value1 = SortUtils.forceGetFieldValue(a, sortname);
            Object value2 = SortUtils.forceGetFieldValue(b, sortname);
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
     * Fill the digital object with 0 on the left according to the specified length.
     * example: addZero2Str(11,4) 返回 "0011", addZero2Str(-18,6) return "-000018"
     *
     * @param numObj number object
     * @param length Specified length
     * @return
     */
    public static String addZero2Str(Number numObj, int length) {
        NumberFormat nf = NumberFormat.getInstance();
        // Set whether to use grouping
        nf.setGroupingUsed(false);
        // Set the maximum integer digits
        nf.setMaximumIntegerDigits(length);
        // Set minimum integer digits
        nf.setMinimumIntegerDigits(length);
        return nf.format(numObj);
    }

    /**
     * Get the specified attribute value of the specified object (remove the restrictions of private and protected)
     *
     * @param obj       The object where the property name is
     * @param fieldName Attribute name
     * @return
     * @throws Exception
     */
    public static Object forceGetFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        Object object = null;
        boolean accessible = field.isAccessible();
        if (!accessible) {
            // If it is private or protected, it needs to be modified to be accessible
            field.setAccessible(true);
            object = field.get(obj);
            // Restore the access properties of private and protected attributes
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


