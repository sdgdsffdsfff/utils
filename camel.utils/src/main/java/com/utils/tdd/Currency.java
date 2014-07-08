/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.utils.tdd;

/**
 * 
 * @author dengqb
 * @date 2013年11月22日
 */
public abstract class Currency {
    protected Double amount;
    
    private ICurrencyExchageRate exchangeRate;
    protected final static String US = "US";
    protected final static String RMB = "RMB";
    
    protected Currency exchange(Double total, Currency current, Currency expected){
        Double rate = getRate(current.toString(), expected.toString());
        expected.setAmount(total * rate);
        return expected;
    }
    
    public Double getRate(String current, String expected){
        String rateStr = exchangeRate.getRate(current, expected);
        return Double.parseDouble(rateStr);
    }
    
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public abstract String toString();
}
