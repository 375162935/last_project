package cn.yyn.last_project.service;

import cn.yyn.last_project.bean.UserInfo;

import java.util.List;

/**
 * @author 杨以诺
 * by 2020-09-25 20:44
 */
public interface UserService {

    //根据账号差用户
    UserInfo findByUsername(String username);
    //根据账号查商家管理员
    UserInfo findAdminByUsername(String username);
    //根据账号查供应商
    UserInfo findSupplierByUsername(String username);
    //根据手机号查询用户
    List<UserInfo> findUserByPhone(String userPhone);
    //查找所有供应商
    List<UserInfo> findAllSupplier(Integer page,Integer size);
    List<UserInfo> findAllSupplier();
    //查找所有会员
    List<UserInfo> findAllUser(Integer page,Integer size);
    List<UserInfo> findAllUser();

    //修改用户、供应商密码
    Integer updPassword(UserInfo userInfo);
    //修改商家密码
    Integer updAdminPassword(UserInfo userInfo);

    //修改用户、供应商信息
    Integer updDetail(UserInfo userInfo);
    //修改商家信息
    Integer updAdminDetail(UserInfo userInfo);

    //添加用户、供应商
    Integer addUser(UserInfo userInfo);
    //修改用户积分
    Integer updUserIntegral(UserInfo userInfo);
}
