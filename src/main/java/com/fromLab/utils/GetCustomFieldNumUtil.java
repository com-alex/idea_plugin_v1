package com.fromLab.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
 * @Auther: JIN KE
 * @Date: 2020/2/19 13:17
 */


public class GetCustomFieldNumUtil {

    private final static String OPENPROJECT_URL="http://projects.plugininide.com/openproject";
    private final static String API_KEY="e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123";

    /**
     *
     * @param customFieldName  ÀýÈçTask type £¬End date , Time spent
     * @param openProjectURL
     * @param apikey
     * @return customFieldX
     */
    public static String getCustomfiledNum(String customFieldName,String openProjectURL,String apikey){
        OpenprojectURL o=new OpenprojectURL(OPENPROJECT_URL,apikey,OpenprojectURL.WORKPAGES_URL);
        String json= o.getJson(OPENPROJECT_URL+"/api/v3/work_packages");
        JSONObject jsonObject = JSONObject.fromObject(json);



        String embedded = jsonObject.getString("_embedded");
        JSONObject embeddedJsonObject = JSONObject.fromObject(embedded);


        String schemas = embeddedJsonObject.getString("schemas");
        JSONObject schemaJsonObject = JSONObject.fromObject(schemas);


        String embedded1 = schemaJsonObject.getString("_embedded");
        Integer count = schemaJsonObject.getInt("count");
        JSONObject embeddedJsonObject1 = JSONObject.fromObject(embedded1);


        JSONArray elementsArray = embeddedJsonObject1.getJSONArray("elements");

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
//                System.out.println(name);
//                System.out.println("customField" + i);
                if (customFieldName.equals(name)){
                    return  "customField" + i;
                }

            }
        }

        return null;

    }

    public static void main(String[] args) {
        System.out.println(getCustomfiledNum("Task type", OPENPROJECT_URL, API_KEY));
    }
}
