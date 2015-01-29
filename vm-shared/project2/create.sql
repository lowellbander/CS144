CREATE TABLE User (
    UserID VARCHAR(20),
    Rating INT,
    Location VARCHAR(100),
    Country VARCHAR(100),
    PRIMARY KEY (UserID)
);

CREATE TABLE Item (
    ItemID VARCHAR(20)
    -- Name VARCHAR(255),
    -- Buy_Price FLOAT(10,2),
    -- First_Bid FLOAT(10,2),
    -- Started TIMESTAMP,
    -- Ends TIMESTAMP,
    -- SellerID VARCHAR(20),
    -- Description TEXT,
    -- Location VARCHAR(255),
    -- Country VARCHAR(255),
    -- Latitude DOUBLE(10,7),
    -- Longitude DOUBLE(10,7),
    -- PRIMARY KEY (ItemID),
    -- FOREIGN KEY (SellerID) REFERENCES User(UserID)
);
/*
CREATE TABLE Bid (
    BidderID VARCHAR(20),
    ItemID VARCHAR(20),
    Time TIMESTAMP,
    Amount FLOAT(10,2),
    PRIMARY KEY (BidderID, ItemID, Time),
    FOREIGN KEY (BidderID) REFERENCES User(UserID),
    FOREIGN KEY (ItemID) REFERENCES Item(ItemID)
);

CREATE TABLE Category (
    Category_Name VARCHAR(255),
    ItemID VARCHAR(20),
    PRIMARY KEY (Category_Name, ItemID),
    FOREIGN KEY (ItemID) REFERENCES Item(ItemID)
);

