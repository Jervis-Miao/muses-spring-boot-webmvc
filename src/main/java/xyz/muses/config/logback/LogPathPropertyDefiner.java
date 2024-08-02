/**
 * Copyright 2022 All rights reserved.
 */

package xyz.muses.config.logback;

import ch.qos.logback.core.PropertyDefinerBase;

/**
 * @author jervis
 * @date 2022/11/15.
 */
public class LogPathPropertyDefiner extends PropertyDefinerBase {

    @Override
    public String getPropertyValue() {
        return System.getProperty("logging.path", "/Volumes/workspace/muses/log/webmvc");
    }
}
