/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.samples.catalog.rest.product;


import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.seedstack.business.assembler.modelmapper.ModelMapperAssembler;
import org.seedstack.samples.catalog.domain.product.Product;
import org.seedstack.samples.catalog.rest.CatalogRels;
import org.seedstack.seed.rest.RelRegistry;

import javax.inject.Inject;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class ProductAssembler extends ModelMapperAssembler<Product, ProductRepresentation> {

    @Inject
    private RelRegistry relRegistry;

    @Override
    protected void configureAssembly(final ModelMapper modelMapper) {
        modelMapper.addMappings(new PropertyMap<Product, ProductRepresentation>() {
            @Override
            protected void configure() {
                AbstractConverter<Object, Object> selfConverter = new AbstractConverter<Object, Object>() {
                    @Override
                    protected Object convert(Object source) {
                        return relRegistry.uri(CatalogRels.PRODUCT).set("title", source).getHref();
                    }
                };
                AbstractConverter<Object, Object> tagConverter = new AbstractConverter<Object, Object>() {
                    @Override
                    protected Object convert(Object source) {
                        return relRegistry.uri(CatalogRels.PRODUCT_TAGS).set("title", source).getHref();
                    }
                };
                AbstractConverter<Object, Object> relatedConverter = new AbstractConverter<Object, Object>() {
                    @Override
                    protected Object convert(Object source) {
                        return relRegistry.uri(CatalogRels.PRODUCT_RELATED).set("title", source).getHref();
                    }
                };

                using(selfConverter).map().self(source.getName());
                using(tagConverter).map().setTags(source.getName());
                using(relatedConverter).map().setRelated(source.getName());
            }
        });
    }

    @Override
    protected void configureMerge(ModelMapper modelMapper) {

    }
}
