LOAD DATA LOCAL INFILE '~/shared/project2/user.del' INTO TABLE User 
FIELDS TERMINATED BY '|*|'; 

LOAD DATA LOCAL INFILE '~/shared/project2/item.del' INTO TABLE Item 
FIELDS TERMINATED BY '|*|';

LOAD DATA LOCAL INFILE '~/shared/project2/bid.del' INTO TABLE Bid 
FIELDS TERMINATED BY '|*|';

LOAD DATA LOCAL INFILE '~/shared/project2/category.del' INTO TABLE Category 
FIELDS TERMINATED BY '|*|' ;
