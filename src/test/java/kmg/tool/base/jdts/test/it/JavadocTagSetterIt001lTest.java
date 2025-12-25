package kmg.tool.base.jdts.test.it;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 * Javadocタグ設定ツールの結合テスト001のテスト<br>
 * <p>
 * このテストクラスは、Javadocタグ設定ツールの基本的な動作を検証します。<br>
 * テンプレート設定（TestTemplate.yml）では、以下の設定が行われています：<br>
 * - authorタグ：常に上書き（overwrite: "always"）<br>
 * - sinceタグ：常に上書き（overwrite: "always"）<br>
 * - versionタグ：常に上書き（overwrite: "always"）<br>
 * </p>
 * <p>
 * テスト内容：<br>
 * - クラスのJavadocでタグが一つもない場合にタグを追加する<br>
 * - 文字列内のJavadocコメント（「\/** XXXXX *\/」）を無視する<br>
 * - テキストブロック内のJavadocコメントを無視する<br>
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.0
 *
 * @version 0.2.2
 */
@Disabled
public class JavadocTagSetterIt001lTest extends AbstractJavadocTagSetterItTest {

    /**
     * main メソッドのテスト - 正常系：パターン01<br>
     * <p>
     * クラスのJavadocで、タグが一つもない場合に、タグを追加する。
     * </p>
     *
     * @since 0.2.0
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    @Test
    @Disabled
    public void testMain_normalPt01(final TestInfo testInfo) throws Exception {

        this.executeJavadocTagSetterTestWithDefaultFiles(testInfo);

    }

    /**
     * main メソッドのテスト - 正常系：パターン02<br>
     * <p>
     * テストメソッド内で文字列として「\/** XXXXX *\/」がある場合に、タグを追加しないことを確認する。
     * </p>
     *
     * @since 0.2.0
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    @Test
    public void testMain_normalPt02(final TestInfo testInfo) throws Exception {

        this.executeJavadocTagSetterTestWithDefaultFiles(testInfo);

    }

    /**
     * main メソッドのテスト - 正常系：パターン03<br>
     * <p>
     * 文字列として「\/** XXXXX *\/\\n～」がある場合に、タグを追加しないことを確認する。<br>
     * 「*\/」の後に改行がり続きの処理がある。
     * </p>
     *
     * @since 0.2.0
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    @Test
    @Disabled
    public void testMain_normalPt03(final TestInfo testInfo) throws Exception {

        this.executeJavadocTagSetterTestWithDefaultFiles(testInfo);

    }

    /**
     * main メソッドのテスト - 正常系：パターン04<br>
     * <p>
     * 文字列がテキストブロックで「"""<br>
     * \/** XXXXX *\/\<br>
     * ～<br>
     * """」がある場合に、タグを追加しないことを確認する。<br>
     * </p>
     *
     * @since 0.2.0
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    @Test
    @Disabled
    public void testMain_normalPt04(final TestInfo testInfo) throws Exception {

        this.executeJavadocTagSetterTestWithDefaultFiles(testInfo);

    }

    /**
     * main メソッドのテスト - 正常系：パターン05<br>
     * <p>
     * 文字列がテキストブロックで「"""<br>
     * \/** XXXXX *\/\<br>
     * ～<br>
     * """」がある場合に、タグを追加しないことを確認する。<br>
     * </p>
     *
     * @since 0.2.0
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    @Test
    @Disabled
    public void testMain_normalPt05(final TestInfo testInfo) throws Exception {

        this.executeJavadocTagSetterTestWithDefaultFiles(testInfo);

    }

    /**
     * main メソッドのテスト - 正常系：パターン06<br>
     * <p>
     * 文字列がテキストブロックで「"""<br>
     * \/** XXXXX *\/\<br>
     * ～<br>
     * """」でかつ、Javadocタグもある場合に、タグを追加しないことを確認する。<br>
     * </p>
     *
     * @since 0.2.0
     *
     * @param testInfo
     *                 テスト情報
     *
     * @throws Exception
     *                   例外
     */
    @Test
    @Disabled
    public void testMain_normalPt06(final TestInfo testInfo) throws Exception {

        this.executeJavadocTagSetterTestWithDefaultFiles(testInfo);

    }

}
