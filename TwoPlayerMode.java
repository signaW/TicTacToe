package at3;

public class TwoPlayerMode extends TicTacToe{
    public void run() {
        firstRun(); //最初起動時簡単な案内文を出力する

        /*
         * GameStatMonitorはゲームが終了したか確認し
         * 終了条件を満たしたらfalseをreturnする
         * 終了条件を満たしたらwhile文も終了する
         */

        while (GameStatMonitor()) {
            PlayerHandler(1);   //プレイヤー1から入力を受けゲームボードに反映する
            if (!GameStatMonitor()) break;
            PlayerHandler(2);   //プレイヤー2から入力を受けゲームボードに反映する
        }
    }
    
    //最初起動時簡単な案内文を出力する
    private void firstRun() {
        System.out.println("================================================");
        System.out.println("2人プレイを開始します\nPlayer 1が「X」、Player 2が「O」です");
        System.out.println("各マスは下の表記と対応しています");
        System.out.println("7 8 9\n4 5 6\n1 2 3");
        GamePrinter();
    }
}
