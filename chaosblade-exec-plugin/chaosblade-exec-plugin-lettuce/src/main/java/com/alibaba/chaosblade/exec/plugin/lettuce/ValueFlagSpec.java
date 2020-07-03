package com.alibaba.chaosblade.exec.plugin.lettuce;

import com.alibaba.chaosblade.exec.common.model.FlagSpec;

/**
 * @author yefei
 */
public class ValueFlagSpec implements FlagSpec {

    @Override
    public String getName() {
        return "value";
    }

    @Override
    public String getDesc() {
        return "value set";
    }

    @Override
    public boolean noArgs() {
        return false;
    }

    @Override
    public boolean required() {
        return false;
    }
}
