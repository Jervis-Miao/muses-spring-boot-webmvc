/**
 * Copyright 2022 All rights reserved.
 */

package cn.muses;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author jervis
 * @date 2022/11/14.
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableAsync(proxyTargetClass = true)
@EnableAspectJAutoProxy(exposeProxy = true)
public class JunitTest {
}
