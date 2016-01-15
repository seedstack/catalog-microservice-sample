/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.samples.catalog;

import com.jayway.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.seedstack.seed.it.AbstractSeedWebIT;
import org.skyscreamer.jsonassert.JSONAssert;

import java.net.URL;

import static com.jayway.restassured.RestAssured.expect;

/**
 * @author pierre.thirouin@ext.mpsa.com (Pierre Thirouin)
 */
public class ProductsResourceIT extends AbstractSeedWebIT {

    @ArquillianResource
    private URL baseURL;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class);
    }

    @RunAsClient
    @Test
    public void hal_builder() throws JSONException {
        Response response = expect().statusCode(200).given().header("Content-Type", "application/hal+json")
                .get(baseURL.toString() + "products?pageSize=10");

        JSONObject expectedResponse = expectedResponse();

        JSONAssert.assertEquals(expectedResponse, new JSONObject(response.asString()), false);
    }

    private JSONObject expectedResponse() throws JSONException {
        JSONObject obj = new JSONObject();
        JSONObject links = new JSONObject();
        links.put("self", new JSONObject().put("href", "/products?pageSize=10&pageIndex=0"));
        links.put("next", new JSONObject().put("href", "/products?pageSize=10&pageIndex=1"));
        obj.put("_links", links);

        JSONObject embedded = new JSONObject();
        JSONArray products = new JSONArray();
        for (int i = 0; i < 10; i++) {
            products.add(new JSONObject());
        }
        embedded.put("products", products);
        obj.put("_embedded", embedded);
        return obj;
    }

    @RunAsClient
    @Test
    public void json_home() throws JSONException {
        Response response = expect().statusCode(200).given().header("Content-Type", "application/json-home")
                .get(baseURL.toString());

        response.print();
    }

}
