Lowell Bander, 204 156 534
Akshay Bakshi, 104 160 782

1) “List your relations. Please specify all keys that hold on each relation. 
You need not specify attribute types at this stage.”

Because underlining is not possible in plaintext, keys are enclosed by pairs of
asterisks in the following relations.

Item (*ItemID*, Name, Buy_Price, First_Bid,
    Started, Ends, SellerID, Description, LocationID)
Location (*LocationID*, *Country*, Longitude, Latitude)
User (*UserID*, Rating)
Bid (*BidderID*, *ItemID*, Time, Amount, LocationID)
Category (*Category_Name*, *ItemID*)

2) “List all completely nontrivial functional dependencies that hold on each
relation, excluding those that effectively specify keys.”

ItemID -> Name, Buy_Price, First_Bid, Started, Ends, SellerID, 
    Description, LocationID
Location_Name, Country -> Longitude, Latitude
UserID -> Rating
BidderID, ItemID -> Time, Amount, LocationID

3) “Are all of your relations in Boyce-Codd Normal Form (BCNF)? If not, either
redesign them and start over, or explain why you feel it is advantageous to use
non-BCNF relations.”

All of our relations are in Boyce-Codd Normal Form (BCNF) because for every
functional dependency X -> Y, X is a super key for the table.

4) Are all of your relations in Fourth Normal Form (4NF)? If not, either
redesign them and start over, or explain why you feel it is advantageous to use
non-4NF relations.

Our relations have no non-trivial multivalued dependencies. This, paired with
the fact that our relations are in BCNF, shows that are relations are in 4NF.

