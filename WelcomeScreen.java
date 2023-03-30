package at3;

import java.util.Scanner;

public class WelcomeScreen {
    public static void firstRun() {
        int cmd = 0;    // ユーザが選択したモードを覚えるint

        while (true) {  // 実行されたら最初に出力され、ユーザからモードの選択を求める
            System.out.println("================================================");
            System.out.println("Tic Tac Toe! ゲームへようこそ");
            System.out.println("モードを選択してください");
            System.out.println("1. 1人プレイモード(AI対戦)");
            System.out.println("2. 2人プレイモード");
            System.out.println("3. ヘルプ");

            //ユーザからモードの選択を受ける
            Scanner scanner = new Scanner(System.in);
            String cmdStr = scanner.next();
            try {   //入力されたコマンドが数字ではない場合、try-catch文で再入力を求める
                cmd = Integer.valueOf(cmdStr).intValue();    
            } catch (NumberFormatException e) {
                System.out.println("数字を入力してください");
                continue;
            }
            
            //ユーザからの入力が指定値を超えている場合、再入力を求める
            if (cmd != 1 && cmd != 2 && cmd != 3) {
                System.out.println("コマンドを確認してから再入力ください");
            } else if (cmd == 3) {
                /*
                 * 3が入力されたらヘルプを出力する
                 * 出力後メイン画面に戻るためにwhile文の中に配置する
                 */
                helpList();
            } else {
                break;
            }
        }
        
        if (cmd == 1) { // 1が入力されたら1人プレイモードを実行
            new SinglePlayerMode().run();
        } else if (cmd == 2) {  // 2が入力されたら2人プレイモードを実行
            new TwoPlayerMode().run();
        }
    }

    //プログラムの使い方とゲームのルールを説明するメソッド
    public static void helpList() {
        System.out.println("================== About Game ==================");
        System.out.println("Tic Tac Toe! はマルバツゲームという名前でも知られています");
        System.out.println("このゲームは1人または2人で楽しめます");
        System.out.println("1人プレイモードでは、AIとの対戦になります");
        System.out.println("===================== Rule =====================");
        System.out.println("3x3サイズのボード上");
        System.out.println("縦、横、斜め方向に自分のマークを先に3個並べば勝利です");
        System.out.println("1Pは「X」、2Pは「O」で表示され、空いている所は「.」で表示されます");
        System.out.println("==================== Control ===================");
        System.out.println("数字を入力してマークを置く場所を指定します");
        System.out.println("7 8 9\n4 5 6\n1 2 3");
        System.out.println("各マスは上記の表に対応しています");
    }
}
