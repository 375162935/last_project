package cn.yyn.last_project.dao;

import cn.yyn.last_project.bean.Order;
import cn.yyn.last_project.bean.OrderSub;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 杨以诺
 * by 2020-10-04 14:38
 */
@Repository
public interface OrderDao {
    List<Order> findOrderByUsername(Integer userId);

    Integer addOrder(Order order);

    Integer addOrderSub(OrderSub orderSub);

    Integer delOrderById(String orderId);

    Integer delOrderSubById(String orderId);

    List<Order> findOrderAll();

    List<Order> findOrderByYear(Order order);

    List<Order> findOrderByMonth(Order order);

    List<Order> findOrderByWeek(Order order);

    List<Order> findOrderByDay(Order order);


}
