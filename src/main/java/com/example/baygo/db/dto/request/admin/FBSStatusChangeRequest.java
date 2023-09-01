package com.example.baygo.db.dto.request.admin;

import com.example.baygo.db.model.enums.FBSSupplyStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class FBSStatusChangeRequest {
    private List<Long>supplyIds;
    private FBSSupplyStatus newStatus;
}
