package com.example.baygo.db.dto.request.admin;

import com.example.baygo.db.model.enums.SupplyStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FBBStatusChangeRequest {
    private List<Long> supplyIds;
    private SupplyStatus newStatus;
}


