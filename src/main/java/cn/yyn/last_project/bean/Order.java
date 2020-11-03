package cn.yyn.last_project.bean;

import java.util.Date;
import java.util.List;

/**
 * @author 杨以诺
 * by 2020-09-22 15:52
 * 用户主订单
 */
public class Order {
    private String orderId;                 //订单编号
    private Double orderPrice;              //订单总价
    private Date orderCreateTime;           //订单创建时间
    private Double orderProfit;             //订单盈利
    private List<OrderSub> subOrderList;    //子订单对象集
    private Integer userId;                 //创建订单用户id
    private UserInfo userInfo;              //创建订单用户对象
    private Integer orderYear;              //订单年
    private Integer orderMonth;             //订单月
    private Integer orderDay;               //订单日
    private Integer orderWeek;              //订单第几周(全年)
    private Integer orderWeekDay;           //订单周几
    private Integer allNum;                 //订单总商品数

    public Order() {
    }

    public Order(String orderId, Double orderPrice, Date orderCreateTime, Double orderProfit, List<OrderSub> subOrderList, Integer userId, UserInfo userInfo, Integer orderYear, Integer orderMonth, Integer orderDay, Integer orderWeek, Integer orderWeekDay, Integer allNum) {
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.orderCreateTime = orderCreateTime;
        this.orderProfit = orderProfit;
        this.subOrderList = subOrderList;
        this.userId = userId;
        this.userInfo = userInfo;
        this.orderYear = orderYear;
        this.orderMonth = orderMonth;
        this.orderDay = orderDay;
        this.orderWeek = orderWeek;
        this.orderWeekDay = orderWeekDay;
        this.allNum = allNum;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderPrice=" + orderPrice +
                ", orderCreateTime=" + orderCreateTime +
                ", orderProfit=" + orderProfit +
                ", subOrderList=" + subOrderList +
                ", userId=" + userId +
                ", userInfo=" + userInfo +
                ", orderYear=" + orderYear +
                ", orderMonth=" + orderMonth +
                ", orderDay=" + orderDay +
                ", orderWeek=" + orderWeek +
                ", orderWeekDay=" + orderWeekDay +
                ", allNum=" + allNum +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public Double getOrderProfit() {
        return orderProfit;
    }

    public void setOrderProfit(Double orderProfit) {
        this.orderProfit = orderProfit;
    }

    public List<OrderSub> getSubOrderList() {
        return subOrderList;
    }

    public void setSubOrderList(List<OrderSub> subOrderList) {
        this.subOrderList = subOrderList;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Integer getOrderYear() {
        return orderYear;
    }

    public void setOrderYear(Integer orderYear) {
        this.orderYear = orderYear;
    }

    public Integer getOrderMonth() {
        return orderMonth;
    }

    public void setOrderMonth(Integer orderMonth) {
        this.orderMonth = orderMonth;
    }

    public Integer getOrderDay() {
        return orderDay;
    }

    public void setOrderDay(Integer orderDay) {
        this.orderDay = orderDay;
    }

    public Integer getOrderWeek() {
        return orderWeek;
    }

    public void setOrderWeek(Integer orderWeek) {
        this.orderWeek = orderWeek;
    }

    public Integer getOrderWeekDay() {
        return orderWeekDay;
    }

    public void setOrderWeekDay(Integer orderWeekDay) {
        this.orderWeekDay = orderWeekDay;
    }

    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }
}
