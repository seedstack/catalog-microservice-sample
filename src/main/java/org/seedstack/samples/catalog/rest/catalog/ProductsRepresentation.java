/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.samples.catalog.rest.catalog;

import org.seedstack.business.view.PaginatedView;
import org.seedstack.samples.catalog.rest.product.ProductRepresentation;
import org.seedstack.seed.rest.hal.HalRepresentation;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class ProductsRepresentation extends HalRepresentation {

    private long totalProduct;

    private long currentPage;

    // required by jackson
    ProductsRepresentation() {
    }

    public ProductsRepresentation(PaginatedView<ProductRepresentation> page) {
        this.totalProduct = page.getResultSize();
        this.currentPage = page.getPageIndex();
        if (page.getView().size() > 0) {
            embedded("products", page.getView());
        }
    }

    public long getTotalProduct() {
        return totalProduct;
    }

    public long getCurrentPage() {
        return currentPage;
    }
}
