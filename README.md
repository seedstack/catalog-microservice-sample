# Catalog microservice sample

A REST microservice exposing the API of a product catalog, based on the e-commerce domain model.

# Run it

If you have [Maven 3](http://maven.apache.org/) installed, you can clone the repository and run it locally with the maven Jetty plugin:

    git clone https://github.com/seedstack/catalog-microservice-sample.git
    cd catalog-microservice-sample
    mvn clean install && mvn jetty:run

Or you can just deploy it on your own Heroku account by clicking this button:

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)
 
# Use it

Discover all application entry points as a JSON-HOME resource:

    curl 'http://localhost:8080/rest/'

Then follow links to HAL resources.

# Copyright and license

This source code is copyrighted by [The SeedStack Authors](https://github.com/seedstack/seedstack/blob/master/AUTHORS) and
released under the terms of the [Mozilla Public License 2.0](https://www.mozilla.org/MPL/2.0/). 
