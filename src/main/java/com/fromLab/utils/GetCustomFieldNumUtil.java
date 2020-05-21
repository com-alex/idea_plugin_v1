package com.fromLab.utils;

import com.fromLab.exception.BusinessException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;


/**
 * @Auther: JIN KE
 * @Date: 2020/2/19 13:17
 */


public class GetCustomFieldNumUtil {

    /**
     *
     * @param customFieldName  Task type ，End date , Time spent
     * @
     * @return customFieldX
     */
    public static String getCustomFieldNum(String customFieldName, OpenprojectURL openprojectURL) throws BusinessException {
        String json= openprojectURL.getJson();
        JSONObject jsonObject = JSONObject.fromObject(json);



        String embedded = jsonObject.getString("_embedded");
        JSONObject embeddedJsonObject = JSONObject.fromObject(embedded);


        String schemas = embeddedJsonObject.getString("schemas");
        JSONObject schemaJsonObject = JSONObject.fromObject(schemas);


        String embedded1 = schemaJsonObject.getString("_embedded");
        Integer count = schemaJsonObject.getInt("count");
        JSONObject embeddedJsonObject1 = JSONObject.fromObject(embedded1);


        JSONArray elementsArray = embeddedJsonObject1.getJSONArray("elements");

        String result = null;

        int position = 0 ;
        for (int i = 0; i < count ; i++) {
            String s = String.valueOf(elementsArray.getJSONObject(i).getOrDefault("customField1", "null"));
            if (!"null".equals(s)){
                position=i;
            }
        }
        for (int i = 1; i < 20; i++) {

            String CustomFieldString = String.valueOf(elementsArray.getJSONObject(position).getOrDefault("customField" + i, "null"));
            if (!"null".equals(CustomFieldString)) {
                String[] split = CustomFieldString.split(",");
                String[] splitName = split[1].split(":");
                String name=splitName[1].substring(1,(splitName[1].length()-1));
                if (customFieldName.equals(name)){
                    result = "customField" + i;
                    return result;
                }

            }
        }
        if(StringUtils.isBlank(result)){
            throw new BusinessException("Fail to get CustomField:" + customFieldName);
        }

        return result;

    }


}
