package pl.com.bottega.photostock.sales.model;


import org.junit.Test;

import static org.junit.Assert.*;

public class ClientTest {

    private Address address = new Address("ul. Północna 11", "Poland", "Lublin", "20-001");
    private Client clientWithCredit = new VIPClient("test",
            address,
            ClientStatus.VIP,
            Money.ZERO,
            Money.valueOf(100));

    private Client clientWithNoMoney = new VIPClient("test", address);

    @Test
    public void shouldCheckIfClientCanAfford() {

        clientWithCredit.reCharge(Money.valueOf(100));
        assertTrue(clientWithCredit.canAfford(Money.valueOf(200)));
        assertTrue(clientWithCredit.canAfford(Money.valueOf(150)));
        assertFalse(clientWithCredit.canAfford(Money.valueOf(201)));
    }

    @Test
    public void shouldCheckIfClientCanAffordWithCredit() {
        //then
        assertTrue(clientWithCredit.canAfford(Money.valueOf(100)));
        assertFalse(clientWithCredit.canAfford(Money.valueOf(101)));
    }

    @Test
    public void shouldChargeAndRechargeClient() {
        // when
        clientWithCredit.charge(Money.valueOf(100), "Testowy zakup");
        clientWithCredit.reCharge(Money.valueOf(100));

        //then
        assertTrue(clientWithCredit.canAfford(Money.valueOf(100)));
        assertFalse(clientWithCredit.canAfford(Money.valueOf(101)));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotAllowToChargeMoreThanCanAfford() {
        clientWithCredit.charge(Money.valueOf(50), "Testowy zakup");
        clientWithCredit.charge(Money.valueOf(100), "Testowy zakup");
        clientWithCredit.charge(Money.valueOf(100), "Testowy zakup");
    }
}

