#!/bin/bash
#
# Copyright 2016-2017 Testify Project.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

set -e

CURRENT_DIR=$(pwd)
EXAMPLES_DIR="$CURRENT_DIR/junit4"
ARCHETYPES_DIR="$CURRENT_DIR/archetype"

echo "Building Examples"

pushd $CURRENT_DIR
cd $EXAMPLES_DIR
mvn -B clean install -Parchetype -Pvalidate
popd

echo "Copying Generated Example Arechtypes to Arechtype Modules"
for DIR in $(find $EXAMPLES_DIR -maxdepth 1 -mindepth 1 -type d -not -path "$EXAMPLES_DIR/target")
do
    MODULE_NAME="${DIR/#$EXAMPLES_DIR\/example-/}"
    EXAMPLE_SRC_DIR="$DIR/target/generated-sources/archetype/src"
    ARCHETYPE_DIR="$ARCHETYPES_DIR/$MODULE_NAME-archetype"
    ARCHETYPE_SRC_DIR="$ARCHETYPE_DIR/src"

    # Escape certain configuration properties so they are not replaced        
    find $EXAMPLE_SRC_DIR -name "pom.xml" | xargs sed -i 's/${jacocoSurefireArgs}/\\\$\{jacocoSurefireArgs\}/g'
    find $EXAMPLE_SRC_DIR -name "pom.xml" | xargs sed -i 's/${jacocoFailesafeArgs}/\\\$\{jacocoFailesafeArgs\}/g'

    # Updating goal.txt to verify
    find -name "goal.txt" -exec sh -c "echo verify > {}" \;

    echo "Replacing $ARCHETYPE_SRC_DIR with $EXAMPLE_SRC_DIR"
    rm -rf "$ARCHETYPE_SRC_DIR"
    cp -R $EXAMPLE_SRC_DIR $ARCHETYPE_DIR
done

echo "Building Archetypes"

pushd $CURRENT_DIR
cd $ARCHETYPES_DIR
mvn -B clean install -Pbuild
popd

echo "Arechetypes Updated. All Done!"

exit 0
