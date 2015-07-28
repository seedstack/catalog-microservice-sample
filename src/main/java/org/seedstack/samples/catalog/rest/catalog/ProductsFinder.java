package org.seedstack.samples.catalog.rest.catalog;

import org.seedstack.business.api.interfaces.finder.Finder;
import org.seedstack.business.api.interfaces.finder.RangeFinder;
import org.seedstack.business.api.interfaces.view.Page;
import org.seedstack.business.api.interfaces.view.PaginatedView;
import org.seedstack.samples.catalog.rest.product.ProductRepresentation;

import java.util.Map;

/**
 * This finder provides paginated queries on {@link org.seedstack.samples.catalog.domain.product.Product}.
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@Finder
public interface ProductsFinder extends RangeFinder<ProductRepresentation, Map<String, Object>> {

    /**
     * Finds a list of products. The list will be paginated according to the given page.
     * An optional query can also be passed. It will filter the products based on their name.
     *
     * @param page  the expected page
     * @param query the filter query
     * @return the paginated view of product representations
     */
    PaginatedView<ProductRepresentation> findProducts(Page page, String query);
}
