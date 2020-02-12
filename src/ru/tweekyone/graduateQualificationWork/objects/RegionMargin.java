package ru.tweekyone.graduateQualificationWork.objects;

import java.util.LinkedList;

public class RegionMargin {
    private int id;
    private String region;
    private boolean hasZone;
    private LinkedList<ZoneMargin> zoneMargin;
    
    public RegionMargin(){
        
    }
    
    public RegionMargin(int id, String region, boolean hasZone, LinkedList<ZoneMargin> zoneMargin){
        this.id = id;
        this.region = region;
        this.hasZone = hasZone;
        this.zoneMargin = zoneMargin;
    }

    public int getId() {
        return id;
    }

    public String getRegion() {
        return region;
    }

    public boolean hasZone() {
        return hasZone;
    }

    public LinkedList<ZoneMargin> getZoneMargin() {
        return zoneMargin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setHasZone(boolean hasZone) {
        this.hasZone = hasZone;
    }

    public void setZoneMargin(LinkedList<ZoneMargin> zoneMargin) {
        this.zoneMargin = zoneMargin;
    }
    
    public String[] getZoneArray(){
        String[] zoneArray = new String[zoneMargin.size()];
        for (int i = 0; i < zoneMargin.size(); i++){
            zoneArray[i] = zoneMargin.get(i).getZone();
        }
        return zoneArray;
    }
}
