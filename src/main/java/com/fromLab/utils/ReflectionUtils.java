package com.fromLab.utils;


import com.fromLab.annotation.Ignored;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wsh
 * @date 2019-12-09
 * The utility class for Reflection
 */

public class ReflectionUtils {

    /**
     * Create the object based on the the class and fields
     *
     * @param clazz
     * @param params
     * @return
     */
    public static Object createObject(Class clazz, List<Object> params) {
        Object object = null;
        try {
            object = clazz.newInstance();
            Field[] f = clazz.getDeclaredFields();
            for (int i = 0; i < f.length; i++) {
                String attributeName = f[i].getName();
                String methodName = attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
                Ignored ignored = f[i].getAnnotation(Ignored.class);
                if (ignored == null) {
                    try {
                        Method setMethod = clazz.getMethod("set" + methodName, String.class);
                        setMethod.invoke(object, params.get(i));
                    } catch (NoSuchMethodException e) {
                        Method setMethod = null;
                        try {
                            setMethod = clazz.getMethod("set" + methodName, Integer.class);
                            setMethod.invoke(object, params.get(i));
                        } catch (NoSuchMethodException ex) {
                            ex.printStackTrace();
                        } catch (IllegalAccessException ex) {
                            ex.printStackTrace();
                        } catch (InvocationTargetException ex) {
                            ex.printStackTrace();
                        }

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * Get the fields in a object
     *
     * @param object
     * @return List
     */
    public static List<Object> getObjectParams(Object object) {
        List<Object> objectParams = new ArrayList<>();
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String attributeName = fields[i].getName();
            String methodName = attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
            Object result;
            try {
                Ignored ignored = fields[i].getAnnotation(Ignored.class);
                if (ignored == null) {
                    Method getMethod = clazz.getMethod("get" + methodName);
                    result = getMethod.invoke(object);
                    objectParams.add(result);
                }
            } catch (NoSuchMethodException e) {

                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return objectParams;
    }

    /**
     * Copy the fields of one object to another
     *
     * @param o1 : The object being copied
     * @param o2 : result object
     * @return Object
     */
    public static Object copyProperties(Object o1, Object o2) {
        Class claz1 = o1.getClass();
        Class claz2 = o2.getClass();

        //get the field name and field value of the object being copied
        Map<String, Object> attributeMap = new HashMap<>();
        Field[] o1Fields = claz1.getDeclaredFields();
        for (Field field : o1Fields) {
            String attributeName = field.getName();
            String methodName = attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
            try {
                Method getMethod = claz1.getMethod("get" + methodName);
                Object result = getMethod.invoke(o1);
                attributeMap.put(attributeName, result);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        Field[] o2Fields = claz2.getDeclaredFields();
        for (Field field : o2Fields) {
            String attributeName = field.getName();
            String methodName = attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
            if (attributeMap.containsKey(attributeName)) {
                try {
                    Method setMethod = claz2.getMethod("set" + methodName, String.class);
                    setMethod.invoke(o2, attributeMap.get(attributeName));
                } catch (NoSuchMethodException e) {
                    Method setMethod = null;
                    Method setMethod2 = null;
                    try {
                        setMethod = claz2.getMethod("set" + methodName, Integer.class);
                        setMethod.invoke(o2, attributeMap.get(attributeName));
                    } catch (NoSuchMethodException ex) {
                        try {
                            setMethod2 = claz2.getMethod("set" + methodName, BigDecimal.class);
                            setMethod2.invoke(o2, attributeMap.get(attributeName));
                        } catch (NoSuchMethodException exception) {
                            exception.printStackTrace();
                        } catch (IllegalAccessException exception) {
                            exception.printStackTrace();
                        } catch (InvocationTargetException exception) {
                            exception.printStackTrace();
                        }
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    } catch (InvocationTargetException ex) {
                        ex.printStackTrace();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return o2;
    }
}
