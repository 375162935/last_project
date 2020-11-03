package cn.yyn.last_project.service;

import cn.yyn.last_project.bean.Supplier;

import java.util.List;

/**
 * @author 杨以诺
 * by 2020-09-28 20:12
 */
public interface SupplierService {
    List<Supplier> findAllSupplier(Integer page,Integer size);

    List<Supplier> findAllSupplier();

    Integer addSupplier(Supplier supplier);

    Supplier findSupplierByName(String supplierName);

    Supplier findSupplierById(Integer supplierId);

    Integer updSupplier(Supplier supplier);

    Integer delSupplier(Integer supplierId);
}
