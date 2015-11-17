package org.seedstack.samples.catalog.rest;

/**
 * Lists all the REST resource provided by this application.
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class CatalogRels {

    /**
     * Catalog resource. Provides a list of products.
     */
    public static final String CATALOG = "catalog";

    /**
     * Product resource. Exposes all the information about a specific product.
     */
    public static final String PRODUCT = "product";

    /**
     * Product's tags sub resource. Exposes all the tags of a product.
     */
    public static final String PRODUCT_TAGS = "tags";

    /**
     * Tag resource. Exposes all the products corresponding to a tag.
     */
    public static final String TAG = "tag";

    /**
     * All products related to another products.
     */
    public static final String PRODUCT_RELATED = "related";
}
