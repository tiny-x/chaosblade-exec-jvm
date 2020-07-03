package com.alibaba.chaosblade.exec.plugin.lettuce;

import com.alibaba.chaosblade.exec.common.aop.EnhancerModel;
import com.alibaba.chaosblade.exec.common.model.action.ActionExecutor;
import com.alibaba.chaosblade.exec.common.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author yefei
 */
public class LettuceActionExecutor implements ActionExecutor {

    private static final Logger logger = LoggerFactory.getLogger(LettuceEnhancer.class);

    private ValueFlagSpec valueFlagSpec;

    public LettuceActionExecutor(ValueFlagSpec valueFlagSpec) {
        this.valueFlagSpec = valueFlagSpec;
    }

    @Override
    public void run(EnhancerModel enhancerModel) throws Exception {
        Object command = enhancerModel.getMethodArguments()[1];
        Object args = ReflectUtil.getFieldValue(command, "command", false);
        Object commandArgs = ReflectUtil.getFieldValue(args, "args", false);
        List singularArguments = ReflectUtil.getFieldValue(commandArgs, "singularArguments", false);

        Object valArgument = singularArguments.get(1);
        Object originVal = ReflectUtil.getFieldValue(valArgument, "val", false);
        if (!(originVal instanceof String)) {
            logger.info("not support value, value type: {}", originVal.getClass());
            return;
        }

        Object codec = ReflectUtil.getFieldValue(valArgument, "codec", false);

        String value = enhancerModel.getActionFlag(valueFlagSpec.getName());
        Object[] arguments = new Object[]{value, codec};
        Object valueArgument = ReflectUtil.invokeStaticMethod(valArgument.getClass(), "of", arguments, false);

        if (valueArgument != null) {
            logger.info("update value success. origin value: {}, update value: {}", originVal, value);
            singularArguments.set(1, valueArgument);
        }
    }
}
