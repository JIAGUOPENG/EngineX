package com.risk.riskmanage.util;

import org.apache.commons.lang3.StringUtils;
import java.util.Stack;

/**
 * 括号匹配工具类
 */
public class BracketMatchUtil {

    public static boolean matching(String expression) {

        if (StringUtils.isBlank(expression)) {
            return false;
        }

        Stack<Character> stack = new Stack<>();

        for (int index = 0; index < expression.length(); index++) {
            switch (expression.charAt(index)) {
                case '(':
                    stack.push(expression.charAt(index));
                    break;
                case '{':
                    stack.push(expression.charAt(index));
                    break;
                case ')':
                    if (!stack.empty() && stack.peek() == '(') {
                        stack.pop();
                    }
                    break;

                case '}':
                    if (!stack.empty() && stack.peek() == '{') {
                        stack.pop();
                    }
            }
        }

        if (stack.empty()) {
            return true;
        }

        return false;
    }

}
