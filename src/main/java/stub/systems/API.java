package stub.systems;

import stub.exceptions.IncorrectRequirementsystemMaping;
import stub.helpers.RequirementsTypes;
import stub.rest.SystemA;

import java.util.ArrayList;

import static stub.helpers.GlobalValues.APIs;

public class API {
    ArrayList<ExistingSystem> Targets;
    RequirementsTypes Requirement;
    String url;

    public API(RequirementsTypes Requirement, String url, ExistingSystem Target){
        this.Requirement = Requirement;
        Targets = new ArrayList<ExistingSystem>();
        addTarget(Target);
        this.url = url;
    }

    public void addTarget(ExistingSystem target){
        if(!Targets.contains(target)) {
            System.out.println("Target added "+ target.getSystemName());
            Targets.add(target);
        }
    }

    @Override
    public boolean equals(Object api){
        if(!(api instanceof API)) return false;
        else {
            if (Requirement != ((API) api).Requirement) return false;
            else {
                for (ExistingSystem Target : ((API) api).Targets) {
                    if (!Targets.contains(Target)) return false;
                }
            }
        }
        return true;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static API findSuitableAPI(RequirementsTypes Requirement, ExistingSystem Target) throws IncorrectRequirementsystemMaping {
        API API = null;
        for (API A : APIs) {
            if (A.equals(new API(Requirement, "", Target))) {
                API = A;
                break;
            }
        }
        if (API == null) throw new IncorrectRequirementsystemMaping( Requirement.toString(), Target.getSystemName());
        return API;
    }

    public ArrayList<ExistingSystem> getTargets() {
        return Targets;
    }

    public void setTargets(ArrayList<ExistingSystem> targets) {
        Targets = targets;
    }

    public RequirementsTypes getRequirement() {
        return Requirement;
    }

    public void setRequirement(RequirementsTypes requirement) {
        Requirement = requirement;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
