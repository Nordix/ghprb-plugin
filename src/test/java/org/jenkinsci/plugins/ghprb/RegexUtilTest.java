package org.jenkinsci.plugins.ghprb;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.assertThat;

public class RegexUtilTest {

    @Test
    public void testRegexForTimeout() {
        Pattern pattern = Pattern.compile("(a+)+b");
        String input = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaac";
        boolean result = RegexUtil.find(pattern, input, 1000);
        assertThat(result).isEqualTo(false);
    }
}
