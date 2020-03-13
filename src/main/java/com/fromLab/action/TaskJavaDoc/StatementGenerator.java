package com.fromLab.action.TaskJavaDoc;

public class StatementGenerator {

    public static String defaultFormat =
            "/**\n" +
             "* @taskName test1  \n" +
             "* @taskId 123  \n" +
             "* @priority high  \n" +
             "* @status  in progress  \n" +
             "* @startTime ${DATE}  \n" +
             "* @dueTime   2020-03-05 \n" +
             "* @timeSpent 123  \n" +
             "*/ ";

    public  static String[]  StringToStringArray(String defaultFormat){
        boolean flag=true;
        String[] arr=new String[20];
        int lastindex=3;
        arr[0]="/**";
        int count=1;
        while (flag){
            int i = defaultFormat.indexOf("\n", lastindex+1);
            if (i==-1) {
                arr[count] = " */";
                flag = false;
            }
            else {
                String s=defaultFormat.substring(lastindex+1,i);
                arr[count]=s;
                count++;
                lastindex=i;
            }
        }
    return arr;
    }
    public static void main(String[] args) {
        String[] string = StringToStringArray(defaultFormat);
        for (int i = 0; i < string.length; i++) {
            System.out.println(string[i]);

        }
    }
}