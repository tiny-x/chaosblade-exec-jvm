package com.alibaba.chaosblade.exec.plugin.lettuce;

import com.alibaba.chaosblade.exec.common.aop.PointCut;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.ClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.NameClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.MethodMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.NameMethodMatcher;

/**
 * @author yefei
 */
public class LettucePointCut implements PointCut {

    @Override
    public ClassMatcher getClassMatcher() {
        NameClassMatcher nameClassMatcher = new NameClassMatcher("io.lettuce.core.protocol.CommandHandler");
        return nameClassMatcher;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        NameMethodMatcher nameMethodMatcher = new NameMethodMatcher("write");
        return nameMethodMatcher;
    }
}
