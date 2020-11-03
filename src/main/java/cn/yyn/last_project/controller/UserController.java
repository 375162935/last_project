package cn.yyn.last_project.controller;

import cn.yyn.last_project.bean.*;
import cn.yyn.last_project.service.CommodityService;
import cn.yyn.last_project.service.UserService;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author 杨以诺
 * by 2020-09-25 20:43
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommodityService commodityService;
    private static String loginType;

    //-----进入主页-----
    @RequestMapping("/toIndex")
    public String toIndex(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        if (userInfo == null) {
            return "redirect:/user/closeUser";
        } else {
            List<CommodityType> commodityTypes = commodityService.findAllCommodityType();
            session.setAttribute("commodityTypes", commodityTypes);
            session.setAttribute("page_type", "主页");
            loginType = (String) session.getAttribute("user_type");
            if (loginType.equals("2")) {
                //进入后端获取盈利表
                return "redirect:/order/operateBill";
            } else {
                List<Commodity> commodities = commodityService.findAllCommodity();
                Map<Integer, List<Commodity>> map = new HashMap<>();
                for (CommodityType ct : commodityTypes) {
                    map.put(ct.getCommodityTypeId(), new ArrayList<Commodity>());
                }
                for (Commodity c : commodities) {
                    int index = c.getSupplier().getSupplierId();
                    List<Commodity> commodityList = map.get(index);
                    commodityList.add(c);
                    map.put(index, commodityList);
                }
                session.setAttribute("map", map);
                //进入前端主页
                return "redirect:/commodity/findAllCommodity";
            }

        }
    }
    //-----进入主页-----

    /**
     * 登录功能
     */
    //-----无账号登录-----
    @RequestMapping("/nullLogin")
    public String nullLogin(HttpSession session) {
        UserInfo userInfo = userService.findByUsername("1");
        session.setAttribute("user", userInfo);//当前登录会员信息
        session.setAttribute("user_type", "0");//标注为无账号登录
        return "redirect:/user/toIndex";
    }
    //-----无账号登录-----

    //-----登录-----
    @ResponseBody
    @RequestMapping("/login")
    public String login(String loginType,
                        String username,
                        String password,
                        HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        UserInfo userInfo = null;
        if (loginType.equals("2")) {
            userInfo = userService.findAdminByUsername(username);
        } else if (loginType.equals("3")) {
            userInfo = userService.findSupplierByUsername(username);
        } else {
            userInfo = userService.findByUsername(username);
        }
        if (userInfo == null) {
            jsonObject.put("result", "0");
            System.out.println("账号不存在");
        } else {
            if (userInfo.getPassword().equals(password)) {
                session.setAttribute("user", userInfo);//传入会员信息
                session.setAttribute("user_type", loginType);//标注登录身份
                jsonObject.put("result", "2");
            } else {
                jsonObject.put("result", "1");
                System.out.println("密码错误");
            }
        }
        return jsonObject.toString();
    }
    //-----登录-----

    /**
     * 修改密码功能
     */
    //-----跳转修改密码页面-----
    @RequestMapping("/toUpdPassword")
    public String toUpdPassword(HttpSession session) {
        session.setAttribute("page_type", "修改密码");
        if (loginType.equals("2") || loginType.equals("3")) {
            return "emp/user/user_password";
        } else {
            return "front/user/front_user_update_pwd";
        }
    }
    //-----跳转修改密码页面-----

    //-----检验旧密码是否正确-----
    @ResponseBody
    @RequestMapping("/judgmentPsd")
    public String judgmentPsd(String oldpwd,
                              HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        if (userInfo.getPassword().equals(oldpwd)) {
            jsonObject.put("result", "1");
        } else {
            jsonObject.put("result", "0");
        }
        return jsonObject.toString();
    }
    //-----检验旧密码是否正确-----

    //-----修改密码-----
    @RequestMapping("/updPassword")
    public String updPassword(String confirmpwd,
                              HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        System.out.println(userInfo);
        userInfo.setPassword(confirmpwd);
        Integer i = userService.updPassword(userInfo);
        if (i > 0) {
            System.out.println("修改成功");
        } else {
            System.out.println("修改失败");
            session.setAttribute("user", userInfo);
        }
        return "redirect:/user/toIndex";
    }
    //-----修改密码-----

    /**
     * 修改用户信息功能
     */
    //-----跳转用户信息页面-----
    @RequestMapping("/toUpdUserDetail")
    public String toUpdUserDetail(HttpSession session) {
        loginType = (String) session.getAttribute("user_type");
        session.setAttribute("page_type", "修改用户信息");
        if (loginType.equals("2") || loginType.equals("3")) {
            return "emp/user/user_detail";
        } else if (loginType.equals("1")) {
            return "/front/user/front_user_dental";
        } else {
            return "/front/user/front_user_register";
        }
    }
    //-----跳转用户信息页面-----

    //-----前往用户修改页面-----
    @RequestMapping("/toUpdateUser")
    public String toUpdateUser(HttpSession session) {
        return "front/user/front_user_update";
    }
    //-----前往用户修改页面-----

    //-----修改用户信息-----
    @ResponseBody
    @RequestMapping("/updUserDetail")
    public String updUserDetail(@Param("name") String name,
                                @Param("gender") String gender,
                                @Param("birthday") String birthday,
                                @Param("userPhone") String userPhone,
                                HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        JSONObject jsonObject = new JSONObject();
        try {
            Date user_birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
            userInfo.setName(name);
            userInfo.setGender(gender);
            userInfo.setBirthday(user_birthday);
            userInfo.setUserPhone(userPhone);
            System.out.println(userInfo);
            Integer i = userService.updDetail(userInfo);
            if (i > 0) {
                jsonObject.put("result", "1");
                System.out.println("修改成功");
            } else {
                jsonObject.put("result", "0");
                System.out.println("修改失败");
            }
            return jsonObject.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return jsonObject.toString();
    }
    //-----修改用户信息-----

    /**
     * 用户注册功能
     */
    //-----会员注册-----
    @RequestMapping("/toAddUser")
    public String toAddUser(HttpSession session) {
        session.setAttribute("page_type", "注册会员");
        if (loginType.equals("2") || loginType.equals("3")) {
            return "emp/user/user_register";
        } else {
            return "front/user/front_user_register";
        }
    }

    //-----检验账号是否存在-----
    @ResponseBody
    @RequestMapping("/judgeUsername")
    public String judgeUsername(String username) {
        JSONObject jsonObject = new JSONObject();
        UserInfo userInfo = userService.findByUsername(username);
        if (userInfo == null) {
            jsonObject.put("result", "1");
        } else {
            jsonObject.put("result", "0");
        }
        return jsonObject.toString();
    }
    //-----检验账号是否存在-----

    //-----会员注册-----
    @ResponseBody
    @RequestMapping("/addUser")
    public String addUser(String username,
                          String password,
                          String name,
                          String gender,
                          String birthday,
                          String userPhone) throws ParseException {
        Date birth_day = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
        JSONObject jsonObject = new JSONObject();
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setName(name);
        userInfo.setGender(gender);
        userInfo.setBirthday(birth_day);
        userInfo.setUserPhone(userPhone);
        userInfo.setUserType("1");
        Integer i = userService.addUser(userInfo);
        if (i > 0) {
            jsonObject.put("result", "1");
        } else {
            jsonObject.put("result", "0");
        }
        return jsonObject.toString();
    }
    //-----会员注册-----

    /**
     * 会员管理功能
     */
    //-----所有会员-----
    @RequestMapping("/findAllVip")
    public ModelAndView findAllVip(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   HttpSession session) {
        ModelAndView mv = new ModelAndView();
        List<UserInfo> userInfoList = userService.findAllUser(page, size);
        PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfoList);
        mv.addObject("pageInfo", pageInfo);
        session.setAttribute("page_type", "会员总览");
        mv.setViewName("emp/user/user_all");
        return mv;
    }
    //-----所有会员-----

    //-----通过账号查找会员-----
    @ResponseBody
    @RequestMapping("/findVipByUsername")
    public String findVipByUsername(String keyword,
                                    HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        List<UserInfo> userInfoList = userService.findAllUser();
        session.setAttribute("vip_keyword", keyword);
        if (keyword.length() == 0 || keyword.equals("") || keyword == null) {
            if (session.getAttribute("find_vip") != null) {
                jsonObject.put("result", "1");
            }
        } else {
            List<UserInfo> userInfo = new ArrayList<UserInfo>();
            for (UserInfo u : userInfoList) {
                if (u.getUsername().indexOf(keyword.trim()) != -1) {
                    userInfo.add(u);
                }
            }
            if (userInfo.size() > 0) {
                jsonObject.put("result", "0");
                session.setAttribute("find_vip", userInfo);
            } else {
                jsonObject.put("result", "1");
            }
        }
        return jsonObject.toString();
    }
    //-----通过账号查找会员-----

    //-----前往查找结果页面-----
    @RequestMapping("/toFindVipByUsername")
    public String toFindVipByUsername(HttpSession session) {
        session.setAttribute("page_type", "搜索会员");
        return "emp/user/user_find";
    }
    //-----前往查找结果页面-----

    //-----根据电话查找用户-----
    @ResponseBody
    @RequestMapping("/getUserByPhone")
    public String getUserByPhone(String userPhone,
                                 HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        List<UserInfo> users = userService.findUserByPhone(userPhone);
        System.out.println(users);
        if (users.size()==1){//存在
            jsonObject.put("result","2");
            session.setAttribute("buy_user",users.get(0));
        }else if (users.size()==0){//不存在
            jsonObject.put("result","1");
        }else {//异常
            jsonObject.put("result","0");
        }
        return jsonObject.toString();
    }
    //-----根据电话查找用户-----

    //-----清除购买用户信息-----
    @RequestMapping("/closeBuyUser")
    public String closeBuyUser(HttpSession session){
        session.removeAttribute("buy_user");
        return "redirect:/shopCar/toShopCommodityCar";
    }
    //-----清除购买用户信息-----

    /**
     * 退出系统功能
     */
    //-----退出系统-----
    @RequestMapping("/close")
    public String close(HttpSession session) {
        List<Order> orderList = (List<Order>) session.getAttribute("orderList");//挂单集合
        if (orderList != null && orderList.size() > 0) {//清空挂单
            for (Order order : orderList) {
                List<Commodity> commodityList = new ArrayList<Commodity>();
                for (OrderSub orderSub : order.getSubOrderList()) {                           //修改操作后的商品剩余库存
                    if (orderSub.getCommodityQuantity() > 0) {
                        Commodity commodity = commodityService.findCommodityById(orderSub.getCommodityId());
                        Integer stock = 0;
                        stock = commodity.getCommodityStock() + orderSub.getCommodityQuantity();
                        commodity.setCommodityStock(stock);
                        commodityList.add(commodity);
                    }
                }
                commodityService.updCommodityStock(commodityList);
            }
        }
        Enumeration em = session.getAttributeNames();
        while (em.hasMoreElements()) {
            session.removeAttribute(em.nextElement().toString());
        }
        return "login";
    }
    //-----退出系统-----

    //-----清除用户信息-----
    @RequestMapping("/closeUser")
    public String closeUser(HttpSession session) {
        session.removeAttribute("user");
        return "login";
    }
    //-----清除用户信息-----
}
