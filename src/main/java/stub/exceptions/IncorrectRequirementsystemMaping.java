package stub.exceptions;

import stub.systems.ExistingSystem;

public class IncorrectRequirementsystemMaping extends Exception {
    public IncorrectRequirementsystemMaping(String requirement, String SystemName){
        super("it is impossible to check "+ requirement+" requirement using "+ SystemName+"System");

    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}
