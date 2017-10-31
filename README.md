# bitso-api

 This project consumes public Rest Api and subscrie to channels from Bitso(https://bitso.com/api_info/?shell#orders), is developed with Java 8, for the UI is a simple JavaFX  interface with three tables that shows Recent trades, best bids and best offers.

 It simulates trades, has a contrarian strategy, that **sells** 1 BTC after **M**-time the prices has go down and **buys** 1 BTC after **N**-time the price has go up. The values for **M** and **N** are configured since the beginning of the app.

 Use the Rest Api to show the **N** recent trades. The value for **N** is configured since the beginning.

 Use the web socket connection to show the **N**-best Bids an Asks. The value for **N** is configured since the beginning.
