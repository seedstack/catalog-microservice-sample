# Catalog microservice sample

A REST microservice exposing the API of a product catalog.

# Run it

Download the sample:

    git clone https://github.com/seedstack/catalog-microservice-sample
    cd catalog-microservice-sample

Build the project

    mvn clean install

Start the sample:

    mvn jetty:run

Show all the application entry points as a JSON-HOME resource:

    curl 'http://localhost:8080/rest/'

Then follow links to HAL resources.

# Copyright and license

This source code is copyrighted by [The SeedStack Authors](https://github.com/seedstack/seedstack/blob/master/AUTHORS) and
released under the terms of the [Mozilla Public License 2.0](https://www.mozilla.org/MPL/2.0/). 
