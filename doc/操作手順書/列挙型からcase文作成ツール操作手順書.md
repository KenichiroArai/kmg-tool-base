# 列挙型から case 文作成ツール操作手順書

## 1. 概要

列挙型から case 文作成ツールは、Java の列挙型定義から switch-case 文を自動生成するためのツールである。

### 主な機能

- 列挙型定義から switch-case 文の自動生成
- テンプレートベースの柔軟な出力形式
- コメント付きの case 文の生成
- 中間ファイルを経由した安全な処理

## 2. ファイル配置

### 2.1 ディレクトリ構造

```text
kmg-tool/
├── work/io/                                        # 優先パス（開発時推奨）
│   ├── input.txt                                   # 入力ファイル
│   ├── output.txt                                  # 出力ファイル
│   └── template/
│       └── Enum2SwitchCaseCreationTool.yml         # テンプレートファイル
└── src/main/resources/tool/io/                     # 代替パス
    ├── input.txt                                   # 入力ファイル
    ├── output.txt                                  # 出力ファイル
    └── template/
        └── Enum2SwitchCaseCreationTool.yml         # テンプレートファイル
```

### 2.2 ファイルパスの優先順位

1. **優先パス**: `work/io/` ディレクトリが存在する場合
2. **代替パス**: `work/io/` ディレクトリが存在しない場合、`src/main/resources/tool/io/` を使用

## 3. 入力ファイルの準備

### 3.1 入力ファイル形式

入力ファイル（`input.txt`）は Java 列挙型定義形式で列挙項目を記述する：

```text
/**
 * Javadocコメント
 */
項目名("表示名", …),
```

### 3.2 入力ファイル例

```text
    /**
     * 指定無し
     *
     * @since 0.1.0
     */
    NONE("指定無し",

    /**
     * クラス
     *
     * @since 0.1.0
     */
    CLASS("クラス",

    /**
     * 内部クラス
     *
     * @since 0.1.0
     */
    INNER_CLASS("内部クラス",
```

### 3.3 入力ファイルの注意事項

- 各項目は Javadoc コメントブロック（`/** ... */`）で開始する
- 項目名の後に表示名を括弧内に記述する（`項目名("表示名",`）
- 正規表現パターンは `(項目名)\\("(表示名)",` の形式で項目名と表示名を抽出する
- 空行は無視される
- 追加の値（識別子、説明など）がある場合も処理されるが、ツールでは最初の表示名のみが使用される

## 4. テンプレートファイルの設定

### 4.1 テンプレートファイルの場所

- **優先パス**: `work/io/template/Enum2SwitchCaseCreationTool.yml`
- **代替パス**: `src/main/resources/tool/io/template/Enum2SwitchCaseCreationTool.yml`

### 4.2 テンプレートファイルの設定方法

テンプレートファイルの詳細な構造や設定方法については、実際のテンプレートファイル（`Enum2SwitchCaseCreationTool.yml`）を参照してください。テンプレートファイルには、プレースホルダーの定義やテンプレート内容の設定方法が記載されています。

## 5. 実行手順

### 5.1 基本的な実行方法

1. **入力ファイルの準備**

   ```bash
   # work/ioディレクトリを作成（存在しない場合）
   mkdir -p work/io

   # 入力ファイルを作成
   cat > work/io/input.txt << 'EOF'
       /**
        * 指定無し
        *
        * @since 0.1.0
        */
       NONE("指定無し", ...),

       /**
        * クラス
        *
        * @since 0.1.0
        */
       CLASS("クラス", ...),

       /**
        * 内部クラス
        *
        * @since 0.1.0
        */
       INNER_CLASS("内部クラス", ...),
   EOF
   ```

2. **テンプレートファイルの配置**

   ```bash
   # テンプレートディレクトリを作成
   mkdir -p work/io/template

   # テンプレートファイルをコピー
   cp src/main/resources/tool/io/template/Enum2SwitchCaseCreationTool.yml work/io/template/
   ```

3. **ツールの実行**

   ```bash
   # Mavenを使用して実行
   mvn exec:java -Dexec.mainClass="kmg.tool.e2scc.presentation.ui.cli.Enum2SwitchCaseCreationTool"

   # または、JARファイルから実行
   java -cp target/classes:target/dependency/* kmg.tool.e2scc.presentation.ui.cli.Enum2SwitchCaseCreationTool
   ```

### 5.2 コマンドライン引数

現在のバージョンでは、コマンドライン引数は使用されない。ファイルパスは自動的に決定される。

### 5.3 実行時のログ出力

実行時には以下のようなログが出力される：

```text
[INFO] 列挙型からcase文作成ツール開始
[INFO] 入力ファイル: work/io/input.txt
[INFO] テンプレートファイル: work/io/template/Enum2SwitchCaseCreationTool.yml
[INFO] 出力ファイル: work/io/output.txt
[DEBUG] 項目処理完了: NONE - 指定無し
[DEBUG] 項目処理完了: CLASS - クラス
[DEBUG] 項目処理完了: INNER_CLASS - 内部クラス
[INFO] 処理完了: 3件の列挙項目を処理しました
[INFO] 列挙型からcase文作成ツール終了
```

## 6. 出力ファイルの確認

### 6.1 出力ファイルの場所

- **優先パス**: `work/io/output.txt`
- **代替パス**: `src/main/resources/tool/io/output.txt`

### 6.2 出力例

入力ファイルの例に対して、以下のような出力が生成される：

```java
case NONE:
    /* 指定無し */
    break;
case CLASS:
    /* クラス */
    break;
case INNER_CLASS:
    /* 内部クラス */
    break;
```

### 6.3 出力ファイルの使用方法

生成された case 文は、以下のように switch 文内で使用できる：

```java
public void processClassificationType(JavaClassificationTypes classificationType) {
    switch (classificationType) {
        case NONE:
            /* 指定無し */
            break;
        case CLASS:
            /* クラス */
            break;
        case INNER_CLASS:
            /* 内部クラス */
            break;
        default:
            throw new IllegalArgumentException("未対応の分類: " + classificationType);
    }
}
```

## 7. トラブルシューティング

### 7.1 よくあるエラーと対処法

#### エラー: 入力ファイルが見つからない

```text
[ERROR] 入力ファイルが見つかりません: work/io/input.txt
```

**対処法**:

- 入力ファイルが正しい場所に配置されているか確認
- ファイル名が `input.txt` になっているか確認
- ファイルの読み取り権限があるか確認

#### エラー: テンプレートファイルが見つからない

```text
[ERROR] テンプレートファイルが見つかりません: work/io/template/Enum2SwitchCaseCreationTool.yml
```

**対処法**:

- テンプレートファイルが正しい場所に配置されているか確認
- ファイル名が `Enum2SwitchCaseCreationTool.yml` になっているか確認
- YAML ファイルの構文が正しいか確認

#### エラー: 入力ファイルの形式が正しくない

```text
[ERROR] 行番号 3: 列挙項目の定義形式が正しくありません
```

**対処法**:

- 各項目が Javadoc コメントブロック（`/** ... */`）で開始されているか確認
- 項目名の後に表示名が括弧内に正しく記述されているか確認（`項目名("表示名",`）
- 正規表現パターン `(\\w+)\\(\"(\\S+)\",` にマッチする形式になっているか確認
- 不正な文字や余分な空白が含まれていないか確認
- 表示名に空白文字が含まれていないか確認（`\\S+` パターンのため）

#### エラー: 列挙項目の変換に失敗

```text
[ERROR] 列挙項目の変換処理でエラーが発生しました
```

**対処法**:

- 入力ファイルの各行が正しい列挙型定義形式になっているか確認
- 項目名に使用できない文字（空白、特殊文字など）が含まれていないか確認
- コメント部分に改行文字などの制御文字が含まれていないか確認

### 7.2 ログファイルの確認

詳細なエラー情報は以下のログファイルで確認できる：

- `logs/kmg-tool/kmg-tool.log`
- `logs/exceptions/kmg-core-exceptions.log`
