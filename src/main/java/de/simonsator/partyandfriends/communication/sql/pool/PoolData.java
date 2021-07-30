package de.simonsator.partyandfriends.communication.sql.pool;

import java.util.Locale;

/**
 * @author Simonsator
 * @version 1.0.0 11.04.17
 */
public class PoolData {
	public final int MIN_POOL_SIZE;
	public final int MAX_POOL_SIZE;
	public final int INITIAL_POOL_SIZE;
	public final int IDLE_CONNECTION_TEST_PERIOD;
	public final boolean TEST_CONNECTION_ON_CHECKIN;
	public final String CONNECTION_POOL;

	@Deprecated
	public PoolData(int pMinPoolSize, int pMaxPoolSize, int pInitialPoolSize, int pIdleConnectionTestPeriod, boolean pTestConnectionOnCheckin) {
		this(pMinPoolSize, pMaxPoolSize, pInitialPoolSize, pIdleConnectionTestPeriod, pTestConnectionOnCheckin, "C3P0");
	}

	public PoolData(int pMinPoolSize, int pMaxPoolSize, int pInitialPoolSize, int pIdleConnectionTestPeriod, boolean pTestConnectionOnCheckin, String pConnectionPool) {
		MIN_POOL_SIZE = pMinPoolSize;
		MAX_POOL_SIZE = pMaxPoolSize;
		INITIAL_POOL_SIZE = pInitialPoolSize;
		IDLE_CONNECTION_TEST_PERIOD = pIdleConnectionTestPeriod;
		TEST_CONNECTION_ON_CHECKIN = pTestConnectionOnCheckin;
		CONNECTION_POOL = pConnectionPool.toUpperCase(Locale.ROOT);
	}

	@Deprecated
	public PoolData(int pMinPoolSize, int pMaxPoolSize, int pInitialPoolSize) {
		this(pMinPoolSize, pMaxPoolSize, pInitialPoolSize, 290, false);
	}
}
