package com.ecom.topk.stats.domain;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class AthenaResponse {
    private String status;
    private  List<ProductSum> result;

}
