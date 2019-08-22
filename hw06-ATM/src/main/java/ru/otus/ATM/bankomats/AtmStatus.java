package ru.otus.ATM;

import java.util.List;

/**Вывод информации о банкомате
 *
 * @author Sergei Viacheslaev
 */
public interface AtmStatus {

    void printCassetteStorage();
    void showCurrentAtmCashBalance();
    int getAtmCashBalance();

}
