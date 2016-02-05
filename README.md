# Catalog micro-service sample
[![Build status](https://travis-ci.org/seedstack/catalog-microservice-sample.svg?branch=master)](https://travis-ci.org/seedstack/catalog-microservice-sample)

A micro-service project demonstrating REST features of SeedStack: JAX-RS integration, hypermedia and JSON-home.

# Running it

## Locally

If you have [Maven 3](http://maven.apache.org/) installed, you can clone the repository and run it locally:

    mvn seedstack:run

## With docker

With Docker, first build the image:

    docker build -t micro-cata-v1.0 .

Then start a container:

    docker run -d -p 8080:8080 micro-cata-v1.0

## On Heroku

Or you can just deploy it on your own Heroku account by clicking this button:

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

# Usage

Discover all application entry points as a JSON-HOME resource on the following URL:

    curl http://localhost:8080/

Then follow the links to HAL resources.

# Copyright and license

This source code is copyrighted by [The SeedStack Authors](https://github.com/seedstack/seedstack/blob/master/AUTHORS) and
released under the terms of the [Mozilla Public License 2.0](https://www.mozilla.org/MPL/2.0/). 
