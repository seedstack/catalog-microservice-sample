package org.seedstack.samples.catalog.domain.product;

import org.seedstack.business.api.domain.BaseValueObject;

/**
 * A tag value object.
 *
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */

public class Tag extends BaseValueObject {

    private String name;

    /**
     * Constructor.
     *
     * @param name the tag value
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     * @return the tag name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
