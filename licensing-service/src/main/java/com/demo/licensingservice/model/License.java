package com.demo.licensingservice.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "licenses")
public class License implements Serializable {

    @Id
    @Column(name = "license_id", nullable = false)
    private String licenseId;

    @Column(name = "organization_id", nullable = false)
    private String organizationId;

    @Transient
    private String organizationName = "";

    @Transient
    private String contactName = "";

    @Transient
    private String contactPhone = "";

    @Transient
    private String contactEmail = "";

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "license_type", nullable = false)
    private String licenseType;

    @Column(name = "license_max", nullable = false)
    private Integer licenseMax;

    @Column(name = "license_allocated", nullable = false)
    private Integer licenseAllocated;

    @Column(name = "comment")
    private String comment;
}
