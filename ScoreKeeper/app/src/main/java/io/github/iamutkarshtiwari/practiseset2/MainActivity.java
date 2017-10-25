package io.github.iamutkarshtiwari.scorekeeper;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

import static io.github.iamutkarshtiwari.scorekeeper.R.id.team1_score_board;
import static io.github.iamutkarshtiwari.scorekeeper.R.id.team2_score_board;


public class MainActivity extends AppCompatActivity {

    final String[] TEAM_NAMES = {"Kolkata\nKnight Riders", "Mumbai\nIndians", "Chennai\nSuper Kings", "Sunrisers\nHyderabad",
            "Delhi\nDareDevils", "Kings IX\nPunjab", "Rajasthan\nRoyals", "Royal Challengers\nBangalore"};

    final String[] TEAM1_PLAYERS = {"Gautam Gambhir", "Yusuf Pathan", "Robin Uthappa", "Manish Pandey",
            "Rovman Powell", "Darren Bravo", "Piyush Chawla", "Sheldon Jackson", "Rishi Dhawan", "Suryakumar Yadav"};

    final String[] TEAM2_PLAYERS = {"Rohit Sharma", "Hardik Pandya", "Lasith Malinga", "Tim Southee",
            "Lendl Simmons", "Kieron Pollard", "Harbhajan Singh", "Nitish Rana", "Parthiv Patel", "J Suchith"};

    int inning;
    int currentPlayerID;
    int nextPlayer;
    Team firstTeam;
    Team secondTeam;
    Team currentTeam;
    Player firstPlayer;
    Player secondPlayer;
    Player currentPlayer;

    /**
     * @param view    touch of which is toggled
     * @param enabled flag to enable/disable the touch on this view
     */
    public static void enableTouchOnView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableTouchOnView(group.getChildAt(idx), enabled);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reset(null);
    }

    /**
     * Sets up the whole data for new match
     *
     * @param view
     */
    public void reset(View view) {

        inning = 1;
        currentPlayerID = 1;

        // Initiate inning
        changeOfInning(inning);

        // Selects two random teams
        Random rand = new Random();
        int rand1 = rand.nextInt(8);
        int rand2 = rand.nextInt(8);

        while (rand2 == rand1) {
            rand2 = rand.nextInt(8);
        }

        firstTeam = new Team(TEAM_NAMES[rand1]);
        secondTeam = new Team(TEAM_NAMES[rand2]);
        currentTeam = (inning == 1) ? firstTeam : secondTeam;

        // Set all ball icons to Color.WHITE
        for (int i = 1; i <= 6; i++) {
            updateTint(findViewById(R.id.team1_score_board), i, Color.WHITE);
            updateTint(findViewById(R.id.team2_score_board), i, Color.WHITE);
        }

        // Refresh the stats
        statsRefresh(firstTeam, secondTeam, inning);

        // Initialize first two players
        firstPlayer = new Player(TEAM1_PLAYERS[0], 0);
        secondPlayer = new Player(TEAM1_PLAYERS[1], 1);
        nextPlayer = 1;
        currentPlayer = (currentPlayerID == 1) ? firstPlayer : secondPlayer;

        // Refresh player scores
        playerScoreRefresh(firstPlayer, secondPlayer, currentPlayerID);

        // Hide win textView and show the current player scores
        TextView result = (TextView) findViewById(R.id.result);
        View currentPlayersScores = findViewById(R.id.current_players_score);
        result.setVisibility(View.INVISIBLE);
        currentPlayersScores.setVisibility(View.VISIBLE);
    }

    /**
     * @param view function is called on this view
     */
    public void updateRuns(View view) {

        switch (view.getId()) {
            case R.id.run_0:
                updateRunsAndBalls(1, 0);
                break;
            case R.id.run_1:
                updateRunsAndBalls(1, 1);
                break;
            case R.id.run_2:
                updateRunsAndBalls(1, 2);
                break;
            case R.id.run_3:
                updateRunsAndBalls(1, 3);
                break;
            case R.id.run_4:
                updateRunsAndBalls(1, 4);
                break;
            case R.id.run_6:
                updateRunsAndBalls(1, 6);
                break;
            case R.id.wide:
                updateRunsAndBalls(0, 1);
                break;
            case R.id.no_ball:
                updateRunsAndBalls(0, 1);
                break;
            case R.id.out:
                updateRunsAndBalls(1, 0);
                playerOut();
                break;
            default:
                break;
        }

        playerScoreRefresh(firstPlayer, secondPlayer, currentPlayerID);
        statsRefresh(firstTeam, secondTeam, inning);
    }

    /**
     * @param balls number of balls delivered
     * @param runs  number of runs scored a the ball
     */
    public void updateRunsAndBalls(int balls, int runs) {
        currentTeam.balls_left -= balls;
        currentTeam.runs += runs;
        currentPlayer.balls += balls;
        currentPlayer.runs += runs;

        if (balls != 0) {
            View teamScoreBoard = findViewById(inning == 1 ? R.id.team1_score_board : R.id.team2_score_board);

            // Update balls colors
            int overBall = (120 - currentTeam.balls_left) % 6;
            if (overBall == 1) {
                for (int i = 1; i <= 6; i++) {
                    updateTint(teamScoreBoard, i, Color.WHITE);
                }
                updateTint(teamScoreBoard, overBall, Color.RED);
            } else {
                // If over complete switch player
                if (overBall == 0) {
                    overBall = 6;
                    currentPlayerID = currentPlayerID == 1 ? 2 : 1;
                    currentPlayer = currentPlayerID == 1 ? firstPlayer : secondPlayer;
                }
                updateTint(teamScoreBoard, overBall, Color.RED);
            }

            if (currentTeam.isInningComplete()) {
                if (inning == 1) {
                    inning = 2;
                    changeOfInning(inning);
                } else {
                    declareWinner();
                }
            }
        }
    }

    /**
     * @param teamScoreBoard scoreboard's ID with dots to be updated
     * @param overBall       number of the ball in this over
     * @param color          color of the tint value
     */
    public void updateTint(View teamScoreBoard, int overBall, int color) {
        String buttonID = "dot" + "_" + (overBall);
        int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
        View dot = teamScoreBoard.findViewById(resID);
        dot.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    /**
     * Updates the data when a player is out
     */
    public void playerOut() {
        currentTeam.wickets++;
        nextPlayer = Math.max(firstPlayer.index, secondPlayer.index) + 1;
        if (nextPlayer > 9) {
            if (inning == 1) {
                inning = 2;
                changeOfInning(inning);
                return;
            } else {
                declareWinner();
                changeOfInning(3);
                return;
            }
        }

        String arrayName[] = inning == 1 ? TEAM1_PLAYERS : TEAM2_PLAYERS;

        if (currentPlayer == firstPlayer) {
            currentPlayer = firstPlayer = new Player(arrayName[nextPlayer], nextPlayer);
        } else {
            currentPlayer = secondPlayer = new Player(arrayName[nextPlayer], nextPlayer);
        }


    }

    /**
     * Displays the win message
     */
    public void declareWinner() {
        // Toggle the win message
        TextView result = (TextView) findViewById(R.id.result);
        View currentPlayersScores = findViewById(R.id.current_players_score);
        result.setVisibility(View.VISIBLE);
        currentPlayersScores.setVisibility(View.INVISIBLE);


        if (firstTeam.runs > secondTeam.runs) {
            if (firstTeam.wickets < secondTeam.wickets) {
                result.setText(String.format(getString(R.string.won_by_wickets_and_run), firstTeam.name,
                        Math.abs(firstTeam.runs - secondTeam.runs), Math.abs(secondTeam.wickets - firstTeam.wickets)));
            } else {
                result.setText(String.format(getString(R.string.won_by_run), firstTeam.name,
                        Math.abs(firstTeam.runs - secondTeam.runs)));
            }
        } else if (firstTeam.runs == secondTeam.runs) {
            result.setText(String.format(getString(R.string.tie), firstTeam.name, secondTeam.name));
        } else {
            if (firstTeam.wickets > secondTeam.wickets) {
                result.setText(String.format(getString(R.string.won_by_wickets_and_run), secondTeam.name,
                        Math.abs(firstTeam.runs - secondTeam.runs), Math.abs(secondTeam.wickets - firstTeam.wickets)));
            } else {
                result.setText(String.format(getString(R.string.won_by_run), secondTeam.name,
                        Math.abs(firstTeam.runs - secondTeam.runs)));
            }
        }
    }

    /**
     * @param inning inning value
     */
    public void changeOfInning(int inning) {

        Button firstInning = (Button) findViewById(R.id.inning1_button);
        Button secondInning = (Button) findViewById(R.id.inning2_button);

        View firstTeamActions = findViewById(R.id.team1_actions);
        View secondTeamActions = findViewById(R.id.team2_actions);

        if (inning == 1) {
            firstInning.setSelected(true);
            secondInning.setSelected(false);

            enableTouchOnView(firstTeamActions, true);
            enableTouchOnView(secondTeamActions, false);
        } else if (inning == 2) {
            firstInning.setSelected(false);
            secondInning.setSelected(true);

            enableTouchOnView(firstTeamActions, false);
            enableTouchOnView(secondTeamActions, true);

            firstPlayer = new Player(TEAM2_PLAYERS[0], 0);
            secondPlayer = new Player(TEAM2_PLAYERS[1], 1);
            currentPlayer = firstPlayer;
            nextPlayer = 0;
            currentTeam = secondTeam;
        } else { // If match is complete
            enableTouchOnView(firstTeamActions, false);
            enableTouchOnView(secondTeamActions, false);
        }

    }

    /**
     * @param player1         first player
     * @param player2         second player
     * @param currentPlayerID current player's ID
     */
    public void playerScoreRefresh(Player player1, Player player2, int currentPlayerID) {
        updatePlayerScores(player1, R.id.player1_score, (currentPlayerID == 1) ? 1 : 0);
        updatePlayerScores(player2, R.id.player2_score, (currentPlayerID == 1) ? 0 : 1);

    }

    /**
     * @param pl              object of this player
     * @param player_score_id this player's score_panel id
     * @param is_Current      flag to set visibility of yellow dot
     */
    public void updatePlayerScores(Player pl, int player_score_id, int is_Current) {

        View player = findViewById(player_score_id);
        TextView playerName = (TextView) player.findViewById(R.id.player);
        TextView playerBalls = (TextView) player.findViewById(R.id.player_balls);
        TextView playerRuns = (TextView) player.findViewById(R.id.player_runs);

        playerName.setText(pl.name);
        setTextValues(playerRuns, "%d", pl.runs);
        setTextValues(playerBalls, "%d", pl.balls);

        View dot = player.findViewById(R.id.dot);
        dot.setVisibility(is_Current == 1 ? View.VISIBLE : View.INVISIBLE);

    }


    /**
     * Sets the alignment and margins for the passed textView
     *
     * @param txtV      textView to be modified
     * @param alignment alignment value for the layout gravity
     */
    public void setTextLayout(TextView txtV, int alignment) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, getMarginInDP(8), 0, 0);
        params.gravity = alignment;
        txtV.setLayoutParams(params);
    }

    /**
     * Converts dp values to pixels
     *
     * @param sizeInDP size in DP
     * @return value converted to pixels
     */
    public int getMarginInDP(int sizeInDP) {

        float d = this.getResources().getDisplayMetrics().density;
        return (int) (sizeInDP * d);
    }

    /**
     * Refreshes the stats for both the teams
     *
     * @param firstTeam  team on the left side
     * @param secondTeam team on the right side
     */
    public void statsRefresh(Team firstTeam, Team secondTeam, int inning) {
        teamStatsRefresh(firstTeam, R.id.team1_stats, team1_score_board, Gravity.START, (inning == 1) ? 1 : 0);
        teamStatsRefresh(secondTeam, R.id.team2_stats, team2_score_board, Gravity.END, (inning == 2) ? 1 : 0);

    }

    /**
     * @param team           this team
     * @param stats_board_id id of this team's stats board
     * @param score_board_id id of this team's score board
     * @param gravity        layout_gravity value for this stats board
     * @param inning         inning value
     */
    public void teamStatsRefresh(Team team, int stats_board_id, int score_board_id, int gravity, int inning) {
        // Team stats update
        View teamStats = findViewById(stats_board_id);
        TextView teamOvers = (TextView) teamStats.findViewById(R.id.overs);
        TextView teamRunRate = (TextView) teamStats.findViewById(R.id.run_rate);
        TextView teamBallsLeft = (TextView) teamStats.findViewById(R.id.balls_left);

        setTextValues(teamOvers, "%.1f", team.overs);
        setTextValues(teamRunRate, "%.1f", team.run_rate);
        setTextValues(teamBallsLeft, "%d", team.balls_left);

        setTextLayout(teamOvers, gravity);
        setTextLayout(teamRunRate, gravity);
        setTextLayout(teamBallsLeft, gravity);

        // Team name and main score update
        View teamBoard = findViewById(score_board_id);
        TextView teamName = (TextView) teamBoard.findViewById(R.id.team_name);
        TextView teamScore = (TextView) teamBoard.findViewById(R.id.score);

        int colorYellow = 0xFFD4E920;
        int colorWhite = 0xFFFFFFFF;

        teamName.setText(team.name);
        teamName.setTextColor((inning == 1) ? colorYellow : colorWhite);
        teamScore.setText(team.runs + "/" + team.wickets);
        teamScore.setTextColor((inning == 1) ? colorYellow : colorWhite);

        if (inning == 2) {
            if (team.runs > firstTeam.runs) {
                declareWinner();
                return;
            }
        }
    }

    public void setTextValues(TextView txtV, String formatter, double value) {
        txtV.setText(String.format(java.util.Locale.US, formatter, value));
    }

    public void setTextValues(TextView txtV, String formatter, int value) {
        txtV.setText(String.format(java.util.Locale.US, formatter, value));
    }


}

class Player {
    int index;
    String name;
    int runs;
    int balls;

    /**
     * @param index index of this player in the array
     * @param name  of this player
     */
    Player(String name, int index) {
        this.index = index;
        this.name = name;
        this.runs = 0;
        this.balls = 0;
    }
}

class Team {
    String name;
    int runs;
    int wickets;
    double run_rate;
    int balls_left;
    double overs;

    /**
     * @param name of this team
     */
    Team(String name) {
        this.name = name;
        this.runs = 0;
        this.wickets = 0;
        this.overs = 0.0;
        balls_left = 120;
        run_rate = 0;
    }

    /**
     * Updates the stats on every ball
     */
    public boolean isInningComplete() {
        this.run_rate = (double) runs / (120 - balls_left);
        this.overs = (double) ((120 - balls_left) / 6) + (0.1 * ((120 - balls_left) % 6));

        if (this.overs >= 20 || this.balls_left == 0) {
            return true;
        }
        return false;
    }
}
