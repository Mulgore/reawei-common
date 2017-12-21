package cn.reawei.common.enums;

import java.util.Arrays;
import java.util.Objects;

/**
 * OCR错误码
 */
public enum OCRErrorCode {

    UNKNOWN_ERROR(1, "服务器内部错误，请再次请求"),
    SERVICE_UNAVAILABLE(2, "服务暂不可用，请再次请求"),
    API_NOT_METHOD(3, "调用的API不存在"),
    API_REQUEST_LIMIT(4, "每天流量超限额"),
    API_DAILY_REQUEST_LIMIT(17, "每天流量超限额"),
    API_QPS_REQUEST_LIMIT(18, "QPS超限额"),
    API_TOTAL_REQUEST_LIMIT(19, "请求总量超限额"),
    INVALID_PARAMETER(100, "无效参数"),
    TOKEN_INVALID(110, "Access Token失效"),
    TOKEN_EXPIRED(111, "Access token过期"),
    INTERNAL_ERROR(282000, "服务器内部错误，请再次请求"),
    INTERNAL_PARAMETER(216100, "请求中包含非法参数"),
    NOT_ENOUGH_PARAM(216101, "缺少必须的参数"),
    SERVICE_NOT_SUPPORT(216102, "请求了不支持的服务"),
    PARAM_TOO_LONG(216103, "请求中某些参数过长"),
    APP_ID_NOT_EXIST(216110, "appId不存在"),
    EMPTY_IMAGE(216200, "图片为空"),
    IMAGE_FORMAT_ERROR(216201, "图片格式错误"),
    IMAGE_SIZE_ERROR(216202, "图片大小错误"),
    RECOGNIZE_ERROR(216630, "识别错误"),
    RECOGNIZE_BANK_CARD_ERROR(216631, "识别银行卡错误"),
    RECOGNIZE_ID_CARD_ERROR(216633, "识别银行卡错误"),
    DETECT_ERROR(216634, "检测错误"),
    MISSING_PARAM(282003, "请求参数缺失"),
    BATCH_PROCESSING_ERROR(282005, "处理批量任务时发生部分或全部错误"),
    BATCH_TASK_LIMIT(282006, "批量任务处理数量超出限制"),
    URL_NOT_EXIT(282110, "URL参数不存在"),
    URL_FORMAT_ERROR(282111, "URL格式非法"),
    URL_DOWNLOAD_TIMEOUT(282112, "url下载超时"),
    URL_RESPONSE_INVALID(282113, "URL返回无效参数"),
    URL_SIZE_ERROR(282114, "URL长度超过1024字节或为0"),
    REQUEST_ID_NOT_EXIT(282808, "request ID 不存在"),
    RESULT_TYPE_ERROR(282809, "返回结果请求错误"),
    IMAGE_RECOGNIZE_ERROR(282810, "图像识别错误");

    public final Integer code;
    public final String message;

    OCRErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(Integer code) {
        return Arrays.stream(values()).anyMatch(item -> Objects.equals(code, item.code)) ? Arrays.stream(values()).filter(item -> Objects.equals(code, item.code)).findFirst().get().message : null;
    }
}
