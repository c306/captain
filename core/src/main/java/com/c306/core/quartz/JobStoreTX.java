package com.c306.core.quartz;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.JobPersistenceException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public class JobStoreTX extends org.quartz.impl.jdbcjobstore.JobStoreTX {

	protected Set<TriggerKey> selectPausedTriggers(Connection conn) throws JobPersistenceException {
		
        try {
        	return ((StdJDBCDelegate)this.getDelegate()).selectPausedTriggers(conn);
        } catch (Exception e) {
            throw new JobPersistenceException( "Couldn't obtain paused triggers: " + e.getMessage(), e);
        }
	}

	@SuppressWarnings("unchecked")
	public Set<TriggerKey> selectPausedTriggers() throws JobPersistenceException {
		return (Set<TriggerKey>) executeWithoutLock( // no locks necessary for read...
			new TransactionCallback() {
				public Object execute(Connection conn) throws JobPersistenceException {
					return selectPausedTriggers(conn);
				}
			});
	}
	
	
	protected Map<String, String> selectTriggersState(Connection conn, List<Trigger> triggerList) throws JobPersistenceException {
		
		try {
			return ((StdJDBCDelegate)this.getDelegate()).selectTriggersState(conn, triggerList);
		} catch (Exception e) {
			throw new JobPersistenceException( "Couldn't obtain triggers: " + e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> selectTriggersState(final List<Trigger> triggerList) throws JobPersistenceException {
		return (Map<String, String>) executeWithoutLock( // no locks necessary for read...
				new TransactionCallback() {
					public Object execute(Connection conn) throws JobPersistenceException {
						return selectTriggersState(conn, triggerList);
					}
				});
	}

}
