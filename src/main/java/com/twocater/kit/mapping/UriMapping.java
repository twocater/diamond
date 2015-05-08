package com.twocater.kit.mapping;

import java.util.Map;

public class UriMapping implements SingleMapping {

    private Map<String, String> servletMappings;// 路径-->名称

    public UriMapping(Map<String, String> servletMappings) {
        this.servletMappings = servletMappings;
    }

    @Override
    public MappingResult mapping(String path) {
        // 精确路径匹配
        String temp = path;
        for (;;) {
            String name = servletMappings.get(temp);
            if (name != null) {
                String extraPath = path.substring(temp.length());
                if ("".equals(extraPath)) {
                    extraPath = null;
                }
                return createMappingResult(temp, name, temp, extraPath);
            }
            int index = temp.lastIndexOf("/");
            if (index == -1) {
                break;
            }
            temp = temp.substring(0, index);
        }
        // 最长路径匹配
        temp = path;
        for (;;) {
            int index = temp.lastIndexOf("/");
            if (index == -1) {
                break;
            }
            temp = temp.substring(0, index);
            String pattern = temp + "/*";
            String name = servletMappings.get(pattern);
            if (name != null) {
                String extraPath = path.substring(temp.length());
                if ("".equals(extraPath)) {
                    extraPath = null;
                }
                return createMappingResult(pattern, name, temp, extraPath);
            }
        }
        // 扩展匹配
        int index = path.lastIndexOf(".");
        if (index != -1) {
            String pattern = "*" + path.substring(index);
            String name = servletMappings.get(pattern);
            if (name != null) {
                if ("".equals(path)) {
                    path = null;
                }
                return createMappingResult(pattern, name, "", path);
            }
        }
        // 缺省匹配
        String name = servletMappings.get("/");
        if (name != null) {
            if ("".equals(path)) {
                path = null;
            }
            return createMappingResult("/", name, "", path);
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
