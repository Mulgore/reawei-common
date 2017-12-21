package cn.reawei.common.utils;

import com.baidu.aip.imageclassify.AipImageClassify;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import static cn.reawei.common.enums.BaiDu.BAI_DU_COR_API_KEY;
import static cn.reawei.common.enums.BaiDu.BAI_DU_COR_APP_ID;
import static cn.reawei.common.enums.BaiDu.BAI_DU_COR_SECRET_KEY;

/**
 * 百度图像分类
 */
public class ImageClassifyUtil {

    private static AipImageClassify client = new AipImageClassify(BAI_DU_COR_APP_ID.key, BAI_DU_COR_API_KEY.key, BAI_DU_COR_SECRET_KEY.key);

    /**
     * 动物
     *
     * @param imgUrl 网络图片
     */
    public static String getAnimalImageUrl(String imgUrl, Integer topNum) {
        // 调用接口
        byte[] file = ImageUtil.getImageFromNetByUrl(imgUrl);
        HashMap<String, String> options = new HashMap<>();
        options.put("top_num", topNum.toString());
        JSONObject response = new JSONObject();
        if (Objects.nonNull(file)) {
            response = client.animalDetect(file, options);
        }
        return response.toString();
    }

    /**
     * 动物
     *
     * @param path 本地图片
     */
    public static String getAnimal(String path, Integer topNum) {
        // 调用接口
        byte[] file = ImageUtil.readImageFile(path);
        HashMap<String, String> options = new HashMap<>();
        options.put("top_num", topNum.toString());
        JSONObject response = new JSONObject();
        if (Objects.nonNull(file)) {
            response = client.animalDetect(file, options);
        }
        return response.toString();
    }
}
