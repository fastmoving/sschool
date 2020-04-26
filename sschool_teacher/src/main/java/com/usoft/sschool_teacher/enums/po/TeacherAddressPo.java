package com.usoft.sschool_teacher.enums.po;

import javax.validation.constraints.NotNull;

/**
 * @author cq
 * @date 2020/4/26 8:53
 */
public class TeacherAddressPo {
    /**
     * id
     */
    private Integer id;

    /**
     * 教师ID
     */
    private String teacherId;

    /**
     * 联系人
     */
    @NotNull(message = "联系人不能为空")
    private String linkMan;

    /**
     * 联系电话
     */
    @NotNull(message = "联系电话不能为空")
    private String phone;

    /**
     * 是否设置默认
     */
    @NotNull(message = "是否为默认地址，1.是；2.不是，默认2")
    private String isDefault;

    /**
     * 省份
     */
    @NotNull(message = "省份不能为空")
    private String province;

    /**
     * 城市
     */
    @NotNull(message = "城市不能为空")
    private String city;

    /**
     * 区县
     */
    @NotNull(message = "区县不能为空")
    private String dist;

    /**
     * 详细地址
     */
    @NotNull(message = "详细地址不能为空")
    private String address;

    /**
     * 城市编码
     */
    private String cityCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TeacherAddressPo() {
    }

    public TeacherAddressPo(String teacherId, @NotNull(message = "联系人不能为空") String linkMan,
                            @NotNull(message = "联系电话不能为空") String phone, @NotNull(message = "是否为默认地址，1.是；2.不是，默认2") String isDefault,
                            @NotNull(message = "省份不能为空") String province, @NotNull(message = "城市不能为空") String city,
                            @NotNull(message = "区县不能为空") String dist, @NotNull(message = "详细地址不能为空") String address, String cityCode,Integer id) {
        this.teacherId = teacherId;
        this.linkMan = linkMan;
        this.phone = phone;
        this.isDefault = isDefault;
        this.province = province;
        this.city = city;
        this.dist = dist;
        this.address = address;
        this.cityCode = cityCode;
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
