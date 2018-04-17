package stub;

import stub.helpers.RequirementsTypes;
import stub.systems.ExistingSystem;
import static stub.helpers.GlobalValues.Systems;

public class Initialization {
    public static void initialization (){
        ExistingSystem SystemA = new ExistingSystem("SystemA");
        SystemA.addRequirement(RequirementsTypes.Routing);
        ExistingSystem SystemB = new ExistingSystem("SystemB");
        SystemB.addRequirement(RequirementsTypes.Routing);
        ExistingSystem SystemC = new ExistingSystem("SystemC");
        SystemC.addRequirement(RequirementsTypes.Routing);
        ExistingSystem SystemD = new ExistingSystem("SystemD");
        SystemD.addRequirement(RequirementsTypes.Routing);
        Systems.put("SystemA", SystemA);
        Systems.put("SystemB", SystemB);
        Systems.put("SystemC", SystemC);
        Systems.put("SystemD", SystemD);


    }
}
