package com.driver;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderRepository {
    HashMap<String,Order> orders=new HashMap<>();
    HashMap<String,DeliveryPartner>  partners=new HashMap<>();
    HashMap<Order,DeliveryPartner> ordersDel=new HashMap<>();
    HashMap<DeliveryPartner, List<Order>> deliveryOrders=new HashMap<>();

    public void addOrderToRepo(Order order){
        orders.put(order.getId(),order);
        ordersDel.put(order,null);
    }
    public void addPartnerToRepo(DeliveryPartner partner){
        partners.put(partner.getId(),partner);
        deliveryOrders.put(partner,new ArrayList<>());
    }
    public Order sendOrderFromRepo(String id){
        Order order=orders.get(id);
        return order;
    }
    public DeliveryPartner sendPartnerFromRepo(String id){
        DeliveryPartner partner=partners.get(id);
        return partner;
    }
    public void pairOrderAndPartner(String orderId,String partnerId){
        Order order=orders.get(orderId);
        DeliveryPartner partner=partners.get(partnerId);
        if(order!=null && partner!=null){
            ordersDel.put(order,partner);
            List<Order> listOfOrders=deliveryOrders.get(partner);
            if(!listOfOrders.contains(order)){
                listOfOrders.add(order);
            }
            deliveryOrders.put(partner,listOfOrders);
            partner.setNumberOfOrders(listOfOrders.size());
        }
    }
    public int getOrderCountByPartner(String partnerId){
        DeliveryPartner partner=partners.get(partnerId);
        if(partner!=null){
            int count=deliveryOrders.get(partner).size();
            return count;
        }
        return 0;
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        List<String> list=new ArrayList<>();
        DeliveryPartner partner=partners.get(partnerId);
        if(partner!=null){
            List<Order> orders=deliveryOrders.get(partner);
            for(int i=0;i<orders.size();i++){
                list.add(orders.get(i).getId());
            }
        }
        return list;
    }

    public List<String> getAllOrders(){
        List<String> list=new ArrayList<>();
        for(Map.Entry<String,Order> order:orders.entrySet()){
            list.add(order.getKey());
        }
        return list;
    }
    public int getCountOfUnassignedOrders(){
        int count=0;
        for(Map.Entry<Order,DeliveryPartner> order:ordersDel.entrySet()){
            if(order.getValue()==null){
                count++;
            }
        }
        return count;
    }

    public void deletePartnerById(String partnerId){
        DeliveryPartner partner=partners.get(partnerId);
        if(partner!=null){
            partners.remove(partnerId);
            List<Order> list=deliveryOrders.get(partner);
            deliveryOrders.remove(partner);
            for(int i=0;i<list.size();i++){
                Order o=list.get(i);
                ordersDel.put(o,null);
            }
        }
    }

    public void deleteOrderById(String orderId){
        Order order=orders.get(orderId);
        if(order!=null){
            orders.remove(orderId);
            DeliveryPartner partner=ordersDel.get(order);
            ordersDel.remove(order);
            List<Order> list=deliveryOrders.get(partner);
            list.remove(order);
            deliveryOrders.put(partner,list);
            partner.setNumberOfOrders(list.size());
        }
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        String time="";
        DeliveryPartner partner=partners.get(partnerId);
        List<Order> orderList=deliveryOrders.get(partner);
        int maxTime=0;

        for(int i=0;i<orderList.size();i++){
            int currOrderTime=orderList.get(i).getDeliveryTime();
            if(currOrderTime>maxTime){
                maxTime=currOrderTime;
            }
        }
        int mins=maxTime%60;
        int hours=maxTime/60;
        if(hours<10){
            time+="0"+hours+":";
        }else{
            time+=hours+":";
        }
        if(mins<10){
            time+="0"+mins;
        }else{
            time+=mins;
        }
        return time;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId){
        int count=0;
        int hours=(Integer.valueOf(time.charAt(0))-48)*10+(Integer.valueOf(time.charAt(1))-48);
        int minutes=(Integer.valueOf(time.charAt(3))-48)*10+(Integer.valueOf(time.charAt(4))-48);
        int timeTarget=(hours*60)+minutes;
        DeliveryPartner partner=partners.get(partnerId);
        List<Order> orders=deliveryOrders.get(partner);
        for(int i=0;i<orders.size();i++){
            int currOrderTime=orders.get(i).getDeliveryTime();
            if(currOrderTime<=timeTarget){
                count++;
            }
        }
        return count;
    }


}
