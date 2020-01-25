package ru.tweekyone.graduateQualificationWork.databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import ru.tweekyone.graduateQualificationWork.gui.MarkupController;
import ru.tweekyone.graduateQualificationWork.objects.RegionMargin;
import ru.tweekyone.graduateQualificationWork.objects.ZoneMargin;

/**
 *
 * @author Пирожок
 */
public class RegionMarginDataAccess {
    private static final String GET_MARGIN_DATA = "SELECT * FROM REGION LEFT JOIN "
            + "ZONE_MARGIN ON REGION.ID = ZONE_MARGIN.ID_REGION WHERE REGION.ID = ?";
    private static final String SET_MARGIN_DATA = "UPDATE zone_margin SET WR_UP_TO_50 = ?, WR_OVER_50_TO_500 = ?, WR_OVER_500 = ?, RR_UP_TO_50 = ?,"
+ " RR_OVER_50_TO_500 = ?, RR_OVER_500 = ? WHERE ID = ?";
    private static final String GET_REGIONS = "SELECT REGION FROM USERNAME.REGION";    
    
    public static LinkedList<String> getRegionsList(){
        LinkedList<String> result = new LinkedList<>();
        try(Connection con = ConnectionPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(GET_REGIONS);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                result.add(resultSet.getString(1));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally{
            return result;
        }
    }
    
    public static RegionMargin getRegionMargin(int regionId){
        LinkedList<ZoneMargin> zoneMargin = new LinkedList<>();
        RegionMargin regionMargin = new RegionMargin();
        try(Connection con = ConnectionPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(GET_MARGIN_DATA);
            ps.setInt(1, regionId);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            regionMargin.setId(resultSet.getInt(1));
            regionMargin.setRegion(resultSet.getString(2));
            regionMargin.setHasZone(resultSet.getBoolean(3));
            zoneMargin.add(new ZoneMargin(resultSet.getInt(4), regionMargin.getId(), resultSet.getString(6), 
                            resultSet.getString(7), resultSet.getDouble(8), 
                            resultSet.getDouble(9), resultSet.getDouble(10), 
                            resultSet.getDouble(11), resultSet.getDouble(12),
                            resultSet.getDouble(13)));
            while(resultSet.next()){
                zoneMargin.add(new ZoneMargin(resultSet.getInt(4), regionMargin.getId(), resultSet.getString(6), 
                                resultSet.getString(7), resultSet.getDouble(8), 
                                resultSet.getDouble(9), resultSet.getDouble(10), 
                                resultSet.getDouble(11), resultSet.getDouble(12),
                                resultSet.getDouble(13)));
            }
            regionMargin.setZoneMargin(zoneMargin);
        } catch (SQLException e){
            e.printStackTrace();
        } finally{
            return regionMargin;
        }
    }
    
    public static boolean setRegionMargin(MarkupController mc){
        if(mc.isFloatCheck()){
            try(Connection con = ConnectionPool.getConnection()){
                PreparedStatement ps = con.prepareStatement(SET_MARGIN_DATA);
                ps.setFloat(1, mc.getTfWhol_50Value());
                ps.setFloat(2, mc.getTfWhol_50_500Value());
                ps.setFloat(3, mc.getTfWhol_500Value());
                ps.setFloat(4, mc.getTfRet_50Value());
                ps.setFloat(5, mc.getTfRet_50_500Value());
                ps.setFloat(6, mc.getTfRet_500Value());
                ps.setInt(7, mc.getCurrentZoneId());
                ps.executeUpdate();
                return true;
            } catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        } return false;
    }
}
