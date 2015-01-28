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
    UserID INT PRIMARY KEY,
    Name VARCHAR(255),
    Buy_Price FLOAT(10,2),
    First_Bid FLOAT(10,2),
    Started TIMESTAMP,
    Ends TIMESTAMP,
    SellerID INT FOREIGN KEY REFERENCES User(UserID),
    Description TEXT,
    LocationID VARCHAR(255) FOREIGN KEY REFERENCES Location(LocationID)
);

CREATE TABLE Bid (
);

