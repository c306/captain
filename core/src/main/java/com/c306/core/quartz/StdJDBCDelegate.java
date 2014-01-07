package com.c306.core.quartz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.jdbcjobstore.Constants;

public class StdJDBCDelegate extends
		org.quartz.impl.jdbcjobstore.StdJDBCDelegate {
	

    String SELECT_PAUSED_TRIGGERS = "SELECT "
        + COL_TRIGGER_NAME + ", " + COL_TRIGGER_GROUP
        + " FROM "
        + TABLE_PREFIX_SUBST
        + TABLE_TRIGGERS
        + " WHERE "
        + COL_SCHEDULER_NAME + " = " + SCHED_NAME_SUBST + " and "
        + "("
        + COL_TRIGGER_STATE + " = ? or " + COL_TRIGGER_STATE + " = ?)";
    
    String SELECT_TRIGGERS_STATE = "SELECT "
    		+ COL_TRIGGER_NAME + ", " + COL_TRIGGER_GROUP + ", " + COL_TRIGGER_STATE
    		+ " FROM "
    		+ TABLE_PREFIX_SUBST
    		+ TABLE_TRIGGERS
    		+ " WHERE "
    		+ COL_SCHEDULER_NAME + " = " + SCHED_NAME_SUBST + " and "
    		+ COL_TRIGGER_NAME + " = ? and " + COL_TRIGGER_GROUP + " = ?";;

    String SELECT_SCHEDULER_INSTANCES = "SELECT * FROM "
        + TABLE_PREFIX_SUBST
        + TABLE_SCHEDULER_STATE;
	
	

    public Set<TriggerKey> selectPausedTriggers(Connection conn) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
    		Set<TriggerKey> set = new HashSet<TriggerKey>();
            String group = null;
            String name = null;

            ps = conn.prepareStatement(rtp(SELECT_PAUSED_TRIGGERS));
            ps.setString(1, Constants.STATE_PAUSED);
            ps.setString(2, Constants.STATE_PAUSED_BLOCKED);
            rs = ps.executeQuery();

            while (rs.next()) {
                name = rs.getString(COL_TRIGGER_NAME);
                group = rs.getString(COL_TRIGGER_GROUP);
                
                set.add(new TriggerKey(name, group));
            }

            return set;
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
        }

    }
    
    public Map<String, String> selectTriggersState(Connection conn, List<Trigger> triggerList) throws SQLException {
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	HashMap<String, String> map = new HashMap<String, String>(triggerList.size());
    	
    	for( Trigger trigger : triggerList ) {
        	try {
        		String group = null;
        		String name = null;
        		String state = null;
        		
        		ps = conn.prepareStatement(rtp(SELECT_TRIGGERS_STATE));
        		ps.setString(1, trigger.getKey().getName());
        		ps.setString(2, trigger.getKey().getGroup());
        		rs = ps.executeQuery();
        		
        		while (rs.next()) {
        			name = rs.getString(COL_TRIGGER_NAME);
        			group = rs.getString(COL_TRIGGER_GROUP);
        			state = rs.getString(COL_TRIGGER_STATE);
        			
        			map.put((group + "." + name), state);
        		}
        		
        	} finally {
        		closeResultSet(rs);
        		closeStatement(ps);
        	}
    		
    	}// end for triggerList
		return map;
    }
    
//    
//    public Set<TriggerKey> selectSchedulerInstances(Connection conn) throws SQLException {
//    	PreparedStatement ps = null;
//    	ResultSet rs = null;
//    	
//    	try {
//    		Set<TriggerKey> map = new HashSet<TriggerKey>();
//    		String group = null;
//    		String name = null;
//    		
//    		ps = conn.prepareStatement(rtp(SELECT_SCHEDULER_INSTANCES));
//    		rs = ps.executeQuery();
//    		
//    		if (rs.next()) {
//    			group = rs.getString(COL_);
//    			name = rs.getString(COL_TRIGGER_GROUP);
//    			
//    			map.add(new TriggerKey(group, name));
//    		}
//    		
//    		return map;
//    	} finally {
//    		closeResultSet(rs);
//    		closeStatement(ps);
//    	}
//    	
//    }

}
