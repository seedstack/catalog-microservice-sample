package org.seedstack.samples.catalog.infrastructure.finder;

import com.google.inject.Inject;
import org.seedstack.business.api.interfaces.assembler.FluentAssembler;
import org.seedstack.business.api.interfaces.finder.Range;
import org.seedstack.business.api.interfaces.finder.Result;
import org.seedstack.business.api.interfaces.view.Page;
import org.seedstack.business.api.interfaces.view.PaginatedView;
import org.seedstack.business.jpa.BaseJpaRangeFinder;
import org.seedstack.samples.catalog.domain.product.Product;
import org.seedstack.samples.catalog.Config;
import org.seedstack.samples.catalog.rest.catalog.ProductsFinder;
import org.seedstack.samples.catalog.rest.product.ProductRepresentation;
import org.seedstack.seed.persistence.jpa.api.JpaUnit;
import org.seedstack.seed.transaction.api.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@Transactional
@JpaUnit(Config.JPA_UNIT)
class ProductsJPAFinder extends BaseJpaRangeFinder<ProductRepresentation> implements ProductsFinder {

    @Inject
    private EntityManager entityManager;

    @Inject
    private FluentAssembler fluently;

    @Override
    public PaginatedView<ProductRepresentation> findProducts(Page page, String query) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (query != null && !"".equals(query)) {
            map.put("q", "%" + query + "%");
        }
        Result<ProductRepresentation> result = find(Range.rangeFromPageInfo(page.getIndex(), page.getCapacity()), map);
        return new PaginatedView<ProductRepresentation>(result, page);
    }

    @Override
    protected List<ProductRepresentation> computeResultList(Range range, Map<String, Object> criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> q = cb.createQuery(Product.class);
        Root<Product> product = q.from(Product.class);
        q.select(product);
        if (criteria != null && criteria.get("q") != null) {
            q.where(cb.like(product.<String>get("name"), (String) criteria.get("q")));
        }
        List<Product> products;

        if (range != null) {
            products = entityManager.createQuery(q).setFirstResult((int) range.getOffset()).setMaxResults((int) range.getSize()).getResultList();
        } else {
            products = entityManager.createQuery(q).getResultList();
        }

        return fluently.assemble(products).to(ProductRepresentation.class);
    }

    @Override
    protected long computeFullRequestSize(Map<String, Object> criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Product> product = q.from(Product.class);
        q.select(cb.count(product));
        if (criteria != null && criteria.get("q") != null) {
            q.where(cb.like(product.<String>get("name"), (String) criteria.get("q")));
        }
        return entityManager.createQuery(q).getSingleResult();
    }
}
