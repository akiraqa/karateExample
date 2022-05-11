# karateExample

## このリポジトリについて

以下の記事・サンプルコードを元に、DBUnitやlogbackを組み合わせ、karateでDBアクセス系APIをテストするサンプルです。

- [APIテスト自動化ツール「Karate」現場ですぐに使える実装パターン8選(前編)](https://note.com/shift_tech/n/n614cedcbe9d7)
- [後編(javaクラスの呼び出し、並列実行など)](https://note.com/shift_tech/n/n2588d2826f60)
- [サンプルコード(github)](https://github.com/YusukeKashiwagi44/KarateExample)

いまのところ、テスト前後にDBの内容をXML/Excel(XLS)/JSON/YMLファイルに出力することしかやってませんが、同じ要領でなんでもできるはず。

XML以外の形式での出力は[database-rider](https://database-rider.github.io/database-rider)を使っています。

## karateについて

[karate本家](https://github.com/karatelabs/karate)

[REST API 自動テストツールまとめ](https://qiita.com/os1ma/items/9eadcfb91fa26af762be)

## dockerでJDK
JDKをホストOSにインストールしたくない場合、JDK入りdockerコンテナでも実行できます。

その場合、karateのプロジェクトのディレクトリで以下のようにコンテナ内でgradleを実行します。
```
docker run --rm  --volume $PWD:$PWD --workdir $PWD --network host -it openjdk:11-slim ./gradlew clean test
```

## karate実行方法
### 1つのfeatureだけテストする
```
./gradlew clean test -Dkarate.options="classpath:dbtest/mysample.feature"
```

### Runnerのmethodを指定してテストする
```
./gradlew clean test --tests "dbtest.DbtestRunner.run"
```
Runnerのmethod側でタグ指定したり並列実行を指定したりできるので、YusukeKashiwagi44さんの記事を参照。

