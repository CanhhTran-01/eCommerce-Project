package com.myproject.ecommerce.repository.custom.impl;

import com.myproject.ecommerce.dto.request.ProductFilterSearchRequest;
import com.myproject.ecommerce.dto.response.ProductSummaryResponse;
import com.myproject.ecommerce.entity.Product;
import com.myproject.ecommerce.entity.Review;
import com.myproject.ecommerce.repository.custom.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public List<ProductSummaryResponse> searchbyFilter(ProductFilterSearchRequest request) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductSummaryResponse> cq = cb.createQuery(ProductSummaryResponse.class);
        Root<Product> product = cq.from(Product.class);

        // LEFT JOIN p.reviewList r
        Join<Product, Review> review = product.join("reviewList", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        // filter by search text
        if (request.getSearchText() != null && !request.getSearchText().isBlank()) {

            String pattern = "%" + request.getSearchText().toLowerCase() + "%";

            Predicate namePredicate = cb.like(cb.lower(product.get("productName")), pattern);
            Predicate brandPredicate = cb.like(cb.lower(product.get("brand")), pattern);
            Predicate codePredicate = cb.like(cb.lower(product.get("productCode")), pattern);

            Predicate searchPredicate = cb.or(namePredicate, brandPredicate, codePredicate);
            predicates.add(searchPredicate);
        }

        // filter by category
        if (request.getCategoryId() != null && !request.getCategoryId().isEmpty()) {
            predicates.add(product.get("category").get("id").in(request.getCategoryId()));
        }

        // filter by price
        if (request.getMinPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(product.get("price"), request.getMinPrice()));
        }
        if (request.getMaxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(product.get("price"), request.getMaxPrice()));
        }

        // aggregate
        Expression<Long> ratingCount = cb.count(review.get("id"));
        Expression<Double> ratingAvg = cb.coalesce(cb.avg(review.get("rating")), 0.0);

        // SELECT new ProductSummaryResponse(...)
        cq.select(cb.construct(
                ProductSummaryResponse.class,
                product.get("id"),
                product.get("productName"),
                product.get("mainImageUrl"),
                product.get("price"),
                product.get("discountPrice"),
                ratingCount,
                ratingAvg));

        // WHERE
        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        // GROUP BY
        cq.groupBy(
                product.get("id"),
                product.get("productName"),
                product.get("mainImageUrl"),
                product.get("price"),
                product.get("discountPrice"));

        // ORDER BY
        if (request.getSort() != null) {
            switch (request.getSort()) {
                case "newest":
                    cq.orderBy(cb.desc(product.get("updatedAt")));
                    break;
                case "price_asc":
                    cq.orderBy(cb.asc(product.get("price")));
                    break;
                case "price_desc":
                    cq.orderBy(cb.desc(product.get("price")));
                    break;
            }
        }

        return entityManager.createQuery(cq).getResultList();
    }
}
