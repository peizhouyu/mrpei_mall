package cn.mrpei.manager.service.impl;

import cn.mrpei.common.pojo.EUDResult;
import cn.mrpei.common.pojo.MallResult;
import cn.mrpei.manager.dao.TbItemMapper;
import cn.mrpei.manager.dao.TbItemParamItemMapper;
import cn.mrpei.manager.dao.TbItemParamMapper;
import cn.mrpei.manager.pojo.*;
import cn.mrpei.manager.service.ItemParamService;
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
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamMapper itemParamMapper;

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;


    @Override
    public MallResult getItemParamByCid(long cid) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
        //判断是否查询到结果
        if (list != null && list.size() > 0) {
            return MallResult.ok(list.get(0));
        }

        return MallResult.ok();
    }

    @Override
    public MallResult insertItemParam(TbItemParam itemParam) {
        //补全pojo
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        //插入到规格参数模板表
        itemParamMapper.insert(itemParam);
        return MallResult.ok();
    }


    //分页查询
    @Override
    public EUDResult getItemList(int page, int rows) {
        TbItemParamExample example=new TbItemParamExample();
        TbItemCatExample example1=new TbItemCatExample();
        PageHelper.startPage(page, rows);

        EUDResult result=new EUDResult();

        List<TbItemParam> list = itemParamMapper.selectByExample(example);
		/*if(list!=null && list.size()>0){
			for (TbItemParam item : list) {
				Long itemId = item.getItemCatId();
				cn.tf.taotao.po.TbItemCatExample.Criteria createCriteria = example1.createCriteria();
				createCriteria.andIdEqualTo(itemId);
				List<TbItemCat> list1 = itemCatMapper.selectByExample(example1);

				result.setRows(list1);

			}

		}*/
        result.setRows(list);

        PageInfo<TbItemParam> pageInfo=new PageInfo<TbItemParam>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }


    @Override
    public TbItemParamItem listParamDesc(Long id) {

        TbItemExample example=new TbItemExample();
        TbItemExample.Criteria createCriteria = example.createCriteria();
        createCriteria.andIdEqualTo(id);

        List<TbItem> list = itemMapper.selectByExample(example);
        if(list!=null && list.size()>0){
            TbItem item=list.get(0);
            Long cid = item.getCid();

            //通过cid查询tb_item_cat的id
            TbItemParamExample example1=new TbItemParamExample();
            TbItemParamExample.Criteria createCriteria1 = example1.createCriteria();
            createCriteria1.andItemCatIdEqualTo(cid);

            List<TbItemParam> list1 = itemParamMapper.selectByExample(example1);
            if(list1!=null && list1.size()>0){
                TbItemParam item1=list1.get(0);
                Long id2 = item1.getId();

                //通过id2取规格参数
                TbItemParamItem result = itemParamItemMapper.selectByPrimaryKey(id2);
                return result;
            }

        }
        return null;
    }


    @Override
    public MallResult deleteParam(String ids) {
        try {
            String[] idsArray = ids.split(",");
            List<Long> values = new ArrayList<Long>();
            for(String id : idsArray) {
                values.add(Long.parseLong(id));
            }
            TbItemParamExample e = new TbItemParamExample();
            TbItemParamExample.Criteria c = e.createCriteria();
            c.andIdIn(values);
            itemParamMapper.deleteByExample(e);


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return MallResult.ok();
    }

}
