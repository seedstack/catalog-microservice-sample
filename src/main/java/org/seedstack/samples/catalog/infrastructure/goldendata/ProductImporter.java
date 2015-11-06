package org.seedstack.samples.catalog.infrastructure.goldendata;

import org.seedstack.business.domain.Repository;
import org.seedstack.samples.catalog.domain.product.Product;
import org.seedstack.samples.catalog.Config;
import org.seedstack.seed.DataImporter;
import org.seedstack.seed.DataSet;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
@DataSet(group = "catalog", name = "product")
class ProductImporter implements DataImporter<Product> {

    @Inject
    @Jpa
    private Repository<Product, String> repository;

    private List<Product> stating = new ArrayList<Product>();

    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public void importData(Product data) {
        stating.add(data);
    }

    @JpaUnit(Config.JPA_UNIT)
    @Transactional
    @Override
    public void commit(boolean clear) {
        Random random = new Random();
        for (Product product : stating) {
            for (int i = 0; i < 4; i++) {
                Product product1 = stating.get(random.nextInt(stating.size()));
                product.addRelated(product1.getEntityId());
            }
            repository.persist(product);
        }

    }

    @Override
    public void rollback() {
        stating.clear();
    }
}
