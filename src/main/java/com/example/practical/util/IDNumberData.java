package com.example.practical.util;

import java.time.LocalDate;
import java.time.Period;

public class IDNumberData {

    private final String idNumber;
    private final LocalDate dateOfBirth;
    private final Gender gender;
    private final Nationality citizenship;
    private final boolean valid;

    /**
     */
    public IDNumberData(String idNumber, LocalDate dateOfBirth, Gender gender, Nationality citizenship, boolean valid) {
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.citizenship = citizenship;
        this.valid = valid;
    }

    /**
     * @return String
     */
    public String getIdNumber() {
        return this.idNumber;
    }

    /**
     * @return LocalDate
     */
    public LocalDate getBirthdate() {
        return this.dateOfBirth;
    }

    /**
     * @return int
     */
    public int getAge() {
        LocalDate today = LocalDate.now();
        Period p = Period.between(this.dateOfBirth, today);
        return p.getYears();
    }

    /**
     * @return Gender
     */
    public Gender getGender() {
        return this.gender;
    }

    /**
     * @return Nationality
     */
    public Nationality getCitizenShip() {
        return this.citizenship;
    }

    /**
     * @return boolean
     */
    public boolean isValid() {
        return this.valid;
    }
}
