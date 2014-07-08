package com.utils.tdd;

public class Calculator {

    public Double times(Double amount, Double multi){
        return amount*multi;
    }
    
    public Double exchange(Double total, String current, String expected){
        return total * getRate(current, expected);
    }
    
    /**
     * 获取汇率
     * @param current 当前币种
     * @param expected 期望币种
     * @return 每一个当期币种=期望币种。比如1RMB=0.1641US
     */
    private Double getRate(String current, String expected){
        if (current.equals("RMB") && expected.equals("US")){
            return 0.1641;
        }
        return 1D;
    }
}
