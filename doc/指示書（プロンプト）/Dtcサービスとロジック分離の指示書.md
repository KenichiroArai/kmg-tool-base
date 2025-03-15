# Dtcサービスとロジック分離の指示書

## 概要

テンプレートの動的変換（DynamicTemplateConversion）機能のサービスとロジックを分離するための指示書です。

## 分離の基本方針

1. サービス（DtcServiceImpl）の役割
   - 処理の流れの制御
   - ロジックの呼び出し順序の管理
   - エラーハンドリングとログ出力
   - トランザクション管理（必要な場合）

2. ロジック（DtcLogicImpl）の役割
   - 具体的な処理の実装
   - データの変換処理
   - ファイル操作の実装
   - プレースホルダーの処理

## 具体的な分離方法

### DtcServiceImplの実装方針

1. 処理の流れを以下の順序で制御

    ```java
    // 1. ロジックの初期化
    dtcLogic.initialize(inputPath, templatePath, outputPath);

    // 2. テンプレートの読み込みと解析
    Map<String, Object> yamlData = dtcLogic.loadAndParseTemplate();

    // 3. プレースホルダー定義の取得
    Map<String, String> csvPlaceholderMap = dtcLogic.extractCsvPlaceholderDefinitions(yamlData);
    List<DtcDerivedPlaceholderModel> derivedPlaceholders = dtcLogic.extractDerivedPlaceholderDefinitions(yamlData);
    String templateContent = (String) yamlData.get(DtcKeyTypes.TEMPLATE_CONTENT.getKey());

    // 4. 入力ファイルの処理と出力
    dtcLogic.processInputAndGenerateOutput(csvPlaceholderMap, derivedPlaceholders, templateContent);
    ```

2. エラーハンドリングとログ出力

   - 各処理ステップでのエラーハンドリング
   - 適切なログレベルでのログ出力
   - リソースの確実なクローズ処理

### DtcLogicImplの実装方針

1. ファイル操作の実装

    ```java
    // ファイルの読み書き処理
    private void openInputFile()
    private void openOutputFile()
    private void closeReader()
    private void closeWriter()
    ```

2. プレースホルダー処理の実装

    ```java
    // CSVプレースホルダーの処理
    public Map<String, String> extractCsvPlaceholderDefinitions(Map<String, Object> yamlData)

    // 派生プレースホルダーの処理
    public List<DtcDerivedPlaceholderModel> extractDerivedPlaceholderDefinitions(Map<String, Object> yamlData)
    ```

3. データ変換処理の実装

    ```java
    // テンプレート処理
    private String processCsvPlaceholders(String template, String[] csvLine, Map<String, String> csvValues)
    private String processDerivedPlaceholders(String template, Map<String, String> csvValues)
    ```

## リファクタリング手順

1. DtcLogicImplの修正
   - 具体的な処理メソッドの実装を維持
   - ファイル操作関連のメソッドを整理
   - プレースホルダー処理メソッドの最適化

2. DtcServiceImplの修正
   - 処理フローの制御を強化
   - エラーハンドリングの追加
   - ログ出力の追加
   - リソース管理の改善

3. インターフェースの整理
   - DtcLogicインターフェースの見直し
   - DtcServiceインターフェースの見直し

## 注意事項

1. トランザクション境界の明確化
2. エラーハンドリングの統一
3. リソースの確実なクローズ
4. 適切なログレベルの使用
5. コードの重複を避ける

## 期待される効果

1. 責務の明確な分離
2. コードの保守性向上
3. テスタビリティの向上
4. エラーハンドリングの改善
5. ログ出力の統一性確保
