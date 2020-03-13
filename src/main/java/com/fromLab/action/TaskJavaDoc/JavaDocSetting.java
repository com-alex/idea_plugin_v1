package com.fromLab.action.TaskJavaDoc;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;


@State(
        name = "JavaDocSetting",
        storages = @Storage("$APP_CONFIG$/JavaDoc.xml")

)
public class JavaDocSetting implements PersistentStateComponent<Element> {

    private String JavaDocFormat;


    public JavaDocSetting() {
    }

    public static JavaDocSetting getInstance() {
        return (JavaDocSetting) ServiceManager.getService(JavaDocSetting.class);
    }

    @Nullable
    public Element getState() {
        Element element = new Element("JavaDocSetting");
        element.setAttribute("JavaDocFormat", this.getJavaDocFormat());
        return element;
    }

    public void loadState(Element state) {
        this.setJavaDocFormat(state.getAttributeValue("JavaDocFormat"));
    }

    public String getJavaDocFormat() {
        return this.JavaDocFormat == null ? StatementGenerator.defaultFormat : this.JavaDocFormat;
    }

    public void setJavaDocFormat(String SetJavaDocFormat) {
        this.JavaDocFormat = SetJavaDocFormat;
    }


}