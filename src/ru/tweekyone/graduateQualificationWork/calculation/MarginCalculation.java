package ru.tweekyone.graduateQualificationWork.calculation;

import java.util.Locale;
import ru.tweekyone.graduateQualificationWork.gui.MarkupController;

public class MarginCalculation {
    public static String getWholesaleMargin(boolean withoutVat, float ownerPrice, MarkupController mp){
        float markup = ownerPrice*getWholesaleMarkup(ownerPrice, mp)/100;
        if(!withoutVat){
            float vat = (ownerPrice + markup)*10/100;
            return String.format(Locale.US, "%.2f", (ownerPrice + markup + vat));
        }else{
            float vat = ownerPrice*10/100;
            return String.format(Locale.US, "%.2f", (ownerPrice + markup + vat));       
        }
    }
    
    public static String getRetailMargin(boolean withoutVat, boolean withoutRetailVat, 
                                        float ownerPrice, String wholesalePrice, MarkupController mp){
        float markup = ownerPrice*getRetailMarkup(ownerPrice, mp)/100;
        if(!withoutVat){
            if(!withoutRetailVat){
                float marginPriceWithoutVAT = getWholesalePriceWithoutVAT(ownerPrice, mp) + markup;
                float vat = marginPriceWithoutVAT*10/100;
                return String.format(Locale.US, "%.2f", (marginPriceWithoutVAT + vat));
            }else{
                float marginPriceWithoutVAT = Float.parseFloat(getWholesaleMargin(true, ownerPrice, mp)) + markup;
                float vat = marginPriceWithoutVAT*10/100;
                return String.format(Locale.US, "%.2f", (marginPriceWithoutVAT + vat));
            }
        }else{
                float marginPrice = Float.parseFloat(getWholesaleMargin(withoutRetailVat, ownerPrice, mp));
                return String.format(Locale.US, "%.2f", (markup + marginPrice));
        }
    }
    
    private static float getWholesalePriceWithoutVAT(float fOwnerPrice, MarkupController mp){
        float markup = fOwnerPrice*getWholesaleMarkup(fOwnerPrice, mp)/100;
        return fOwnerPrice + markup;
    }
    
    private static Float getWholesaleMarkup (float fOwnerPrice, MarkupController mp){
        if (fOwnerPrice < 50){
            return mp.getTfWhol_50Value();
        }else if(fOwnerPrice >= 50 && fOwnerPrice < 500){
            return mp.getTfWhol_50_500Value();
        }else{
            return mp.getTfWhol_500Value();
        }
    }
    
    private static Float getRetailMarkup (float fOwnerPrice, MarkupController mp){
        if (fOwnerPrice < 50){
            return mp.getTfRet_50Value();
        }else if(fOwnerPrice >= 50 && fOwnerPrice < 500){
            return mp.getTfRet_50_500Value();
        }else{
            return mp.getTfRet_500Value();
        }
    }
}
