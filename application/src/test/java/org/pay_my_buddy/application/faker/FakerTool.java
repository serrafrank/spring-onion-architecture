package org.pay_my_buddy.application.faker;

public class FakerTool {

    public static net.datafaker.Faker data() {
        return new net.datafaker.Faker();
    }

    public static String randomString() {
        return randomString(10);
    }

    public static String randomString(int length) {
        return data().regexify("[a-zA-Z0-9]{" + length + "}");
    }

    public static Number randomNumber(long min, long max) {
        return data().number().numberBetween(min, max);
    }

    public static Number randomNumber() {
        return randomNumber(1, 10);
    }

}
