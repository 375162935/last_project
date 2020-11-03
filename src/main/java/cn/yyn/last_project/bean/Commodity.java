package cn.yyn.last_project.bean;

/**
 * @author 杨以诺
 * by 2020-09-22 10:39
 * 商品
 */
public class Commodity {
    private Integer commodityId;             //商品编号
    private String commodityName;            //商品名称
    private String commodityParameter;       //商品参数
    private Double commodityPurchase;        //商品进价
    private Double commoditySell;            //商品售价
    private Integer commodityStock;          //商品库存
    private Integer commodityTypeId;         //商品类型id
    private CommodityType commodityType;     //商品类型对象
    private Integer supplierId;              //供应商id
    private Supplier supplier;               //供应商对象
    private String commodityImg;             //商品图片地址

    public Commodity() {
    }

    public Commodity(Integer commodityId, String commodityName, String commodityParameter, Double commodityPurchase, Double commoditySell, Integer commodityStock, Integer commodityTypeId, CommodityType commodityType, Integer supplierId, Supplier supplier,String commodityImg) {
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.commodityParameter = commodityParameter;
        this.commodityPurchase = commodityPurchase;
        this.commoditySell = commoditySell;
        this.commodityStock = commodityStock;
        this.commodityTypeId = commodityTypeId;
        this.commodityType = commodityType;
        this.supplierId = supplierId;
        this.supplier = supplier;
        this.commodityImg = commodityImg;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "commodityId=" + commodityId +
                ", commodityName='" + commodityName + '\'' +
                ", commodityParameter='" + commodityParameter + '\'' +
                ", commodityPurchase=" + commodityPurchase +
                ", commoditySell=" + commoditySell +
                ", commodityStock=" + commodityStock +
                ", commodityTypeId=" + commodityTypeId +
                ", commodityType=" + commodityType +
                ", supplierId=" + supplierId +
                ", supplier=" + supplier +
                ", commodityImg=" + commodityImg +
                '}';
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityParameter() {
        return commodityParameter;
    }

    public void setCommodityParameter(String commodityParameter) {
        this.commodityParameter = commodityParameter;
    }

    public Double getCommodityPurchase() {
        return commodityPurchase;
    }

    public void setCommodityPurchase(Double commodityPurchase) {
        this.commodityPurchase = commodityPurchase;
    }

    public Double getCommoditySell() {
        return commoditySell;
    }

    public void setCommoditySell(Double commoditySell) {
        this.commoditySell = commoditySell;
    }

    public Integer getCommodityStock() {
        return commodityStock;
    }

    public void setCommodityStock(Integer commodityStock) {
        this.commodityStock = commodityStock;
    }

    public Integer getCommodityTypeId() {
        return commodityTypeId;
    }

    public void setCommodityTypeId(Integer commodityTypeId) {
        this.commodityTypeId = commodityTypeId;
    }

    public CommodityType getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(CommodityType commodityType) {
        this.commodityType = commodityType;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getCommodityImg() {
        return commodityImg;
    }

    public void setCommodityImg(String commodityImg) {
        this.commodityImg = commodityImg;
    }
}
