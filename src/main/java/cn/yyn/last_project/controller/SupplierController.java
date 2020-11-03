package cn.yyn.last_project.controller;

import cn.yyn.last_project.bean.Supplier;
import cn.yyn.last_project.bean.UserInfo;
import cn.yyn.last_project.service.SupplierService;
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
import java.util.List;

/**
 * @author 杨以诺
 * by 2020-09-29 11:11
 */
@Controller
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private UserService userService;

    /**
     * 搜索供应商功能
     */
    //-----所有供应商-----
    @RequestMapping("/findAllSupplier")
    public ModelAndView findAllSupplier(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        HttpSession session) {
        ModelAndView mv = new ModelAndView();
        List<Supplier> supplierList = supplierService.findAllSupplier(page, size);
        PageInfo<Supplier> pageInfo = new PageInfo<Supplier>(supplierList);
        session.setAttribute("page_type", "所有供应商");
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("emp/supplier/supplier_all");
        return mv;
    }
    //-----所有供应商-----

    /**
     * 注册供应商功能
     */
    //-----跳转供应商注册页面-----
    @RequestMapping("/toAddSupplier")
    public String toAddSupplier(HttpSession session) {
        session.setAttribute("page_type", "注册供应商");
        return "emp/supplier/supplier_register";
    }
    //-----跳转供应商注册页面-----

    //-----检验供应商公司是否已注册-----
    @ResponseBody
    @RequestMapping("/judgeSupplier")
    public String judgeSupplier(String supplierName) {
        JSONObject jsonObject = new JSONObject();
        Supplier supplier = supplierService.findSupplierByName(supplierName);
        if (supplier == null) {
            jsonObject.put("result", "1");
        } else {
            jsonObject.put("result", "0");
        }
        return jsonObject.toString();
    }
    //-----检验供应商公司是否已注册-----

    //-----注册供应商-----
    @ResponseBody
    @RequestMapping("/addSupplier")
    public String addSupplier(String supplierName,
                              String supplierAddress,
                              String supplierCEO,
                              String supplierPhone,
                              String supplierCountry,
                              String username,
                              String password,
                              String name,
                              String gender,
                              String userPhone) {
        JSONObject jsonObject = new JSONObject();
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setName(name);
        userInfo.setGender(gender);
        userInfo.setUserType("3");
        userInfo.setUserPhone(userPhone);
        Integer i = userService.addUser(userInfo);
        if (i > 0) {
            Integer userId = userService.findByUsername(username).getUserId();
            Supplier supplier = new Supplier();
            supplier.setSupplierName(supplierName);
            supplier.setSupplierCEO(supplierCEO);
            supplier.setSupplierPhone(supplierPhone);
            supplier.setSupplierCountry(supplierCountry);
            supplier.setUserId(userId);
            supplier.setSupplierAddress(supplierAddress);
            Integer j = supplierService.addSupplier(supplier);
            if (j > 0) {
                jsonObject.put("result", "1");
            } else {
                jsonObject.put("result", "0");
                System.out.println("添加供应商失败");
            }
        } else {
            jsonObject.put("result", "0");
            System.out.println("添加供应商业务员失败");
        }
        return jsonObject.toString();
    }
    //-----注册供应商-----

    //-----前往修改供应商信息页面-----
    @RequestMapping("/toUpdSupplier")
    public ModelAndView toUpdSupplier(Integer supplierId,
                                      HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Supplier supplier = supplierService.findSupplierById(supplierId);
        List<UserInfo> userInfos = userService.findAllSupplier();
        session.setAttribute("page_type", "修改供应商");
        mv.addObject("supplier", supplier);
        mv.addObject("userInfos", userInfos);
        mv.setViewName("emp/supplier/supplier_update");
        System.out.println(supplier.getSupplierName());
//        System.out.println(supplier.getUserId());
        System.out.println(supplier.getSupplierAddress());
        return mv;
    }
    //-----前往修改供应商信息页面-----

    //-----修改供应商信息-----
    @ResponseBody
    @RequestMapping("/updSupplier")
    public String updSupplier(Integer supplierId,
                              String supplierName,
                              Integer userId,
                              String supplierAddress) {
        JSONObject jsonObject = new JSONObject();
        Supplier supplier = supplierService.findSupplierById(supplierId);
        boolean flag = false;
        if (!supplierName.equals(supplier.getSupplierName()) ||
//                !userId.equals(supplier.getUserId()) ||
                !supplierAddress.equals(supplier.getSupplierAddress())) {
            flag = true;
        }
        if (true) {
            supplier.setSupplierName(supplierName);
//            supplier.setUserId(userId);
            supplier.setSupplierAddress(supplierAddress);
            Integer i = supplierService.updSupplier(supplier);
            if (i > 0) {
                System.out.println("修改成功");
                jsonObject.put("result","0");
            } else {
                System.out.println("修改失败");
                jsonObject.put("result","1");
            }
        } else {
            System.out.println("未修改");
            jsonObject.put("result","2");
        }
        return jsonObject.toString();
    }
    //-----修改供应商信息-----

    //-----删除供应商-----
    @RequestMapping("/delSupplier")
    public String delSupplier(Integer supplierId,
                              Integer pageNum) {
        supplierService.delSupplier(supplierId);
        return "redirect:/supplier/findAllSupplier?page=" + pageNum;
    }
    //-----删除供应商-----
}
