package cn.reawei.common.enums;


/**
 * 百度地图AK
 */
public enum BaiDu {
    // "百度地图Ak"
    BAI_DU_AK2("R62KkaKOTkOHC93qDWipbg0sON1Pwvc6");

    private String ak;

    BaiDu(String ak) {
        this.ak = ak;
    }

    public static String getAk(BaiDu item) {
        return item.ak;
    }

    public String getAk() {
        return ak;
    }
}
