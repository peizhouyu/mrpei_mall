package cn.mrpei.common.pojo;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/21
 * @last-modified ：
 * @class cn.mrpei.common.pojo
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
public enum FrontStatusEnum {

    NO_LOGIN(0,"未登录，需要转到登录页！");

    FrontStatusEnum(int code, String value){
        this.code = code;
        this.value = value;
    }
    private String value;
    private int code;

    public String getValue() {
        return value;
    }

    public int getCode() {
        return code;
    }

    public static FrontStatusEnum codeOf(int code){
        for (FrontStatusEnum orderStatusEnum : values()){
            if (orderStatusEnum.getCode() == code){
                return orderStatusEnum;
            }
        }
        throw new RuntimeException("没有找到对应的枚举");
    }
}
