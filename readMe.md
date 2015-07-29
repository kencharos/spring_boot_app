Spring サンプルアプリケーション
---------------------------

## 概要

[@IT連載記事](http://www.atmarkit.co.jp/ait/articles/1507/02/news012.html) で使用するサンプルアプリケーションのSpring Boot版です。
## 環境

+ JDK8
+ Spring Boot 1.2.5

## 導入方法
データベースは組み込みDBのH2を使用していますので、
特に準備作業は不要です。

`mvn package` を実行すると、 target フォルダに jarファイルが作成されますので、
`java -jar xxx.jar` で実行可能です。
