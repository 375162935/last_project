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
    List<CommodityType> findAllCommodityType();

    List<Commodity> findAllCommodity();

    List<Commodity> findCommodityByType(Integer commodityTypeId);

    List<Commodity> findCommodityBySupplier(Integer supplierId);

    List<Commodity> findCommodityByKeyword(String keyword);

    Commodity findCommodityById(Integer commodityId);

    Commodity findCommodityByName(String commodityName);

    Integer delCommodity(Integer commodityId);

    Integer addCommodity(Commodity commodity);

    Integer updCommodity(Commodity commodity);

    Integer updCommodityStock(Commodity commodity);
}
