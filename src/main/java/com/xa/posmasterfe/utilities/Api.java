package com.xa.posmasterfe.utilities;

public class Api {
    protected Api(){

    }

    public static final String MASTER_BASE_PATH = "http://localhost:9000/api";
    public static final String TRANSACTION_BASE_PATH = "http://localhost:9001/api";
    public static final String API_MASTER_CATEGORY = MASTER_BASE_PATH + "/category";
    public static final String API_MASTER_PRODUCT = MASTER_BASE_PATH + "/product";
    public static final String API_MASTER_VARIANT = MASTER_BASE_PATH + "/variant";
}
