package org.seedstack.samples.catalog.infrastructure.goldendata;

import org.seedstack.business.domain.Repository;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.samples.catalog.Config;
import org.seedstack.samples.catalog.domain.product.Product;
import org.seedstack.seed.DataImporter;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.transaction.Transactional;

import javax.inject.Inject;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@DataSet(group = "catalog", name = "product")
class ProductImporter implements DataImporter<Product> {

    public static final int MAXIMUM_ITERATION = 15;
    
    @Inject
    @Jpa
    private Repository<Product, String> repository;

    private SecureRandom random = new SecureRandom();

    private List<Product> staging = new ArrayList<>();

    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public void importData(Product data) {
        staging.add(data);
    }

    @JpaUnit(Config.JPA_UNIT)
    @Transactional
    @Override
    public void commit(boolean clear) {
        for (Product product : staging) {
            addRelatedProducts(product);
            repository.persist(product);
        }
    }

    private void addRelatedProducts(Product product) {
        int iteration = 0;
        // Iterate until it finds 4 unique related products
        while (product.getRelated().size() < 4 && iteration < MAXIMUM_ITERATION) {
            Product relatedProduct = staging.get(random.nextInt(staging.size()));
            product.addRelated(relatedProduct.getEntityId());
            iteration++;
        }
    }

    @Override
    public void rollback() {
        staging.clear();
    }
}
