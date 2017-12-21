package cn.reawei.common.enums;


/**
 * 百度地图AK
 */
public enum BaiDu {
    // "百度地图Ak"
    BAI_DU_AK("R62KkaKOTkOHC93qDWipbg0sON1Pwvc6"),
    BAI_DU_OCR_APP_ID("10563433"),
    BAI_DU_OCR_API_KEY("Q89gMZL9LGyg1DAN7eMBLLhf"),
    BAI_DU_OCR_SECRET_KEY("AGxepbOXYWE4W93GNIP8IHghG1hHC1K6");

    public final String key;

    BaiDu(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
