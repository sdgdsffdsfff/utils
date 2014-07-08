package com.utils.tdd;

public class RMB extends Currency {
    public RMB(){
    }
    
    public RMB(Double amount){
        this.amount = amount;
    }
    
    @Override
    public String toString() {
        return Currency.RMB;
    }

}
