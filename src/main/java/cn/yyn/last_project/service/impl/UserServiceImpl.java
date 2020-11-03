package cn.yyn.last_project.service.impl;

import cn.yyn.last_project.bean.UserInfo;
import cn.yyn.last_project.dao.UserDao;
import cn.yyn.last_project.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 杨以诺
 * by 2020-09-25 20:44
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserInfo findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public UserInfo findAdminByUsername(String username) {
        return userDao.findAdminByUsername(username);
    }

    @Override
    public UserInfo findSupplierByUsername(String username) {
        return userDao.findSupplierByUsername(username);
    }

    @Override
    public List<UserInfo> findUserByPhone(String userPhone) {
        return userDao.findUserByPhone(userPhone);
    }

    @Override
    public List<UserInfo> findAllSupplier(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        return userDao.findAllSupplier();
    }

    @Override
    public List<UserInfo> findAllSupplier() {
        return userDao.findAllSupplier();
    }

    @Override
    public List<UserInfo> findAllUser(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        return userDao.findAllUser();
    }

    @Override
    public List<UserInfo> findAllUser() {
        return userDao.findAllUser();
    }

    @Override
    public Integer updPassword(UserInfo userInfo) {
        return userDao.updPassword(userInfo);
    }

    @Override
    public Integer updAdminPassword(UserInfo userInfo) {
        return userDao.updAdminPassword(userInfo);
    }

    @Override
    public Integer updDetail(UserInfo userInfo) {
        return userDao.updDetail(userInfo);
    }

    @Override
    public Integer updAdminDetail(UserInfo userInfo) {
        return userDao.updAdminDetail(userInfo);
    }

    @Override
    public Integer addUser(UserInfo userInfo) {
        return userDao.addUser(userInfo);
    }

    @Override
    public Integer updUserIntegral(UserInfo userInfo) {
        return userDao.updUserIntegral(userInfo);
    }
}
