/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.samples.catalog.rest.product;

import com.google.inject.Inject;
import io.swagger.annotations.Api;
import org.seedstack.business.assembler.FluentAssembler;
import org.seedstack.business.domain.Repository;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.samples.catalog.Config;
import org.seedstack.samples.catalog.domain.product.Product;
import org.seedstack.samples.catalog.rest.CatalogRels;
import org.seedstack.samples.catalog.rest.catalog.TagRepresentation;
import org.seedstack.seed.rest.Rel;
import org.seedstack.seed.rest.RelRegistry;
import org.seedstack.seed.rest.hal.HalBuilder;
import org.seedstack.seed.rest.hal.HalRepresentation;
import org.seedstack.seed.transaction.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Api
@Transactional
@JpaUnit(Config.JPA_UNIT)
@Path("/products/{title}")
@Produces({MediaType.APPLICATION_JSON, "application/hal+json"})
public class ProductResource {

    private static final String PRODUCT_DOES_NOT_EXIST = "Product %s doesn't exist";

    @Inject @Jpa
    private Repository<Product, String> repository;
    @Inject
    private RelRegistry relRegistry;
    @Inject
    private FluentAssembler fluently;

    @PathParam("title")
    private String productName;

    @GET
    @Rel(value = CatalogRels.PRODUCT, home = true)
    public ProductRepresentation getProduct() {
        Product product = repository.load(productName);
        if (product == null) {
            throw new NotFoundException(String.format(PRODUCT_DOES_NOT_EXIST, productName));
        }
        return fluently.assemble(product).to(ProductRepresentation.class);
    }

    @GET
    @Path("/tags")
    @Rel(CatalogRels.PRODUCT_TAGS)
    public HalRepresentation getTags() {
        Product product = repository.load(productName);
        if (product == null) {
            throw new NotFoundException(String.format(PRODUCT_DOES_NOT_EXIST, productName));
        }
        String selfLink = relRegistry.uri(CatalogRels.PRODUCT_TAGS).set("title", productName).expand();
        List<HalRepresentation> tagRepresentations = product.getTags().stream()
                .map(this::buildHalTag).collect(Collectors.toList());
        return HalBuilder.create(null).self(selfLink).embedded("tags", tagRepresentations);
    }

    private HalRepresentation buildHalTag(String tagName) {
        return HalBuilder.create(new TagRepresentation(tagName)).self(relRegistry.uri(CatalogRels.TAG).set("tagName", tagName).expand());
    }

    @GET
    @Path("/related")
    @Rel(CatalogRels.PRODUCT_RELATED)
    public HalRepresentation getRelated() {
        Product product = repository.load(productName);
        if (product == null) {
            throw new NotFoundException(String.format(PRODUCT_DOES_NOT_EXIST, productName));
        }
        String selfLink = relRegistry.uri(CatalogRels.PRODUCT_RELATED).set("title", productName).expand();
        List<ProductRepresentation> embeddedRelatedProduct = getRelatedProducts(product);
        return HalBuilder.create(null).self(selfLink).embedded("related", embeddedRelatedProduct);
    }

    private List<ProductRepresentation> getRelatedProducts(Product product) {
        return product.getRelated().stream()
                .map(repository::load).filter(p -> p != null)
                .map(relatedProduct -> fluently.assemble(relatedProduct).to(ProductRepresentation.class))
                .collect(Collectors.toList());
    }
}
