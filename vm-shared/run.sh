#!/bin/bash

cd project2/ &&
bash runLoad.sh &&
cd .. &&

ant -f project3-indexer/build.xml run &&
ant -f project3-searcher/build.xml run &&

cd project3-searcher/ &&
bash deploy.sh &&
cd .. &&

cd project4/ &&
bash run.sh &&
cd .. &&

echo "Done."
