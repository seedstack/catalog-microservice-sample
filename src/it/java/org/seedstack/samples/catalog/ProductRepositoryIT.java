/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.samples.catalog;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.domain.Repository;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.samples.catalog.domain.product.Price;
import org.seedstack.samples.catalog.domain.product.Product;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.transaction.Transactional;

import javax.inject.Inject;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@Transactional
@JpaUnit(Config.JPA_UNIT)
@RunWith(SeedITRunner.class)
public class ProductRepositoryIT {

    public static final String PRODUCT_NAME = "SeedStack in Action";

    @Inject @Jpa
    private Repository<Product, String> repository;

    @Test
    public void test_database_config() {
        Product product = new Product(PRODUCT_NAME);
        product.setDescription("Book presenting seedstack and all its awesome features");
        product.setPricing(new Price(45, "euro"));
        repository.persist(product);

        Product product1 = repository.load(PRODUCT_NAME);
        Assertions.assertThat(product1).isNotNull();
        Assertions.assertThat(product1.getDescription()).isEqualTo("Book presenting seedstack and all its awesome features");

        repository.delete(product1); // cleanup
    }

    @Test
    public void test_related_products() {
        Product eventage = repository.load("Xixan");
        Assertions.assertThat(eventage.getRelated()).hasSize(4);
        Assertions.assertThat(repository.load(eventage.getRelated().iterator().next())).isNotNull();
    }
}
