CREATE TABLE User (
    UserID INT PRIMARY KEY,
    Rating INT
);

CREATE TABLE Location (
    LocationID VARCHAR(255) PRIMARY KEY,
    Country VARCHAR(255) PRIMARY KEY,
    Longitude DOUBLE(8,5),
    Latitude DOUBLE(8,5)
);

CREATE TABLE Item (
    ItemID VARCHAR(20) PRIMARY KEY,
    Name VARCHAR(255),
    Buy_Price FLOAT(10,2),
    First_Bid FLOAT(10,2),
    Started TIMESTAMP,
    Ends TIMESTAMP,
    SellerID VARCHAR(20) FOREIGN KEY REFERENCES User(UserID),
    Description TEXT,
    LocationID VARCHAR(255) FOREIGN KEY REFERENCES Location(LocationID)
);

CREATE TABLE Bid (
    BidderID VARCHAR(20) FOREIGN KEY REFERENCES User(UserID),
    ItemID VARCHAR(20) FOREIGN KEY REFERENCES Item(ItemID),
    Time TIMESTAMP,
    Amount FLOAT(10,2),
    LocationID VARCHAR(255) FOREIGN KEY REFERENCES Location(LocationID),
    PRIMARY KEY (BidderID, ItemID)
);

CREATE TABLE Category (
    Category_Name VARCHAR(255),
    ItemID VARCHAR(20) FOREIGN KEY REFERENCES Item(ItemID),
    PRIMARY KEY (Category_Name, ItemID)
);

