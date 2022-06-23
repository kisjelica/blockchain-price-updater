// SPDX-License-Identifier: MIT
pragma solidity ^0.8.7;
import "@openzeppelin/contracts/utils/math/SafeMath.sol";
contract PriceUpdater {

    using SafeMath for uint256;
    //percentage precision up to 2 decimal points
    uint256 precision = 4;
    mapping(string => uint256) public tokenPrices;

    event priceUpdated(string indexed symbol, uint256 price);

    function updatePrice(string calldata symbol, uint256 price) external{
        if(tokenPrices[symbol] != 0){
        require((((computeDifference(tokenPrices[symbol], price).mul(10**precision)).div(tokenPrices[symbol]))) > 2*(10**(precision-2)), "Price difference lesser than 2%");
        }
        tokenPrices[symbol] = price;
        emit priceUpdated(symbol, price);
    }

    function computeDifference(uint256 price1, uint256 price2) internal pure returns (uint256) {
        uint256 diff = 0;
        if(price1 > price2)
        diff = price1.sub(price2);
        else
        diff = price2.sub(price1);
        return diff;
    }
}