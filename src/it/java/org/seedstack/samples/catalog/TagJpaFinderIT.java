/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.samples.catalog;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.finder.Range;
import org.seedstack.business.finder.Result;
import org.seedstack.samples.catalog.rest.product.ProductRepresentation;
import org.seedstack.samples.catalog.rest.tags.TagFinder;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@RunWith(SeedITRunner.class)
public class TagJpaFinderIT {

    @Inject @Named("tag")
    private TagFinder tagFinder;

    @Test
    public void test_find() {
        Map<String, Object> criteria = new HashMap<String, Object>();
        criteria.put("tagName", "tag2");
        Result<ProductRepresentation> result = tagFinder.find(new Range(0, 10), criteria);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getFullSize()).isGreaterThanOrEqualTo(0);
        Assertions.assertThat(result.getSize()).isGreaterThanOrEqualTo(0);
    }
}
