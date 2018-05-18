package cn.mrpei.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/18
 * @last-modified ：
 * @class cn.mrpei.common.pojo
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
public class EUDResult implements Serializable {

    private long total;
    private List<?> rows;
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public List<?> getRows() {
        return rows;
    }
    public void setRows(List<?> rows) {
        this.rows = rows;
    }
    public EUDResult(long total, List<?> rows) {
        super();
        this.total = total;
        this.rows = rows;
    }
    public EUDResult() {
        super();
    }
}
