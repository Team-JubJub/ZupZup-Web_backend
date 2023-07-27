package com.rest.api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "EnterReqsBuilder")
@Getter
@Table(name = "enter_reqs")
public class EnterReqs {

}
