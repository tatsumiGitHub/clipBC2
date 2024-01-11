# clipBC2
ユーザが予め指定したテキストを1クリックでクリップボードに保存するプログラムです．

**本プログラムを用いて行う一切の行為，被った損害・損失に対しては，一切の責任を負いかねます**

## 導入とビルド

### 1. 実行環境
このプログラムの実行に求められる環境は以下の通りです．
```
 java 17.0.7 2023-04-18 LTS
 Java(TM) SE Runtime Environment (build 17.0.7+8-LTS-224)
 Java HotSpot(TM) 64-Bit Server VM (build 17.0.7+8-LTS-224, mixed mode, sharing)
```

### 2. ビルド
特別な操作を必要とせず，このプログラムをビルドすることが可能です．
以下の操作により，ビルドが可能です．
```
make
```
jarファイルを生成するには，以下の操作を実行してください．
```
make jar
```
ビルドをクリーンアップするには，以下の操作を実行してください．
```
make clean
```

### 3. 実行
このプログラムは実行時に，
実行に必要なファイルをプログラムが存在するディレクトリ内に生成するため，
予め各種ファイルを用意する必要がありません．

- .object_list.dat 生成したインスタンスを保存するファイル

## 操作方法
このプログラムは，GUI操作により，クリップボードにテキストを保存するボタンを作成する．

|アイコン|内容|
|-|-|
|<img src=".img/back/button_base.png" width="40%" />|1つ前に訪れたページへ移動する．|
|<img src=".img/home/button_base.png" width="40%" />|パネル"Main"へ移動し，ホームページへ移動する．|
|<img src=".img/button_base.png" width="40%" />|パネル"Main"へ移動する．|
|<img src=".img/make/button_base.png" width="40%" />|パネル"Add Button"へ移動する．|
|<img src=".img/sort/button_base.png" width="40%" />|パネル"Sort Button"へ移動する．|
|<img src=".img/delete/button_base.png" width="40%" />|パネル"Delete Button"へ移動する．|
|<img src=".img/close/button_base.png" width="40%" />|ウィンドウを閉じる．|

各パネルにおける操作方法を以下に記載する．
### Main
このパネルの中央に位置する四角い領域を**ページ**とよぶ．
また，初期位置となるページのことを**ホームページ**とよぶ．

クリックすることにより，クリップボードにテキストを保存できるボタン，または別のページに遷移するボタンが存在するパネルである．
これらのボタンはページ内に存在し，後述のパネル"Add Button"により追加される．

### Add Button
クリックすることによりテキストを保存するボタン，
または新しいページに移動するボタンを現在のページに作成するパネルである．
なお，一番上に存在するコンボボックスでボタン名を指定し，updateのチェックボックスを有効にすることにより，
既存のボタンを更新することも可能である．

### Sort Button
現在のページに存在するボタンの順序を並べ替えるパネルである．
枠内のアイテムをドラッグすることで並べ替え，
右上のソートボタンを押すことで，順序を確定する．

### Delete Button
現在のページに存在するボタンを削除するパネルである．
削除時，"I delete the button."のチェックボックスを有効にする必要がある．