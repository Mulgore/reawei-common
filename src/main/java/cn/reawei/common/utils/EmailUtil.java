package cn.reawei.common.utils;

import com.alibaba.fastjson.JSONObject;
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
        webClient.getOptions().setRedirectEnabled(true);

        HtmlPage page = webClient.getPage("http://mail.sina.cn/?vt=4");
        webClient.waitForBackgroundJavaScript(2000);
        // 方法1：
//        HtmlForm htmlForm = page.getForms().get(0);
//        //登录
//        HtmlInput ln = htmlForm.getInputByName("username");
//        HtmlInput pwd = htmlForm.getInputByName("passwd");
//        ln.setAttribute("value", username);
//        pwd.setAttribute("value", password);
//        HtmlInput btn = htmlForm.getInputByValue("登 录");
//        btn.click();
        // 方法2：
        String hrefValue = "javascript:window.sinaSSOController.login(" + "\"" + username + "\",\"" + password + "\"," + 0 + ");";
        page.executeJavaScript(hrefValue);//执行js方法
//        HtmlPage hpm = (HtmlPage) s.getNewPage();//获得执行后的新page对象
        //登录完成，现在可以爬取任意你想要的页面了。
        System.out.println("Cookies : " + webClient.getCookieManager().getCookies().toString());
        webClient.close();
    }

    public static void oneSixThree(String email, String password) throws FailingHttpStatusCodeException, IOException {
        webClient.getOptions().setJavaScriptEnabled(true);    //默认执行js，如果不执行js，则可能会登录失败，因为用户名密码框需要js来绘制。
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; 1503-M02 Build/MRA58K) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/37.0.0.0 Mobile MQQBrowser/6.2 TBS/036558 Safari/537.36 MicroMessenger/6.3.25.861 NetType/WIFI Language/zh_CN");
        //获取页面
        HtmlPage page = webClient.getPage("http://mail.163.com/");
        webClient.waitForBackgroundJavaScript(10000);
        HtmlElement iframe = page.getHtmlElementById("x-URS-iframe");//切换到iframe
        webClient.waitForBackgroundJavaScript(10000);
        String src = iframe.getAttribute("src");
        HtmlPage ifrpage = webClient.getPage(src);//读取iframe网页
        webClient.waitForBackgroundJavaScript(20000);
        List<HtmlForm> form = ifrpage.getForms();
        HtmlElement divBox = ifrpage.getHtmlElementById("cnt-box-parent");
        DomNodeList<HtmlElement> domNodeList = divBox.getElementsByTagName("input");
//        HtmlTextInput username = aa.getChildElements("email");
//        HtmlTextInput passwd = aa.getInputByName("password");
//        username.setValueAttribute(email);
//        passwd.setValueAttribute("password");
//        HtmlButton button = page.getHtmlElementById("dologin");
//        button.click();
        //获取cookie
        System.out.println("Cookies : " + webClient.getCookieManager().getCookies().toString());
        //关闭webclient
        webClient.close();
    }

    public static void qq(String email, String password, String pw) throws FailingHttpStatusCodeException, IOException, InterruptedException {
        webClient.getOptions().setJavaScriptEnabled(true);    //默认执行js，如果不执行js，则可能会登录失败，因为用户名密码框需要js来绘制。
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setRedirectEnabled(true);
        //获取页面
        HtmlPage page = webClient.getPage("https://w.mail.qq.com/cgi-bin/loginpage?f=xhtml&kvclick=loginpage|app_push|enter|ios&ad=false&f=xhtml");
        webClient.waitForBackgroundJavaScript(2000);
        // 根据form的名字获取页面表单，也可以通过索引来获取：page.getForms().get(0)
        HtmlInput username = page.getHtmlElementById("u");
        HtmlInput pwd = page.getHtmlElementById("p");
        username.setAttribute("value", email);
        pwd.setAttribute("value", password);
        HtmlPage returnPage = page.getHtmlElementById("go").click();
        webClient.waitForBackgroundJavaScript(2000);
        System.out.println(returnPage.asXml());
        if (Objects.nonNull(pw)) {
            HtmlPage retPage = webClient.getPage("https://w.mail.qq.com/cgi-bin/loginpage?ad=false&f=xhtml&aliastype=&s=&errtype=4&clientuin=" + email.substring(0, email.indexOf("@")) + "&verify=&aliastype=&fun=&g_key=&msg=&ss=&autologin=n&plain=&spcache=2e0d5c2ac03eaecMTUxNDMwMTU2OA&target=&3g_sid=&vt=passport");
            retPage.getHtmlElementById("pwd").setAttribute("value", pw);
            retPage.getHtmlElementById("submitBtn").click();
        }
        //获取cookie
        Set<Cookie> cookies = webClient.getCookieManager().getCookies();
        System.out.println(cookies);
        //关闭webclient
        webClient.close();
    }

    public static void zhongxin(String idNumber, String password) throws FailingHttpStatusCodeException, IOException, InterruptedException {
        webClient.getOptions().setJavaScriptEnabled(true);    //默认执行js，如果不执行js，则可能会登录失败，因为用户名密码框需要js来绘制。
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setRedirectEnabled(true);
        //获取页面
        HtmlPage page = webClient.getPage("https://uc.creditcard.ecitic.com/citiccard/newucwap/login/wxlogin.html");
        webClient.waitForBackgroundJavaScript(2000);
        // 根据form的名字获取页面表单，也可以通过索引来获取：page.getForms().get(0)
        HtmlForm form = page.getFormByName("idformInput");
        HtmlInput idNum = form.getInputByName("idNbr1");
        HtmlInput userpass = form.getInputByName("userpass1");
        HtmlInput code = form.getInputByName("verfiry_code1");
        DomNodeList<HtmlElement> dom = form.getElementsByTagName("a");
        dom.get(2).click();
        //获取cookie
        Set<Cookie> cookies = webClient.getCookieManager().getCookies();
        System.out.println(cookies);
        //关闭webclient
        webClient.close();
    }


    public static void nongye(String idNumber, String password) throws FailingHttpStatusCodeException, IOException, InterruptedException {
        webClient.getOptions().setJavaScriptEnabled(true);    //默认执行js，如果不执行js，则可能会登录失败，因为用户名密码框需要js来绘制。
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setRedirectEnabled(true);

        //获取页面
        HtmlPage page = webClient.getPage("https://uc.creditcard.ecitic.com/citiccard/newucwap/login/wxlogin.html");
        webClient.waitForBackgroundJavaScript(2000);
        // 根据form的名字获取页面表单，也可以通过索引来获取：page.getForms().get(0)
        HtmlForm form = page.getFormByName("idformInput");
        HtmlInput idNum = form.getInputByName("idNbr1");
        HtmlInput userpass = form.getInputByName("userpass1");
        HtmlInput code = form.getInputByName("verfiry_code1");
        DomNodeList<HtmlElement> dom = form.getElementsByTagName("a");
        dom.get(2).click();
        //获取cookie
        Set<Cookie> cookies = webClient.getCookieManager().getCookies();
        System.out.println(cookies);
        //关闭webclient
        webClient.close();
    }

    public static void icbc(String idNumber, String password) throws FailingHttpStatusCodeException, IOException, InterruptedException {
        webClient.getOptions().setJavaScriptEnabled(true);    //默认执行js，如果不执行js，则可能会登录失败，因为用户名密码框需要js来绘制。
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_2_6 like Mac OS X) AppleWebKit/604.5.6 (KHTML, like Gecko) Mobile/15D100 MicroMessenger/6.6.5 NetType/WIFI Language/zh_CN");
        //获取页面
        String url = "https://smsb.icbc.com.cn/ICBCSMSBank/sms/servlet/MenuServlet?whichProcess=subMenu&subMenuId=QueryBillList&submenuType=webview&sms_channel=6&t=" + new Date().getTime();
        HtmlPage page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(2000);
        // 根据form的名字获取页面表单，也可以通过索引来获取：page.getForms().get(0)

//        HtmlForm form = page.getFormByName("idformInput");
//        HtmlInput idNum = form.getInputByName("idNbr1");
//        HtmlInput userpass = form.getInputByName("userpass1");
//        HtmlInput code = form.getInputByName("verfiry_code1");
//        DomNodeList<HtmlElement> dom = form.getElementsByTagName("a");
//        dom.get(2).click();
        //获取cookie
        Set<Cookie> cookies = webClient.getCookieManager().getCookies();
        System.out.println(cookies);
        //关闭webclient
        webClient.close();
    }

    public static void main(String[] args) {
        try {
//            zhongxin("360428199301074312", "258079");
            icbc("360428199301074312", "258079");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
