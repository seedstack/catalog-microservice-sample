package org.seedstack.samples.catalog.rest.product;

import com.google.inject.Inject;
import com.sun.jersey.api.NotFoundException;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.assembler.FluentAssembler;
import org.seedstack.samples.catalog.domain.product.Product;
import org.seedstack.samples.catalog.Config;
import org.seedstack.samples.catalog.rest.CatalogRels;
import org.seedstack.samples.catalog.rest.catalog.TagRepresentation;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.rest.Rel;
import org.seedstack.seed.rest.RelRegistry;
import org.seedstack.seed.rest.hal.HalBuilder;
import org.seedstack.seed.rest.hal.HalRepresentation;
import org.seedstack.seed.transaction.Transactional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
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
    public Response getProduct() {
        Product product = repository.load(productName);
        if (product == null) {
            throw new NotFoundException(String.format(PRODUCT_DOES_NOT_EXIST, productName));
        }

        return Response.ok(fluently.assemble(product).to(ProductRepresentation.class)).build();
    }

    @GET
    @Path("/tags")
    @Rel(CatalogRels.PRODUCT_TAGS)
    public Response getTags() {
        Product product = repository.load(productName);
        if (product == null) {
            throw new NotFoundException(String.format(PRODUCT_DOES_NOT_EXIST, productName));
        }

        List<HalRepresentation> tagRepresentations = new ArrayList<HalRepresentation>(product.getTags().size());
        for (String tagName : product.getTags()) {
            tagRepresentations.add(HalBuilder.create(new TagRepresentation(tagName)).self(relRegistry.uri(CatalogRels.TAG).set("tagName", tagName).expand()));
        }

        return Response.ok(HalBuilder.create(null)
                        .self(relRegistry.uri(CatalogRels.PRODUCT_TAGS).set("title", productName).expand())
                        .embedded("tags", tagRepresentations)
        ).build();
    }

    @GET
    @Path("/related")
    @Rel(CatalogRels.PRODUCT_RELATED)
    public Response getRelated() {
        Product product = repository.load(productName);
        if (product == null) {
            throw new NotFoundException(String.format(PRODUCT_DOES_NOT_EXIST, productName));
        }

        List<ProductRepresentation> related = new ArrayList<ProductRepresentation>(product.getRelated().size());
        for (String relatedName : product.getRelated()) {
            Product relatedProduct = repository.load(relatedName);
            if (relatedProduct != null) {
                related.add(fluently.assemble(relatedProduct).to(ProductRepresentation.class));
            }
        }

        return Response.ok(HalBuilder.create(null)
                        .self(relRegistry.uri(CatalogRels.PRODUCT_RELATED).set("title", productName).expand())
                        .embedded("related", related)).build();
    }
}
