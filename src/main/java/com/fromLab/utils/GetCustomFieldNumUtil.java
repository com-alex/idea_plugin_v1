package com.fromLab.utils;

import com.fromLab.OpenprojectURL;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import static com.fromLab.utils.JsonToObjectUtil.apiKey;
import static com.fromLab.utils.JsonToObjectUtil.openprojectURL;

/**
 * @Auther: JIN KE
 * @Date: 2020/2/19 13:17
 */


public class GetCustomFieldNumUtil {
    /**
     *
     * @param customFieldName  例如Task type ，End date , Time spent
     * @param openProjectURL
     * @param apikey
     * @return customFieldX
     */
    public static String getCustomfiledNum(String customFieldName,String openProjectURL,String apikey){
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apikey,OpenprojectURL.WORKPAGES_URL);
        String json= o.getJson(openprojectURL+"/api/v3/work_packages");
        JSONObject jsonObject = JSONObject.fromObject(json);
        String embedded = jsonObject.getString("_embedded");
        JSONObject embeddedJsonObject = JSONObject.fromObject(embedded);
        String schemas = embeddedJsonObject.getString("schemas");
        JSONObject schemaJsonObject = JSONObject.fromObject(schemas);
        String embedded1 = schemaJsonObject.getString("_embedded");
        JSONObject embeddedJsonObject1 = JSONObject.fromObject(embedded1);
        JSONArray elementsArray = embeddedJsonObject1.getJSONArray("elements");
        for (int i = 1; i < 20; i++) {
            String CustomFieldString = String.valueOf(elementsArray.getJSONObject(elementsArray.size() - 1).getOrDefault("customField" + i, "null"));
            if (!"null".equals(CustomFieldString)) {
                String[] split = CustomFieldString.split(",");
                String[] splitName = split[1].split(":");
                String name=splitName[1].substring(1,(splitName[1].length()-1));
//                System.out.println(name);
//                System.out.println("customField" + i);
                if (customFieldName.equals(name))
                    return  "customField" + i;
            }
        }

        return null;

    }

    public static void main(String[] args) {
        System.out.println(getCustomfiledNum("Task type", openprojectURL, apiKey));
    }
}
