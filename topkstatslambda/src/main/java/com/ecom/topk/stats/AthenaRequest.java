package com.ecom.topk.stats;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AthenaRequest {
    @Getter
    @Setter
    public String region;

    @Getter
    @Setter
    public String s3Path;

    @Getter
    @Setter
    public String sql;

    @Getter
    @Setter
    public String columnListStr;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
