package com.themarket.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "prd")
public class Product  implements Serializable {

    @Serial
    private static final long serialVersionUID = 2806329010529758266L;

    @Id
    @Column(name = "prd_no", nullable = false)
    private String prdNo;

    @Column(name = "prd_nm", nullable = false)
    private String prdNm;

    @Column(name = "prd_prc", nullable = false)
    private Integer prdPrc;

    @Column(name = "prd_stock", nullable = false)
    private Integer prdStock;

}
