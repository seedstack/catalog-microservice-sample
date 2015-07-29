package org.seedstack.samples.catalog.rest.product;

import org.seedstack.business.api.interfaces.assembler.DtoOf;
import org.seedstack.samples.catalog.domain.product.Product;
import org.seedstack.samples.catalog.rest.CatalogRels;
import org.seedstack.seed.rest.api.hal.HalRepresentation;

import java.net.URI;
import java.util.List;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@DtoOf(Product.class)
public class ProductRepresentation extends HalRepresentation {

    private String name;

    private URI picture;

    private String pricing;

    private String description;

    private List<String> details;

    public void setTags(String uri) {
        link(CatalogRels.PRODUCT_TAGS, uri);
    }

    public void setRelated(String uri) {
        link(CatalogRels.PRODUCT_RELATED, uri);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getPicture() {
        return picture;
    }

    public void setPicture(URI picture) {
        this.picture = picture;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
