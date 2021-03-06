package com.business.tpas.utils;

import com.business.tpas.entity.ParamsRulesSetting;
import com.business.tpas.entity.RuleSetting;
import com.business.tpas.enums.ParamsRulesValueTypeEnum;
import com.business.tpas.enums.RuleSettingRuleTypeEnum;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author dude
 * @version 1.0
 * @classname AssessRuleUtil
 * @description 绩效检验计算工具
 * @date 2021/3/3
 **/
public class AssessRuleUtil {

    private static final Logger logger = LoggerFactory.getLogger(AssessRuleUtil.class);

    public static Pattern DOUBLE_OP = Pattern.compile("[+\\-*/]{2,}");

    public static Pattern OP_AFTER_QUOTE = Pattern.compile("[({][+\\-*/)]");

    public static Pattern OP_BEFORE_QUOTE = Pattern.compile("[+\\-*/(][)}]");

    public static Pattern NOT_OP_BEFORE_QUOTE = Pattern.compile("[^+\\-*/][(]");

    public static Pattern NOT_OP_AFTER_QUOTE = Pattern.compile("[)][^+\\-*/]");

    public static Pattern OTHER_CHAR = Pattern.compile("[^+\\-*/0-9.{}()]");

    public static Pattern TRIM_WITH_OP = Pattern.compile("^[+\\-*/)}]|[+\\-*/({]$");

    public static Pattern PARAM_TRIM = Pattern.compile("(}[^+\\-*/)])|([^+\\-*/(]\\{)");

    public static Pattern PARAM = Pattern.compile("\\{([^}]*)}");

    /**
     * 校验解析绩效规则中的权值参数id
     *
     * @author dude
     * @date 2021/3/3
     **/
    public static List<Integer> parseParamsFromAssessDetail(String assessDetail, Boolean needCheck) {
        String temp = assessDetail.replaceAll("\\s", "");
        if (needCheck) {
            // 删除空白符
            if (StringUtils.isBlank(temp)) {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
            // 检验四则运算规则
            if (DOUBLE_OP.matcher(temp).find()
                    || OP_AFTER_QUOTE.matcher(temp).find()
                    || OP_BEFORE_QUOTE.matcher(temp).find()
                    || OP_AFTER_QUOTE.matcher(temp).find()
                    || NOT_OP_BEFORE_QUOTE.matcher(temp).find()
                    || NOT_OP_AFTER_QUOTE.matcher(temp).find()
                    || OTHER_CHAR.matcher(temp).find()
                    || TRIM_WITH_OP.matcher(temp).find()
                    || PARAM_TRIM.matcher(temp).find()) {
                logger.error("检验四则运算规则不通过");
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
            // 匹配括号
            Stack<Character> stack = new Stack<>();
            for (char c : temp.toCharArray()) {
                if (c == '(') {
                    stack.push('(');
                } else if (c == ')') {
                    if (!stack.empty()) {
                        stack.pop();
                    } else {
                        logger.error("检验四则运算括号匹配不正确");
                        throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
                    }
                }
            }
            if (!stack.empty()) {
                logger.error("检验四则运算括号匹配不正确");
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
        }
        // 校验花括号{}
        Matcher matcher = PARAM.matcher(temp);
        List<Integer> rules = new ArrayList<>();
        while (matcher.find()) {
            String target = matcher.group().replaceAll("[{}]", "");
            if (StringUtils.isNumeric(target)) {
                rules.add(Integer.valueOf(target));
            } else {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
        }
        return rules;
    }

    /**
     * 由信息和权值列表以及符合信息的规则id，选取权值
     *
     * @author dude
     * @date 2021/3/5
     **/
    public static Map<Integer, BigDecimal> selectParamValue(Map<String, Object> data, List<ParamsRulesSetting> paramsRulesSettings, Set<Long> ruleIdList) {
        Map<Integer, BigDecimal> selectedValue = new HashMap<>();
        // 权值由cNum分组，取第一个符合条件的值
        paramsRulesSettings.stream().collect(Collectors.groupingBy(ParamsRulesSetting::getcNum)).forEach((key, value) -> {
            ParamsRulesSetting setting = value.stream().filter(eachSetting -> {
                List<Long> idList = parseIdList(eachSetting.getRulesSettingIds());
                return idList.isEmpty() || ruleIdList.containsAll(idList);
            }).findFirst().orElse(null);
            if (setting == null) {
                selectedValue.put(key, BigDecimal.ZERO);
            } else {
                // 根据权值取值
                if (setting.getValueType().equals(ParamsRulesValueTypeEnum.COLUMN.getCode())) {
                    BigDecimal paramValue = new BigDecimal(data.getOrDefault(setting.getColumnName(), "0").toString());
                    selectedValue.put(key, paramValue);
                } else {
                    selectedValue.put(key, setting.getParamValue());
                }
            }
        });
        return selectedValue;
    }

    /**
     * 由绩效规则和权值数值计算分数
     *
     * @author dude
     * @date 2021/3/3
     **/
    public static String parseFormat(String assessDetail, Map<Integer, BigDecimal> paramValue) {
        String temp = assessDetail.replaceAll("\\s", "");
        String format = temp;
        Matcher matcher = PARAM.matcher(temp);
        while (matcher.find()) {
            String target = matcher.group();
            String num = target.replaceAll("[{}]", "");
            if (StringUtils.isNumeric(num) && paramValue.containsKey(Integer.parseInt(num))) {
                format = format.replace(target, paramValue.getOrDefault(Integer.parseInt(num), BigDecimal.ZERO).toString());
            } else {
                format = format.replace(target, "0");
            }
        }
        return format;
    }

    public static BigDecimal calculate(String format) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine scriptEngine = manager.getEngineByName("js");
        try {
            String result = scriptEngine.eval(format).toString();
            return new BigDecimal(result);
        } catch (ScriptException e) {
            logger.error("计算绩效分数发生异常: ", e);
            throw new BusinessException(ErrorCodeEnum.CALCULATE_ERROR);
        }
    }


    /**
     * 校验规则，返回符合的规则id
     *
     * @author dude
     * @date 2021/3/5
     **/
    public static List<Long> checkRules(Map<String, Object> data, List<RuleSetting> rules) {
        List<Long> ruleIdList = new ArrayList<>();
        if (rules == null || rules.isEmpty()) {
            return ruleIdList;
        }
        rules.stream().filter(each -> StringUtils.isBlank(each.getValueName()) || data.containsKey(each.getValueName()))
                .forEach(it -> {
                    if (StringUtils.isBlank(it.getValueName())) {
                        ruleIdList.add(it.getId());
                    } else {
                        BigDecimal dataValue = new BigDecimal(data.getOrDefault(it.getValueName(), "0").toString());
                        if (it.getRuleType().equals(RuleSettingRuleTypeEnum.CONST.getCode())) {
                            if (dataValue.compareTo(it.getLeftValue()) == 0) {
                                ruleIdList.add(it.getId());
                            }
                        } else {
                            if (it.getLeftValue() != null && it.getRightValue() != null) {
                                if (dataValue.compareTo(it.getLeftValue()) >= 0 && dataValue.compareTo(it.getRightValue()) <= 0) {
                                    ruleIdList.add(it.getId());
                                }
                            } else if (it.getLeftValue() != null && dataValue.compareTo(it.getLeftValue()) >= 0) {
                                ruleIdList.add(it.getId());
                            } else if (it.getRightValue() != null && dataValue.compareTo(it.getRightValue()) <= 0) {
                                ruleIdList.add(it.getId());
                            }
                        }
                    }
                });
        return ruleIdList;
    }

    public static List<Long> parseIdList(String idList) {
        if (idList == null || StringUtils.isBlank(idList)) {
            return Collections.emptyList();
        }
        return Arrays.stream(idList.split(","))
                .filter(it -> StringUtils.isNumeric(it.trim()))
                .map(it -> Long.valueOf(it.trim()))
                .distinct()
                .collect(Collectors.toList());
    }

}
