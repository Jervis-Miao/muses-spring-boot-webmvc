/**
 * Copyright 2022 All rights reserved.
 */

package cn.muses.config.logback;

import ch.qos.logback.core.PropertyDefinerBase;

/**
 * @author jervis
 * @date 2022/11/15.
 */
public class LogRollingPropertyDefiner extends PropertyDefinerBase {

    @Override
    public String getPropertyValue() {
        return System.getProperty("logging.isRolling", "true");
    }
}
