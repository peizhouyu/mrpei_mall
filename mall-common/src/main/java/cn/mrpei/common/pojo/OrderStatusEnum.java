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
public enum OrderStatusEnum {

    CANCELED(0,"已取消"),
    NO_PAY(10,"未支付"),
    PAID(20,"已付款"),
    SHIPPED(40,"已发货"),
    ORDER_SUCCESS(50,"订单完成"),
    ORDER_CLOSE(60,"订单关闭");

    OrderStatusEnum(int code, String value){
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

    public static OrderStatusEnum codeOf(int code){
        for (OrderStatusEnum orderStatusEnum : values()){
            if (orderStatusEnum.getCode() == code){
                return orderStatusEnum;
            }
        }
        throw new RuntimeException("没有找到对应的枚举");
    }
}
