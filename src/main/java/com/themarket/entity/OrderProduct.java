package com.themarket.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orderProduct")
public class OrderProduct  implements Serializable{

    @Serial
    private static final long serialVersionUID = -3848149454299478918L;

    @EmbeddedId
    private OrderProductId orderProductId;

    @Embeddable
    @NoArgsConstructor
    public class OrderProductId implements Serializable {

        @Serial
        private static final long serialVersionUID = 3142417894931649554L;

        @Column(name = "ord_sn", nullable = false)
        private Long ordSn;

        @Column(name = "ord_prd_turn", nullable = false)
        private Long ordPrdTurn;

    }

    @Column(name = "ord_prd_nm", nullable = false)
    private String ordPrdNm;

    @Column(name = "ord_prd_prc", nullable = false)
    private Integer ordPrdPrc;

}
