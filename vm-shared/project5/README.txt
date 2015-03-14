Q1)  For which communication(s) do you use the SSL encryption? If you are
encrypting the communication from (1) to (2) in Figure 2, for example, write
(1)->(2) in your answer.

A1) (4) -> (5) and (5) -> (6).

Q2) How do you ensure that the item was purchased exactly at the Buy_Price of
that particular item?

A2) When an instance of getItem.jsp is requested to be rendered, ItemServlet
takes the ItemID from the request and contacts the backend eBay web service to
obtain the Buy Price for that item. In addition to setting the Buy Price as an
attribute of the the request so that it can be included in the rendering of the
HTML returned to the client, it is added to the session object. When the user
then clicks "Buy Now", a credit card input page is similarly rendered, but this
time the Buy Price info is obtained through the session as opposed to the
request, so the user has no opportunity to alter the price.

References: None.

