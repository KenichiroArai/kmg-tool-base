# KMG ツール（kmg-tool）

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen.svg)](https://github.com/your-username/kmg-tool)

KMG ツールは、Java 開発における様々な自動化処理を提供するツール集です。

## 概要

KMG ツールは、Java 開発の効率化を目的として開発されたツール群です。Javadoc の自動生成、アクセサメソッドの作成、フィールド定義の自動生成など、開発者が日常的に行う作業を自動化します。

## 主な機能

### Javadoc 関連ツール

- **Javadoc タグ設定ツール**: Java ファイルの Javadoc コメントにタグ（@author、@since、@version）を自動追加
- **Javadoc 行削除ツール**: 指定された行から Javadoc コメント行を削除

### アクセサメソッド生成ツール

- **アクセサ作成ツール**: フィールド定義から getter/setter メソッドを自動生成
- **インタフェースアクセサ作成ツール**: インタフェース用のアクセサメソッドを自動生成

### フィールド・データベース関連ツール

- **フィールド作成ツール**: データベースフィールド定義から Java フィールド宣言を自動生成
- **挿入 SQL 作成ツール**: データベース用の挿入 SQL を自動生成

### コード生成ツール

- **メッセージの種類作成ツール**: メッセージ種類定義から Java 列挙型を自動生成
- **列挙型から case 文作成ツール**: 列挙型から switch-case 文を自動生成

### 変換・マッピングツール

- **マッピング変換ツール**: Java ファイル内の文字列を一括置換
- **シンプル 1 入力 1 出力ツール**: 基本的な 1 対 1 変換処理
- **シンプル 2 入力 1 出力ツール**: テンプレートを使用した変換処理

## ドキュメント

詳細なドキュメントは [docs/](docs/) ディレクトリに格納されています：

- **操作手順書**: 各ツールの使用方法と実行手順
- **設計書**: システム設計図とアーキテクチャ

## セットアップ

### 前提条件

- Java 21 以上
- Maven 3.6 以上

### インストール

1. リポジトリをクローンします：

   ```bash
   git clone https://github.com/KenichiroArai/kmg-tool.git
   cd kmg-tool
   ```

2. Maven を使用してビルドします：

   ```bash
   mvn clean install
   ```

## 使用方法

各ツールは個別に実行可能で、Maven を使用して以下のように実行できます：

```bash
# 例：Javadocタグ設定ツールの実行
mvn exec:java -Dexec.mainClass="kmg.tool.jdts.presentation.ui.cli.JavadocTagSetterTool"
```

詳細な使用方法については、各ツールの操作手順書を参照してください。

## 貢献

プロジェクトへの貢献を歓迎します！以下の手順で貢献できます：

1. このリポジトリをフォークします
2. フィーチャーブランチを作成します (`git checkout -b feature/amazing-feature`)
3. 変更をコミットします (`git commit -m 'Add some amazing feature'`)
4. ブランチにプッシュします (`git push origin feature/amazing-feature`)
5. プルリクエストを作成します

## 問題の報告

バグを発見した場合や機能要求がある場合は、[Issues](https://github.com/KenichiroArai/kmg-tool/issues) で報告してください。

## ライセンス

このプロジェクトは MIT ライセンスの下で公開されています。詳細は[LICENSE](LICENSE)ファイルを参照してください。
