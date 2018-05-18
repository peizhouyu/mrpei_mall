package cn.mrpei.common.pojo;

import java.io.Serializable;
import java.util.List;
/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/16
 * @last-modified ：
 * @class cn.mrpei.common.jedis
 * @copyright Copyright ? 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
public class EUDataGridResult implements Serializable {

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
	
	
}
