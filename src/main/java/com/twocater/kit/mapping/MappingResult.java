package com.twocater.kit.mapping;

public class MappingResult {

    /**
     * 映射目标对象名称
     */
    private String desName;
    /**
     * 匹配模式
     */
    private String pattern;
    /**
     * 映射路径(与额外路径一起组合为完整路径)
     */
    private String mappingPath;
    /**
     * 额外路径
     */
    private String extraPath;

    public String getDesName() {
        return desName;
    }

    public void setDesName(String desName) {
        this.desName = desName;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getMappingPath() {
        return mappingPath;
    }

    public void setMappingPath(String mappingPath) {
        this.mappingPath = mappingPath;
    }

    public String getExtraPath() {
        return extraPath;
    }

    public void setExtraPath(String extraPath) {
        this.extraPath = extraPath;
    }

    @Override
    public String toString() {
        return "MappingResult [desName=" + desName + ", pattern=" + pattern + ", mappingPath=" + mappingPath + ", extraPath=" + extraPath + "]";
    }

}
