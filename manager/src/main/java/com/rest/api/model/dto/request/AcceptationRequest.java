package com.rest.api.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AcceptationRequest {

    // 승인/거절 여부
    private Boolean result;
}
