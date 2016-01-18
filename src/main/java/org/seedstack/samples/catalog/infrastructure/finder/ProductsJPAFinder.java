/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.samples.catalog.infrastructure.finder;

import com.google.inject.Inject;
import org.seedstack.business.assembler.FluentAssembler;
import org.seedstack.business.finder.Range;
import org.seedstack.business.finder.Result;
import org.seedstack.business.view.Page;
import org.seedstack.business.view.PaginatedView;
import org.seedstack.jpa.BaseJpaRangeFinder;
import org.seedstack.samples.catalog.domain.product.Product;
import org.seedstack.samples.catalog.Config;
import org.seedstack.samples.catalog.rest.catalog.ProductsFinder;
import org.seedstack.samples.catalog.rest.product.ProductRepresentation;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@JpaUnit(Config.JPA_UNIT)
class ProductsJPAFinder extends BaseJpaRangeFinder<ProductRepresentation> implements ProductsFinder {

    public static final String SEARCH_CRITERIA = "q";

    @Inject
    private EntityManager entityManager;
    @Inject
    private FluentAssembler fluently;

    @Override
    public PaginatedView<ProductRepresentation> findProducts(Page page, String query) {
        Map<String, Object> map = new HashMap<>();
        if (query != null && !"".equals(query)) {
            map.put(SEARCH_CRITERIA, buildSqlLikeQuery(query));
        }
        Result<ProductRepresentation> result = find(Range.rangeFromPageInfo(page.getIndex(), page.getCapacity()), map);
        return new PaginatedView<>(result, page);
    }

    private String buildSqlLikeQuery(String query) {
        return "%" + query + "%";
    }

    @Override
    protected List<ProductRepresentation> computeResultList(Range range, Map<String, Object> criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> q = cb.createQuery(Product.class);
        Root<Product> product = q.from(Product.class);

        q.select(product);
        if (criteria != null && criteria.get(SEARCH_CRITERIA) != null) {
            q.where(cb.like(product.<String>get("name"), (String) criteria.get(SEARCH_CRITERIA)));
        }

        TypedQuery<Product> query = entityManager.createQuery(q);
        if (range != null) {
            query.setFirstResult((int) range.getOffset()).setMaxResults((int) range.getSize());
        }

        return fluently.assemble(query.getResultList()).to(ProductRepresentation.class);
    }

    @Override
    protected long computeFullRequestSize(Map<String, Object> criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Product> product = q.from(Product.class);
        q.select(cb.count(product));
        if (criteria != null && criteria.get(SEARCH_CRITERIA) != null) {
            q.where(cb.like(product.<String>get("name"), (String) criteria.get(SEARCH_CRITERIA)));
        }
        return entityManager.createQuery(q).getSingleResult();
    }
}
