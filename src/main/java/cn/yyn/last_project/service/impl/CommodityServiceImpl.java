package cn.yyn.last_project.service.impl;

import cn.yyn.last_project.bean.Commodity;
import cn.yyn.last_project.bean.CommodityType;
import cn.yyn.last_project.dao.CommodityDao;
import cn.yyn.last_project.service.CommodityService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 杨以诺
 * by 2020-09-28 11:21
 */
@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    private CommodityDao commodityDao;

    @Override
    public List<CommodityType> findAllCommodityType() {
        return commodityDao.findAllCommodityType();
    }

    @Override
    public Integer addCommodityType(CommodityType commodityType) {
        return commodityDao.addCommodityType(commodityType);
    }

    @Override
    public Integer updCommodityType(CommodityType commodityType) {
        return commodityDao.updCommodityType(commodityType);
    }

    @Override
    public Integer delCommodityType(Integer commodityTypeId) {
        return commodityDao.delCommodityType(commodityTypeId);
    }

    @Override
    public Integer delCommodityType(Integer page, Integer size, Integer commodityTypeId) {
        PageHelper.startPage(page, size);
        return commodityDao.delCommodityType(commodityTypeId);
    }

    @Override
    public List<Commodity> findAllCommodity(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return commodityDao.findAllCommodity();
    }

    @Override
    public List<Commodity> findAllCommodity() {
        return commodityDao.findAllCommodity();
    }

    @Override
    public Commodity findCommodityById(Integer commodityId) {
        return commodityDao.findCommodityById(commodityId);
    }

    @Override
    public Commodity findCommodityByName(String commodityName) {
        return commodityDao.findCommodityByName(commodityName);
    }

    @Override
    public List<CommodityType> findAllCommodityType(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return commodityDao.findAllCommodityType();
    }

    @Override
    public Integer delCommodity(Integer commodityId) {
        return commodityDao.delCommodity(commodityId);
    }

    @Override
    public List<Commodity> findCommodityByType(Integer commodityTypeId) {
        return commodityDao.findCommodityByType(commodityTypeId);
    }

    @Override
    public List<Commodity> findCommodityBySupplier(Integer supplierId) {
        return commodityDao.findCommodityBySupplier(supplierId);
    }

    @Override
    public List<Commodity> findCommodityBySupplier(Integer page, Integer size, Integer supplierId) {
        PageHelper.startPage(page, size);
        return commodityDao.findCommodityBySupplier(supplierId);
    }

    @Override
    public List<Commodity> findCommodityByKeyword(String keyword) {
        return commodityDao.findCommodityByKeyword(keyword);
    }

    @Override
    public Integer addCommodity(Commodity commodity) {
        return commodityDao.addCommodity(commodity);
    }

    @Override
    public Integer updCommodity(Commodity commodity) {
        return commodityDao.updCommodity(commodity);
    }

    @Override
    public Integer updCommodityStock(List<Commodity> commodityList) {
        Integer i = 1;
        for (Commodity commodity : commodityList) {
            i = commodityDao.updCommodityStock(commodity);
        }
        return i;
    }
}
