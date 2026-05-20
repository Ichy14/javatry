package org.docksidestage.javatry.basic.st6.os;

public class St6MacOS extends St6OperationSystem {

    // TODO ichikawa Animalと比較して、OSみんな持つ変数(属性/データ)は抽象クラスの方で宣言でOK by jflute (2026/05/20)
    // ただ、loginIdを最初に受け取るのはあくまで具象クラス(のコンストラクター)なので、
    // それを、superのコンストラクターに引き渡してあげる必要がある。
    private final String loginId;

    public St6MacOS(String loginId) {
        this.loginId = loginId;
    }

    // TODO ichikawa DBMSクラスと同じになっている。流れがコピペされている。 by jflute (2026/05/20)
    // なんとかして、これをsuper(OS)の方に持っていきたい。
    @Override
    public String buildUserResourcePath(String relativePath) {
        String fileSeparator = getFileSeparator();
        String userDirectory = getUserDirectory();
        String resourcePath = buildPath(userDirectory, fileSeparator, relativePath);
        return resourcePath.replace("/", fileSeparator);
    }

    protected String getFileSeparator() {
        return "/";
    }

    protected String getUserDirectory() {
        return "/Users/" + loginId;
    }
}
