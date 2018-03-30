package com.ecom.topk.stats.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AthenaRequest {
    @Getter
    @Setter
    public String startDate;

    @Getter
    @Setter
    public String endDate;

    @Getter
    @Setter
    public String quantity;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
