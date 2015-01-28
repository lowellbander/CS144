#!/bin/bash

# Run the drop.sql batch file to drop existing tables
mysql CS144 < drop.sql

# Run the create.sql batch file to create the database and tables
mysql CS144 < create.sql

# Compile and run the parser to generate the appropriate load files
ant
ant run-all
#...

# If the Java code does not handle duplicate removal, do this now
user="user.del"
location="location.del"
item="item.del"
bid="bid.del"
category="category.del"

(sort $user | uniq -u) > $user
(sort $location | uniq -u) > $location
(sort $item | uniq -u) > $item
(sort $bid | uniq -u) > $bid
(sort $category | uniq -u) > $category

# Run the load.sql batch file to load the data
mysql CS144 < load.sql

# Remove all temporary files
# rm ...
# ...

