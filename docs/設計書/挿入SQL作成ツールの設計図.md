# 挿入 SQL 作成ツール設計書

## 1. クラス図

```mermaid
classDiagram
    %% インターフェース実装関係
    IsCreationService <|.. IsCreationServiceImpl
    IsFileCreationService <|.. IsFileCreationServiceImpl
    IslDataSheetCreationService <|.. IsDataSheetCreationServiceImpl
    Runnable <|.. IsDataSheetCreationServiceImpl
    IsBasicInformationLogic <|.. IsBasicInformationLogicImpl
    IsDataSheetCreationLogic <|.. IsDataSheetCreationLogicImpl

    %% サービス関係
    IsCreationServiceImpl --> IsFileCreationService : uses
    IsFileCreationServiceImpl --> IsBasicInformationLogic : uses
    IsFileCreationServiceImpl --> IslDataSheetCreationService : uses
    IsDataSheetCreationServiceImpl --> IsDataSheetCreationLogic : uses

    class IsCreationService {
        <<interface>>
        +void initialize(Path inputPath, Path outputPath, short threadNum)
        +void outputInsertionSql()
    }

    class IsCreationServiceImpl {
        -Path inputPath
        -Path outputPath
        -short threadNum
        -IsFileCreationService isFileCreationService
        +void initialize(Path inputPath, Path outputPath, short threadNum)
        +void outputInsertionSql()
    }

    class IsFileCreationService {
        <<interface>>
        +void initialize(Path inputPath, Path outputPath, short threadNum)
        +void outputInsertionSql()
    }

    class IsFileCreationServiceImpl {
        -IslDataSheetCreationService islDataSheetCreationService
        -IsBasicInformationLogic insertionSqlFileCreationLogic
        -Path inputPath
        -Path outputPath
        -short threadNum
        +void initialize(Path inputPath, Path outputPath, short threadNum)
        +void outputInsertionSql()
        -ExecutorService getExecutorService()
        -void processWorkbook(Workbook inputWb)
    }

    class IsBasicInformationLogic {
        <<interface>>
        +static String SETTING_SHEET_NAME
        +static String LIST_NAME
        +KmgDbTypes getKmgDbTypes()
        +Map~String, String~ getSqlIdMap()
        +void initialize(Workbook inputWk)
    }

    class IsBasicInformationLogicImpl {
        -Workbook inputWb
        +KmgDbTypes getKmgDbTypes()
        +Map~String, String~ getSqlIdMap()
        +void initialize(Workbook inputWk)
    }

    class IslDataSheetCreationService {
        <<interface>>
        +void initialize(KmgDbTypes kmgDbTypes, Sheet inputSheet, Map~String, String~ sqlIdMap, Path outputPath)
        +void outputInsertionSql()
    }

    class IsDataSheetCreationServiceImpl {
        -Logger logger
        -KmgMessageSource messageSource
        -IsDataSheetCreationLogic isDataSheetCreationLogic
        -KmgDbTypes kmgDbTypes
        -Sheet inputSheet
        -Map~String, String~ sqlIdMap
        -Path outputPath
        +IsDataSheetCreationServiceImpl()
        +IsDataSheetCreationServiceImpl(Logger logger)
        +void initialize(KmgDbTypes kmgDbTypes, Sheet inputSheet, Map~String, String~ sqlIdMap, Path outputPath)
        +void outputInsertionSql()
        +void run()
    }

    class IsDataSheetCreationLogic {
        <<interface>>
        +void createOutputFileDirectories()
        +Charset getCharset()
        +short getColumnNum()
        +List~String~ getColumnPhysicsNameList()
        +String getDeleteComment()
        +String getDeleteSql()
        +String getInsertComment()
        +String getInsertSql(Row datasRow)
        +List~KmgDbDataTypeTypes~ getKmgDbDataTypeList()
        +Path getOutputFilePath()
        +String getSqlId()
        +String getTableLogicName()
        +String getTablePhysicsName()
        +void initialize(KmgDbTypes kmgDbTypes, Sheet inputSheet, Map~String, String~ sqlIdMap, Path outputPath)
    }

    class IsDataSheetCreationLogicImpl {
        -KmgDbTypes kmgDbTypes
        -Sheet inputSheet
        -Map~String, String~ sqlIdMap
        -Path outputPath
        -String tableLogicName
        -String tablePhysicsName
        -String sqlId
        -List~String~ columnPhysicsNameList
        -List~KmgDbDataTypeTypes~ kmgDbDataTypeList
        -short columnNum
        -Charset charset
        -Path outputFilePath
        +void createOutputFileDirectories()
        +Charset getCharset()
        +short getColumnNum()
        +List~String~ getColumnPhysicsNameList()
        +String getDeleteComment()
        +String getDeleteSql()
        +String getInsertComment()
        +String getInsertSql(Row datasRow)
        +List~KmgDbDataTypeTypes~ getKmgDbDataTypeList()
        +Path getOutputFilePath()
        +String getSqlId()
        +String getTableLogicName()
        +String getTablePhysicsName()
        +void initialize(KmgDbTypes kmgDbTypes, Sheet inputSheet, Map~String, String~ sqlIdMap, Path outputPath)
        -void extractTableInformation()
        -void extractColumnInformation()
        -String formatValueForSql(Object value, KmgDbDataTypeTypes dataType)
    }

    class Workbook {
        <<Apache POI>>
        +int getNumberOfSheets()
        +Sheet getSheetAt(int index)
        +Sheet getSheet(String name)
    }

    class Sheet {
        <<Apache POI>>
        +String getSheetName()
        +int getLastRowNum()
        +Row getRow(int rowIndex)
    }

    class Row {
        <<Apache POI>>
        +Cell getCell(int columnIndex)
    }

    class Cell {
        <<Apache POI>>
        +Object getCellValue()
        +String getStringCellValue()
        +double getNumericCellValue()
        +Date getDateCellValue()
    }
```

## 2. シーケンス図

```mermaid
sequenceDiagram
    participant User as ユーザー
    participant ICS as IsCreationService
    participant ICSImpl as IsCreationServiceImpl
    participant IFCS as IsFileCreationService
    participant IFCSImpl as IsFileCreationServiceImpl
    participant IBIL as IsBasicInformationLogic
    participant IDSCS as IslDataSheetCreationService
    participant IDSCSImpl as IsDataSheetCreationServiceImpl
    participant IDSCL as IsDataSheetCreationLogic
    participant Excel as Excelファイル
    participant SQL as SQLファイル

    User->>ICS: サービス呼び出し
    ICS->>ICSImpl: initialize(inputPath, outputPath, threadNum)
    ICSImpl->>ICSImpl: パラメータ保存

    ICS->>ICSImpl: outputInsertionSql()
    ICSImpl->>IFCS: initialize(inputPath, outputPath, threadNum)
    IFCS->>IFCSImpl: initialize(inputPath, outputPath, threadNum)

    ICSImpl->>IFCS: outputInsertionSql()
    IFCS->>IFCSImpl: outputInsertionSql()
    IFCSImpl->>Excel: FileInputStream読み込み
    Excel-->>IFCSImpl: Workbook取得
    IFCSImpl->>IFCSImpl: processWorkbook(Workbook)

    IFCSImpl->>IBIL: initialize(Workbook)
    IBIL->>IBIL: 基本情報抽出
    IFCSImpl->>IBIL: getKmgDbTypes()
    IBIL-->>IFCSImpl: KMG DBの種類
    IFCSImpl->>IBIL: getSqlIdMap()
    IBIL-->>IFCSImpl: SQL IDマップ

    loop 各シート処理
        IFCSImpl->>IFCSImpl: シート取得
        IFCSImpl->>IDSCS: initialize(kmgDbTypes, sheet, sqlIdMap, outputPath)
        IDSCS->>IDSCSImpl: initialize(kmgDbTypes, sheet, sqlIdMap, outputPath)
        IFCSImpl->>IDSCS: execute()
        IDSCS->>IDSCSImpl: run()
        IDSCSImpl->>IDSCSImpl: outputInsertionSql()

        IDSCSImpl->>IDSCL: initialize(kmgDbTypes, sheet, sqlIdMap, outputPath)
        IDSCL->>IDSCL: テーブル情報抽出
        IDSCL->>IDSCL: カラム情報抽出

        IDSCSImpl->>IDSCL: createOutputFileDirectories()
        IDSCL->>IDSCL: 出力ディレクトリ作成

        IDSCSImpl->>IDSCL: getOutputFilePath()
        IDSCL-->>IDSCSImpl: 出力ファイルパス
        IDSCSImpl->>IDSCL: getCharset()
        IDSCL-->>IDSCSImpl: 文字セット

        IDSCSImpl->>SQL: BufferedWriter作成

        IDSCSImpl->>IDSCL: getDeleteComment()
        IDSCL-->>IDSCSImpl: 削除コメント
        IDSCSImpl->>SQL: 削除コメント出力

        IDSCSImpl->>IDSCL: getDeleteSql()
        IDSCL-->>IDSCSImpl: 削除SQL
        IDSCSImpl->>SQL: 削除SQL出力

        IDSCSImpl->>IDSCL: getInsertComment()
        IDSCL-->>IDSCSImpl: 挿入コメント
        IDSCSImpl->>SQL: 挿入コメント出力

        loop データ行処理
            IDSCSImpl->>Excel: データ行取得
            IDSCSImpl->>IDSCL: getInsertSql(datasRow)
            IDSCL->>IDSCL: 値のフォーマット処理
            IDSCL-->>IDSCSImpl: 挿入SQL
            IDSCSImpl->>SQL: 挿入SQL出力
        end

        IDSCSImpl->>SQL: BufferedWriterクローズ
    end

    ICSImpl-->>User: 処理完了
```

## 3. 処理フロー詳細

1. **サービス初期化**

   - IsCreationService が初期化される
   - 入力パス、出力パス、スレッド数が設定される

2. **挿入 SQL 出力処理開始**

   - IsCreationService の outputInsertionSql() メソッドが呼び出される
   - IsFileCreationService が初期化される

3. **Excel ファイル処理**

   - Excel ファイル（Workbook）が読み込まれる
   - 基本情報ロジックが初期化され、設定情報から KMG DB の種類と SQL ID マップを取得

4. **シート別処理**

   - 各シートに対して並列処理が実行される（指定されたスレッド数で）
   - 設定シートと一覧シートはスキップされる
   - 各データシートに対して以下の処理が実行される：
     - テーブル情報（論理名、物理名、SQL ID）の抽出
     - カラム情報（物理名、データ型）の抽出
     - 出力ディレクトリの作成
     - SQL ファイルの生成

5. **SQL ファイル生成**

   - 各シートごとに SQL ファイルが生成される
   - 削除 SQL（DELETE 文）が先に出力される
   - 挿入 SQL（INSERT 文）がデータ行ごとに出力される
   - データ値は各カラムのデータ型に応じて適切にフォーマットされる

6. **処理完了**
   - 全シートの処理が完了
   - リソースが適切にクローズされる

## 4. 主要コンポーネント

### IsCreationService

- 挿入 SQL 作成のメインサービスインターフェース
- 初期化と挿入 SQL 出力の責任を持つ

### IsCreationServiceImpl

- IsCreationService の実装クラス
- IsFileCreationService への委譲を行う

### IsFileCreationService

- ファイルレベルの挿入 SQL 作成サービスインターフェース
- Excel ファイルの読み込みと処理の管理

### IsFileCreationServiceImpl

- IsFileCreationService の実装クラス
- Apache POI を使用して Excel ファイルを処理
- 並列処理（ExecutorService）によるシート別処理の管理

### IsBasicInformationLogic

- Excel ファイルの基本情報抽出ロジックインターフェース
- 設定シートから KMG DB の種類と SQL ID マップを取得

### IsBasicInformationLogicImpl

- IsBasicInformationLogic の実装クラス
- 設定シートの解析と基本情報の抽出

### IslDataSheetCreationService

- データシート別の挿入 SQL 作成サービスインターフェース
- Runnable インターフェースを実装し、並列処理に対応

### IsDataSheetCreationServiceImpl

- IslDataSheetCreationService の実装クラス
- 個別のデータシートに対する挿入 SQL 生成処理
- ファイル出力の管理

### IsDataSheetCreationLogic

- データシート作成の詳細ロジックインターフェース
- テーブル情報、カラム情報の抽出と SQL 生成

### IsDataSheetCreationLogicImpl

- IsDataSheetCreationLogic の実装クラス
- テーブル情報とカラム情報の詳細な抽出処理
- データ型に応じた値のフォーマット処理
- SQL 文の生成

## 5. 技術的特徴

### 並列処理

- ExecutorService を使用したマルチスレッド処理
- ユーザー指定のスレッド数での並列実行
- シート単位での独立した処理

### Excel 処理

- Apache POI を使用した Excel ファイルの読み込み
- 設定シートからの基本情報抽出
- データシートからのテーブル・カラム情報抽出

### エラーハンドリング

- KmgToolMsgException による統一された例外処理
- ログ出力による詳細なエラー情報の記録
- ユーザーフレンドリーなエラーメッセージ

## 6. 入力・出力仕様

### 入力

- **Excel ファイル**: 設定シートとデータシートを含むワークブック
- **設定シート**: KMG DB の種類と SQL ID マップの定義
- **データシート**: テーブル情報（論理名、物理名、SQL ID）とカラム情報（物理名、データ型）を含む

### 出力

- **SQL ファイル**: 各データシートに対応する挿入 SQL ファイル
- **削除 SQL**: テーブル内の全データを削除する DELETE 文
- **挿入 SQL**: データ行ごとの INSERT 文
- **コメント**: 各 SQL 文に対する説明コメント

### 設定項目

- **入力ファイルパス**: 処理対象の Excel ファイル
- **出力ディレクトリパス**: SQL ファイルの出力先
- **スレッド数**: 並列処理に使用するスレッド数（デフォルト：CPU 論理プロセッサ数）
