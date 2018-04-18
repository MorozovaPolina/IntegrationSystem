package stub.helpers;

import stub.transaction.Transaction;

import java.util.HashMap;
import java.util.Map;

public class GlobalValues {
    public static Map<Integer, Transaction> Transactions = new HashMap(); /*список идентификаторов всех открытых на
    момент запуска программы транзакций и всех транзакций,
    созданых во время данной сессии (открытых, упавших и успешно завершенных). */
    public static int TransactionNumber=1; /* идентификатор транзакции. У каждой транзакции уникальный номер.
    Чтобы не лезть и не проверять, какой следующий номер свободен,
    будем хранить следующий возможный номер в данной переменной*/

}
