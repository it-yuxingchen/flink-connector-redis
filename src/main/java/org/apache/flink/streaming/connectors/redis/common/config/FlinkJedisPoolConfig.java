package org.apache.flink.streaming.connectors.redis.common.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.Objects;

/** Configuration for Jedis pool. */
public class FlinkJedisPoolConfig extends FlinkJedisConfigBase {

    private static final long serialVersionUID = 1L;

    private final String host;
    private final int port;
    private final int database;

    /**
     * Jedis pool configuration. The host is mandatory, and when host is not set, it throws
     * NullPointerException.
     *
     * @param host hostname or IP
     * @param port port, default value is 6379
     * @param connectionTimeout socket / connection timeout, default value is 2000 milli second
     * @param password password, if any
     * @param database database index
     * @param maxTotal the maximum number of objects that can be allocated by the pool, default
     *     value is 8
     * @param maxIdle the cap on the number of "idle" instances in the pool, default value is 8
     * @param minIdle the minimum number of idle objects to maintain in the pool, default value is 0
     * @throws NullPointerException if parameter {@code host} is {@code null}
     */
    private FlinkJedisPoolConfig(
            String host,
            int port,
            int connectionTimeout,
            String password,
            int database,
            int maxTotal,
            int maxIdle,
            int minIdle) {
        super(connectionTimeout, maxTotal, maxIdle, minIdle, password);
        Objects.requireNonNull(host, "Host information should be presented");
        this.host = host;
        this.port = port;
        this.database = database;
    }

    /**
     * Returns host.
     *
     * @return hostname or IP
     */
    public String getHost() {
        return host;
    }

    /**
     * Returns port.
     *
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns database index.
     *
     * @return database index
     */
    public int getDatabase() {
        return database;
    }

    /** Builder for initializing {@link FlinkJedisPoolConfig}. */
    public static class Builder {
        private String host;
        private int port = Protocol.DEFAULT_PORT;
        private int timeout = Protocol.DEFAULT_TIMEOUT;
        private int database = Protocol.DEFAULT_DATABASE;
        private String password;
        private int maxTotal = GenericObjectPoolConfig.DEFAULT_MAX_TOTAL;
        private int maxIdle = GenericObjectPoolConfig.DEFAULT_MAX_IDLE;
        private int minIdle = GenericObjectPoolConfig.DEFAULT_MIN_IDLE;

        /**
         * Sets value for the {@code maxTotal} configuration attribute for pools to be created with
         * this configuration instance.
         *
         * @param maxTotal maxTotal the maximum number of objects that can be allocated by the pool,
         *     default value is 8
         * @return Builder itself
         */
        public Builder setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
            return this;
        }

        /**
         * Sets value for the {@code maxIdle} configuration attribute for pools to be created with
         * this configuration instance.
         *
         * @param maxIdle the cap on the number of "idle" instances in the pool, default value is 8
         * @return Builder itself
         */
        public Builder setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
            return this;
        }

        /**
         * Sets value for the {@code minIdle} configuration attribute for pools to be created with
         * this configuration instance.
         *
         * @param minIdle the minimum number of idle objects to maintain in the pool, default value
         *     is 0
         * @return Builder itself
         */
        public Builder setMinIdle(int minIdle) {
            this.minIdle = minIdle;
            return this;
        }

        /**
         * Sets host.
         *
         * @param host host
         * @return Builder itself
         */
        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        /**
         * Sets port.
         *
         * @param port port, default value is 6379
         * @return Builder itself
         */
        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        /**
         * Sets timeout.
         *
         * @param timeout timeout, default value is 2000
         * @return Builder itself
         */
        public Builder setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        /**
         * Sets database index.
         *
         * @param database database index, default value is 0
         * @return Builder itself
         */
        public Builder setDatabase(int database) {
            this.database = database;
            return this;
        }

        /**
         * Sets password.
         *
         * @param password password, if any
         * @return Builder itself
         */
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        /**
         * Builds JedisPoolConfig.
         *
         * @return JedisPoolConfig
         */
        public FlinkJedisPoolConfig build() {
            return new FlinkJedisPoolConfig(
                    host, port, timeout, password, database, maxTotal, maxIdle, minIdle);
        }
    }

    @Override
    public String toString() {
        return "JedisPoolConfig{"
                + "host='"
                + host
                + '\''
                + ", port="
                + port
                + ", timeout="
                + connectionTimeout
                + ", database="
                + database
                + ", maxTotal="
                + maxTotal
                + ", maxIdle="
                + maxIdle
                + ", minIdle="
                + minIdle
                + '}';
    }
}
