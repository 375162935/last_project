package cn.yyn.last_project.bean;

/**
 * @author 杨以诺
 * by 2020-09-22 15:43
 * 商品种类
 */
public class CommodityType {
    private Integer commodityTypeId;    //商品类别id
    private String commodityTypeName;   //商品类别名称

    public CommodityType() {
    }

    public CommodityType(Integer commodityTypeId, String commodityTypeName) {
        this.commodityTypeId = commodityTypeId;
        this.commodityTypeName = commodityTypeName;
    }

    @Override
    public String toString() {
        return "CommodityType{" +
                "commodityTypeId=" + commodityTypeId +
                ", commodityTypeName='" + commodityTypeName + '\'' +
                '}';
    }

    public Integer getCommodityTypeId() {
        return commodityTypeId;
    }

    public void setCommodityTypeId(Integer commodityTypeId) {
        this.commodityTypeId = commodityTypeId;
    }

    public String getCommodityTypeName() {
        return commodityTypeName;
    }

    public void setCommodityTypeName(String commodityTypeName) {
        this.commodityTypeName = commodityTypeName;
    }
}
