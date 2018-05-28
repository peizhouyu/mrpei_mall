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
public class EsInfo implements Serializable {
    private String cluster_name;

    private String status;

    private String number_of_nodes;

    private Integer count;

    public String getCluster_name() {
        return cluster_name;
    }

    public void setCluster_name(String cluster_name) {
        this.cluster_name = cluster_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumber_of_nodes() {
        return number_of_nodes;
    }

    public void setNumber_of_nodes(String number_of_nodes) {
        this.number_of_nodes = number_of_nodes;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
