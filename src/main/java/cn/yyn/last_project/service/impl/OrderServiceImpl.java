package cn.yyn.last_project.service.impl;

import cn.yyn.last_project.bean.Order;
import cn.yyn.last_project.bean.OrderSub;
import cn.yyn.last_project.dao.OrderDao;
import cn.yyn.last_project.service.OrderService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 杨以诺
 * by 2020-10-04 14:40
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Order> findOrderByUsername(Integer userId) {
        return orderDao.findOrderByUsername(userId);
    }

    @Override
    public List<Order> findOrderByUsername(Integer page, Integer size, Integer userId) {
        PageHelper.startPage(page, size);
        return findOrderByUsername(userId);
    }

    @Override
    public Integer addOrder(Order order) {
        return orderDao.addOrder(order);
    }

    @Override
    public Integer addOrderSub(List<OrderSub> orderSubList,String orderId) {
        Integer key=1;
        for (int i=0;i<orderSubList.size();i++){
            orderSubList.get(i).setOrderId(orderId);
            key*=orderDao.addOrderSub(orderSubList.get(i));
        }
        return key;
    }

    @Override
    public Integer delOrderById(String orderId) {
        Integer i=orderDao.delOrderSubById(orderId);
        i*=orderDao.delOrderById(orderId);
        return i;
    }

    @Override
    public List<Order> findOrderAll() {
        return orderDao.findOrderAll();
    }

    @Override
    public List<Order> findOrderByYear(Order order) {
        return orderDao.findOrderByYear(order);
    }

    @Override
    public List<Order> findOrderByMonth(Order order) {
        return orderDao.findOrderByMonth(order);
    }

    @Override
    public List<Order> findOrderByDay(Order order) {
        return orderDao.findOrderByDay(order);
    }

    @Override
    public List<Order> findOrderByWeek(Order order) {
        return orderDao.findOrderByWeek(order);
    }

}
