/* 1) Find the number of users in the database. */
SELECT COUNT(*) FROM User;

/* 2) Find the number of items in "New York", (i.e., items whose location is
 * exactly the string "New York"). Pay special attention to case sensitivity.
 * You should match the items in "New York" but not in "new york". */
SELECT COUNT(*) FROM Item WHERE LocationID = "New York";

/* 3) Find the number of auctions belonging to exactly four categories.*/

/* 4) Find the ID(s) of current (unsold) auction(s) with the highest bid. Remember
 * that the data was captured at the point in time December 20th, 2001, one
 * second after midnight, so you can use this time point to decide which
 * auction(s) are current. Pay special attention to the current auctions without
 * any bid.*/

/* 5) Find the number of sellers whose rating is higher than 1000.*/
SELECT COUNT(*)
FROM (SELECT * FROM User Where Rating > 1000 AND
        UserID IN (SELECT SellerID FROM Item)
);

/* 6) Find the number of users who are both sellers and bidders.*/
SELECT COUNT(*)
FROM (SELECT * FROM User WHERE 
        UserID IN (SELECT BidderID FROM Bid) AND
        UserID IN (SELECT SellerID FROM Item)
);

/* 7) Find the number of categories that include at least one item with a bid of
 * more than $100.*/


