package com.twocater.diamond.util;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

public class HttpUtil {
    private static final DateFormat COOKIE_FORMAT = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss z", Locale.US);
    private static final String ancientDate;

    static {
        COOKIE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
        ancientDate = COOKIE_FORMAT.format(new Date(10000));
    }

    // -----------------------------解析头-----------------------------

    /**
     * 解析头:Accept-Language
     *
     * @param value 格式：语言-国家-种类 例如:zh-cn
     * @return 可接受语言列表
     */
    public static List<Locale> parseLocales(String value) {
        // 按照权重排序
        TreeMap<Double, List<Locale>> locales = new TreeMap<Double, List<Locale>>();
        String[] strs = value.split(",");
        for (String entry : strs) {
            double quality = 1.0;
            int semi = entry.indexOf(";q=");
            if (semi >= 0) {
                try {
                    quality = Double.parseDouble(entry.substring(semi + 3));
                } catch (NumberFormatException e) {
                    quality = 0.0;
                }
                entry = entry.substring(0, semi);
            }
            if (quality < 0.00005) {
                continue;
            }
            if ("*".equals(entry)) {
                continue;
            }
            String[] temps = entry.split("-", 3);
            String language = temps[0];
            String country = temps.length > 1 ? temps[1] : "";
            String variant = temps.length > 2 ? temps[2] : "";
            if (!isWord(language) || !isWord(country) || !isWord(variant)) {
                continue;
            }
            Locale locale = new Locale(language, country, variant);
            Double key = new Double(-quality);
            List<Locale> values = locales.get(key);
            if (values == null) {
                values = new ArrayList<Locale>();
                locales.put(key, values);
            }
            values.add(locale);
        }
        List<Locale> list = new ArrayList<Locale>();
        for (List<Locale> localeList : locales.values()) {
            for (Locale locale : localeList) {
                list.add(locale);
            }
        }
        return list;
    }

    /**
     * 解析头:Content-Type
     *
     * @param contentType 内容类型
     * @return 内容编码
     */
    public static String parseCharset(String contentType) {
        if (contentType == null) {
            return null;
        }
        int start = contentType.indexOf("charset=");
        if (start == -1) {
            return null;
        }
        String encoding = contentType.substring(start + 8).trim();
        int end = encoding.indexOf(';');
        if (end >= 0) {
            encoding = encoding.substring(0, end);
        }
        if (encoding.length() > 2 && encoding.startsWith("\"") && encoding.endsWith("\"")) {
            encoding = encoding.substring(1, encoding.length() - 1);
        }
        return encoding;

    }

    // -----------------------------解析命令行-----------------------------

    /**
     * 解析参数
     *
     * @param data
     * @return
     */
    public static Map<String, String[]> parseParameter(String data) {
        Map<String, String[]> params = new HashMap<String, String[]>();
        if (isNotEmpty(data)) {
            String[] temps = data.split("&");
            for (String temp : temps) {
                String[] kv = temp.split("=", 2);
                String key = kv[0];
                String value = kv.length == 1 ? "" : kv[1];
                if (params.get(key) == null) {
                    params.put(key, new String[]{value});
                } else {
                    String[] values = params.get(key);
                    String[] newValues = new String[values.length + 1];
                    System.arraycopy(values, 0, newValues, 0, values.length);
                    newValues[values.length] = value;
                    params.put(key, newValues);
                }
            }
        }
        return params;
    }

    public static Map<String, List<String>> parseListParameter(String data) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        if (isNotEmpty(data)) {
            String[] temps = data.split("&");
            for (String temp : temps) {
                String[] kv = temp.split("=", 2);
                String key = kv[0];
                String value = kv.length == 1 ? "" : kv[1];
                List<String> list = params.get(key);
                if (list == null) {
                    list = new ArrayList<String>();
                    params.put(key, list);
                }
                list.add(value);
            }
        }
        return params;
    }

    public static void parseParameters(Map<String, String[]> map, String queryString, String encoding) {
        byte[] data = null;
        try {
            data = queryString.getBytes(encoding);
        } catch (Exception e) {
            throw new IllegalArgumentException("UnsupportedEncoding:" + encoding);
        }
        if (data == null || data.length == 0) {
            return;
        }
        int ix = 0;
        int ox = 0;
        String key = null;
        String value = null;
        while (ix < data.length) {
            byte c = data[ix++];
            switch ((char) c) {
                case '&':
                    try {
                        value = new String(data, 0, ox, encoding);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("UnsupportedEncoding:" + encoding);
                    }
                    if (key != null) {
                        putMapEntry(map, key, value);
                        key = null;
                    }
                    ox = 0;
                    break;
                case '=':
                    if (key == null) {
                        try {
                            key = new String(data, 0, ox, encoding);
                        } catch (Exception e) {
                            throw new IllegalArgumentException("UnsupportedEncoding:" + encoding);
                        }
                        ox = 0;
                    } else {
                        data[ox++] = c;
                    }
                    break;
                case '+':
                    data[ox++] = (byte) ' ';
                    break;
                case '%':
                    data[ox++] = (byte) ((convertHexDigit(data[ix++]) << 4) + convertHexDigit(data[ix++]));
                    break;
                default:
                    data[ox++] = c;
            }
        }
        if (key != null) {
            try {
                value = new String(data, 0, ox, encoding);
            } catch (Exception e) {
                throw new IllegalArgumentException("UnsupportedEncoding:" + encoding);
            }
            putMapEntry(map, key, value);
        }

    }

    /**
     * 在字符串前后增加双引号,并格式化字串内的双引号<br/>
     * 如果字符串格式为"*",则只格式化字串内的双引号
     *
     * @param s
     * @return
     */
    public static String wrapDoubleQuotes(String s) {
        if (s == null || s.length() == 0) {
            return "\"\"";
        }
        if (s.startsWith("\"") && s.endsWith("\"")) {
            return s;
        }
        s = "\"" + escapeDoubleQuotes(s) + "\"";
        return s;
    }

    /**
     * 格式化双引号<br/>
     * 将双引号转换为\"
     *
     * @param s
     * @return
     */
    public static String escapeDoubleQuotes(String s) {
        if (s == null || s.length() == 0 || s.indexOf('"') == -1) {
            return s;
        }
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\') {
                b.append(c);
                i++;
                if (i < s.length()) {
                    b.append(s.charAt(i));
                }
            } else if (c == '"') {
                b.append('\\').append('"');
            } else {
                b.append(c);
            }
        }
        return b.toString();
    }

    /**
     * 格式化请求路径
     *
     * @param path
     * @return
     */
    public static String formatRequestPath(String path) {

        if (path == null) {
            return null;
        }
        if (path.indexOf('\\') != -1) {
            path = path.replace('\\', '/');
        }

        if (path.equals("/.")) {
            return "/";
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        // Resolve occurrences of "//" in the normalized path
        path.replaceAll("//", "/");
        // Resolve occurrences of "/./" in the normalized path
        while (true) {
            int index = path.indexOf("/./");
            if (index == -1) {
                break;
            }
            path = path.substring(0, index) + path.substring(index + 2);
        }

        // Resolve occurrences of "/../" in the normalized path
        while (true) {
            int index = path.indexOf("/../");
            if (index == -1) {
                break;
            }
            if (index == 0)
                return null; // Trying to go outside our context
            int index2 = path.lastIndexOf('/', index - 1);
            path = path.substring(0, index2) + path.substring(index + 3);
        }
        return path;
    }

    /**
     * 将一个相对路径转换为URL绝对路径<br/>
     * 如果uri以/开头，则相对于环境路径，否则相对于所有路径
     *
     * @param uri         需要转换的路径，此路径不包括协议
     * @param relativeUrl 作为参照的URL地址,包括路径信息
     * @return
     */
    public static String toAbsolute(String uri, String relativeUrl) {
        if (uri == null) {
            return null;
        }
        int index = relativeUrl.indexOf("//");
        if (index == -1) {
            throw new IllegalArgumentException("relativeUrl=" + relativeUrl);
        }
        if (uri.startsWith("/")) {
            index = relativeUrl.indexOf("/", index + 2);
            if (index == -1) {
                return relativeUrl + uri;
            }
            index = relativeUrl.indexOf("/", index + 1);
            if (index == -1) {
                return relativeUrl + uri;
            }
            return relativeUrl.substring(0, index) + uri;
        }
        int end = relativeUrl.lastIndexOf("/");
        return relativeUrl.substring(0, end) + "/" + uri;
    }

    private static void putMapEntry(Map<String, String[]> map, String name, String value) {
        String[] newValues = null;
        String[] oldValues = map.get(name);
        if (oldValues == null) {
            newValues = new String[1];
            newValues[0] = value;
        } else {
            newValues = new String[oldValues.length + 1];
            System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
            newValues[oldValues.length] = value;
        }
        map.put(name, newValues);
    }

    private static byte convertHexDigit(byte b) {
        if ((b >= '0') && (b <= '9')) {
            return (byte) (b - '0');
        }
        if ((b >= 'a') && (b <= 'f')) {
            return (byte) (b - 'a' + 10);
        }
        if ((b >= 'A') && (b <= 'F')) {
            return (byte) (b - 'A' + 10);
        }
        throw new IllegalArgumentException("HttpUtil.convertHexDigit.notHex" + Character.valueOf((char) b));
    }

    public static Map<String, String> parseCookie(String cookie) {
        Map<String, String> map = new HashMap<String, String>();
        String[] strs = cookie.split(";");
        for (String entry : strs) {
            String[] temps = entry.split(",");
            for (String temp : temps) {
                String[] kv = temp.split("=", 2);
                String key = kv[0].trim();
                String val = kv.length > 1 ? kv[1].trim() : "";
                String cookieName = null;
                String cookieVal = null;
                if (!key.equalsIgnoreCase("version") && !key.equalsIgnoreCase("domain") && !key.equalsIgnoreCase("path") && !key.equalsIgnoreCase("comment")
                        && !key.equalsIgnoreCase("max-age") && !key.equalsIgnoreCase("secure")) {
                    cookieName = key;
                    cookieVal = val;
                }
                if (cookieName != null) {
                    map.put(cookieName, cookieVal);
                }
            }
        }
        return map;
    }

    public static String encodeCookie(String name, String value) {
        StringBuffer sb = new StringBuffer();
        sb.append(name).append("=").append(value);
        sb.append("; Expires=").append(ancientDate);
        return sb.toString();
    }

    public static void parseSetCookie(Map<String, String> cookies, String cookie) {
        String[] strs = cookie.split(";");
        for (String entry : strs) {
            String[] kv = entry.split("=", 2);
            String key = kv[0].trim();
            String val = kv.length > 1 ? kv[1].trim() : "";
            String cookieName = null;
            String cookieVal = null;
            if (!key.equalsIgnoreCase("version") && !key.equalsIgnoreCase("domain") && !key.equalsIgnoreCase("path") && !key.equalsIgnoreCase("comment")
                    && !key.equalsIgnoreCase("max-age") && !key.equalsIgnoreCase("secure") && !key.equalsIgnoreCase("Expires")) {
                cookieName = key;
                cookieVal = val;
            }
            if (cookieName != null) {
                cookies.put(cookieName, cookieVal);
            }
        }
    }

    public static boolean isEmpty(String src) {
        return src == null || src.trim().length() == 0;
    }

    public static boolean isNotEmpty(String src) {
        return src != null && src.trim().length() > 0;
    }

    /**
     * 是否英文字母
     *
     * @param value
     * @return
     */
    public static boolean isWord(String value) {
        return value.matches("[A-Za-z]*");
    }

    public static boolean isInteger(String src) {
        if (src == null) {
            return false;
        }
        try {
            Integer.parseInt(src);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getHostAddress(SocketAddress address) {
        String ip = address.toString().trim();
        int end = ip.lastIndexOf(':');
        if (end == -1) {
            end = ip.length();
        }
        int start = ip.indexOf("/") + 1;
        return ip.substring(start, end);
    }

    public static String getHostAddress(InetSocketAddress address) {
        return address.getAddress().getHostAddress();
    }

    public static String getHostName(SocketAddress address) {
        String ip = address.toString().trim();
        if (ip.startsWith("/")) {
            int end = ip.lastIndexOf(':');
            if (end == -1) {
                end = ip.length();
            }
            return ip.substring(1, end);
        }
        if (ip.contains("/")) {
            return ip.substring(0, ip.indexOf("/"));
        }
        int end = ip.lastIndexOf(':');
        if (end == -1) {
            end = ip.length();
        }
        return ip.substring(0, end);
    }

    public static String getHostName(InetSocketAddress address) {
        return address.getAddress().getHostName();
    }

    public static int getHostPort(SocketAddress address) {
        String port = address.toString().trim();
        int start = port.lastIndexOf(':');
        if (start == -1) {
            return -1;
        }
        return Integer.parseInt(port.substring(start + 1));
    }

    public static int getHostPort(InetSocketAddress address) {
        return address.getPort();
    }
}
