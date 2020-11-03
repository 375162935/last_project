package cn.yyn.last_project.service;

import cn.yyn.last_project.bean.Order;
import cn.yyn.last_project.bean.OrderSub;

import java.util.List;

/**
 * @author 杨以诺
 * by 2020-10-04 14:40
 */
public interface OrderService {
    List<Order> findOrderByUsername(Integer userId);

    List<Order> findOrderByUsername(Integer page,Integer size,Integer userId);

    Integer addOrder(Order order);

    Integer addOrderSub(List<OrderSub> orderSubList,String orderId);

    Integer delOrderById(String orderId);

    List<Order> findOrderAll();

    List<Order> findOrderByYear(Order order);

    List<Order> findOrderByMonth(Order order);

    List<Order> findOrderByDay(Order order);

    List<Order> findOrderByWeek(Order order);

}
