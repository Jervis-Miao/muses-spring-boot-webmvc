/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author jervis
 * @date 2020/12/1.
 */
public class TestDemo extends JunitTest {
	@Value("${spring.application.name}")
	private String applicationName;

	@Test
	public void test() {
		System.out.println(applicationName);
	}
}
