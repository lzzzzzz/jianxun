package org.openmore.codegen;

/**
 * Created by LZ on 2017/7/11.
 */
public class OpenMoreEntity {
    private String modelName;
    private String className;
    private String classNameZn;
    private String attrs;
    private String controllerDesc;
    private boolean flagCreateFile;


    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassNameZn() {
        return classNameZn;
    }

    public void setClassNameZn(String classNameZn) {
        this.classNameZn = classNameZn;
    }

    public String getController_desc() {
        return controllerDesc;
    }

    public void setController_desc(String controllerDesc) {
        this.controllerDesc = controllerDesc;
    }

    public boolean getFlag_create_file() {
        return flagCreateFile;
    }

    public void setFlag_create_file(boolean flagCreateFile) {
        this.flagCreateFile = flagCreateFile;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }
}
