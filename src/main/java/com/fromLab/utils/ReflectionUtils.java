package com.fromLab.utils;


import com.fromLab.annotation.Column;
import com.fromLab.annotation.Ignored;
import com.fromLab.annotation.PrimaryKey;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wsh
 * @date 2019-12-09
 * 反射工具类
 * 用于创建实例对象和实例对象链表
 */
public class ReflectionUtils {

    public static List<Object> createObjects(Class clazz, List<List<Object>> fieldsGroud){
        List<Object> objectList = new ArrayList<>();
        for(List<Object> fields : fieldsGroud){
            Object object =  ReflectionUtils.createObject(clazz, fields);
            objectList.add(object);
        }
        return objectList;
    }

    public static Object createObject(Class clazz, List<Object> params){
        Object object = null;
        try {
            object = clazz.newInstance();
            Field[] f = clazz.getDeclaredFields();
            for (int i = 0; i < f.length; i++){
                String attributeName = f[i].getName();
                String methodName=attributeName.substring(0,1).toUpperCase()+attributeName.substring(1);
                Ignored ignored = f[i].getAnnotation(Ignored.class);
                if(ignored == null){
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

    public static List<Object> getObjectParams(Object object){
        List<Object> objectParams = new ArrayList<>();
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(int i = 0; i < fields.length; i++){
            String attributeName = fields[i].getName();
            String methodName=attributeName.substring(0,1).toUpperCase()+attributeName.substring(1);
            Object result;
            try {
                Ignored ignored = fields[i].getAnnotation(Ignored.class);
                if(ignored == null){
                    Method getMethod=clazz.getMethod("get"+methodName);
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


    public static List<String> getAttributeAnnotations(Object object){
        List<String> attributeAnnotations = new ArrayList<>();
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(int i = 0; i < fields.length; i++){
            Column column = fields[i].getAnnotation(Column.class);
            if(column != null){
                attributeAnnotations.add(column.column());
            }
        }
        return attributeAnnotations;
    }

    /**
     *
     * @param o1 : 被复制对象
     * @param o2 : 结果
     * @return
     */
    public static Object copyProperties(Object o1, Object o2){
        Class claz1 = o1.getClass();
        Class claz2 = o2.getClass();

        //获取o1的属性名与属性值
        Map<String, Object> attributeMap = new HashMap<>();
        Field[] o1Fields = claz1.getDeclaredFields();
        for(Field field : o1Fields) {
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
        for (Field field : o2Fields){
            String attributeName = field.getName();
            String methodName=attributeName.substring(0,1).toUpperCase()+attributeName.substring(1);
            if (attributeMap.containsKey(attributeName)){
                try {
                    Method setMethod = claz2.getMethod("set" + methodName, String.class);
                    setMethod.invoke(o2, attributeMap.get(attributeName));
                } catch (NoSuchMethodException e) {
                    Method setMethod = null;
                    try {
                        setMethod = claz2.getMethod("set" + methodName, Integer.class);
                        setMethod.invoke(o2, attributeMap.get(attributeName));
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
        return o2;
    }

    public static String getPrimaryKeyAttributeName(Class clazz){
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            PrimaryKey annotation = field.getAnnotation(PrimaryKey.class);
            if(annotation != null){
                Column column = field.getAnnotation(Column.class);
                return column.column();
            }
        }
        return null;
    }

}
