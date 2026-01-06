# KMG ツール基盤（kmg-tool-base）

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen.svg)](https://github.com/KenichiroArai/kmg-tool-base)

KMG ツール基盤（kmg-tool-base）は、Java 開発における様々な自動化処理を提供するツール集の基盤ライブラリです。

## 概要

KMG ツール基盤は、Java 開発の効率化を目的として開発されたツール群の基盤となるライブラリです。Javadoc の自動生成、アクセサメソッドの作成、フィールド定義の自動生成など、開発者が日常的に行う作業を自動化するための共通機能を提供します。

## ドキュメント

詳細なドキュメントは [docs/](docs/) ディレクトリに格納されています。詳細は [docs/README.md](docs/README.md) を参照してください。

- **設計書**: システム設計図とアーキテクチャ（[設計書一覧](docs/README.md#設計書)）

## 主な機能

### Javadoc 関連ツール

- **Javadoc タグ設定ツール**: Java ファイルの Javadoc コメントにタグ（@author、@since、@version）を自動追加
- **Javadoc 行削除ツール**: 指定された行から Javadoc コメント行を削除

### アクセサメソッド生成ツール

- **アクセサ作成ツール**: フィールド定義から getter/setter メソッドを自動生成

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

## プロジェクト構成

```test
kmg-tool-base/
├── docs/                          # ドキュメント
│   ├── README.md                  # ドキュメント概要
│   ├── メッセージ一覧.xlsx         # メッセージ一覧
│   └── 設計書/                    # システム設計書
├── src/                           # ソースコード
│   ├── main/java/kmg/tool/base/   # メインソースコード
│   │   ├── acccrt/                # アクセサ作成ツール
│   │   │   └── application/       # アプリケーション層
│   │   │       ├── logic/         # ビジネスロジック
│   │   │       ├── service/       # サービス
│   │   │       └── types/         # 型定義
│   │   ├── cmn/                   # 共通機能
│   │   │   └── infrastructure/    # インフラストラクチャ層
│   │   │       ├── exception/    # 例外処理
│   │   │       ├── msg/          # メッセージ管理
│   │   │       └── types/        # 型定義
│   │   ├── dtc/                   # データ変換ツール
│   │   │   └── domain/           # ドメイン層
│   │   │       ├── logic/        # ビジネスロジック
│   │   │       ├── model/        # モデル
│   │   │       ├── service/      # サービス
│   │   │       └── types/        # 型定義
│   │   ├── e2scc/                 # 列挙型からcase文作成ツール
│   │   │   ├── application/      # アプリケーション層
│   │   │   └── service/          # サービス
│   │   ├── fldcrt/                # フィールド作成ツール
│   │   │   └── application/       # アプリケーション層
│   │   │       ├── logic/        # ビジネスロジック
│   │   │       └── service/       # サービス
│   │   ├── iito/                  # 挿入SQL作成ツール（中間処理）
│   │   │   └── domain/           # ドメイン層
│   │   │       ├── logic/        # ビジネスロジック
│   │   │       └── service/      # サービス
│   │   ├── input/                 # 入力処理
│   │   │   └── domain/           # ドメイン層
│   │   │       └── service/      # サービス
│   │   ├── io/                    # 入出力処理
│   │   │   └── domain/           # ドメイン層
│   │   │       └── service/      # サービス
│   │   ├── is/                    # 挿入SQL作成ツール
│   │   │   └── application/      # アプリケーション層
│   │   │       ├── logic/        # ビジネスロジック
│   │   │       └── service/      # サービス
│   │   ├── jdoc/                  # Javadoc処理（共通機能）
│   │   │   └── domain/           # ドメイン層
│   │   │       ├── model/        # モデル
│   │   │       └── types/        # 型定義
│   │   ├── jdocr/                 # Javadoc行削除ツール
│   │   │   ├── application/      # アプリケーション層
│   │   │   └── service/          # サービス
│   │   ├── jdts/                  # Javadocタグ設定ツール
│   │   │   └── application/      # アプリケーション層
│   │   │       ├── logic/        # ビジネスロジック
│   │   │       ├── model/        # モデル
│   │   │       ├── service/      # サービス
│   │   │       └── types/        # 型定義
│   │   ├── mptf/                  # マッピング変換ツール
│   │   │   └── application/      # アプリケーション層
│   │   │       └── service/      # サービス
│   │   ├── msgtpcrt/              # メッセージの種類作成ツール
│   │   │   ├── application/      # アプリケーション層
│   │   │   └── service/          # サービス
│   │   ├── one2one/               # シンプル1入力1出力ツール
│   │   │   ├── application/      # アプリケーション層
│   │   │   └── domain/           # ドメイン層
│   │   ├── simple/                # シンプルツール
│   │   │   ├── application/      # アプリケーション層
│   │   │   └── domain/           # ドメイン層
│   │   ├── two2one/               # シンプル2入力1出力ツール
│   │   │   ├── application/      # アプリケーション層
│   │   │   └── domain/           # ドメイン層
│   │   └── val/                   # バリデーション機能
│   │       └── model/            # モデル
│   ├── main/resources/            # リソースファイル
│   │   ├── application.properties
│   │   ├── kmg-tool-base-messages.properties
│   │   ├── kmg-tool-base-messages-log.properties
│   │   ├── kmg-tool-base-messages-val.properties
│   │   ├── logback-kmg-tool-base.xml
│   │   └── tool/io/               # テスト用入出力ファイル
│   │       ├── input.txt
│   │       ├── output.txt
│   │       └── template/          # テンプレートファイル
│   └── test/                      # テストコード
│       ├── java/kmg/tool/base/    # テストソースコード
│       └── resources/             # テストリソース
├── target/                        # ビルド成果物
├── logs/                          # ログファイル
├── work/                          # 作業ディレクトリ
├── scripts/                       # スクリプト
│   └── release.bat               # リリーススクリプト
├── pom.xml                       # Maven設定ファイル
└── README.md                     # プロジェクト概要
```

### 主要パッケージ構成

- **acccrt**: アクセサ作成ツール（getter/setter 生成）
  - `application/logic`: アクセサ生成ロジック
  - `application/service`: アクセサ生成サービス
  - `application/types`: アクセサ関連型定義
- **cmn**: 共通機能（例外処理、メッセージ管理）
  - `infrastructure/exception`: 例外クラス（KmgToolMsgException、KmgToolValException）
  - `infrastructure/msg`: メッセージ管理（ログ、例外、バリデーション）
  - `infrastructure/types`: メッセージ型定義
- **dtc**: データ変換ツール（テンプレート変換）
  - `domain/logic`: データ変換ロジック
  - `domain/model`: 変換モデル
  - `domain/service`: データ変換サービス
  - `domain/types`: 変換関連型定義
- **e2scc**: 列挙型から case 文作成ツール
  - `application/logic`: case 文生成ロジック
  - `service`: case 文生成サービス
- **fldcrt**: フィールド作成ツール（DB 定義から Java フィールド生成）
  - `application/logic`: フィールド生成ロジック
  - `application/service`: フィールド生成サービス
- **iito**: 挿入 SQL 作成ツール（中間処理）
  - `domain/logic`: 1 行パターン処理ロジック
  - `domain/service`: 挿入 SQL 処理サービス
- **input**: 入力処理（ファイル読み込み、バリデーション）
  - `domain/service`: 入力サービス（プレーンテキスト、CSV など）
- **io**: 入出力処理
  - `domain/service`: 入出力サービス
- **is**: 挿入 SQL 作成ツール
  - `application/logic`: 挿入 SQL 生成ロジック
  - `application/service`: 挿入 SQL 生成サービス
- **jdoc**: Javadoc 処理（共通機能）
  - `domain/model`: Javadoc モデル（タグ、コメントなど）
  - `domain/types`: Javadoc 関連型定義
- **jdocr**: Javadoc 行削除ツール
  - `application/logic`: Javadoc 行削除ロジック
  - `service`: Javadoc 行削除サービス
- **jdts**: Javadoc タグ設定ツール
  - `application/logic`: タグ設定ロジック
  - `application/model`: タグ設定モデル
  - `application/service`: タグ設定サービス
  - `application/types`: タグ設定関連型定義
- **mptf**: マッピング変換ツール（文字列一括置換）
  - `application/service`: マッピング変換サービス
- **msgtpcrt**: メッセージの種類作成ツール
  - `application/logic`: メッセージ種類生成ロジック
  - `service`: メッセージ種類生成サービス
- **one2one**: シンプル 1 入力 1 出力ツール
  - `application/service`: 1 対 1 変換サービス
  - `domain/service`: 1 対 1 変換ドメインサービス
- **simple**: シンプルツール（基本変換）
  - `application/service`: シンプル変換サービス
  - `domain/service`: シンプル変換ドメインサービス
- **two2one**: シンプル 2 入力 1 出力ツール（テンプレート変換）
  - `application/types`: 変換関連型定義
  - `domain/service`: 2 対 1 変換ドメインサービス
- **val**: バリデーション機能
  - `model`: バリデーションモデル

## セットアップ

### 前提条件

- Java 21 以上
- Maven 3.6 以上

### インストール

1. リポジトリをクローンします：

   ```bash
   git clone https://github.com/KenichiroArai/kmg-tool-base.git
   cd kmg-tool-base
   ```

2. Maven を使用してビルドします：

   ```bash
   mvn clean install
   ```

## 使用方法

各ツールは個別に実行可能で、Maven を使用して以下のように実行できます：

```bash
# 例：Javadocタグ設定ツールの実行
mvn exec:java -Dexec.mainClass="kmg.tool.base.jdts.presentation.ui.cli.JavadocTagSetterTool"
```

詳細な使用方法については、[docs/README.md](docs/README.md) を参照してください。

## 貢献

プロジェクトへの貢献を歓迎します！以下の手順で貢献できます：

1. このリポジトリをフォークします
2. フィーチャーブランチを作成します (`git checkout -b feature/amazing-feature`)
3. 変更をコミットします (`git commit -m 'Add some amazing feature'`)
4. ブランチにプッシュします (`git push origin feature/amazing-feature`)
5. プルリクエストを作成します

## 問題の報告

バグを発見した場合や機能要求がある場合は、[Issues](https://github.com/KenichiroArai/kmg-tool-base/issues) で報告してください。

## ライセンス

このプロジェクトは MIT ライセンスの下で公開されています。詳細は[LICENSE](LICENSE)ファイルを参照してください。
