package ru.otus.ATM;

/**
 * Номинал банкноты
 *
 * @author Sergei Viacheslaev
 */
public enum FaceValue {


    ONE_HUNDRED(100), FIVE_HUNDRED(500), ONE_THOUSAND(1000),
    THREE_THOUSAND(3000), FIVE_THOUSAND(5000);

    private final int intValue;

    FaceValue(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }

    @Override
    public String toString() {
        return String.format("'%s'", String.valueOf(intValue));
    }
}
