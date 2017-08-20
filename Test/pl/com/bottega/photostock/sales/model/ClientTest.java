package pl.com.bottega.photostock.sales.model;


import org.junit.Test;

import static org.junit.Assert.*;

public class ClientTest {

    private Address address = new Address("ul. Północna 11",
            "Polska", "Lublin", "20-001");
    private Client clientWithCredit = new Client("Jan Nowak", address,
            ClientStatus.VIP, Money.valueOf(100), Money.valueOf(100));
    private Client clientWithoutMoney = new Client("Jan Nowak", address);

    @Test
    public void shouldCheckIfClientAfford() {
        //when
        clientWithoutMoney.reCharge(Money.valueOf(100));
        //then
        assertTrue(clientWithoutMoney.canAfford(Money.valueOf(100)));
        assertTrue(clientWithoutMoney.canAfford(Money.valueOf(50)));
        assertFalse(clientWithoutMoney.canAfford(Money.valueOf(101)));
    }

    @Test
    public void shouldCheckIfClientCanAffordWithCredit() {
    //then
        assertTrue(clientWithCredit.canAfford(Money.valueOf(200)));
        assertFalse(clientWithCredit.canAfford(Money.valueOf(201)));
    }
    @Test
    public void shouldChargeAndRechargeClient(){
        //when
        clientWithCredit.charge(Money.valueOf(200), "Testowy zakup");
        clientWithCredit.reCharge(Money.valueOf(100));
        //then
        assertTrue(clientWithCredit.canAfford(Money.valueOf(100)));
        assertFalse(clientWithCredit.canAfford(Money.valueOf(101)));
    }
    @Test(expected = IllegalStateException.class)
    public void shouldNotAllowToChargeMorThanCanAfford(){
        clientWithCredit.charge(Money.valueOf(150), "Testowy wydatek");
        clientWithCredit.charge(Money.valueOf(150), "Testowy wydatek2");
    }

}

