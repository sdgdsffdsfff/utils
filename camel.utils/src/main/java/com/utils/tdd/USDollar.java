package com.utils.tdd;

public class USDollar extends Currency {
    public USDollar(Double amount){
        this.amount = amount;
    }
    
    public Currency times(Double multi,Currency expected){
        return exchange(this.amount * multi, this, expected);
    }
    
    @Override
    public String toString(){
        return Currency.US;
    }
}
