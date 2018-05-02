package stub.helpers;

import stub.transaction.AbstractTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GlobalValues {
    public static Map<Integer, AbstractTransaction> Transactions = new HashMap(); /*список идентификаторов всех открытых на
    момент запуска программы транзакций и всех транзакций,
    созданых во время данной сессии (открытых, упавших и успешно завершенных). */
    public static int TransactionNumber=1; /* идентификатор транзакции. У каждой транзакции уникальный номер.
    Чтобы не лезть и не проверять, какой следующий номер свободен,
    будем хранить следующий возможный номер в данной переменной*/
    public static ArrayList<String> NewMessagesLog = new ArrayList<>();

}
