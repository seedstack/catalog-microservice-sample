package org.seedstack.samples.catalog.rest.tags;

import org.seedstack.business.api.interfaces.view.PaginatedView;
import org.seedstack.samples.catalog.rest.product.ProductRepresentation;
import org.seedstack.seed.rest.api.hal.HalRepresentation;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class TagRepresentation extends HalRepresentation {

    private String name;

    private long totalItems;

    private long currentPage;

    // required by jackson
    TagRepresentation() {
    }

    public TagRepresentation(String tagName, PaginatedView<ProductRepresentation> view) {
        this.name = tagName;
        this.totalItems = view.getResultSize();
        this.currentPage = view.getPageIndex();
        if (view.getView().size() > 0) {
            embedded("products", view.getView());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public long getCurrentPage() {
        return currentPage;
    }
}
