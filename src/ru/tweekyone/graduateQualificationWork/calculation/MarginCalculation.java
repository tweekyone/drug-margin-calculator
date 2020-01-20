package ru.tweekyone.graduateQualificationWork.calculation;

import ru.tweekyone.graduateQualificationWork.gui.MarckupPanel;

/**
 *
 * @author Пирожок
 */
public class MarginCalculation {
    public static String getWholesaleMargin(Boolean isVAT, String ownerPrice, MarckupPanel mp){
        float fOwnerPrice = Float.parseFloat(ownerPrice);
        if(isVAT){
            
        } else{
            
        }
    }
    
    private static Float getWholesaleMarkup (float fOwnerPrice, MarckupPanel mp){
        if (fOwnerPrice < 50){
            return Float.parseFloat(mp.getTfWhol_50Value());
        } else if(fOwnerPrice >= 50 && fOwnerPrice < 500){
            return Float.parseFloat(mp.getTfWhol_50_500Value());
        } else{
            return Float.parseFloat(mp.getTfWhol_500Value());
        }
    }
    
    private static Float getRetailMarkup (float fOwnerPrice, MarckupPanel mp){
        if (fOwnerPrice < 50){
            return Float.parseFloat(mp.getTfRet_50Value());
        } else if(fOwnerPrice >= 50 && fOwnerPrice < 500){
            return Float.parseFloat(mp.getTfRet_50_500Value());
        } else{
            return Float.parseFloat(mp.getTfRet_500Value());
        }
    }
}
