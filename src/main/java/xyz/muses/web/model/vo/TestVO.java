/**
 * Copyright 2022 All rights reserved.
 */

package xyz.muses.web.model.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author jervis
 * @date 2022/11/15.
 */
public class TestVO implements Serializable {
    private static final long serialVersionUID = 6880149966298024937L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("name", name)
            .toString();
    }
}
