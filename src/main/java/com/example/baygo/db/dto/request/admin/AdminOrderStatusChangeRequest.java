package com.example.baygo.db.dto.request.admin;

import com.example.baygo.db.model.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class AdminOrderStatusChangeRequest {
  private List<Long> orderIds;
  private OrderStatus orderStatus;

}
