package cn.yyn.last_project.bean;

/**
 * @author 杨以诺
 * by 2020-09-22 10:27
 * 供应商
 */
public class Supplier {
    private Integer supplierId;         //供应商id
    private String supplierName;        //供应商名称
    private String supplierAddress;     //供应商地址
    private String supplierCEO;         //供应商董事长名称
    private String supplierPhone;       //供应商客服电话
    private String supplierCountry;     //供应商所属国家
    private Integer userId;             //供应商对应业务员id
    private UserInfo user;              //供应商对应业务员

    public Supplier() {
    }

    public Supplier(Integer supplierId,
                    String supplierName,
                    String supplierAddress,
                    String supplierCEO,
                    String supplierPhone,
                    String supplierCountry,
                    Integer userId,
                    UserInfo user) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierCEO = supplierCEO;
        this.supplierPhone = supplierPhone;
        this.supplierCountry = supplierCountry;
        this.userId = userId;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", supplierAddress='" + supplierAddress + '\'' +
                ", supplierCEO='" + supplierCEO + '\'' +
                ", supplierPhone='" + supplierPhone + '\'' +
                ", supplierCountry='" + supplierCountry + '\'' +
                ", userId='" + userId + '\'' +
                ", user='" + user + '\'' +
                '}';
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierCEO() {
        return supplierCEO;
    }

    public void setSupplierCEO(String supplierCEO) {
        this.supplierCEO = supplierCEO;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierCountry() {
        return supplierCountry;
    }

    public void setSupplierCountry(String supplierCountry) {
        this.supplierCountry = supplierCountry;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
