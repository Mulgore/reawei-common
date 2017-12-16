package cn.reawei.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class CookieUtil {
    private final static int COOKIE_AGE_DEFAULT = 864000 * 365;
    private final static String DOMAIN = "";

    /**
     * 设置一个Cookie，自定义Cookie时间，如果Cookie存在会进行覆盖
     */
    public static void setCookie(HttpServletResponse response, String cookieName, String value, int cookieTime) {
        add(response, cookieName, value, cookieTime);
    }

    /**
     * 增加一个Cookie, 自定义Cookie时间，如果Cookie存在会反回失败
     */
    public static boolean addCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String value, int cookieTime) {
        if (Objects.isNull(getCookie(request, cookieName))) {
            return add(response, cookieName, value, cookieTime);
        }
        return false;
    }

    /**
     * 设置一个Cookie，默认永久Cookie，如果Cookie存在会进行覆盖
     */
    public static void setCookie(HttpServletResponse response, String cookieName, String value) {
        add(response, cookieName, value, null);
    }

    /**
     * 增加一个Cookie，默认永久Cookie，如果Cookie存在会反回失败
     */
    public static boolean addCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String value) {
        if (Objects.isNull(getCookie(request, cookieName))) {
            return add(response, cookieName, value, null);
        }
        return false;
    }

    private static boolean add(HttpServletResponse response, String cookieName, String value, Integer cookieTime) {
        Cookie cookies = new Cookie(cookieName, value);
        cookies.setPath("/");
        cookies.setMaxAge(COOKIE_AGE_DEFAULT);
        if (Objects.nonNull(cookieTime)) {
            cookies.setMaxAge(cookieTime);
        }
        cookies.setDomain(DOMAIN);
        response.addCookie(cookies);
        return true;
    }

    /**
     * 移除一个Cookie
     */
    public static boolean removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        if (StringUtil.isNotEmpty(cookieName)) {
            Cookie cookie = getCookie(request, cookieName);
            if (Objects.nonNull(cookie)) {
                cookie.setPath("/");// 不要漏掉
                cookie.setMaxAge(0);// 如果0，就说明立即删除
                cookie.setDomain(DOMAIN);
                response.addCookie(cookie);
                return true;
            }
        }
        return false;
    }

    /**
     * 获得一个Cookie
     */
    public static Cookie getCookie(HttpServletRequest request, final String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                if (c.getName().equalsIgnoreCase(cookieName)) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * 检测该用户是否有uid
     */
    public static boolean checkUID(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("uid".equals(cookie.getName())) {
                    String value = cookie.getValue();
                    return (value != null && !value.isEmpty());
                }
            }
        }
        return false;
    }

    public static String get(HttpServletRequest request, String k) {
        // 从cookie中取
        Cookie cookie = getCookie(request, k);
        return (null != cookie) ? cookie.getValue() : null;
    }
}
