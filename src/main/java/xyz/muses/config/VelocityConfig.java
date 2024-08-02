package xyz.muses.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.ToolManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.velocity.VelocityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.alibaba.boot.velocity.VelocityConstants;

import xyz.muses.utils.VelocityUtils;

/**
 * Velocity bean 配置
 *
 * @author Jervis.
 * @date 2021/02/02 16:05
 * @see
 * @since
 */
@Configuration
public class VelocityConfig {

    @ConditionalOnClass(VelocityEngine.class)
    @ConditionalOnProperty({VelocityConstants.VELOCITY_ENABLED_PROPERTY_NAME,
        VelocityConstants.VELOCITY_TOOLBOX_CONFIG_LOCATION_PROPERTY_NAME})
    @ConditionalOnMissingBean(ToolManager.class)
    @Bean
    public ToolManager toolManager(VelocityEngine velocityEngine, VelocityProperties velocityProperties) {
        ToolManager toolManager = new ToolManager(false, false);
        toolManager.setVelocityEngine(velocityEngine);
        // ToolManager only support load config resource from classpath(without "classpath:") or filesystem
        String toolConfigLocation = StringUtils.remove(velocityProperties.getToolboxConfigLocation(),
            ResourceUtils.CLASSPATH_URL_PREFIX).trim();
        toolManager.configure(toolConfigLocation);
        return toolManager;
    }

    @Bean
    public VelocityUtils velocityUtil(VelocityEngine velocityEngine, ToolManager toolManager,
        VelocityProperties velocityProperties) {
        String toolConfigLocation = StringUtils.remove(velocityProperties.getResourceLoaderPath(),
            ResourceUtils.CLASSPATH_URL_PREFIX).trim();
        return new VelocityUtils(velocityEngine, toolManager, toolConfigLocation);
    }
}
