package cn.yyn.last_project.bean;

/**
 * @author 杨以诺
 * by 2020-09-22 15:55
 * 用户子订单表
 */
public class OrderSub {
    private String orderId;             //主订单id
    private Integer commodityId;        //子订单商品id
    private Commodity commodity;        //子订单商品对象
    private Integer commodityQuantity;  //子订单商品数量
    private Double totalPrice;          //子订单商品总价
    private Double totalProfit;         //子订单商品盈利总额

    public OrderSub() {
    }

    public OrderSub(String orderId, Integer commodityId, Commodity commodity, Integer commodityQuantity, Double totalPrice, Double totalProfit) {
        this.orderId = orderId;
        this.commodityId = commodityId;
        this.commodity = commodity;
        this.commodityQuantity = commodityQuantity;
        this.totalPrice = totalPrice;
        this.totalProfit = totalProfit;
    }

    @Override
    public String toString() {
        return "OrderSub{" +
                "orderId=" + orderId +
                ", commodityId=" + commodityId +
                ", commodity=" + commodity +
                ", commodityQuantity=" + commodityQuantity +
                ", totalPrice=" + totalPrice +
                ", totalProfit=" + totalProfit +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public Integer getCommodityQuantity() {
        return commodityQuantity;
    }

    public void setCommodityQuantity(Integer commodityQuantity) {
        this.commodityQuantity = commodityQuantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }
}
