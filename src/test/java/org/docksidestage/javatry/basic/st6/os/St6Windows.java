package org.docksidestage.javatry.basic.st6.os;

public class St6Windows extends St6OperationSystem {

    public St6Windows(String loginId) {
        super(loginId);
    }

    @Override
    public String buildUserResourcePath(String relativePath) {
        String fileSeparator = getFileSeparator();
        String userDirectory = getUserDirectory();
        String resourcePath = buildPath(userDirectory, fileSeparator, relativePath);
        return resourcePath.replace("/", fileSeparator);
    }

    protected String getFileSeparator() {
        return "\\";
    }

    protected String getUserDirectory() {
        return "/Users/" + loginId;
    }
}
