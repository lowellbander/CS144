SELECT COUNT(DISTINCT ItemID) 
FROM (
    SELECT I.ItemID, Name, Category_Name, Description 
    FROM Item AS I, Category AS C
    WHERE I.ItemID = C.ItemID
) AS ItemsAndTheirCategories
WHERE Name LIKE "%kitchenware%"
    OR Category_Name LIKE "%kitchenware%"
    OR Description LIKE "%kitchenware%"
;
