/*
 * Copyright All rights reserved.
 */

package xyz.muses.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author MiaoQiang
 * @date 2018/8/20.
 */
public class VelocityUtils {
    private static Logger logger = LoggerFactory.getLogger(VelocityUtils.class);

    private String classpathPre;
    private VelocityEngine velocityEngine;
    private ToolManager toolManager;

    public VelocityUtils(VelocityEngine velocityEngine, ToolManager toolManager, String classpathPre) {
        this.velocityEngine = velocityEngine;
        this.toolManager = toolManager;
        this.classpathPre = classpathPre;
    }

    /**
     * 利用模板文件.vm
     *
     * @param name
     * @param context
     * @return
     */
    public String parseVMTemplate(String name, Map<String, Object> context) {
        StringWriter sw = new StringWriter();
        try {
            Template template = this.velocityEngine.getTemplate(name);
            ToolContext toolContext = this.toolManager.createContext();
            if (MapUtils.isNotEmpty(context)) {
                toolContext.putAll(context);
            }
            template.merge(toolContext, sw);
            return sw.toString();
        } finally {
            try {
                sw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 利用模板文件.vm
     *
     * @param name
     * @param context
     * @return
     */
    public String parseVMTemplateClasspath(String name, Map<String, Object> context) {
        StringWriter sw = new StringWriter();
        try {
            Template template = this.velocityEngine.getTemplate(classpathPre + name);
            ToolContext toolContext = this.toolManager.createContext();
            if (MapUtils.isNotEmpty(context)) {
                toolContext.putAll(context);
            }
            template.merge(toolContext, sw);
            return sw.toString();
        } finally {
            try {
                sw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
