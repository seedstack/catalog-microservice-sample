#
# Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
#
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/.
#

FROM maven:3.3.3-jdk-8-onbuild
MAINTAINER 'pierre.thirouin@ext.mpsa.com'
EXPOSE 8080
CMD ["java" "jar" "target/catalog-capsule.jar"]
