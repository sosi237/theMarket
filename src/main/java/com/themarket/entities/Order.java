package com.themarket.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ord")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 6112915162573673178L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ord_sn", nullable = false)
    private Long ordSn;

    @Column(name="reg_dt")
    private LocalDateTime regDt;

    @OneToMany
    @JoinColumn(name="ord_sn", referencedColumnName = "ord_sn")
    private List<OrderProduct> orderProducts;
}
