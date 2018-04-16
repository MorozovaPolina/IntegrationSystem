package stub.systems;

import stub.exceptions.NoSuchSystem;
import stub.helpers.RequirementsTypes;

import java.util.ArrayList;

import static stub.helpers.GlobalValues.Systems;

public class ExistingSystem {
        String SystemName;
        ArrayList<RequirementsTypes> AvailableRequirements;

        public ExistingSystem (String SystemName){
            this.SystemName=SystemName;
            AvailableRequirements= new ArrayList<RequirementsTypes>();
        }

        public  static ExistingSystem getSystem(String Name) throws NoSuchSystem {
            ExistingSystem System = Systems.get(Name);
            if (System==null) throw new NoSuchSystem("Name");
            return System;
        }

        public void addRequirement(RequirementsTypes requirementsTypes){
            if(AvailableRequirements.contains(requirementsTypes)) return;
            else AvailableRequirements.add(requirementsTypes);
        }

        public ArrayList<RequirementsTypes> getAvailableRequirements() {
            return AvailableRequirements;
        }

        public void setAvailableRequirements(ArrayList<RequirementsTypes> availableRequirements) {
            AvailableRequirements = availableRequirements;
        }

        public String getSystemName() {
            return SystemName;
        }

        public void setSystemName(String systemName) {
            SystemName = systemName;
        }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ExistingSystem)) return false;
        else return((ExistingSystem) obj).getSystemName()== SystemName;
            }

}
