/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.samples.catalog.rest.catalog;

import org.seedstack.business.finder.Finder;
import org.seedstack.business.finder.RangeFinder;
import org.seedstack.business.view.Page;
import org.seedstack.business.view.PaginatedView;
import org.seedstack.samples.catalog.rest.product.ProductRepresentation;

/**
 * This finder provides paginated queries on {@link org.seedstack.samples.catalog.domain.product.Product}.
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@Finder
public interface ProductsFinder extends RangeFinder<ProductRepresentation, String> {

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
