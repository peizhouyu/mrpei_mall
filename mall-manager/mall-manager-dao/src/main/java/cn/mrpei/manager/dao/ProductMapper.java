package cn.mrpei.manager.dao;


import cn.mrpei.manager.pojo.Product;
import org.apache.ibatis.annotations.Param;


import java.util.List;


public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectList();

    List<Product> selectByNameAndProductId(@Param("productName") String productName, @Param("productId") Integer productId);

    List<Product> selectByNameAndCategoryIds(@Param("productName") String productName, @Param("categoryIdList") List<Integer> categoryIdList);

    //用Integer 因为int无法为null 有些商品可能被删除
    Integer selectStockByProductId(Integer id);

    long countProduct();
}

