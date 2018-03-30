package com.ecom.topk.stats.domain;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;

import java.util.List;

public class AthenaResponse {
    @Getter
    @Setter
    public String status;

    @Getter
    @Setter
    public List<ProductSum> result;



}
