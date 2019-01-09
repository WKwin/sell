package com.kwin.sell.common.converter;

import org.springframework.beans.BeanUtils;

import com.kwin.sell.sell.dto.OrderDTO;
import com.kwin.sell.sell.model.OrderMaster;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 转换器
 * @author Kwin
 *
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster) {

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }
}
