package kmg.tool.base.jdts.test.it;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 * Javadocタグ設定ツールの結合テスト002のテスト<br>
 * <p>
 * このテストクラスは、Javadocタグ設定ツールの上書き設定の動作を検証します。<br>
 * テンプレート設定（TestTemplate.yml）では、以下の設定が行われています：<br>
 * - authorタグ：常に上書き（overwrite: "always"）<br>
 * - sinceタグ：上書きしない（overwrite: "never"）<br>
 * - versionタグ：上書きしない（overwrite: "never"）<br>
 * </p>
 * <p>
 * テスト内容：<br>
 * - 内部クラスのJavadocに既存のタグがある場合の動作を検証<br>
 * - authorタグは削除され、sinceタグとversionタグは保持されることを確認<br>
 * </p>
 *
 * @author KenichiroArai
 *
 * @since 0.2.2
 *
 * @version 0.2.2
 */
@Disabled
public class JavadocTagSetterIt002lTest extends AbstractJavadocTagSetterItTest {

    /**
     * main メソッドのテスト - 正常系：パターン01<br>
     * <p>
     * 内部クラスのJavadocに「author」、「since」、「version」が設定された状態で、「author」は削除し、「since」と「version」はそのままであること。
     * </p>
     *
     * @since 0.2.2
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


}
