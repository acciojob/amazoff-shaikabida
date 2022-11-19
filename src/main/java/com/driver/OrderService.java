package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public void sendOrderToRepo(Order order){
        orderRepository.addOrderToRepo(order);
    }

    public void sendPartnerToRepo(String partnerId){
        DeliveryPartner partner=new DeliveryPartner(partnerId);
        orderRepository.addPartnerToRepo(partner);
    }

    public Order getOrderFromRepo(String orderId){
        Order order=orderRepository.sendOrderFromRepo(orderId);
        return order;
    }
    public DeliveryPartner getPartnerFromRepo(String partnerId){
        DeliveryPartner partner=orderRepository.sendPartnerFromRepo(partnerId);
        return partner;
    }
    public void pairOrderAndPartner(String orderId,String partnerId){
        orderRepository.pairOrderAndPartner(orderId,partnerId);
    }
    public int getOrderCountByPartner(String partnerId){
        int count=orderRepository.getOrderCountByPartner(partnerId);
        return count;
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        List<String> list=orderRepository.getOrdersByPartnerId(partnerId);
        return list;
    }
    public List<String> getAllOrders(){
        List<String> list=orderRepository.getAllOrders();
        return list;
    }
    public int getCountOfUnassignedOrders(){
        int count=orderRepository.getCountOfUnassignedOrders();
        return count;
    }

    public void deletePartnerById(String partnerId){
        orderRepository.deletePartnerById(partnerId);
    }
    public void deleteOrderById(String orderId){
        orderRepository.deleteOrderById(orderId);
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        String time=orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
        return time;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId){
        int count=orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
        return count;
    }

}
