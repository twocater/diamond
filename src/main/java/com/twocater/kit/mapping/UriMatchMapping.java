package com.twocater.kit.mapping;

public class UriMatchMapping implements MatchMapping {

    @Override
    public boolean isMatch(String path, String pattern) {
        if (pattern == null) {
            return false;
        }
        // 缺省匹配
        if (pattern.equals("/")) {
            return true;
        }
        if (path == null || path.isEmpty()) {
            return false;
        }
        // 精确路径匹配
        String temp = path;
        for (;;) {
            if (pattern.equals(temp)) {
                return true;
            }
            int index = temp.lastIndexOf("/");
            if (index == -1) {
                break;
            }
            temp = temp.substring(0, index);
        }
        // 最长路径匹配
        if (pattern.endsWith("/*")) {
            return path.equals(pattern.substring(0, pattern.length() - 2)) || pattern.regionMatches(0, path, 0, pattern.length() - 1);
        }
        // 扩展匹配
        if (pattern.startsWith("*.")) {
            return path.endsWith(pattern.substring(1));
        }
        return false;
    }
}
