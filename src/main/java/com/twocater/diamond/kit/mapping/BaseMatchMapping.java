package com.twocater.diamond.kit.mapping;

public class BaseMatchMapping implements MatchMapping {

    private String defaultPath;

    public BaseMatchMapping() {
    }

    public BaseMatchMapping(String defaultPath) {
        this.defaultPath = defaultPath;
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public void setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
    }

    @Override
    public boolean isMatch(String path, String pattern) {
        if (pattern == null) {
            return false;
        }
        if (pattern.equals(path)) {
            return true;
        }
        return defaultPath != null && pattern.equals(defaultPath);
    }

}
