package pl.com.bottega.photostock.sales.model;

import org.junit.Test;

import static org.junit.Assert.*;


public class MoneyTest {

    private Money fiftyCredit = Money.valueOf(50);
    private Money seventyCredit = Money.valueOf(70);
    private Money fiftyEur = Money.valueOf(50, "EUR");

    @Test
    public void shouldAddMoney() {
        //when
        Money m1PlusM2 = fiftyCredit.add(seventyCredit);
        //then
        Money expected = Money.valueOf(120);
        assertEquals(expected, m1PlusM2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddMOneyWithDifferentCurrencies() {
        //when
        fiftyCredit.add(fiftyEur);
    }

    @Test
    public void shouldSubtractMoney() {
        //when
        Money m1MinusM2 = fiftyCredit.subTract(seventyCredit);
        //then
        Money expected = Money.valueOf(-20);
        assertEquals(expected, m1MinusM2);
    }
    @Test(expected = IllegalArgumentException.class)
    public void shoudNotSubtractMoneyWithDifferentCurrencys(){
        //when
        fiftyCredit.subTract(fiftyEur);
    }
    @Test
    public void shouldCompareMoney(){
        assertTrue(fiftyCredit.compareTo(seventyCredit) < 0);
        assertTrue(seventyCredit.compareTo(fiftyCredit) > 0);
        assertTrue(fiftyCredit.compareTo(fiftyCredit) == 0);
    }
    @Test
    public void shouldCompareMoneyUsingBoooleanMethods(){
        assertTrue(fiftyCredit.lt(seventyCredit));
        assertTrue(fiftyCredit.lte(seventyCredit));
        assertTrue(seventyCredit.gt(fiftyCredit));
        assertTrue(seventyCredit.gte(fiftyCredit));
        assertFalse(fiftyCredit.gt(seventyCredit));
        assertFalse(fiftyCredit.gte(seventyCredit));
        assertFalse(seventyCredit.lt(fiftyCredit));
        assertFalse(seventyCredit.lte(fiftyCredit));
        assertTrue(fiftyCredit.lte(fiftyCredit));
        assertTrue(fiftyCredit.gte(fiftyCredit));
        assertFalse(fiftyCredit.lt(fiftyCredit));
        assertFalse(fiftyCredit.gt(fiftyCredit));
    }
    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCompareDifferentCurrencies(){
        fiftyCredit.compareTo(fiftyEur);
    }


}
