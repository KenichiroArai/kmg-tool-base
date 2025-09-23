# Javadoc 処理の設計図

## 1. クラス図

```mermaid
classDiagram
    %% 継承関係
    AbstractTool <|-- AbstractIoTool
    AbstractIoTool <|-- AbstractTwo2OneTool
    AbstractTwo2OneTool <|-- AbstractJdocTool
    AbstractJdocTool <|-- JdocTool

    %% インターフェース実装関係
    Two2OneService <|.. JdocService
    JdocService <|.. JdocServiceImpl
    JdocLogic <|.. JdocLogicImpl

    %% モデル実装関係
    JavadocModel <|.. JavadocModelImpl
    JavadocTagsModel <|.. JavadocTagsModelImpl
    JavadocTagModel <|.. JavadocTagModelImpl

    %% サービス関係
    JdocServiceImpl --> JdocLogic : uses
    JdocLogicImpl --> JavadocModel : uses
    JdocLogicImpl --> JavadocTagsModel : uses

    %% ファイル関連
    JdocTool ..> JavaFile : processes
    JdocTool ..> JavadocFile : generates

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

    class AbstractJdocTool {
        +AbstractJdocTool(String toolName)
        #abstract IitoProcessorService getIoService()
    }

    class JdocTool {
        -static String TOOL_NAME
        -JdocService jdocService
        +static void main(String[] args)
        +JdocTool()
        +void run(String[] args)
        #JdocService getIoService()
    }

    class Two2OneService {
        <<interface>>
        +Path getInputPath()
        +Path getOutputPath()
        +Path getTemplatePath()
        +boolean initialize(Path inputPath, Path templatePath, Path outputPath)
        +boolean process()
    }

    class JdocService {
        <<interface>>
    }

    class JdocServiceImpl {
        -Logger logger
        -KmgMessageSource messageSource
        -JdocLogic jdocLogic
        +JdocServiceImpl()
        +boolean initialize(Path inputPath, Path templatePath, Path outputPath)
        +boolean process()
        -void closeJdocLogic()
    }

    class JdocLogic {
        <<interface>>
        +Path getInputPath()
        +Path getOutputPath()
        +Path getTemplatePath()
        +boolean initialize(Path inputPath, Path templatePath, Path outputPath)
        +boolean loadJavaFile()
        +boolean parseJavadoc()
        +void processJavadocTags()
        +boolean addOutputBufferContent()
        +boolean writeOutputBuffer()
        +void clearOutputBufferContent()
        +boolean clearParsedData()
        +String getContentsOfOneItem()
    }

    class JdocLogicImpl {
        -Path inputPath
        -Path templatePath
        -Path outputPath
        -BufferedReader reader
        -BufferedWriter writer
        -String lineOfJavaCode
        -String convertedLine
        -KmgDelimiterTypes intermediateDelimiter
        -Map~String, String~ intermediatePlaceholderMap
        -List~JavadocModel~ javadocModels
        -String templateContent
        -String contentsOfOneItem
        -StringBuilder outputBufferContent
        +boolean initialize(Path inputPath, Path templatePath, Path outputPath)
        +boolean loadJavaFile()
        +boolean parseJavadoc()
        +void processJavadocTags()
        +boolean addOutputBufferContent()
        +boolean writeOutputBuffer()
        +void clearOutputBufferContent()
        +boolean clearParsedData()
        +String getContentsOfOneItem()
        -void processJavadocTags(List~JavadocModel~ javadocModels)
        -void processJavadocTag(JavadocModel javadocModel)
        -boolean loadTemplateContent()
        -boolean loadJavadocDefinitions()
    }

    class JavadocModel {
        <<interface>>
        +JavadocTagsModel getJavadocTagsModel()
        +String getSrcJavadoc()
    }

    class JavadocModelImpl {
        -String srcJavadoc
        -JavadocTagsModel javadocTagsModel
        +JavadocModelImpl(String javadoc)
    }

    class JavadocTagsModel {
        <<interface>>
        +JavadocTagModel findByTag(KmgJavadocTagTypes tag)
        +List~JavadocTagModel~ getJavadocTagModelList()
    }

    class JavadocTagsModelImpl {
        -List~JavadocTagModel~ javadocTagModelList
        +JavadocTagsModelImpl()
        +JavadocTagsModelImpl(String sourceJavadoc)
    }

    class JavadocTagModel {
        <<interface>>
        +String getDescription()
        +KmgJavadocTagTypes getTag()
        +String getTargetStr()
        +String getValue()
    }

    class JavadocTagModelImpl {
        -String targetStr
        -KmgJavadocTagTypes tag
        -String value
        -String description
        +JavadocTagModelImpl(String targetStr, KmgJavadocTagTypes tag, String value, String description)
    }

    class JavaFile {
        +String content
        +List~String~ javadocBlocks
    }

    class JavadocFile {
        +String content
        +List~JavadocModel~ javadocModels
    }
```

## 2. シーケンス図

```mermaid
sequenceDiagram
    participant User as ユーザー
    participant JdocTool as JdocTool
    participant AJT as AbstractJdocTool
    participant AT2OT as AbstractTwo2OneTool
    participant AIT as AbstractIoTool
    participant JdocService as JdocServiceImpl
    participant JdocLogic as JdocLogicImpl
    participant JavaFile as JavaFile
    participant JavadocFile as JavadocFile

    User->>JdocTool: アプリケーション起動
    Note over JdocTool: main(String[] args)
    JdocTool->>JdocTool: SpringApplication.run
    JdocTool->>JdocTool: run(args)

    JdocTool->>AT2OT: initialize()
    AT2OT->>JdocService: initialize(inputPath, templatePath, outputPath)
    JdocService->>JdocService: パス設定

    JdocTool->>AIT: execute()
    AIT->>JdocTool: getIoService()
    JdocTool-->>AIT: jdocService
    AIT->>JdocService: process()
    JdocService->>JdocLogic: initialize(inputPath, templatePath, outputPath)
    JdocLogic->>JdocLogic: ファイルオープン処理

    JdocService->>JdocLogic: loadJavaFile()
    JdocLogic->>JavaFile: Javaファイル読み込み
    JdocLogic->>JdocLogic: Javaファイル内容解析

    JdocService->>JdocLogic: parseJavadoc()
    JdocLogic->>JdocLogic: Javadocブロック抽出
    JdocLogic->>JdocLogic: Javadocタグ解析
    JdocLogic->>JdocLogic: Javadocモデル生成

    loop Javadocブロック処理
        JdocService->>JdocLogic: processJavadocTags()
        JdocLogic->>JdocLogic: テンプレートコンテンツ初期化
        JdocLogic->>JdocLogic: Javadocタグ処理
        Note over JdocLogic: タグ解析・変換処理
        JdocLogic->>JdocLogic: 出力コンテンツ生成

        JdocService->>JdocLogic: addOutputBufferContent()
        JdocLogic->>JdocLogic: 出力バッファに追加

        JdocService->>JdocLogic: writeOutputBuffer()
        JdocLogic->>JavadocFile: 出力ファイル書き込み

        JdocService->>JdocLogic: clearOutputBufferContent()
        JdocLogic->>JdocLogic: 出力バッファクリア
    end

    JdocService->>JdocLogic: close()
    JdocLogic->>JdocLogic: リソースクローズ

    JdocTool-->>User: 処理完了
```

## 3. 処理フロー詳細

1. ユーザーがアプリケーションを起動
2. SpringBoot アプリケーションが起動し、JdocTool のインスタンスが生成される
3. AbstractTwo2OneTool の initialize()メソッドが呼び出され、JdocService が初期化される
4. AbstractIoTool の execute()メソッドが呼び出され、メイン処理が実行される
5. JdocServiceImpl の process()メソッドが実行され、以下の処理が開始される：
   - JdocLogic の初期化（入力ファイル、テンプレートファイル、出力ファイルのオープン）
   - Java ファイルの読み込みと内容解析
   - Javadoc ブロックの抽出と解析
   - Javadoc タグの処理と変換
   - テンプレートへのデータ適用と出力ファイル生成
6. リソースがクローズされ、処理が完了する

## 4. 主要コンポーネント

### JdocTool

- SpringBootApplication として動作するエントリーポイント
- AbstractJdocTool を継承（さらに AbstractTwo2OneTool を継承）
- JdocService を使用して Javadoc 処理を実行

### AbstractJdocTool

- AbstractTwo2OneTool を継承
- Javadoc 処理ツールの抽象クラス
- IitoProcessorService を返す抽象メソッドを定義

### AbstractTwo2OneTool

- AbstractIoTool を継承
- テンプレートファイルパスの管理と初期化処理を担当

### JdocServiceImpl

- Two2OneService インターフェースを実装
- JdocLogic を使用して Javadoc 処理を実行
- Java ファイルの読み込みと Javadoc ファイルの生成を担当

### JdocLogicImpl

- JdocLogic インターフェースを実装
- Javadoc 処理の実際のロジックを担当
- Java ファイルの解析と Javadoc ブロックの抽出
- Javadoc タグの解析と変換処理
- テンプレートへのデータ適用と出力ファイル生成

### JavadocModelImpl

- JavadocModel インターフェースを実装
- Javadoc の基本情報を管理
- 元の Javadoc 文字列とタグ情報を保持

### JavadocTagsModelImpl

- JavadocTagsModel インターフェースを実装
- Javadoc タグの一覧情報を管理
- 正規表現を使用したタグの抽出と解析
- タグの検索機能を提供

### JavadocTagModelImpl

- JavadocTagModel インターフェースを実装
- 個別の Javadoc タグ情報を管理
- タグの種類、値、説明などの詳細情報を保持

## 5. 処理対象ファイル

### JavaFile

- 入力となる Java ソースファイル
- Javadoc コメントが含まれる Java コード
- クラス、メソッド、フィールドなどの定義

### JavadocFile

- 出力される Javadoc ファイル
- 解析された Javadoc 情報を構造化した形式
- タグ情報や説明文が整理された形式

## 6. Javadoc タグ処理

### 対応タグ

- `@author`: 作成者情報
- `@since`: バージョン情報
- `@version`: バージョン番号
- `@param`: パラメータ説明
- `@return`: 戻り値説明
- `@throws`: 例外説明
- その他の標準 Javadoc タグ

### タグ解析処理

1. 正規表現を使用したタグの抽出
2. タグの種類と値の分離
3. 説明文の解析と整形
4. タグ情報のモデル化
5. 出力形式への変換

## 7. 拡張性

### カスタムタグ対応

- 新しい Javadoc タグの追加が容易
- タグの解析ロジックの拡張
- 出力形式のカスタマイズ

### テンプレート機能

- 出力形式のテンプレート化
- プレースホルダーによる動的生成
- 複数出力形式の対応
