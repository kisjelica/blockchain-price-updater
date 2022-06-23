# blockchain-price-updater

How to run:

Run ```java -jar backend-1.0-SNAPSHOT.jar``` in backend/target folder, or run the main class from an IDE. The server will run on http://localhost:8080.

Functionalities:
1) To get the price history for a currency, use the endpoint /price-history. Request body needs to contain argument "symbol" (BTC or ETH) and two timestamps, "from" and "to".
2) To get the current price for a currency, use the endpoint /current-price. Request body needs to contain argument "symbol" (BTC or ETH).

A **postman collection** is included in the repository so you can use that for testing.

Smart contract is deployed at **0xc518e70ac25519d15eAd25720CfB8ADBAa24C8Ec**.
