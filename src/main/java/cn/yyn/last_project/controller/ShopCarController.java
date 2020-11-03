package cn.yyn.last_project.controller;

import cn.yyn.last_project.bean.Commodity;
import cn.yyn.last_project.bean.Order;
import cn.yyn.last_project.bean.OrderSub;
import cn.yyn.last_project.service.CommodityService;
import cn.yyn.last_project.service.OrderService;
import cn.yyn.last_project.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨以诺
 * by 2020-10-07 14:32
 */
@Controller
@RequestMapping("/shopCar")
public class ShopCarController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private UserService userService;

    public List<Commodity> findAll() {
        return commodityService.findAllCommodity();
    }


    /**
     * 用户商品选购功能
     */
    //-----跳转用户选购模块-----
    @RequestMapping("/toShopCommodity")
    public ModelAndView toShopCommodity(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        List<Commodity> commodityList = commodityService.findAllCommodity();//获取所有商品信息
        mv.addObject("commodityList", commodityList);
        mv.setViewName("emp/shop/shop_commodity_all");
        session.setAttribute("page_type", "商品选购");
        return mv;
    }
    //-----跳转用户选购模块-----

    //-----调整购物车商品-----
    @ResponseBody
    @RequestMapping("/addCarCommodity")
    public String addCarCommodity(Integer c_id,
                                  Integer num,
                                  HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        List<OrderSub> orderSubList = (List<OrderSub>) session.getAttribute("orderSubList");//获取购物车商品信息
        if (orderSubList == null) {//未加入则初始化
            orderSubList = new ArrayList<OrderSub>();
        }
        if (num == 0) {//删除购物车选购商品
            if (orderSubList.size() > 0) {
                for (int i = 0; i < orderSubList.size(); i++) {
                    if (orderSubList.get(i).getCommodityId() == c_id ||
                            orderSubList.get(i).getCommodityId().equals(c_id)) {
                        orderSubList.remove(i);
                        break;
                    }
                }
            }
        } else {
            boolean flag = true;
            for (int i = 0; i < orderSubList.size(); i++) {
                if (orderSubList.get(i).getCommodityId() == c_id ||
                        orderSubList.get(i).getCommodityId().equals(c_id)) {
                    flag = false;
                    double sell = orderSubList.get(i).getCommodity().getCommoditySell();
                    double purchase = orderSubList.get(i).getCommodity().getCommodityPurchase();
                    double totalPrice = sell * num;//总价
                    double totalProfit = (sell - purchase) * num;
                    orderSubList.get(i).setCommodityQuantity(num);//订单商品数量
                    orderSubList.get(i).setTotalPrice(totalPrice);//订单商品总价
                    orderSubList.get(i).setTotalProfit(totalProfit);//订单商品盈利总额
                    break;
                }
            }//没有该商品时
            if (flag) {
                OrderSub orderSub = new OrderSub();
                orderSub.setCommodityId(c_id);//商品id
                orderSub.setCommodity(commodityService.findCommodityById(c_id));//商品对象
                orderSub.setCommodityQuantity(num);//商品数量
                double sell = orderSub.getCommodity().getCommoditySell();
                double purchase = orderSub.getCommodity().getCommodityPurchase();
                orderSub.setTotalPrice(sell * num);//商品总价
                orderSub.setTotalProfit((sell - purchase) * num);//商品盈利额
                orderSubList.add(orderSub);
            }
        }
        session.setAttribute("car_number", orderSubList.size());
        session.setAttribute("orderSubList", orderSubList);
        jsonObject.put("result", orderSubList.size());
        return jsonObject.toString();
    }
    //-----记录购物车商品-----

    //-----获取商品选购按钮状态-----
    @ResponseBody
    @RequestMapping("/getCommodityType")
    public String getCommodityType(HttpSession session) {
        List<OrderSub> orderSubList = (List<OrderSub>) session.getAttribute("orderSubList");//获取购物车商品信息
        JSONObject jsonObject = new JSONObject();
        if (orderSubList == null) {//检测购物车是否有商品
            jsonObject.put("result", "0");
        } else {
            jsonObject.put("result", orderSubList);//传入购物车商品
        }
        return jsonObject.toString();
    }
    //-----获取商品选购按钮状态-----

    //-----跳转购物车-----
    @RequestMapping("/toShopCommodityCar")
    public ModelAndView toShopCommodityCar(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String user_type= (String) session.getAttribute("user_type");
        List<OrderSub> orderSubList = (List<OrderSub>) session.getAttribute("orderSubList");//购物车集合
        if (orderSubList == null) {//初始化购物车集合
            orderSubList = new ArrayList<OrderSub>();
        }
        List<Order> orderList = (List<Order>) session.getAttribute("orderList");//挂单集合
        if (orderList == null) {//初始化挂单集合
            orderList = new ArrayList<Order>();
        }
        System.out.println("这是购物车的orderSubList");
        System.out.println(orderSubList);
        session.setAttribute("page_type", "我的购物车");
        session.setAttribute("orderList", orderList);//挂单集合
        mv.addObject("orderSubList", orderSubList);//购物车集合
        if (user_type.equals("2")||user_type.equals("3")) {
            mv.setViewName("emp/shop/shop_commodity_car");
        } else {
            mv.setViewName("/front/front_commodity_car");
        }
        return mv;
    }
    //-----跳转购物车-----

}
