package com.fromLab.action.TaskJavaDoc;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;


public class JavaDocConfigurable implements SearchableConfigurable {

    private JavaDocForm javaDocForm;
    private JavaDocSetting javaDocSetting = JavaDocSetting.getInstance();

    public JavaDocConfigurable() {
    }

    @NotNull
    public String getId() {
        return "TaskJavaDoc";
    }

    @Nls
    public String getDisplayName() {
        return this.getId();
    }

    @Nullable
    public String getHelpTopic() {
        return this.getId();
    }

    @Nullable
    public JComponent createComponent() {
        if(null == this.javaDocForm) {
            this.javaDocForm = new JavaDocForm();
        }
        return this.javaDocForm.mainPanel;
    }

    public boolean isModified() {
        return !this.javaDocSetting.getJavaDocFormat().equals(this.javaDocForm.textArea.getText());
    }

    public void apply() throws ConfigurationException {
        this.javaDocSetting.setJavaDocFormat(this.javaDocForm.textArea.getText());
    }

    public void reset() {
        this.javaDocForm.textArea.setText(this.javaDocSetting.getJavaDocFormat());
    }

}
