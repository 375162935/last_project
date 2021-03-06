package cn.yyn.last_project.dao;

import cn.yyn.last_project.bean.Commodity;
import cn.yyn.last_project.bean.CommodityType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 杨以诺
 * by 2020-09-28 11:20
 */
@Repository
public interface CommodityDao {
    //查找所有商品类别
    List<CommodityType> findAllCommodityType();
    //添加商品类别
    Integer addCommodityType(CommodityType commodityType);
    //修改商品类别
    Integer updCommodityType(CommodityType commodityType);
    //根据商品类别id删除商品类别
    Integer delCommodityType(Integer commodityTypeId);
    //查找所有商品
    List<Commodity> findAllCommodity();
    //根据商品类别id查找商品类别
    List<Commodity> findCommodityByType(Integer commodityTypeId);
    //根据供应商id查找商品
    List<Commodity> findCommodityBySupplier(Integer supplierId);
    //根据关键词查找商品
    List<Commodity> findCommodityByKeyword(String keyword);
    //根据商品id查找商品
    Commodity findCommodityById(Integer commodityId);
    //根据名字查商品
    Commodity findCommodityByName(String commodityName);
    //根据商品id删除商品
    Integer delCommodity(Integer commodityId);
    //添加商品
    Integer addCommodity(Commodity commodity);
    //修改商品
    Integer updCommodity(Commodity commodity);
    //修改商品库存
    Integer updCommodityStock(Commodity commodity);
}
