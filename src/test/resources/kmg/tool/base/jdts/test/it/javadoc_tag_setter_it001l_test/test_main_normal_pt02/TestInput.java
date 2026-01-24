public class TestInput {
    public void testInitialize_normalSuccessful() throws Exception {

        /* 期待値の定義 */
        final int expectedHeadTagPosOffset = -1;

        /* 準備 */
        final List<JdtsTagConfigModel> testTagConfigModels = new ArrayList<>();
        testTagConfigModels.add(this.mockTagConfigModel);

        Mockito.when(this.mockConfigsModel.getJdaTagConfigModels()).thenReturn(testTagConfigModels);
        Mockito.when(this.mockSrcBlockModel.getJavadocModel()).thenReturn(this.mockJavadocModel);
        Mockito.when(this.mockJavadocModel.getSrcJavadoc()).thenReturn("/** Test javadoc */");
        Mockito.when(this.mockJavadocModel.getJavadocTagsModel()).thenReturn(this.mockJavadocTagsModel);

        /* テスト対象の実行 */
        final boolean testResult = this.testTarget.initialize(this.mockConfigsModel, this.mockSrcBlockModel);

        /* 検証の準備 */
        final boolean       actualResult           = testResult;
        final StringBuilder actualReplacedBlock    = (StringBuilder) this.reflectionModel.get("replacedJavadocBlock");
        final int           actualHeadTagPosOffset = (Integer) this.reflectionModel.get("headTagPosOffset");

        /* 検証の実施 */
        Assertions.assertTrue(actualResult, "初期化が成功すること");
        Assertions.assertNotNull(actualReplacedBlock, "置換用Javadocブロックが初期化されること");
        Assertions.assertEquals(expectedHeadTagPosOffset, actualHeadTagPosOffset, "先頭タグの位置オフセットが設定されること");

    }  
}
