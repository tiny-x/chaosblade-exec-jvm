package com.alibaba.chaosblade.exec.plugin.lettuce;

import com.alibaba.chaosblade.exec.common.aop.BeforeEnhancer;
import com.alibaba.chaosblade.exec.common.aop.EnhancerModel;
import com.alibaba.chaosblade.exec.common.model.matcher.MatcherModel;
import com.alibaba.chaosblade.exec.common.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yefei
 */
public class LettuceEnhancer extends BeforeEnhancer {

    private static final Logger logger = LoggerFactory.getLogger(LettuceEnhancer.class);

    private static final  List<String> SUPPORTS_COMMANDS = new ArrayList<String>();

    static {
        SUPPORTS_COMMANDS.add("SET");
        SUPPORTS_COMMANDS.add("SETNX");
        SUPPORTS_COMMANDS.add("HSET");
    }

    @Override
    public EnhancerModel doBeforeAdvice(ClassLoader classLoader,
                                        String className,
                                        Object object,
                                        Method method,
                                        Object[] methodArguments) throws Exception {

        Object command = methodArguments[1];
        Object args = ReflectUtil.getFieldValue(command, "command", false);

        Object commandType = ReflectUtil.getFieldValue(args, "type", false);
        if (!SUPPORTS_COMMANDS.contains(String.valueOf(commandType))) {
            return null;
        }

        Object commandArgs = ReflectUtil.getFieldValue(args, "args", false);
        List singularArguments = ReflectUtil.getFieldValue(commandArgs, "singularArguments", false);
        Object keyArgument = singularArguments.get(0);

        MatcherModel matcherModel = new MatcherModel();
        if (keyArgument == null) {
            return null;
        }
        Object key = ReflectUtil.getFieldValue(keyArgument, "key", false);
        matcherModel.add("key", key);
        logger.debug("lettuce matchers: {}", JSON.toJSONString(matcherModel));
        return new EnhancerModel(classLoader, matcherModel);
    }
}
