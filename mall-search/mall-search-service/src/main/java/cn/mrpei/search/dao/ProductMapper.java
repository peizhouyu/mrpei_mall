package cn.mrpei.search.dao;


import cn.mrpei.manager.pojo.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {

    Product selectByPrimaryKey(Integer id);

    List<Product> selectList();

}

