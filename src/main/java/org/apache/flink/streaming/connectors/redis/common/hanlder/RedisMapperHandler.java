/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.streaming.connectors.redis.common.hanlder;

import org.apache.flink.streaming.connectors.redis.common.config.RedisOptions;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Map;

import static org.apache.flink.streaming.connectors.redis.descriptor.RedisValidator.REDIS_KEY_TTL;
import static org.apache.flink.streaming.connectors.redis.descriptor.RedisValidator.REDIS_PARTITION_COLUMN;

/**
 * handler for create redis mapper.
 */
public interface RedisMapperHandler extends RedisHandler {

    Logger LOGGER = LoggerFactory.getLogger(RedisMapperHandler.class);

    /**
     * create a correct redis mapper use properties.
     * @param properties to create redis mapper.
     * @return redis mapper.
     */
    default RedisMapper createRedisMapper(Map<String, String> properties) {
        String ttl = properties.get(REDIS_KEY_TTL);
        String additionalKey = properties.get(RedisOptions.ADDITIONALKEY.key());
        String partitionColumn = properties.get(REDIS_PARTITION_COLUMN);
        try {
            Class redisMapper = Class.forName(this.getClass().getCanonicalName());

            if (ttl == null) {
                Constructor c = redisMapper.getConstructor(String.class, String.class);
                return (RedisMapper) c.newInstance(additionalKey, partitionColumn);
            }
            Constructor c = redisMapper.getConstructor( Integer.class, String.class, String.class);
            return (RedisMapper) c.newInstance(Integer.parseInt(ttl), additionalKey, partitionColumn);
        } catch (Exception e) {
            LOGGER.error("create redis mapper failed", e);
            throw new RuntimeException(e);
        }
    }

}
