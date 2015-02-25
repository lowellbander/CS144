Q) "Decide which index(es) to create on which attribute(s). Document your 
design choices and briefly discuss why you've chosen to create your particular 
index(es)."

A) Create an index on the following attributes of the Item table: ItemID, Name,
Description, and categories of each Item. Because Category is not an column of 
the Item table, we will have to do a reverse lookup (join?) on the Category 
table.

For this index, the ItemID field is needed as an identifier for the index,
and the remaining fields are used for allowing the basic keyword search.

========================== ORIGINAL CONTENTS ==================================
This example contains a simple utility class to simplify opening database
connections in Java applications, such as the one you will write to build
your Lucene index. 

To build and run the sample code, use the "run" ant target inside
the directory with build.xml by typing "ant run".
