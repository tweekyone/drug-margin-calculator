package ru.tweekyone.graduateQualificationWork.calculation;

import java.util.Locale;
import ru.tweekyone.graduateQualificationWork.gui.MarkupController;

/**
 *
 * @author Пирожок
 */
public class MarginCalculation {
    public static String getWholesaleMargin(boolean isVAT, float ownerPrice, MarkupController mp){;
        float markup = ownerPrice*getWholesaleMarkup(ownerPrice, mp)/100;
        if(isVAT){
            float vat = (ownerPrice + markup)*10/100;
            return String.format(Locale.US, "%.2f", (ownerPrice + markup + vat));
        }else{
            float vat = ownerPrice*10/100;
            return String.format(Locale.US, "%.2f", (ownerPrice + markup + vat));       
        }
    }
    
    public static String getRetailMargin(boolean isVAT, boolean isRetailVAT, 
                                        float ownerPrice, String wholesalePrice, MarkupController mp){
        float markup = ownerPrice*getRetailMarkup(ownerPrice, mp)/100;
        if(isVAT){
            if(isRetailVAT){
                float marginPriceWithoutVAT = getWholesalePriceWithoutVAT(ownerPrice, mp) + markup;
                float vat = marginPriceWithoutVAT*10/100;
                return String.format(Locale.US, "%.2f", (marginPriceWithoutVAT + vat));
            }else{
                float marginPriceWithoutVAT = Float.parseFloat(getWholesaleMargin(false, ownerPrice, mp)) + markup;
                float vat = marginPriceWithoutVAT*10/100;
                return String.format(Locale.US, "%.2f", (marginPriceWithoutVAT + vat));
            }
        }else{
                float marginPrice = Float.parseFloat(getWholesaleMargin(isRetailVAT, ownerPrice, mp));
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
