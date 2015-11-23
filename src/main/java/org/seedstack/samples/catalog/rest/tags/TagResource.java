/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.samples.catalog.rest.tags;

import org.seedstack.business.view.Page;
import org.seedstack.business.view.PaginatedView;
import org.seedstack.samples.catalog.Config;
import org.seedstack.samples.catalog.rest.CatalogRels;
import org.seedstack.samples.catalog.rest.product.ProductRepresentation;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.rest.Rel;
import org.seedstack.seed.rest.RelRegistry;
import org.seedstack.seed.rest.hal.HalRepresentation;
import org.seedstack.seed.transaction.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@Rel(CatalogRels.TAG)
@Path("/tags/{tagName}")
public class TagResource {

    @Inject
    private RelRegistry relRegistry;

    @Inject @Named("tag")
    private TagFinder tagFinder;

    @JpaUnit(Config.JPA_UNIT)
    @Transactional
    @GET
    @Produces({MediaType.APPLICATION_JSON, "application/hal+json"})
    public Response getTag(@PathParam("tagName") String tagName, @DefaultValue("0") @QueryParam("pageIndex") Integer pageIndex,
                           @DefaultValue("10") @QueryParam("pageSize") Integer pageSize) {

        if (tagName == null || "".equals(tagName)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("The tag name should be specified").build();
        }

        PaginatedView<ProductRepresentation> view = tagFinder.findProductsByTag(new Page(pageIndex, pageSize), tagName);

        HalRepresentation tagRepresentation = new TagRepresentation(tagName, view)
                .self(buildTagHref(tagName, pageIndex, pageSize));

        if (view.hasPrev()) {
            tagRepresentation.link("prev", buildTagHref(tagName, view.prev().getIndex(), pageSize));
        }
        if (view.hasNext()) {
            tagRepresentation.link("prev", buildTagHref(tagName, view.next().getIndex(), pageSize));
        }

        return Response.ok(tagRepresentation).build();
    }

    private String buildTagHref(String tagName, long pageIndex, long pageSize) {
        return relRegistry.uri(CatalogRels.TAG)
                .set("tagName", tagName)
                .set("pageIndex", pageIndex)
                .set("pageSize", pageSize).expand();
    }
}
