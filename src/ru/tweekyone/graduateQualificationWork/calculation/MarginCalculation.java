package ru.tweekyone.graduateQualificationWork.calculation;

import ru.tweekyone.graduateQualificationWork.gui.MarkupController;

/**
 *
 * @author Пирожок
 */
public class MarginCalculation {
    public static String getWholesaleMargin(boolean isVAT, String ownerPrice, MarkupController mp){
        float fOwnerPrice = Float.parseFloat(ownerPrice);
        float markup = fOwnerPrice*getWholesaleMarkup(fOwnerPrice, mp)/100;
        if(isVAT){
            float vat = (fOwnerPrice + markup)*10/100;
            return Float.toString(fOwnerPrice + markup + vat);
        }else{
            float vat = fOwnerPrice*10/100;
            return Float.toString(fOwnerPrice + markup + vat);            
        }
    }
    
    public static String getRetailMargin(boolean isVAT, boolean isWholesaleVAT, 
                                        String ownerPrice, String wholesalePrice, MarkupController mp){
        float fOwnerPrice = Float.parseFloat(ownerPrice);
        float markup = fOwnerPrice*getRetailMarkup(fOwnerPrice, mp)/100;
        if(isVAT){
            if(isWholesaleVAT){
                float marginPriceWithoutVAT = getWholesalePriceWithoutVAT(fOwnerPrice, mp) + markup;
                float vat = marginPriceWithoutVAT*10/100;
                return Float.toString(marginPriceWithoutVAT + vat);
            }else{
                float marginPriceWithoutVAT = Float.parseFloat(getWholesaleMargin(false, ownerPrice, mp)) + markup;
                float vat = marginPriceWithoutVAT*10/100;
                return Float.toString(marginPriceWithoutVAT + vat);
            }
        }else{
            if(isWholesaleVAT){
                float marginPrice = Float.parseFloat(getWholesaleMargin(true, ownerPrice, mp));
                return Float.toString(markup + marginPrice);
            }else{
                float marginPrice = Float.parseFloat(getWholesaleMargin(false, ownerPrice, mp));
                return Float.toString(markup + marginPrice);
            }
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
