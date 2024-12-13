/*
 * Copyright 2024 Jervis All Rights Reserved
 */
package xyz.muses.task;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RLock;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisException;
import org.redisson.client.codec.StringCodec;

import xyz.muses.constants.redis.lock.RedisLockConst;
import xyz.muses.framework.common.utils.MD5Utils;

/**
 * @author jervis
 * @date 2024/12/13
 */
public abstract class AbstractScriptTask extends AbstractTask {

    private final RedissonClient redisson;
    private final StringCodec stringCodec;
    private final String takeScript;
    private volatile String takeScriptSha;

    public AbstractScriptTask(RedissonClient redisson, StringCodec stringCodec, String takeScript) {
        this.redisson = redisson;
        this.stringCodec = stringCodec;
        this.takeScript = takeScript;
        this.takeScriptSha = redisson.getScript(stringCodec).scriptLoad(takeScript);
    }

    protected List<Object> evalShaData(List<Object> keys, Object... values) {
        try {
            return this.redisson.getScript(stringCodec).evalSha(RScript.Mode.READ_WRITE, this.takeScriptSha,
                RScript.ReturnType.MULTI, keys, values);
        } catch (RedisException e) {
            if (e.getMessage().contains("NOSCRIPT")) {
                RLock lock = this.redisson
                    .getLock(RedisLockConst.Lock.SCRIPT_LOAD_TASK.key() + MD5Utils.md5(this.takeScript));
                try {
                    lock.lock(3, TimeUnit.SECONDS);
                    List<Boolean> flags;
                    if (CollectionUtils
                        .isEmpty(flags = this.redisson.getScript(stringCodec).scriptExists(this.takeScriptSha))
                        || !flags.get(0)) {
                        // 如果脚本被清除，重新加载脚本
                        this.takeScriptSha = this.redisson.getScript(stringCodec).scriptLoad(takeScript);
                    }
                    // 重新执行脚本
                    return redisson.getScript(stringCodec).evalSha(RScript.Mode.READ_WRITE, this.takeScriptSha,
                        RScript.ReturnType.MULTI, keys, values);
                } catch (Exception e1) {
                    logger.error("scriptLoad exception", e1);
                } finally {
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            } else {
                logger.error("evalShaData exception", e);
            }
        }
        return null;
    }
}
