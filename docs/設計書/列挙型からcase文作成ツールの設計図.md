# 列挙型から case 文作成ツール設計書

## 1. クラス図

```mermaid
classDiagram
    %% 継承関係
    AbstractTool <|-- AbstractIoTool
    AbstractIoTool <|-- AbstractTwo2OneTool
    AbstractTwo2OneTool <|-- AbstractDtcTool
    AbstractDtcTool <|-- Enum2SwitchCaseCreationTool

    %% インターフェース実装関係
    IoService <|.. Two2OneService
    Two2OneService <|.. IitoProcessorService
    IitoProcessorService <|.. Enum2SwitchCaseCreationService
    Two2OneService <|.. DtcService

    %% サービス実装関係
    AbstractIitoProcessorService ..|> IitoProcessorService
    Enum2SwitchCaseCreationServiceImpl --|> AbstractIitoProcessorService
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

    class AbstractDtcTool {
        +AbstractDtcTool(String toolName)
        #abstract IitoProcessorService getIoService()
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

    class IitoProcessorService {
        <<interface>>
    }

    class Enum2SwitchCaseCreationService {
        <<interface>>
    }

    class DtcService {
        <<interface>>
    }

    class AbstractIitoProcessorService {
        -Path inputPath
        -Path templatePath
        -Path outputPath
        -Path intermediatePath
        -DtcService dtcService
        +Path getIntermediatePath()
        +Path getInputPath()
        +Path getOutputPath()
        +Path getTemplatePath()
        +boolean initialize(Path inputPath, Path templatePath, Path outputPath)
        +boolean process()
        #abstract boolean writeIntermediateFile()
    }

    class Enum2SwitchCaseCreationServiceImpl {
        -Logger logger
        -KmgMessageSource messageSource
        -Enum2SwitchCaseCreationLogic enum2SwitchCaseMakingLogic
        +Enum2SwitchCaseCreationServiceImpl()
        #boolean writeIntermediateFile()
    }

    class DtcServiceImpl {
        -Logger logger
        -KmgMessageSource messageSource
        -DtcLogic dtcLogic
        -Path inputPath
        -Path templatePath
        -Path outputPath
        +boolean initialize(Path inputPath, Path templatePath, Path outputPath)
        +boolean process()
    }

    class Enum2SwitchCaseCreationLogic {
        <<interface>>
        +boolean addItemNameToRows()
        +boolean addItemToRows()
        +boolean convertEnumDefinition()
        +String getItem()
        +String getItemName()
    }

    class DtcLogic {
        <<interface>>
        +boolean addOutputBufferContent()
        +void applyTemplateToInputFile()
        +void clearOutputBufferContent()
        +boolean clearReadingData()
        +String getContentsOfOneItem()
        +boolean loadTemplate()
        +boolean readOneLineOfData()
        +boolean writeOutputBuffer()
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
    participant AIPS as AbstractIitoProcessorService
    participant ESCLogic as Enum2SwitchCaseCreationLogicImpl
    participant DtcService as DtcServiceImpl
    participant DtcLogic as DtcLogic
    participant Template as Enum2SwitchCaseCreationTool.yml
    participant Input as input.txt
    participant Intermediate as 中間ファイル
    participant Output as output.txt

    User->>ESCT: アプリケーション起動
    Note over ESCT: main(String[] args)
    ESCT->>ESCT: SpringApplication.run

    ESCT->>AT2OT: initialize()
    AT2OT->>ESCService: initialize(inputPath, templatePath, outputPath)
    ESCService->>AIPS: initialize(inputPath, templatePath, outputPath)
    AIPS->>AIPS: createTempIntermediateFile()

    ESCT->>AIT: execute()
    AIT->>ESCT: getIoService()
    ESCT-->>AIT: enum2SwitchCaseMakingService
    AIT->>ESCService: process()
    ESCService->>AIPS: process()
    AIPS->>ESCService: writeIntermediateFile()

    ESCService->>ESCLogic: initialize(inputPath, intermediatePath)
    ESCService->>ESCLogic: addOneLineOfDataToRows()

    loop 入力ファイル処理
        ESCService->>ESCLogic: readOneLineOfData()
        ESCLogic-->>Input: ファイル読み込み

        ESCService->>ESCService: processColumns()

        ESCService->>ESCLogic: convertEnumDefinition()
        ESCLogic->>ESCLogic: 列挙型定義抽出処理

        ESCService->>ESCLogic: addItemToRows()
        ESCLogic->>Intermediate: 中間ファイルに項目追加

        ESCService->>ESCLogic: addItemNameToRows()
        ESCLogic->>Intermediate: 中間ファイルに項目名追加

        ESCService->>ESCLogic: writeIntermediateFile()
        ESCLogic->>Intermediate: 中間ファイル書き込み

        ESCService->>ESCService: clearAndPrepareNextLine()
        ESCService->>ESCLogic: clearRows()
        ESCService->>ESCLogic: clearProcessingData()
        ESCService->>ESCLogic: addOneLineOfDataToRows()
    end

    AIPS->>DtcService: initialize(intermediatePath, templatePath, outputPath)
    AIPS->>DtcService: process()
    DtcService->>DtcLogic: initialize(intermediatePath, templatePath, outputPath)
    DtcService->>DtcLogic: loadTemplate()
    DtcLogic->>Template: テンプレート読み込み

    loop テンプレート適用処理
        DtcService->>DtcLogic: readOneLineOfData()
        DtcLogic-->>Intermediate: 中間ファイル読み込み
        DtcService->>DtcLogic: applyTemplateToInputFile()
        DtcService->>DtcLogic: addOutputBufferContent()
        DtcService->>DtcLogic: writeOutputBuffer()
        DtcLogic->>Output: 出力ファイル書き込み
        DtcService->>DtcLogic: clearOutputBufferContent()
    end

    ESCService->>ESCLogic: close()
    ESCLogic->>ESCLogic: closeReader()
    ESCLogic->>ESCLogic: closeWriter()

    ESCT-->>User: 処理完了
```

## 3. テンプレートファイル構造

Enum2SwitchCaseCreationTool.yml は以下の構造を持っています：

1. **intermediatePlaceholders**: 中間ファイルから直接取得するプレースホルダー定義

   - displayName: 画面表示用の名称
   - replacementPattern: 置換対象のパターン

2. **derivedPlaceholders**: 中間ファイルから取得した値を変換して生成するプレースホルダー定義

   - displayName: 画面表示用の名称
   - replacementPattern: 置換対象のパターン
   - sourceKey: 変換元となる中間プレースホルダーの displayName
   - transformation: 適用する変換処理

3. **templateContent**: テンプレートの内容
   - {item}, {itemName}のプレースホルダーが実際の値に置換される

## 4. 処理フロー詳細

1. ユーザーがアプリケーションを起動
2. SpringBoot アプリケーションが起動し、Enum2SwitchCaseCreationTool のインスタンスが生成される
3. AbstractTwo2OneTool の initialize()メソッドが呼び出され、Enum2SwitchCaseCreationService が初期化される
4. AbstractIoTool の execute()メソッドが呼び出され、メイン処理が実行される
5. Enum2SwitchCaseCreationServiceImpl の writeIntermediateFile()メソッドが実行され、入力ファイルの処理が開始される
6. 入力ファイルから 1 行ずつデータを読み込み、以下の処理を行う：
   - 列挙型定義の抽出と変換
   - 列挙型の項目と項目名の抽出
   - 中間ファイル形式に変換して中間ファイルに書き込み
7. 中間ファイルの生成が完了したら、DtcService（テンプレートの動的変換サービス）を使用して：
   - テンプレートファイル（Enum2SwitchCaseCreationTool.yml）を読み込む
   - 中間ファイルのデータを読み込む
   - テンプレートにデータを適用して出力ファイルを生成する
8. リソースがクローズされ、処理が完了する

## 5. 主要コンポーネント

### Enum2SwitchCaseCreationTool

- SpringBootApplication として動作するエントリーポイント
- AbstractDtcTool を継承（さらに AbstractTwo2OneTool を継承）
- Enum2SwitchCaseCreationService を使用して switch-case 文生成を実行

### AbstractDtcTool

- AbstractTwo2OneTool を継承
- テンプレートの動的変換ツールの抽象クラス
- IitoProcessorService を返す抽象メソッドを定義

### AbstractTwo2OneTool

- AbstractIoTool を継承
- テンプレートファイルパスの管理と初期化処理を担当

### Enum2SwitchCaseCreationServiceImpl

- AbstractIitoProcessorService を継承
- Enum2SwitchCaseCreationService インターフェースを実装
- 入力ファイルの読み込みと中間ファイル形式への変換を担当

### Enum2SwitchCaseCreationLogicImpl

- switch-case 文作成の実際のロジックを担当
- 列挙型定義から switch-case 文に必要な情報を抽出
- 中間ファイル形式の中間ファイルを生成

### DtcService（テンプレートの動的変換サービス）

- テンプレートファイルと中間ファイルデータを使用して最終的な出力ファイルを生成
- プレースホルダの置換処理を担当

### テンプレートファイル（Enum2SwitchCaseCreationTool.yml）

- YAML フォーマットで定義されたテンプレート設定ファイル
- 以下の主要セクションで構成：
  - `intermediatePlaceholders`: 中間ファイルから直接取得するプレースホルダー定義
  - `derivedPlaceholders`: 中間ファイルから取得した値を変換して生成するプレースホルダー定義
  - `templateContent`: 実際のテンプレート内容

#### intermediatePlaceholders

- 中間ファイルの各列から直接マッピングされるプレースホルダー
  - `{item}`: 列挙型の項目名
  - `{itemName}`: 列挙型の表示名

#### derivedPlaceholders

- 既存のプレースホルダーから変換して生成される派生プレースホルダー
  - 必要に応じて定義される変換プレースホルダー

#### templateContent

- switch-case 文のテンプレートを定義
- 上記のプレースホルダーを使用して、列挙型の各項目に対応する case 文を生成
