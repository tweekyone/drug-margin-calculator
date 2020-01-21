package ru.tweekyone.graduateQualificationWork.objects;

/**
 *
 * @author Пирожок
 */
public class DrugInfo {
    private final String MNN;
    private final String TN;
    private final String SPEC;
    private final String OWNER;
    private final int AMOUNT;
    private float ownerPrice;
    private float wholesalePrice;
    private float retailPrice;
    private final String REGNO;
    private final String DATE;

    public DrugInfo(String mnn, String tn, String spec, String owner, int amount,
                    float ownerPrice, String regno, String date){
        this.MNN = mnn;
        this.TN = tn;
        this.SPEC = spec;
        this.OWNER = owner;
        this.AMOUNT = amount;
        this.ownerPrice = ownerPrice;
        this.REGNO = regno;
        this.DATE = date;
    }
    
    public String getMNN() {
        return MNN;
    }

    public String getTN() {
        return TN;
    }

    public String getSPEC() {
        return SPEC;
    }

    public String getOWNER() {
        return OWNER;
    }

    public int getAMOUNT() {
        return AMOUNT;
    }

    public float getOwnerPrice() {
        return ownerPrice;
    }

    public float getWholesalePrice() {
        return wholesalePrice;
    }

    public float getRetailPrice() {
        return retailPrice;
    }

    public String getREGNO() {
        return REGNO;
    }

    public String getDATE() {
        return DATE;
    }

    public void setOwnerPrice(float ownerPrice) {
        this.ownerPrice = ownerPrice;
    }

    public void setWholesalePrice(float wholesalePriceProc) {
        this.wholesalePrice = ownerPrice + ownerPrice*wholesalePriceProc/100;
    }

    public void setRetailPrice(float retailPriceProc) {
        this.retailPrice = retailPrice + retailPrice*retailPriceProc/100;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(this == obj){
            return true;
        } 
        if(!(obj instanceof DrugInfo)){
            return false;
        }
        DrugInfo di = (DrugInfo) obj;
        return di.getREGNO().equals(this.getREGNO()) && di.getSPEC().equals(this.getSPEC());
    }
    
    @Override
    public int hashCode(){
        return this.getREGNO().hashCode() + (int) this.getOwnerPrice() + 
                (int) this.getWholesalePrice() + (int) this.getRetailPrice();
    }
}
