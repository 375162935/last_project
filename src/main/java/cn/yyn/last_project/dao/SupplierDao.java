package cn.yyn.last_project.dao;

import cn.yyn.last_project.bean.Supplier;
import cn.yyn.last_project.bean.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 杨以诺
 * by 2020-09-28 20:12
 */
@Repository
public interface SupplierDao {
    List<Supplier> findAllSupplier();

    Integer addSupplier(Supplier supplier);

    Supplier findSupplierByName(String supplierName);

    Supplier findSupplierById(Integer supplierId);

    Integer updSupplier(Supplier supplier);

    Integer delSupplier(Integer supplierId);
}
