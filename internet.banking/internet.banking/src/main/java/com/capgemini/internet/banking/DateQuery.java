package com.capgemini.internet.banking;

public class DateQuery {

    public static final String FIND_BY_DATE = "SELECT * FROM TB_TRANSACTION where TRANSACTION_DATE BETWEEN :initDate and :endDate";
}
