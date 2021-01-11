package com.jds.service;

import com.jds.dao.repository.OrderDAO;
import com.jds.dao.entity.DoorEntity;
import com.jds.dao.entity.DoorOrder;
import com.jds.dao.entity.UserEntity;
import com.jds.model.BackResponse.OrderResponse;
import com.jds.model.PrintAppToTheOrder;
import com.jds.model.modelEnum.OrderStatus;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderService {

    @Autowired
    private OrderDAO dAO;

    @Autowired
    private UserService userService;

    @Autowired
    OrderDiscountService orderDiscountService;


    public List<DoorOrder> getOrders() {

        UserEntity user = userService.getCurrentUser();
        List<DoorOrder> orders;

        if (user.isAdmin()){
            orders = dAO.getOrders();
        }
        else {
            orders = dAO.getOrdersByUser(user);
        }

        orders.stream()
                .peek((order)->clearNonSerializingFields(order))
                .collect(Collectors.toList());

        return orders;
    }

    public List<DoorOrder> getOrders(OrderStatus status) {

        UserEntity user = userService.getCurrentUser();
        List<DoorOrder> orders;


        if (user.isAdmin() && status!=null){
            orders = dAO.getOrdersByStatus(status.name());
        }
        else {
            orders = dAO.getOrdersByUser(user);
        }

        orders.stream()
                .peek((order)->clearNonSerializingFields(order))
                .collect(Collectors.toList());

        return orders;
    }

    public List<DoorOrder> getOrders(@NonNull String userId) {

        UserEntity user = userService.getUser(userId);
        List<DoorOrder> orders;

        if (user!=null){
            orders = dAO.getOrdersByUser(user);
            orders.stream()
                    .peek((order)->clearNonSerializingFields(order))
                    .collect(Collectors.toList());
        }
        else {
            orders = new ArrayList<>();
        }

        return orders;
    }

    public DoorOrder getOrder(String id) {

        int intId = Integer.parseInt(id);
        return getOrder(intId);

    }

    public DoorOrder getOrder(int id) {

        if (id == 0) {
            return new DoorOrder();
        }
        DoorOrder order =  dAO.getOrder(id);
        order.setStatusList(OrderStatus.statusList(order.getStatus()));

        return clearNonSerializingFields(order);
    }

    public DoorOrder checkStatusAndSave(@NonNull DoorOrder order){
        OrderStatus baseOrderStatus = statusOrderBaseByOrderId(order.getOrderId());
        if(OrderStatus.CALC == baseOrderStatus || OrderStatus.READY == baseOrderStatus){
            order.setSeller(userService.getCurrentUser());
                return saveAndCalc(order);
        }
        else {
            return null;
        }
    }

    public OrderResponse checkAccessAndSave(@NonNull DoorOrder order){
        if ("admin".equals(userService.getCurrentUser().getUsername())){
            return new OrderResponse(false, "!save is not available for admin");
        }else {
            return new OrderResponse(checkStatusAndSave(order));
        }
    }

    private DoorOrder saveAndCalc(@NonNull DoorOrder order) {

        order.calculateTotal(
                userService.getUserSetting(),
                orderDiscountService.getOrderDiscounts(String.valueOf(order.getOrderId())));
        return dAO.saveOrder(order);
    }

    public String deleteOrder(String orderId) {
        DoorOrder order = dAO.getOrder(Integer.parseInt(orderId));
        dAO.deleteOrder(order);
        orderDiscountService.deleteOrderDiscountByOrderId(orderId);
        return String.valueOf(order.getOrderId());
    }

    public static DoorOrder clearNonSerializingFields(DoorOrder order) {

        //order.getSeller().setOrders(null);

        List<DoorEntity> doors = order.getDoors();
        for (DoorEntity door : doors) {
            door.clearNonSerializingFields();
        }
        return order;
    }

    public List<PrintAppToTheOrder> getPrintAppList(String orderId) {

        DoorOrder order = dAO.getOrder(Integer.parseInt(orderId));
        List<DoorEntity> doors = order.getDoors();

        List<PrintAppToTheOrder> printAppList = new ArrayList<>();
        for (DoorEntity door : doors) {
            door.clearNonSerializingFields();
            printAppList.add(new PrintAppToTheOrder(door, order));
        }

        return printAppList;

    }

    public void setStatusAndSaveOrder(@NonNull int id, @NonNull String status, Date releasDate){

       OrderStatus orderStatus = validationOrderStatus(status);
       if (orderStatus!=null){
           DoorOrder order = dAO.getOrder(id);
           order.setStatus(orderStatus);
           order.setReleasDate(releasDate);
           save(order);
       }
    }

    private DoorOrder save(DoorOrder order) {
        return dAO.saveOrder(order);
    }

    private OrderStatus validationOrderStatus(@NonNull String status){
        if ("IN_WORK".equals(status)){
            return OrderStatus.IN_WORK;
        }
        else if ("READY".equals(status)){
            return OrderStatus.READY;
        }
        return null;
    }

    private OrderStatus statusOrderBaseByOrderId(int id){
        if (id>0) {
            DoorOrder orderInBase = dAO.getOrder(id);
            return orderInBase.getStatus();
        }else {
            return OrderStatus.CALC;
        }
    }
}
