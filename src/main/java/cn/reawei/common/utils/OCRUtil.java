package cn.reawei.common.utils;

import cn.reawei.common.enums.OCRErrorCode;
import cn.reawei.common.vo.ResultBean;
import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;

import static cn.reawei.common.enums.BaiDu.BAI_DU_COR_API_KEY;
import static cn.reawei.common.enums.BaiDu.BAI_DU_COR_APP_ID;
import static cn.reawei.common.enums.BaiDu.BAI_DU_COR_SECRET_KEY;

/**
 * 百度图像识别
 */
public class OCRUtil {

    private static AipOcr client = new AipOcr(BAI_DU_COR_APP_ID.key, BAI_DU_COR_API_KEY.key, BAI_DU_COR_SECRET_KEY.key);

    /**
     * @param imgPath 本地图片路径
     */
    public static ResultBean getBankNumber(String imgPath) {
        // 调用接口
        JSONObject response = client.bankcard(imgPath);
        return getResultBeanByBankCard(response);
    }

    /**
     * @param imgPath 本地图片路径
     */
    public static ResultBean getBankNumber(String imgPath, AipOcr client) {
        // 调用接口
        JSONObject response = client.bankcard(imgPath);
        return getResultBeanByBankCard(response);
    }

    /**
     * @param imgUrl 网络图片路径
     */
    public static ResultBean getBankNumberByImageUrl(String imgUrl) {
        // 调用接口
        byte[] file = ImageUtil.getImageFromNetByUrl(imgUrl);
        JSONObject response = new JSONObject();
        if (Objects.nonNull(file)) {
            response = client.bankcard(file);
        }
        return getResultBeanByBankCard(response);
    }

    /**
     * @param imgUrl 网络图片路径
     */
    public static ResultBean getBankNumberByImageUrl(String imgUrl, AipOcr client) {
        // 调用接口
        byte[] file = ImageUtil.getImageFromNetByUrl(imgUrl);
        JSONObject response = new JSONObject();
        if (Objects.nonNull(file)) {
            response = client.bankcard(file);
        }
        return getResultBeanByBankCard(response);
    }

    /**
     * @param inStream 输入流
     */
    public static ResultBean getBankNumberBySteam(InputStream inStream) throws Exception {
        // 调用接口
        byte[] file = ImageUtil.readInputStream(inStream);
        JSONObject response = client.bankcard(file);
        return getResultBeanByBankCard(response);
    }

    /**
     * @param inStream 输入流
     */
    public static ResultBean getBankNumberBySteam(InputStream inStream, AipOcr client) throws Exception {
        // 调用接口
        byte[] file = ImageUtil.readInputStream(inStream);
        JSONObject response = client.bankcard(file);
        return getResultBeanByBankCard(response);
    }

    /**
     * @param path    本地图片
     * @param isFront 是否正面
     */
    public static ResultBean getIDCard(String path, boolean isFront) {
        // 调用接口
        byte[] file = ImageUtil.readImageFile(path);
        HashMap<String, String> options = new HashMap<>();
        JSONObject response = client.idcard(file, isFront, options);
        return getResultBeanByIDCard(isFront, response);
    }

    /**
     * @param path    本地图片
     * @param isFront 是否正面
     */
    public static ResultBean getIDCard(String path, boolean isFront, AipOcr client) {
        // 调用接口
        byte[] file = ImageUtil.readImageFile(path);
        HashMap<String, String> options = new HashMap<>();
        JSONObject response = client.idcard(file, isFront, options);
        return getResultBeanByIDCard(isFront, response);
    }


    /**
     * @param imgUrl  网络图片
     * @param isFront 是否正面
     */
    public static ResultBean getIDCardByImageUrl(String imgUrl, boolean isFront) {
        // 调用接口
        HashMap<String, String> options = new HashMap<>();
        byte[] file = ImageUtil.getImageFromNetByUrl(imgUrl);
        JSONObject response = new JSONObject();
        if (Objects.nonNull(file)) {
            response = client.idcard(file, isFront, options);
        }
        return getResultBeanByIDCard(isFront, response);
    }

    /**
     * @param imgUrl  网络图片
     * @param isFront 是否正面
     */
    public static ResultBean getIDCardByImageUrl(String imgUrl, boolean isFront, AipOcr client) {
        // 调用接口
        byte[] file = ImageUtil.getImageFromNetByUrl(imgUrl);
        HashMap<String, String> options = new HashMap<>();
        JSONObject response = new JSONObject();
        if (Objects.nonNull(file)) {
            response = client.idcard(file, isFront, options);
        }
        return getResultBeanByIDCard(isFront, response);
    }

    /**
     * @param inStream 输入流
     * @param isFront  是否正面
     */
    public static ResultBean getIDCardByStream(InputStream inStream, boolean isFront) throws Exception {
        // 调用接口
        byte[] file = ImageUtil.readInputStream(inStream);
        HashMap<String, String> options = new HashMap<>();
        JSONObject response = client.idcard(file, isFront, options);
        return getResultBeanByIDCard(isFront, response);
    }

    /**
     * @param inStream 输入流
     * @param isFront  是否正面
     */
    public static ResultBean getIDCardByStream(InputStream inStream, boolean isFront, AipOcr client) throws Exception {
        // 调用接口
        byte[] file = ImageUtil.readInputStream(inStream);
        HashMap<String, String> options = new HashMap<>();
        JSONObject response = client.idcard(file, isFront, options);
        return getResultBeanByIDCard(isFront, response);
    }

    /**
     * 从JSON字符串中获取身份证数据
     *
     * @param isFront  正反面
     * @param response JSON字符串
     */
    private static ResultBean getResultBeanByIDCard(boolean isFront, JSONObject response) {
        if (response.isNull("image_status")) {
            return new ResultBean<>(OCRErrorCode.getMessage(response.getInt("error_code")));
        }
        switch (response.getString("image_status")) {
            case "normal":
                JSONObject result = response.getJSONObject("words_result");
                if (isFront) {
                    JSONObject data = new JSONObject();
                    if (!result.isNull("姓名")) {
                        data.put("name", result.getJSONObject("姓名").getString("words"));
                    }
                    if (!result.isNull("民族")) {
                        data.put("peoples", result.getJSONObject("民族").getString("words"));
                    }
                    if (!result.isNull("住址")) {
                        data.put("address", result.getJSONObject("住址").getString("words"));
                    }
                    if (!result.isNull("公民身份号码")) {
                        data.put("idNumber", result.getJSONObject("公民身份号码").getString("words"));
                    }
                    if (!result.isNull("出生")) {
                        data.put("birthday", result.getJSONObject("出生").getString("words"));
                    }
                    if (!result.isNull("性别")) {
                        data.put("sex", result.getJSONObject("性别").getString("words"));
                    }
                    return new ResultBean<>(data);
                } else {
                    JSONObject data = new JSONObject();
                    if (!result.isNull("失效日期")) {
                        data.put("endTime", result.getJSONObject("失效日期").getString("words"));
                    }
                    if (!result.isNull("签发机关")) {
                        data.put("issuing", result.getJSONObject("签发机关").getString("words"));
                    }
                    if (!result.isNull("签发日期")) {
                        data.put("startTime", result.getJSONObject("签发日期").getString("words"));
                    }
                    return new ResultBean<>(data);
                }
            case "reversed_side":
                return new ResultBean<>("未摆正身份证");
            case "non_idcard":
                return new ResultBean<>("上传的图片中不包含身份证");
            case "blurred":
                return new ResultBean<>("身份证模糊");
            case "over_exposure":
                return new ResultBean<>("身份证关键字段反光或过曝");
            case "unknown":
                return new ResultBean<>("未知状态");
        }
        return new ResultBean<>("识别异常");
    }

    /**
     * JSON字符串获取银行卡信息
     * @param response json字符串
     */
    private static ResultBean getResultBeanByBankCard(JSONObject response) {
        if (response.isNull("result")) {
            return new ResultBean<>(OCRErrorCode.getMessage(response.getInt("error_code")));
        }
        JSONObject result = response.getJSONObject("result");
        JSONObject data = new JSONObject();
        if (!result.isNull("bank_card_number")) {
            data.put("number", result.getString("bank_card_number").replaceAll(" ", ""));
        }
        if (!result.isNull("bank_card_type")) {
            data.put("type", result.getInt("bank_card_type"));
        }
        if (!result.isNull("bank_name")) {
            data.put("name", result.getString("bank_name"));
        }
        return new ResultBean<>(data);
    }

    public static void main(String[] args) {
//        String path = "/Users/xingwu/Downloads/bankCard.jpg";
        AipOcr client = new AipOcr(BAI_DU_COR_APP_ID.key, BAI_DU_COR_API_KEY.key, BAI_DU_COR_SECRET_KEY.key);
//        String path = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513836045361&di=31f5c0f4ac5631712eea1637fc0b1948&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3D6665b20d0a24ab18e043e93300cacafb%2F3b292df5e0fe992588323e7b37a85edf8db171da.jpg";
        String path = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513846741878&di=8d06c6855d73ba0f77f5d07f820c722d&imgtype=0&src=http%3A%2F%2Fwww.yktchina.com%2FFileUpLoad%2F2009%2Fb2d696c0b1b74521b1af0275cd286121.jpg";
        System.out.println(getBankNumberByImageUrl(path).getMessage());
//        String urlPath = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513837657145&di=2fd4fb799bb6fba839861ac288e89fa5&imgtype=0&src=http%3A%2F%2Fimg.chinawutong.com%2Fhuiyuan%2Fuppic%2Fs_636054904236200186.png";

//        System.out.println(getIDCard(path, true));
    }

}
