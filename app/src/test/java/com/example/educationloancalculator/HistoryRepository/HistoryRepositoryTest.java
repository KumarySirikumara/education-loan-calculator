package com.example.educationloancalculator.HistoryRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HistoryRepositoryTest {
    HistoryRepository historyRepository = new HistoryRepository();

    @Test
    @After
    public void getlAmount() {
        assertEquals(Double.toString(15000.0), Double.toString(historyRepository.getlAmount()));
    }

    @Test
    @Before
    public void setlAmount() {
        historyRepository.setlAmount(15000);
    }

    @Test
    @After
    public void getiRate() {
        assertNotEquals(Float.toString(5.1F), Double.toString(historyRepository.getiRate()));
    }

    @Test
    @Before
    public void setiRate() {
        historyRepository.setiRate(5.0F);
    }

    @Test
    @After
    public void getRatePer() {
        assertEquals("MONTH", historyRepository.getRatePer());
    }

    @Test
    @Before
    public void setRatePer() {
        historyRepository.setRatePer("MONTH");
    }

    @Test
    @After
    public void getTermPeriod() {
        assertEquals("YEARS", historyRepository.getTermPeriod());
    }

    @Test
    @Before
    public void setTermPeriod() {
        historyRepository.setTermPeriod("YEARS");
    }

    @Test
    @After
    public void getSysDate() {
        assertEquals("2020-08-26", historyRepository.getSysDate());
    }

    @Test
    @Before
    public void setSysDate() {
        historyRepository.setSysDate("2020-08-26");
    }

    @Test
    @After
    public void getlTerm() {
        assertEquals(Integer.toString(5), Integer.toString(historyRepository.getlTerm()));
    }

    @Test
    @Before
    public void setlTerm() {
        historyRepository.setlTerm(5);
    }
}