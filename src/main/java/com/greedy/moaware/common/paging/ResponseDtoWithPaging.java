package com.greedy.moaware.common.paging;

import lombok.Data;

@Data
public class ResponseDtoWithPaging {

    private Object data;
    private PagingButtonInfo pageInfo;

}
