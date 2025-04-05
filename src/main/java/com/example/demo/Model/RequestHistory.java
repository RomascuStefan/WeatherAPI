package com.example.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class RequestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column(nullable = false)
    @Size(max=255)
    private String lat;

    @Column(nullable = false)
    @Size(max=255)
    private String lon;

    @Column(nullable = false)
    @Size(max=2047)
    private String response;

    @Column
    private Boolean q;

    @Column
    private Boolean aqi;

    @Column
    private Integer days;

    @Column
    private Boolean alerts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
