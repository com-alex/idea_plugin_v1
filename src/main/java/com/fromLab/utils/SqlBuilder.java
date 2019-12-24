package com.fromLab.utils;

import com.fromLab.annotation.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wsh
 * @date 2019-11-06
 * SQL封装的工具类
 * 用法：
 *      使用SqlBuilder.getInstance创建sqlBuilder
 *      用sqlBuilder和链式语句拼接sql语句
 *      调用createQuery(sqlBuilder.getSql(), sql.getParams(). 转换的类.class)就能直接从数据库查询数据并赋值给对象
 *      调用saveOrUpdate(Object) 传入一个实体类，会直接插入数据库或更新数据库
 * 注意点：
 *      该方法只能自动给类里的String、Integer属性赋值，其他类型不能使用（时间、float等不可以）
 *      使用时对象的属性顺序要与查询顺序一致的
 *
 * 详细见
 *      com.User 实体类
 *      GUI.java.com.alex.utils.SqlBuilderTest 测试方法
 */
public class SqlBuilder {
    private StringBuffer sb = new StringBuffer();
    private List<Object> params = new ArrayList<>();
    private Integer count = 0;
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet rs;

    public SqlBuilder() {
        connection = DBUtils.getConnection();
    }

    public static SqlBuilder getInstance() {
        return new SqlBuilder();
    }

    public String getSql() {
        return sb.toString();
    }

    public List<Object> getParams() {
        return this.params;
    }

    public SqlBuilder append(String sql) {
        sb.append(" ").append(sql).append("  ");
        return this;
    }

    public SqlBuilder select(String sql) {
        sb.append("select ").append(sql).append(" ");
        return this;
    }

    public SqlBuilder insert(String sql){
        sb.append("insert into  ").append(sql).append("  ");
        return this;
    }

    public SqlBuilder values(List<Object> insertParams){
        sb.append("value (");
        for(int i = 0; i < insertParams.size(); i++){
            if(i == insertParams.size() - 1){
                sb.append(" ? )");
            }else{
                sb.append(" ?, ");
            }
            params.add(insertParams.get(i));
        }
        return this;
    }

    public SqlBuilder update(String sql) {
        sb.append("update ").append(sql).append("  ");
        return this;
    }

    public SqlBuilder delete(String sql) {
        sb.append("delete from ").append(sql).append("  ");
        return this;
    }

    public SqlBuilder set(String sql, Object param) {
        if (param != null) {
            if (count == 0) {
                sb.append(" set ").append(sql).append(" = ?");
                count++;
            } else {
                sb.append(" , ").append(sql).append(" = ?");
            }
            params.add(param);
        }
        return this;
    }

    public SqlBuilder updateWhere(String sql, Object param){
        if(param != null){
            sb.append(" where " + sql + " = ? ");
            params.add(param);
        }
        return this;
    }

    public SqlBuilder from() {
        sb.append(" from ");
        return this;
    }

    public SqlBuilder from(String sql) {
        sb.append(" from ").append(sql).append(" ");
        return this;
    }

    public SqlBuilder where() {
        sb.append(" where 1 = 1 ");
        return this;
    }

    public SqlBuilder where(String sql, Object param) {
        if (param != null) {
            sb.append(" where ").append(sql).append("  ");
            params.add(param);
        }
        return this;
    }

    public SqlBuilder add(String sql) {
        sb.append(" and ").append(sql).append(" ");
        return this;
    }

    public SqlBuilder addEqualTo(String sql, Object param) {
        if (param != null) {
            sb.append(" and ").append(sql).append(" = ? ");
            params.add(param);
        }
        return this;
    }

    public SqlBuilder isNull(String sql, Object param) {
        if (param != null) {
            sb.append(" and ").append(sql).append(" is null ");
            params.add(param);
        }
        return this;
    }

    public SqlBuilder isNotNull(String sql, Object param) {
        if (param != null) {
            sb.append(" and ").append(sql).append(" is not null ");
            params.add(param);
        }
        return this;
    }

    public SqlBuilder addGreaterThen(String sql, Object param) {
        if (param != null) {
            sb.append(" and ").append(sql).append(" > ? ");
            params.add(param);
        }
        return this;
    }

    public SqlBuilder addGreaterOrEqualTo(String sql, Object param) {
        if (param != null) {
            sb.append(" and ").append(sql).append(" >= ? ");
            params.add(param);
        }
        return this;
    }

    public SqlBuilder addLessThen(String sql, Object param) {
        if (param != null) {
            sb.append(" and ").append(sql).append(" < ? ");
            params.add(param);
        }
        return this;
    }

    public SqlBuilder addLessOrEqualTo(String sql, Object param) {
        if (param != null) {
            sb.append(" and ").append(sql).append(" <= ? ");
            params.add(param);
        }
        return this;
    }

    public SqlBuilder addLike(String sql, Object param) {
        if (param != null) {
            sb.append(" and ").append(sql).append(" like ? ");
            params.add(param);
        }
        return this;
    }

    public SqlBuilder addNotLike(String sql, Object param) {
        if (param != null) {
            sb.append(" and ").append(sql).append(" not like ? ");
            params.add(param);
        }
        return this;
    }

    /**
     * 查询对象，返回值为对象的数组
     * @param sql
     * @param params
     * @param clazz
     * @return
     * @throws Exception
     */
    public Object createQuery(String sql, List<Object> params, Class clazz) throws Exception {
        preparedStatement = connection.prepareStatement(sql);
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
        }
        rs = preparedStatement.executeQuery();

        int columnCount = rs.getMetaData().getColumnCount();


        List<List<Object>> fieldsGroup = new ArrayList<List<Object>>();
        while(rs.next()){
            List<Object> fields = new ArrayList<Object>();
            for (int i = 0 ; i < columnCount; i++){
                fields.add(rs.getObject(i + 1));
            }
            fieldsGroup.add(fields);
        }
        List<Object> objectList = ReflectionUtils.createObjects(clazz, fieldsGroup);
        return objectList;
    }

    private static Boolean createUpdate(String sql, List<Object> params) throws Exception{
        preparedStatement = connection.prepareStatement(sql);
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
        }
        int result = preparedStatement.executeUpdate();
        return result != 0 ? true : false;
    }

    /**
     * 保存和更新操作（静态方法）
     * 如果数据库存在数据，则执行更新，否则执行保存
     * @param object  实例对象
     * @return  Boolean
     * @throws Exception
     */
    public static Boolean saveOrUpdate(Object object) throws Exception {
        //获取实体映射类的表名
        String tableName = "";
        Class clazz = object.getClass();
        Table table = (Table) clazz.getAnnotation(Table.class);
        if (table != null){
            tableName = table.tableName();
        } else{
            throw new RuntimeException("error");
        }
        //获取实体映射类的属性值
        List<Object> fields = ReflectionUtils.getObjectParams(object);
        String primaryKey = ReflectionUtils.getPrimaryKeyAttributeName(clazz);
        SqlBuilder sqlBuilder = SqlBuilder.getInstance();
        sqlBuilder.select("*")
                .from(tableName)
                .where()
                .addEqualTo(primaryKey, fields.get(0));
        List<Object> objectList = (List<Object>) sqlBuilder.createQuery(sqlBuilder.getSql(), sqlBuilder.getParams(), clazz);

        //获取实体映射累的属性上的注解
        List<String> annotations = ReflectionUtils.getAttributeAnnotations(object);

        if(objectList != null && objectList.size() > 0){

            //执行update操作
            SqlBuilder updateSqlBuilder = SqlBuilder.getInstance();
            updateSqlBuilder.update(tableName);
                    for(int i = 1; i < annotations.size(); i++){
                        updateSqlBuilder.set(annotations.get(i), fields.get(i));
                    }
            updateSqlBuilder.updateWhere(primaryKey, fields.get(0));
            return createUpdate(updateSqlBuilder.getSql(), updateSqlBuilder.getParams());
        }
        else{
            //执行save操作
            SqlBuilder saveSqlBuilder = SqlBuilder.getInstance();
            saveSqlBuilder.insert(tableName)
                    .values(fields);
            return createUpdate(saveSqlBuilder.getSql(), saveSqlBuilder.getParams());
        }
    }

    /**
     * 关闭所有资源（静态方法）
     */
    public static void closeAll(){
        DBUtils.closeAll(connection, preparedStatement, rs);
    }
}
