package cn.yyn.last_project.controller;

//import com.alipay.sdk.app.EnvUtils;

import cn.yyn.last_project.bean.Order;
import cn.yyn.last_project.bean.OrderSub;
import cn.yyn.last_project.bean.UserInfo;
import cn.yyn.last_project.config.AliPayConfig;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 杨以诺
 * by 2020-10-19 16:06
 */
@Controller
@RequestMapping("/Alipay")
public class AliPayController {
    /**
     * 结算界面
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/PayPage")
    public String payController(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient =
                new DefaultAlipayClient(AliPayConfig.gatewayUrl,
                        AliPayConfig.APP_ID,
                        AliPayConfig.APP_PRIVATE_KEY,
                        "json",
                        AliPayConfig.CHARSET,
                        AliPayConfig.ALIPAY_PUBLIC_KEY,
                        AliPayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AliPayConfig.return_url);
        alipayRequest.setNotifyUrl(AliPayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填

        String out_trade_no = request.getParameter("Order");
        //付款金额，必填  ShopName
        String total_amount = request.getParameter("Money");
        //订单名称，必填
        String subject = request.getParameter("Name");
        //商品描述，可空
        String body = request.getParameter("购物测试");
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1c";
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + timeout_express + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String url = "";
        try {
            url = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
               /* response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
                response.getWriter().write(url); // 直接将完整的表单html输出到页面
                response.getWriter().flush();
                response.getWriter().close();*/
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.println(url);
        return url;
    }

    @ResponseBody
    @RequestMapping(value = "/AliPay")
    public String ZFBPay(HttpSession session) throws IOException {
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        Order order = (Order) session.getAttribute("order");
        //获得初始化的AlipayClient
        AlipayClient alipayClient =
                new DefaultAlipayClient(AliPayConfig.gatewayUrl,
                        AliPayConfig.APP_ID,
                        AliPayConfig.APP_PRIVATE_KEY,
                        "json",
                        AliPayConfig.CHARSET,
                        AliPayConfig.ALIPAY_PUBLIC_KEY,
                        AliPayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AliPayConfig.return_url);//传入结束后异步跳转的页面
        alipayRequest.setNotifyUrl(AliPayConfig.notify_url);//传入结束后同步跳转的页面
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getOrderId();
        //付款金额，必填  ShopName
        String total_amount = String.valueOf(order.getOrderPrice());
        //订单名称，必填
        String subject =userInfo.getName()+"的订单";
        //商品描述，可空
        String body = "";
        for (OrderSub orderSub : order.getSubOrderList()) {
            body += orderSub.getCommodity().getCommodityName() + ","+
                    orderSub.getCommodity().getCommodityParameter() + ",";
        }
        body=body.substring(0,body.length()-1);//去字符串尾的字符
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1c";
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + timeout_express + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String url = "";
        try {
            url = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
               /* response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
                response.getWriter().write(url); // 直接将完整的表单html输出到页面
                response.getWriter().flush();
                response.getWriter().close();*/
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.println(url);
        return url;
    }

    /**
     * 同步跳转
     *
     * @param request
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/returnUrl")
    public ModelAndView returnUrl(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();

        // 获取支付宝GET过来反馈信息（官方固定代码）
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.CHARSET, AliPayConfig.sign_type); // 调用SDK验证签名

        // 返回界面
        if (signVerified) {
            System.out.println("前往支付成功页面");
            mav.setViewName("redirect:/order/payOrder?payType=2");
        } else {
            System.out.println("前往支付失败页面");
            mav.setViewName("login");
        }
        return mav;
    }

    /**
     * 支付宝服务器异步通知
     *
     * @param request
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/notifyUrl")
    public void notifyUrl(HttpServletRequest request) throws Exception {
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.CHARSET, AliPayConfig.sign_type); // 调用SDK验证签名

        System.out.println("进入异步");
        if (signVerified) { // 验证成功 更新订单信息
            System.out.println("异步通知成功");
            // 商户订单号
            String out_trade_no = request.getParameter("out_trade_no");
            // 交易状态
            String trade_status = request.getParameter("trade_status");
            // 修改数据库
        } else {
            System.out.println("异步通知失败");
        }
    }
}
