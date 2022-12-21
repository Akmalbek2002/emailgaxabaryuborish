package com.example.emailgaxabaryuborish.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Mahsulot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String mahsulotNomi;
    @Column(nullable = false)
    private String mahsulotTuri;
    @Column(nullable = false)
    private String mahsulotNarxi;
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp yaratilganVaqt;
    @UpdateTimestamp
    private Timestamp tahrirlanganVaqt;
    @CreatedBy
    private Integer kimTomonidanYaratilgan;
    @LastModifiedBy
    private Integer kimTomonidanTahrirlangan;
}
