package com.myproject.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "product_image")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Lob
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_main")
    private Boolean isMain;

    @Column(name = "sort_Order")
    private Integer sortOrder;

    @Column(name = "alt_text")
    private String altText;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
