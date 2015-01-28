LOAD DATA LOCAL INFILE '~/shared/project2/user.dat' INTO TABLE User 
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

LOAD DATA LOCAL INFILE '~/shared/project2/location.dat' INTO TABLE Location
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

LOAD DATA LOCAL INFILE '~/shared/project2/item.dat' INTO TABLE Item 
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

LOAD DATA LOCAL INFILE '~/shared/project2/bid.dat' INTO TABLE Bid 
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

LOAD DATA LOCAL INFILE '~/shared/project2/category.dat' INTO TABLE Category 
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';
