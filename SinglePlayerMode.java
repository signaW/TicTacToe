package at3;

public class SinglePlayerMode extends TicTacToe {
    public void run() {
        firstRun(); //最初起動時簡単な案内文を出力する
        
        /*
         * GameStatMonitorはゲームが終了したか確認し
         * 終了条件を満たしたらfalseをreturnする
         * 終了条件を満たしたらwhile文も終了する
         */
        while (GameStatMonitor()) {
            PlayerHandler(1);   //ユーザから入力を受けゲームボードに反映する
            if (!GameStatMonitor()) break;
            PlayerCOMHandler(); //コンピュータが最適化を選択しゲームボードに反映する
        }
    }

    //最初起動時簡単な案内文を出力する
    private void firstRun() {
        System.out.println("================================================");
        System.out.println("AI対戦を開始します\nあなたは「X」です");
        System.out.println("各マスは下の表記と対応しています");
        System.out.println("7 8 9\n4 5 6\n1 2 3");
        GamePrinter();
    }

    //コンピュータの動作を示すメソッド
    private void PlayerCOMHandler() {
        // コンピュータはすべてのマスを評価し、一番評価の良いマスにマークを置く

        /*
         * マークを置く座標を示すnextXとnextY
         * 評価値を保存するestimate
         * 最高評価値を保存するmax
         */
        int nextX=999, nextY=999, estimate=0, max=0;

        //すべてのマスを評価する
        for (int y=0; y<3; y++) {
            for (int x=0; x<3; x++) {
                if (gameBoard[y][x] != 0)   continue;   // すでに使われてるマスはスキップ
                
                estimate = Estimate(x, y);  //評価値を返すメソッドEstimateを呼び出す

                if (estimate > max) {   //一番良い評価値と座標を保存するための処理
                    max = estimate;
                    nextX = x;
                    nextY = y;
                }
            }
        }

        // 最後まで次どこに置くか決まらなかったら空いているところに適当に置く
        if (nextX == 999 || nextY == 999) {
            for (int y=0; y<3; y++) {
                for (int x=0; x<3; x++) {
                    if (gameBoard[y][x] == 0) {
                        gameBoard[y][x] = 2;
                        GamePrinter();
                        return;
                    }            
                }
            }
        }

        // 評価が一番高いマスを選択してマークを置く
        gameBoard[nextY][nextX] = 2;
        GamePrinter();  //ゲームボードの現状を出力するメソッド
    }

    //マスの評価を行うメソッド
    private int Estimate(int targetX, int targetY) {
        /*
         * targetXとtargetY評価を行う座標である
         * 優先順位は次の通りであり、順位によって点数を付与する
         * 1. マークを置くとコンピュータがすぐに勝てるマスは3点を付与
         * 2. マークを置く置くとコンピュータがすぐに負けるマスは2点を付与
         * 3. マークを2個連続でつなげることができ、3個までつなげる可能性のあるマスは1点を付与
         * 4. 何の意味もないマスは0点を付与
         */

        // COMが必ず勝てるマス
        if (isRow(targetX, targetY, 2)) { 
            return 3;   //3点を付与
        }


        // 選択しないと必ず負けるマス(防御)
        // P2の立場で、P1が3連続を達成できる場所に置きたい
        if (isRow(targetX, targetY, 1)) {
            return 2;   //2点を付与
        }

        // 2個を連続でつなぐことができ、3個までつなげる可能性があるマス
        int numO=0, numX=0;

        //最初に横と縦方向を探索する
        for (int x=0; x<3; x++) {
            if (gameBoard[targetY][x] == 1) {
                numX++;
            } else if (gameBoard[targetY][x] == 2) {
                numO++;
            }
        }
        if (numO == 1 && numX == 0) {
            return 1;
        }

        numO=0; numX=0;
        for (int y=0; y<3; y++) {
            if (gameBoard[y][targetX] == 1) {
                numX++;
            } else if (gameBoard[y][targetX] == 2) {
                numO++;
            }
        }
        
        if (numO == 1 && numX == 0) {
            return 1;
        }

        //斜め方向を探索する
        if ((targetY==0 && targetX==0) || (targetY==0 && targetX==0) || (targetY==0 && targetX==0) || (targetY==0 && targetX==0) || (targetY==0 && targetX==0)) {
            numO=0; numX=0;
            for (int i=0; i<3; i++) {
                if (gameBoard[i][i] == 1) {
                    numX++;
                } else if (gameBoard[i][i] == 2) {
                    numO++;
                }
            }

            if (numO == 1 && numX == 0) {
                return 1;
            }

            numO=0; numX=0;
            for (int i=0; i<3; i++) {
                if (gameBoard[i][(-1)*(i-2)] == 1) {
                    numX++;
                } else if (gameBoard[i][(-1)*(i-2)] == 2) {
                    numO++;
                }
            }

            if (numO == 1 && numX == 0) {
                return 1;
            }
        }

        // 何でもないマスは0点を付与
        return 0;
    }


    // (targetY, targetX)に置いたとき、PlayerNは3連続を達成できるのかを判別するメソッド
    private Boolean isRow(int targetX, int targetY, int playerN) {
        int [][] estiBoard = {{0,0,0},{0,0,0},{0,0,0}};
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                estiBoard[i][j] = gameBoard[i][j];
            }
        }
        estiBoard[targetY][targetX] = playerN;

        // 斜めで連続3個
        // 右下
        int diagonalRight = 0;
        for (int i=0; i<3; i++) {
            if (estiBoard[i][i] == playerN) {
                diagonalRight++;
            }
        }

        // 左下
        int diagonalLeft = 0;
        for (int i=0; i<3; i++) {
            if (estiBoard[i][(-1)*(i-2)] == playerN) {
                diagonalLeft++;
            }
        }

        // 縦連続3個
        int vertical = 0;
        for (int i=0; i<3; i++) {
            if (estiBoard[i][targetX] == playerN) {
                vertical++;
            }
        }


        // 横連続3個
        int horizontal = 0;
        for (int i=0; i<3; i++) {
            if (estiBoard[targetY][i] == playerN) {
                horizontal++;
            }
        }
        

        //3個連続でつなげるならtrue
        if (diagonalRight == 3 || diagonalLeft == 3 || vertical == 3 || horizontal == 3) {
            return true;
        }

        //そうでなければfalse
        return false;
    }



    // 1人プレイモードでは相手が2PではなくCOMであるため、Overrideする
    // GameOverHandlerは親クラスで呼び出すため、override使用が必要
    @Override
    protected void GameOverHandler(int winner) {
        if (winner == 1) { // 1P 勝利
            System.out.println("あなたの勝利です! おめでとうございます!");
        } else if (winner == 2) { // 2P 勝利
            System.out.println("AIの勝利です. 残念");
        } else if (winner == 3) { // 引き分け
            System.out.println("引き分けです");
        }
    }
}
