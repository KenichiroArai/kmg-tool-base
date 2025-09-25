# Javadoc タグ設定ツール操作手順書

## 1. 概要

Javadoc タグ設定ツールは、Java ファイルの Javadoc コメントに対して、指定されたタグ（@author、@since、@version）を自動的に追加・更新・削除するためのツールである。

### 主な機能

- Java ファイルの Javadoc コメントに対するタグの自動設定
- テンプレートベースの柔軟な設定
- 準拠モードによる Java 標準ルールに従った自動配置
- 手動モードによる特定要素へのタグ設定
- 既存タグの上書き・保持設定

## 2. ファイル配置

### 2.1 ディレクトリ構造

```text
kmg-tool\
├── work\io\                          # 優先パス（開発時推奨）
│   ├── input.txt                     # 入力ファイル
│   └── template\
│       └── JavadocTagSetterTool.yml  # テンプレートファイル
└── src\main\resources\tool\io\       # 代替パス
    ├── input.txt                     # 入力ファイル
    └── template\
        └── JavadocTagSetterTool.yml  # テンプレートファイル
```

### 2.2 ファイルパスの優先順位

1. **優先パス**: `work\io\` ディレクトリが存在する場合
2. **代替パス**: `work\io\` ディレクトリが存在しない場合、`src\main\resources\tool\io\` を使用

## 3. 入力ファイルの準備

### 3.1 入力ファイル形式

入力ファイル（`input.txt`）は処理対象の Java ファイルのパスを記述する：

```text
対象Javaファイルのパス
```

### 3.2 入力ファイル例

```text
D:\wk\kmg-core\src\main\java\kmg\tool\jdts\presentation\ui\cli\JavadocTagSetterTool.java
```

### 3.3 入力ファイルの注意事項

- 処理対象の Java ファイルのパスを 1 行で記述する
- 絶対パスで記述する
- ディレクトリを指定した場合は、そのディレクトリ内の全ての Java ファイルが処理対象となる

## 4. テンプレートファイルの設定

### 4.1 テンプレートファイルの場所

- **優先パス**: `work\io\template\JavadocTagSetterTool.yml`
- **代替パス**: `src\main\resources\tool\io\template\JavadocTagSetterTool.yml`

### 4.2 テンプレートファイルの設定方法

テンプレートファイルの詳細な構造や設定方法については、実際のテンプレートファイル（`JavadocTagSetterTool.yml`）を参照してください。テンプレートファイルには、プレースホルダーの定義やテンプレート内容の設定方法が記載されています。

## 5. 実行手順

### 5.1 基本的な実行方法

1. **入力ファイルの準備**

   ```bash
   # work\ioディレクトリを作成（存在しない場合）
   mkdir -p work\io

   # 入力ファイルを作成
   cat > work\io\input.txt << 'EOF'
   D:\wk\kmg-core\src\main\java\kmg\tool\jdts\presentation\ui\cli\JavadocTagSetterTool.java
   EOF
   ```

2. **テンプレートファイルの配置**

   ```bash
   # テンプレートディレクトリを作成
   mkdir -p work\io\template

   # テンプレートファイルをコピー
   cp src\main\resources\tool\io\template\JavadocTagSetterTool.yml work\io\template\
   ```

3. **ツールの実行**

   ```bash
   # Mavenを使用して実行
   mvn exec:java -Dexec.mainClass="kmg.tool.jdts.presentation.ui.cli.JavadocTagSetterTool"

   # または、JARファイルから実行
   java -cp target\classes:target\dependency\* kmg.tool.jdts.presentation.ui.cli.JavadocTagSetterTool
   ```

### 5.2 コマンドライン引数

現在のバージョンでは、コマンドライン引数は使用されない。ファイルパスは自動的に決定される。

### 5.3 実行時のログ出力

実行時には以下のようなログが出力される：

```text
[INFO] Javadocタグ設定ツール開始
[INFO] 入力ファイル: work\io\input.txt
[INFO] テンプレートファイル: work\io\template\JavadocTagSetterTool.yml
[INFO] 対象ファイル: src\main\java\kmg\tool\jdts\presentation\ui\cli\JavadocTagSetterTool.java
[INFO] 処理完了: 1件のファイルを処理しました
[INFO] Javadocタグ設定ツール終了
```

## 6. 処理結果の確認

### 6.1 処理対象ファイルの確認

処理対象の Java ファイルが直接更新される。元のファイルは上書きされるため、必要に応じてバックアップを取ることを推奨する。

### 6.2 処理例

入力ファイルの例に対して、以下のような処理が行われる：

**処理前:**

```java
\**
 * テンプレートの動的変換派生プレースホルダー定義モデル実装<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * <\p>
 *\
public class DtcDerivedPlaceholderModelImpl {
}
```

**処理後:**

```java
\**
 * テンプレートの動的変換派生プレースホルダー定義モデル実装<br>
 * <p>
 * 「Dtc」→「DynamicTemplateConversion」の略。
 * <\p>
 * @author KenichiroArai
 * @since 0.1.0
 * @version 0.1.0
 *\
public class DtcDerivedPlaceholderModelImpl {
}
```

### 6.3 処理の特徴

- **準拠モード**: Java の標準的な Javadoc 配置ルールに従った自動配置
- **タグの追加**: 既存の Javadoc コメントにタグを追加
- **タグの更新**: 既存のタグを設定値で更新
- **タグの削除**: 設定に基づいて不適切な位置のタグを削除
- **安全な処理**: 文字列内の Javadoc コメントは処理対象外
