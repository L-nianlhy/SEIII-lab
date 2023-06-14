package com.example.collect.util;

/**
 *  常量
 */

public class Constant {
    // post操作成功返回码
    public static final Integer REQUEST_SUCCESS = 1;

    // post操作失败返回码
    public static final Integer REQUEST_FAIL = 0;

    // 文件未上传
    public static final String FILE_NOT_UPLOAD = "NOT_UPLOAD";

    // 无关字段的默认值
    public static final String DEFAULT_VALUE = "NONE";

    // 等级评价
    public static final String[] LEVEL = new String[]{"least","less","normal","more","most"};

    // 活跃度评分标准
    public static final int[] activity = new int[]{0, 1, 3, 6, 9};

    // 报告协作能力评分标准
    public static final int[] cooperation = new int[]{0, 1, 3, 6, 9};

    // 报告审查能力评分标准
    public static final int[] censor = new int[]{0, 1, 5, 10, 15};

    // 报告查重评分标准
    public static final int[] dup = new int[]{0, 1, 3, 6, 9};
}
