package com.zhaofujun.nest.demo.infrastructure.repository;

import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.core.EntityFactory;
import com.zhaofujun.nest.core.EntityLoader;
import com.zhaofujun.nest.core.Identifier;
import com.zhaofujun.nest.core.Repository;
import com.zhaofujun.nest.demo.domain.Order;
import com.zhaofujun.nest.demo.domain.User;
import com.zhaofujun.nest.demo.domain.WebUser;
import com.zhaofujun.nest.demo.infrastructure.persistence.OrderDmo;
import com.zhaofujun.nest.demo.infrastructure.persistence.mapper.OrderDmoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderRepository implements Repository<Order> {

    @Autowired
    private OrderDmoMapper orderDmoMapper;

    @Override
    public Class<Order> getEntityClass() {
        return Order.class;
    }

    @Override
    public Order getEntityById(Identifier identifier, EntityLoader<Order> entityLoader) {
        OrderDmo orderDmo = orderDmoMapper.selectById(identifier.toValue());
        if (orderDmo == null) return null;

        Order order = entityLoader.create(identifier);
        order.setSeller(EntityFactory.load(WebUser.class, StringIdentifier.valueOf(orderDmo.getSellerId())));
        return order;
    }

    @Override
    public void insert(Order order) {
        if (order == null) return;
        OrderDmo orderDmo = new OrderDmo();
        orderDmo.setId(order.getId().getId());
        orderDmo.setSellerId(order.getSeller().getId().getId());

        orderDmoMapper.insert(orderDmo);
    }

    @Override
    public void update(Order order) {
        if (order == null) return;
        OrderDmo orderDmo = new OrderDmo();
        orderDmo.setId(order.getId().getId());
        orderDmo.setSellerId(order.getSeller().getId().getId());

        orderDmoMapper.updateById(orderDmo);
    }

    @Override
    public void delete(Order order) {
        if (order == null) return;
        orderDmoMapper.deleteById(order.getId().getId());
    }
}
