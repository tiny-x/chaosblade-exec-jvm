package com.alibaba.chaosblade.exec.plugin.lettuce;

import com.alibaba.chaosblade.exec.common.model.FrameworkModelSpec;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yefei
 */
public class LettuceModeSpec extends FrameworkModelSpec {

    @Override
    public List<MatcherSpec> createNewMatcherSpecs() {
        addActionSpec(new UpdateActionSpec());

        List<MatcherSpec> matchers = new ArrayList<MatcherSpec>();
        matchers.add(new KeyMatcherSpec());
        return matchers;
    }

    @Override
    public String getTarget() {
        return "lettuce";
    }

    @Override
    public String getShortDesc() {
        return "redis client lettuce experiment";
    }

    @Override
    public String getLongDesc() {
        return "redis client lettuce experiment";
    }

    @Override
    public String getExample() {
        return "lettuce update --key=name --value=meepo";
    }
}
