package cn.yyn.last_project.controller;

import cn.yyn.last_project.bean.*;
import cn.yyn.last_project.service.CommodityService;
import cn.yyn.last_project.service.SupplierService;
import cn.yyn.last_project.utils.QiniuUtils;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨以诺
 * by 2020-09-28 11:20
 */
@Controller
@RequestMapping("/commodity")
public class CommodityController {
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private SupplierService supplierService;

    private List<Commodity> commodities;

    public List<Commodity> findAll() {
        return commodityService.findAllCommodity();
    }

    /**
     * 商品查询功能
     */
    //-----查询部分商品-----
    @ResponseBody
    @RequestMapping("/findCommodityByName")
    public String findCommodityByName(String keyword,
                                      HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        System.out.println(keyword);
        session.setAttribute("keyword", keyword);
        jsonObject.put("result", "0");
        if (keyword.trim().length() == 0 || keyword.trim() == null || keyword.trim().equals("")) {
            if (session.getAttribute("find_commodity") != null) {
                jsonObject.put("result", "1");
            }
        } else {
            List<Commodity> commodityList = new ArrayList<Commodity>();
            for (Commodity commodity : commodities) {
                int i = commodity.getCommodityName().indexOf(keyword.trim());
                if (i != -1) {
                    commodityList.add(commodity);
                }
            }
            session.setAttribute("find_commodity", commodityList);
            if (commodityList.size() > 0) {
                jsonObject.put("result", "2");
            } else {
                jsonObject.put("result", "1");
            }
        }
        return jsonObject.toString();
    }
    //-----查询部分商品-----

    //-----跳转搜索结果-----
    @RequestMapping("/toCommodityFind")
    public String toCommodityFind(HttpSession session) {
        session.setAttribute("page_type", "搜索商品");
        return "emp/commodity/commodity_find";
    }
    //-----跳转搜索结果-----

    //-----用户搜索商品-----
    @RequestMapping("/findCommodity")
    public ModelAndView findCommodity(String keyword) {
        System.out.println(keyword);
        ModelAndView mv = new ModelAndView();
        List<Commodity> commodityList = commodityService.findCommodityByKeyword(keyword);
        mv.addObject("commodityList", commodityList);
        mv.addObject("keyword", keyword);
        mv.setViewName("front/front_commodity_find");
        return mv;
    }
    //-----用户搜索商品-----

    //-----查看指定商品信息-----
    @RequestMapping("/findCommodityById")
    public ModelAndView findCommodityById(Integer commodityId,
                                          HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Commodity commodity = commodityService.findCommodityById(commodityId);
        mv.addObject("commodity", commodity);
        mv.setViewName("emp/commodity/commodity_detail");
        session.setAttribute("page_type", "商品详情");
        return mv;
    }
    //-----查看指定商品信息-----

    //-----查询所有商品-----
    @RequestMapping("/findAllCommodity")
    public ModelAndView findAllCommodity(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "15") Integer size,
                                         HttpSession session) {
        commodities = findAll();
        session.removeAttribute("find_commodity");
        ModelAndView mv = new ModelAndView();
        List<Commodity> commodityList = commodityService.findAllCommodity(page, size);
        PageInfo<Commodity> pageInfo = new PageInfo<Commodity>(commodityList);
        mv.addObject("pageInfo", pageInfo);
        mv.addObject("commodityList", commodityList);
        String user_type = (String) session.getAttribute("user_type");
        if (user_type.equals("2") || user_type.equals("3")) {
            //后端商品页
            mv.setViewName("emp/commodity/commodity_all");
        } else {
            //前端商品页
            mv.addObject("commodityType", "全部商品");
            mv.addObject("type", 0);
            mv.setViewName("/front/front_commodity_all");
        }
        session.setAttribute("page_type", "所有商品");
        return mv;
    }
    //-----查询所有商品-----

    //-----根据商品类型查商品-----
    @RequestMapping("/findCommodityByType")
    public ModelAndView findCommodityByType(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "15") Integer size,
                                            Integer commodityTypeId,
                                            String typeName,
                                            HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String user_type = (String) session.getAttribute("user_type");
        List<Commodity> commodityList = commodityService.findCommodityByType(commodityTypeId);
        mv.addObject("commodityList", commodityList);
        if (user_type.equals("2") || user_type.equals("3")) {
            session.setAttribute("page_type", typeName);
            mv.setViewName("emp/shop/shop_commodity_type");
        } else {
            mv.addObject("commodityType", typeName);
            mv.addObject("pageType", commodityTypeId);
            mv.setViewName("/front/front_commodity_all");
        }
        return mv;
    }
    //-----根据商品类型查商品-----

    //-----根据公司查商品-----
    @RequestMapping("/findCommodityBySupplier")
    public ModelAndView findCommodityBySupplier(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "15") Integer size,
                                                Integer supplierId) {
        ModelAndView mv = new ModelAndView();
        Supplier supplier = supplierService.findSupplierById(supplierId);
        List<Supplier> supplierList = supplierService.findAllSupplier();
        List<Commodity> commodityList = commodityService.findCommodityBySupplier(supplierId);
        mv.addObject("commodityList", commodityList);//供应商商品列表
        mv.addObject("supplierList", supplierList);//供应商列表
        mv.addObject("supplier", supplier);//当前供应商信息
        mv.setViewName("/front/front_commodity_supplier");
        return mv;
    }
    //-----根据公司查商品-----

    /**
     * 商品添加功能
     */
    //-----跳转添加商品页面-----
    @RequestMapping("/toAddCommodity")
    public ModelAndView toAddCommodity(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        List<CommodityType> commodityTypeList = commodityService.findAllCommodityType();
        List<Supplier> supplierList = supplierService.findAllSupplier();
        mv.addObject("commodityTypeList", commodityTypeList);
        mv.addObject("supplierList", supplierList);
        mv.setViewName("emp/commodity/commodity_add");
        session.setAttribute("page_type", "添加商品");
        return mv;
    }
    //-----跳转添加商品页面-----

    //-----检查商品名称-----
    @ResponseBody
    @RequestMapping("/commodityName")
    public String findCommodityName(String commodityName) {
        JSONObject jsonObject = new JSONObject();
        Commodity commodity = commodityService.findCommodityByName(commodityName);
        if (commodity == null) {
            jsonObject.put("result", "1");
        } else {
            jsonObject.put("result", "0");
        }
        return jsonObject.toString();
    }
    //-----检查商品名称-----

    //-----添加商品-----
    @RequestMapping("/addCommodity")
    public String addCommodity(String commodityName,
                               @RequestParam(value = "upload", required = false)
                                       MultipartFile upload,
                               String commodityParameter,
                               Integer commodityTypeId,
                               String commodityPurchase,
                               String commoditySell,
                               String commodityStock,
                               Integer supplierId) throws IOException {
//        File file = QiniuUtils.getNewFile(upload);
//        String img_upload = file.toString();
        String img_upload = QiniuUtils.loadQiNiu(upload);
        System.out.println(img_upload);
        if (img_upload != null) {
            Commodity commodity = new Commodity();
            commodity.setCommodityName(commodityName);
            commodity.setCommodityParameter(commodityParameter);
            commodity.setCommodityTypeId(commodityTypeId);
            commodity.setCommodityPurchase(new Double(commodityPurchase));
            commodity.setCommoditySell(new Double(commoditySell));
            commodity.setCommodityStock(new Integer(commodityStock));
            commodity.setSupplierId(supplierId);
            commodity.setCommodityImg(img_upload);
            Integer i = commodityService.addCommodity(commodity);
            if (i > 0) {
                System.out.println("商品添加成功");
            } else {
                System.out.println("商品添加失败");
            }
        }
        return "redirect:/commodity/findAllCommodity";
    }
    //-----添加商品-----

    /**
     * 商品删除功能
     */
    //-----删除商品-----
    @RequestMapping("/delCommodity")
    public String delCommodity(Integer commodityId,
                               Integer pageNum) {
        Commodity commodity = commodityService.findCommodityById(commodityId);
        String img_load = commodity.getCommodityImg();
        QiniuUtils.delImgQiNiu(img_load);
        commodityService.delCommodity(commodityId);
        return "redirect:/commodity/findAllCommodity?page=" + pageNum;
    }
    //-----删除商品-----

    /**
     * 商品信息修改功能
     */
    //-----跳转商品信息修改页面-----
    @RequestMapping("/toUpdCommodity")
    public ModelAndView toUpdCommodity(Integer commodityId,
                                       HttpSession session) {
        ModelAndView mv = new ModelAndView();
        List<CommodityType> commodityTypeList = commodityService.findAllCommodityType();
        List<Supplier> supplierList = supplierService.findAllSupplier();
        Commodity commodity = commodityService.findCommodityById(commodityId);
        mv.addObject("commodityTypeList", commodityTypeList);
        mv.addObject("supplierList", supplierList);
        mv.addObject("commodity", commodity);
        mv.setViewName("emp/commodity/commodity_update");
        session.setAttribute("page_type", "修改商品信息");
        return mv;
    }
    //-----跳转商品信息修改页面-----

    //-----修改商品信息-----
    @RequestMapping("/updCommodity")
    public String updCommodity(Integer commodityId,
                               @RequestParam(value = "upload", required = false)
                                       MultipartFile upload,
                               String commodityName,
                               String commodityParameter,
                               Integer commodityTypeId,
                               String commodityPurchase,
                               String commoditySell,
                               String commodityStock,
                               Integer supplierId) throws IOException {
        Commodity commodity = commodityService.findCommodityById(commodityId);
        String img_upload = "";
        if (upload.getSize() > 0 || commodity.getCommodityImg() != null) {
            //有图片时
            if (upload.getSize() == 0) {
                img_upload = commodity.getCommodityImg();
            } else if (upload.getSize() > 0) {
                if (commodity.getCommodityImg() != null && !commodity.getCommodityImg().equals("")) {
                    String old_key = commodity.getCommodityImg();
                    System.out.println(old_key);
                    //删除七牛云旧的图片
                    QiniuUtils.delImgQiNiu(old_key);
                }
                //保存图片到七牛云
                img_upload = QiniuUtils.loadQiNiu(upload);
            }
            System.out.println(img_upload);
            if (img_upload != null) {
                commodity.setCommodityName(commodityName);
                commodity.setCommodityParameter(commodityParameter);
                commodity.setCommodityTypeId(commodityTypeId);
                commodity.setCommodityPurchase(new Double(commodityPurchase));
                commodity.setCommoditySell(new Double(commoditySell));
                commodity.setCommodityStock(new Integer(commodityStock));
                commodity.setSupplierId(supplierId);
                commodity.setCommodityImg(img_upload);
                System.out.println(commodity);
                Integer i = commodityService.updCommodity(commodity);
                if (i > 0) {
                    System.out.println("修改成功");
                } else {
                    System.out.println("修改失败");
                }
            } else {
                System.out.println("图片上传失败");
            }
            return "redirect:/commodity/findAllCommodity";
        } else {
            return "redirect:/commodity/toUpdCommodity?commodityId=" + commodityId;
        }

    }
    //-----修改商品信息-----

    /**
     * 商品类别管理
     */
    //-----商品类别查询-----
    @RequestMapping("/findAllCommodityType")
    public ModelAndView findAllCommodityType(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer size,
                                             HttpSession session) {
        ModelAndView mv = new ModelAndView();
        List<CommodityType> commodityTypeList = commodityService.findAllCommodityType(page, size);
        PageInfo<CommodityType> pageInfo = new PageInfo<>(commodityTypeList);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("emp/commodity_type/commodity_type_all");
        return mv;
    }
    //-----商品类别查询-----

    //-----商品类别添加-----
    @ResponseBody
    @RequestMapping("/addCommodityType")
    public String addCommodityType(String commodityTypeName) {
        JSONObject jsonObject = new JSONObject();
        List<CommodityType> commodityTypeList = commodityService.findAllCommodityType();
        boolean flag = true;
        for (CommodityType c : commodityTypeList) {
            if (c.getCommodityTypeName().equals(commodityTypeName)) {
                flag = false;
            }
        }
        if (flag) {
            CommodityType commodityType = new CommodityType();
            commodityType.setCommodityTypeName(commodityTypeName);
            Integer i = commodityService.addCommodityType(commodityType);
            if (i > 0) {
                jsonObject.put("result", "2");
            } else {
                jsonObject.put("result", "1");
            }
        } else {
            jsonObject.put("result", "0");
        }
        return jsonObject.toString();
    }
    //-----商品类别添加-----

    //-----商品类别编辑-----
    @ResponseBody
    @RequestMapping("/updCommodityType")
    public String updCommodityType(Integer commodityTypeId,
                                   String commodityTypeName,
                                   HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        CommodityType commodityType = new CommodityType(commodityTypeId, commodityTypeName);
        Integer i = commodityService.updCommodityType(commodityType);
        if (i > 0) {
            jsonObject.put("result", "1");
            List<CommodityType> commodityTypes = commodityService.findAllCommodityType();
            session.setAttribute("commodityTypes", commodityTypes);
        } else {
            jsonObject.put("result", "0");
        }
        return jsonObject.toString();
    }
    //-----商品类别编辑-----

    //-----商品类别删除-----
    @RequestMapping("/delCommodityType")
    public String delCommodityType(Integer commodityTypeId) {
        commodityService.delCommodityType(commodityTypeId);
        return "redirect:/commodity/findAllCommodityType";
    }
    //-----商品类别删除-----
}
