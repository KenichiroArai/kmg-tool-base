# 列挙型からcase文作成ツール設計書

## 1. クラス図

```mermaid
classDiagram
    %% 継承関係
    AbstractTool <|-- AbstractIoTool
    AbstractIoTool <|-- AbstractTwo2OneTool
    AbstractTwo2OneTool <|-- AbstractDynamicTemplateConversionTool
    AbstractDynamicTemplateConversionTool <|-- Enum2SwitchCaseCreationTool

    %% インターフェース実装関係
    IoService <|.. Two2OneService
    Two2OneService <|.. DtcService
    Two2OneService <|.. Enum2SwitchCaseCreationService

    %% サービス実装関係
    AbstractIctoProcessorService ..|> Two2OneService
    Enum2SwitchCaseCreationServiceImpl --|> AbstractIctoProcessorService
    Enum2SwitchCaseCreationServiceImpl ..|> Enum2SwitchCaseCreationService
    DtcServiceImpl ..|> DtcService

    %% ロジック関係
    Enum2SwitchCaseCreationTool --> Enum2SwitchCaseCreationService : uses
    Enum2SwitchCaseCreationServiceImpl --> Enum2SwitchCaseCreationLogic : uses
    Enum2SwitchCaseCreationServiceImpl --> DtcService : uses
    DtcServiceImpl --> DtcLogic : uses

    %% テンプレート関連
    Enum2SwitchCaseCreationTool ..> Enum2SwitchCaseCreationTool.yml : uses template

    class AbstractTool {
        +boolean execute()
    }

    class AbstractIoTool {
        -String toolName
        +static Path getBasePath()
        +static Path getInputPath()
        +static Path getOutputPath()
        +boolean execute()
        #abstract IoService getIoService()
    }

    class AbstractTwo2OneTool {
        -Logger logger
        -Path templatePath
        +Path getTemplatePath()
        +boolean initialize()
        #abstract Two2OneService getIoService()
    }

    class AbstractDynamicTemplateConversionTool {
        +AbstractDynamicTemplateConversionTool(String toolName)
    }

    class Enum2SwitchCaseCreationTool {
        -static String TOOL_NAME
        -Enum2SwitchCaseCreationService enum2SwitchCaseMakingService
        +static void main(String[] args)
        +Enum2SwitchCaseCreationTool()
        #Enum2SwitchCaseCreationService getIoService()
    }

    class IoService {
        <<interface>>
        +boolean process()
    }

    class Two2OneService {
        <<interface>>
        +Path getInputPath()
        +Path getOutputPath()
        +Path getTemplatePath()
        +boolean initialize(Path inputPath, Path templatePath, Path outputPath)
    }

    class Enum2SwitchCaseCreationService {
        <<interface>>
    }

    class DtcService {
        <<interface>>
    }

    class AbstractIctoProcessorService {
        -Path inputPath
        -Path templatePath
        -Path outputPath
        -Path csvPath
        -DtcService dtcService
        +Path getCsvPath()
        +Path getInputPath()
        +Path getOutputPath()
        +Path getTemplatePath()
        +boolean initialize(Path inputPath, Path templatePath, Path outputPath)
        +boolean process()
        #abstract boolean writeCsvFile()
    }

    class Enum2SwitchCaseCreationServiceImpl {
        -Logger logger
        -KmgMessageSource messageSource
        -Enum2SwitchCaseCreationLogic enum2SwitchCaseMakingLogic
        +Enum2SwitchCaseCreationServiceImpl()
        #boolean writeCsvFile()
    }

    class DtcServiceImpl {
        +boolean initialize(Path inputPath, Path templatePath, Path outputPath)
        +boolean process()
    }

    class Enum2SwitchCaseCreationLogic {
        <<interface>>
        +boolean addItemToCsvRows()
        +boolean addOneLineOfDataToCsvRows()
        +boolean addValueToCsvRows()
        +boolean clearCsvRows()
        +boolean clearProcessingData()
        +boolean convertEnumValues()
        +String getItem()
        +String getValue()
    }

    class DtcLogic {
        <<interface>>
        +boolean addOutputBufferContent()
        +void applyTemplateToInputFile()
        +void clearOutputBufferContent()
        +boolean clearReadingData()
        +String getContentsOfOneItem()
    }

    class Enum2SwitchCaseCreationTool.yml {
        intermediatePlaceholders
        derivedPlaceholders
        templateContent
    }
```

## 2. シーケンス図

```mermaid
sequenceDiagram
    participant User as ユーザー
    participant ESCT as Enum2SwitchCaseCreationTool
    participant AT2OT as AbstractTwo2OneTool
    participant AIT as AbstractIoTool
    participant ESCService as Enum2SwitchCaseCreationServiceImpl
    participant AIPS as AbstractIctoProcessorService
    participant ESCLogic as Enum2SwitchCaseCreationLogicImpl
    participant DtcService as DtcService
    participant Template as Enum2SwitchCaseCreationTool.yml
    participant Input as input.txt
    participant CSV as csv中間ファイル
    participant Output as output.txt

    User->>ESCT: アプリケーション起動
    Note over ESCT: main(String[] args)
    ESCT->>ESCT: SpringApplication.run

    ESCT->>AT2OT: initialize()
    AT2OT->>ESCService: initialize(inputPath, templatePath, outputPath)
    ESCService->>AIPS: initialize(inputPath, templatePath, outputPath)
    AIPS->>AIPS: createTempCsvFile()

    ESCT->>AIT: execute()
    AIT->>ESCT: getIoService()
    ESCT-->>AIT: enum2SwitchCaseMakingService
    AIT->>ESCService: process()
    ESCService->>AIPS: process()
    AIPS->>ESCService: writeCsvFile()

    ESCService->>ESCLogic: initialize(inputPath, csvPath)
    ESCService->>ESCLogic: addOneLineOfDataToCsvRows()

    loop 入力ファイル処理
        ESCService->>ESCLogic: readOneLineOfData()
        ESCLogic-->>Input: ファイル読み込み

        ESCService->>ESCService: processColumns()

        ESCService->>ESCService: addNameColumn()
        ESCService->>ESCLogic: convertEnumValues()
        ESCLogic->>ESCLogic: 列挙定数抽出処理

        ESCService->>ESCLogic: addValueToCsvRows()
        ESCLogic->>CSV: CSVに列挙値追加

        ESCService->>ESCLogic: addItemToCsvRows()
        ESCLogic->>CSV: CSVに項目名追加

        ESCService->>ESCLogic: writeCsvFile()
        ESCLogic->>CSV: CSVファイル書き込み

        ESCService->>ESCService: clearAndPrepareNextLine()
        ESCService->>ESCLogic: clearCsvRows()
        ESCService->>ESCLogic: clearProcessingData()
        ESCService->>ESCLogic: addOneLineOfDataToCsvRows()
    end

    AIPS->>DtcService: initialize(csvPath, templatePath, outputPath)
    AIPS->>DtcService: process()
    DtcService->>Template: テンプレート読み込み
    DtcService->>CSV: CSV読み込み

    loop テンプレート適用処理
        DtcService->>DtcService: readOneLineData()
        DtcService->>DtcService: applyTemplateToInputFile()
        DtcService->>DtcService: addOutputBufferContent()
        DtcService->>DtcService: writeOutputBuffer()
        DtcService->>DtcService: clearOutputBufferContent()
    end

    DtcService->>Output: 出力ファイル生成

    ESCService->>ESCLogic: close()
    ESCLogic->>ESCLogic: closeReader()
    ESCLogic->>ESCLogic: closeWriter()

    ESCT-->>User: 処理完了
```

## 3. テンプレートファイル構造

Enum2SwitchCaseCreationTool.ymlは以下の構造を持っています：

1. **intermediatePlaceholders**: 中間から直接取得するプレースホルダー定義
   - displayName: 画面表示用の名称
   - replacementPattern: 置換対象のパターン

2. **derivedPlaceholders**: CSVから取得した値を変換して生成するプレースホルダー定義
   - displayName: 画面表示用の名称
   - replacementPattern: 置換対象のパターン
   - sourceKey: 変換元となるCSVプレースホルダーのdisplayName
   - transformation: 適用する変換処理

3. **templateContent**: テンプレートの内容
   - {item}, {value}のプレースホルダーが実際の値に置換される

## 4. 処理フロー詳細

1. ユーザーがアプリケーションを起動
2. SpringBootアプリケーションが起動し、Enum2SwitchCaseCreationToolのインスタンスが生成される
3. AbstractTwo2OneToolのinitialize()メソッドが呼び出され、Enum2SwitchCaseCreationServiceが初期化される
4. AbstractIoToolのexecute()メソッドが呼び出され、メイン処理が実行される
5. Enum2SwitchCaseCreationServiceImplのwriteCsvFile()メソッドが実行され、入力ファイルの処理が開始される
6. 入力ファイルから1行ずつデータを読み込み、以下の処理を行う：
   - 列挙型定数の抽出と変換
   - 列挙型の値と項目名の抽出
   - CSV形式に変換して中間ファイルに書き込み
7. 中間ファイル（CSV）の生成が完了したら、DtcService（テンプレートの動的変換サービス）を使用して：
   - テンプレートファイル（Enum2SwitchCaseCreationTool.yml）を読み込む
   - 中間ファイル（CSV）のデータを読み込む
   - テンプレートにデータを適用して出力ファイルを生成する
8. リソースがクローズされ、処理が完了する

## 5. 主要コンポーネント

### Enum2SwitchCaseCreationTool

- SpringBootApplicationとして動作するエントリーポイント
- AbstractDynamicTemplateConversionToolを継承（さらにAbstractTwo2OneToolを継承）
- Enum2SwitchCaseCreationServiceを使用してswitch-case文生成を実行

### AbstractTwo2OneTool

- AbstractIoToolを継承
- テンプレートファイルパスの管理と初期化処理を担当

### Enum2SwitchCaseCreationServiceImpl

- AbstractIctoProcessorServiceを継承
- Enum2SwitchCaseCreationServiceインターフェースを実装
- 入力ファイルの読み込みとCSV形式への変換を担当

### Enum2SwitchCaseCreationLogicImpl

- switch-case文作成の実際のロジックを担当
- 列挙型定義からswitch-case文に必要な情報を抽出
- CSV形式の中間ファイルを生成

### DtcService（テンプレートの動的変換サービス）

- テンプレートファイルとCSVデータを使用して最終的な出力ファイルを生成
- プレースホルダの置換処理を担当

### テンプレートファイル（Enum2SwitchCaseCreationTool.yml）

- YAMLフォーマットで定義されたテンプレート設定ファイル
- 以下の主要セクションで構成：
  - `intermediatePlaceholders`: 中間から直接取得するプレースホルダー定義
  - `derivedPlaceholders`: CSVから取得した値を変換して生成するプレースホルダー定義
  - `templateContent`: 実際のテンプレート内容

#### intermediatePlaceholders

- CSVファイルの各列から直接マッピングされるプレースホルダー
  - `{item}`: 列挙型の項目名
  - `{value}`: 列挙型の値

#### derivedPlaceholders

- 既存のプレースホルダーから変換して生成される派生プレースホルダー
  - 必要に応じて定義される変換プレースホルダー

#### templateContent

- switch-case文のテンプレートを定義
- 上記のプレースホルダーを使用して、列挙型の各項目に対応するcase文を生成
