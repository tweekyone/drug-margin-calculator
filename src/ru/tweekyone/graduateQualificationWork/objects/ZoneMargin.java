package ru.tweekyone.graduateQualificationWork.objects;

/**
 *
 * @author Пирожок
 */
public class ZoneMargin {
    private final int regionId;
    private final String zone;
    private final String consist;
    private double wrUpTo50;
    private double wrOver50to500;
    private double wrOver500;    
    private double rrUpTo50;
    private double rrOver50to500;
    private double rrOver500;
    
    public ZoneMargin(int regionId, String zone, String consist, double wrUpTo50, 
        double wrOver50to500, double wrOver500, double rrUpTo50, 
        double rrOver50to500, double rrOver500){
        this.regionId = regionId;
        this.zone = zone;
        this.consist = consist;
        this.wrUpTo50 = wrUpTo50;
        this.wrOver50to500 = wrOver50to500;
        this.wrOver500 = wrOver500;
        this.rrUpTo50 = rrUpTo50;
        this.rrOver50to500 = rrOver50to500;
        this.rrOver500 = rrOver500;
    }

    public String getZone() {
        return zone;
    }

    public String getConsist() {
        return consist;
    }
    
    public void setWrUpTo50(double wrUpTo50) {
        this.wrUpTo50 = wrUpTo50;
    }

    public void setWrOver50to500(double wrOver50to500) {
        this.wrOver50to500 = wrOver50to500;
    }

    public void setWrOver500(double wrOver500) {
        this.wrOver500 = wrOver500;
    }

    public void setRrUpTo50(double rrUpTo50) {
        this.rrUpTo50 = rrUpTo50;
    }

    public void setRrOver50to500(double rrOver50to500) {
        this.rrOver50to500 = rrOver50to500;
    }

    public void setRrOver500(double rrOver500) {
        this.rrOver500 = rrOver500;
    }

    public int getRegionId() {
        return regionId;
    }

    public double getWrUpTo50() {
        return wrUpTo50;
    }

    public double getWrOver50to500() {
        return wrOver50to500;
    }

    public double getWrOver500() {
        return wrOver500;
    }

    public double getRrUpTo50() {
        return rrUpTo50;
    }

    public double getRrOver50to500() {
        return rrOver50to500;
    }

    public double getRrOver500() {
        return rrOver500;
    }
    
}
