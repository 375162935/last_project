package cn.yyn.last_project.bean;

import java.util.Date;

/**
 * @author 杨以诺
 * by 2020-09-21 11:55
 * 用户
 */
public class UserInfo {
    private Integer userId;         //用户id
    private String username;        //用户账号
    private String password;        //用户密码
    private String name;            //用户姓名
    private String gender;          //性别
    private Date birthday;          //用户生日
    private Double integral;        //用户积分
    private String userType;        //用户类型
    private String userPhone;       //用户电话
    private String userImg;         //用户头像

    public UserInfo() {
    }

    public UserInfo(Integer userId, String username, String password, String name, String gender, Date birthday, Double integral, String userType, String userPhone, String userImg) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.integral = integral;
        this.userType = userType;
        this.userPhone = userPhone;
        this.userImg = userImg;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", integral=" + integral +
                ", userType=" + userType +
                ", userPhone='" + userPhone + '\'' +
                ", userImg='" + userImg + '\'' +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Double getIntegral() {
        return integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
