package com.twocater.kit.mapping;

import java.util.Map;
import java.util.Map.Entry;

public class BaseMapping implements SingleMapping {

    private Map<String, String> mappings;
    private String defaultPath;

    public BaseMapping(Map<String, String> mappings) {
        this.mappings = mappings;
    }

    public BaseMapping(Map<String, String> mappings, String defaultPath) {
        this.mappings = mappings;
        this.defaultPath = defaultPath;
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public void setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
    }

    public Map<String, String> getMappings() {
        return mappings;
    }

    @Override
    public MappingResult mapping(String path) {
        // 精确路径匹配 
        // ??可以直接使用mappings.get(path);
        for (Entry<String, String> entry : mappings.entrySet()) {
            if (entry.getKey().equals(path)) {
                return createMappingResult(entry.getKey(), entry.getValue(), path, null);
            }
        }
        if (defaultPath != null) {
            // 缺省匹配
            for (Entry<String, String> entry : mappings.entrySet()) {
                if (entry.getKey().equals(defaultPath)) {
                    if ("".equals(path)) {
                        path = null;
                    }
                    return createMappingResult(entry.getKey(), entry.getValue(), "", path);
                }
            }
        }
        return null;
    }

    private MappingResult createMappingResult(String pattern, String desName, String mappingPath, String extraPath) {
        MappingResult mappingResult = new MappingResult();
        mappingResult.setDesName(desName);
        mappingResult.setPattern(pattern);
        mappingResult.setMappingPath(mappingPath);
        mappingResult.setExtraPath(extraPath);
        return mappingResult;
    }

}
