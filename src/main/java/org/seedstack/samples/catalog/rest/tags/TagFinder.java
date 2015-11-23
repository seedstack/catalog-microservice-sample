/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.samples.catalog.rest.tags;

import org.seedstack.business.finder.Finder;
import org.seedstack.business.finder.RangeFinder;
import org.seedstack.business.view.Page;
import org.seedstack.business.view.PaginatedView;
import org.seedstack.samples.catalog.rest.product.ProductRepresentation;

import java.util.Map;

/**
 * Provides method to find tags.
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@Finder
public interface TagFinder extends RangeFinder<ProductRepresentation, Map<String, Object>> {

    /**
     * Finds the products associated to a given tag.
     *
     * @param page    the required page
     * @param tagName the tag name
     * @return a product page
     */
    PaginatedView<ProductRepresentation> findProductsByTag(Page page, String tagName);

}
