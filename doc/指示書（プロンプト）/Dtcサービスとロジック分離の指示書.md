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

   - エラー処理は手動実装のため、初期値として「NONE」を設定
   - 各処理ステップに以下のTODOコメントを追加。下記は例。

     ```java
     //TODO Kenichiro [2024/03/27] [ログ]または[例外処理]
     ```

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
2. エラーハンドリングは手動実装（初期値：NONE）
3. リソースの確実なクローズ
4. TODOコメントの統一フォーマット使用
5. コードの重複を避ける

## 期待される効果

1. 責務の明確な分離
2. コードの保守性向上
3. テスタビリティの向上
4. エラーハンドリングの改善
5. ログ出力の統一性確保

## 実装チェックリスト

### DtcLogicImplの実装チェック

- [ ] initialize メソッドが適切に実装されている
- [ ] loadAndParseTemplate メソッドが YAML ファイルを正しく読み込んでいる
- [ ] extractCsvPlaceholderDefinitions メソッドが CSV プレースホルダーを正しく抽出している
- [ ] extractDerivedPlaceholderDefinitions メソッドが派生プレースホルダーを正しく抽出している
- [ ] processInputAndGenerateOutput メソッドが入力と出力を適切に処理している
- [ ] ファイル操作のメソッド（openInputFile, openOutputFile, closeReader, closeWriter）が実装されている
- [ ] プレースホルダー処理メソッドが最適化されている
- [ ] リソースの確実なクローズ処理が実装されている

### DtcServiceImplの実装チェック

- [ ] ロジック呼び出しの順序が指示書通りに実装されている
- [ ] 各ステップにエラーハンドリングが追加されている
- [ ] 各ステップにログ出力が追加されている
- [ ] try-catch-finally などによるリソース管理が実装されている
- [ ] TODOコメントが統一フォーマットで追加されている
- [ ] メソッドの責務が明確に分離されている

### インターフェース整理のチェック

- [ ] DtcLogic インターフェースが必要なメソッドを適切に定義している
- [ ] DtcService インターフェースが必要なメソッドを適切に定義している
- [ ] インターフェースとその実装の整合性が取れている

### 全体確認

- [ ] コードの重複が排除されている
- [ ] トランザクション境界が明確化されている
- [ ] 単体テストが実行可能な構造になっている
- [ ] すべてのTODOコメントが統一フォーマットで追加されている
- [ ] リファクタリング前の挙動と一致していることを確認している
