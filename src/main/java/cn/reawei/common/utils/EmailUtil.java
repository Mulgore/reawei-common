package cn.reawei.common.utils;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.util.Cookie;

import java.io.IOException;
import java.util.*;

public class EmailUtil {

    //创建一个webclient
    private static WebClient webClient = new WebClient(BrowserVersion.CHROME);


    public static void sinaLogin(String username, String password) throws FailingHttpStatusCodeException, IOException, InterruptedException {
        webClient.getOptions().setJavaScriptEnabled(true);    //默认执行js，如果不执行js，则可能会登录失败，因为用户名密码框需要js来绘制。
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getCookieManager().setCookiesEnabled(true);

        HtmlPage page = webClient.getPage("http://mail.sina.cn/?vt=4");
        HtmlForm htmlForm = page.getForms().get(0);
        System.out.println(page.asXml());
        //登录
        HtmlInput ln = htmlForm.getInputByName("username");
        HtmlInput pwd = htmlForm.getInputByName("passwd");
        ln.setAttribute("value", username);
        pwd.setAttribute("value", password);
        HtmlInput btn = htmlForm.getInputByValue("登 录");
        page = btn.click();
        //登录完成，现在可以爬取任意你想要的页面了。
        System.out.println(page.asXml());
        System.out.println("Cookies : " + webClient.getCookieManager().getCookies().toString());
        webClient.close();
    }

    public static void oneSixThree(String email, String password) throws FailingHttpStatusCodeException, IOException, InterruptedException {
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setUseInsecureSSL(true);//接受任何主机连接 无论是否有有效证书
        webClient.getOptions().setJavaScriptEnabled(true);//设置支持javascript脚本
        webClient.getOptions().setCssEnabled(false);//禁用css支持
        webClient.getOptions().setThrowExceptionOnScriptError(false);//js运行错误时不抛出异常
        webClient.getOptions().setTimeout(100000);//设置连接超时时间
        webClient.getOptions().setDoNotTrackEnabled(false);
        //获取页面
        HtmlPage page = webClient.getPage("http://url.cn/51bS1a0");
        System.out.println(page.asXml());
        // 根据form的名字获取页面表单，也可以通过索引来获取：page.getForms().get(0)
//        HtmlForm form = page.getFormByName("login163");
//        HtmlTextInput username = form.getInputByName("username");
//        HtmlPasswordInput pwd = form.getInputByName("password");
//        username.setValueAttribute(email);
//        pwd.setValueAttribute(password);
//        HtmlButton button = page.getHtmlElementById("loginBtn");
//        HtmlPage retPage = button.click();
        //获取cookie
        Set<Cookie> cookies = webClient.getCookieManager().getCookies();
        //关闭webclient
        webClient.close();
    }

    public static void qq(String email, String password) throws FailingHttpStatusCodeException, IOException, InterruptedException {
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setUseInsecureSSL(true);//接受任何主机连接 无论是否有有效证书
        webClient.getOptions().setJavaScriptEnabled(true);//设置支持javascript脚本
        webClient.getOptions().setCssEnabled(false);//禁用css支持
        webClient.getOptions().setThrowExceptionOnScriptError(false);//js运行错误时不抛出异常
        webClient.getOptions().setTimeout(100000);//设置连接超时时间
        webClient.getOptions().setDoNotTrackEnabled(false);
        //获取页面
        HtmlPage page = webClient.getPage("https://ui.ptlogin2.qq.com/cgi-bin/login?style=9&appid=522005705&daid=4&s_url=https%3A%2F%2Fw.mail.qq.com%2Fcgi-bin%2Flogin%3Fvt%3Dpassport%26vm%3Dwsk%26delegate_url%3D%26f%3Dxhtml%26target%3D&hln_css=http%3A%2F%2Fmail.qq.com%2Fzh_CN%2Fhtmledition%2Fimages%2Flogo%2Fqqmail%2Fqqmail_logo_default_200h.png&low_login=1&hln_autologin=%E8%AE%B0%E4%BD%8F%E7%99%BB%E5%BD%95%E7%8A%B6%E6%80%81&pt_no_onekey=1");
        // 根据form的名字获取页面表单，也可以通过索引来获取：page.getForms().get(0)
        HtmlTextInput username = page.getHtmlElementById("u");
        HtmlPasswordInput pwd = page.getHtmlElementById("p");
        username.setValueAttribute(email);
        pwd.setValueAttribute(password);
        page.getHtmlElementById("go").click();
        //获取cookie
        Set<Cookie> cookies = webClient.getCookieManager().getCookies();
        System.out.println(cookies);
        //关闭webclient
        webClient.close();
    }


}
