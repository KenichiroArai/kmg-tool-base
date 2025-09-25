# Javadoc 行削除ツール操作手順書

## 1. 概要

Javadoc 行削除ツールは、指定された Java ファイルの特定の行から Javadoc コメント行を削除するためのツールである。

### 主な機能

- 入力ファイルで指定されたファイルパスと行番号に基づく Javadoc 行の削除
- 複数の Java ファイルに対する一括処理
- 行番号の降順処理による安全な削除
- 削除対象行の自動検証

## 2. ファイル配置

### 2.1 ディレクトリ構造

```text
kmg-tool/
├── work/io/                          # 優先パス（開発時推奨）
│   └── input.txt                     # 入力ファイル
└── src/main/resources/tool/io/       # 代替パス
    └── input.txt                     # 入力ファイル
```

### 2.2 ファイルパスの優先順位

1. **優先パス**: `work/io/` ディレクトリが存在する場合
2. **代替パス**: `work/io/` ディレクトリが存在しない場合、`src/main/resources/tool/io/` を使用

## 3. 入力ファイルの準備

### 3.1 入力ファイル形式

入力ファイル（`input.txt`）はファイルパスと行番号を含む形式で記述する：

```text
ファイルパス.java:行番号: 内容
```

### 3.2 入力ファイル例

```text
D:\wk\kmg-core\src\test\java\kmg\core\infrastructure\model\val\impl\KmgValDataModelImplTest.java:21: 警告: デフォルトのコンストラクタの使用で、コメントが指定されていません
D:\wk\kmg-core\doc\javadoc\kmg\core\infrastructure\exception\KmgValException.htmlの生成中...
D:\wk\kmg-core\doc\javadoc\kmg\core\infrastructure\exception\KmgValExceptionTest.htmlの生成中...
D:\wk\kmg-core\doc\javadoc\kmg\core\infrastructure\model\val\KmgValsModel.htmlの生成中...
D:\wk\kmg-core\doc\javadoc\kmg\core\infrastructure\model\val\impl\KmgValsModelImpl.htmlの生成中...
D:\wk\kmg-core\doc\javadoc\kmg\core\infrastructure\model\val\impl\KmgValsModelImplTest.htmlの生成中...
D:\wk\kmg-core\src\test\java\kmg\core\infrastructure\model\val\impl\KmgValsModelImplTest.java:24: 警告: デフォルトのコンストラクタの使用で、コメントが指定されていません
```

### 3.3 入力ファイルの注意事項

- ファイルパスは絶対パスで記述する
- 行番号は削除したい Javadoc 行の実際の行番号を指定する
- `.java:`の形式で Java ファイルを識別する
- コロン（`:`）区切りでファイルパス、行番号、内容を分離する
- 同一ファイルの複数行を指定可能（自動的に降順でソートされる）
- Java ファイル以外の行は無視される

## 4. 実行手順

### 4.1 基本的な実行方法

1. **入力ファイルの準備**

   ```bash
   # work/ioディレクトリを作成（存在しない場合）
   mkdir -p work/io

   # 入力ファイルを作成（Javadocコンパイル時の警告メッセージを含む）
   cat > work/io/input.txt << 'EOF'
   D:\wk\kmg-core\src\test\java\kmg\core\infrastructure\model\val\impl\KmgValDataModelImplTest.java:21: 警告: デフォルトのコンストラクタの使用で、コメントが指定されていません
   D:\wk\kmg-core\src\test\java\kmg\core\infrastructure\model\val\impl\KmgValsModelImplTest.java:24: 警告: デフォルトのコンストラクタの使用で、コメントが指定されていません
   EOF
   ```

2. **ツールの実行**

   ```bash
   # Mavenを使用して実行
   mvn exec:java -Dexec.mainClass="kmg.tool.jdocr.presentation.ui.cli.JavadocLineRemoverTool"

   # または、JARファイルから実行
   java -cp target/classes:target/dependency/* kmg.tool.jdocr.presentation.ui.cli.JavadocLineRemoverTool
   ```

### 4.2 コマンドライン引数

現在のバージョンでは、コマンドライン引数は使用されない。ファイルパスは自動的に決定される。

### 4.3 実行時のログ出力

実行時には以下のようなログが出力される：

```text
[INFO] Javadoc行削除ツール開始
[INFO] 入力ファイル: work/io/input.txt
[DEBUG] 削除対象行数: 2行
[INFO] Javadoc行削除処理完了
[INFO] Javadoc行削除ツール終了
```

## 5. 処理結果の確認

### 5.1 処理内容

ツールは以下の処理を実行する：

1. **入力ファイルの解析**

   - 入力ファイルから Java ファイルのパスと行番号を抽出
   - ファイルパスごとに行番号をグループ化
   - 行番号を降順にソート（削除時のインデックスずれを防止）

2. **Javadoc 行の削除**

   - 対象ファイルを順次処理
   - 指定された行番号の行を削除
   - ファイルに変更内容を書き戻し

3. **結果の出力**
   - 削除した行数をログに出力
   - 処理結果をコンソールに表示
