package com.sparkfire.squirmulu.exception;

/**
 * 枚举了一些常用API操作码
 */
public enum ErrorCode implements IErrorCode
{
    SYSTEM_EXCEPTION(-1, "服务器开小差了..请稍后再试"),

    ALREADY_EXIST(1001, "[%s]已存在"),
    NOT_EXIST(1002, "[%s]不存在"),
    INSERT_ERROR(1003, "新增失败[%s]"),
    UPDATE_ERROR(1004, " [%s]"),
    DELETE_ERROR(1005, "删除失败[%s]"),

    INVALID_PARAMETERS_EMPT(1006, "[%s]不能为空"),
    INVALID_PARAMETERS_ERROR(1007, "[%s]参数有误"),
    UPLOAD_ERROR(1008, "文件上传失败"),
    METHOD_ERROR(1009, "请求方法类型错误，可以使用' %s '等类型"),

    EXCLE_EXPORT_ERROR(1010,"[%s]下载失败"),

    AUTH_ERROR(401, "令牌验证失败");

    private long code;
    private String message;

    private ErrorCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public Long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
