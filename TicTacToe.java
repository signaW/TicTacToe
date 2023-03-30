package at3;

import java.util.Scanner;

public class TicTacToe {
    //ゲームボードはprotectedに宣言してカプセル化し、メソッドを通じないと操作できないようにする
    protected int[][] gameBoard = {{0,0,0},{0,0,0},{0,0,0}};

    //ゲームボードの現状をコンソル画面上に出力するメソッド
    protected void GamePrinter() {
        /*
         * 空いているマスは「.」
         * プレイヤー1のマークは「X」
         * プレイヤー2のマークは「O」
         * に出力する
         */
        System.out.println("=======================");
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (gameBoard[i][j] == 0) {
                    System.out.print(". ");
                } else if (gameBoard[i][j] == 1) {
                    System.out.print("X ");
                } else if (gameBoard[i][j] == 2) {
                    System.out.print("O ");
                }
            }
            System.out.print('\n');
        }
        System.out.println("=======================");
    }

    //ユーザの入力を受けてゲームボードに反映するメソッド
    protected void PlayerHandler(int playerN) {
        int userInpt=999, userInptX=999, userInptY=999;
        
        // ユーザの入力を受ける
        System.out.println("Player " + playerN + " 入力: ");
        
        while (true) {
            //try-catch文とif文でユーザの誤操作に対応する
            Scanner scanner = new Scanner(System.in);
            String userInptStr = scanner.next();
            try {
                userInpt = Integer.valueOf(userInptStr).intValue();    
            } catch (NumberFormatException e) {
                System.out.println("数字を入力してください");
                continue;
            }

            //ユーザからの1~9範囲の入力を座標に変換する
            if (userInpt == 1) {
                userInptX = 0;
                userInptY = 2;
            } else if (userInpt == 2) {
                userInptX = 1;
                userInptY = 2;
            } else if (userInpt == 3) {
                userInptX = 2;
                userInptY = 2;
            } else if (userInpt == 4) {
                userInptX = 0;
                userInptY = 1;
            } else if (userInpt == 5) {
                userInptX = 1;
                userInptY = 1;
            } else if (userInpt == 6) {
                userInptX = 2;
                userInptY = 1;
            } else if (userInpt == 7) {
                userInptX = 0;
                userInptY = 0;
            } else if (userInpt == 8) {
                userInptX = 1;
                userInptY = 0;
            } else if (userInpt == 9) {
                userInptX = 2;
                userInptY = 0;
            } else { // 誤入力
                System.out.println("誤入力です. 再入力ください\n入力: ");
                continue;
            }

            if (gameBoard[userInptY][userInptX] == 0) {
                break;  //空いているマスならwhile文から脱出
            } else {
                System.out.println("そのマスはすでに使用中です. 再入力ください\n入力: ");
            }
        }

        // ゲームボードに反映する
        gameBoard[userInptY][userInptX] = playerN;
        GamePrinter();  //現状を出力する
    }

    // ゲーム終了条件を満たしたか判定するメソッド
    protected Boolean GameStatMonitor () {
        /*
         * ゲームの終了条件を満たしたらfalse、そうでなければtrueを返す
         * プレイヤー1の勝ちなら1
         * プレイヤー2の勝ちなら2
         * 引き分けなら3をGameOverHandlerに渡す
         */

        // 横連続3個確認
        int p1Horizontal = 0;
        int p2Horizontal = 0;
        for (int y=0; y<3; y++) {
            p1Horizontal = 0;
            p2Horizontal = 0;
            for (int x=0; x<3; x++) {
                if (gameBoard[y][x] == 1) {
                    p1Horizontal++;
                } else if (gameBoard[y][x] == 2) {
                    p2Horizontal++;
                }
            }
            
            if (p1Horizontal == 3) {
                GameOverHandler(1);
                return false;
            } else if (p2Horizontal == 3) {
                GameOverHandler(2);
                return false;
            }
        }

        // 縦連続3個確認
        int p1Vertical = 0;
        int p2Vertical = 0;
        for (int x=0; x<3; x++) {
            p1Vertical = 0;
            p2Vertical = 0;
            for (int y=0; y<3; y++) {
                if (gameBoard[y][x] == 1) {
                    p1Vertical++;
                } else if (gameBoard[y][x] == 2) {
                    p2Vertical++;
                }
            }
            
            if (p1Vertical == 3) {
                GameOverHandler(1);
                return false;
            } else if (p2Vertical == 3) {
                GameOverHandler(2);
                return false;
            }
        }

        // 斜め連続3個確認
        // 右下
        int p1DiagonalRight = 0;
        int p2DiagonalRight = 0;
        for (int i=0; i<3; i++) {
            if (gameBoard[i][i] == 1) {
                p1DiagonalRight++;
            } else if (gameBoard[i][i] == 2) {
                p2DiagonalRight++;
            }
        }

        // 左下
        int p1DiagonalLeft = 0;
        int p2DiagonalLeft = 0;
        for (int i=0; i<3; i++) {
            if (gameBoard[i][(-1)*(i-2)] == 1) {
                p1DiagonalLeft++;
            } else if (gameBoard[i][(-1)*(i-2)] == 2) {
                p2DiagonalLeft++;
            }
        }

        if (p1DiagonalRight == 3 || p1DiagonalLeft == 3) {
            GameOverHandler(1);
            return false;
        } else if (p2DiagonalRight == 3 || p2DiagonalLeft == 3) {
            GameOverHandler(2);
            return false;
        }

        // ゲームボードに空いているマスがなく、勝負が決まらなかったら引き分け
        int emptySpace = 0;
        for (int y=0; y<3; y++) {
            for (int x=0; x<3; x++) {
                if (gameBoard[y][x] == 0) {
                    emptySpace++;
                }
            }
        }

        if (emptySpace == 0) {
            GameOverHandler(3);
            return false;
        }

        // まだゲームが終わっていないならtrue
        return true;
    }
    
    // ゲーム結果を出力する
    protected void GameOverHandler(int winner) {
        if (winner == 1) { // 1P 勝利
            System.out.println("Player 1の勝利です! おめでとうございます!");
        } else if (winner == 2) { // 2P 勝利
            System.out.println("Player 2の勝利です! おめでとうございます!");
        } else if (winner == 3) { // 引き分け
            System.out.println("引き分けです");
        }
    }
}
