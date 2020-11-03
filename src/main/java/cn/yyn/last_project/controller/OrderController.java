package cn.yyn.last_project.controller;

import cn.yyn.last_project.bean.Commodity;
import cn.yyn.last_project.bean.Order;
import cn.yyn.last_project.bean.OrderSub;
import cn.yyn.last_project.bean.UserInfo;
import cn.yyn.last_project.service.CommodityService;
import cn.yyn.last_project.service.OrderService;
import cn.yyn.last_project.service.UserService;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 杨以诺
 * by 2020-10-04 14:31
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommodityService commodityService;

    //编写订单id
    public static String getOrderId() {
        Random random = new Random();
        String result = random.nextInt(100) + "";
        if (result.length() == 1) {
            result = "0" + result;
        }
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String seconds = new SimpleDateFormat("HHmmss").format(new Date());
        String orderId = date + "00001000" + result + "00" + seconds;
        return orderId;
    }


    //-----清除购物车清单-----
    public void closeCar(HttpSession session) {
        session.removeAttribute("car_number");
        session.removeAttribute("orderSubList");
        session.removeAttribute("order");
    }
    //-----清除购物车清单-----

    //-----修改库存-----
    public void updCommodityStock(List<OrderSub> orderSubList, String key) {
        List<Commodity> commodityList = new ArrayList<Commodity>();
        for (OrderSub orderSub : orderSubList) {//修改操作后的商品剩余库存
            if (orderSub.getCommodityQuantity() > 0) {
                Commodity commodity = commodityService.findCommodityById(orderSub.getCommodityId());
                Integer stock = 0;
                if (key.equals("1")) {//扣除
                    stock = commodity.getCommodityStock() - orderSub.getCommodityQuantity();
                } else {//加回
                    stock = commodity.getCommodityStock() + orderSub.getCommodityQuantity();
                }
                commodity.setCommodityStock(stock);
                commodityList.add(commodity);
            }
        }
        commodityService.updCommodityStock(commodityList);
    }
    //-----修改库存-----

    //-----计算账单盈利和总价-----
    public Order getAllPrice(Order order) {
        double orderPrice = 0;//总价
        double orderProfit = 0;//总盈利
        int orderNum = 0;//订单商品总数
        for (OrderSub orderSub : order.getSubOrderList()) {
            double sell = orderSub.getCommodity().getCommoditySell();
            double purchase = orderSub.getCommodity().getCommodityPurchase();
            double profit = sell - purchase;
            orderPrice += orderSub.getTotalPrice();
            orderProfit += profit;
            orderNum += orderSub.getCommodityQuantity();
        }
        order.setOrderPrice(orderPrice);//总价
        order.setOrderProfit(orderProfit);//总盈利
        order.setAllNum(orderNum);//总数
        return order;
    }
    //-----计算账单盈利和总价-----

    //-----删除购物车中数量为0的商品-----
    private List<OrderSub> closeNullCommodity(List<OrderSub> orderSubList) {
        for (int i = 0; i < orderSubList.size(); i++) {
            if (orderSubList.get(i).getCommodityQuantity() == 0) {
                orderSubList.remove(i);
                i--;
            }
        }
        return orderSubList;
    }
    //-----删除购物车中数量为0的商品-----

    //-----传入当前的时间信息-----
    public Order setTime(Order order) {
        order.setOrderYear(Calendar.getInstance().get(Calendar.YEAR));//订单年
        order.setOrderMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);//订单月
        order.setOrderDay(Calendar.getInstance().get(Calendar.DATE));//订单日
        int weekByDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;//星期几
        int week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);//第几周
        if (weekByDay == 0) {
            weekByDay = 7;
            week -= 1;
        }
        order.setOrderWeekDay(weekByDay);//订单周几
        order.setOrderWeek(week);//订单第几周
        return order;
    }
    //-----传入当前的时间信息-----

    //-----创建订单对象-----
    private Order createOrder(List<OrderSub> orderSubList, UserInfo userInfo) {
        Order order = new Order();//创建order对象
        order.setOrderId(getOrderId());//传入订单id
        order.setSubOrderList(orderSubList);//传入订单集合
        order = setTime(order);//传入时间信息
        order.setOrderCreateTime(new Date());//传入订单创建事件
        order = getAllPrice(order);//获取总价、总盈利
        order.setUserInfo(userInfo);//传入用户对象
        order.setUserId(userInfo.getUserId());//传入用户id
        return order;
    }
    //-----创建订单对象-----

    //-----创建订单-----
    private Integer payOrder(HttpSession session, Order order) {
        double integral = order.getUserInfo().getIntegral();//用户已有购物积分
        if (order.getUserId() > 1) {//普通顾客购买时不加积分
            integral = integral + order.getOrderPrice() / 100;
            integral = Double.parseDouble(String.format("%.2f", integral));
        }
        String orderId = order.getOrderId();//获取订单id
        order.getUserInfo().setIntegral(integral);//修改用户购物后的积分
        Integer i = orderService.addOrder(order);//创建订单
        if (i > 0) {
            i = orderService.addOrderSub(order.getSubOrderList(), orderId);//添加订单子订单
            if (i > 0) {
                updCommodityStock(order.getSubOrderList(), "1");//修改商品库存
                if (order.getUserInfo().getUserId()>1){// 修改当前用户购物后的信息
                    userService.updUserIntegral(order.getUserInfo());
                    session.setAttribute("user", order.getUserInfo());
                }
                System.out.println("创建成功");
            } else {
                System.out.println("子订单创建失败");
            }
        } else {
            System.out.println("订单创建失败");
        }
        return i;
    }
    //-----创建订单-----

    //-----购物记录-----
    @RequestMapping("/getOPayOrder")
    public ModelAndView getOPayOrder(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     HttpSession session) {
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        List<Order> payOrder = (List<Order>) session.getAttribute("payOrder");
        if (payOrder == null) {
            payOrder = new ArrayList<>();
        }
        if (userInfo.getUserType().equals("1")) {
            payOrder = orderService.findOrderByUsername(page,size,userInfo.getUserId());
        }
        PageInfo<Order> pageInfo=new PageInfo<>(payOrder);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("front/user/front_user_order");
        return mv;
    }
    //-----购物记录-----

    //-----获取指定用户的账单-----
    @RequestMapping("/getOrderByUsername")
    public ModelAndView getOrderByUsername(@RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer size,
                                           HttpSession session,
                                           Integer userId) {
        ModelAndView mv = new ModelAndView();
        List<Order> orderList = orderService.findOrderByUsername(page, size, userId);
        PageInfo<Order> pageInfo = new PageInfo<Order>(orderList);
        mv.addObject("pageInfo", pageInfo);
        mv.addObject("userId", userId);
        session.setAttribute("page_type", "个人订单查询");
        mv.setViewName("emp/order/order_user");
        return mv;
    }
    //-----获取指定用户的账单-----

    //-----删除指定账单-----
    @ResponseBody
    @RequestMapping("/delOrderById")
    public String delOrderById(String orderId) {
        JSONObject jsonObject = new JSONObject();
        Integer i = orderService.delOrderById(orderId);
        if (i > 0) {
            jsonObject.put("result", "1");
        } else {
            jsonObject.put("result", "0");
        }
        return jsonObject.toString();
    }
    //-----删除指定账单-----

    //-----清空购物车-----
    @RequestMapping("/closeShopCar")
    public String closeShopCar(HttpSession session) {
        closeCar(session);
        return "redirect:/shopCar/toShopCommodityCar";
    }
    //-----清空购物车-----

    //-----确认订单-----
    @RequestMapping("/toPayOrder")
    public ModelAndView toPayOrder(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        List<OrderSub> orderSubList = (List<OrderSub>) session.getAttribute("orderSubList");//购物车集合
        String loginType = (String) session.getAttribute("user_type");
        UserInfo buy_user= (UserInfo) session.getAttribute("buy_user");
        UserInfo userInfo = null;
        if (buy_user!=null){
            userInfo=buy_user;
        } else {
            userInfo = (UserInfo) session.getAttribute("user");
        }
        if (orderSubList != null) {
            orderSubList = closeNullCommodity(orderSubList);//删除购物车中数量为0的商品
            if (orderSubList.size() > 0) {
                if (userInfo != null && userInfo.getUserId() > 1) {
                    mv.addObject("integral", userInfo.getIntegral());
                } else {
                    mv.addObject("integral", 0);
                }
                Order order = createOrder(orderSubList, userInfo);//创建订单对象
                session.setAttribute("order", order);
                mv.addObject("order", order);
                String user_type = (String) session.getAttribute("user_type");
                session.setAttribute("page_type", "确认订单");
                if (user_type.equals("2") || user_type.equals("3")) {
                    mv.setViewName("emp/shop/shop_commodity_order");
                } else {
                    mv.setViewName("front/front_car_order");
                }
                return mv;
            }
        }
        mv.setViewName("redirect:/shopCar/toShopCommodityCar");
        closeCar(session);
        return mv;
    }
    //-----确认订单-----

    //-----支付订单-----
    @RequestMapping("/payOrder")
    public String payOrder(Integer payType,
                           HttpSession session) {
        Order order = (Order) session.getAttribute("order");
        List<Order> payOrder = (List<Order>) session.getAttribute("payOrder");
        if (payOrder == null) {
            payOrder = new ArrayList<>();
        }
        if (order.getSubOrderList().size() > 0) {
            if (payType == 1) {
                //现金
                payOrder(session, order);//创建订单
            } else if (payType == 2) {
                //支付宝
//                return "redirect:/Alipay/PayPage";
                payOrder(session, order);//创建订单
            } else {
                //微信
                payOrder(session, order);//创建订单
            }
            payOrder.add(order);
            session.setAttribute("payOrder", payOrder);
            session.removeAttribute("buy_user");
        } else {
            System.out.println("重新选择商品");
        }
        return "redirect:/order/closeShopCar";
    }
    //-----支付订单-----

    //-----挂单-----
    @RequestMapping("/pendingOrder")
    public String pendingOrder(HttpSession session) {
        List<OrderSub> orderSubList = (List<OrderSub>) session.getAttribute("orderSubList");//购物车商品集合
        if (orderSubList == null) {//购物车初始化
            orderSubList = new ArrayList<OrderSub>();
        }
        List<Order> orderList = (List<Order>) session.getAttribute("orderList");//挂单集合
        if (orderList == null) {//挂单初始化
            orderList = new ArrayList<Order>();
        }
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        orderSubList = closeNullCommodity(orderSubList);//删除购物车中数量为0的商品
        if (orderSubList != null && orderSubList.size() > 0) {//购物测有商品时
            Order order = createOrder(orderSubList, userInfo);//创建订单对象
            orderList.add(order);//订单添加到挂单列表
            updCommodityStock(orderSubList, "1");//修改商品库存
            session.setAttribute("orderList", orderList);//替换旧的挂单集合
            closeCar(session);//清空购物车
            System.out.println("挂单成功");
        } else {
            System.out.println("挂单失败");
            session.setAttribute("orderSubList", orderSubList);
        }
        return "redirect:/shopCar/toShopCommodityCar";
    }
    //-----挂单-----

    //-----取挂单-----
    @RequestMapping("/getPendingOrderList")
    public ModelAndView getPendingOrderList(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        List<Order> orderList = (List<Order>) session.getAttribute("orderList");//挂单集合
        mv.addObject("orderList", orderList);
        mv.setViewName("emp/order/order_pending_list");
        session.setAttribute("page_type", "挂单列表");
        return mv;
    }
    //-----取挂单-----

    //-----删除挂单-----
    @ResponseBody
    @RequestMapping("/delPendingOrder")
    public String delPendingOrder(int order_index,
                                  HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        List<Order> orderList = (List<Order>) session.getAttribute("orderList");//挂单集合
        if (orderList == null) {//初始化挂单集合
            orderList = new ArrayList<Order>();
        }
        List<OrderSub> orderSubList = orderList.get(order_index).getSubOrderList();//获取指定的挂单
        updCommodityStock(orderSubList, "2");//修改商品库存
        orderList.remove(order_index);//从挂单列表中删除指定的订单
        session.setAttribute("orderList", orderList);//替换旧的挂单集合
        if (orderList.size() == 0) {
            jsonObject.put("result", "1");
        } else {
            jsonObject.put("result", "0");
        }
        return jsonObject.toString();
    }
    //-----删除挂单-----

    //-----清空挂单-----
    @RequestMapping("/delAllPendingOrder")
    public String delAllPendingOrder(HttpSession session) {
        List<Order> orderList = (List<Order>) session.getAttribute("orderList");//挂单集合
        for (Order order : orderList) {
            updCommodityStock(order.getSubOrderList(), "2");//修改商品库存
        }
        session.removeAttribute("orderList");
        return "redirect:/shopCar/toShopCommodityCar";
    }
    //-----清空挂单-----

    //-----支付挂单-----
    @ResponseBody
    @RequestMapping("/payPendingOrder")
    public String payPendingOrder(int order_index,
                                  HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        List<Order> orderList = (List<Order>) session.getAttribute("orderList");//获取挂单集合
        if (orderList.size() > 0) {
            Order order = orderList.get(order_index);//获取指定挂单信息
            //判断是否支付成功
            if (true) {
                Integer i = payOrder(session, order);//创建订单
                if (i > 0) {
                    orderList.remove(order_index);//删除当前订单
                    if (orderList.size() == 0) {//挂单清空
                        session.removeAttribute("orderList");
                    } else {
                        session.setAttribute("orderList", orderList);//替换旧的挂单集合
                    }
                    jsonObject.put("result", orderList.size() + 1);
                } else {
                    jsonObject.put("result", 0);
                }
            }
        }
        return jsonObject.toString();
    }
    //-----支付挂单-----

    //-----获取营业额信息-----
    @RequestMapping("/operateBill")
    public ModelAndView operateBill() {
        ModelAndView mv = new ModelAndView();
        Order order = new Order();
        order = setTime(order);
        List<Order> orderByYear = orderService.findOrderByYear(order);//本年的所有订单
        List<Order> orderByMonth = orderService.findOrderByMonth(order);//本月的所有订单
        List<Order> orderByWeek = orderService.findOrderByWeek(order);//本周的所有订单
        List<Order> orderByDay = orderService.findOrderByDay(order);//本日的所有订单
        //每月的总盈利
        Map<Integer, Double> monthMap = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthMap.put(i, 0.0);
        }
        for (Order order1 : orderByYear) {
            int key = order1.getOrderMonth();
            double money = monthMap.get(key);
            money += order1.getOrderPrice();//交易金额
            monthMap.put(key, money);
        }
        //每周的总盈利
        Map<Integer, Double> dayByWeekMap = new HashMap<>();
        for (int i = 1; i <= 7; i++) {
            dayByWeekMap.put(i, 0.0);
        }
        for (Order order1 : orderByWeek) {
            int key = order1.getOrderWeekDay();
            double money = dayByWeekMap.get(key);
            money += order1.getOrderPrice();//交易金额
            dayByWeekMap.put(key, money);
        }
        double yearProfit = 0, monthProfit = 0, weekProfit = 0, dayProfit = 0;
        for (Order order1 : orderByYear) {
            yearProfit += order1.getOrderProfit();
        }
        for (Order order1 : orderByMonth) {
            monthProfit += order1.getOrderProfit();
        }
        for (Order order1 : orderByWeek) {
            weekProfit += order1.getOrderProfit();
        }
        for (Order order1 : orderByDay) {
            dayProfit += order1.getOrderProfit();
        }
        mv.addObject("yearProfit", yearProfit);//年盈利
        mv.addObject("monthProfit", monthProfit);//月盈利
        mv.addObject("weekProfit", weekProfit);//周盈利
        mv.addObject("dayProfit", dayProfit);//日盈利
        mv.addObject("dayByWeekMap", dayByWeekMap);//每日盈利
        mv.addObject("monthMap", monthMap);//每月盈利
        mv.addObject("orderByYear", orderByYear);
        mv.addObject("orderByMonth", orderByMonth);
        mv.addObject("orderByWeek", orderByWeek);
        mv.addObject("orderByDay", orderByDay);
        mv.setViewName("emp/index");
        return mv;
    }
    //-----营业额信息-----

}
