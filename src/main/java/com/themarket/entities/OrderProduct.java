package com.themarket.entities;

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

        @Column(name = "ordSn", nullable = false)
        private Long ordSn;

        @Column(name = "ordPrdTurn", nullable = false)
        private Long ordPrdTurn;

    }

    @Column(name = "ordPrdNm", nullable = false)
    private String ordPrdNm;

    @Column(name = "ordPrdPrc", nullable = false)
    private Integer ordPrdPrc;

}
