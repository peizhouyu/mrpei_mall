package cn.mrpei.manager.service.impl;

import cn.mrpei.common.pojo.EUDataGridResult;
import cn.mrpei.common.pojo.MallResult;
import cn.mrpei.common.utils.ExceptionUtil;
import cn.mrpei.common.utils.IDUtils;
import cn.mrpei.manager.dao.TbItemDescMapper;
import cn.mrpei.manager.dao.TbItemMapper;
import cn.mrpei.manager.dao.TbItemParamItemMapper;
import cn.mrpei.manager.pojo.*;
import cn.mrpei.manager.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/16
 * @last-modified ：
 * @class cn.mrpei.manager.service.impl
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public TbItem getItemById(long itemId) {

        //TbItem item = itemMapper.selectByPrimaryKey(itemId);
        //添加查询条件
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> list = itemMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            TbItem item = list.get(0);
            return item;
        }
        return null;
    }

    /**
     * 商品列表查询
     * <p>Title: getItemList</p>
     * <p>Description: </p>
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EUDataGridResult getItemList(int page, int rows) {
        //查询商品列表
        TbItemExample example = new TbItemExample();
        //分页处理
        PageHelper.startPage(page, rows);
        List<TbItem> list = itemMapper.selectByExample(example);
        //创建一个返回值对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        //取记录总条数
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public MallResult createItem(TbItem item, String desc, String itemParam) throws Exception {
        //item补全
        //生成商品ID
        Long itemId = IDUtils.genItemId();
        item.setId(itemId);
        // '商品状态，1-正常，2-下架，3-删除',
        item.setStatus(1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //插入到数据库
        itemMapper.insert(item);
        //添加商品描述信息
        MallResult result = insertItemDesc(itemId, desc);
        if (result.getStatus() != 200) {
            throw new Exception();
        }
        //添加规格参数
        result = insertItemParamItem(itemId, itemParam);
        if (result.getStatus() != 200) {
            throw new Exception();
        }
        return MallResult.ok();
    }

    @Override
    public MallResult deleteItem(String ids) {
        try {
            String[] idsArray = ids.split(",");
            List<Long> values = new ArrayList<Long>();
            for(String id : idsArray) {
                values.add(Long.parseLong(id));
            }
            TbItemExample e = new TbItemExample();
            TbItemExample.Criteria c = e.createCriteria();
            c.andIdIn(values);

            List<TbItem> list = itemMapper.selectByExample(e);
            if(list!=null && list.size()>0){
                TbItem item=list.get(0);
                item.setStatus(3);
                itemMapper.updateByExample(item, e);
            }
            return MallResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TbItemDesc listItemDesc(Long id) {
        return itemDescMapper.selectByPrimaryKey(id);
    }

    @Override
    public MallResult updateItem(TbItem item, TbItemDesc desc, TbItemParamItem itemParamss) {
        try {
            //更新商品
            TbItemExample e = new TbItemExample();
            TbItemExample.Criteria c = e.createCriteria();
            c.andIdEqualTo(item.getId());

            TbItem tbItem = itemMapper.selectByPrimaryKey(item.getId());
            item.setCreated(tbItem.getCreated());
            item.setUpdated(new Date());
            item.setStatus(1);
            itemMapper.updateByExample(item, e);

            //更新商品描述
            TbItemDescExample de = new TbItemDescExample();
            TbItemDescExample.Criteria criteria = de.createCriteria();
            criteria.andItemIdEqualTo(item.getId());
            desc.setItemId(item.getId());

            desc.setCreated(tbItem.getCreated());
            desc.setUpdated(new Date());
            itemDescMapper.updateByExample(desc, de);

            //更新规格
			/*TaotaoResult result=updateItemParamItem(itemId, itemparam);
			if(result.getStatus()!=200){
				throw new Exception();
			}
			return TaotaoResult.ok();*/


        } catch (Exception e) {
            e.printStackTrace();
            return MallResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return MallResult.ok();
    }

    @Override
    public MallResult instockItem(String ids) {
        try {
            String[] idsArray = ids.split(",");
            List<Long> values = new ArrayList<Long>();
            for(String id : idsArray) {
                values.add(Long.parseLong(id));
            }
            TbItemExample e = new TbItemExample();
            TbItemExample.Criteria c = e.createCriteria();
            c.andIdIn(values);

            List<TbItem> list = itemMapper.selectByExample(e);
            if(list!=null && list.size()>0){
                TbItem item=list.get(0);
                item.setStatus(2);
                itemMapper.updateByExample(item, e);
            }
            return MallResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MallResult reshelfItem(String ids) {
        try {
            String[] idsArray = ids.split(",");
            List<Long> values = new ArrayList<Long>();
            for(String id : idsArray) {
                values.add(Long.parseLong(id));
            }
            TbItemExample e = new TbItemExample();
            TbItemExample.Criteria c = e.createCriteria();
            c.andIdIn(values);
            List<TbItem> list = itemMapper.selectByExample(e);
            if(list!=null && list.size()>0){
                TbItem item=list.get(0);
                item.setStatus(1);
                itemMapper.updateByExample(item, e);
            }
            return MallResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 添加商品描述
     * <p>Title: insertItemDesc</p>
     * <p>Description: </p>
     * @param desc
     */
    private MallResult insertItemDesc(Long itemId, String desc) {
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insert(itemDesc);
        return MallResult.ok();
    }

    /**
     * 添加规格参数
     * <p>Title: insertItemParamItem</p>
     * <p>Description: </p>
     * @param itemId
     * @param itemParam
     * @return
     */
    private MallResult insertItemParamItem(Long itemId, String itemParam) {
        //创建一个pojo
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        //向表中插入数据
        itemParamItemMapper.insert(itemParamItem);

        return MallResult.ok();

    }
}
