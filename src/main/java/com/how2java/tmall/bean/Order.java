package com.how2java.tmall.bean;

import com.how2java.tmall.service.OrderService;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
public class Order {
    @Setter private Integer id;
    private String orderCode;
    private String address;
    private String post;
    private String receiver;
    private String mobile;
    private String userMessage;
    @Setter private Date createDate;
    @Setter private Date payDate;
    @Setter private Date deliveryDate;
    @Setter private Date confirmDate;
    @Setter private Integer uid;
    private String status;
    @Setter private List<OrderItem> orderItems;
    @Setter private float total;
    @Setter private int totalNumber;
    @Setter private User user;

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage == null ? null : userMessage.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getStatusDesc(){
        String desc;
        switch(status){
            case OrderService.waitPay:
                desc="待付款";
                break;
            case OrderService.waitDelivery:
                desc="待发货";
                break;
            case OrderService.waitConfirm:
                desc="待收货";
                break;
            case OrderService.waitReview:
                desc="等评价";
                break;
            case OrderService.finish:
                desc="完成";
                break;
            case OrderService.delete:
                desc="刪除";
                break;
            default:
                desc="未知";
        }
        return desc;
    }
}