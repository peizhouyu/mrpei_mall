package cn.mrpei.manager.service;

import cn.mrpei.common.pojo.EUTreeNode;
import cn.mrpei.common.pojo.MallResult;

import java.util.List;

/**`
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018 /5/16
 * @last-modified ：
 * @class cn.mrpei.manager.service
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
public interface ContentCategoryService {
    /**
     * Gets category list.
     *
     * @param parentId the parent id
     * @return the category list
     */
    List<EUTreeNode> getCategoryList(long parentId);

    /**
     * Insert content category mall result.
     *
     * @param parentId the parent id
     * @param name     the name
     * @return the mall result
     */
    MallResult insertContentCategory(long parentId, String name);
}
