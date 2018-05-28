package cn.mrpei.common.dto;

import java.io.Serializable;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/22
 * @last-modified ：
 * @class cn.mrpei.common.dto
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
public class EsCount implements Serializable {
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
