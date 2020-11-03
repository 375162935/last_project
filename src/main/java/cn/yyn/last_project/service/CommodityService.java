package cn.yyn.last_project.service;

import cn.yyn.last_project.bean.Commodity;
import cn.yyn.last_project.bean.CommodityType;

import java.util.List;

/**
 * @author 杨以诺
 * by 2020-09-28 11:21
 */
public interface CommodityService {
    List<Commodity> findAllCommodity(Integer page,Integer size);

    List<Commodity> findAllCommodity();

    List<Commodity> findCommodityByType(Integer commodityTypeId);

    List<Commodity> findCommodityBySupplier(Integer supplierId);

    List<Commodity> findCommodityByKeyword(String keyword);

    Commodity findCommodityById(Integer commodityId);

    Commodity findCommodityByName(String commodityName);

    List<CommodityType> findAllCommodityType();

    Integer delCommodity(Integer commodityId);

    Integer addCommodity(Commodity commodity);

    Integer updCommodity(Commodity commodity);

    Integer updCommodityStock(List<Commodity> commodityList);

}
