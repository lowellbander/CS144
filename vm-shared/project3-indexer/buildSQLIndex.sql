CREATE TABLE ItemSpatial
(   ItemID BIGINT(20) NOT NULL,
    Coordinates POINT NOT NULL,
    PRIMARY KEY (ItemID)
)   ENGINE=MyISAM;

INSERT INTO ItemSpatial(ItemID, Coordinates)
    SELECT ItemID, POINT(Latitude, Longitude)
    FROM Item
    WHERE Latitude IS NOT NULL  AND Longitude IS NOT NULL;

CREATE SPATIAL INDEX itemSpIndex ON ItemSpatial(Coordinates);

